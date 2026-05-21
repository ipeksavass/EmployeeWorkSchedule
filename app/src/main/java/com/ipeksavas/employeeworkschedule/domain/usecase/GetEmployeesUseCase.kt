package com.ipeksavas.employeeworkschedule.domain.usecase

import com.ipeksavas.employeeworkschedule.domain.model.Employee
import com.ipeksavas.employeeworkschedule.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow

class GetEmployeesUseCase(
    private val repository: EmployeeRepository
) {
    operator fun invoke(): Flow<List<Employee>> {
        return repository.getAllEmployees()
    }
}