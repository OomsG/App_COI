package be.kdg.cityofideas.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.kdg.cityofideas.R
import kotlinx.android.synthetic.main.reactions_list.view.*

class ReactionAdapter (val context: Context?) : RecyclerView.Adapter<ReactionAdapter.ReactionsViewHolder>(){
    class ReactionsViewHolder(view:View):RecyclerView.ViewHolder(view){
        val name = view.NameReaction
        val text = view.TextReaction
    }
    //Dit is nodig wanneer de Get werkt
    /*var Reactions: Array<Reactions> = arrayOf()
        set(reacttions) {
            field = reacttions
            notifyDataSetChanged()
        }
*/

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ReactionsViewHolder {
        val reactionsView =LayoutInflater.from(p0.context).inflate(R.layout.reactions_list, p0, false)
        return ReactionsViewHolder(reactionsView)
    }

    override fun getItemCount() = 5
    //override fun getItemCount() = reactions.size

    override fun onBindViewHolder(p0: ReactionsViewHolder, p1: Int) {
        p0.name.text = "Glenn Ooms"
        p0.text.text = "Hier komt de reactie te staan"
    }


}