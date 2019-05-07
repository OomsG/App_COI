package be.kdg.cityofideas.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.kdg.cityofideas.R
import be.kdg.cityofideas.model.ideations.Ideation
import be.kdg.cityofideas.model.ideations.VoteType
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

    var ideations: Array<Ideation> = arrayOf()
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
        p0.IdedeationsIdeaCount.text = getIdeaCount(ideations[p1])
        p0.IdeationsVoteCount.text = getIdeationVoteCount(ideations)
        p0.IdeationsShareCount.text = getIdeationShareCount(ideations)
        p0.IdeationsShare
        p0.IdeationsVote
        p0.button.setOnClickListener {
            selectionListener.onIdeationSelected(ideations[p1].IdeationId, projectId)
        }
        //p0.Description.text = ideations[p1].
    }


    fun getIdeationVoteCount(ideations: Array<Ideation>): String? {
        var votes: Int = 0
        ideations.forEach {
            it.Ideas?.forEach {
                it.Votes?.forEach {
                    if (it.VoteType == VoteType.VOTE) {
                        votes++
                    }
                }
            }
        }
        return votes.toString() + " Stemmen"
    }

    fun getIdeationShareCount(ideations: Array<Ideation>): String? {
        var votes: Int = 0
        ideations.forEach {
            it.Ideas?.forEach {
                it.Votes?.forEach {
                    if (it.VoteType == VoteType.SHARE_FB || it.VoteType == VoteType.SHARE_TW) {
                        votes++
                    }
                }
            }
        }
        return votes.toString() + " keer gedeeld"
    }

    fun getIdeaCount(ideation: Ideation): String? {
        val size = ideation.Ideas?.size
        if (size == 0) {
            return "Geen ideeën"
        } else if (size == 0) {
            return "1 idee"
        } else if (size != null) {
            if (size > 1) {
                return size.toString() + " ideeën"
            }
        }
        return null
    }
}
