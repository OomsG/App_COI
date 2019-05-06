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
import be.kdg.cityofideas.model.ideations.Ideas
import be.kdg.cityofideas.model.ideations.Reactions
import be.kdg.cityofideas.model.ideations.VoteTypes
import be.kdg.cityofideas.rest.RestClient
import com.google.api.services.youtube.YouTube
import kotlinx.android.synthetic.main.idea_detail.view.*
import kotlinx.android.synthetic.main.ideas_list.view.*
import java.lang.NullPointerException


/* Deze klasse zorgt ervoor dat alle ideen in een lijst getoond worden*/


class IdeaRecyclerAdapter(val context: Context?, val selectionListener: ideaSelectionListener) :
    RecyclerView.Adapter<IdeaRecyclerAdapter.IdeaViewHolder>() {
    private lateinit var BestReaction: Reactions
    private var shareCount = 0;
    private var voteCount = 0;
    private lateinit var view: View

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
        val layout = view.LinearLayoutIdea
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): IdeaViewHolder {
        val ideaView = LayoutInflater.from(p0.context).inflate(R.layout.ideas_list, p0, false)
        view = ideaView
        return IdeaViewHolder(ideaView)
    }

    override fun getItemCount() = ideas.size


    override fun onBindViewHolder(p0: IdeaViewHolder, p1: Int) {
        //p0.name.text = ideas[p1].User.Name
        getIdeaDetails(ideas[p1], context, p0.layout)
        p0.reactionCount.text = getReactionCount(ideas[p1])
        p0.shareCount.text = getIdeaShareCount(ideas[p1])
        p0.voteCount.text = getIdeaVoteCount(ideas[p1])
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
        p0.reactionCount.setOnClickListener {
            if (ideas[p1].Reactions.size != 0) {
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
    }

    fun getIdeaShareCount(ideas: Ideas): String? {
        var shareCounter = 0
        ideas.Votes.forEach {
            if (it.VoteType == VoteTypes.SHARE_FB || it.VoteType == VoteTypes.SHARE_TW) {
                shareCounter++
            }
        }
        shareCounter = shareCounter + shareCount
        return shareCounter.toString() + " keer gedeeld"
    }

    fun getIdeaVoteCount(ideas: Ideas): String? {
        var counter = 0
        ideas.Votes.forEach {
            if (it.VoteType == VoteTypes.VOTE) {
                counter++
            }
        }
        counter = counter + voteCount
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
        } else if (size == 1) {
            return "1 reactie"
        } else if (size > 1) {
            return size.toString() + " reacties"
        }
        return null
    }
}