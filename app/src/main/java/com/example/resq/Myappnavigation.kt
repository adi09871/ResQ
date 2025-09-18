package com.example.resq

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.resq.loginscreen.Loginscreen

@Composable
fun MyAppNavigation(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel   // 👈 class ka naam wahi likho jo file me hai
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"   // 👈 case same rakho
    ) {
        composable("login") {
            Loginscreen(
                modifier = Modifier,
                navController = navController,
                authviewmodel = authViewModel   // 👈 yaha bhi same naam
            )
        }
    }
}
