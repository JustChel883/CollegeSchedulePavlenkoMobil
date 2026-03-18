package com.example.collegeschedulepavlenkomobil.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collegeschedulepavlenkomobil.data.dto.GroupDto
import com.example.collegeschedulepavlenkomobil.data.repository.ScheduleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ScheduleViewModel(
    private val repository: ScheduleRepository
) : ViewModel() {

    private val _groups = MutableStateFlow<List<GroupDto>>(emptyList())
    val groups: StateFlow<List<GroupDto>> = _groups

    private val _selectedGroup = MutableStateFlow<GroupDto?>(null)
    val selectedGroup: StateFlow<GroupDto?> = _selectedGroup

    private val _isLoadingGroups = MutableStateFlow(false)
    val isLoadingGroups: StateFlow<Boolean> = _isLoadingGroups

    init {
        loadGroups()
    }

    fun loadGroups() {
        viewModelScope.launch {
            _isLoadingGroups.value = true
            try {
                _groups.value = repository.loadGroups()

                if (_groups.value.isNotEmpty() && _selectedGroup.value == null) {
                    _selectedGroup.value = _groups.value.first()
                }
            } catch (e: Exception) {

            } finally {
                _isLoadingGroups.value = false
            }
        }
    }

    fun selectGroup(group: GroupDto) {
        _selectedGroup.value = group

    }
}