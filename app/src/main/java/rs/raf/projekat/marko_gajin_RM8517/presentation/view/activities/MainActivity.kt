package rs.raf.projekat.marko_gajin_RM8517.presentation.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import kotlinx.android.synthetic.main.activity_main.*
import rs.raf.projekat.marko_gajin_RM85.R
import rs.raf.projekat.marko_gajin_RM8517.presentation.view.viewpagers.NavigationAdapter

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        initListeners()
        initViewPager()
    }

    private fun initListeners() {
        topAppBar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navigation.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.myLocation -> {
                    viewPager.setCurrentItem(NavigationAdapter.FRAGMENT_MY_LOCATION, false)
                }
                R.id.savedLocations -> {
                    viewPager.setCurrentItem(NavigationAdapter.FRAGMENT_SAVED_LOCATIONS, false)
                }
            }
            true
        }
    }

    private fun initViewPager() {
        viewPager.adapter = NavigationAdapter(supportFragmentManager)
    }

}