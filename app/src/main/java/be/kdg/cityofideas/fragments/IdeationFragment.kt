package be.kdg.cityofideas.fragments


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import be.kdg.cityofideas.R
import be.kdg.cityofideas.adapters.IdeationsAdapter
import be.kdg.cityofideas.adapters.ProjectsAdapter
import java.lang.Exception


class IdeationFragment : Fragment() {

    private lateinit var listener: IdeationsAdapter.IdeationsSelectionListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is IdeationsAdapter.IdeationsSelectionListener) {
            listener = context
        } else throw Exception("context is not Listener")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ideation, container, false)
        initialiseViews(view)
        return view
    }

    @SuppressLint("CheckResult")
    fun initialiseViews(view: View) {
        Log.d("help","fragment")
        val rvIdeation = view.findViewById<RecyclerView>(R.id.rvIdeations)
        rvIdeation.layoutManager = LinearLayoutManager(context)
        rvIdeation.adapter = IdeationsAdapter(context,listener)
        /* RestClient(context)
             .getIdeations()
             .observeOn(AndroidSchedulers.mainThread())
             .subscribeOn(Schedulers.io())
             .subscribe {
                 (rvProjects.adapter as ProjectsAdapter).projects = it
             }
        */
    }
}
