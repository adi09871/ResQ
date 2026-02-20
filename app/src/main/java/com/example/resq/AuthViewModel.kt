package com.example.resq

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.com.example.resq.Doctor
import com.example.resq.com.example.resq.RetrofitClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authstate = MutableLiveData<Authstate>()
    val authstate: LiveData<Authstate> = _authstate

    var loggedInResponderID = mutableStateOf("")
    var loginSuccess = mutableStateOf(false)

    var doctorsList = mutableStateOf<List<Doctor>>(emptyList())
    var subscribedDoctor = mutableStateOf<Doctor?>(null)
    var isCheckingSubscription = mutableStateOf(true)

    init {
        if (auth.currentUser != null) {
            _authstate.value = Authstate.Authenticated
        } else {
            _authstate.value = Authstate.Unauthenticated
        }
    }

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authstate.value = Authstate.Error("Email or password can't be empty")
            return
        }
        _authstate.value = Authstate.Loading
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) _authstate.value = Authstate.Authenticated
            else _authstate.value = Authstate.Error(task.exception?.message ?: "Login failed")
        }
    }

    fun signup(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authstate.value = Authstate.Error("Email or password can't be empty")
            return
        }
        _authstate.value = Authstate.Loading
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) _authstate.value = Authstate.Authenticated
            else _authstate.value = Authstate.Error(task.exception?.message ?: "Signup failed")
        }
    }

    fun signout() {
        auth.signOut()
        _authstate.value = Authstate.Unauthenticated
        loggedInResponderID.value = ""
        loginSuccess.value = false
    }

    fun accessSystem(id: String, pass: String) {
        if (id == "admin" && pass == "1234") {
            loginSuccess.value = true
            loggedInResponderID.value = id
        } else {
            loginSuccess.value = false
            _authstate.value = Authstate.Error("Invalid Responder ID or Password")
        }
    }

    fun saveMedicalInfo(
        fullName: String,
        bloodGroup: String,
        allergies: String,
        contact1: String,
        contact2: String,
        medicalNotes: String,
        insuranceProvider: String,
        policyNumber: String,
        onResult: (Boolean, String) -> Unit
    ) {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            onResult(false, "User not logged in")
            return
        }

        val info = MedicalInfo(
            fullName, bloodGroup, allergies, contact1, contact2, medicalNotes, insuranceProvider, policyNumber
        )

        FirebaseDatabase.getInstance().getReference("medical_info").child(uid)
            .setValue(info)
            .addOnSuccessListener { onResult(true, "Profile Saved Successfully") }
            .addOnFailureListener { onResult(false, it.message ?: "Failed to save data") }
    }

    fun uploadReport(reportName: String, fileUri: Uri, onResult: (Boolean, String) -> Unit) {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            onResult(false, "User not logged in")
            return
        }

        val fileName = "reports/${uid}/${System.currentTimeMillis()}_${reportName}"
        val storageRef = FirebaseStorage.getInstance().reference.child(fileName)

        storageRef.putFile(fileUri).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                val report = Report(
                    System.currentTimeMillis().toString(), reportName, uri.toString(),
                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
                )
                FirebaseDatabase.getInstance().getReference("users").child(uid).child("reports").push().setValue(report)
                    .addOnSuccessListener { onResult(true, "Report Uploaded!") }
                    .addOnFailureListener { onResult(false, "DB Error: ${it.message}") }
            }
        }.addOnFailureListener { onResult(false, "Upload Failed: ${it.message}") }
    }

    fun fetchAvailableDoctors() {
        viewModelScope.launch {
            try {
                val fetchedDoctors = RetrofitClient.api.getDoctors()
                doctorsList.value = fetchedDoctors
            } catch (e: Exception) {
                e.printStackTrace()
                doctorsList.value = emptyList()
            }
        }
    }

    fun checkSubscription() {
        val uid = auth.currentUser?.uid ?: return
        isCheckingSubscription.value = true

        FirebaseDatabase.getInstance().getReference("users").child(uid).child("subscription").get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val doctorId = snapshot.child("doctorId").value.toString()
                viewModelScope.launch {
                    try {
                        val allDocs = RetrofitClient.api.getDoctors()
                        subscribedDoctor.value = allDocs.find { it.id == doctorId }
                        isCheckingSubscription.value = false
                    } catch (e: Exception) {
                        isCheckingSubscription.value = false
                    }
                }
            } else {
                subscribedDoctor.value = null
                isCheckingSubscription.value = false
                fetchAvailableDoctors()
            }
        }.addOnFailureListener { isCheckingSubscription.value = false }
    }

    fun subscribeToDoctor(doctor: Doctor, onResult: (Boolean, String) -> Unit) {
        val uid = auth.currentUser?.uid ?: return

        val subData = mapOf(
            "doctorId" to doctor.id, "status" to "Active",
            "date" to SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        )

        FirebaseDatabase.getInstance().getReference("users").child(uid).child("subscription").setValue(subData)
            .addOnSuccessListener {
                subscribedDoctor.value = doctor
                onResult(true, "Successfully Subscribed to ${doctor.name}")
            }
            .addOnFailureListener { onResult(false, "Subscription Failed") }
    }

    fun saveVitalsAndNotify(bp: String, sugar: String, heartRate: String, doctor: Doctor, onResult: (Boolean, String) -> Unit) {
        val uid = auth.currentUser?.uid ?: return

        val vital = VitalSign(
            SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date()), bp, sugar, heartRate
        )

        FirebaseDatabase.getInstance().getReference("users").child(uid).child("vitals").push().setValue(vital)
            .addOnSuccessListener {
                println(">>> SENT EMAIL TO: ${doctor.email} FOR PATIENT VITALS <<<")
                onResult(true, "Vitals Sent to ${doctor.name}!")
            }
            .addOnFailureListener { onResult(false, "Failed: ${it.message}") }
    }
}

sealed class Authstate {
    object Authenticated : Authstate()
    object Unauthenticated : Authstate()
    object Loading : Authstate()
    data class Error(val message: String) : Authstate()
}

