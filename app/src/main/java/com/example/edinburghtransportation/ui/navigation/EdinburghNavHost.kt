package com.example.edinburghtransportation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.edinburghtransportation.ui.cards.AddCardScreen
import com.example.edinburghtransportation.ui.cards.CardDetailScreen
import com.example.edinburghtransportation.ui.cards.CardsScreen
import com.example.edinburghtransportation.ui.cards.TopUpScreen
import com.example.edinburghtransportation.ui.home.HomeScreen
import com.example.edinburghtransportation.ui.journey.JourneyPlannerScreen
import com.example.edinburghtransportation.ui.login.LoginScreen
import com.example.edinburghtransportation.ui.login.RegisterScreen
import com.example.edinburghtransportation.ui.map.MapScreen
import com.example.edinburghtransportation.ui.routes.RouteDetailScreen
import com.example.edinburghtransportation.ui.routes.RoutesScreen
import com.example.edinburghtransportation.ui.splash.SplashScreen
import com.example.edinburghtransportation.ui.stop.StopsScreen
import com.example.edinburghtransportation.ui.notifications.NotificationsScreen
import com.example.edinburghtransportation.ui.settings.SettingsScreen
import com.example.edinburghtransportation.ui.settings.AccountInfoScreen
import com.example.edinburghtransportation.ui.settings.FaqScreen
import com.example.edinburghtransportation.ui.settings.LanguageScreen
import com.example.edinburghtransportation.ui.settings.ThemeScreen

@Composable
fun EdinburghNavHost(
    selectedTheme: String,
    onThemeSelected: (String) -> Unit
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,

        // startDestination = Screen.JourneyPlanner.route
        startDestination = Screen.Home.route
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

        // Home
        composable(Screen.Home.route) {
            HomeScreen(
                onNotificationsClick = {
                    navController.navigate(Screen.Notifications.route)
                },
                onCardsClick = {
                    navController.navigate(Screen.Cards.route)
                },
                onRoutesClick = {
                    navController.navigate(Screen.Routes.route)
                },
                onStopsClick = {
                    navController.navigate(Screen.Stop.route)
                },
                onJourneyPlannerClick = {
                    navController.navigate(
                        Screen.JourneyPlanner.route
                    )
                },
                onSettingsClick = {
                    navController.navigate(
                        Screen.Settings.route
                    )
                }
            )
        }

        // Notifications
        composable(Screen.Notifications.route) {
            NotificationsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Stops
        composable(Screen.Stop.route) {
            StopsScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onStopClick = { stop ->
                    navController.navigate(
                        Screen.MapWithStop.createRoute(stop.id)
                    )
                }
            )
        }

        // Cards
        composable(Screen.Cards.route) {
            CardsScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onAddCardClick = {
                    navController.navigate(Screen.AddCard.route)
                },
                onCardClick = { card ->
                    navController.navigate(
                        Screen.CardDetail.createRoute(card.id)
                    )
                }
            )
        }

        // Add Card
        composable(Screen.AddCard.route) {
            AddCardScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Card Detail
        composable(
            route = Screen.CardDetail.route
        ) { backStackEntry ->

            val cardId =
                backStackEntry.arguments
                    ?.getString("cardId")
                    ?.toIntOrNull() ?: 0

            CardDetailScreen(
                cardId = cardId,
                onBackClick = {
                    navController.popBackStack()
                },
                onTopUpClick = { selectedCardId ->
                    navController.navigate(
                        Screen.TopUp.createRoute(selectedCardId)
                    )
                }
            )
        }

        // Top Up
        composable(
            route = Screen.TopUp.route
        ) { backStackEntry ->

            val cardId =
                backStackEntry.arguments
                    ?.getString("cardId")
                    ?.toIntOrNull() ?: 0

            TopUpScreen(
                cardId = cardId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Map with selected stop
        composable(
            route = Screen.MapWithStop.route
        ) { backStackEntry ->

            val stopId =
                backStackEntry.arguments
                    ?.getString("stopId")
                    ?.toLongOrNull()

            MapScreen(
                selectedStopId = stopId
            )
        }

        // Journey Planner
        composable(Screen.JourneyPlanner.route) {
            JourneyPlannerScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Routes
        composable(Screen.Routes.route) {
            RoutesScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onRouteClick = { route ->
                    navController.navigate(
                        Screen.RouteDetail.createRoute(route.routeId)
                    )
                }
            )
        }

        // Route Detail
        composable(
            route = Screen.RouteDetail.route
        ) { backStackEntry ->

            val routeId =
                backStackEntry.arguments
                    ?.getString("routeId") ?: ""

            RouteDetailScreen(
                routeId = routeId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Settings
        composable(Screen.Settings.route) {
            SettingsScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onAccountClick = {
                    navController.navigate(Screen.AccountInfo.route)
                },
                onFaqClick = {
                    navController.navigate(Screen.Faq.route)
                },
                onLanguageClick = {
                    navController.navigate(Screen.Language.route)
                },
                onThemeClick = {
                    navController.navigate(Screen.Theme.route)
                }
            )
        }

        // Account Information
        composable(Screen.AccountInfo.route) {
            AccountInfoScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // FAQ
        composable(Screen.Faq.route) {
            FaqScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Language
        composable(Screen.Language.route) {
            LanguageScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Theme
        composable(Screen.Theme.route) {
            ThemeScreen(
                selectedTheme = selectedTheme,
                onThemeSelected = onThemeSelected,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}