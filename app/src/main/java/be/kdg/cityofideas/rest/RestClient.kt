package be.kdg.cityofideas.rest

import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.util.Log
import be.kdg.cityofideas.model.projects.Projects
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import java.io.DataInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

const val BASE_URL = "http://10.0.2.2:5001/Api"

public class RestClient(private val context: Context?) {
    private fun getConnection(urlString: String): HttpURLConnection {
        val connectionManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val netWorkInfo = connectionManager.activeNetworkInfo
        if (netWorkInfo != null && netWorkInfo.isConnected()) {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            //De Kotlin apply functie laat toe om eenvoudig een aantal functies toe te passen op het connection object...
            connection.apply {
                setConnectTimeout(15000) //We wachten maximaal 15 seconden op een connectie
                setReadTimeout(10000) //Hebben we een connectie, dan wachten we maximaal 10 seconden op een antwoord
                setRequestMethod("GET")
                setRequestProperty("connection","close")
                connect()
            }
            return connection
        }
        throw IOException("Unable to connect to network");
    }

    fun getProjects(): Observable<Array<Projects>> {
        val observable = Observable.create<Array<Projects>> { emitter ->
            try {
                val connection = getConnection("${BASE_URL}/projects")
                val json = InputStreamReader(connection.inputStream)
                Log.d("help", json.toString())
                val gson = GsonBuilder().create()
                val projects =
                    gson.fromJson(json, Array<Projects>::class.java)
                projects.forEach {
                    Log.d("help", it.projectName)
                }
                emitter.onNext(projects)
            } catch (e: Exception) {
                emitter.onError(e)
                Log.d("help", "er werkt iets niet")

            }

        }

        return observable
    }
}