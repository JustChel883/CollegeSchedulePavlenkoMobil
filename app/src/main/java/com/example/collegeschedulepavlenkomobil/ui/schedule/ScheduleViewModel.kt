package com.example.collegeschedulepavlenkomobil.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collegeschedulepavlenkomobil.data.dto.GroupDto
import com.example.collegeschedulepavlenkomobil.data.dto.ScheduleByDateDto
import com.example.collegeschedulepavlenkomobil.data.repository.ScheduleRepository
import com.example.collegeschedulepavlenkomobil.utils.getWeekDateRange
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ScheduleViewModel(
    private val repository: ScheduleRepository
) : ViewModel() {

    private val _groups = MutableStateFlow<List<GroupDto>>(emptyList())
    val groups: StateFlow<List<GroupDto>> = _groups.asStateFlow()

    private val _selectedGroup = MutableStateFlow<GroupDto?>(null)
    val selectedGroup: StateFlow<GroupDto?> = _selectedGroup.asStateFlow()


    private val _schedule = MutableStateFlow<List<ScheduleByDateDto>>(emptyList())
    val schedule: StateFlow<List<ScheduleByDateDto>> = _schedule.asStateFlow()

    private val _isLoadingGroups = MutableStateFlow(false)
    val isLoadingGroups: StateFlow<Boolean> = _isLoadingGroups.asStateFlow()

    private val _isLoadingSchedule = MutableStateFlow(false)
    val isLoadingSchedule: StateFlow<Boolean> = _isLoadingSchedule.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadGroups()
    }

    fun loadGroups() {
        viewModelScope.launch {
            _isLoadingGroups.value = true
            _error.value = null
            try {
                val loadedGroups = repository.loadGroups()
                _groups.value = loadedGroups
                if (loadedGroups.isNotEmpty() && _selectedGroup.value == null) {

                    selectGroup(loadedGroups.first())
                }
            } catch (e: Exception) {
                _error.value = "Ошибка загрузки групп: ${e.message}"
            } finally {
                _isLoadingGroups.value = false
            }
        }
    }

    fun selectGroup(group: GroupDto) {
        _selectedGroup.value = group
        loadScheduleForGroup(group.name)
    }

    private fun loadScheduleForGroup(groupName: String) {
        viewModelScope.launch {
            _isLoadingSchedule.value = true
            _error.value = null
            try {
                val (start, end) = getWeekDateRange()
                val loadedSchedule = repository.loadSchedule(groupName, start, end)
                _schedule.value = loadedSchedule
            } catch (e: Exception) {
                _error.value = "Ошибка загрузки расписания: ${e.message}"
            } finally {
                _isLoadingSchedule.value = false
            }
        }
    }
}