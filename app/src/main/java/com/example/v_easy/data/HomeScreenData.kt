package com.example.v_easy.data

object HomeScreenData {
    val income = 5000
    val expenses = 1200
    val transactions = listOf(
        Transaction(
            type = "Shopping",
            amount = -120.0,
            time = "10:00 AM"
        ),
        Transaction(
            type = "Subscription",
            amount = -80.0,
            time = "03:30 PM"
        ),
        Transaction(
            type = "Food",
            amount = -32.0,
            time = "07:30 PM"
        )
    )
}

data class Transaction(
    val type: String,
    val amount: Double,
    val time: String
)