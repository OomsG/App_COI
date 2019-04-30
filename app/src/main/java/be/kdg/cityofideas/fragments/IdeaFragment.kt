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
import be.kdg.cityofideas.model.ideations.Ideations
import be.kdg.cityofideas.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception


class IdeaFragment : Fragment() {
    private lateinit var listener: ideaSelectionListener
    private var ideationId = 0
    private var projectId = 0

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ideaSelectionListener) {
            listener = context
        } else throw Exception("context is not Listener")
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_idea, container, false)
        initialiseViews(view, ideationId, listener)
        return view
    }

    @SuppressLint("CheckResult")
    fun initialiseViews(view: View, ideationId: Int, listener: ideaSelectionListener) {
        val rvIdeas = view.findViewById<RecyclerView>(R.id.rvIdeas)
        rvIdeas.layoutManager = LinearLayoutManager(context)
        rvIdeas.adapter = IdeaRecyclerAdapter(context, listener, ideationId)
        RestClient(context)
            .getIdeations("ideations/" + projectId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                (rvIdeas.adapter as IdeaRecyclerAdapter).ideations = it
            })
    }

    fun setId(ideation: Int, project: Int) {
        ideationId = ideation
        projectId = project
    }

}
