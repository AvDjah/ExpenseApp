package com.example.expense.ui.theme

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.expense.models.Expense

data  class ExpenseUiState(
    var expenseList : MutableList<Expense> = mutableListOf<Expense>(

    )
)