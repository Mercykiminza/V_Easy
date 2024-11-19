package com.example.v_easy.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.v_easy.R

@Composable
fun OnboardingScreen1(navController: NavHostController) {
    OnboardingContent(
        title = "Gain total control of your money",
        description = "Become your own money manager and make every cent count",
        imageRes = R.drawable.onboard1,
        buttonText = "Next",
        currentStep = 1,
        totalSteps = 3
    ) {
        navController.navigate("onboarding2")
    }
}

@Composable
fun OnboardingScreen2(navController: NavHostController) {
    OnboardingContent(
        title = "Know where your money goes",
        description = "Track your transaction easily, with categories and financial report",
        imageRes = R.drawable.onboard1,
        buttonText = "Next",
        currentStep = 2,
        totalSteps = 3
    ) {
        navController.navigate("onboarding3")
    }
}

@Composable
fun OnboardingScreen3(navController: NavHostController) {
    OnboardingContent(
        title = "Planning ahead",
        description = "Setup your budget for each category so you stay in control",
        imageRes = R.drawable.onboard1,
        buttonText = "Get Started",
        currentStep = 3,
        totalSteps = 3
    ) {
        navController.navigate("home") {
            // Clear the backstack to prevent navigating back to onboarding screens
            popUpTo("onboarding1") { inclusive = true }
        }
    }
}

@Composable
fun OnboardingContent(
    title: String,
    description: String,
    imageRes: Int,
    buttonText: String,
    currentStep: Int,
    totalSteps: Int,
    onNextClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = imageRes), contentDescription = null)
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = title, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description, style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onNextClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(buttonText)
            }

            Spacer(modifier = Modifier.height(16.dp))
            ProgressIndicator(currentStep, totalSteps)
        }
    }
}
@Composable
fun ProgressIndicator(currentStep: Int, totalSteps: Int) {
    Row(
        modifier = Modifier.padding(top = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalSteps) { step ->
            Box(
                modifier = Modifier
                    .size(16.dp) // Size for the rounded indicator
                    .background(
                        color = if (step + 1 <= currentStep) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(horizontal = 4.dp)
            )
        }
    }
}
