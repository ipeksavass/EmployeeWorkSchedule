package com.ipeksavas.employeeworkschedule.presentation.employees

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ipeksavas.employeeworkschedule.presentation.employees.component.EmployeeItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeScreen(
    viewModel : EmployeeViewModel,
    onNavigateToCalendar: (Int) -> Unit
){
    val state = viewModel.state.value
    var isDialogOpen by remember { mutableStateOf(false) }
    var newEmployeeName by remember { mutableStateOf("") }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    text = "Personel Listesi",
                    fontWeight= FontWeight.Bold)},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isDialogOpen = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Personel Ekle")
            }
        }
    ){
        paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ){
            if(state.isLoading){//veriler yuklenirken
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center ))
            }
            state.error?.let{ errorMessage ->
                Text( text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center).padding(16.dp)
                )
            }
            if(!state.isLoading && state.error == null){//veriler yuklendi hata yok
                if(state.employees.isEmpty()){
                    Text(
                        text = "Personel bulunamadı",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    ) {
                        items(state.employees){ employee ->
                            EmployeeItem(
                                employee = employee,
                                onItemClick = { clickedEmployee ->
                                    onNavigateToCalendar(clickedEmployee.id)
                                })
                        }
                    }
                }
            }
            
            if(isDialogOpen){
                AlertDialog(
                    onDismissRequest = {
                        isDialogOpen = false
                        newEmployeeName = ""
                    },
                    title = { Text(text = "Yeni Personel Ekle") },
                    text = {
                        OutlinedTextField(
                            value = newEmployeeName,
                            onValueChange = { newEmployeeName = it },
                            label = { Text("Personel Adı Soyadı") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                if (newEmployeeName.isNotBlank()) {
                                    viewModel.addEmployee(newEmployeeName)
                                    isDialogOpen = false
                                    newEmployeeName = ""
                                }
                            }
                        ) {
                            Text("Ekle")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            isDialogOpen = false
                            newEmployeeName = ""
                        }) {
                            Text("İptal")
                        }
                    }
                )
            }
        }
    }
}