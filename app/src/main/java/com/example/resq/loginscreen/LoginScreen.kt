package com.example.resq.loginscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.resq.AuthViewModel
import com.example.resq.R
import com.example.resq.ui.theme.pink1


@Composable
fun Loginscreen(modifier: Modifier,navController: NavController,authviewmodel: AuthViewModel) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = pink1)
            .padding(bottom = 300.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "App Logo",
            modifier = modifier.size(72.dp)

        )
        Text(
            text = "ResQ", fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = Color(0xFFE50914),
            fontFamily = FontFamily.SansSerif,
        )
        Text(
            text = "Emergency Medical QR System ", fontWeight = FontWeight.Bold
        )

        Spacer(modifier = modifier.height(10.dp))
        // Box h ye login wala
        Box(
            modifier = modifier
                .fillMaxWidth(0.9f)
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Column(
                modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = "Sign In",
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center,
                    modifier = modifier.fillMaxWidth()
                )

                Spacer(modifier = modifier.height(20.dp))

                // Email Label
                Text(
                    text = "Email", fontWeight = FontWeight.Bold
                )

                var email by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Enter your email", fontSize = 14.sp) },
                    modifier = modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 40.dp),
                    textStyle = TextStyle(fontSize = 14.sp)
                )

                Spacer(modifier = modifier.height(16.dp))

                // Password Label
                Text(
                    text = "Password", fontWeight = FontWeight.Bold
                )

                var password by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Enter your password", fontSize = 14.sp) },
                    modifier = modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 40.dp),
                    textStyle = TextStyle(fontSize = 14.sp)
                )

                Button(
                    onClick = {
                        authviewmodel.login(email, password)

                        navController.navigate("Medicaldetails") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    modifier = modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White, containerColor = Color(0xFFE50914)
                    )
                ) {
                    Text(text = "Login")
                }
            }
        }
        Spacer(modifier = modifier.height(10.dp))


        Text(text = "Don't have acoount?")
        TextButton(onClick ={} ) {
        Text(text = "Sign up ", fontSize = 16.sp, color = Color(0xFFE50914))}

        Button(
            onClick = {},
            modifier = modifier.fillMaxWidth(0.6f),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White, contentColor = Color.Black,
            )
        ) {
            Image(
                painter = painterResource(R.drawable.shieldicon),
                contentDescription = "icon",
                modifier.size(16.dp)
            )
            Spacer(modifier = modifier.width(4.dp))
            Text(text = "Authorized Responder")
        }


    }
}


