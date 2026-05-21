package com.ipeksavas.employeeworkschedule.domain.usecase

import com.ipeksavas.employeeworkschedule.domain.model.Employee
import com.ipeksavas.employeeworkschedule.domain.repository.EmployeeRepository

class AddEmployeeUseCase(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke(employee: Employee) {
        repository.insertEmployee(employee)
    }
}