package com.ipeksavas.employeeworkschedule.domain.usecase

import com.ipeksavas.employeeworkschedule.domain.model.Permission
import com.ipeksavas.employeeworkschedule.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow

class GetPermissionsUseCase(
    private val repository: EmployeeRepository
) {
    operator fun invoke(employeeId: Int): Flow<List<Permission>> {
        return repository.getPermissionsForEmployee(employeeId)
    }
}