package com.example.collegeschedulepavlenkomobil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.example.collegeschedulepavlenkomobil.data.api.ScheduleApi
import com.example.collegeschedulepavlenkomobil.data.local.FavoritesManager
import com.example.collegeschedulepavlenkomobil.data.repository.ScheduleRepository
import com.example.collegeschedulepavlenkomobil.ui.schedule.ScheduleScreen
import com.example.collegeschedulepavlenkomobil.ui.schedule.ScheduleViewModelFactory
import com.example.collegeschedulepavlenkomobil.ui.theme.CollegeScheduleTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CollegeScheduleTheme {
                val context = LocalContext.current


                val retrofit = remember {
                    Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:5239/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
                val api = remember { retrofit.create(ScheduleApi::class.java) }
                val repository = remember { ScheduleRepository(api) }


                val favoritesManager = remember { FavoritesManager(context) }


                val viewModelFactory = remember { ScheduleViewModelFactory(repository, favoritesManager) }

                CollegeScheduleApp(viewModelFactory)
            }
        }
    }
}

@Composable
fun CollegeScheduleApp(
    viewModelFactory: ScheduleViewModelFactory
) {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            it.icon,
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            when (currentDestination) {
                AppDestinations.HOME -> ScheduleScreen(viewModelFactory)
                AppDestinations.FAVORITES -> Text(
                    text = "Избранные группы",
                    modifier = Modifier.padding(innerPadding)
                )
                AppDestinations.PROFILE -> Text(
                    text = "Профиль студента",
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    HOME("Home", Icons.Default.Home),
    FAVORITES("Favorites", Icons.Default.Favorite),
    PROFILE("Profile", Icons.Default.AccountBox),
}


@PreviewScreenSizes
@Composable
fun PreviewCollegeScheduleApp() {
    CollegeScheduleTheme {
        Text("Заглушка")
    }
}