package com.example.resq.scannerpage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resq.R

@Composable()
@Preview
fun Scannerpage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFE9FDF1))
    ) {

        Row {

            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(top = 8.dp, start = 6.dp)
            )
            Text(
                text = "ResQ ",
                color = Color(0xFF008C3D),
                modifier = Modifier
                    .padding(top = 12.dp), fontSize = 18.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))

            Text(
                "exit button ", modifier = Modifier
                    .padding(top = 12.dp, end = 16.dp)
                    .clickable {
                        //yha apr action likhna baaki h jo back jaaye login pr woh bhi responder k liye h
                    }, fontSize = 16.sp
            )

        }

        Box() {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Enter the code") },
                modifier = Modifier
                    .fillMaxWidth()

                    .padding(16.dp)
                    .background(Color.White)
                    .height(56.dp),
                textStyle = TextStyle(fontSize = 14.sp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Emergency  Scanner",
            modifier = Modifier,
            fontSize = (24.sp),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.SansSerif,
        )

    }
}

