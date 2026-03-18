package com.example.collegeschedulepavlenkomobil.ui.schedule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.collegeschedulepavlenkomobil.data.dto.LessonDto
import com.example.collegeschedulepavlenkomobil.data.dto.ScheduleByDateDto
import com.example.collegeschedulepavlenkomobil.data.dto.LessonGroupPart
import com.example.collegeschedulepavlenkomobil.utils.formatHumanReadableDate
import androidx.compose.foundation.layout.height

@Composable
fun ScheduleList(data: List<ScheduleByDateDto>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(data) { day ->

            Text(
                text = formatHumanReadableDate(day.lessonDate),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(16.dp, 12.dp, 16.dp, 0.dp)
            )

            Text(
                text = day.weekday,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 8.dp)
            )

            if (day.lessons.isEmpty()) {
                Text(
                    text = "Занятий нет",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 12.dp)
                )
            } else {
                day.lessons.forEach { lesson ->
                    LessonCard(lesson)
                }
            }
        }
    }
}

@Composable
fun LessonCard(lesson: LessonDto) {
    val buildingColor = getBuildingColor(lesson.building)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 4.dp, 16.dp, 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = buildingColor.copy(alpha = 0.2f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = getLessonIcon(lesson),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Пара ${lesson.lessonNumber}",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = lesson.time ?: "??",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Normal
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Детали занятия
            if (lesson.groupParts?.size == 1 && lesson.groupParts.containsKey(LessonGroupPart.FULL)) {
                val info = lesson.groupParts[LessonGroupPart.FULL]
                if (info != null) {
                    LessonDetailRow(
                        partName = "Все",
                        subject = info.subject,
                        teacher = info.teacher,
                        classroom = info.classroom,
                        building = info.building
                    )
                }
            } else {
                lesson.groupParts?.forEach { (part, info) ->
                    if (info != null) {
                        LessonDetailRow(
                            partName = part.name,
                            subject = info.subject,
                            teacher = info.teacher,
                            classroom = info.classroom,
                            building = info.building
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LessonDetailRow(
    partName: String,
    subject: String?,
    teacher: String?,
    classroom: String?,
    building: String?
) {
    Column(
        modifier = Modifier.padding(8.dp, 4.dp, 0.dp, 0.dp)
    ) {
        Text(
            text = if (partName != "Все") "[$partName] ${subject ?: "Нет данных"}" else subject ?: "Нет данных",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        )
        Text(
            text = teacher ?: "Преподаватель не указан",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "${building ?: "Корпус"} • ауд. ${classroom ?: "??"}",
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

fun getLessonIcon(lesson: LessonDto): ImageVector {
    return if ((lesson.groupParts?.size ?: 0) > 1) {
        Icons.Default.People
    } else {
        Icons.Default.Book
    }
}

fun getBuildingColor(buildingName: String?): Color {
    return when (buildingName) {
        "Главный корпус" -> Color(0xFFBBDEFB)
        "Лабораторный корпус" -> Color(0xFFC8E6C9)
        "Учебный корпус №1" -> Color(0xFFFFE0B2)
        "Спортивный комплекс" -> Color(0xFFE1BEE7)
        else -> Color(0xFFE0E0E0)
    }
}