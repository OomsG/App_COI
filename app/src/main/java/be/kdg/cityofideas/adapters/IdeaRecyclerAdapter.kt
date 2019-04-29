package be.kdg.cityofideas.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.kdg.cityofideas.R
import be.kdg.cityofideas.model.ideations.Ideas
import be.kdg.cityofideas.model.ideations.Ideations
import kotlinx.android.synthetic.main.ideas_list.view.*

/* Deze klasse zorgt ervoor dat alle ideen in een lijst getoond worden*/


class IdeaRecyclerAdapter(context: Context?, val selectionListener: ideaSelectionListener) : RecyclerView.Adapter<IdeaRecyclerAdapter.IdeaViewHolder>() {

    interface ideaSelectionListener {
       fun onIdeaSelected(idea:Ideas)
    }

    class IdeaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.NameIdea
        val Text = view.textIdea
    }
    lateinit var ideation: Ideations

    var ideas: Array<Ideas> = ideation.Ideas.toTypedArray()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): IdeaViewHolder {
        val ideaView = LayoutInflater.from(p0.context).inflate(R.layout.ideas_list, p0, false)
        return IdeaViewHolder(ideaView)
    }

    override fun getItemCount() = ideas.size


    override fun onBindViewHolder(p0: IdeaViewHolder, p1: Int) {
        p0.name.text = "Glenn"
        p0.Text.text = "Hello"
    }


}