package ru.mrroot.qr_code_db.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QRCodeTypeDao {
    @Query("SELECT * FROM QRCodeType")
    fun getAllQRCodeTypes(): List<QRCodeType>

    @Query("DELETE FROM QRCodeType")
    fun deleteAllQRCodeTypes()

    @Query("DELETE FROM QRCodeType WHERE id = :id")
    fun deleteQRCodeType(id: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQRCodeType(qrCodeType: QRCodeType): Long

    @Query("SELECT * FROM QRCodeType WHERE id = :id")
    fun getQRCodeTypeById(id: Int): QRCodeType

    @Insert
    fun initializeQRCodeTypeDB(qrCodeTypes: List<QRCodeType>)

    @Query("SELECT * FROM QRCodeType WHERE qrCodeTypeName = :qrCodeTypeName")
    fun getQRCodeTypeIDByString(qrCodeTypeName: String): Int
}