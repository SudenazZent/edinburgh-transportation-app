package com.example.edinburghtransportation.ui.navigation

sealed class Screen(val route: String) {

    data object Splash : Screen("splash")

    data object Login : Screen("login")

    data object Register : Screen("register")

    data object Main : Screen("main")

    data object Home : Screen("home")

    data object Routes : Screen("routes")

    data object Map : Screen("map")

    data object Cards : Screen("cards")

    data object Settings : Screen("settings")

    data object RouteDetail : Screen("routeDetail/{routeId}") {

        fun createRoute(routeId: String): String {
            return "routeDetail/$routeId"
        }
    }
}