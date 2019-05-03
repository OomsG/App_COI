package be.kdg.cityofideas.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import be.kdg.cityofideas.R
import be.kdg.cityofideas.adapters.ReactionRecyclerAdapter
import be.kdg.cityofideas.model.ideations.Ideas
import be.kdg.cityofideas.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/* Deze klasse zorgt ervoor dat alle reactions in een lijst getoond worden*/


class ReactionFragment : Fragment() {
    private lateinit var CurrentIdea: Ideas
    private lateinit var view1 : View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_reaction, container, false)
        return view
    }

    @SuppressLint("CheckResult")
    fun initialiseViews(view: View, ideas: Ideas) {
        val rvReactions = view.findViewById<RecyclerView>(R.id.rvReactions)
        rvReactions.layoutManager = LinearLayoutManager(context)
        rvReactions.adapter = ReactionRecyclerAdapter(context,ideas)
    }

    fun setIdea(idea: Ideas){
        initialiseViews(view1, idea)
    }
}
