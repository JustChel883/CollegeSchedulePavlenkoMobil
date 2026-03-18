package com.example.collegeschedulepavlenkomobil.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.collegeschedulepavlenkomobil.data.local.FavoritesManager
import com.example.collegeschedulepavlenkomobil.data.repository.ScheduleRepository

class ScheduleViewModelFactory(
    private val repository: ScheduleRepository,
    private val favoritesManager: FavoritesManager
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScheduleViewModel(repository, favoritesManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}