package com.ipeksavas.employeeworkschedule.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ipeksavas.employeeworkschedule.data.dao.EmployeeDao
import com.ipeksavas.employeeworkschedule.domain.model.Employee
import com.ipeksavas.employeeworkschedule.domain.model.Permission
import com.ipeksavas.employeeworkschedule.domain.model.Shift

@Database(
    entities = [Employee::class, Permission::class, Shift::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val employeeDao: EmployeeDao
}