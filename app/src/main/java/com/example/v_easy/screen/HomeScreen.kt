package com.example.v_easy.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.Subscriptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.v_easy.R


@Composable
fun HomeScreen(navController: NavHostController? = null) {
    val income = 5000
    val expenses = 1200

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
    ) {
        // Container for Profile, Income, and Expenses with a purple background and rounded corners
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF8A2BE2)) // Purple background
                .clip(RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)) // Rounded bottom corners
                .padding(16.dp) // Padding inside the container
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Profile Row (left: profile image and text, right: bell icon)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.onboard1),
                            contentDescription = "Profile image",
                            modifier = Modifier
                                .size(40.dp) // Adjust size as needed
                                .clip(CircleShape) // Make the image circular
                                .border(1.dp, Color.Gray, CircleShape) // Optional: add a border to the image
                        )

                        Spacer(modifier = Modifier.width(8.dp)) // Space between image and text

                        Text(
                            "Welcome Mercy",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = Color.White // White text for visibility on purple background
                        )
                    }

                    // Bell icon (right side)
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = Color.White // White icon for visibility on purple background
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Income and Expenses Cards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FinanceCard(
                        title = "Money in",
                        amount = income,
                        color = Color.White ,// Green
                        backgroundColor = (Color.Green),
                        transactionIcon = Icons.Default.AccountBalanceWallet


                    )
                    FinanceCard(
                        title = "Money out",
                        amount = expenses,
                        color = Color.White, // Red
                        backgroundColor = Color.Red
                        ,     transactionIcon = Icons.Default.AccountBalanceWallet
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Spend Frequency Graph
        SpendFrequencyGraph()

        Spacer(modifier = Modifier.height(16.dp))

        // Recent Transactions
        RecentTransactions()
    }
}

@Composable
fun FinanceCard(
    title: String,
    amount: Int,
    color: Color,
    backgroundColor: Color,
    transactionIcon: ImageVector // Using ImageVector for material icons
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically, // Vertically align the icon and text
        modifier = Modifier.padding(3.dp)
            .background(backgroundColor, RoundedCornerShape(8.dp)) // Rounded corners for the background
            .padding(10.dp) // Padding inside the container
    ) {
        // Displaying the icon
        Icon(
            imageVector = transactionIcon, // Pass the icon vector here
            contentDescription = "Transaction icon", // Descriptive content for accessibility
            tint = Color.White, // You can change the tint color for the icon
            modifier = Modifier.padding(end = 8.dp) // Optional: space between the icon and text
        )

        // Column for title and amount with background
        Column(

        ) {
            Text(
                title,
                color = Color.White, // White text for visibility
                fontWeight = FontWeight.Bold
            )
            Text(
                "Ksh ${amount}",
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = 20.sp
            )
        }
    }
}




@Composable
fun SpendFrequencyGraph() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.trends),
            contentDescription = "Frequency graph",
            modifier = Modifier
                .fillMaxSize(), // Fill the entire Card
            contentScale = ContentScale.FillBounds // Make the image fill the bounds
        )
    }
}


data class Transaction(
    val type: String,
    val description: String,
    val amount: Double,
    val time: String,
    val category: TransactionCategory
)

enum class TransactionCategory(
    val backgroundColor: Color,
    val iconTint: Color,
    val icon: @Composable () -> Unit
) {
    INCOME(
        backgroundColor = Color(0xFFE8F5E9),
        iconTint = Color(0xFF4CAF50),
        icon = { Icon(Icons.Default.AccountBalanceWallet, contentDescription = null) }
    ),
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
    )
}

@Composable
fun RecentTransactions(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recent Transactions",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            TextButton(onClick = { /* Handle see all click */ }) {
                Text(
                    text = "See All",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Updated transactions with both positive and negative amounts
        val transactions = listOf(
            Transaction(
                type = "Salary",
                description = "Monthly salary",
                amount = 2500.0, // Positive amount
                time = "09:00 AM",
                category = TransactionCategory.INCOME
            ),
            Transaction(
                type = "Shopping",
                description = "Buy some grocery",
                amount = -120.0,
                time = "10:00 AM",
                category = TransactionCategory.SHOPPING
            ),
            Transaction(
                type = "Freelance",
                description = "Web design project",
                amount = 350.0, // Positive amount
                time = "02:15 PM",
                category = TransactionCategory.INCOME
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
            )
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(transactions.size) { index ->
                TransactionItem(transaction = transactions[index])
                if (index < transactions.size - 1) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}


@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(transaction.category.backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { /* Handle icon click */ },
                    modifier = Modifier.size(24.dp)
                ) {
                    CompositionLocalProvider(LocalContentColor provides transaction.category.iconTint) {
                        transaction.category.icon()
                    }
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
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = if (transaction.amount > 0) "+ Ksh ${transaction.amount}" else "- Ksh ${Math.abs(transaction.amount)}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = if (transaction.amount > 0) Color.Green else Color.Red
            )
            Text(
                text = transaction.time,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}









@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}