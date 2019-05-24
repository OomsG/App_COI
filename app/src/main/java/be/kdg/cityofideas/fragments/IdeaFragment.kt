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
import be.kdg.cityofideas.adapters.IdeaRecyclerAdapter
import be.kdg.cityofideas.adapters.IdeaRecyclerAdapter.*
import be.kdg.cityofideas.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception


class IdeaFragment : Fragment() {
    private lateinit var listener: ideaSelectionListener
    private lateinit var mView: View

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ideaSelectionListener) {
            listener = context
        } else throw Exception("context is not Listener")
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_idea, container, false)
        mView = view
        return view
    }

    @SuppressLint("CheckResult")
    fun initialiseViews(view: View, ideationId: Int,projectId: Int) {
        val rvIdeas = view.findViewById<RecyclerView>(R.id.rvIdeas)
        rvIdeas.layoutManager = LinearLayoutManager(context)
        Log.d("projectId",projectId.toString())
        rvIdeas.adapter = IdeaRecyclerAdapter(context, listener)
        RestClient(context)
            .getIdeations("ideations/" + projectId)
            .map {
                it.filter {
                    it.IdeationId.equals(ideationId)
                }
            }.map {
                it.first().Ideas
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                (rvIdeas.adapter as IdeaRecyclerAdapter).ideas = it!!.toMutableList()
            })
    }

    fun setId(ideation: Int, project: Int) {
        initialiseViews(mView, ideation, project)
    }

}
