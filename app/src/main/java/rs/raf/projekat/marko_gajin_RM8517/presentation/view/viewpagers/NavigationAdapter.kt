package rs.raf.projekat.marko_gajin_RM8517.presentation.view.viewpagers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rs.raf.projekat.marko_gajin_RM8517.presentation.view.fragments.MyLocationFragment
import rs.raf.projekat.marko_gajin_RM8517.presentation.view.fragments.PlacesFragment

class NavigationAdapter(
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private const val ITEM_COUNT = 2
        const val FRAGMENT_MY_LOCATION = 0
        const val FRAGMENT_SAVED_LOCATIONS = 1
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            FRAGMENT_MY_LOCATION -> MyLocationFragment()
            else -> PlacesFragment()
        }
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            FRAGMENT_MY_LOCATION -> "my location"
            else -> "saved locations"
        }
    }
}