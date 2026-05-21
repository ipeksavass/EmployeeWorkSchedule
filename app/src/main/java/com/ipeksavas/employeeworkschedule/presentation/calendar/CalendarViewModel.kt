package com.ipeksavas.employeeworkschedule.presentation.calendar

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ipeksavas.employeeworkschedule.domain.model.Permission
import com.ipeksavas.employeeworkschedule.domain.usecase.GetEmployeesUseCase
import com.ipeksavas.employeeworkschedule.domain.usecase.GetPermissionsUseCase
import com.ipeksavas.employeeworkschedule.domain.usecase.SavePermissionsUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarViewModel(
    private val getEmployeesUseCase: GetEmployeesUseCase,
    private val getPermissionsUseCase: GetPermissionsUseCase,
    private val savePermissionsUseCase: SavePermissionsUseCase
) : ViewModel() {
    
    private val _state = mutableStateOf(CalendarState())
    val state: State<CalendarState> = _state
    
    fun loadEmployeeData(employeeId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val allEmployees = getEmployeesUseCase().first()
                val foundEmployee = allEmployees.find { it.id == employeeId }
                
                if (foundEmployee == null) {
                    _state.value = _state.value.copy(isLoading = false, error = "Personel bulunamadı!")
                    return@launch
                }
                
                val initialPermissions = getPermissionsUseCase(employeeId).first().map { it.date }
                
                val calculatedQuota = 30 - initialPermissions.size
                
                _state.value = _state.value.copy(
                    isLoading = false,
                    employee = foundEmployee.copy(remainingPermissionQuota = calculatedQuota),
                    selectedDates = initialPermissions,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Mevcut izinler yüklenirken bir hata oluştu."
                )
            }
        }
    }
    
    fun onDateSelected(date: LocalDate) {
        val employee = _state.value.employee ?: return
        
        val currentDates = _state.value.selectedDates.toMutableList()
        val isAlreadySelected = currentDates.contains(date)
        
        if (!isAlreadySelected && employee.remainingPermissionQuota <= 0) {
            _state.value = _state.value.copy(
                error = "${employee.name} için yıllık 30 günlük izin kotası dolmuştur!"
            )
            return
        }
        
        if (isAlreadySelected) {
            currentDates.remove(date)
        } else {
            currentDates.add(date)
        }
        
        val updatedQuota = 30 - currentDates.size
        
        _state.value = _state.value.copy(
            selectedDates = currentDates,
            employee = employee.copy(remainingPermissionQuota = updatedQuota),
            error = null
        )
    }
    
    fun saveChanges(onSuccess: () -> Unit) {
        val employee = _state.value.employee ?: return
        
        val permissionsToSave = _state.value.selectedDates.map { date ->
            Permission(employeeId = employee.id, date = date)
        }
        
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                savePermissionsUseCase(employee.id, permissionsToSave)
                _state.value = _state.value.copy(isLoading = false)
                onSuccess()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Değişiklikler kaydedilirken bir hata oluştu."
                )
            }
        }
    }
    
    fun nextMonth() {
        _state.value = _state.value.copy(currentMonth = _state.value.currentMonth.plusMonths(1))
    }
    fun previousMonth() {
        _state.value = _state.value.copy(currentMonth = _state.value.currentMonth.minusMonths(1))
    }
}