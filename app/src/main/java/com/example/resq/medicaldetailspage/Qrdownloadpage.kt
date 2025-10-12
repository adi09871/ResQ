package com.example.resq.medicaldetailspage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.resq.AuthViewModel
import com.example.resq.R
import com.example.resq.ui.theme.pink1

@Composable
fun Qrdownloadpage(
    modifier: Modifier.Companion,
    navController: NavHostController,
    authviewmodel: AuthViewModel
)  {
    Column(
        modifier = modifier
            .background(color = pink1)
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = pink1)
                .padding(top = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    tint = Color(0xFFE50914),
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

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
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopEnd)
            )
        }



        Text(
            text = "Your Emergency QR Code",
            color = Color.Black,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = "Ready for emergency situations",
            color = Color.Black,
            fontSize = 15.sp,

            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )

        Box( modifier = Modifier
            .padding(18.dp)
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color(0xFF008C3D),
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp))
            ) {

            Text(
                "QR Code Image Placeholder",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(100.dp)
            )
        }

    }
}
