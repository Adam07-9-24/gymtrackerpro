package com.gymtrackerpro.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RutinaDao {
    @Insert
    suspend fun insertar(rutina: Rutina)

    @Query("SELECT * FROM rutinas WHERE usuarioId = :uId")
    suspend fun obtenerRutinasPorUsuario(uId: Int): List<Rutina>
}
