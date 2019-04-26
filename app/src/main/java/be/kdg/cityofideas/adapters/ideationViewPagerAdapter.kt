package be.kdg.cityofideas.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import be.kdg.cityofideas.fragments.IdeationFragment
import be.kdg.cityofideas.model.projects.Phases
import be.kdg.cityofideas.model.projects.getTestPhases

class IdeationViewPagerAdapter(fm: FragmentManager?, val projectId: Int) : FragmentPagerAdapter(fm) {
    override fun getItem(p0: Int): Fragment? {
        return IdeationFragment.newInstance(getPhaseId(p0+1))
    }

    override fun getCount(): Int {
        return getTestPhases().filter { (it.ProjectId.equals(projectId)) }.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "Fase " + position
    }

    fun getPhaseId(projectId: Int): Int {
        return getTestPhases().filter { (it.ProjectId.equals(projectId)) }.first().PhaseId
    }
}