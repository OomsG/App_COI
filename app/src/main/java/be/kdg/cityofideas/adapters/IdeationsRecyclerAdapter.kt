package be.kdg.cityofideas.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.kdg.cityofideas.R
import be.kdg.cityofideas.model.ideations.Ideations
import be.kdg.cityofideas.model.ideations.getTestIdeations
import be.kdg.cityofideas.model.projects.Projects
import be.kdg.cityofideas.model.projects.getTestPhases
import be.kdg.cityofideas.model.projects.getTestProjects
import kotlinx.android.synthetic.main.ideations_list.view.*

/* Deze klasse zorgt ervoor dat alle ideations in een lijst getoond worden*/

class IdeationsRecyclerAdapter(val context: Context?, val selectionListener: IdeationsSelectionListener, val phaseID: Int) :
    RecyclerView.Adapter<IdeationsRecyclerAdapter.IdeationsViewHolder>() {
    interface IdeationsSelectionListener {
        fun onIdeationsSelected(positionIdeation: Int)
    }

    class IdeationsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.TitleIdeation
        val Description = view.SmallDescriptionIdeation
        val picture = view.smallFotoIdeation
    }

    /*Dit is nodig wanneer de Get werkt
        var Ideations: Array<Ideations> = arrayOf()
            set(ideations) {
                field = ideations
                notifyDataSetChanged()
            }
    */
    var ideations: List<Ideations> = getTestIdeations().filter {
        (it.PhaseId.equals(phaseID))
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): IdeationsViewHolder {
        val ideationsView = LayoutInflater.from(p0.context).inflate(R.layout.ideations_list, p0, false)
        return IdeationsViewHolder(ideationsView)
    }

    override fun getItemCount() = ideations.size

    override fun onBindViewHolder(p0: IdeationsViewHolder, p1: Int) {
        p0.title.text = ideations[p1].CentralQuestion
        p0.Description.text = ideations[p1].InputIdeation.toString()
        p0.picture.setImageResource(R.mipmap.ic_launcher_round)
    }
}