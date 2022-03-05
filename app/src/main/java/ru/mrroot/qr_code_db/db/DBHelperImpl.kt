package ru.mrroot.qr_code_db.db

interface DBHelperImpl {
    fun insertQRCode(title: String, qrCodeValue: String, favourite: Boolean): Int
    fun editQRCode(qrCode: QRCode)
    fun getQRCode(id: Int): QRCode
    fun addToFavourites(id: Int): Int
    fun removeFromFavourites(id: Int): Int
    fun deleteQRCode(id: Int): Int
    fun getAllQRCodes(): List<QRCode>
    fun getAllFavourites(): List<QRCode>
    fun getAllQRCodesByType(qrCodeType: Int) : List<QRCode>
    fun getAllFavouritesByType(qrCodeType: Int) : List<QRCode>
    fun deleteAllQRCodes()
    fun deleteAllFavourites()
    fun getAllQRCodeTypes(): List<QRCodeType>
    fun deleteAllQRCodeTypes()
    fun deleteQRCodeType(id: Int): Int
    fun insertQRCodeType(qrCodeType: QRCodeType): Long
    fun getQRCodeTypeById(id: Int): QRCodeType
    fun initializeQRCodeTypeDB(qrCodeTypes: List<QRCodeType>)
    fun getQRCodeTypeIDByString(qrCodeTypeName: String): Int
}