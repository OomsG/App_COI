package be.kdg.cityofideas.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.kdg.cityofideas.adapters.ProjectsAdapter

import be.kdg.cityofideas.R
import kotlinx.android.synthetic.main.fragment_project.view.*


class ProjectFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_project, container, false)
        Log.d("help", "Hello van Projectfragment")
       initialiseViews(view)
        return view
    }

    fun initialiseViews(view: View){
        val rvProjects = view.rvProjects
        rvProjects.layoutManager = LinearLayoutManager(context)
        rvProjects.adapter = ProjectsAdapter(context)

    }


}
