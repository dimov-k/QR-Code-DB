package ru.mrroot.qr_code_db.ui.mainactivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ru.mrroot.qr_code_db.ui.history.ScannedHistoryFragment
import ru.mrroot.qr_code_db.ui.qrscanner.QRScannerFragment

class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                QRScannerFragment.newInstance()
            }

            1 -> {
                ScannedHistoryFragment.newInstance(ScannedHistoryFragment.ResultListType.ALL_CODES)
            }

            2 -> {
                ScannedHistoryFragment.newInstance(ScannedHistoryFragment.ResultListType.FAVOURITES)
            }

            else -> {
                QRScannerFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }
}