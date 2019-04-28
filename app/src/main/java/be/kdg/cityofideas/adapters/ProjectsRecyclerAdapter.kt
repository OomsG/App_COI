package be.kdg.cityofideas.adapters

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.kdg.cityofideas.R
import be.kdg.cityofideas.listener.SelectionListener
import be.kdg.cityofideas.model.projects.Projects
import kotlinx.android.synthetic.main.projects_list.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ProjectsRecyclerAdapter(val context: Context?, val selectionListener: SelectionListener, val status: String) :
    RecyclerView.Adapter<ProjectsRecyclerAdapter.ProjectsViewHolder>() {

    class ProjectsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.TitleProject
        val Description = view.SmallDescriptionProject
        val picture = view.smallFotoProject
    }

    var projects: Array<Projects> = arrayOf()
        set(projects) {
            field = projects
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProjectsViewHolder {
        val projectsView = LayoutInflater.from(p0.context).inflate(R.layout.projects_list, p0, false)
        return ProjectsViewHolder(projectsView)
    }

    override fun getItemCount() = projects.size

    override fun onBindViewHolder(p0: ProjectsViewHolder, p1: Int) {
        p0.title.text = projects[p1].ProjectName
        p0.Description.text = projects[p1].Description
        p0.picture.setImageResource(R.drawable.antwerpen)
        p0.itemView.setOnClickListener {
            selectionListener.onSelected(projects[p1].ProjectId)
        }
    }
}
