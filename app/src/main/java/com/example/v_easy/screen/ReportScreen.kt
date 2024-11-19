package com.example.v_easy.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ReportScreen(navController: NavHostController? = null) {
    var selectedTab by remember { mutableStateOf(Tab.Expense) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Financial Report",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Tab Selector
        TabSelector(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        // Circular Progress with Amount
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            when (selectedTab) {
                Tab.Expense -> CircularProgress(
                    totalAmount = 332f,
                    items = listOf(
                        ExpenseItem("Shopping", 120f, Color(0xFFFFA500)),
                        ExpenseItem("Subscription", 80f, Color(0xFF9370DB)),
                        ExpenseItem("Food", 32f, Color(0xFFFF6347))
                    )
                )
                Tab.Income -> CircularProgress(
                    totalAmount = 6000f,
                    items = listOf(
                        ExpenseItem("Salary", 5000f, Color(0xFF00A86B)),
                        ExpenseItem("Passive Income", 1000f, Color.Black)
                    )
                )
            }
        }

        // Category List
        Text(
            text = "Category",
            modifier = Modifier.padding(vertical = 8.dp),
            color = Color.Gray
        )

        when (selectedTab) {
            Tab.Expense -> ExpenseList(
                items = listOf(
                    ExpenseItem("Shopping", 120f, Color(0xFFFFA500)),
                    ExpenseItem("Subscription", 80f, Color(0xFF9370DB)),
                    ExpenseItem("Food", 32f, Color(0xFFFF6347))
                ),
                isExpense = true
            )
            Tab.Income -> ExpenseList(
                items = listOf(
                    ExpenseItem("Salary", 5000f, Color(0xFF00A86B)),
                    ExpenseItem("Passive Income", 1000f, Color.Black)
                ),
                isExpense = false
            )
        }
    }
}

@Composable
private fun TabSelector(
    selectedTab: Tab,
    onTabSelected: (Tab) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFF5F5F5))
            .padding(4.dp)
    ) {
        Tab.values().forEach { tab ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (selectedTab == tab) Color(0xFF9370DB) else Color.Transparent)
                    .noRippleClickable { onTabSelected(tab) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tab.name,
                    color = if (selectedTab == tab) Color.White else Color.Gray
                )
            }
        }
    }
}

@Composable
private fun CircularProgress(
    totalAmount: Float,
    items: List<ExpenseItem>
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val radius = size.width.coerceAtMost(size.height) / 3
            val strokeWidth = 30f

            // Background circle
            drawCircle(
                color = Color(0xFFF5F5F5),
                radius = radius,
                style = Stroke(width = strokeWidth)
            )

            var startAngle = -90f
            items.forEach { item ->
                val sweepAngle = (item.amount / totalAmount) * 360f

                drawArc(
                    color = item.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                    size = Size(radius * 2, radius * 2),
                    topLeft = Offset(centerX - radius, centerY - radius)
                )
                startAngle += sweepAngle
            }
        }

        Text(
            text = "Ksh $totalAmount",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ExpenseList(
    items: List<ExpenseItem>,
    isExpense: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(item.color)
                    )
                    Text(text = item.name)
                }
                Text(
                    text = "${if (isExpense) "-" else "+"} Ksh ${item.amount}",
                    color = if (isExpense) Color(0xFFFF4444) else Color(0xFF00A86B)
                )
            }
        }
    }
}

enum class Tab {
    Expense, Income
}

data class ExpenseItem(
    val name: String,
    val amount: Float,
    val color: Color
)

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}