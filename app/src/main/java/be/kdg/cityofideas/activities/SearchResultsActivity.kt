package be.kdg.cityofideas.activities

import android.app.SearchManager
import android.content.Intent
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import be.kdg.cityofideas.R

class SearchResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)

            Log.d("SearchResult query", query)

            val cProject = getStringMatches(
                query,
                helper.getProjectEntry().TBL_PROJECT,
                arrayOf(helper.getProjectEntry().PROJECT_NAME, helper.getProjectEntry().PROJECT_DESCRIPTION)
            )

            if (cProject.moveToFirst()) {
                while (cProject.moveToNext()) {
                    Log.d("cProject", cProject.getString(cProject.getColumnIndex(helper.getProjectEntry().PROJECT_NAME)).toString())
                    Log.d("cProject", cProject.getString(cProject.getColumnIndex(helper.getProjectEntry().PROJECT_DESCRIPTION)).toString())
                }
            }
        }
    }

    private fun getStringMatches(query: String, table: String, columns: Array<String>): Cursor {
        var selection = ""
        val selectionArgs = mutableListOf<String>()

        for (i in 1..columns.size) {
            Log.d("columns", columns[i -1])

            if (i == 1)
                selection += "${columns[i - 1]} LIKE ?"
            else
                selection += " OR ${columns[i - 1]} LIKE ?"

            selectionArgs.add("*$query*")
        }

        return manager.querySearch(table, selection, selectionArgs.toTypedArray())
    }
}
