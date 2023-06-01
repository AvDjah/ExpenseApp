package com.example.expense

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expense.Screens.AddExpenseBox
import com.example.expense.Screens.GroupInnerScreen
import com.example.expense.Screens.GroupScreen
import com.example.expense.models.Expense
import com.example.expense.ui.theme.ExpenseTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTheme {
                    GroupInnerScreen()
            }
        }
    }
}
//
//@Preview()
//@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
//@Composable
//fun HomeScreenScaffold(){
//
//    val expenseViewModel : ExpenseViewModel = viewModel()
//    val keyboardController = LocalSoftwareKeyboardController.current
//    val focusManager = LocalFocusManager.current
//    val focusRequester = remember {
//        FocusRequester()
//    }
//
//    var addMode by remember {
//        mutableStateOf(false)
//    }
//
////    LaunchedEffect(key1 = Unit,  ){
////            focusRequester.requestFocus()
////    }
//
//
////TODO : Find a way to use request focus on the conditional textfield
//
//
//    Scaffold(
//        floatingActionButton = {
//            if(!addMode){
//                FloatingActionButton(onClick = {
//                    addMode = !addMode
//
////                    focusRequester.requestFocus()
//                }) {
//                    androidx.compose.material3.Icon(imageVector = Icons.Default.Add, contentDescription = null)
//                }
//            }
//        }
//    ) { contentPadding -> Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
//        HomeScreen(contentPadding)
//        if(addMode){
//        Row(modifier = Modifier.fillMaxWidth()) {
//
//            var loremText by remember { mutableStateOf("") }
//            LaunchedEffect(key1 = Unit, ){
//                focusRequester.requestFocus()
//            }
////                TextField(
////                    value = loremText,
////                    onValueChange = { loremText = it },
////                )
//            TextField(
//                modifier = Modifier.focusRequester(focusRequester),
//                value = loremText,
//                onValueChange = { loremText = it },
//
//            )
//            Button(onClick = {
//                expenseViewModel.addExpense(loremText)
//                loremText = ""
//                if (keyboardController != null) {
//                    keyboardController.hide()
//                }
//                focusRequester.freeFocus()
//                addMode = !addMode
//
//            }) {
//                Text(text = "ADD")
//            }
//        }
//        }
//
//    }
//}}
//
//
//@Composable
//fun HomeScreen(contentPadding: PaddingValues) {
//    val expenseViewModel : ExpenseViewModel = viewModel()
//    val items = expenseViewModel.list1
//    val expenses = expenseViewModel.expenseList
//
//    LazyColumn(){
//        itemsIndexed(expenses){
//            index, item -> ExpenseItem(item, changeCreditType = {
//                expenseViewModel.updateExpense(index)
//            })
//        }
//}


//
//@Composable
//fun ExpenseItem(expense : Expense, changeCreditType : () -> (Unit), modifier: Modifier = Modifier){
//    val expenseViewModel : ExpenseViewModel = viewModel()
//    val isExpanded = remember {
//        mutableStateOf(false)
//    }
//    val paddingModifier : Modifier = Modifier.padding(8.dp)
//    Column(modifier = modifier
//        .fillMaxWidth()
//        .padding(8.dp)
//        .animateContentSize(
//            animationSpec = spring(
//                dampingRatio = Spring.DampingRatioLowBouncy,
//                stiffness = Spring.StiffnessLow
//            )
//        )
//        ) {
//        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//            .clickable {
//                isExpanded.value = !isExpanded.value
//            }) {
//            Text(text = expense.title, modifier = paddingModifier)
//            Button(modifier = paddingModifier,onClick = {
//                changeCreditType()
//            }, colors = ButtonDefaults.buttonColors(containerColor = if(expense.credit){
//                Color.Green
//            } else {
//                Color.Red
//            })) {
//                Text(expense.amount.toString())
//            }
//        }
//        if(isExpanded.value){
//            Card(elevation = CardDefaults.cardElevation(
//                defaultElevation = 10.dp
//            ),) {
//                Text(expense.description, modifier = modifier.padding(8.dp))
//            }
//        }
//    }
//}


//Column {
//    expenses.forEachIndexed {index , it ->
//        Row() {
//            Text(text = it.title)
//            Button(onClick = {
//                expenseViewModel.updateExpense(index)
//            }, colors = ButtonDefaults.buttonColors(containerColor = if(expenses[index].credit){
//                Color.Green
//            } else {
//                Color.Red
//            }) ) {
//                Text(text = it.amount.toString())
//            }
//        }
//    }
//}
