package com.example.resq.responderhome

import android.R
import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.resq.AuthViewModel
import com.google.firebase.annotations.concurrent.Background

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
        Row(
            modifier = Modifier.padding(top = 22.dp, start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(com.example.resq.R.drawable.logo),
                modifier = modifier.size(30.dp), contentDescription = null,
                colorFilter = ColorFilter.tint(Color(0xFF008C3D))
            )


            Text(
                text = "ResQ Responder",
                fontSize = 24.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = { /* TODO: action */ }
            ) {
                Icon(
                    painter = painterResource(id = com.example.resq.R.drawable.exitlogo),
                    contentDescription = "Responder Logo",
                    tint = Color(0xFF008C3D),

                )
            }

        }
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .background(
                    color = Color(0xFFE9FDF1),
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 1.dp,
                    color = Color(0xFF00C853),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp) // inner padding
        ) {

            Text(
                text = "Hello World",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }

}
