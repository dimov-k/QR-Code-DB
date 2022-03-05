package ru.mrroot.qr_code_db.ui.mainactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_scanned_history.view.*
import ru.mrroot.qr_code_db.R
import ru.mrroot.qr_code_db.ui.history.ScannedHistoryFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setViewPager()
        setBottomViewListener()
        setViewPagerListener()
    }


    private fun setViewPager() {
        viewPager.adapter = MainPagerAdapter(supportFragmentManager)
        viewPager.offscreenPageLimit = 2
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int) { }

            override fun onPageSelected(position: Int) {
                val fragment: Fragment? = (viewPager.adapter as MainPagerAdapter).createdFragments[position]
                if (fragment != null && fragment is ScannedHistoryFragment) {
                    viewPager.swipeRefresh.isRefreshing = true
                    fragment.showListOfResults()
                    viewPager.swipeRefresh.isRefreshing = false
                }
            }

            override fun onPageScrollStateChanged(state: Int) { }

        })
    }

    private fun setBottomViewListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.qrScanMenuId -> {
                    viewPager.currentItem = 0
                }
                R.id.scannedResultMenuId -> {
                    viewPager.currentItem = 1
                }
                R.id.favouriteScannedMenuId -> {
                    viewPager.currentItem = 2
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }


    private fun setViewPagerListener() {
        viewPager.setOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        bottomNavigationView.selectedItemId = R.id.qrScanMenuId
                    }
                    1 -> {
                        bottomNavigationView.selectedItemId = R.id.scannedResultMenuId
                    }
                    2 -> {
                        bottomNavigationView.selectedItemId = R.id.favouriteScannedMenuId
                    }
                }
            }
        })
    }
}