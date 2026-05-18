package com.ipeksavas.employeeworkschedule.domain.model

import java.time.LocalDate

data class Shift(
    val id: Int = 0,
    val employeeId: Int,
    val date: LocalDate
)
