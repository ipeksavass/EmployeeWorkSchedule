package com.ipeksavas.employeeworkschedule.domain.repository

import com.ipeksavas.employeeworkschedule.domain.model.Employee
import com.ipeksavas.employeeworkschedule.domain.model.Permission
import com.ipeksavas.employeeworkschedule.domain.model.Shift
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface EmployeeRepository {
    fun getAllEmployees(): Flow<List<Employee>>
    suspend fun insertEmployee(employee: Employee)
    suspend fun deleteEmployee(employee: Employee)
    fun getPermissionsForEmployee(employeeId: Int): Flow<List<Permission>>
    suspend fun insertPermission(permission: Permission)
    suspend fun deletePermission(employeeId: Int, date: LocalDate)
    suspend fun savePermissions(employeeId: Int, permissions: List<Permission>)
    suspend fun updateEmployeeQuota(employeeId: Int, newQuota: Int)
    fun getShiftsForMonth(startDate: LocalDate, endDate: LocalDate): Flow<List<Shift>>
    suspend fun insertShifts(shifts: List<Shift>)
    suspend fun deleteShiftsForMonth(startDate: LocalDate, endDate: LocalDate)
    suspend fun getAllPermissions(): List<Permission>
    suspend fun incrementWeekendShiftCount(employeeId: Int)
}