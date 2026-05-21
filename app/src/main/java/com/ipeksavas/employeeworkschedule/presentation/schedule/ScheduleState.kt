package com.ipeksavas.employeeworkschedule.presentation.schedule

import com.ipeksavas.employeeworkschedule.domain.model.Shift
import java.time.YearMonth

data class ScheduleState(
    val currentMonth: YearMonth = YearMonth.now(),
    val shifts: List<Shift> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)