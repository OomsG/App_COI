package be.kdg.cityofideas.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.kdg.cityofideas.R
import kotlinx.android.synthetic.main.ideas_list.view.*

/* Deze klasse zorgt ervoor dat alle ideen in een lijst getoond worden*/


class IdeaRecyclerAdapter (context: Context? ) : RecyclerView.Adapter<IdeaRecyclerAdapter.IdeaViewHolder>(){

    class IdeaViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name = view.NameIdea
        val Text = view.textIdea
    }
    //Dit is nodig wanneer de Get werkt
    /*var Ideas: Array<Ideas> = arrayOf()
        set(ideas) {
            field = ideas
            notifyDataSetChanged()
        }
*/

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): IdeaViewHolder {
        val ideaView = LayoutInflater.from(p0.context).inflate(R.layout.ideas_list,p0,false)
        return IdeaViewHolder(ideaView)
    }

    override fun getItemCount() = 5
    //override fun getItemCount() = ideas.size


    override fun onBindViewHolder(p0: IdeaViewHolder, p1: Int) {
        p0.name.text = "Glenn"
        p0.Text.text = "Hello"
    }




}