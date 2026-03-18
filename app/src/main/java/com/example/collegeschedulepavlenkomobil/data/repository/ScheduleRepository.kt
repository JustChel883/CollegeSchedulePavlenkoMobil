package com.example.collegeschedulepavlenkomobil.data.repository

import com.example.collegeschedulepavlenkomobil.data.api.ScheduleApi
import com.example.collegeschedulepavlenkomobil.data.dto.ScheduleByDateDto
import com.example.collegeschedulepavlenkomobil.data.dto.GroupDto


class ScheduleRepository(private val api: ScheduleApi) {


    suspend fun loadSchedule(group: String): List<ScheduleByDateDto> {
        return loadSchedule(group, "2026-01-12", "2026-01-17")
    }


    suspend fun loadSchedule(group: String, start: String, end: String): List<ScheduleByDateDto> {
        return api.getSchedule(group, start, end)
    }

    suspend fun loadGroups(): List<GroupDto> = api.getGroups()
}