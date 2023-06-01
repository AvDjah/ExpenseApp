package com.example.expense.models

data class Expense(
    val amount : Float =  0f,
    val title : String = "",
    val description : String = "",
    val creatorId : Int = 0,
    val groupId : Int = 0,
    var userShares : Map<Int,Float> = mapOf(creatorId to amount.toFloat())
)
