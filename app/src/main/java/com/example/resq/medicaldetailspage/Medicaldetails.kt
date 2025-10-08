package com.example.resq.medicaldetailspage


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.resq.AuthViewModel
import com.example.resq.MedicalInfo
import com.example.resq.ui.theme.pink1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun Medicaldetails(modifier: Modifier,navController: NavController,authviewmodel: AuthViewModel) {
    BackHandler {
        navController.navigate("login") {
            popUpTo("medicaldetails") { inclusive = true }
            launchSingleTop = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = pink1)

    ) {

        // 🔙 Top Row (Back Button + Title)
        Row(modifier = Modifier.fillMaxWidth()) {

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
                    text = "Emergency Information",
                    fontSize = 20.sp, fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                // full name  Label
                Text(
                    text = "Full name ", fontWeight = FontWeight.Bold
                )

                var fullname by remember { mutableStateOf("") }
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

                // blood group  Label
                Text(
                    text = "Blood Group ", fontWeight = FontWeight.Bold
                )

                var blodgroup by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = blodgroup,
                    onValueChange = {blodgroup = it },
                    placeholder = { Text(" Enter your blood group ", fontSize = 14.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 30.dp),
                    textStyle = TextStyle(fontSize = 14.sp)
                )
                Spacer(modifier = Modifier.height(16.dp))

            //allergies label
                Text(
                    text = "Allergies ", fontWeight = FontWeight.Bold
                )

                var allergies by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = allergies  ,
                    onValueChange = { allergies  = it },
                    placeholder = { Text(" List any allergies (medical,food,etc.)", fontSize = 14.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 40.dp),
                    textStyle = TextStyle(fontSize = 14.sp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // emrgency contact 1 Label
                Text(
                    text = "Emergency Contact 1", fontWeight = FontWeight.Bold
                )

                var conatct1 by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = conatct1,
                    onValueChange = { conatct1 = it },
                    placeholder = { Text("Name and phone number", fontSize = 14.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 40.dp),
                    textStyle = TextStyle(fontSize = 14.sp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // emergency contact 2 Label
                Text(
                    text = "Emergency Contact 2 ", fontWeight = FontWeight.Bold
                )

                var email by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = email   ,
                    onValueChange = { email  = it },
                    placeholder = { Text("Name and phone number", fontSize = 14.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 40.dp),
                    textStyle = TextStyle(fontSize = 14.sp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // MEDICAL NOTES ka Label
                Text(
                    text = "Medical notes  ", fontWeight = FontWeight.Bold
                )

                var medicalnotes  by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = medicalnotes    ,
                    onValueChange = { medicalnotes = it },
                    placeholder = { Text("Important Medical Condition,medication,etc", fontSize = 14.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 40.dp),
                    textStyle = TextStyle(fontSize = 14.sp)
                )
                var message by remember { mutableStateOf("") }


                Button(
                    onClick = {
                        val uid = FirebaseAuth.getInstance().currentUser?.uid
                        if (uid != null) {
                            val MedicalInfo = MedicalInfo(
                                fullName = fullname,
                                bloodGroup = blodgroup,
                                allergies = allergies,
                                contact1 = conatct1,
                                contact2 = conatct1,
                                medicalNotes = medicalnotes
                            )

                            val dbRef = FirebaseDatabase.getInstance()
                                .getReference("medical_info")
                                .child(uid)

                            CoroutineScope(Dispatchers.IO).launch {
                                dbRef.setValue(MedicalInfo)
                                    .addOnSuccessListener {
                                        message = "✅ Data saved successfully!"
                                    }
                                    .addOnFailureListener {
                                        message = "❌ Failed to save: ${it.message}"
                                    }
                            }
                        } else {
                            message = "User not logged in!"
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE50914),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Save Medical Information")
                }

                if (message.isNotEmpty()) {
                    Spacer(Modifier.height(12.dp))
                    Text(text = message, color = Color.Black, fontSize = 14.sp)
                }
            }
        }
            }
        }
