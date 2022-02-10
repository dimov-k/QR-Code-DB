package ru.mrroot.qr_code_db.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import kotlinx.android.synthetic.main.layout_generate_qr_code.*
import ru.mrroot.qr_code_db.R

class QRCodeDialogFragment(var context: Context) {

    private var dialog: Dialog = Dialog(context)
    private var qrgEncoder: QRGEncoder? = null
    private var bitmap: Bitmap? = null

    init {
        dialog.setContentView(R.layout.layout_generate_qr_code)
        dialog.setCancelable(false)
        dialog.qrCode.setOnClickListener { dialog.dismiss() }
    }

    fun show(qrCodeValue: String?) {
        qrgEncoder = QRGEncoder(qrCodeValue, null, QRGContents.Type.TEXT, 500)
        try {
            bitmap = qrgEncoder!!.encodeAsBitmap()
            dialog.qrCode.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}