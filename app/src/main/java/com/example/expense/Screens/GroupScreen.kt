package com.example.expense.Screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.expense.GroupViewModel
import com.example.expense.models.Expense
import com.example.expense.models.Group
import com.example.expense.models.User
import kotlin.math.exp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupScreen(navController: NavController, groupViewModel: GroupViewModel){
    HomeScreen(openGroup = {
            groupViewModel.changeSelectedGroup(it)
            Log.d("GROUP SELECTED",groupViewModel.groupUiState.value.selectedGroupId.toString())
            navController.navigate(DialogDestinations.GROUP_INNER_SCREEN.toString())
    })

}

val group : Group = Group(
    name = "Homies",
    users = listOf(
        User("Arvind",0),
        User("Meena",1)
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




@Composable
fun HomeScreen(modifier: Modifier = Modifier, openGroup: (Int) -> Unit) {
    var groupViewModel : GroupViewModel = viewModel()
    var groupList = groupViewModel.groupList
    LazyColumn(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp)){
        item {
            Text("GROUPS LIST", style = TextStyle(
                color = Color.Black, background = Color.White, fontSize = MaterialTheme.typography.headlineSmall.fontSize
            ), modifier = modifier.padding(10.dp))
        }
        itemsIndexed(groupList){
            index, it ->
            GroupItem(group = it, openGroup = {
                openGroup(index)
            })
        }
    }
}

@Composable
fun GroupItem(group : Group, modifier: Modifier = Modifier, openGroup : ()->Unit){
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp).clickable {
            openGroup()
        }) {
        Column(modifier = modifier.padding(8.dp)) {
            Text(text = group.name, modifier.padding(8.dp))
        }
    }

}


//        if(showShares){
//            LazyColumn(modifier = modifier.fillMaxWidth()){
//                item {
//                    Text(text = "Hello")
//                }
//                items(expense.userShares.keys.toList()){
//                    Row(modifier = modifier.padding(10.dp), horizontalArrangement =  Arrangement.SpaceBetween) {
//                        Text(text = getUserName(it))
//                        Text(text = expense.userShares[it].toString())
//                    }
//                }
//            }
//        }
