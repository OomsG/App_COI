package be.kdg.cityofideas.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import be.kdg.cityofideas.fragments.ProjectFragment

/* Deze klasse zorg voor de menubar waarbij de verschillende projectfragments worden aangesproken
afhankelijk van welke status deze hebben (Actief, in de toekomst of al voorbij

 */

class ProjectsViewPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
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