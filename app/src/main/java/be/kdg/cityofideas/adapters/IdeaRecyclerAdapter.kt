package be.kdg.cityofideas.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import be.kdg.cityofideas.R
import be.kdg.cityofideas.listener.VoteListener
import be.kdg.cityofideas.model.ideations.Idea
import be.kdg.cityofideas.model.ideations.Reaction
import be.kdg.cityofideas.model.ideations.VoteType
import kotlinx.android.synthetic.main.idea_detail.view.*
import kotlinx.android.synthetic.main.ideas_list.view.*
import java.lang.NullPointerException


/* Deze klasse zorgt ervoor dat alle ideen in een lijst getoond worden*/

class IdeaRecyclerAdapter(val context: Context?, val selectionListener: ideaSelectionListener) :
    RecyclerView.Adapter<IdeaRecyclerAdapter.IdeaViewHolder>() {

    private var shareCount = 0;
    private var voteCount = 0;
    private lateinit var view: View
    private lateinit var bestReaction: Reaction

    interface ideaSelectionListener {
        fun onIdeaSelected(idea: Idea)
    }

    var ideas: Array<Idea> = arrayOf()
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
        val layout = view.LinearLayoutIdea
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): IdeaViewHolder {
        val ideaView = LayoutInflater.from(p0.context).inflate(R.layout.ideas_list, p0, false)
        view = ideaView
        return IdeaViewHolder(ideaView)
    }

    override fun getItemCount() = ideas.size


    override fun onBindViewHolder(p0: IdeaViewHolder, p1: Int) {
        //p0.name.text = ideas[p1].user.Name
        getIdeaDetails(ideas[p1], context, p0.layout)
        p0.reactionCount.text = getReactionCount(ideas[p1])
        p0.shareCount.text = getIdeaShareCount(ideas[p1])
        p0.voteCount.text = getIdeaVoteCount(ideas[p1])
        p0.itemView.setOnClickListener {
            if (ideas[p1].Reactions.size != 0) {
                selectionListener.onIdeaSelected(ideas[p1])
            } else
                Toast.makeText(it.context, "Er zijn geen reacties om te tonen", Toast.LENGTH_LONG).show()
        }
        p0.voteButton.setOnClickListener {
            RestClient(context).createVote(ideas[p1].IdeaId, "VOTE", "A")
            voteCount++
            notifyDataSetChanged()

        }
        p0.shareButton.setOnClickListener {
            RestClient(context).createVote(ideas[p1].IdeaId, "SHARE_FB", "A")
            shareCount++
            notifyDataSetChanged()

        }
        p0.reactionText.text = getBestReaction(ideas[p1])
        p0.itemView.setOnClickListener {
            if (ideas[p1].Reactions?.size != 0) {
                selectionListener.onIdeaSelected(ideas[p1])
            } else
                Toast.makeText(it.context, "Er zijn geen reacties om te tonen", Toast.LENGTH_LONG).show()
        }
    }
    fun getIdeaDetails(idea: Ideas, context: Context?, layout: LinearLayout) {
        idea.IdeaObjects.forEach {
            try {
                if (it.Text!!.isNotEmpty()) {
                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    val text = TextView(context)
                    text.id = it.IdeaObjectId
                    text.text = it.Text
                    text.layoutParams = params
                    layout.addView(text)
                }
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
            try {
                if (!it.ImageName!!.isNotEmpty()) {
                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    val image = ImageView(context)
                    image.id = it.IdeaObjectId
                    image.setImageBitmap(it.Image)
                    image.layoutParams = params
                    layout.addView(image)

                }
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
            try {
                if (!it.Url!!.isEmpty()) {
                    val video = YouTube.Activities.Insert.USER_AGENT_SUFFIX
                }
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }

        }

    fun getIdeaShareCount(idea: Idea): String? {
        var counter = 0
        idea.Votes?.forEach {
            if (it.VoteType == VoteType.SHARE_FB || it.VoteType == VoteType.SHARE_TW) {
                counter++
            }
        }
        shareCounter = shareCounter + shareCount
        return shareCounter.toString() + " keer gedeeld"
    }

    fun getIdeaVoteCount(idea: Idea): String? {
        var counter = 0
        idea.Votes?.forEach {
            if (it.VoteType == VoteType.VOTE) {
                counter++
            }
        }
        counter = counter + voteCount
        return counter.toString() + " Stemmen"
    }

    fun getBestReaction(idea: Idea): String? {
        /* var a: Int = 0
         idea.Reaction.forEach {
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
        val reactions: Array<Reaction> = idea.Reactions!!.toTypedArray()
        if (reactions.isEmpty()) {
            return "Er zijn geen reacties om weer te geven"
        } else {
            return idea.Reactions?.first()?.ReactionText
        }
    }

    fun getReactionCount(idea: Idea): String? {
        val size = idea.Reactions?.size
        if (size == 0) {
            return "Geen reacties"
        } else if (size == 1) {
            return "1 reactie"
        } else if (size != null) {
            if (size > 1) {
                return size.toString() + " reacties"
            }
        }
        return null
    }
}