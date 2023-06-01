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
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.expense.GroupViewModel
import com.example.expense.models.Expense
import com.example.expense.models.Group
import com.example.expense.models.User


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupInnerScreen() {

    val groupViewModel: GroupViewModel = viewModel()
    val groupUiState = groupViewModel.groupUiState.collectAsState()

    val selectedIndex = groupUiState.value.selectedGroupId
    var selectedGroup: Group? = null
    var groupName: String = "NO NAME"


    var expanded by remember {
        mutableStateOf(false)
    }
    var clicked by remember {
        mutableStateOf(false)
    }

    if (selectedIndex != -1) {
        groupName = groupViewModel.groupList[selectedIndex].name
        selectedGroup = groupViewModel.groupList[selectedIndex]
    }

    val navController = rememberNavController()
    //TODO : Add a condition so that if no group is selected this screen is automatically closed or doesn't open at all

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = groupName)
            }, actions = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More"
                    )
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    if (selectedGroup != null) {
                        selectedGroup.users.forEach {
                            DropdownMenuItem(
                                text = { Text(text = it.name) },
                                onClick = { /*TODO*/ })
                        }
                    }
                }
            })

        }, floatingActionButton = {
            FloatingActionButton(onClick = {
                 navController.navigate(DialogDestinations.FIRST_SCREEN.toString())
//                if (!clicked) {
//                    clicked = true
//                } else {
//                    navController.navigate(DialogDestinations.FIRST_SCREEN.toString())
//                }
//                navController.navigate(DialogDestinations.FIRST_SCREEN.toString())
            }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        innerPadding ->
        val groupViewModel: GroupViewModel = viewModel()

        NavHost(
            navController = navController, DialogDestinations.GROUP_INNER_SCREEN.toString(), modifier = Modifier.padding(innerPadding)
        ) {
            composable(DialogDestinations.GROUP_INNER_SCREEN.toString()){
//                GroupInnerScreen(addExpnse = {
//                    navController.navigate(DialogDestinations.FIRST_SCREEN.toString())
//                }, navController = navController)

                if (selectedGroup != null) {
                    val selectedGroup = groupViewModel.groupList[selectedIndex]
                    Column {
                        GroupInnerHome(
                            group = selectedGroup,
                            navController = navController,
                            groupViewModel = groupViewModel
                        )
//                if (clicked) {
//                    AddExpenseBox(selectedGroup = selectedGroup, navController = navController)
//                }
                    }
                } else {
                    Text(text = "No GROUP SELECTED",)
                }

            }
            dialog(
                DialogDestinations.FIRST_SCREEN.toString(), dialogProperties = DialogProperties(
                    dismissOnClickOutside = true
                )
            ) {

                if (selectedGroup != null) {
                    FIRST_SCREEN(
                        groupViewModel = groupViewModel,
                        selectedGroup = selectedGroup,
                        onNextClick = {
                            navController.navigate(DialogDestinations.SECOND_SCREEN.toString())
            //                    navController.popBackStack()
            //                    navController.navigate(DialogDestinations.SECOND_SCREEN.toString())
                        })
                } else {
                    Text("NO GROUP SELECTED")
                }
            }
            dialog(DialogDestinations.SECOND_SCREEN.toString()) {
                SECOND_SCREEN(groupViewModel = groupViewModel, onGoBackClick = {
                    navController.popBackStack(DialogDestinations.FIRST_SCREEN.toString(), false)

                }, navController = navController)
            }
            dialog(DialogDestinations.THIRD_SCREEN.toString()) {
                THIRD_SCREEN()
            }

        }




    }
}

//val color1 = Colo
val color1 = Color.Transparent

enum class DialogDestinations {
    FIRST_SCREEN, SECOND_SCREEN, THIRD_SCREEN,
    GROUP_INNER_SCREEN
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseBox(
    modifier: Modifier = Modifier,
    selectedGroup: Group,
    navController: NavHostController = rememberNavController()
) {


}

@Composable
fun THIRD_SCREEN(modifier: Modifier = Modifier) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FIRST_SCREEN(
    modifier: Modifier = Modifier,
    selectedGroup: Group,
    onNextClick: () -> Unit,
    groupViewModel: GroupViewModel
) {

    val checkList = remember {
        mutableStateListOf<Boolean>()
    }
    var amount = remember {
        mutableStateOf("")
    }
    val localContext = LocalContext.current
    checkList.addAll(selectedGroup.users.map { false })

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
                    Text(
                        text = selectedGroup.users[index].name
                    )
                }
            }

        }
        Text(
            "NEXT", modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(color1)
                .clickable {
                    val res =
                        groupViewModel.addExpense(
                            if (amount.value.isEmpty()) {
                                0f
                            } else {
                                amount.value.toFloat()
                            },
                            mapOf<User, Int>(),
                            checkList = checkList,
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
                    //                    onNextClick()
                }, textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyMedium
        )
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
        mutableStateListOf<Pair<Int,Float>>()
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
            oldShareList.forEachIndexed {
                index , it ->
                val name = groupViewModel.getUserName(it.first)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(name)
                    TextField(
                        value = it.second.toString(),
                        onValueChange = {
                            newValue ->
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

                val mutableMap = mutableMapOf<Int,Float>()
                mutableMap.putAll(oldShareList)

                selectedGroup.expenseList[expenseIndex].userShares = mutableMap

                groupViewModel.groupList[groupIndex] = groupViewModel.groupList[groupIndex].copy(
                    expenseList = selectedGroup.expenseList
                )
                navController.popBackStack()

            }, modifier = modifier.padding(10.dp)) {
                Text("Save")
            }
        }
    }
}


@Composable
fun GroupInnerHome(

    group: Group,
    navController: NavHostController,
    groupViewModel: GroupViewModel
) {
    AGroup(group = group,  onExpenseItemClick = {
        groupViewModel.selectedExpense = it
        navController.navigate(DialogDestinations.SECOND_SCREEN.toString())
    })
}


@Composable
fun AGroup(
    group: Group,
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
            itemsIndexed(group.expenseList) { index, item ->
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

