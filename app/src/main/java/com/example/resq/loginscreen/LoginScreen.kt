package com.example.resq.loginscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun loginscreen(){
    Column (){


        Text(text = "ResQ", modifier = Modifier.fillMaxSize(),
            fontSize = 16.sp)
    }
}