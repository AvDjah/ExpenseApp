package com.example.expense

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.example.expense.Screens.DialogDestinations
import com.example.expense.Screens.FIRST_SCREEN
import com.example.expense.Screens.GroupInnerHome
import com.example.expense.Screens.GroupInnerScreen
import com.example.expense.Screens.GroupScreen
import com.example.expense.Screens.SECOND_SCREEN
import com.example.expense.models.Group


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    val groupViewModel : GroupViewModel = viewModel()

    NavHost(
        navController = navController,
        DialogDestinations.GROUP_SCREEN.toString(),
    ) {
        composable(DialogDestinations.GROUP_SCREEN.toString()){
            GroupScreen(navController =  navController, groupViewModel = groupViewModel)
        }
        composable(DialogDestinations.GROUP_INNER_SCREEN.toString()) {
            GroupInnerScreen(navController = navController, groupViewModel = groupViewModel)
        }
        dialog(
            DialogDestinations.FIRST_SCREEN.toString(), dialogProperties = DialogProperties(
                dismissOnClickOutside = true
            )
        ){
            FIRST_SCREEN( onNextClick = {
                                        navController.popBackStack(DialogDestinations.GROUP_INNER_SCREEN.toString(),false)
            }, groupViewModel = groupViewModel)
        }
        dialog(DialogDestinations.SECOND_SCREEN.toString()) {
            SECOND_SCREEN(onGoBackClick = {
                                          navController.popBackStack(DialogDestinations.GROUP_INNER_SCREEN.toString(),false)
            }, groupViewModel = groupViewModel, navController = navController)
        }
    }

    //TODO : Add a condition so that if no group is selected this screen is automatically closed or doesn't open at all


}