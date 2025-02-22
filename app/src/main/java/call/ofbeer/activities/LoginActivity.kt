package call.ofbeer.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import call.ofbeer.R
import call.ofbeer.api.LoginResponse
import call.ofbeer.api.RetrofitClient
import call.ofbeer.api.SessionManager
import call.ofbeer.api.Validation
import call.ofbeer.requests.LoginRequest
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        session = SessionManager(applicationContext)

        btn_login.setOnClickListener {


            val email = input_email.text.toString().trim()
            val password = input_password.text.toString().trim()


            if (email.isEmpty()) {
                input_email.error = "Email required"
                input_email.requestFocus()
                return@setOnClickListener
            }


            if (!Validation.isValidEmail(email)) {
                input_email.error = "Enter correct email"
                input_email.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                input_password.error = "Password required"
                input_password.requestFocus()
                return@setOnClickListener
            }



            RetrofitClient.instance.login(LoginRequest(email, password))
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {

                        if (response.code() == 200) {
                            Toast.makeText(applicationContext, "Zalogowany!", Toast.LENGTH_LONG)
                                .show()
                            session.createLoginSession(response.body()?.token!!)

                            session.getDetailOfUser(email)
                            val i = Intent(applicationContext, MainActivity::class.java)
                            startActivity(i)

                        } else
                            Toast.makeText(
                                applicationContext,
                                "Coś poszło nie tak ;< Upewnij się, że wprowadziłeś poprawne dane logowania i spróbuj ponownie",
                                Toast.LENGTH_LONG
                            ).show()
                    }

                })

        }

        btn_signUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


    }


}
