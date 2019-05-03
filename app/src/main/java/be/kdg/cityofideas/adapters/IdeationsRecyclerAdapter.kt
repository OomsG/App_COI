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
import kotlinx.android.synthetic.main.ideations_list.view.*

/* Deze klasse zorgt ervoor dat alle ideations in een lijst getoond worden*/

class IdeationsRecyclerAdapter(
    val context: Context?,
    val selectionListener: IdeationsSelectionListener,
    val projectId: Int
) :
    RecyclerView.Adapter<IdeationsRecyclerAdapter.IdeationsViewHolder>() {

    interface IdeationsSelectionListener {
        fun onIdeationSelected(ideationid: Int, projectId: Int)
    }

    class IdeationsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.TitleIdeation
        val Description = view.SmallDescriptionIdeation
        val button = view.giveIdea
        val IdedeationsIdeaCount = view.IdeationIdeaCount
        val IdeationsVoteCount = view.IdeationVoteCount
        val IdeationsShareCount = view.IdeationShareCount
        val IdeationsVote = view.IdeationVote
        val IdeationsShare = view.IdeationShare
    }

    var ideations: Array<Ideations> = arrayOf()
        set(ideations) {
            field = ideations
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): IdeationsViewHolder {
        val ideationsView = LayoutInflater.from(p0.context).inflate(R.layout.ideations_list, p0, false)
        return IdeationsViewHolder(ideationsView)
    }

    override fun getItemCount() = ideations.size

    override fun onBindViewHolder(p0: IdeationsViewHolder, p1: Int) {
        p0.title.text = ideations[p1].CentralQuestion
        p0.IdedeationsIdeaCount.text = ideations[p1].Ideas.size.toString() + " Ideeën"
        p0.IdeationsVoteCount.text = getIdeationVoteCount(ideations).toString() + " Stemmen"
        p0.IdeationsShareCount.text = getIdeationShareCount(ideations).toString() + " keer gedeeld"
        p0.IdeationsShare
        p0.IdeationsVote
        p0.button.setOnClickListener {
            selectionListener.onIdeationSelected(ideations[p1].IdeationId, projectId)
        }
        //p0.Description.text = ideations[p1].
    }


    fun getIdeationVoteCount(ideations: Array<Ideations>): Int? {
        var votes: Int = 0
        ideations.forEach {
            it.Ideas.forEach {
                it.Votes.forEach {
                    //Log.d("vote",it.VoteType.toString())
                    if (it.VoteType == VoteTypes.VOTE) {

                        votes++
                    }
                }
            }
        }
        return votes
    }

    fun getIdeationShareCount(ideations: Array<Ideations>): Int? {
        var votes: Int = 0
        ideations.forEach {
            it.Ideas.forEach {
                it.Votes.forEach {
                    if (it.VoteType == VoteTypes.SHARE_FB || it.VoteType == VoteTypes.SHARE_TW) {
                        votes++
                    }
                }
            }
        }
        return votes
    }
}