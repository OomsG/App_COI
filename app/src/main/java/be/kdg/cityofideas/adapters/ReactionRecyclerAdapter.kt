package be.kdg.cityofideas.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.kdg.cityofideas.R
import be.kdg.cityofideas.model.ideations.Ideas
import be.kdg.cityofideas.model.ideations.Reactions
import kotlinx.android.synthetic.main.reactions_list.view.*

class ReactionRecyclerAdapter(val context: Context?) :
    RecyclerView.Adapter<ReactionRecyclerAdapter.ReactionsViewHolder>() {

    var reactions: Array<Reactions> = arrayOf()
        set(reactions) {
            field = reactions
            notifyDataSetChanged()
        }

    class ReactionsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.NameReaction
        val text = view.TextReaction
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ReactionsViewHolder {
        val reactionsView = LayoutInflater.from(p0.context).inflate(R.layout.reactions_list, p0, false)
        return ReactionsViewHolder(reactionsView)
    }

    override fun getItemCount() = reactions.size

    override fun onBindViewHolder(p0: ReactionsViewHolder, p1: Int) {
        p0.text.text = reactions[p1].ReactionText
    }

}