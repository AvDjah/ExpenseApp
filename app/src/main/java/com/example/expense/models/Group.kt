package com.example.expense.models

data class Group(
    val name : String = "",
    val groupId : Int,
    val users : List<User> = listOf(
        User("Arvind",0),
        User("Abhishek", 1),
        User("Amrita",2)
    ),
    val adminId : Int = 0,
    val expenseList : List<Expense> = listOf(),
)
