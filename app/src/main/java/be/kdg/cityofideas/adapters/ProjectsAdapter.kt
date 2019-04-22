package be.kdg.cityofideas.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.kdg.cityofideas.R
import be.kdg.cityofideas.model.projects.Projects
import be.kdg.cityofideas.model.projects.getTestProjects
import kotlinx.android.synthetic.main.projects_list.view.*

class ProjectsAdapter(val context: Context?, val selectionListener: ProjectsSelectionListener, val status : String) :
    RecyclerView.Adapter<ProjectsAdapter.ProjectsViewHolder>() {
    interface ProjectsSelectionListener {
        fun onProjectsSelected(positionProject: Int)
    }


    class ProjectsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.TitleProject
        val Description = view.SmallDescriptionProject
        val picture = view.smallFotoProject
    }

    //Dit is nodig wanneer de Get werkt
    var projects: List<Projects> = getTestProjects().filter {
        (it.status.equals(status))
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProjectsViewHolder {
        val projectsView = LayoutInflater.from(p0.context).inflate(R.layout.projects_list, p0, false)
        return ProjectsViewHolder(projectsView)
    }
    override fun getItemCount() = projects.size

    //Testing
    //override fun getItemCount() = 10


    override fun onBindViewHolder(p0: ProjectsViewHolder, p1: Int) {
        Log.d("help", "help me1")
        p0.title.text = projects[p1].projectName
        p0.Description.text = projects[p1].description
        p0.picture.setImageResource(R.mipmap.ic_launcher_round)
        p0.itemView.setOnClickListener{
            selectionListener.onProjectsSelected(p1)
        }

        //Testing
        /*
        p0.title.text = "Dit moet wel werken"
        p0.Description.text =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed mollis arcu quis nunc venenatis finibus. Cras velit magna, sodales id diam vitae, imperdiet sagittis ex."
        p0.picture.setImageResource(R.mipmap.ic_launcher_round)
        p0.itemView.setOnClickListener {
            selectionListener.onProjectsSelected(p1)
        }
        */
    }
}
