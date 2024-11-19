import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController

@Composable
fun ProfileScreen(navController: NavHostController? = null) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray)
                        )
                    }

                    Column {
                        Text(
                            text = "Username",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "Iriana Saliha",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                IconButton(onClick = { /* Handle edit click */ }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Profile",
                        tint = Color(0xFF6B4EFF)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Payment Methods Section
            Text(
                text = "Payment Methods",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SquareCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.AccountBalance,
                    title = "M-Pesa",
                    subtitle = "BALANCE"
                )
                SquareCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.CreditCard,
                    title = "PHYSICAL",
                    subtitle = "CASH"
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Sim Cards Section
            Text(
                text = "Sim-Cards",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SquareCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.SimCard,
                    title = "Sim 1"
                )
                SquareCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.SimCard,
                    title = "Sim 2"
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Menu Items
            MenuItemButton(
                icon = Icons.Default.AccountBalanceWallet,
                text = "My Wallet",
                backgroundColor = Color(0xFFF3F0FF)
            ) {
                // Handle wallet click
            }

            Spacer(modifier = Modifier.height(16.dp))

            MenuItemButton(
                icon = Icons.Default.Settings,
                text = "Settings",
                backgroundColor = Color(0xFFF3F0FF)
            ) {
                // Handle settings click
            }

            Spacer(modifier = Modifier.height(16.dp))

            MenuItemButton(
                icon = Icons.Default.Download,
                text = "Export Data",
                backgroundColor = Color(0xFFF3F0FF)
            ) {
                // Handle export click
            }

            Spacer(modifier = Modifier.height(16.dp))

            MenuItemButton(
                icon = Icons.Default.ExitToApp,
                text = "Logout",
                backgroundColor = Color(0xFFFFEEEE),
                textColor = Color.Red,
                iconTint = Color.Red
            ) {
                showLogoutDialog = true
            }

            // Add bottom padding to ensure the last item is fully visible
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Logout Dialog
    if (showLogoutDialog) {
        Dialog(onDismissRequest = { showLogoutDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Logout?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Are you sure do you wanna logout?",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = { showLogoutDialog = false },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF3F0FF),
                                contentColor = Color(0xFF6B4EFF)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("No")
                        }

                        Button(
                            onClick = { /* Handle logout */ },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF6B4EFF)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Yes")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SquareCard(
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String? = null
) {
    Card(
        modifier = modifier.aspectRatio(1f),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF3F0FF)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF6B4EFF),
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun MenuItemButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    backgroundColor: Color,
    textColor: Color = Color.Black,
    iconTint: Color = Color(0xFF6B4EFF),
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                color = textColor,
                fontSize = 16.sp
            )
        }
    }
}