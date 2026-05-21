package com.ipeksavas.employeeworkschedule.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee_table")
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val remainingPermissionQuota: Int = 30,
    val weekendShiftCount: Int = 0
)
