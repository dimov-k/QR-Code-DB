package ru.mrroot.qr_code_db.db

interface DBHelperImpl {
    fun insertQRCode(title: String, qrCodeValue: String, favourite: Boolean): Int
    fun getQRCode(id: Int): QRCode
    fun addToFavourites(id: Int): Int
    fun removeFromFavourites(id: Int): Int
    fun deleteQRCode(id: Int): Int
    fun getAllQRCodes(): List<QRCode>
    fun getAllFavourites(): List<QRCode>
    fun deleteAllQRCodes()
    fun deleteAllFavourites()
}