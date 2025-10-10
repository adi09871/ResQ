package com.example.resq.medicaldetailspage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.resq.R
@Preview
@Composable
fun Qrdownloadpage() {



    Column ()

    {
        Row {
            Icon(painter = painterResource(R.drawable.logo),
                contentDescription = null, modifier = Modifier.size(15.dp))
        }
    }
}