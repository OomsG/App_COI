package be.kdg.cityofideas.fragments


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.kdg.cityofideas.adapters.ProjectsRecyclerAdapter

import be.kdg.cityofideas.R
import java.lang.Exception


class ProjectFragment : Fragment() {

    private lateinit var listener: ProjectsRecyclerAdapter.ProjectsSelectionListener
    private lateinit var status: String

    companion object {
        fun newInstance(position: Int): ProjectFragment {
            val fragment = ProjectFragment()
            val args = Bundle()
            var status = ""
            when (position) {
                0 -> status = "active"
                1 -> status = "future"
                2 -> status = "past"
            }
            args.putString("status", status)
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        arguments?.getString("status")?.let {
            status = it
        }
        if (context is ProjectsRecyclerAdapter.ProjectsSelectionListener) {
            listener = context
        } else throw Exception("context is not Listener")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_project, container, false)
        initialiseViews(view, listener, status)
        return view
    }


    @SuppressLint("CheckResult")
    fun initialiseViews(view: View, listener: ProjectsRecyclerAdapter.ProjectsSelectionListener, status: String) {
        val rvProjects = view.findViewById<RecyclerView>(R.id.rvProjects)
        rvProjects.layoutManager = LinearLayoutManager(context)
        rvProjects.adapter = ProjectsRecyclerAdapter(context, listener, status)
        /*RestClient(context)
            .getProjects()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                (rvProjects.adapter as ProjectsRecyclerAdapter).projects = it
            }*/

    }
}
