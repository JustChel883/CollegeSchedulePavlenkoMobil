package com.example.collegeschedulepavlenkomobil.ui.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.collegeschedulepavlenkomobil.data.dto.GroupDto
import com.example.collegeschedulepavlenkomobil.ui.schedule.ScheduleViewModel

@Composable
fun FavoritesScreen(
    viewModel: ScheduleViewModel,
    onNavigateToHome: () -> Unit
) {
    val groups by viewModel.groups.collectAsState()
    val favoriteGroupNames by viewModel.favoriteGroupNames.collectAsState()


    val favoriteGroups = groups.filter { it.name in favoriteGroupNames }

    if (favoriteGroups.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Нет избранных групп",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            items(favoriteGroups) { group ->
                FavoriteGroupItem(
                    group = group,
                    onClick = {
                        viewModel.selectGroup(group)
                        onNavigateToHome()
                    }
                )
            }
        }
    }
}

@Composable
fun FavoriteGroupItem(
    group: GroupDto,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = group.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Курс: ${group.course}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}