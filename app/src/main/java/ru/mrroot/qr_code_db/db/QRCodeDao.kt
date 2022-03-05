package ru.mrroot.qr_code_db.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QRCodeDao {
    @Query("SELECT * FROM QRCode ORDER BY dateAdded DESC")
    fun getAllQRCodes(): List<QRCode>

    @Query("SELECT * FROM QRCode WHERE favourite = 1 ORDER BY dateAdded DESC")
    fun getAllFavourites(): List<QRCode>

    @Query("SELECT * FROM QRCode WHERE qrCodeType = :qrCodeType")
    fun getAllQRCodesByType(qrCodeType: Int): List<QRCode>

    @Query("SELECT * FROM QRCode WHERE favourite = 1 AND qrCodeType = :qrCodeType")
    fun getAllFavouritesByType(qrCodeType: Int): List<QRCode>

    @Query("DELETE FROM QRCode")
    fun deleteAllQRCodes()

    @Query("DELETE FROM QRCode WHERE favourite = 1")
    fun deleteAllFavourites()

    @Query("DELETE FROM QRCode WHERE id = :id")
    fun deleteQRCode(id: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQRCode(qrCode: QRCode): Long

    @Query("SELECT * FROM QRCode WHERE id = :id")
    fun getQRCode(id: Int): QRCode

    @Query("UPDATE QRCode SET favourite = 1 WHERE id = :id")
    fun addToFavourites(id: Int): Int

    @Query("UPDATE QRCode SET favourite = 0 WHERE id = :id")
    fun removeFromFavourites(id: Int): Int

    @Query("SELECT * FROM QRCode WHERE title = :title ")
    fun checkIfQRCodeExists(title: String): Int
}