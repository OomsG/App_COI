package be.kdg.cityofideas.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import be.kdg.cityofideas.R
import be.kdg.cityofideas.activities.IdeaActivity
import be.kdg.cityofideas.fragments.IdeaFragment
import be.kdg.cityofideas.fragments.ReactionFragment
import be.kdg.cityofideas.listener.VoteListener
import be.kdg.cityofideas.model.ideations.Ideas
import be.kdg.cityofideas.model.ideations.Ideations
import be.kdg.cityofideas.model.ideations.Reactions
import be.kdg.cityofideas.model.ideations.VoteTypes
import kotlinx.android.synthetic.main.idea_detail.view.*
import kotlinx.android.synthetic.main.ideas_list.view.*
import java.lang.Error

/* Deze klasse zorgt ervoor dat alle ideen in een lijst getoond worden*/


class IdeaRecyclerAdapter(context: Context?, val selectionListener: ideaSelectionListener) :
    RecyclerView.Adapter<IdeaRecyclerAdapter.IdeaViewHolder>() {

    private lateinit var BestReaction: Reactions


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
        val voteCount = view.IdeaVoteCount
        val reactionCount = view.IdeaReactionCount
        val shareCount = view.IdeaShareCount
        val voteButton = view.IdeaVoteButton
        val shareButton = view.IdeaShareButton
        val reactionName = view.IdeaReactionNameFirst
        val reactionText = view.IdeaReactionTextFirst
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): IdeaViewHolder {
        val ideaView = LayoutInflater.from(p0.context).inflate(R.layout.ideas_list, p0, false)
        return IdeaViewHolder(ideaView)
    }

    override fun getItemCount() = ideas.size


    override fun onBindViewHolder(p0: IdeaViewHolder, p1: Int) {
        //p0.name.text = ideas[p1].User.Name
        p0.reactionCount.text = getReactionCount(ideas[p1])
        p0.shareCount.text = getIdeaShareCount(ideas[p1])
        p0.voteCount.text = getIdeaVoteCount(ideas[p1])
        p0.voteButton.setOnClickListener {
        }
        p0.shareButton.setOnClickListener { }
        p0.reactionText.text = getBestReaction(ideas[p1])
        p0.itemView.setOnClickListener {
            if (ideas[p1].Reactions.size != 0) {
                selectionListener.onIdeaSelected(ideas[p1])
            } else
                Toast.makeText(it.context, "Er zijn geen reacties om te tonen", Toast.LENGTH_LONG).show()
        }
    }

    fun getIdeaShareCount(ideas: Ideas): String? {
        var counter = 0
        ideas.Votes.forEach {
            if (it.VoteType == VoteTypes.SHARE_FB || it.VoteType == VoteTypes.SHARE_TW) {
                counter++
            }
        }
        return counter.toString() + " keer gedeeld"
    }

    fun getIdeaVoteCount(ideas: Ideas): String? {
        var counter = 0
        ideas.Votes.forEach {
            if (it.VoteType == VoteTypes.VOTE) {
                counter++
            }
        }
        return counter.toString() + " Stemmen"
    }

    fun getBestReaction(ideas: Ideas): String? {
        /* var a: Int = 0
         ideas.Reactions.forEach {
             try {
                 val b = it.Like.size
                 if (b.compareTo(a) < 0) {
                     a = b
                     BestReaction = it
                 }
             } catch (e: Error) {
                 e.printStackTrace()
             }

         }*/
        val reactions: Array<Reactions> = ideas.Reactions.toTypedArray()
        if (reactions.isEmpty()) {
            return "Er zijn geen reacties om weer te geven"
        } else {
            return ideas.Reactions.first().ReactionText
        }
    }

    fun getReactionCount(idea: Ideas): String? {
        val size = idea.Reactions.size
        if (size == 0) {
            return "Geen reacties"
        } else if (size == 0) {
            return "1 reactie"
        } else if (size > 1) {
            return size.toString() + " reacties"
        }
        return null
    }
}