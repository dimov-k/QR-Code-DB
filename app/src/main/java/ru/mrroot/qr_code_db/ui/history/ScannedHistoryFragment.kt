package ru.mrroot.qr_code_db.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.mrroot.qr_code_db.R
import ru.mrroot.qr_code_db.ui.adapter.HistoryListAdapter
import kotlinx.android.synthetic.main.fragment_scanned_history.view.*
import kotlinx.android.synthetic.main.layout_header_history.view.*
import ru.mrroot.qr_code_db.db.DBHelper
import ru.mrroot.qr_code_db.db.DBHelperImpl
import ru.mrroot.qr_code_db.db.QRCode
import ru.mrroot.qr_code_db.db.QRCodeDB
import ru.mrroot.qr_code_db.utils.gone
import ru.mrroot.qr_code_db.utils.visible
import java.io.Serializable


class ScannedHistoryFragment : Fragment() {

    enum class ResultListType : Serializable {
        ALL_CODES, FAVOURITES
    }

    private lateinit var mView: View

    private lateinit var dbHelperImpl: DBHelperImpl

    private var resultListType: ResultListType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleArguments()
    }

    private fun handleArguments() {
        resultListType = arguments?.getSerializable(ARGUMENT_RESULT_LIST_TYPE) as ResultListType
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_scanned_history, container, false)
        init()
        setSwipeRefresh()
        onClicks()
        showListOfResults()
        return mView.rootView
    }

    private fun init() {
        dbHelperImpl = DBHelper(QRCodeDB.getAppDatabase(requireContext())!!)
        mView.layoutHeader.tvHeaderText.text = getString(R.string.recent_qr_codes)
    }

    private fun showListOfResults() {
        when (resultListType) {
            ResultListType.FAVOURITES -> showFavouriteResults()
            else -> showAllResults()

        }
    }


    private fun showAllResults() {
        val listOfAllResult = dbHelperImpl.getAllQRCodes()
        showResults(listOfAllResult)
        mView.layoutHeader.tvHeaderText.text = getString(R.string.recent_scanned)
    }

    private fun showFavouriteResults() {
        val listOfFavouriteResult = dbHelperImpl.getAllFavourites()
        showResults(listOfFavouriteResult)
        mView.layoutHeader.tvHeaderText.text = getString(R.string.favourites)
    }


    private fun showResults(listOfQrResult: List<QRCode>) {
        if (listOfQrResult.isNotEmpty())
            initRecyclerView(listOfQrResult)
        else
            showEmptyState()
    }

    private fun initRecyclerView(listOfQrResult: List<QRCode>) {
        mView.scannedHistoryRecyclerView.layoutManager = LinearLayoutManager(context)
        mView.scannedHistoryRecyclerView.adapter =
            HistoryListAdapter(dbHelperImpl, requireContext(), listOfQrResult.toMutableList())
        showRecyclerView()
    }

    private fun setSwipeRefresh() {
        mView.swipeRefresh.setOnRefreshListener {
            mView.swipeRefresh.isRefreshing = false
            showListOfResults()
        }
    }


    private fun onClicks() {
        mView.layoutHeader.removeAll.setOnClickListener {
            showRemoveAllScannedResultDialog()
        }
    }

    private fun showRemoveAllScannedResultDialog() {
        AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog).setTitle(getString(R.string.warning))
            .setMessage(getString(R.string.delete_all_qr_codes_message))
            .setPositiveButton(getString(R.string.delete_confirm)) { _, _ ->
                clearAllRecords()
            }
            .setNegativeButton(getString(R.string.delete_refuse)) { dialog, _ ->
                dialog.cancel()
            }.show()

    }

    private fun clearAllRecords() {
        when (resultListType) {
            ResultListType.FAVOURITES -> dbHelperImpl.deleteAllFavourites()
            else -> dbHelperImpl.deleteAllQRCodes()
        }
        mView.scannedHistoryRecyclerView.adapter?.notifyDataSetChanged()
        showListOfResults()
    }

    private fun showRecyclerView() {
        mView.layoutHeader.removeAll.visible()
        mView.scannedHistoryRecyclerView.visible()
        mView.noResultFound.gone()
    }

    private fun showEmptyState() {
        mView.layoutHeader.removeAll.gone()
        mView.scannedHistoryRecyclerView.gone()
        mView.noResultFound.visible()
    }

    companion object {

        private const val ARGUMENT_RESULT_LIST_TYPE = "ArgumentResultType"

        fun newInstance(screenType: ResultListType): ScannedHistoryFragment {
            val bundle = Bundle()
            bundle.putSerializable(ARGUMENT_RESULT_LIST_TYPE, screenType)
            val fragment = ScannedHistoryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
