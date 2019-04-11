package be.kdg.cityofideas.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.kdg.cityofideas.R
import be.kdg.cityofideas.model.projects.Projects
import kotlinx.android.synthetic.main.projects_list.view.*

class ProjectsAdapter(val context: Context?) : RecyclerView.Adapter<ProjectsAdapter.ProjectsViewHolder>() {
    class ProjectsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.TitleProject
        val Description = view.SmallDescriptionProject
        val picture = view.smallFotoProject
    }

    var projects: List<Projects> = listOf()
        set(projects) {
            field = projects
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProjectsAdapter.ProjectsViewHolder {
        val projectsView = LayoutInflater.from(p0.context).inflate(R.layout.projects_list,p0,false)
        Log.d("help", "Hello van ProjectAdapter")
        return ProjectsViewHolder(projectsView)
    }

    override fun getItemCount() = projects.size


    override fun onBindViewHolder(p0: ProjectsAdapter.ProjectsViewHolder, p1: Int) {
        p0.Description.text = "Dit is een titel"
        p0.title.text = "Hello dit is een korte beschrijving"
        p0.picture
    }
}
