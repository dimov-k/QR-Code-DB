package ru.mrroot.qr_code_db.ui.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_single_item_qr_result.view.*
import ru.mrroot.qr_code_db.QRCodeTypes
import ru.mrroot.qr_code_db.R
import ru.mrroot.qr_code_db.db.DBHelperImpl
import ru.mrroot.qr_code_db.db.QRCode
import ru.mrroot.qr_code_db.ui.dialogs.QRCodeDialogFragment
import ru.mrroot.qr_code_db.utils.gone
import ru.mrroot.qr_code_db.utils.toFormattedDisplay
import ru.mrroot.qr_code_db.utils.visible


class HistoryListAdapter(
    var dbHelperImpl: DBHelperImpl,
    var context: Context,
    private var listOfScannedResult: MutableList<QRCode>
) :
    RecyclerView.Adapter<HistoryListAdapter.HistoryListViewHolder>() {
    private var qrCodeDialogFragment: QRCodeDialogFragment = QRCodeDialogFragment(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryListViewHolder {
        return HistoryListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_single_item_qr_result,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listOfScannedResult.size
    }

    override fun onBindViewHolder(holder: HistoryListViewHolder, position: Int) {
        holder.bind(listOfScannedResult[position], position)
    }

    inner class HistoryListViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        fun bind(qrCode: QRCode, position: Int) {
            view.title.text = qrCode.title
            view.dateAdded.text = qrCode.dateAdded.toFormattedDisplay()
            if (qrCode.qrCodeType == QRCodeTypes.URL) view.openLinkButton.visible() else view.openLinkButton.gone()
            if (qrCode.favourite) view.favouriteIcon.visible() else view.nonFavouriteIcon.visible()
            onClick(qrCode, position)
        }

        private fun onClick(qrCode: QRCode, position: Int) {
            view.generateQRCodeButton.setOnClickListener { qrCodeDialogFragment.show(qrCode.qrCodeValue) }

            view.openLinkButton.setOnClickListener {
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(qrCode.qrCodeValue)
                context.startActivity(openURL)
            }

            view.favouriteIcon.setOnClickListener {
                dbHelperImpl.removeFromFavourites(qrCode.id!!)
                view.favouriteIcon.gone()
                view.nonFavouriteIcon.visible()
                Toast.makeText(context, R.string.removed_from_favourites, Toast.LENGTH_SHORT).show()
            }

            view.nonFavouriteIcon.setOnClickListener {
                dbHelperImpl.addToFavourites(qrCode.id!!)
                view.nonFavouriteIcon.gone()
                view.favouriteIcon.visible()
                Toast.makeText(context, R.string.added_to_favourites, Toast.LENGTH_SHORT).show()
            }

            view.setOnLongClickListener {
                showDeleteDialog(qrCode, position)
                return@setOnLongClickListener true
            }
        }

        private fun showDeleteDialog(qrCode: QRCode, position: Int) {
            AlertDialog.Builder(context, R.style.CustomAlertDialog)
                .setTitle(context.getString(R.string.warning))
                .setMessage(context.getString(R.string.delete_single_qr_code_message))
                .setPositiveButton(context.getString(R.string.delete_confirm)) { _, _ ->
                    deleteThisRecord(qrCode, position)
                }
                .setNegativeButton(context.getString(R.string.delete_refuse)) { dialog, _ ->
                    dialog.cancel()
                }.show()
        }

        private fun deleteThisRecord(qrCode: QRCode, position: Int) {
            dbHelperImpl.deleteQRCode(qrCode.id!!)
            listOfScannedResult.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }
    }
}