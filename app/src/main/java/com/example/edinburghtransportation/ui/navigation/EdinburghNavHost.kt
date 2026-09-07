package com.example.edinburghtransportation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.edinburghtransportation.ui.home.HomeScreen
import com.example.edinburghtransportation.ui.login.LoginScreen
import com.example.edinburghtransportation.ui.login.RegisterScreen
import com.example.edinburghtransportation.ui.routes.RouteDetailScreen
import com.example.edinburghtransportation.ui.routes.RoutesScreen
import com.example.edinburghtransportation.ui.splash.SplashScreen
import com.example.edinburghtransportation.ui.map.MapScreen

@Composable
fun EdinburghNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        //startDestination = Screen.Splash.route
        startDestination = Screen.Map.route
    ) {

        // Splash Screen
        composable(Screen.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Login Screen
        composable(Screen.Login.route) {
            LoginScreen(
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                },
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Register Screen
        composable(Screen.Register.route) {
            RegisterScreen(
                onLoginClick = {
                    navController.popBackStack()
                },
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen()
        }

        composable(Screen.Map.route) {
            MapScreen()
        }

        composable(Screen.Routes.route) {
            RoutesScreen(
                onRouteClick = { route ->
                    navController.navigate(
                        Screen.RouteDetail.createRoute(route.routeId)
                    )
                }
            )
        }

        composable(
            route = Screen.RouteDetail.route
        ) { backStackEntry ->

            val routeId =
                backStackEntry.arguments?.getString("routeId") ?: ""

            RouteDetailScreen(
                routeId = routeId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}