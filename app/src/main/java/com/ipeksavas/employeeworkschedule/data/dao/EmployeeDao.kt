package com.ipeksavas.employeeworkschedule.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ipeksavas.employeeworkschedule.domain.model.Employee
import com.ipeksavas.employeeworkschedule.domain.model.Permission
import com.ipeksavas.employeeworkschedule.domain.model.Shift
import kotlinx.coroutines.flow.Flow
@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employee_table")
    fun getAllEmployees(): Flow<List<Employee>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: Employee)
    
    @Delete
    suspend fun deleteEmployee(employee: Employee)
    
    @Query("SELECT * FROM permission_table WHERE employeeId = :employeeId")
    fun getPermissionsForEmployee(employeeId: Int): Flow<List<Permission>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPermission(permission: Permission)
    
    @Query("DELETE FROM permission_table WHERE employeeId = :employeeId AND date = :date")
    suspend fun deletePermission(employeeId: Int, date: String)
    
    @Query("DELETE FROM permission_table WHERE employeeId = :employeeId")
    suspend fun deleteAllPermissionsForEmployee(employeeId: Int)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPermissions(permissions: List<Permission>)
    
    @Query("UPDATE employee_table SET remainingPermissionQuota = :newQuota WHERE id = :employeeId")
    suspend fun updateEmployeeQuota(employeeId: Int, newQuota: Int)
    
    @Query("SELECT * FROM shift_table WHERE date >= :startDate AND date <= :endDate")
    fun getShiftsForMonth(startDate: String, endDate: String): Flow<List<Shift>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShifts(shifts: List<Shift>)
    
    @Query("DELETE FROM shift_table WHERE date >= :startDate AND date <= :endDate")
    suspend fun deleteShiftsForMonth(startDate: String, endDate: String)
    
    @Query("SELECT * FROM permission_table")
    suspend fun getAllPermissions(): List<Permission>
    
    @Query("UPDATE employee_table SET weekendShiftCount = weekendShiftCount + 1 WHERE id = :employeeId")
    suspend fun incrementWeekendShiftCount(employeeId: Int)
}