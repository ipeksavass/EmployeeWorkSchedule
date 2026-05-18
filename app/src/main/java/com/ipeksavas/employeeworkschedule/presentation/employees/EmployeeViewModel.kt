package com.ipeksavas.employeeworkschedule.presentation.employees

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ipeksavas.employeeworkschedule.domain.model.Employee

class EmployeeViewModel : ViewModel() {
    private val _state = mutableStateOf(EmployeeListState())
    val state: State<EmployeeListState> = _state
    private val allEmployees = mutableListOf(
        Employee(1, "İpek Savaş"),
        Employee(2, "Esen Savas"),
        Employee(3, "Emircan Unal")
    )

    init {
        updateState()
    }

    private fun updateState() {
        _state.value = _state.value.copy(
            employees = allEmployees.toList(),
            isLoading = false,
            error = null
        )
    }

    fun addEmployee(name: String) {
        if (name.isBlank()) return

        val newId = (allEmployees.maxByOrNull { it.id }?.id ?: 0) + 1
        val newEmployee = Employee(id = newId, name = name)
        
        allEmployees.add(newEmployee)
        
        updateState()
    }
}
