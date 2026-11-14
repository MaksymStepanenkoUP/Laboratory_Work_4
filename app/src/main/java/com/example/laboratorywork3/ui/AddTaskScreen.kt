package com.example.laboratorywork3.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.laboratorywork3.model.Priority
import com.example.laboratorywork3.viewmodel.TasksViewModel

@Composable
fun AddTaskScreen(
    taskId: Int,
    vm: TasksViewModel,
    onBack: () -> Unit
) {
    val isEdit = taskId >= 0
    val existing = remember(taskId) { if (isEdit) vm.tasks.value.find { it.id == taskId } else null }

    var title by rememberSaveable(taskId) { mutableStateOf(existing?.title ?: "") }
    var description by rememberSaveable(taskId) { mutableStateOf(existing?.description ?: "") }
    var priority by rememberSaveable(taskId) { mutableStateOf(existing?.priority ?: Priority.HIGH) }
    var menuOpen by remember { mutableStateOf(false) }

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        unfocusedBorderColor = Color(0xFFE0E0E0)
    )

    Scaffold(
        containerColor = ScreenBg,
        topBar = { AppTopBar(if (isEdit) "Редагування завдання" else "Нове завдання") }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(12.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Назва завдання") },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = fieldColors,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text("Опис") },
                minLines = 3,
                shape = RoundedCornerShape(8.dp),
                colors = fieldColors,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                "Пріоритет",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Box {
                OutlinedButton(
                    onClick = { menuOpen = true },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(priority.label, modifier = Modifier.weight(1f))
                    Text("▾")
                }
                DropdownMenu(expanded = menuOpen, onDismissRequest = { menuOpen = false }) {
                    Priority.values().forEach { p ->
                        DropdownMenuItem(
                            text = { Text(p.label) },
                            onClick = { priority = p; menuOpen = false }
                        )
                    }
                }
            }

            GrayButton(
                text = if (isEdit) "Зберегти" else "Створити",
                enabled = title.isNotBlank(),
                onClick = {
                    if (isEdit) vm.updateTask(taskId, title, description, priority)
                    else vm.addTask(title, description, priority)
                    onBack()
                }
            )
            GrayButton(text = "Скасувати", onClick = onBack)
        }
    }
}