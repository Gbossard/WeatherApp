package com.example.weatherapp.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.R
import com.example.weatherapp.ui.screens.DetailsScreen
import com.example.weatherapp.ui.screens.HomeScreen
import com.example.weatherapp.ui.screens.WeatherViewModel

enum class WeatherScreen(@StringRes val title: Int) {
    Home(title = R.string.app_name),
    Details(title = R.string.weather)
}

@Composable
fun WeatherApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = WeatherScreen.valueOf(
        backStackEntry?.destination?.route ?: WeatherScreen.Home.name
    )
    Scaffold(
        topBar = {
            WeatherAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val weatherViewModel: WeatherViewModel = viewModel(factory = WeatherViewModel.Factory)
        NavHost(
            navController = navController,
            startDestination = WeatherScreen.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = WeatherScreen.Home.name) {
                HomeScreen(
                    onClick = {
                        navController.navigate(WeatherScreen.Details.name)
                        weatherViewModel.showWeather()
                    }
                )
            }
            composable(route = WeatherScreen.Details.name) {
                DetailsScreen(
                    weatherUiState = weatherViewModel.weatherUiState,
                    onClick = {weatherViewModel.reset()}
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    currentScreen: WeatherScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (currentScreen == WeatherScreen.Details) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    stringResource(currentScreen.title)
                )
            },
            modifier = modifier,
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            }
        )
    }
}