package be.kdg.cityofideas.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import be.kdg.cityofideas.R
import be.kdg.cityofideas.adapters.ReactionRecyclerAdapter

/* Deze klasse zorgt ervoor dat alle reactions in een lijst getoond worden*/


class ReactionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_reaction, container, false)
        initialiseViews(view)
        return view
    }

    fun initialiseViews(view: View) {
     val rvReactions = view.findViewById<RecyclerView>(R.id.rvReactions)
        rvReactions.layoutManager = LinearLayoutManager(context)
        rvReactions.adapter = ReactionRecyclerAdapter(context)
    }
}
