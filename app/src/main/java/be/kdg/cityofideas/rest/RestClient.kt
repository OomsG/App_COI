package be.kdg.cityofideas.rest

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.util.Base64
import android.util.Log
import be.kdg.cityofideas.login.Credentials
import be.kdg.cityofideas.model.ideations.Idea
import be.kdg.cityofideas.model.ideations.Ideation
import be.kdg.cityofideas.model.ideations.Reaction
import be.kdg.cityofideas.model.ideations.Tag
import be.kdg.cityofideas.model.projects.Phase
import be.kdg.cityofideas.model.projects.Project
import be.kdg.cityofideas.model.surveys.Question
import be.kdg.cityofideas.model.surveys.Survey
import be.kdg.cityofideas.model.users.User
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import io.reactivex.Observable
import okhttp3.*
import java.io.IOException
import java.util.*
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

    //region Project
    //region GET
    fun getProjects(url: String): Observable<Array<Project>> {
        val prefix: String = if (https) {
            HTTPS_PREFIX
        } else HTTP_PREFIX
        val observable = Observable.create<Array<Project>> {
            try {
                val request = Request.Builder().url(prefix + host + ":" + port + apistring + url).build()
                val response = getClient()?.newCall(request)?.execute()?.body()?.string()
                val gson = GsonBuilder().create()
                val projects = gson.fromJson(response, Array<Project>::class.java)
                projects.forEach {
                    val ImageRequest = Request.Builder().url(prefix + host + ":" + port + it.BackgroundImage).build()
                    val imageResponse = getClient()?.newCall(ImageRequest)?.execute()?.body()?.byteStream()
                    it.BackgroundIMG = BitmapFactory.decodeStream(imageResponse)
                    it.Phases?.forEach {
                        it.Ideations?.forEach {
                            it.Ideas?.forEach {
                                it.IdeaObjects?.forEach {
                                    try {
                                        if (it.ImageName!!.isNotEmpty()) {
                                            val ImageRequestIdea =
                                                Request.Builder().url(prefix + host + ":" + port + it.ImagePath).build()
                                            val imageResponseIdea =
                                                getClient()?.newCall(ImageRequestIdea)?.execute()?.body()?.byteStream()
                                            it.Image = BitmapFactory.decodeStream(imageResponseIdea)
                                        }
                                    } catch (e: NullPointerException) {

                                    }
                                }
                            }
                        }
                    }
                }
                it.onNext(projects)
                it.onComplete()
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
        return observable
    }
    //endregion

    //endregion
    //region Ideation
    //region GET
    fun getIdeations(url: String): Observable<Array<Ideation>> {
        val prefix: String = if (https) {
            HTTPS_PREFIX
        } else HTTP_PREFIX
        val observable = Observable.create<Array<Ideation>> {
            try {
                val request = Request.Builder().url(prefix + host + ":" + port + apistring + url).build()
                val response = getClient()?.newCall(request)?.execute()?.body()?.string()
                val gson = GsonBuilder().create()
                val ideations = gson.fromJson(response, Array<Ideation>::class.java)
                ideations.forEach {
                    it.Ideas?.forEach {
                        it.IdeaObjects?.forEach {
                            try {
                                if (it.ImageName!!.isNotEmpty()) {
                                    val ImageRequest =
                                        Request.Builder().url(prefix + host + ":" + port + it.ImagePath).build()
                                    val imageResponse =
                                        getClient()?.newCall(ImageRequest)?.execute()?.body()?.byteStream()
                                    it.Image = BitmapFactory.decodeStream(imageResponse)
                                }
                            } catch (e: NullPointerException) {

                            }
                        }
                    }
                }
                it.onNext(ideations)
                it.onComplete()
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
        return observable
    }


    fun getSurveys(url: String): Observable<Array<Survey>> {
        val prefix: String = if (https) {
            HTTPS_PREFIX
        } else HTTP_PREFIX
        val observable = Observable.create<Array<Survey>> {
            try {
                val request = Request.Builder().url(prefix + host + ":" + port + apistring + url).build()
                val response = getClient()?.newCall(request)?.execute()?.body()?.string()
                val gson = GsonBuilder().create()
                val surveys = gson.fromJson(response, Array<Survey>::class.java)
                it.onNext(surveys)
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
        return observable
    }




    fun getReactions(url: String): Observable<Array<Reaction>> {
        val prefix: String = if (https) {
            HTTPS_PREFIX
        } else HTTP_PREFIX
        val observable = Observable.create<Array<Reaction>> {
            try {
                val request = Request.Builder().url(prefix + host + ":" + port + apistring + url).build()
                val response = getClient()?.newCall(request)?.execute()?.body()?.string()
                val gson = GsonBuilder().create()
                val reactions = gson.fromJson(response, Array<Reaction>::class.java)
                it.onNext(reactions)
                it.onComplete()
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
        return observable
    }

    fun getPhases(url: String): Observable<Array<Phase>> {
        val prefix: String = if (https) {
            HTTPS_PREFIX
        } else HTTP_PREFIX
        val observable = Observable.create<Array<Phase>> {
            try {
                val request = Request.Builder().url(prefix + host + ":" + port + apistring + url).build()
                val response = getClient()?.newCall(request)?.execute()?.body()?.string()
                val gson = GsonBuilder().create()
                val phases = gson.fromJson(response, Array<Phase>::class.java)
                it.onNext(phases)
                it.onComplete()
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
        return observable
    }

    fun getIdea(url: String): Observable<Idea> {
        val prefix: String = if (https) {
            HTTPS_PREFIX
        } else HTTP_PREFIX
        val observable = Observable.create<Idea> {
            try {
                val request = Request.Builder().url(prefix + host + ":" + port + apistring + url).build()
                val response = getClient()?.newCall(request)?.execute()?.body()?.string()
                val gson = GsonBuilder().create()
                val idea = gson.fromJson(response, Idea::class.java)
                it.onNext(idea)
                it.onComplete()
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
        return observable
    }
    //endregion
    //region Put

    fun createVote(ideaId: Int, voteType: VoteType, userId: String) {

        val formBody = FormBody.Builder()
            .add("IdeaId", ideaId.toString())
            .add("voteType", voteType.toString()).build()

        val gson = Gson().toJson(formBody)
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),gson)
        val request = Request.Builder()
            .url(HTTPS_PREFIX + host + ":" + port + apistring + "vote")
            .post(body)
            .build()
        try {
            getClient()!!.newCall(request).execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    //endregion
    //endregion

    //region User
    fun getUser(url: String, email: String, password: String) : Observable<User> {
        val prefix: String = if (https) {
            HTTPS_PREFIX
        } else HTTP_PREFIX
        val observable = Observable.create<User> {
            try {
                val request = Request.Builder()
                    .url(prefix + host + ":" + port + apistring + url)
                    .header("Email", Base64.encodeToString(email.toByteArray(Charsets.UTF_8), Base64.NO_WRAP).toString())
                    .header("Password", Base64.encodeToString(password.toByteArray(Charsets.UTF_8), Base64.NO_WRAP).toString())
                    .build()

                val response = getClient()?.newCall(request)?.execute()?.body()?.string()
                val gson = GsonBuilder().create()
                val user = gson.fromJson(response, User::class.java)
                it.onNext(user)
                it.onComplete()
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
        return observable
    }

    fun getTokens(url: String, email: String, password: String) : Observable<Credentials> {
        val prefix: String = if (https) {
            HTTPS_PREFIX
        } else HTTP_PREFIX
        val observable = Observable.create<Credentials> {
            try {
                val request = Request.Builder()
                    .url(prefix + host + ":" + port + apistring + url)
                    .header("Email", Base64.encodeToString(email.toByteArray(Charsets.UTF_8), Base64.NO_WRAP).toString())
                    .header("Password", Base64.encodeToString(password.toByteArray(Charsets.UTF_8), Base64.NO_WRAP).toString())
                    .build()

                val response = getClient()?.newCall(request)?.execute()?.body()?.string()
                val gson = GsonBuilder().create()
                val credentials = gson.fromJson(response, Credentials::class.java)
                it.onNext(credentials)
                it.onComplete()
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
        return observable
    }

    fun getUsers(url: String): Observable<Array<User>> {
        val prefix: String = if (https) {
            HTTPS_PREFIX
        } else HTTP_PREFIX
        val observable = Observable.create<Array<User>> {
            try {
                val request = Request.Builder().url(prefix + host + ":" + port + apistring + url).build()
                val response = getClient()?.newCall(request)?.execute()?.body()?.string()
                val gson = GsonBuilder().create()
                val users = gson.fromJson(response, Array<User>::class.java)
                it.onNext(users)
                it.onComplete()
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
        return observable
    }


    //endregion
    //region Survey
    fun getQuestions(url: String): Observable<Array<Question>> {
        val prefix: String = if (https) {
            HTTPS_PREFIX
        } else HTTP_PREFIX
        val observable = Observable.create<Array<Question>> {
            try {
                val request = Request.Builder().url(prefix + host + ":" + port + apistring + url).build()
                val response = getClient()?.newCall(request)?.execute()?.body()?.string()
                val gson = GsonBuilder().create()
                val questions = gson.fromJson(response, Array<Question>::class.java)
                it.onNext(questions)
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
        return observable
    }
    //endregion
    //region Tag
    fun getTags(url: String): Observable<Array<Tag>> {
        val prefix: String = if (https) {
            HTTPS_PREFIX
        } else HTTP_PREFIX
        val observable = Observable.create<Array<Tag>> {
            try {
                val request = Request.Builder().url(prefix + host + ":" + port + apistring + url).build()
                val response = getClient()?.newCall(request)?.execute()?.body()?.string()
                val gson = GsonBuilder().create()
                val tags = gson.fromJson(response, Array<Tag>::class.java)
                it.onNext(tags)
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
        return observable
    }
    //endregion
}