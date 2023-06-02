package com.example.expense.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.expense.GroupViewModel
import com.example.expense.models.Expense
import com.example.expense.models.Group
import com.example.expense.models.User


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupInnerScreen(navController: NavHostController, groupViewModel: GroupViewModel) {

    var expanded by remember {
        mutableStateOf(false)
    }

    val groupUiState = groupViewModel.groupUiState.collectAsState()

    val selectedIndex = groupUiState.value.selectedGroupId

    if (selectedIndex == -1) {
        Log.d("INNERSCREEN", "SELECTED INDEX NOT VALID")
        navController.navigate(DialogDestinations.GROUP_SCREEN.toString())
    }

    var selectedGroup by remember {
        mutableStateOf(groupViewModel.groupList[selectedIndex])
    }
    var groupName by remember {
        mutableStateOf(selectedGroup.name)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Log.d("INNERSCREEN", "SELECTED GRP INDEX: $selectedIndex")
                Log.d("INNERSCREEN", "GRP NAME: $groupName")
                Text(text = groupName)
            }, actions = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More"
                    )
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    selectedGroup?.users?.forEach {
                        DropdownMenuItem(
                            text = { Text(text = it.name) },
                            onClick = { /*TODO*/ })
                    }
                }
            })

        }, floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(DialogDestinations.FIRST_SCREEN.toString())
            }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            GroupInnerHome(
                group = selectedGroup,
                navController = navController,
                groupViewModel = groupViewModel
            )
        }

    }
}

@Composable
fun GroupInnerHome(

    group: Group,
    navController: NavHostController,
    groupViewModel: GroupViewModel
) {
    val groupUiState = groupViewModel.groupUiState.collectAsState()
    val selectedGroupId = groupUiState.value.selectedGroupId
    val expenseList = groupViewModel.groupList[selectedGroupId].expenseList
    Column() {
        Log.d("GROUP_LOADED", groupUiState.value.selectedGroupId.toString())
        AGroup(expenseList = expenseList, onExpenseItemClick = {
            groupViewModel.selectedExpense = it
            navController.navigate(DialogDestinations.SECOND_SCREEN.toString())
        })
    }
}


//val color1 = Colo
val color1 = Color.Transparent

enum class DialogDestinations {
    FIRST_SCREEN, SECOND_SCREEN, THIRD_SCREEN,
    GROUP_INNER_SCREEN, GROUP_SCREEN
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FIRST_SCREEN(
    modifier: Modifier = Modifier,
    onNextClick: () -> Unit,
    groupViewModel: GroupViewModel
) {

    val groupUiState = groupViewModel.groupUiState.collectAsState()
    val selectedGroupId = groupUiState.value.selectedGroupId
    val selectedGroup = groupViewModel.groupList[selectedGroupId]

    val checkList = remember {
        mutableStateListOf<Boolean>()
    }

    var amount = remember {
        mutableStateOf("")
    }

    val localContext = LocalContext.current
    if(checkList.size != selectedGroup.users.size){
        checkList.addAll(selectedGroup.users.map { false })
    }

    Card(
        modifier = modifier
            .background(color1)
            .padding(16.dp),
    ) {
        Column() {
            TextField(
                value = amount.value, onValueChange = {
                    amount.value = it
                },
                label = {
                    Text("Enter Amount")
                },
                placeholder = {
                    Text("0")
                }, keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            checkList.forEachIndexed { index, it ->
                Row(
                    modifier = modifier
                        .padding(8.dp)
                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = checkList[index], onCheckedChange = {
                        Log.d("CHECK TRIGGER", checkList.joinToString(","))
                        Log.d("CHECK TRIGGER", index.toString())
                        checkList[index] = !checkList[index]
                    })

                    Log.d("CHECKLIST", index.toString())
                    Log.d("CHECKLIST", checkList.toList().toString())
                    Text(
                        text = selectedGroup.users[index].name
                    )
                }
            }

        }
        Button(onClick = {
            val res =
                groupViewModel.addExpense(
                    if (amount.value.isEmpty()) {
                        0f
                    } else {
                        amount.value.toFloat()
                    },
                    checkList = checkList.toList(),
                )
            if (!res) {
                Toast
                    .makeText(
                        localContext,
                        "NO user selected or Invalid group",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            } else {
                Toast
                    .makeText(localContext, "Success", Toast.LENGTH_SHORT)
                    .show()
            }
            onNextClick()
        }) {
            Text(
                "NEXT",
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(color1),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SECOND_SCREEN(
    modifier: Modifier = Modifier,
    onGoBackClick: () -> Unit,
    groupViewModel: GroupViewModel,
    navController: NavHostController
) {
//    val groupViewModel: GroupViewModel = viewModel()

    var tempExpense = groupViewModel.tempExpense

    var oldShareList = remember {
        mutableStateListOf<Pair<Int, Float>>()
    }

    var validationResult = remember {
        mutableStateOf(false)
    }

//    oldShareList.addAll(tempExpense.userShares.toList().toMutableList())
    if (groupViewModel.selectedExpense != -1) {
        tempExpense =
            groupViewModel.groupList[groupViewModel.groupUiState.collectAsState().value.selectedGroupId]
                .expenseList[groupViewModel.selectedExpense]
        oldShareList.addAll(tempExpense.userShares.toList().toMutableList())
    }
    val groupUiState = groupViewModel.groupUiState.collectAsState()
    Card(
        modifier = modifier
            .background(color1)
            .padding(16.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(16.dp)
        ) {
            Button(onClick = {
                onGoBackClick()
            }, modifier = modifier.padding(10.dp)) {
                Text("GO BACK")
            }
            if (validationResult.value) {
                val groupIndex = groupUiState.value.selectedGroupId
                val expenseIndex = groupViewModel.selectedExpense
                val selectedGroup = groupViewModel.groupList[groupIndex]
                Text(
                    "BALANCE NOT FULFILLED. ${selectedGroup.expenseList[expenseIndex].amount}",
                    fontStyle = FontStyle.Italic,
                    style = TextStyle(
                        color = Color.Red, textAlign = TextAlign.Center
                    ),
                    modifier = modifier.padding(8.dp)
                )
            } else {
                Text(
                    "ALL GOOD", fontStyle = FontStyle.Italic, style = TextStyle(
                        color = Color.Green, textAlign = TextAlign.Center
                    ), modifier = modifier.padding(8.dp)
                )
            }
            oldShareList.forEachIndexed { index, it ->
                val name = groupViewModel.getUserName(it.first)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(name)
                    TextField(
                        value = it.second.toString(),
                        onValueChange = { newValue ->
                            oldShareList[index] = it.copy(second = newValue.toFloat())
                        },
                        modifier = modifier.width(80.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }
            }
            Button(onClick = {
                val groupIndex = groupUiState.value.selectedGroupId
                val expenseIndex = groupViewModel.selectedExpense
                val selectedGroup = groupViewModel.groupList[groupIndex]

                val mutableMap = mutableMapOf<Int, Float>()
                mutableMap.putAll(oldShareList)
                var totalAdded: Float = 0f
                for (i in mutableMap) {
                    totalAdded += i.value
                }
                if (!validateBalance(totalAdded, selectedGroup.expenseList[expenseIndex].amount)) {
                    validationResult.value = true
                } else {
                    validationResult.value = false
                    selectedGroup.expenseList[expenseIndex].userShares = mutableMap

                    groupViewModel.groupList[groupIndex] =
                        groupViewModel.groupList[groupIndex].copy(
                            expenseList = selectedGroup.expenseList
                        )
                    navController.popBackStack()
                }

            }, modifier = modifier.padding(10.dp)) {
                Text("Save")
            }


        }
    }
}

fun validateBalance(totalAdded: Float, original: Float): Boolean {
    return totalAdded == original
}


@Composable
fun AGroup(
    expenseList: List<Expense>,
    modifier: Modifier = Modifier,
    onExpenseItemClick: (index: Int) -> Unit
) {
    Column(
        modifier = modifier
            .padding(8.dp)
    ) {
        LazyColumn(
            modifier = modifier
                .background(color = Color.Yellow.copy(alpha = 0.1f))
                .clip(shape = RoundedCornerShape(20.dp))
                .padding(2.dp), verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Log.d("UPDATED EXPENSE_LIST",expenseList.size.toString())
            itemsIndexed(expenseList) { index, item ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    ExpenseItem(expense = item, onExpenseItemClick = {
                        onExpenseItemClick(index)
                    })
                }
            }
        }
    }
}


@Composable
fun ExpenseItem(
    expense: Expense,
    modifier: Modifier = Modifier,
    onExpenseItemClick: () -> Unit = {}
) {

    var showShares by remember {
        mutableStateOf(false)
    }

    var nameTextStyle = MaterialTheme.typography.bodyMedium
    var expenseTextStyle = MaterialTheme.typography.bodyLarge
    val groupViewModel: GroupViewModel = viewModel()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(Color.Black, shape = RoundedCornerShape(10.dp))
            .padding(8.dp)
            .animateContentSize(
                animationSpec = spring(
                    stiffness = Spring.StiffnessMedium,
                    dampingRatio = Spring.DampingRatioLowBouncy
                )
            )
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier
            .fillMaxWidth()
            .clickable {
                showShares = !showShares
            }) {
            Text(text = expense.title, style = expenseTextStyle)
            Text(text = expense.amount.toString(), style = expenseTextStyle)
        }
        if (showShares) {
            expense.userShares.entries.forEach {
                Row(
                    modifier = modifier
                        .padding(4.dp)
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable {
                            onExpenseItemClick()
                        },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = groupViewModel.getUserName(it.key), style = nameTextStyle)
                    Text(text = it.value.toString(), style = nameTextStyle)
                }
            }
        }
    }
}

