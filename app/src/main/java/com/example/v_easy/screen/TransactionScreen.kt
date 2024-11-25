import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

// Data class for Transaction
data class Transaction(
    val type: String,
    val description: String,
    val amount: Double,
    val time: String,
    val category: TransactionCategory
)

// Enum class for Transaction Categories
enum class TransactionCategory(
    val backgroundColor: Color,
    val iconTint: Color,
    val icon: @Composable () -> Unit
) {
    SHOPPING(
        backgroundColor = Color(0xFFFFF3E0),
        iconTint = Color(0xFFFF9800),
        icon = { Icon(Icons.Outlined.ShoppingBag, contentDescription = null) }
    ),
    SUBSCRIPTION(
        backgroundColor = Color(0xFFF3E5F5),
        iconTint = Color(0xFF9C27B0),
        icon = { Icon(Icons.Outlined.Subscriptions, contentDescription = null) }
    ),
    FOOD(
        backgroundColor = Color(0xFFFFEBEE),
        iconTint = Color(0xFFE91E63),
        icon = { Icon(Icons.Outlined.Restaurant, contentDescription = null) }
    ),
    TRANSPORTATION(
        backgroundColor = Color(0xFFE3F2FD),
        iconTint = Color(0xFF2196F3),
        icon = { Icon(Icons.Outlined.DirectionsCar, contentDescription = null) }
    ),
    SALARY(
        backgroundColor = Color(0xFFE8F5E9),
        iconTint = Color(0xFF4CAF50),
        icon = { Icon(Icons.Outlined.AccountBalance, contentDescription = null) }
    ),
    FREELANCE(
        backgroundColor = Color(0xFFF3E5F5),
        iconTint = Color(0xFF9C27B0),
        icon = { Icon(Icons.Outlined.Computer, contentDescription = null) }
    )
}

@Composable
fun TransactionScreen(navController: NavHostController? = null) {
    // Sample data
    val expenses = listOf(
        Transaction(
            type = "Shopping",
            description = "Buy some grocery",
            amount = -120.0,
            time = "10:00 AM",
            category = TransactionCategory.SHOPPING
        ),
        Transaction(
            type = "Subscription",
            description = "Disney+ Annual..",
            amount = -80.0,
            time = "03:30 PM",
            category = TransactionCategory.SUBSCRIPTION
        ),
        Transaction(
            type = "Food",
            description = "Buy a ramen",
            amount = -32.0,
            time = "07:30 PM",
            category = TransactionCategory.FOOD
        ),
        Transaction(
            type = "Transportation",
            description = "Charging Tesla",
            amount = -18.0,
            time = "08:30 PM",
            category = TransactionCategory.TRANSPORTATION
        )
    )

    val incomes = listOf(
        Transaction(
            type = "Salary",
            description = "Salary for July",
            amount = 5000.0,
            time = "04:30 PM",
            category = TransactionCategory.SALARY
        ),
        Transaction(
            type = "Freelance",
            description = "Salary for July",
            amount = 1200.0,
            time = "01:30 PM",
            category = TransactionCategory.FREELANCE
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "See your financial report",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6B4EFF),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Top categories",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Expenses items
            items(expenses.size) { index ->
                TransactionItem(transaction = expenses[index])
            }

            item {
                Text(
                    text = "Income",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
                )
            }

            // Income items
            items(incomes.size) { index ->
                TransactionItem(transaction = incomes[index])
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = transaction.category.backgroundColor,
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                CompositionLocalProvider(LocalContentColor provides transaction.category.iconTint) {
                    transaction.category.icon()
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = transaction.type,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = transaction.description,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "${if (transaction.amount > 0) "+" else "-"} ksh ${Math.abs(transaction.amount)}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = if (transaction.amount > 0) Color(0xFF4CAF50) else Color(0xFFE53935)
            )
            Text(
                text = transaction.time,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}