package be.kdg.cityofideas.rest

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
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
import io.reactivex.Observable
import okhttp3.*
import java.io.IOException
import android.util.Base64
import android.util.Log
import be.kdg.cityofideas.login.LoggedInUserView
import be.kdg.cityofideas.model.IoT.IoTSetup
import be.kdg.cityofideas.model.ideations.*
import com.google.android.youtube.player.internal.i
import org.json.JSONObject
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class RestClient(private val context: Context?) {
    // online
//    private val host = "34.76.196.101"
//    private val port = 5000
//    private val https = false

    // offline
    private val host = "10.0.2.2"
    private val port = 5001
    private val https = true

    private val apistring = "/Api/"
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
                idea.IdeaObjects?.forEach {
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
                it.onNext(idea)
                it.onComplete()
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
        return observable
    }

    fun getSharedVotes(url: String): Observable<Array<Vote>> {
        val prefix: String = if (https) {
            HTTPS_PREFIX
        } else HTTP_PREFIX
        val observable = Observable.create<Array<Vote>> {
            try {
                val request = Request.Builder().url(prefix + host + ":" + port + apistring + url).build()
                val response = getClient()?.newCall(request)?.execute()?.body()?.string()
                val gson = GsonBuilder().create()
                val vote = gson.fromJson(response, Array<Vote>::class.java)
                if (vote != null) {
                    it.onNext(vote)
                }
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
        return observable
    }
    //endregion
    //region POST
    fun createReaction(param: String, userId: String, id: Int, element: String) {
        val formBody = FormBody.Builder()
            .add("param", param)
            .add("userId", userId)
            .add("id", id.toString())
            .add("element", element)
        val gson = Gson().toJson(formBody)
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson)
        val request = Request.Builder()
            .url(HTTPS_PREFIX + host + ":" + port + apistring + "reaction")
            //headers post the data
            .header("param", param)
            .header("userId", userId)
            .header("id", id.toString())
            .header("element", element)
            //body is needed for rider to know it's a post request
            .post(body)
            .build()
        try {
            getClient()!!.newCall(request).execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun createVote(ideaId: Int, voteType: VoteType, userId: String) {
        val formBody = FormBody.Builder()
            .add("userId", userId)
            .add("id", ideaId.toString())
            .add("vote", voteType.toString()).build()
        val gson = Gson().toJson(formBody)
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson)
        val request = Request.Builder()
            .url(HTTPS_PREFIX + host + ":" + port + apistring + "vote")
            //headers post the data
            .header("id", ideaId.toString())
            .header("vote", voteType.toString())
            .header("userId", userId)
            //body is needed for rider to know it's a post request
            .post(body)
            .build()

        try {
            getClient()!!.newCall(request).execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    //work in progress
    fun createIdea(postList: ArrayList<IdeaObject>, ideationId: Int, id: String) {

        val json = JSONObject()

        postList.forEach {
            when (it.Discriminator) {
                "image" -> {
                    val formBody = FormBody.Builder()
                        .add("discriminator", it.Discriminator)
                        .add("image", it.ImageName!!)
                        .build()

                    for (i in 0 until formBody.size()) {
                        json.put(formBody.encodedName(i), formBody.encodedValue(i))
                    }
                }
                "text" -> {
                    val formBody = FormBody.Builder()
                        .add("discriminator", it.Discriminator)
                        .add("text", it.Text!!)
                        .build()

                    for (i in 0 until formBody.size()) {
                        json.put(formBody.encodedName(i), formBody.encodedValue(i))
                    }
                }
            }
        }
        Log.d("json", json.toString())
        val body = RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), json.toString())
        val request = Request.Builder()
            .url(HTTPS_PREFIX + host + ":" + port + apistring + "idea")
            //headers post the data
            .header("userid", id)
            .header("ideationid", ideationId.toString())
            //body is needed for rider to know it's a post request
            .post(body)
            .build()

        try {
            getClient()!!.newCall(request).execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun createLike(reactionId: Int,userId: String){
        val formBody = FormBody.Builder()
            .add("userId", userId)
            .add("id", reactionId.toString())
            .build()
        val gson = Gson().toJson(formBody)
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson)
        val request = Request.Builder()
            .url(HTTPS_PREFIX + host + ":" + port + apistring + "like")
            //headers post the data
            .header("reactionId", reactionId.toString())
            .header("userId", userId)
            //body is needed for rider to know it's a post request
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
    //region GET
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
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
        return observable
    }

    fun getUser(url: String, username: String, password: String): Observable<User> {
        val prefix: String = if (https) {
            HTTPS_PREFIX
        } else HTTP_PREFIX
        val observable = Observable.create<User> {
            try {
                val request = Request.Builder()
                    .url(prefix + host + ":" + port + apistring + url)
                    .header(
                        "Username",
                        Base64.encodeToString(username.toByteArray(Charsets.UTF_8), Base64.NO_WRAP).toString()
                    )
                    .header(
                        "Password",
                        Base64.encodeToString(password.toByteArray(Charsets.UTF_8), Base64.NO_WRAP).toString()
                    )
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

    fun createUser(url: String, username: String, email: String, password: String): Observable<User> {
        val prefix: String = if (https) {
            HTTPS_PREFIX
        } else HTTP_PREFIX
        val observable = Observable.create<User> {
            try {
                val request = Request.Builder()
                    .url(prefix + host + ":" + port + apistring + url)
                    .header(
                        "Username",
                        Base64.encodeToString(username.toByteArray(Charsets.UTF_8), Base64.NO_WRAP).toString()
                    )
                    .header(
                        "Email",
                        Base64.encodeToString(email.toByteArray(Charsets.UTF_8), Base64.NO_WRAP).toString()
                    )
                    .header(
                        "Password",
                        Base64.encodeToString(password.toByteArray(Charsets.UTF_8), Base64.NO_WRAP).toString()
                    )
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
    //endregion
    //region PUT
    fun updateUser(user: LoggedInUserView) {
        val formBody = FormBody.Builder()
            // encode for special characters
            .add("Surname", Base64.encodeToString(user.Surname.toString().toByteArray(Charsets.UTF_8), Base64.NO_WRAP).toString())
            .add("LastName", Base64.encodeToString(user.Name.toString().toByteArray(Charsets.UTF_8), Base64.NO_WRAP).toString())
            .add("Sex", user.Sex.toString())
            .add("Age", user.Age.toString())
            .add("ZipCode", user.Zipcode.toString())
            .build()

        val json = JSONObject()
        for (i in 0 until formBody.size()) {
            json.put(formBody.encodedName(i), formBody.encodedValue(i))
        }

        val body = RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), json.toString())

        val request = Request.Builder()
            .url(HTTPS_PREFIX + host + ":" + port + apistring + "users/update")
            //headers post the data
            .header("Username", user.UserName)
            //body is needed for rider to know it's a post request
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

    //region Survey
    //region GET
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
    //region POST
    fun postAnswers(url: String, surveyId: Int, answers: MutableMap<Int, Array<String>>) {
        val jsonArray = mutableListOf<JSONObject>()

        for ((k, v) in answers) {
            v.forEach {
                val formBody = FormBody.Builder()
                    // encode for special characters
                    .add("AnswerText", Base64.encodeToString(it.toByteArray(Charsets.UTF_8), Base64.NO_WRAP).toString())
                    .add("QuestionNr", k.toString())
                    .build()

                val json = JSONObject()

                for (i in 0 until formBody!!.size()) {
                    json.put(formBody.encodedName(i), formBody.encodedValue(i))
                }

                jsonArray.add(json)
            }
        }

        val body = RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), jsonArray.toString())

        val request = Request.Builder()
            .url(HTTPS_PREFIX + host + ":" + port + apistring + url)
            .header("SurveyId", surveyId.toString())
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

    //region Tag
    //region GET
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
    //endregion

    //region IoT
    fun getIoTSetups(url: String): Observable<Array<IoTSetup>> {
        val prefix: String = if (https) {
            HTTPS_PREFIX
        } else HTTP_PREFIX
        val observable = Observable.create<Array<IoTSetup>> {
            try {
                val request = Request.Builder().url(prefix + host + ":" + port + apistring + url).build()
                val response = getClient()?.newCall(request)?.execute()?.body()?.string()
                val gson = GsonBuilder().create()
                val iotSetups = gson.fromJson(response, Array<IoTSetup>::class.java)
                it.onNext(iotSetups)
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
        return observable
    }
    //endregion
}