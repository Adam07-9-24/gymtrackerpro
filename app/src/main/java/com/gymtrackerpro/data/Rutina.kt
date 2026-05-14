package com.gymtrackerpro.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rutinas")
data class Rutina(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombreRutina: String,
    val descripcion: String,
    val usuarioId: Int
)
