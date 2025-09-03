package com.example.resq.responderloginscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resq.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = false, showSystemUi = false,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
fun Responderloginscreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFE9FDF1))
    ) {

        // Top Row (Back button + Title)
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
                color = Color(0xFF008C3D),
                modifier = Modifier
                    .padding(top = 12.dp)
                    .align(Alignment.CenterVertically),
                fontSize = 18.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        }

        // Image below Row
        Column(
            modifier = Modifier.fillMaxWidth()

        Row(modifier = Modifier) {
            Text(text = "ResQ Responder", color = Color.White)
  }


    }
}