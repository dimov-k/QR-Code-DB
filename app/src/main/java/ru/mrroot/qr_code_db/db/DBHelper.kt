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

    override fun editQRCode(qrCode: QRCode) {
        qrCodeDB.getQRDao().insertQRCode(qrCode)
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

    override fun getAllQRCodesByType(qrCodeType: Int): List<QRCode> {
        return qrCodeDB.getQRDao().getAllQRCodesByType(qrCodeType)
    }

    override fun getAllFavouritesByType(qrCodeType: Int): List<QRCode> {
        return qrCodeDB.getQRDao().getAllQRCodesByType(qrCodeType)
    }

    override fun deleteAllQRCodes() {
        return qrCodeDB.getQRDao().deleteAllQRCodes()
    }

    override fun deleteAllFavourites() {
        return qrCodeDB.getQRDao().deleteAllFavourites()
    }

    override fun deleteAllQRCodeTypes() {
        return qrCodeDB.getQRTypeDao().deleteAllQRCodeTypes()
    }

    override fun getAllQRCodeTypes(): List<QRCodeType> {
        return qrCodeDB.getQRTypeDao().getAllQRCodeTypes()
    }

    override fun deleteQRCodeType(id: Int): Int {
        return qrCodeDB.getQRTypeDao().deleteQRCodeType(id)
    }

    override fun insertQRCodeType(qrCodeType: QRCodeType): Long {
        return qrCodeDB.getQRTypeDao().insertQRCodeType(qrCodeType)
    }

    override fun getQRCodeTypeById(id: Int): QRCodeType {
        return qrCodeDB.getQRTypeDao().getQRCodeTypeById(id)
    }

    override fun initializeQRCodeTypeDB(qrCodeTypes: List<QRCodeType>) {
        return qrCodeDB.getQRTypeDao().initializeQRCodeTypeDB(qrCodeTypes)
    }

    override fun getQRCodeTypeIDByString(qrCodeTypeName: String): Int {
        return qrCodeDB.getQRTypeDao().getQRCodeTypeIDByString(qrCodeTypeName)
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