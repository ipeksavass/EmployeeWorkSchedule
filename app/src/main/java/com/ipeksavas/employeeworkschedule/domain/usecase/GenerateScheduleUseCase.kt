package com.ipeksavas.employeeworkschedule.domain.usecase

import com.ipeksavas.employeeworkschedule.domain.model.Shift
import com.ipeksavas.employeeworkschedule.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.first
import java.time.YearMonth
class GenerateScheduleUseCase(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke(targetMonth: YearMonth) {
        val startDate = targetMonth.atDay(1)
        val endDate = targetMonth.atEndOfMonth()
        
        val allEmployees = repository.getAllEmployees().first()
        val allPermissions = repository.getAllPermissions()
        
        repository.deleteShiftsForMonth(startDate, endDate)
        
        val generatedShifts = mutableListOf<Shift>()
        val monthlyShiftCounts = allEmployees.associate { it.id to 0 }.toMutableMap()
        val weekendCounts = allEmployees.associate { it.id to it.weekendShiftCount }.toMutableMap()
        
        var currentDate = startDate
        while (!currentDate.isAfter(endDate)) {
            
            val isWeekend = currentDate.dayOfWeek.value == 6 || currentDate.dayOfWeek.value == 7
            
            val permissionsForToday = allPermissions.filter { it.date == currentDate }.map { it.employeeId }
            
            val recentlyWorkedEmployeeIds = generatedShifts
                .filter { it.date == currentDate.minusDays(1) || it.date == currentDate.minusDays(2) }
                .map { it.employeeId }
            
            val availableCandidates = allEmployees.filter { employee ->
                !permissionsForToday.contains(employee.id) && !recentlyWorkedEmployeeIds.contains(employee.id)
            }
            
            if (availableCandidates.isNotEmpty()) {
                val chosenEmployee = if (isWeekend) {
                    availableCandidates.minByOrNull { weekendCounts[it.id] ?: 0 }!!
                } else {
                    availableCandidates.minByOrNull { monthlyShiftCounts[it.id] ?: 0 }!!
                }
                
                generatedShifts.add(
                    Shift(
                        employeeId = chosenEmployee.id,
                        employeeName = chosenEmployee.name,
                        date = currentDate
                    )
                )
                
                monthlyShiftCounts[chosenEmployee.id] = (monthlyShiftCounts[chosenEmployee.id] ?: 0) + 1
                
                if (isWeekend) {
                    weekendCounts[chosenEmployee.id] = (weekendCounts[chosenEmployee.id] ?: 0) + 1
                    repository.incrementWeekendShiftCount(chosenEmployee.id)
                }
            }
            currentDate = currentDate.plusDays(1)
        }
        repository.insertShifts(generatedShifts)
    }
}