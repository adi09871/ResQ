package com.example.resq.loginscreen

import android.R.attr.text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resq.R

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.resq.ui.theme.pink1


@Preview
@Composable
fun Loginscreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = pink1)
            .padding(bottom = 300.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(72.dp)

        )
        Text(
            text = "ResQ", fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = Color(0xFFE50914),
            fontFamily = FontFamily.SansSerif,
        )
        Text(
            text = "Emergency Medical QR System ",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))
        // Box h ye login wala
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(250.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp)
                ),

            ) {
            Text(
                text = "Sign In",
                modifier = Modifier.fillMaxSize(),
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Email",
                modifier = Modifier
                    .padding(top = 35.dp)
                    .offset(x = 30.dp),
                fontWeight = FontWeight.Bold


                var password by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Enter your password", fontSize = 14.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 40.dp),
                    textStyle = TextStyle(fontSize = 14.sp)
                )
            }
        }
    }
}