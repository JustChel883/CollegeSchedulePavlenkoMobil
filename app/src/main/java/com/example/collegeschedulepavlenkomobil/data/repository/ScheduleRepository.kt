package com.example.collegeschedulepavlenkomobil.data.repository

import com.example.collegeschedulepavlenkomobil.data.api.ScheduleApi
import com.example.collegeschedulepavlenkomobil.data.dto.ScheduleByDateDto
import com.example.collegeschedulepavlenkomobil.data.dto.GroupDto


class ScheduleRepository(private val api: ScheduleApi) {
    suspend fun loadSchedule(group: String): List<ScheduleByDateDto> {
        return api.getSchedule(
            groupName = group,
            start = "2026-01-12",
            end = "2026-01-17"
        )
    }

    suspend fun loadGroups(): List<GroupDto> = api.getGroups()
}

