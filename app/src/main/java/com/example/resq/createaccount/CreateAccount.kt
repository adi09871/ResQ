package com.example.resq.createaccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resq.R
import com.example.resq.ui.theme.pink1

@Composable
@Preview
fun CreateAccount() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = pink1)
            .padding(bottom = 300.dp),


        ) {


        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = { }) {
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
                color = Color(0xFFE50914),
                modifier = Modifier
                    .padding(top = 12.dp)
                    .align(Alignment.CenterVertically),
                fontSize = 18.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )

        }


        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center) {

            Column(
                modifier = Modifier.fillMaxWidth(0.9f). fillMaxWidth(0.9f)
                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {

                Text(
                    text = "Create Account",
                    fontSize = 20.sp, fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                // full name  Label
                Text(
                    text = "Email", fontWeight = FontWeight.Bold
                )

                var email by remember { mutableStateOf("") }
                OutlinedTextField(
                    value =fullname,
                    onValueChange = { fullname = it },
                    placeholder = { Text("Enter your full name ", fontSize = 14.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 40.dp),
                    textStyle = TextStyle(fontSize = 14.sp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password Label
                Text(
                    text = "Phone number ", fontWeight = FontWeight.Bold
                )

                var password by remember { mutableStateOf("") }
                OutlinedTextField(
                    value =phonenumber,
                    onValueChange = {phonenumber = it },
                    placeholder = { Text("Enter your phone number ", fontSize = 14.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 40.dp),
                    textStyle = TextStyle(fontSize = 14.sp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // email Label
                Text(
                    text = "Email ", fontWeight = FontWeight.Bold
                )

                var email by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = email   ,
                    onValueChange = { email  = it },
                    placeholder = { Text("  Enter your  email", fontSize = 14.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 40.dp),
                    textStyle = TextStyle(fontSize = 14.sp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Password Label
                Text(
                    text = "Password", fontWeight = FontWeight.Bold
                )

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

                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White, containerColor = Color(0xFFE50914)
                    )
                ) {
                    Text(text = "Login")
                }
            }
        }
    }}