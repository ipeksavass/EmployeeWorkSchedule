package com.ipeksavas.employeeworkschedule.domain.usecase

import com.ipeksavas.employeeworkschedule.domain.model.Permission
import com.ipeksavas.employeeworkschedule.domain.repository.EmployeeRepository

class SavePermissionsUseCase(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke(employeeId: Int, permissions: List<Permission>) {
        repository.savePermissions(employeeId, permissions)
        
        val newQuota = 30 - permissions.size
        repository.updateEmployeeQuota(employeeId, newQuota)
    }
}