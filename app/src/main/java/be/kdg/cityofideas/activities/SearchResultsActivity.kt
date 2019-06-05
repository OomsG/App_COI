package be.kdg.cityofideas.activities

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MenuItem
import android.widget.RelativeLayout
import android.widget.TextView
import be.kdg.cityofideas.R

class SearchResultsActivity : AppCompatActivity() {
    private lateinit var tvSearchString: TextView
    private lateinit var listSearchedProjects: RelativeLayout
    private lateinit var listSearchedIdeations: RelativeLayout
    private lateinit var listSearchedIdeas: RelativeLayout
    private lateinit var listSearchedReactions: RelativeLayout
    private lateinit var projectSearchResults: MutableMap<Int, String>
    private lateinit var ideationSearchResults: MutableMap<Int, String>
    private lateinit var ideaSearchResults: MutableMap<Int, String>
    private lateinit var reactionSearchResults: MutableMap<Int, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        initialiseViews()
        handleIntent(intent)
        addEventHandlers()

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home)
            this.finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onNewIntent(intent: Intent) {
        handleIntent(intent)
    }

    private fun initialiseViews() {
        tvSearchString = findViewById(R.id.tvSearchString)
        listSearchedProjects = findViewById(R.id.listSearchedProjects)
        listSearchedIdeations = findViewById(R.id.listSearchedIdeations)
        listSearchedIdeas = findViewById(R.id.listSearchedIdeas)
        listSearchedReactions = findViewById(R.id.listSearchedReactions)
    }

    private fun addEventHandlers() {
        for (i in 1..listSearchedProjects.childCount) {
            listSearchedProjects.getChildAt(i - 1).setOnClickListener {
                val intent = Intent(this, IdeationActivity::class.java)
                var j = 1
                projectSearchResults.forEach {
                    if (j == i) {
                        intent.putExtra(PROJECT_ID, it.key)
                    }

                    j++
                }

                startActivity(intent)
            }
        }

        for (i in 1..listSearchedIdeations.childCount) {
            listSearchedIdeations.getChildAt(i - 1).setOnClickListener {
                val intent = Intent(this, IdeaActivity::class.java)
                var j = 1
                ideationSearchResults.forEach {
                    if (j == i) {
                        val projectId: Int
                        val phaseId: Int

                        val cIdeation = manager.getDetails(
                            helper.getIdeationEntry().TBL_IDEATION,
                            null,
                            "${helper.getIdeationEntry().IDEATION_ID} = ?",
                            arrayOf(it.key.toString()),
                            null, null, null
                        )

                        if (cIdeation.moveToFirst()) {
                            phaseId = cIdeation.getInt(cIdeation.getColumnIndex(helper.getPhaseEntry().PHASE_ID))

                            val cPhase = manager.getDetails(
                                helper.getPhaseEntry().TBL_PHASE,
                                null,
                                "${helper.getPhaseEntry().PHASE_ID} = ?",
                                arrayOf(phaseId.toString()),
                                null, null, null
                            )

                            if (cPhase.moveToFirst()) {
                                projectId = cPhase.getInt(cPhase.getColumnIndex(helper.getProjectEntry().PROJECT_ID))
                                intent.putExtra(PROJECT_ID, projectId)
                                intent.putExtra(IDEATION_ID, it.key)
                            }
                        }
                    }

                    j++
                }

                startActivity(intent)
            }
        }

        for (i in 1..listSearchedIdeas.childCount) {
            listSearchedIdeas.getChildAt(i - 1).setOnClickListener {
                val intent = Intent(this, ReactionActivity::class.java)
                var j = 1
                ideaSearchResults.forEach {
                    if (j == i)
                        intent.putExtra(IDEA_ID, it.key)

                    j++
                }

                startActivity(intent)
            }
        }

        for (i in 1..listSearchedReactions.childCount) {
            listSearchedReactions.getChildAt(i - 1).setOnClickListener {
                val intent = Intent(this, ReactionActivity::class.java)
                var j = 1
                reactionSearchResults.forEach {
                    if (j == i)
                        intent.putExtra(IDEA_ID, it.key)

                    j++
                }

                startActivity(intent)
            }
        }
    }

    @SuppressLint("SetTextI18n", "ResourceType")
    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)

            tvSearchString.text = tvSearchString.text.toString() + " \"$query\""

            val cProject = getStringMatches(
                query,
                helper.getProjectEntry().TBL_PROJECT,
                arrayOf(helper.getProjectEntry().PROJECT_NAME, helper.getProjectEntry().PROJECT_DESCRIPTION)
            )

            projectSearchResults = getSearchResults(
                cProject,
                helper.getProjectEntry().PROJECT_ID,
                helper.getProjectEntry().PROJECT_NAME
            )

            var totalSearchedProjects = 1000
            for (s in getStringResultsOfMap(projectSearchResults)) {
                val params = setLayoutParams()

                if (totalSearchedProjects != 1000)
                    params.addRule(RelativeLayout.BELOW, totalSearchedProjects)
                else
                    params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)

                totalSearchedProjects++

                val tvProject = TextView(this)
                tvProject.id = totalSearchedProjects
                tvProject.text = s
                tvProject.gravity = Gravity.CENTER
                tvProject.setBackgroundResource(R.xml.textview_border)
                tvProject.setPadding(10, 10, 10, 10)

                listSearchedProjects.addView(tvProject, params)
            }

            val cIdeation = getStringMatches(
                query,
                helper.getIdeationEntry().TBL_IDEATION,
                arrayOf(helper.getIdeationEntry().IDEATION_CENTRAL_QUESTION)
            )

            ideationSearchResults = getSearchResults(
                cIdeation,
                helper.getIdeationEntry().IDEATION_ID,
                helper.getIdeationEntry().IDEATION_CENTRAL_QUESTION
            )

            var totalSearchedIdeations = 2000
            for (s in getStringResultsOfMap(ideationSearchResults)) {
                val params = setLayoutParams()

                if (totalSearchedIdeations != 2000)
                    params.addRule(RelativeLayout.BELOW, totalSearchedIdeations)
                else
                    params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)

                totalSearchedIdeations++

                val tvIdeation = TextView(this)
                tvIdeation.id = totalSearchedIdeations
                tvIdeation.text = s
                tvIdeation.gravity = Gravity.CENTER
                tvIdeation.setBackgroundResource(R.xml.textview_border)
                tvIdeation.setPadding(10, 10, 10, 10)

                listSearchedIdeations.addView(tvIdeation, params)
            }

            val cIdea = getStringMatches(
                query,
                helper.getIdeaEntry().TBL_IDEA,
                arrayOf(helper.getIdeaEntry().IDEA_TITLE)
            )

            ideaSearchResults = getSearchResults(
                cIdea,
                helper.getIdeaEntry().IDEA_ID,
                helper.getIdeaEntry().IDEA_TITLE
            )

            var totalSearchedIdeas = 3000
            for (s in getStringResultsOfMap(ideaSearchResults)) {
                val params = setLayoutParams()

                if (totalSearchedIdeas != 3000)
                    params.addRule(RelativeLayout.BELOW, totalSearchedIdeas)
                else
                    params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)

                totalSearchedIdeas++

                val tvIdea = TextView(this)
                tvIdea.id = totalSearchedIdeas
                tvIdea.text = s
                tvIdea.gravity = Gravity.CENTER
                tvIdea.setBackgroundResource(R.xml.textview_border)
                tvIdea.setPadding(10, 10, 10, 10)

                listSearchedIdeas.addView(tvIdea, params)
            }

            val cReaction = getStringMatches(
                query,
                helper.getReactionEntry().TBL_REACTION,
                arrayOf(helper.getReactionEntry().REACTION_TEXT)
            )

            reactionSearchResults = getSearchResults(
                cReaction,
                helper.getIdeaEntry().IDEA_ID,
                helper.getReactionEntry().REACTION_TEXT
            )

            var totalSearchedReactions = 4000
            for (s in getStringResultsOfMap(reactionSearchResults)) {
                val params = setLayoutParams()

                if (totalSearchedReactions != 4000)
                    params.addRule(RelativeLayout.BELOW, totalSearchedReactions)
                else
                    params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)

                totalSearchedReactions++

                val tvReaction = TextView(this)
                tvReaction.id = totalSearchedReactions
                tvReaction.text = s
                tvReaction.gravity = Gravity.CENTER
                tvReaction.setBackgroundResource(R.xml.textview_border)
                tvReaction.setPadding(10, 10, 10, 10)

                listSearchedReactions.addView(tvReaction, params)
            }
        }
    }

    private fun getStringMatches(query: String, table: String, columns: Array<String>): Cursor {
        var selection = ""
        val selectionArgs = mutableListOf<String>()

        for (i in 1..columns.size) {
            if (i == 1)
                selection += "${columns[i - 1]} LIKE ?"
            else
                selection += " OR ${columns[i - 1]} LIKE ?"

            selectionArgs.add("%$query%")
        }

        return manager.querySearch(table, selection, arrayOf("%$query%"))
    }

    private fun getSearchResults(cursor: Cursor, idColumn: String, searchColumn: String): MutableMap<Int, String> {
        val searchResults = mutableMapOf<Int, String>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(idColumn))
                if (id != 0)
                    searchResults[id] = cursor.getString(cursor.getColumnIndex(searchColumn))
            } while (cursor.moveToNext())
        }

        return searchResults
    }

    private fun setLayoutParams(): RelativeLayout.LayoutParams {
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        params.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE)
        params.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
        params.setMargins(0, 0, 0, 50)

        return params
    }

    private fun getStringResultsOfMap(map: MutableMap<Int, String>): Array<String> {
        val stringResults = mutableListOf<String>()

        for ((_, v) in map) {
            stringResults.add(v)
        }

        return stringResults.toTypedArray()
    }
}
