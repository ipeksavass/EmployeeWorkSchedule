package com.ipeksavas.employeeworkschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ipeksavas.employeeworkschedule.presentation.calendar.CalendarScreen
import com.ipeksavas.employeeworkschedule.presentation.calendar.CalendarViewModel
import com.ipeksavas.employeeworkschedule.presentation.employees.EmployeeScreen
import com.ipeksavas.employeeworkschedule.presentation.employees.EmployeeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    
                    val employeeViewModel: EmployeeViewModel = viewModel()
                    val calendarViewModel: CalendarViewModel = viewModel()
                    
                    NavHost(
                        navController = navController,
                        startDestination = "employee_list"
                    ) {
                        composable(route = "employee_list") {
                            EmployeeScreen(
                                viewModel = employeeViewModel,
                                onNavigateToCalendar = { employeeId ->
                                    navController.navigate("calendar_screen/$employeeId")
                                }
                            )
                        }
                        
                        composable(
                            route = "calendar_screen/{employeeId}",
                            arguments = listOf(
                                navArgument("employeeId") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            val employeeId = backStackEntry.arguments?.getInt("employeeId") ?: 0
                            
                            CalendarScreen(
                                employeeId = employeeId,
                                viewModel = calendarViewModel,
                                onNavigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}