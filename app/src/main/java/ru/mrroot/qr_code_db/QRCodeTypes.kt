package ru.mrroot.qr_code_db

object QRCodeTypes {
    const val URL: Int = 1
    const val EMAIL: Int = 2
    const val TELEPHONE_NUMBER: Int = 3
    const val CONTACT: Int = 4
    const val PROMO_CODE: Int = 5
    const val VACCINE_QRCODE: Int = 6
    const val RESTAURANT_MENU: Int = 7
    const val UNDEFINED: Int = 255

    const val URL_TEXT: String = "Link Address"
    const val EMAIL_TEXT: String = "E-mail"
    const val TELEPHONE_NUMBER_TEXT: String = "Telephone Number"
    const val CONTACT_TEXT: String = "Contact"
    const val PROMO_CODE_TEXT: String = "Promotion Code"
    const val VACCINE_QRCODE_TEXT: String = "Vaccine QR Code"
    const val UNDEFINED_TEXT: String = "Undefined Code"
    const val RESTAURANT_MENU_TEXT: String = "Restaurant Menu"
}