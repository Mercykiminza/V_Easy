package com.example.v_easy.navigation


import ProfileScreen
import TransactionScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController




import com.example.v_easy.onboarding.OnboardingScreen1
import com.example.v_easy.onboarding.OnboardingScreen2
import com.example.v_easy.onboarding.OnboardingScreen3
import com.example.v_easy.screen.AddTransactionScreen

import com.example.v_easy.screen.HomeScreen
import com.example.v_easy.screen.ReportScreen
import kotlinx.coroutines.delay




@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(2000)
        isLoading = false
    }

    if (isLoading) {
        LoadingScreen()
    } else {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        Scaffold(
            bottomBar = {
                if (currentRoute !in listOf(
                        Screen.Onboarding1.route,
                        Screen.Onboarding2.route,
                        Screen.Onboarding3.route,
                        Screen.NewTransaction.route // Hide bottom bar for new transaction screen
                    )) {
                    BottomNavigationBarWithFAB(navController)
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Onboarding1.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                // Onboarding flow
                composable(Screen.Onboarding1.route) {
                    OnboardingScreen1(navController)
                }
                composable(Screen.Onboarding2.route) {
                    OnboardingScreen2(navController)
                }
                composable(Screen.Onboarding3.route) {
                    OnboardingScreen3(navController)
                }

                // Main app screens
                composable(Screen.Home.route) { HomeScreen(navController) }
                composable(Screen.Transactions.route) { TransactionScreen(navController) }
                composable(Screen.Profile.route) { ProfileScreen(navController) }
                composable(Screen.Report.route) { ReportScreen(navController) }

                // New Transaction screen (without bottom navigation)
                composable(Screen.NewTransaction.route) {
               AddTransactionScreen(navController)
                }
            }
        }
    }
}
@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}


@Composable
fun MainNavigation(navController: NavHostController) {
    // Main content with bottom navigation bar
    Scaffold(
        bottomBar = {
            BottomNavigationBarWithFAB(navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route
            ) {
                composable(Screen.Home.route) {
                    HomeScreen()
                }
                composable(Screen.Transactions.route) {
                    TransactionScreen()
                }
                composable(Screen.Profile.route) {
                    ProfileScreen()
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBarWithFAB(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        NavigationBar(
            modifier = Modifier.fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            // Home Item
            NavigationBarItem(
                icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = "Home") },
                label = { Text("Home") },
                selected = currentRoute == Screen.Home.route,
                onClick = { navController.navigate(Screen.Home.route) }
            )

            // Transactions Item
            NavigationBarItem(
                icon = { Icon(imageVector = Icons.Filled.Money, contentDescription = "Transaction") },
                label = { Text("Transaction") },
                selected = currentRoute == Screen.Transactions.route,
                onClick = { navController.navigate(Screen.Transactions.route) }
            )

            // Empty space for FAB
            NavigationBarItem(
                icon = { Box(modifier = Modifier.size(24.dp)) },
                label = { Text("") },
                selected = false,
                onClick = { }
            )

            // Budget Item
            NavigationBarItem(
                icon = { Icon(imageVector = Icons.Filled.Report, contentDescription = "Report") },
                label = { Text("Report") },
                selected = currentRoute == Screen.Report.route,
                onClick = { navController.navigate(Screen.Report.route) }
            )

            // Profile Item
            NavigationBarItem(
                icon = { Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Profile") },
                label = { Text("Profile") },
                selected = currentRoute == Screen.Profile.route,
                onClick = { navController.navigate(Screen.Profile.route) }
            )
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = { navController.navigate(Screen.NewTransaction.route) },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-28).dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add Transaction",
                tint = Color.White
            )
        }
    }
}


sealed class Screen(val route: String) {
    // Onboarding screens
    object Onboarding1 : Screen("onboarding1")
    object Onboarding2 : Screen("onboarding2")
    object Onboarding3 : Screen("onboarding3")

    // Main screens
    object Home : Screen("home")
    object Transactions : Screen("transactions")
    object Report : Screen("report")
    object Profile : Screen("profile")
    object NewTransaction : Screen("new_transaction")
}