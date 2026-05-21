package com.ipeksavas.employeeworkschedule.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "shift_table")
data class Shift(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val employeeId: Int,
    val employeeName: String,
    val date: LocalDate
)
