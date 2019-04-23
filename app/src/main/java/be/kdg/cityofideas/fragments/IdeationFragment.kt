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
import java.lang.Exception


class IdeationFragment : Fragment() {

    private lateinit var listener: IdeationsRecyclerAdapter.IdeationsSelectionListener
    private var numberOfTab: Int =0

    companion object {
        fun newInstance(position: Int): ProjectFragment {
            val fragment = ProjectFragment()
            val args = Bundle()
            args.putInt("position", position)
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        arguments?.getInt("position")?.let {
            numberOfTab = it
        }
        if (context is IdeationsRecyclerAdapter.IdeationsSelectionListener) {
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
        val rvIdeation = view.findViewById<RecyclerView>(R.id.rvIdeations)
        rvIdeation.layoutManager = LinearLayoutManager(context)
        rvIdeation.adapter = IdeationsRecyclerAdapter(context, listener, numberOfTab)
        /* RestClient(context)
             .getIdeations()
             .observeOn(AndroidSchedulers.mainThread())
             .subscribeOn(Schedulers.io())
             .subscribe {
                 (rvProjects.adapter as ProjectsRecyclerAdapter).projects = it
             }
        */
    }
}
