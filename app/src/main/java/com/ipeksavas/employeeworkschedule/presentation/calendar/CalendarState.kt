package com.ipeksavas.employeeworkschedule.presentation.calendar

import com.ipeksavas.employeeworkschedule.domain.model.Employee
import java.time.LocalDate
import java.time.YearMonth

data class CalendarState(
    val isLoading: Boolean = false,
    val employee: Employee? = null,
    val selectedDates: List<LocalDate> = emptyList(),
    val currentMonth: YearMonth = YearMonth.now(),
    val error: String? = null
)