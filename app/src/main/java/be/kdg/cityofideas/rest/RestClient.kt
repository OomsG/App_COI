package be.kdg.cityofideas.rest

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.util.Log
import be.kdg.cityofideas.model.ideations.IdeaObjects.IdeaObject
import be.kdg.cityofideas.model.ideations.Ideas
import be.kdg.cityofideas.model.Users.Users
import be.kdg.cityofideas.model.ideations.Ideations
import be.kdg.cityofideas.model.ideations.Reactions
import be.kdg.cityofideas.model.projects.Phases
import be.kdg.cityofideas.model.projects.Projects
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.io.InputStreamReader
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

public class RestClient(private val context: Context?) {
    //private val host = "35.187.121.148"
    private val host = "10.0.2.2"
    private val port = 5001
    private val apistring = "/Api/"
    private val https = true

    private val HTTP_PREFIX = "http://"
    private val HTTPS_PREFIX = "https://"

    //region Connection
    private fun getUnsafeOkHttpClient(): OkHttpClient {
        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            })
            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }
            return builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun getClient(): OkHttpClient? {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected()) {
            try {
                val client: OkHttpClient
                if (https) {
                    client = getUnsafeOkHttpClient()
                } else {
                    client = OkHttpClient()
                }
                return client
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
        return null;
    }


    //endregion

    //region Projects
    //region GET
    fun getProjects(url: String): Observable<Array<Projects>> {
        val prefix: String = if (https) {
            HTTPS_PREFIX
        } else HTTP_PREFIX
        val observable = Observable.create<Array<Projects>> {
            try {
                val request = Request.Builder().url(prefix + host + ":" + port + apistring + url).build()
                val response = getClient()?.newCall(request)?.execute()?.body()
                val json = InputStreamReader(response?.string()?.byteInputStream())
                val gson = GsonBuilder().create()
                val projects = gson.fromJson(json, Array<Projects>::class.java)
                it.onNext(projects)
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
        return observable
    }
    //endregion

    //endregion

    //region Ideations
    //region GET
    fun getIdeations(url: String): Observable<Array<Ideations>> {
        val prefix: String = if (https) {
            HTTPS_PREFIX
        } else HTTP_PREFIX
        val observable = Observable.create<Array<Ideations>> {
            try {
                val request = Request.Builder().url(prefix + host + ":" + port + apistring + url).build()
                val response = getClient()?.newCall(request)?.execute()?.body()
                val json = InputStreamReader(response?.string()?.byteInputStream())
                val gson = GsonBuilder().create()
                val ideations = gson.fromJson(json, Array<Ideations>::class.java)
                it.onNext(ideations)
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
        return observable
    }

    fun getReactions(url: String): Observable<Array<Reactions>> {
        val prefix: String = if (https) {
            HTTPS_PREFIX
        } else HTTP_PREFIX
        val observable = Observable.create<Array<Reactions>> {
            try {
                val request = Request.Builder().url(prefix + host + ":" + port + apistring + url).build()
                val response = getClient()?.newCall(request)?.execute()?.body()
                val json = InputStreamReader(response?.string()?.byteInputStream())
                val gson = GsonBuilder().create()
                val reactions = gson.fromJson(json, Array<Reactions>::class.java)
                it.onNext(reactions)
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
        return observable
    }

    fun getPhases(url: String): Observable<Array<Phases>> {
        val prefix: String = if (https) {
            HTTPS_PREFIX
        } else HTTP_PREFIX
        val observable = Observable.create<Array<Phases>> {
            try {
                val request = Request.Builder().url(prefix + host + ":" + port + apistring + url).build()
                val response = getClient()?.newCall(request)?.execute()?.body()
                val json = InputStreamReader(response?.string()?.byteInputStream())
                val gson = GsonBuilder().create()
                val phases = gson.fromJson(json, Array<Phases>::class.java)
                it.onNext(phases)
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
        return observable
    }

    fun getIdeas(url: String): Observable<Array<Ideas>> {
        val prefix: String = if (https) {
            HTTPS_PREFIX
        } else HTTP_PREFIX
        val observable = Observable.create<Array<Ideas>> {
            try {
                val request = Request.Builder().url(prefix + host + ":" + port + apistring + url).build()
                val response = getClient()?.newCall(request)?.execute()?.body()
                val json = InputStreamReader(response?.string()?.byteInputStream())
                val gson = GsonBuilder().create()
                val ideas = gson.fromJson(json, Array<Ideas>::class.java)
                it.onNext(ideas)
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
        return observable
    }
    //endregion
    //endregion
    //region Users
    fun getUser(url: String) : Observable<Users> {
        val prefix: String = if (https) {
            HTTPS_PREFIX
        } else HTTP_PREFIX
        val observable = Observable.create<Users> {
            try {
                val request = Request.Builder().url(prefix + host + ":" + port + apistring + url).build()
                val response = getClient()?.newCall(request)?.execute()?.body()
                val json = InputStreamReader(response?.string()?.byteInputStream())
                val gson = GsonBuilder().create()
                val user = gson.fromJson(json, Users::class.java)
                it.onNext(user)
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
        return observable
    }
    //endregion


}