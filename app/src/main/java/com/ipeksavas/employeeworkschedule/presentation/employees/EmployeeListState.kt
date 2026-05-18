package com.ipeksavas.employeeworkschedule.presentation.employees

import com.ipeksavas.employeeworkschedule.domain.model.Employee

data class EmployeeListState(
    val isLoading:Boolean = false,
    val employees: List<Employee> = emptyList(),
    val error: String? = null
)
