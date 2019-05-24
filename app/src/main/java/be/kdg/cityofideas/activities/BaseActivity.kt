package be.kdg.cityofideas.activities;

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import be.kdg.cityofideas.*
import be.kdg.cityofideas.login.LoginActivity
import be.kdg.cityofideas.login.LoginSessionManager
import be.kdg.cityofideas.login.loggedInUser

open class BaseActivity : AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        if (loggedInUser != null) {
            menu?.findItem(R.id.qrScanner)?.isVisible = true
            menu?.findItem(R.id.account)?.isVisible = true
            menu?.findItem(R.id.logout)?.isVisible = true
            menu?.findItem(R.id.login)?.isVisible = false
        }

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.search)?.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(ComponentName(context, SearchResultsActivity::class.java)))
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.login -> {
                signIn()
                true
            }
//            R.id.search -> {
//                search()
//                true
//            }
            R.id.account -> {
                showAccountInfo()
                true
            }
            R.id.logout -> {
                signOut()
                true
            }
            R.id.qrScanner -> {
                scanQr()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun signIn() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun search() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun showAccountInfo() {
        val intent = Intent(this, AccountActivity::class.java)
        startActivity(intent)
    }

    private fun signOut() {
        val session = LoginSessionManager(this)

        Toast.makeText(
            applicationContext,
            "Tot ziens ${loggedInUser!!.UserName}!",
            Toast.LENGTH_LONG
        ).show()

        session.logoutUser()
        invalidateOptionsMenu()
    }

    private fun scanQr() {
        val intent = Intent(this, QrActivity::class.java)
        startActivity(intent)
    }
}
