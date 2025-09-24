package com.example.resq.responderhome

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.resq.AuthViewModel

@Composable
fun Responderhome(
    modifier: Modifier,
    navController: NavController,
    authviewmodel: AuthViewModel,

    ) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFE9FDF1))
    ) {
        Row(modifier = Modifier.padding(top = 25.dp, start = 16.dp)) {
            Image(
                painter = painterResource(com.example.resq.R.drawable.logo),
                modifier  = modifier.size(30.dp)
                      , contentDescription = null,
                colorFilter = ColorFilter.tint(Color(0xFF008C3D))
            )


            Text(
                text = "ResQ Responder",
                fontSize = 24.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        }
    }

}