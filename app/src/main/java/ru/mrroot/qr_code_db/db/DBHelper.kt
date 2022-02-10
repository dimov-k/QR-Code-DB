package ru.mrroot.qr_code_db.db

import ru.mrroot.qr_code_db.QRCodeTypes
import java.util.*

class DBHelper(var qrCodeDB: QRCodeDB) : DBHelperImpl {
    override fun insertQRCode(title: String, qrCodeValue: String, favourite: Boolean): Int {
        val dateAdded = Calendar.getInstance()
        val qrCodeType = determineQRCodeType(qrCodeValue)
        val qrCode = QRCode(
            title = title,
            qrCodeValue = qrCodeValue,
            qrCodeType = qrCodeType,
            favourite = favourite,
            dateAdded = dateAdded
        )
        return qrCodeDB.getQRDao().insertQRCode(qrCode).toInt()
    }

    override fun getQRCode(id: Int): QRCode {
        return qrCodeDB.getQRDao().getQRCode(id)
    }

    override fun addToFavourites(id: Int): Int {
        return qrCodeDB.getQRDao().addToFavourites(id)
    }

    override fun removeFromFavourites(id: Int): Int {
        return qrCodeDB.getQRDao().removeFromFavourites(id)
    }

    override fun deleteQRCode(id: Int): Int {
        return qrCodeDB.getQRDao().deleteQRCode(id)
    }

    override fun getAllQRCodes(): List<QRCode> {
        return qrCodeDB.getQRDao().getAllQRCodes()
    }

    override fun getAllFavourites(): List<QRCode> {
        return qrCodeDB.getQRDao().getAllFavourites()
    }

    override fun deleteAllQRCodes() {
        return qrCodeDB.getQRDao().deleteAllQRCodes()
    }

    override fun deleteAllFavourites() {
        return qrCodeDB.getQRDao().deleteAllFavourites()
    }


    private fun determineQRCodeType(qrCodeValue: String) : Int {
        with (qrCodeValue) {
            return when {
                startsWith("http://") -> QRCodeTypes.URL
                startsWith("https://") -> QRCodeTypes.URL
                startsWith("mailto:") -> QRCodeTypes.EMAIL
                startsWith("tel:") -> QRCodeTypes.TELEPHONE_NUMBER
                startsWith("BEGIN:VCARD") -> QRCodeTypes.CONTACT
                else -> QRCodeTypes.UNDEFINED
            }
        }
    }
}