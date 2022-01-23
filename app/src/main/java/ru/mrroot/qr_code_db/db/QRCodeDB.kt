package ru.mrroot.qr_code_db.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [QRCode::class], version = 1, exportSchema = false)
abstract class QRCodeDB : RoomDatabase() {
    abstract fun getQRDao() : QRCodeDao

    companion object {
        private const val DB_NAME = "QRCodeDatabase"
        private var qrCodeDB: QRCodeDB? = null
        fun getAppDatabase(context: Context): QRCodeDB? {
            if (qrCodeDB == null) {
                qrCodeDB = Room.databaseBuilder(context.applicationContext,
                    QRCodeDB::class.java, DB_NAME).allowMainThreadQueries().build()
            }
            return qrCodeDB
        }

        fun destroyInstance() {
            qrCodeDB = null
        }
    }
}