package be.kdg.cityofideas.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import be.kdg.cityofideas.R
import be.kdg.cityofideas.adapters.ReactionRecyclerAdapter
import be.kdg.cityofideas.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/* Deze klasse zorgt ervoor dat alle reactions in een lijst getoond worden*/


class ReactionFragment : Fragment() {
    private lateinit var mView : View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_reaction, container, false)
        mView =view
        return view
    }

    @SuppressLint("CheckResult")
    fun initialiseViews(view: View, id:Int) {
        val rvReactions = view.findViewById<RecyclerView>(R.id.rvReactions)
        rvReactions.layoutManager = LinearLayoutManager(context)
        rvReactions.adapter = ReactionRecyclerAdapter(context)
        RestClient(context)
            .getReactions("reactions/"+ id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                (rvReactions.adapter as ReactionRecyclerAdapter).reactions = it
            }
    }

    fun setId(ideaId: Int){
        initialiseViews(mView, ideaId)
    }
}
