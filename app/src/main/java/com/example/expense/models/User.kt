package com.example.expense.models

data class User(
    val name : String = "",
    val userId : Int,
    val groupList : List<Int> = listOf(0)
)
