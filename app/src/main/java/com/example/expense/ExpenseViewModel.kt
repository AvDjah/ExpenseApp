package com.example.expense

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.expense.models.Expense
import com.example.expense.ui.theme.ExpenseUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class ExpenseViewModel : ViewModel() {
    private val _list1 = mutableStateListOf<Int>()
    val list1: List<Int> = _list1

//    private val _uiState = MutableStateFlow(ExpenseUiState())
//    val uiState = _uiState.asStateFlow()
//
//    private val _expenseList = mutableStateListOf<Expense>(
//        Expense(100,"Title","Course! From a group of Lybian Nationalists They wanted me to build them a bomb, so I took their plutonium and in turn I gave them a shiny bomb caseing full of used pinball machine parts!"
//        ,false)
//    )
//    var expenseList : List<Expense>  = _expenseList
//
//    fun updateExpense(index: Int) {
//        _expenseList[index] = _expenseList[index].copy(credit = !_expenseList[index].credit)
//        Log.d("BUTTON CLICKED","change index: ${index.toString()}")
//    }
//
//    fun addExpense(title: String){
//        if(title.isEmpty()){
//            return
//        }
//        _expenseList.add(Expense(100,title,"Des",false))
//    }

}


    //    private var _list2 = mutableStateListOf<Expense>(
//        Expense(0,"1","2",false)
//    )
//    val list2 : List<Expense> = _list2
//
//    fun addNumber(){
//        val number = Random(1).nextInt()

//        _list1.add(number)
//    }
//
//    fun updateExpense(index : Int){
//        _list2[index] = _list2[index].copy(credit = !list2[index].credit)
//        Log.d("BUTTON CLICKED", "clicked~!!!!")
//    }