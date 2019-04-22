package be.kdg.cityofideas.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import be.kdg.cityofideas.activities.MainActivity
import be.kdg.cityofideas.fragments.IdeationFragment
import be.kdg.cityofideas.fragments.ProjectFragment
import be.kdg.cityofideas.model.projects.Projects
import kotlin.contracts.contract

class ViewPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    override fun getItem(p0: Int): Fragment? {
        return ProjectFragment.newInstance(p0)
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> {
                return "Active"
            }
            1 -> {
                return "Future"
            }
            2 -> {
                return "Past"
            }
            else -> return null
        }
    }
}