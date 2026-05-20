package com.ipeksavas.employeeworkschedule.presentation.calendar

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ipeksavas.employeeworkschedule.domain.model.Employee
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarViewModel: ViewModel(){
    private val _state = mutableStateOf(CalendarState())
    val state: State<CalendarState> = _state
    
    private val employeePool = mutableListOf(
        Employee(1, "İpek Savaş"),
        Employee(2, "Esen Savaş"),
        Employee(3, "Emircan Ünal")
    )
    
    fun loadEmployeeData(employeeId: Int){
        viewModelScope.launch{
            _state.value = _state.value.copy(isLoading = true)
            try{
                val foundEmployee = employeePool.find { it.id == employeeId } ?: Employee(employeeId, "Unknown")
                val mockPermission = when(employeeId){
                    1-> listOf(LocalDate.now().plusDays(1),LocalDate.now().plusDays(2))
                    2-> listOf(LocalDate.now().plusDays(5))
                    else-> emptyList()
                }
                
                _state.value = _state.value.copy(
                    isLoading = false,
                    employee = foundEmployee.copy(remainingPermissionQuota = foundEmployee.remainingPermissionQuota - mockPermission.size),
                    selectedDates = mockPermission
                )
            }catch(e: Exception){
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Veriler yüklenirken hata oluştu"
                )
            }
        }
    }
    
    fun onDateSelected(date: LocalDate){
        val currentEmployee = _state.value.employee ?: return
        val currentSelectedDates = _state.value.selectedDates.toMutableList()
        
        if(currentSelectedDates.contains(date)){
            currentSelectedDates.remove(date)
            val updatedEmployee = currentEmployee.copy(remainingPermissionQuota = currentEmployee.remainingPermissionQuota + 1)
            _state.value = _state.value.copy(
                employee=updatedEmployee,
                selectedDates = currentSelectedDates,
                error = null
            )
        }else{
            if(currentEmployee.remainingPermissionQuota <= 0){
                _state.value = _state.value.copy(
                    error = "Çalışanın izin hakkı kalmamıştır."
                )
                return
            }
            currentSelectedDates.add(date)
            val updatedEmployee = currentEmployee.copy(remainingPermissionQuota = currentEmployee.remainingPermissionQuota - 1)
            _state.value =_state.value.copy(
                employee = updatedEmployee,
                selectedDates = currentSelectedDates,
                error =null
            )
        }
    }
    
    fun nextMonth(){
        _state.value = _state.value.copy(currentMonth = _state.value.currentMonth.plusMonths(1))
    }
    fun previousMonth(){
        _state.value = _state.value.copy(currentMonth = _state.value.currentMonth.minusMonths(1))
    }
}