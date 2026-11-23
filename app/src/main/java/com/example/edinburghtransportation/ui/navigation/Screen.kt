package com.example.edinburghtransportation.ui.navigation

sealed class Screen(val route: String) {

    data object Splash : Screen("splash")

    data object Login : Screen("login")

    data object Register : Screen("register")

    data object Home : Screen("home")

    data object Routes : Screen("routes")


    data object MapWithStop : Screen("map/{stopId}") {
        fun createRoute(stopId: Long): String = "map/$stopId"
    }
    data object Stop : Screen("stop")
    data object JourneyPlanner : Screen("journeyPlanner")

    data object Cards : Screen("cards")
    data object AddCard : Screen("addCard")

    data object CardDetail : Screen("cardDetail/{cardId}") {

        fun createRoute(cardId: Int): String {
            return "cardDetail/$cardId"
        }
    }

    data object TopUp : Screen("topUp/{cardId}") {

        fun createRoute(cardId: Int): String {
            return "topUp/$cardId"
        }
    }

    data object Notifications : Screen("notifications")

    data object Settings : Screen("settings")
    data object Theme : Screen("theme")
    data object AccountInfo : Screen("accountInfo")
    data object Faq : Screen("faq")
    data object Language : Screen("language")
    data object RouteDetail : Screen("routeDetail/{routeId}") {

        fun createRoute(routeId: String): String {
            return "routeDetail/$routeId"
        }
    }
}