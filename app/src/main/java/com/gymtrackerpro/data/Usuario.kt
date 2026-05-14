package com.gymtrackerpro.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombreCompleto: String,
    val nombreUsuario: String,
    val email: String,
    val edad: Int,
    val contrasena: String
)
