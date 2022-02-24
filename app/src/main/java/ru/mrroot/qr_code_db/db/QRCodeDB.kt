package ru.mrroot.qr_code_db.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.mrroot.qr_code_db.db.QRCodeDao
import ru.mrroot.qr_code_db.db.QRCode

@Database(
    version = 2,
    entities = [QRCode::class, QRCodeType::class],
    autoMigrations = [AutoMigration(from = 1, to = 2)],
    exportSchema = true
)
abstract class QRCodeDB : RoomDatabase() {
    abstract fun getQRDao(): QRCodeDao
    abstract fun getQRTypeDao(): QRCodeTypeDao

    companion object {
        private const val DB_NAME = "QRCodeDatabase"
        private var qrCodeDB: QRCodeDB? = null
        fun getAppDatabase(context: Context): QRCodeDB? {
            if (qrCodeDB == null) {
                qrCodeDB = Room.databaseBuilder(
                    context.applicationContext,
                    QRCodeDB::class.java, DB_NAME
                ).allowMainThreadQueries().build()
            }
            return qrCodeDB
        }

        fun destroyInstance() {
            qrCodeDB = null
        }
    }
}