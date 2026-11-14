package com.example.laboratorywork3.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.laboratorywork3.ui.AddTaskScreen
import com.example.laboratorywork3.ui.DetailsTaskScreen
import com.example.laboratorywork3.ui.TasksListScreen
import com.example.laboratorywork3.viewmodel.TasksViewModel

object Routes {
    const val LIST = "list"
    const val ADD = "add?taskId={taskId}"
    const val DETAILS = "details/{taskId}"

    fun add(taskId: Int = -1) = if (taskId < 0) "add" else "add?taskId=$taskId"
    fun details(taskId: Int) = "details/$taskId"
}

@Composable
fun AppNavHost(vm: TasksViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LIST) {

        composable(Routes.LIST) {
            TasksListScreen(
                vm = vm,
                onAddClick = { navController.navigate(Routes.add()) },
                onTaskClick = { id -> navController.navigate(Routes.details(id)) }
            )
        }

        composable(
            route = Routes.ADD,
            arguments = listOf(navArgument("taskId") {
                type = NavType.IntType
                defaultValue = -1
            })
        ) { entry ->
            val taskId = entry.arguments?.getInt("taskId") ?: -1
            AddTaskScreen(
                taskId = taskId,
                vm = vm,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.DETAILS,
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { entry ->
            val taskId = entry.arguments?.getInt("taskId") ?: -1
            DetailsTaskScreen(
                taskId = taskId,
                vm = vm,
                onEdit = { navController.navigate(Routes.add(taskId)) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
