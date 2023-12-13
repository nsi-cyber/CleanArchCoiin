package com.nsicyber.boilerplate.presenter.base.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nsicyber.boilerplate.presenter.detail.DetailScreen
import com.nsicyber.boilerplate.presenter.discover.DiscoverScreen
import com.nsicyber.boilerplate.presenter.login.LoginScreen
import com.nsicyber.boilerplate.presenter.profile.ProfileScreen
import com.nsicyber.boilerplate.presenter.signup.SignupScreen
import com.nsicyber.boilerplate.presenter.splash.SplashScreen


var navHostController: NavHostController? = null

var login = "login"
var signup = "signUp"
var discover = "discover"
var detail = "detail"
var profile = "profile"
var splash = "splash"


@Composable
fun NavigationController() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = splash) {

        composable(splash) {
            SplashScreen()
        }

        composable(login) {
            LoginScreen()
        }

        composable(signup) {
            SignupScreen()
        }
        composable(discover) {
            DiscoverScreen()
        }

        composable(profile) {
            ProfileScreen()
        }
        composable(
            "$detail?id={id}", arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                },
            )
        ) { navBackStackEntry ->
            DetailScreen(navBackStackEntry.arguments?.getString("id"))
        }




    }
    navHostController = navController;

}
