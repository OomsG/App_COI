package be.kdg.cityofideas.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import be.kdg.cityofideas.R
import be.kdg.cityofideas.model.surveys.Survey

class SurveyRecyclerAdapter(val context: Context?, val selectionListener: IdeationsRecyclerAdapter.IdeationsSelectionListener, val projectsId: Int) : RecyclerView.Adapter<SurveyRecyclerAdapter.SurveyViewHolder>() {
    var surveys: Array<Survey> = arrayOf()
        set(question) {
            field = question
            notifyDataSetChanged()
        }


    class SurveyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.TitleSurvey)
        val button = view.findViewById<Button>(R.id.answerSurvey)
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SurveyViewHolder {
        val surveyViewHolder = LayoutInflater.from(p0.context).inflate(R.layout.survey_list, p0, false)
        return SurveyViewHolder(surveyViewHolder)
    }

    override fun getItemCount() = surveys.size


    override fun onBindViewHolder(p0: SurveyViewHolder, p1: Int) {
        p0.title.text = surveys[p1].Title
        p0.button.setOnClickListener{
            selectionListener.onSurveySelected(surveys[p1].SurveyId,projectsId)
        }
    }
}