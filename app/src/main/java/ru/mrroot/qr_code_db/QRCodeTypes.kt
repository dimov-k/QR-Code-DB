package ru.mrroot.qr_code_db

object QRCodeTypes {
    const val URL: Int = 1
    const val EMAIL: Int = 2
    const val TELEPHONE_NUMBER: Int = 3
    const val CONTACT: Int = 4
    const val PROMO_CODE: Int = 5
    const val VACCINE_QRCODE: Int = 6
    const val UNDEFINED: Int = 255

    private val qrCodeTypesMap: HashMap<String, Int> = hashMapOf(
    "Link Address" to URL,
    "E-mail" to EMAIL,
    "Telephone Number" to TELEPHONE_NUMBER,
    "Contact" to CONTACT,
    "Promotion Code" to PROMO_CODE,
    "Vaccine QR Code" to VACCINE_QRCODE,
    "Undefined Code" to UNDEFINED
    )

    fun getQRCodeTypeID(qrCodeTypeText: String): Int {
        return qrCodeTypesMap[qrCodeTypeText]!!
    }
}