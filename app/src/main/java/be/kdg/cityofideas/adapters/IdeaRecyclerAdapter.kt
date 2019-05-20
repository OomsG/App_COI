package be.kdg.cityofideas.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.media.session.MediaControllerCompat.setMediaController
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.system.Os.remove
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import be.kdg.cityofideas.R
import be.kdg.cityofideas.login.LoggedInUserView
import be.kdg.cityofideas.login.loggedInUser
import be.kdg.cityofideas.model.ideations.Idea
import be.kdg.cityofideas.model.ideations.Reaction
import be.kdg.cityofideas.model.ideations.Vote
import be.kdg.cityofideas.model.ideations.VoteType
import be.kdg.cityofideas.rest.RestClient
import kotlinx.android.synthetic.main.ideas_list.view.*
import com.google.android.youtube.player.YouTubePlayerSupportFragment

const val YOUTUBE_API: String = "AIzaSyAyyohq9ZDQT-bnC_E50ZcU-iA8efhXMjY"
/* Deze klasse zorgt ervoor dat alle ideen in een lijst getoond worden*/

//region toplevel Functions


fun getIdeaDetails(idea: Idea, context: Context?, layout: LinearLayout) {
    idea.IdeaObjects?.forEach {
        val id = it.IdeaObjectId
        it.Text?.let {
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            val text = TextView(context)
            text.id = id
            text.text = it
            text.layoutParams = params
            layout.addView(text)
        }
        it.Image?.let {
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            val image = ImageView(context)
            image.id = id
            image.setImageBitmap(it)
            image.layoutParams = params
            layout.addView(image)
        }

    }
}

fun getIdeaShareCount(idea: Idea, counted: Int): String? {
    var counter = 0
    idea.Votes?.forEach {
        if (it.VoteType == VoteType.SHARE_FB || it.VoteType == VoteType.SHARE_TW) {
            counter++
        }
    }
    counter = counted + counter
    return counter.toString() + " keer gedeeld"
}

fun getIdeaVoteCount(idea: Idea, counted: Int): String? {
    var counter = 0
    idea.Votes?.forEach {
        if (it.VoteType == VoteType.VOTE) {
            counter++
        }
    }
    counter = counted + counter
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
        return idea.Reactions.first().ReactionText
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
//endregion

class IdeaRecyclerAdapter(val context: Context?, val selectionListener: ideaSelectionListener) :
    RecyclerView.Adapter<IdeaRecyclerAdapter.IdeaViewHolder>() {
    private lateinit var bestReaction: Reaction
    private lateinit var view: View
    private var VoteCounter = 0
    private var ShareCounter = 0

    interface ideaSelectionListener {
        fun onIdeaSelected(id: Int)
        fun shareButtonPressed(id: Int, loggedInUser: LoggedInUserView)
    }

    var ideas: MutableList<Idea> = arrayListOf()
        set(ideas) {
            field = ideas
            notifyDataSetChanged()
        }

    class IdeaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.IdeaTitle
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
        val ideaView = LayoutInflater.from(p0.context).inflate(be.kdg.cityofideas.R.layout.ideas_list, p0, false)
        view = ideaView
        return IdeaViewHolder(ideaView)
    }

    override fun getItemCount() = ideas.size

    override fun onBindViewHolder(p0: IdeaViewHolder, p1: Int) {
        //p0.name.text = ideas[p1].User!!.Name
        p0.title.text = ideas[p1].Title
        getIdeaDetails(ideas[p1], context, p0.layout)
        p0.reactionCount.text = getReactionCount(ideas[p1])
        p0.shareCount.text = getIdeaShareCount(ideas[p1], ShareCounter)
        p0.voteCount.text = getIdeaVoteCount(ideas[p1], VoteCounter)
        p0.voteButton.setOnClickListener {
            if (loggedInUser != null) {
                Thread {
                    RestClient(context).createVote(ideas[p1].IdeaId, VoteType.VOTE, loggedInUser!!.UserId)
                    Log.d("vote", "Voted")
                }.start()
                notifyItemChanged(p1)
            } else {
                Toast.makeText(it.context, "U bent niet ingelogd!", Toast.LENGTH_LONG).show()
            }
        }
        p0.shareButton.setOnClickListener {
            if (loggedInUser != null) {
                selectionListener.shareButtonPressed(ideas[p1].IdeaId, loggedInUser!!)
            } else {
                Toast.makeText(it.context, "U bent niet ingelogd!", Toast.LENGTH_LONG).show()
            }
        }
        p0.reactionText.text = getBestReaction(ideas[p1])
        p0.reactionCount.setOnClickListener {
            if (ideas[p1].Reactions?.size != 0) {
                selectionListener.onIdeaSelected(ideas[p1].IdeaId)
            } else
                Toast.makeText(it.context, "Er zijn geen reacties om te tonen", Toast.LENGTH_LONG).show()
        }
    }
}
