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

import be.kdg.cityofideas.R
import be.kdg.cityofideas.adapters.IdeaRecyclerAdapter
import be.kdg.cityofideas.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class IdeaFragment : Fragment() {
    private lateinit var listener: IdeaRecyclerAdapter.ideaSelectionListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_idea, container, false)
        initialiseViews(view)
        return view
    }

    @SuppressLint("CheckResult")
    fun initialiseViews(view: View) {
        val rvIdeas = view.findViewById<RecyclerView>(R.id.rvIdeas)
        rvIdeas.layoutManager = LinearLayoutManager(context)
        rvIdeas.adapter = IdeaRecyclerAdapter(context,listener)
    }


}
