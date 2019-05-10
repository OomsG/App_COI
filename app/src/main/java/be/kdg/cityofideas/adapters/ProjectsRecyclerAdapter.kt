package be.kdg.cityofideas.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.kdg.cityofideas.R
import be.kdg.cityofideas.model.projects.Project
import kotlinx.android.synthetic.main.projects_list.view.*
import java.text.SimpleDateFormat
import java.util.*

class ProjectsRecyclerAdapter(
    val context: Context?,
    private val selectionListener: ProjectsSelectionListener,
    private val status: String
) :
    RecyclerView.Adapter<ProjectsRecyclerAdapter.ProjectsViewHolder>() {
    interface ProjectsSelectionListener {
        fun onProjectsSelected(id: Int)
    }

    class ProjectsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.TitleProject
        val Description = view.SmallDescriptionProject
        val picture = view.smallFotoProject
    }

    var projects: Array<Project> = arrayOf()
        set(projects) {
            field = projects
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProjectsViewHolder {
        val projectsView = LayoutInflater.from(p0.context).inflate(R.layout.projects_list, p0, false)
        return ProjectsViewHolder(projectsView)
    }

    override fun getItemCount() = getProjectsOfStatus(projects, status).size

    override fun onBindViewHolder(p0: ProjectsViewHolder, p1: Int) {
        p0.title.text = getProjectsOfStatus(projects, status)[p1].ProjectName
        p0.Description.text = getProjectsOfStatus(projects, status)[p1].Description
        p0.picture.setImageBitmap( getProjectsOfStatus(projects, status)[p1].BackgroundIMG)
        p0.itemView.setOnClickListener {
            selectionListener.onProjectsSelected(getProjectsOfStatus(projects, status)[p1].ProjectId)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getProjectsOfStatus(projects: Array<Project>, status: String): Array<Project> {
        val today = Date()
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        when (status) {
            "active" -> return projects.filter {
                format.parse(it.StartDate).before(today) && format.parse(it.EndDate).after(today)
            }.toTypedArray()

            "future" -> return projects.filter {
                format.parse(it.StartDate).after(today)
            }.toTypedArray()

            "past" -> return projects.filter {
                format.parse(it.EndDate).before(today)
            }.toTypedArray()
        }
        return projects
    }
}
