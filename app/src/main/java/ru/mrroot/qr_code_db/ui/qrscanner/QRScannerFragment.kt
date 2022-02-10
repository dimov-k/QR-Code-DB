package ru.mrroot.qr_code_db.ui.qrscanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_qrscanner.view.*
import me.dm7.barcodescanner.zbar.ZBarScannerView
import ru.mrroot.qr_code_db.R
import ru.mrroot.qr_code_db.db.DBHelper
import ru.mrroot.qr_code_db.db.DBHelperImpl
import ru.mrroot.qr_code_db.db.QRCodeDB
import ru.mrroot.qr_code_db.ui.dialogs.QrCodeResultDialog

class QRScannerFragment : Fragment(), ZBarScannerView.ResultHandler {

    companion object {

        fun newInstance(): QRScannerFragment {
            return QRScannerFragment()
        }
    }

    private lateinit var mView: View

    lateinit var scannerView: ZBarScannerView

    lateinit var resultDialog: QrCodeResultDialog

    private lateinit var dbHelperI: DBHelperImpl

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_qrscanner, container, false)
        init()
        initViews()
        onClicks()
        return mView.rootView
    }

    private fun init() {
        dbHelperI = DBHelper(QRCodeDB.getAppDatabase(requireContext())!!)
    }

    private fun initViews() {
        initializeQRCamera()
        setResultDialog()
    }

    private fun initializeQRCamera() {
        scannerView = ZBarScannerView(context)
        scannerView.setResultHandler(this)
        scannerView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorTranslucent))
        scannerView.setBorderColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
        scannerView.setLaserColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
        scannerView.setBorderStrokeWidth(10)
        scannerView.setSquareViewFinder(true)
        scannerView.setupScanner()
        scannerView.setAutoFocus(true)
        startQRCamera()
        mView.containerScanner.addView(scannerView)
    }

    private fun setResultDialog() {
        resultDialog = QrCodeResultDialog(requireContext())
        resultDialog.setOnDismissListener(object : QrCodeResultDialog.OnDismissListener {
            override fun onDismiss() {
                resetPreview()
            }
        })
    }


    override fun handleResult(rawResult: Result?) {
        onQrResult(rawResult?.contents)
    }

    private fun onQrResult(contents: String?) {
        if (contents.isNullOrEmpty())
            showToast("Empty Qr Result")
        else
            saveToDataBase(contents)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun saveToDataBase(contents: String) {
        val insertedResultId = dbHelperI.insertQRCode("Title", contents, false)
        val qrResult = dbHelperI.getQRCode(insertedResultId)
        resultDialog.show(qrResult)
    }

    private fun startQRCamera() {
        scannerView.startCamera()
    }

    private fun resetPreview() {
        scannerView.stopCamera()
        scannerView.startCamera()
        scannerView.stopCameraPreview()
        scannerView.resumeCameraPreview(this)
    }

    private fun onClicks() {
        mView.flashToggle.setOnClickListener {
            if (mView.flashToggle.isSelected) {
                offFlashLight()
            } else {
                onFlashLight()
            }
        }
    }

    private fun onFlashLight() {
        mView.flashToggle.isSelected = true
        scannerView.flash = true
    }

    private fun offFlashLight() {
        mView.flashToggle.isSelected = false
        scannerView.flash = false
    }

    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this)
        scannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView.stopCamera()
    }

}
