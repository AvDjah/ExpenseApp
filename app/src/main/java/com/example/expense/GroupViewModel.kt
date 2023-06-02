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
import kotlinx.coroutines.flow.update

class GroupViewModel : ViewModel() {
    private val _groupUiState = MutableStateFlow(GroupUiState())
    val groupUiState = _groupUiState.asStateFlow()

    fun changeSelectedGroup(index : Int){
        _groupUiState.update {
            it -> groupUiState.value.copy(
                selectedGroupId = index
            )
        }
    }

    fun addExpense(amount : Float, checkList: List<Boolean>) : Boolean{
            val selectedGroupId = groupUiState.value.selectedGroupId
            if(selectedGroupId == -1){
                return false
            }

            Log.d("INSIDE ADD EXPENSE", selectedGroupId.toString())

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
            tempExpense = newExpense
            return true
    }

    var tempExpense: Expense =  Expense(
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
    var selectedExpense = -1
    fun changeExpenseShares(){
        val expense = tempExpense
    }

    fun getUserName(userId: Int): String {
        val selectedGroupId : Int = 0
        val group = groupList[selectedGroupId]
        return group.users[userId].name
    }


    val groupList = mutableStateListOf<Group>(
        Group(
            name = "Kulu",
            users = listOf(
                User("Arvind", 0),
                User("Meena", 1),
                User("Balwinder",2)
            ),
            groupId = 1,
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
        ),Group(
            name = "Homies",
            users = listOf(
                User("Arvind", 0),
                User("Meena", 1),
                User("Dummy",2)
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