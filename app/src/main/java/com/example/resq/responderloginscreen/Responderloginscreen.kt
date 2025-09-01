package com.example.resq.responderloginscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = false, showSystemUi = false,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
fun Responderloginscreen() {


    Column(modifier = Modifier.fillMaxSize()) {


        Row(modifier = Modifier) {
            Text(text = "ResQ Responder", color = Color.White)
  }


    }
}