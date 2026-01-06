package com.example.resq

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.resq.createaccount.CreateAccount
import com.example.resq.loginscreen.Loginscreen
import com.example.resq.medicaldetailspage.Medicaldetails
import com.example.resq.medicaldetailspage.Qrdownloadpage

import com.example.resq.responderhome.Responderhome
import com.example.resq.responderloginscreen.Responderloginscreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyAppNavigation(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier.fillMaxSize()
    ) {
        composable("login") {
            Loginscreen(
                modifier = Modifier,
                navController = navController,
                authviewmodel = authViewModel
            )
        }
        composable("signup") {
            CreateAccount(
                modifier = Modifier,
                navController = navController,
                authviewmodel = authViewModel
            )
        }
        composable("respoder") {
            Responderloginscreen(
                modifier = Modifier,
                navController = navController,
                authviewmodel = authViewModel
            )
        }
        composable("medicaldetails") {
            Medicaldetails(
                modifier = Modifier,
                navController = navController,
                authviewmodel = authViewModel
            )
        }
        composable("responderlogin") {
            Responderloginscreen(
                modifier = Modifier,
                navController = navController,
                authviewmodel = authViewModel
            )
        }
        composable("responderhome") {
            Responderhome(
                modifier = Modifier,
                navController = navController,
                authviewmodel = authViewModel
            )
        }

        composable("qrdownloadpage") {
            Qrdownloadpage(
                modifier = Modifier,
                navController = navController,
                authviewmodel = authViewModel
            )
        }

        composable("qrpage") {
            Qrdownloadpage(
                navController = navController,
                authviewmodel = authViewModel
            )
        }
    }
}