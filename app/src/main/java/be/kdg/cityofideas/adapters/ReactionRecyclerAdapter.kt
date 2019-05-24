package be.kdg.cityofideas.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import be.kdg.cityofideas.R
import be.kdg.cityofideas.login.loggedInUser
import be.kdg.cityofideas.model.ideations.Reaction
import be.kdg.cityofideas.rest.RestClient
import kotlinx.android.synthetic.main.reactions_list.view.*

class ReactionRecyclerAdapter(val context: Context?) :
    RecyclerView.Adapter<ReactionRecyclerAdapter.ReactionsViewHolder>() {

    var reactions: Array<Reaction> = arrayOf()
        set(reactions) {
            field = reactions
            notifyDataSetChanged()
        }

    class ReactionsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.NameReaction
        val text: TextView = view.TextReaction
        val submitLike: ImageButton = view.LikeButton
        val likeCounter: TextView = view.LikeCounter
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ReactionsViewHolder {
        val reactionsView = LayoutInflater.from(p0.context).inflate(R.layout.reactions_list, p0, false)
        return ReactionsViewHolder(reactionsView)
    }

    override fun getItemCount() = reactions.size

    override fun onBindViewHolder(p0: ReactionsViewHolder, p1: Int) {
        p0.text.text = reactions[p1].ReactionText
        p0.submitLike.setOnClickListener {
            if (loggedInUser != null) {
                Thread {
                    RestClient(context).createLike(reactions[p1].ReactionId, loggedInUser!!.UserId)
                }.start()
                Toast.makeText(context, "Liked", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "U bent niet ingelogd", Toast.LENGTH_LONG).show()
            }
        }
        p0.likeCounter.text = getLikeCount(reactions[p1])

    }

    private fun getLikeCount(reaction: Reaction): CharSequence? {
        return if (reaction.Likes.isNullOrEmpty()) {
            "0"
        } else {
            reaction.Likes.size.toString()
        }
    }

}