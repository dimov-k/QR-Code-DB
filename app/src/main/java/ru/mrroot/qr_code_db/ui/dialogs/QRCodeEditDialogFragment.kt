package ru.mrroot.qr_code_db.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.layout_qr_code_edit.*
import ru.mrroot.qr_code_db.QRCodeTypes
import ru.mrroot.qr_code_db.R
import ru.mrroot.qr_code_db.db.DBHelperImpl
import ru.mrroot.qr_code_db.db.QRCode
import ru.mrroot.qr_code_db.utils.gone
import ru.mrroot.qr_code_db.utils.visible

class QRCodeEditDialogFragment(var context: Context, var dbHelperImpl: DBHelperImpl) {
    private var dialog: Dialog = Dialog(context)
    private lateinit var qrCodeToSave: QRCode
    private lateinit var spinner: Spinner
    private var onDismissListener: OnDismissListener?= null

    init {
        dialog.setContentView(R.layout.layout_qr_code_edit)
        dialog.setCancelable(false)
        dialog.saveEditButton.setOnClickListener { saveEdit() }
        dialog.cancelEditButton.setOnClickListener { dialog.dismiss() }
        dialog.favouriteIcon.setOnClickListener {
            dialog.favouriteIcon.gone()
            dialog.nonFavouriteIcon.visible()
        }
        dialog.nonFavouriteIcon.setOnClickListener {
            dialog.nonFavouriteIcon.gone()
            dialog.favouriteIcon.visible()
        }
    }

    fun show(qrCode: QRCode) {
        qrCodeToSave = qrCode
        dialog.titleEdit.text = qrCodeToSave.title

        spinner = dialog.qrCodeTypesList
        ArrayAdapter.createFromResource(
            context, R.array.qr_code_types, android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        if (qrCodeToSave.qrCodeType == QRCodeTypes.UNDEFINED) spinner.setSelection(spinner.count - 1)
            else spinner.setSelection(qrCodeToSave.qrCodeType!!-1)
        dialog.favouriteIcon.isVisible = qrCodeToSave.favourite
        dialog.nonFavouriteIcon.isVisible = !qrCodeToSave.favourite
        dialog.show()
    }

    private fun saveEdit() {
        qrCodeToSave.title = dialog.titleEdit.text.toString()
        qrCodeToSave.qrCodeType = QRCodeTypes.getQRCodeTypeID(spinner.selectedItem.toString())
        qrCodeToSave.favourite = dialog.favouriteIcon.isVisible
        dbHelperImpl.editQRCode(qrCodeToSave)
        dialog.dismiss()
        onDismissListener?.onDismiss()
    }

    interface OnDismissListener {
        fun onDismiss()
    }

    fun setOnDismissListener(dismissListener: OnDismissListener) {
        this.onDismissListener = dismissListener
    }
}