package com.example.resq.medicaldetailspage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resq.R
import com.example.resq.ui.theme.pink1

@Preview
@Composable
fun Qrdownloadpage(
    modifier: Modifier.Companion,
    navController: NavHostController,
    authviewmodel: AuthViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = pink1)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.align(Alignment.TopStart)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                tint = Color(0xFFE50914),
                modifier = modifier.size(28.dp)
            )

            Spacer(modifier = modifier.width(8.dp))

            Text(
                text = "ResQ",
                fontSize = 24.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE50914)
            )
        }


        Icon(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile Icon",
            modifier = modifier
                .size(28.dp)
                .align(Alignment.TopEnd)
        )
    }
}