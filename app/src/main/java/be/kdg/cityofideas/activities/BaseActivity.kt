package be.kdg.cityofideas.activities;

import android.content.Intent
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import be.kdg.cityofideas.R
import be.kdg.cityofideas.login.LoginActivity
import be.kdg.cityofideas.login.loggedInUser

open class BaseActivity : AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        if (loggedInUser != null) {
            Log.d("menu", "loggedInUser is niet null")
            menu?.findItem(R.id.account)?.isVisible = true
            menu?.findItem(R.id.logout)?.isVisible = true
            menu?.findItem(R.id.login)?.isVisible = false
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
            R.id.search -> {
                search()
                true
            }
            R.id.account -> {
                showAccountInfo()
                true
            }
            R.id.logout -> {
                signOut()
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

    }
}
