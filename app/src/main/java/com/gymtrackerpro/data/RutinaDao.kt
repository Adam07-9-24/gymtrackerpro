package com.gymtrackerpro.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RutinaDao {

    @Insert
    suspend fun insertar(rutina: Rutina)

    @Query("SELECT * FROM rutinas WHERE usuario_id = :usuarioId ORDER BY id DESC")
    suspend fun listarPorUsuario(usuarioId: Int): List<Rutina>

    @Query("SELECT * FROM rutinas WHERE id = :rutinaId LIMIT 1")
    suspend fun buscarPorId(rutinaId: Int): Rutina?

    @Update
    suspend fun actualizar(rutina: Rutina)

    @Delete
    suspend fun eliminar(rutina: Rutina)

    @Query("SELECT COUNT(*) FROM rutinas WHERE usuario_id = :usuarioId")
    suspend fun contarRutinas(usuarioId: Int): Int

    @Query("SELECT IFNULL(SUM(peso_kg * series * repeticiones), 0) FROM rutinas WHERE usuario_id = :usuarioId")
    suspend fun calcularVolumenTotal(usuarioId: Int): Double
}