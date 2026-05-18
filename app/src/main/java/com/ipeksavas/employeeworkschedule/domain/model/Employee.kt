package com.ipeksavas.employeeworkschedule.domain.model

data class Employee(
    val id: Int = 0,
    val name: String,
    val remainingPermissionQuota: Int = 30,
    val weekendShiftCount: Int = 0
)
