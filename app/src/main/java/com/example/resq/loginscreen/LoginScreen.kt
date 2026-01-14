package com.example.resq.loginscreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.resq.AuthViewModel
import com.example.resq.Authstate
import com.example.resq.R
import com.example.resq.ui.theme.pink1

@Composable
fun Loginscreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authviewmodel: AuthViewModel
) {
    val authState by authviewmodel.authstate.observeAsState()
    val context = LocalContext.current

    // ✅ DIRECT CHECK: isLoading variable ki zaroorat nahi, authState se hi check karlo
    val isLoading = authState is Authstate.Loading

    // Navigation & Error Handling Effect
    LaunchedEffect(authState) {
        when (authState) {
            is Authstate.Authenticated -> {
                navController.navigate("medicaldetails") {
                    popUpTo("login") { inclusive = true }
                }
            }
            is Authstate.Error -> {
                val errorMsg = (authState as Authstate.Error).message
                Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = pink1)
            .verticalScroll(rememberScrollState()),
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

                Text(text = "Email", fontWeight = FontWeight.Bold)
                var email by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Enter your email", fontSize = 14.sp) },
                    modifier = modifier.fillMaxWidth(),
                    textStyle = TextStyle(fontSize = 14.sp)
                )

                Spacer(modifier = modifier.height(16.dp))

                Text(text = "Password", fontWeight = FontWeight.Bold)
                var password by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Enter your password", fontSize = 14.sp) },
                    modifier = modifier.fillMaxWidth(),
                    textStyle = TextStyle(fontSize = 14.sp)
                )

                Spacer(modifier = modifier.height(16.dp))

                Button(
                    onClick = {
                        authviewmodel.login(email.trim(), password.trim())
                    },
                    modifier = modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White, containerColor = Color(0xFFE50914)
                    ),
                    enabled = !isLoading // Loading ke waqt button disable
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(text = "Login")
                    }
                }
            }
        }

        // ... Baaki code same (Signup & Responder Button) ...
        Spacer(modifier = modifier.height(10.dp))
        Text(text = "Don't have acoount?")
        TextButton(
            onClick = { navController.navigate("signup") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ){
            Text(text = "Sign up ", fontSize = 16.sp, color = Color(0xFFE50914))
        }

        Button(
            onClick = { navController.navigate("responderlogin") },
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