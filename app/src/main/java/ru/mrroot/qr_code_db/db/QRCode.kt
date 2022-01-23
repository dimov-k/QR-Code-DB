package ru.mrroot.qr_code_db.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.mrroot.qr_code_db.QRCodeTypes
import ru.mrroot.qr_code_db.utils.DateTimeConverters
import java.util.*

@Entity
@TypeConverters(DateTimeConverters::class)
data class QRCode (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "Title")
    val title: String?,

    @ColumnInfo(name = "QRCodeValue")
    val qrCodeValue: String?,

    @ColumnInfo(name = "QRCodeType")
    val qrCodeType: Int? = QRCodeTypes.UNDEFINED,

    @ColumnInfo(name = "Favourite")
    val favourite: Boolean = false,

    @ColumnInfo(name = "DateAdded")
    val dateAdded: Calendar
)