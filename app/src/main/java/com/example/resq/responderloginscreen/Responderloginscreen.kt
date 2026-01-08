package com.example.resq.responderloginscreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.resq.AuthViewModel
import com.example.resq.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Responderloginscreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authviewmodel: AuthViewModel
) {
    val context = LocalContext.current


    LaunchedEffect(authviewmodel.loginSuccess.value) {
        if (authviewmodel.loginSuccess.value) {

            navController.navigate("responderhome") {
                popUpTo("responderlogin") { inclusive = true }
            }
            authviewmodel.loginSuccess.value = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFE9FDF1))
    ) {

        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.backarrow),
                    contentDescription = "back",
                    tint = Color.Black,
                    modifier = Modifier.padding(top = 4.dp, start = 12.dp)
                )
            }
            Text(
                text = "Back",
                modifier = Modifier.padding(top = 16.dp),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(50.dp))
            Text(
                text = "ResQ Responder",
                color = Color(0xFF008C3D),
                modifier = Modifier
                    .padding(top = 12.dp)
                    .align(Alignment.CenterVertically),
                fontSize = 18.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(72.dp)
                    .align(Alignment.CenterHorizontally),
                colorFilter = ColorFilter.tint(Color(0xFF008C3D))
            )
            Text(
                text = "ResQ", fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color(0xFF008C3D),
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Emergency Personnel Only", fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally), fontSize = 15.sp
            )
            Text(
                "(Access Restricted to authorized personnel only)", fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 10.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Sign In",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.SansSerif,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(text = "Responder ID", fontWeight = FontWeight.Bold)
                    var responderID by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = responderID,
                        onValueChange = { responderID = it },
                        placeholder = { Text("Enter ID (admin)", fontSize = 14.sp) },
                        modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 40.dp),
                        textStyle = TextStyle(fontSize = 14.sp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Password", fontWeight = FontWeight.Bold)
                    var password by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text("Enter Password (1234)", fontSize = 14.sp) },
                        modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 40.dp),
                        textStyle = TextStyle(fontSize = 14.sp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (responderID.isEmpty() || password.isEmpty()) {
                                Toast.makeText(context, "Please enter ID and Password", Toast.LENGTH_SHORT).show()
                            } else {
                                // ✅ FIX: Calling the correct function
                                authviewmodel.accessSystem(responderID, password)

                                // Feedback agar login fail ho gaya (ViewModel check karega)
                                if (!authviewmodel.loginSuccess.value && responderID.isNotEmpty()) {
                                    Toast.makeText(context, "Wrong ID or Password", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White, containerColor = Color(0xFF008C3D)
                        )
                    ) {
                        Text("Access System")
                    }
                }
            }
        }
    }
}