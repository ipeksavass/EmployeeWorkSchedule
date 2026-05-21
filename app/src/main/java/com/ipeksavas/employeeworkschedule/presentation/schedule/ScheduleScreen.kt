package com.ipeksavas.employeeworkschedule.presentation.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

@Composable
fun ScheduleScreen(viewModel: ScheduleViewModel) {
    val state = viewModel.state.value
    val daysInMonth = remember(state.currentMonth) { state.currentMonth.lengthOfMonth() }
    val firstDayOfMonth = remember(state.currentMonth) { state.currentMonth.atDay(1).dayOfWeek.value }
    val blankSpaces = remember(firstDayOfMonth) { firstDayOfMonth - 1 }
    
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Aylık Çalışma Planı", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.previousMonth() }) { Icon(Icons.Default.KeyboardArrowLeft, "Önceki Ay") }
            Text(
                text = "${state.currentMonth.month.getDisplayName(TextStyle.FULL, Locale("tr"))} ${state.currentMonth.year}",
                style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Medium
            )
            IconButton(onClick = { viewModel.nextMonth() }) { Icon(Icons.Default.KeyboardArrowRight, "Sonraki Ay") }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(modifier = Modifier.fillMaxWidth()) {
            listOf("Pzt", "Sal", "Çar", "Per", "Cum", "Cmt", "Paz").forEach {
                Text(text = it, modifier = Modifier.weight(1f), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth()) {
                items(blankSpaces) { Spacer(modifier = Modifier.aspectRatio(1f)) }
                
                items(daysInMonth) { dayIndex ->
                    val day = dayIndex + 1
                    val date = state.currentMonth.atDay(day)
                    
                    val shiftForDay = state.shifts.find { it.date == date }
                    
                    Card(
                        modifier = Modifier.aspectRatio(0.8f).padding(2.dp).border(1.dp, Color.LightGray, RoundedCornerShape(4.dp)),
                        colors = CardDefaults.cardColors(
                            containerColor = if (date == LocalDate.now()) MaterialTheme.colorScheme.primaryContainer else Color.White
                        )
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(2.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = day.toString(), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            
                            if (shiftForDay != null) {
                                Box(
                                    modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primary, RoundedCornerShape(2.dp)).padding(2.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = shiftForDay.employeeName,
                                        color = Color.White,
                                        fontSize = 9.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            } else {
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                }
        }
    }
}