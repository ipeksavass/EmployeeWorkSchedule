package com.ipeksavas.employeeworkschedule.presentation.schedule

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ipeksavas.employeeworkschedule.domain.usecase.GenerateScheduleUseCase
import com.ipeksavas.employeeworkschedule.domain.usecase.GetShiftsUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import androidx.compose.runtime.State

class ScheduleViewModel(
    private val generateScheduleUseCase: GenerateScheduleUseCase,
    private val getShiftsUseCase: GetShiftsUseCase
) : ViewModel() {
    private val _state = mutableStateOf(ScheduleState())
    val state: State<ScheduleState> = _state
    
    init {
        loadSchedule()
    }
    fun loadSchedule() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            
            generateScheduleUseCase(_state.value.currentMonth)
            
            getShiftsUseCase(_state.value.currentMonth)
                .catch { e ->
                    _state.value = _state.value.copy(isLoading = false, error = e.localizedMessage)
                }
                .collect { shiftList ->
                    _state.value = _state.value.copy(isLoading = false, shifts = shiftList)
                }
        }
    }
    
    fun nextMonth() {
        _state.value = _state.value.copy(currentMonth = _state.value.currentMonth.plusMonths(1))
        loadSchedule()
    }
    
    fun previousMonth() {
        _state.value = _state.value.copy(currentMonth = _state.value.currentMonth.minusMonths(1))
        loadSchedule()
    }
}