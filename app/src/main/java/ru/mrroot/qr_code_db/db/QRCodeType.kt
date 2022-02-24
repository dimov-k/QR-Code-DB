package ru.mrroot.qr_code_db.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.mrroot.qr_code_db.QRCodeTypes

@Entity
data class QRCodeType(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "QRCodeTypeName")
    var qrCodeTypeName: String?,

    @ColumnInfo(name = "QRCodeType")
    var qrCodeType: Int? = QRCodeTypes.UNDEFINED,
)