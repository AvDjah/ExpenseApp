package com.example.expense

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.expense.data.GroupUiState
import com.example.expense.models.Expense
import com.example.expense.models.Group
import com.example.expense.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GroupViewModel : ViewModel() {
    private val _groupUiState = MutableStateFlow(GroupUiState())
    val groupUiState = _groupUiState.asStateFlow()


    fun addExpense(amount : Float, userShare : Map<User,Int>, checkList: List<Boolean>) : Boolean{
            val selectedGroupId = groupUiState.value.selectedGroupId
            if(selectedGroupId == -1){
                return false
            }
            val group = groupList[selectedGroupId]
            val finalUserShare : MutableMap<Int, Float> = mutableMapOf()
            checkList.forEachIndexed{
                index, b ->
                if(b){
                    finalUserShare[index] = 0f
                }
            }
            if(finalUserShare.isEmpty()){
                return false
            }
            finalUserShare[0] = amount
            Log.d("MAP CREATED",checkList.toList().toString())
            Log.d("MAP CREATED", finalUserShare.toList().toString())
            val newExpense = Expense(
                groupId = selectedGroupId,
                title = "Dummy",
                description = "Dummy",
                amount = amount,
                creatorId = 0,
                userShares = finalUserShare
            )
            val oldExpenseList : MutableList<Expense> = group.expenseList.toMutableList()
            oldExpenseList.add(newExpense)
            groupList[selectedGroupId] = groupList[selectedGroupId].copy(
                expenseList = oldExpenseList.toList()
            )
            return true
    }



    fun getUserName(userId: Int): String {
        val selectedGroupId : Int = 0
        val group = groupList[selectedGroupId]
        return group.users[userId].name
    }


    val groupList = mutableStateListOf<Group>(
        Group(
            name = "Homies",
            users = listOf(
                User("Arvind", 0),
                User("Meena", 1)
            ),
            groupId = 0,
            adminId = 0,
            expenseList = listOf(
                Expense(
                    groupId = 0,
                    title = "Buy Apple",
                    description = "Dummy",
                    amount = 1000f,
                    creatorId = 0,
                    userShares = mapOf(
                        0 to 500.0f,
                        1 to 500.0f
                    )
                )
            )
        )
    )

}