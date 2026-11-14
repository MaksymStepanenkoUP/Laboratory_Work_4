package com.example.laboratorywork3.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laboratorywork3.viewmodel.TasksViewModel

@Composable
fun TasksListScreen(
    vm: TasksViewModel,
    onAddClick: () -> Unit,
    onTaskClick: (Int) -> Unit
) {
    val tasks by vm.tasks.collectAsState()

    Scaffold(
        containerColor = ScreenBg,
        topBar = { AppTopBar("Мої завдання") },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                shape = RoundedCornerShape(8.dp),
                containerColor = Color(0xFFEEEEEE),
                contentColor = Color.Black
            ) {
                Text("+", fontSize = 24.sp)
            }
        }
    ) { padding ->
        if (tasks.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Завдань поки немає", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(tasks, key = { it.id }) { task ->
                    WhiteCard(
                        stripe = stripeColor(task.priority),
                        onClick = { onTaskClick(task.id) }
                    ) {
                        Text(
                            text = task.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            textDecoration = if (task.isDone) TextDecoration.LineThrough else null
                        )
                        if (task.description.isNotBlank()) {
                            Text(
                                text = task.description,
                                fontSize = 12.sp,
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Spacer(Modifier.height(6.dp))
                        PriorityBadge(task.priority)
                    }
                }
            }
        }
    }
}