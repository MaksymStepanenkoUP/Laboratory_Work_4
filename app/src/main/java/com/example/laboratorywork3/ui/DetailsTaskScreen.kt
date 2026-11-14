package com.example.laboratorywork3.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laboratorywork3.viewmodel.TasksViewModel

@Composable
fun DetailsTaskScreen(
    taskId: Int,
    vm: TasksViewModel,
    onEdit: () -> Unit,
    onBack: () -> Unit
) {
    val tasks by vm.tasks.collectAsState()
    val task = tasks.find { it.id == taskId }

    Scaffold(
        containerColor = ScreenBg,
        topBar = { AppTopBar("Деталі завдання") }
    ) { padding ->
        if (task == null) {
            Box(Modifier.fillMaxSize().padding(padding))
        } else {
            Column(
                modifier = Modifier.padding(padding).padding(12.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                WhiteCard {
                    Text(task.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, textAlign = TextAlign.Center)
                    Text(
                        text = "Статус: " + if (task.isDone) "Виконано" else "В процесі",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Spacer(Modifier.height(6.dp))
                    PriorityBadge(task.priority)
                }

                WhiteCard(stripe = AppBlue) {
                    Text("Опис", fontSize = 11.sp, color = Color.Gray)
                    Text(
                        text = if (task.description.isBlank()) "Без опису" else task.description,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }

                WhiteCard {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = task.isDone, onCheckedChange = { vm.toggleDone(task.id) })
                        Text("Позначити як виконане")
                    }
                }

                GrayButton(text = "Редагувати", onClick = onEdit)
                GrayButton(
                    text = "Видалити",
                    onClick = {
                        vm.deleteTask(task.id)
                        onBack()
                    }
                )
                GrayButton(text = "Назад", onClick = onBack)
            }
        }
    }
}