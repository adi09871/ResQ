package com.example.resq

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.resq.loginscreen.Loginscreen
import com.example.resq.medicaldetailspage.Medicaldetails
import com.example.resq.ui.theme.ResQTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Medicaldetails()

                Loginscreen()



            }
        }
    }
