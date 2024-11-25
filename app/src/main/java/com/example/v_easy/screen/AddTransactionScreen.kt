package com.example.v_easy.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class TransactionCategory1(
    val name: String,
    val icon: @Composable () -> Unit
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(navController: NavHostController) {
    var isExpense by remember { mutableStateOf(true) }
    var amount by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<TransactionCategory1?>(null) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showCategoryDropdown by remember { mutableStateOf(false) }

    // Validation states
    var showErrors by remember { mutableStateOf(false) }
    val isAmountValid = amount.isNotBlank() && amount.toDoubleOrNull() != null && amount.toDoubleOrNull()!! > 0
    val isTitleValid = title.isNotBlank()
    val isCategoryValid = selectedCategory != null

    // Category options
    val expenseCategories = remember {
        listOf(
            TransactionCategory1("Shopping") { Icon(Icons.Default.ShoppingCart, contentDescription = "Shopping") },
            TransactionCategory1("Food") { Icon(Icons.Default.Restaurant, contentDescription = "Food") },
            TransactionCategory1("Transport") { Icon(Icons.Default.DirectionsCar, contentDescription = "Transport") },
            TransactionCategory1("Entertainment") { Icon(Icons.Default.Movie, contentDescription = "Entertainment") },
            TransactionCategory1("Bills") { Icon(Icons.Default.Receipt, contentDescription = "Bills") }
        )
    }

    val incomeCategories = remember {
        listOf(
            TransactionCategory1("Salary") { Icon(Icons.Default.AttachMoney, contentDescription = "Salary") },
            TransactionCategory1("Investment") { Icon(Icons.Default.TrendingUp, contentDescription = "Investment") },
            TransactionCategory1("Gift") { Icon(Icons.Default.CardGiftcard, contentDescription = "Gift") },
            TransactionCategory1("Business") { Icon(Icons.Default.Business, contentDescription = "Business") }
        )
    }

    // Date picker dialog
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDate.toEpochDay() * 24 * 60 * 60 * 1000
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        selectedDate = LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // Time picker dialog
    if (showTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = selectedTime.hour,
            initialMinute = selectedTime.minute,
            is24Hour = true // Add this parameter
        )

        TimePickerDialog(
            onDismiss = { showTimePicker = false },
            onConfirm = {
                selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                showTimePicker = false
            },
        ) {
            CustomTimePicker(state = timePickerState)
        }
    }


    val backgroundColor = if (isExpense) {
        Color(0xFFFF4B55)
    } else {
        Color(0xFF00BFA5)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // Top Bar with back button
        CenterAlignedTopAppBar(
            title = { },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Transparent
            )
        )

        // Expense/Income Toggle
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = RoundedCornerShape(50),
                color = Color.White.copy(alpha = 0.2f),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(4.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = if (isExpense) Color.White else Color.Transparent,
                        modifier = Modifier
                            .width(120.dp)
                            .clickable { isExpense = true }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "Expense",
                                modifier = Modifier.padding(vertical = 8.dp),
                                color = if (isExpense) backgroundColor else Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = if (!isExpense) Color.White else Color.Transparent,
                        modifier = Modifier
                            .width(120.dp)
                            .clickable { isExpense = false }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "Income",
                                modifier = Modifier.padding(vertical = 8.dp),
                                color = if (!isExpense) backgroundColor else Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

        // Amount Section
        Text(
            text = "How much?",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 16.dp, top = 24.dp)
        )

        OutlinedTextField(
            value = amount,
            onValueChange = { newValue ->
                // Only allow numeric input
                if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                    amount = newValue
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                focusedBorderColor = Color.White
            ),
            prefix = { Text("Ksh ", color = Color.White) },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            isError = showErrors && !isAmountValid
        )

        // Form Section
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Category Dropdown
                ExposedDropdownMenuBox(
                    expanded = showCategoryDropdown,
                    onExpandedChange = { showCategoryDropdown = it }
                ) {
                    OutlinedTextField(
                        value = selectedCategory?.name ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Category") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = showCategoryDropdown)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        isError = showErrors && !isCategoryValid
                    )

                    ExposedDropdownMenu(
                        expanded = showCategoryDropdown,
                        onDismissRequest = { showCategoryDropdown = false }
                    ) {
                        val categories = if (isExpense) expenseCategories else incomeCategories
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        category.icon()
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(category.name)
                                    }
                                },
                                onClick = {
                                    selectedCategory = category
                                    showCategoryDropdown = false
                                }
                            )
                        }
                    }
                }

                if (showErrors && !isCategoryValid) {
                    Text(
                        "Please select a category",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                // Title Field
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    label = { Text("Title") },
                    isError = showErrors && !isTitleValid
                )

                if (showErrors && !isTitleValid) {
                    Text(
                        "Title is required",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                // Description Field
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    label = { Text("Description") }
                )

                // Date and Time Selection
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { showDatePicker = true },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Select Date"
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(selectedDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")))
                        }
                    }

                    Button(
                        onClick = { showTimePicker = true },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = "Select Time"
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(selectedTime.format(DateTimeFormatter.ofPattern("HH:mm")))
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Add Button with Validation
                Button(
                    onClick = {
                        showErrors = true
                        if (isAmountValid && isTitleValid && isCategoryValid) {
                            // Handle valid form submission
                            val transaction = Transaction1(
                                type = if (isExpense) "EXPENSE" else "INCOME",
                                amount = amount.toDouble(),
                                category = selectedCategory!!.name,
                                title = title,
                                description = description,
                                date = selectedDate,
                                time = selectedTime
                            )
                            // TODO: Save transaction
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = backgroundColor
                    )
                ) {
                    Text("Add Transaction")
                }
            }
        }
    }
}
@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = { },
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()

                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Using regular Button instead of TextButton
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("OK")
                    }
                }
            }
        }
    }
}

// Updated TransactionCategory data class with proper Composable handling


// Custom TimePicker implementation
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTimePicker(
    state: TimePickerState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Time input display
        TimeInput(
            state = state,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// Data class for the transaction
data class Transaction1(
    val type: String,
    val amount: Double,
    val category: String,
    val title: String,
    val description: String,
    val date: LocalDate,
    val time: LocalTime
)