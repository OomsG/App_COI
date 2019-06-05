package be.kdg.cityofideas.login

import android.annotation.SuppressLint
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import be.kdg.cityofideas.R
import be.kdg.cityofideas.activities.helper
import be.kdg.cityofideas.activities.manager
import be.kdg.cityofideas.model.users.User
import be.kdg.cityofideas.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

var loggedInUser: LoggedInUserView? = null

class LoginActivity : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var login: Button
    private lateinit var noAccount: TextView
    private lateinit var loading: ProgressBar
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var email: EditText
    private lateinit var confirmPwd: EditText
    private var registering = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initialiseViews()
        addEventHandlers()
        setUpLoginViewModel()
    }

    private fun initialiseViews() {
        login = findViewById(R.id.login)
        noAccount = findViewById(R.id.CreateAccountText)
        loading = findViewById(R.id.loginLoading)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        email = findViewById(R.id.email)
        confirmPwd = findViewById(R.id.confirmPassword)
    }

    @SuppressLint("CheckResult")
    private fun addEventHandlers() {
        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                registering,
                username.text.toString(),
                email.text.toString(),
                password.text.toString(),
                confirmPwd.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    registering,
                    username.text.toString(),
                    email.text.toString(),
                    password.text.toString(),
                    confirmPwd.text.toString()
                )
            }
        }

        email.afterTextChanged {
            loginViewModel.loginDataChanged(
                registering,
                username.text.toString(),
                email.text.toString(),
                password.text.toString(),
                confirmPwd.text.toString()
            )
        }

        confirmPwd.afterTextChanged {
            loginViewModel.loginDataChanged(
                registering,
                username.text.toString(),
                email.text.toString(),
                password.text.toString(),
                confirmPwd.text.toString()
            )
        }


//            setOnEditorActionListener { _, actionId, _ ->
//                when (actionId) {
//                    EditorInfo.IME_ACTION_DONE ->
//                        loginViewModel.login(
//                            context,
//                            username.text.toString(),
//                            password.text.toString()
//                        )
//                }
//                false
//            }

        login.setOnClickListener {
            loading.visibility = View.VISIBLE

            if (!registering) {
                RestClient(this).getUser("users/login", username.text.toString(), password.text.toString())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                        val loggedInUser = User(
                            it.Id,
                            it.UserName,
                            it.Email,
                            it.Platform,
                            it.PasswordHash,
                            it.Surname,
                            it.Name,
                            it.Sex,
                            it.Age,
                            it.Zipcode,
                            it.Votes
                        )
                        loginViewModel.login(loggedInUser, this)
                    }
            } else {
                if (!userExistsAlready(username.text.toString(), email.text.toString())) {
                    RestClient(this).createUser("users/register", username.text.toString(), email.text.toString(), password.text.toString())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe {
                            val loggedInUser = User(
                                it.Id,
                                it.UserName,
                                it.Email,
                                it.Platform,
                                it.PasswordHash,
                                it.Surname,
                                it.Name,
                                it.Sex,
                                it.Age,
                                it.Zipcode,
                                it.Votes
                            )
                            loginViewModel.login(loggedInUser, this)
                        }
                }
            }
        }

        noAccount.setOnClickListener {
            if (!registering) {
                login.text = getString(R.string.register)
                noAccount.text = getString(R.string.already_account)
                username.hint = getString(R.string.username)
                email.visibility = View.VISIBLE
                confirmPwd.visibility = View.VISIBLE
                registering = true
            } else {
                login.text = getString(R.string.log_in)
                noAccount.text = getString(R.string.no_account)
                username.hint = getString(R.string.prompt_username)
                email.visibility = View.GONE
                confirmPwd.visibility = View.GONE
                registering = false
            }
        }
    }

    private fun setUpLoginViewModel() {
        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless login/registering values are valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.emailError != null) {
                email.error = getString(loginState.emailError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
            if (loginState.confirmPwdError != null) {
                confirmPwd.error = getString(loginState.confirmPwdError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        loggedInUser = model

        Toast.makeText(
            applicationContext,
            "$welcome ${model.UserName}!",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    private fun userExistsAlready(username: String, email: String): Boolean {
        val c = manager.getDetails(
            helper.getUserEntry().TBL_USER,
            null,
            "${helper.getUserEntry().USER_NAME} LIKE ? OR ${helper.getUserEntry().USER_EMAIL} LIKE ?",
            arrayOf(username, email),
            null, null, null
        )

        return c.moveToFirst()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
