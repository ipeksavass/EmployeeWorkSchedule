package com.ipeksavas.employeeworkschedule.data.repository

import com.ipeksavas.employeeworkschedule.data.dao.EmployeeDao
import com.ipeksavas.employeeworkschedule.domain.model.Employee
import com.ipeksavas.employeeworkschedule.domain.model.Permission
import com.ipeksavas.employeeworkschedule.domain.model.Shift
import com.ipeksavas.employeeworkschedule.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class EmployeeRepositoryImpl(
    private val dao: EmployeeDao
) : EmployeeRepository {
    
    override fun getAllEmployees(): Flow<List<Employee>> {
        return dao.getAllEmployees()
    }
    
    override suspend fun insertEmployee(employee: Employee) {
        dao.insertEmployee(employee)
    }
    
    override suspend fun deleteEmployee(employee: Employee) {
        dao.deleteEmployee(employee)
    }
    
    override fun getPermissionsForEmployee(employeeId: Int): Flow<List<Permission>> {
        return dao.getPermissionsForEmployee(employeeId)
    }
    
    override suspend fun insertPermission(permission: Permission) {
        dao.insertPermission(permission)
    }
    
    override suspend fun deletePermission(employeeId: Int, date: LocalDate) {
        val dateString = date.toString()
        dao.deletePermission(employeeId, dateString)
    }
    
    override suspend fun savePermissions(employeeId: Int, permissions: List<Permission>) {
        dao.deleteAllPermissionsForEmployee(employeeId)
        dao.insertPermissions(permissions)
    }
    
    override suspend fun updateEmployeeQuota(employeeId: Int, newQuota: Int) {
        dao.updateEmployeeQuota(employeeId, newQuota)
    }
    
    override fun getShiftsForMonth(startDate: LocalDate, endDate: LocalDate): Flow<List<Shift>> {
        return dao.getShiftsForMonth(startDate.toString(), endDate.toString())
    }
    
    override suspend fun insertShifts(shifts: List<Shift>) {
        dao.insertShifts(shifts)
    }
    
    override suspend fun deleteShiftsForMonth(startDate: LocalDate, endDate: LocalDate) {
        dao.deleteShiftsForMonth(startDate.toString(), endDate.toString())
    }
    
    override suspend fun getAllPermissions(): List<Permission> {
        return dao.getAllPermissions()
    }
    
    override suspend fun incrementWeekendShiftCount(employeeId: Int) {
        dao.incrementWeekendShiftCount(employeeId)
    }
}