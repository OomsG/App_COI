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
import be.kdg.cityofideas.adapters.IdeationsRecyclerAdapter
import be.kdg.cityofideas.adapters.ProjectsRecyclerAdapter
import be.kdg.cityofideas.listener.SelectionListener
import be.kdg.cityofideas.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_project.*
import java.lang.Exception


class IdeationFragment : Fragment() {

    private lateinit var listener: SelectionListener
    private var phaseNr: Int = 0
    private var projectId: Int = 0

    companion object {
        fun newInstance(phaseNr: Int, projectId: Int): ProjectFragment {
            val fragment = ProjectFragment()
            val args = Bundle()
            args.putInt("phaseNr", phaseNr)
            args.putInt("projectId", projectId)
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        arguments?.getInt("phaseNr")?.let {
            phaseNr = it
        }
        arguments?.getInt("projectId")?.let {
            projectId = it
        }
        if (context is SelectionListener) {
            listener = context
        } else throw Exception("context is not Listener")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ideation, container, false)
        initialiseViews(view,listener,phaseNr)
        return view
    }

    @SuppressLint("CheckResult")
    fun initialiseViews(view: View,listener: SelectionListener,phaseNr: Int) {
        val rvIdeation = view.findViewById<RecyclerView>(R.id.rvIdeations)
        rvIdeation.layoutManager = LinearLayoutManager(context)
        rvIdeation.adapter = IdeationsRecyclerAdapter(context, listener, phaseNr)
        RestClient(context)
            .getIdeations("Ideations" + projectId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                (rvIdeation.adapter as IdeationsRecyclerAdapter).ideations = it
            }

    }
}
