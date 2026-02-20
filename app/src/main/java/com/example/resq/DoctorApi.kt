package com.example.resq.com.example.resq


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// --- NEW CHANGES: Doctor Data Class representing your JSON ---
data class Doctor(
    val id: String = "",
    val name: String = "",
    val specialty: String = "",
    val hospital: String = "",
    val email: String = "",
    val fee: String = "",
    val experience: String = ""
)

// --- NEW CHANGES: Interface handling the GET request ---
interface DoctorApiService {
    // Tumhara Gist RAW endpoint bina aage '/' lagaye
    @GET("adi09871/94c203b08535dd34724ae8ff70d7ad4a/raw/")
    suspend fun getDoctors(): List<Doctor>
}

// --- NEW CHANGES: Singleton object setup for Retrofit ---
object RetrofitClient {
    private const val BASE_URL = "https://gist.githubusercontent.com/"

    val api: DoctorApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DoctorApiService::class.java)
    }
}