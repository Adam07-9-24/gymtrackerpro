package com.gymtrackerpro.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDao {
    @Insert
    suspend fun registrar(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE nombreUsuario = :username LIMIT 1")
    suspend fun buscarPorNombreUsuario(username: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun buscarPorEmail(email: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE nombreUsuario = :username AND contrasena = :pass LIMIT 1")
    suspend fun login(username: String, pass: String): Usuario?
}
