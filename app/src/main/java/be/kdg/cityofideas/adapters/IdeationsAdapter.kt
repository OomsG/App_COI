package be.kdg.cityofideas.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.kdg.cityofideas.R
import kotlinx.android.synthetic.main.ideations_list.view.*
import kotlinx.android.synthetic.main.projects_list.view.*

/* Deze klasse zorgt ervoor dat alle ideations in een lijst getoond worden*/

class IdeationsAdapter(val context: Context?, val seltionListener: IdeationsSelectionListener) : RecyclerView.Adapter<IdeationsAdapter.IdeationsViewHolder>() {
    interface IdeationsSelectionListener{
        fun onIdeationsSelected(positionIdeation:Int)
    }


    class IdeationsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.TitleIdeation
        val Description = view.SmallDescriptionIdeation
        val picture = view.smallFotoIdeation
    }

//Dit is nodig wanneer de Get werkt
    /*var Ideations: Array<Ideations> = arrayOf()
        set(ideations) {
            field = ideations
            notifyDataSetChanged()
        }
*/

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): IdeationsAdapter.IdeationsViewHolder {
        val ideationsView = LayoutInflater.from(p0.context).inflate(R.layout.ideations_list, p0, false)
        Log.d("help","adapter")
        return IdeationsViewHolder(ideationsView)
    }

    override fun getItemCount() = 10
    //override fun getItemCount() = ideations.size

    override fun onBindViewHolder(p0: IdeationsAdapter.IdeationsViewHolder, p1: Int) {
        p0.title.text = "Dit moet wel werken"
        p0.Description.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed mollis arcu quis nunc venenatis finibus. Cras velit magna, sodales id diam vitae, imperdiet sagittis ex."
        p0.picture.setImageResource(R.mipmap.ic_launcher_round)
    }
}