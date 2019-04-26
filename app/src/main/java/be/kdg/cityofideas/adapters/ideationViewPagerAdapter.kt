package be.kdg.cityofideas.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import be.kdg.cityofideas.fragments.IdeationFragment
import be.kdg.cityofideas.model.ideations.Ideations
import be.kdg.cityofideas.model.projects.Phases
import be.kdg.cityofideas.model.projects.getTestPhases

class IdeationViewPagerAdapter(fm: FragmentManager?, val projectId: Int) : FragmentPagerAdapter(fm) {

    var phases: List<Phases> = listOf()
        set(phases) {
            field = phases
            notifyDataSetChanged()
        }

    override fun getItem(p0: Int): Fragment? {
        return IdeationFragment.newInstance((p0+1),projectId)
    }

    override fun getCount(): Int =phases.size

    override fun getPageTitle(position: Int): CharSequence? {
        return "Fase " + position
    }
}