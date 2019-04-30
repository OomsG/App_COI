package be.kdg.cityofideas.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.kdg.cityofideas.R
import be.kdg.cityofideas.model.ideations.Ideas
import be.kdg.cityofideas.model.ideations.Ideations
import be.kdg.cityofideas.model.ideations.VoteTypes
import kotlinx.android.synthetic.main.ideas_list.view.*

/* Deze klasse zorgt ervoor dat alle ideen in een lijst getoond worden*/


class IdeaRecyclerAdapter(context: Context?, val selectionListener: ideaSelectionListener) :
    RecyclerView.Adapter<IdeaRecyclerAdapter.IdeaViewHolder>() {

    interface ideaSelectionListener {
        fun onIdeaSelected(idea: Ideas)
    }

    var ideas: Array<Ideas> = arrayOf()
        set(ideas) {
            field = ideas
            notifyDataSetChanged()
        }

    class IdeaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.IdeaUserName
        val description = view.IdeaDescription
        val voteCount = view.IdeaVoteCount
        val reactionCount = view.IdeaReactionCount
        val shareCount = view.IdeaShareCount
        val voteButton = view.IdeaVoteButton
        val shareButton = view.IdeaShareButton
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): IdeaViewHolder {
        val ideaView = LayoutInflater.from(p0.context).inflate(R.layout.ideas_list, p0, false)
        return IdeaViewHolder(ideaView)
    }

    override fun getItemCount() = ideas.size


    override fun onBindViewHolder(p0: IdeaViewHolder, p1: Int) {
        //p0.name.text = ideas[p1].
        //p0.description.text = ideas[p1].
        p0.reactionCount.text = ideas[p1].Reactions.size.toString() + " Reacties"
        p0.shareCount.text = getIdeaShareCount(ideas[p1]).toString() + " keer gedeeld"
        p0.voteCount.text = getIdeaVoteCount(ideas[p1]).toString()+ " stemmen"
        p0.voteButton.setOnClickListener { }
        p0.shareButton.setOnClickListener { }
    }

    fun getIdeaShareCount(ideas: Ideas): Int {
        var counter = 0
        ideas.Votes.forEach {
            if (it.VoteType == VoteTypes.SHARE_FB || it.VoteType == VoteTypes.SHARE_TW) {
                counter++
            }
        }
        return counter
    }

    fun getIdeaVoteCount(ideas: Ideas): Int {
        var counter = 0
        ideas.Votes.forEach {
            if (it.VoteType == VoteTypes.VOTE) {
                counter++
            }
        }
        return counter
    }
}