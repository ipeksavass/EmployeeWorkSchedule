package com.ipeksavas.employeeworkschedule.domain.usecase

import com.ipeksavas.employeeworkschedule.domain.model.Shift
import com.ipeksavas.employeeworkschedule.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth

class GetShiftsUseCase(private val repository: EmployeeRepository) {
    operator fun invoke(targetMonth: YearMonth): Flow<List<Shift>> {
        return repository.getShiftsForMonth(targetMonth.atDay(1), targetMonth.atEndOfMonth())
    }
}