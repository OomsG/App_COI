package be.kdg.cityofideas.activities

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Intent
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import be.kdg.cityofideas.R

class SearchResultsActivity : AppCompatActivity() {
    private lateinit var tvSearchString: TextView
    private lateinit var listSearchedProjects: GridView
    private lateinit var listSearchedIdeations: GridView
    private lateinit var listSearchedIdeas: GridView
    private lateinit var listSearchedReactions: GridView

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
        listSearchedProjects.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, IdeationActivity::class.java)
            var i = 0
            projectSearchResults.forEach {
                if (i == position) {
                    intent.putExtra(PROJECT_ID, it.key)
                }

                i++
            }

            startActivity(intent)
        }

        listSearchedIdeations.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, IdeaActivity::class.java)
            var i = 0
            ideationSearchResults.forEach {
                if (i == position) {
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

                i++
            }

            startActivity(intent)
        }

        listSearchedIdeas.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, ReactionActivity::class.java)
            var i = 0
            ideaSearchResults.forEach {
                if (i == position) {
                    intent.putExtra(IDEA_ID, it.key)

                    Log.d("idea-key", it.key.toString())
                }

                i++

                Log.d("idea-i", i.toString())
            }

            startActivity(intent)
        }

        listSearchedReactions.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, ReactionActivity::class.java)
            var i = 0
            reactionSearchResults.forEach {
                if (i == position) {
                    intent.putExtra(IDEA_ID, it.key)

                    Log.d("reaction-key", it.key.toString())
                }

                i++

                Log.d("reaction-i", i.toString())
            }

            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
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

            setAdapter(listSearchedProjects, getStringResultsOfMap(projectSearchResults))

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

            setAdapter(listSearchedIdeations, getStringResultsOfMap(ideationSearchResults))

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

            setAdapter(listSearchedIdeas, getStringResultsOfMap(ideaSearchResults))

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

            setAdapter(listSearchedReactions, getStringResultsOfMap(reactionSearchResults))
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

    private fun setAdapter(gridView: GridView, array: Array<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, array)
        gridView.adapter = adapter
    }

    private fun getStringResultsOfMap(map: MutableMap<Int, String>): Array<String> {
        val stringResults = mutableListOf<String>()

        for ((k, v) in map) {
            stringResults.add(v)
        }

        return stringResults.toTypedArray()
    }
}
