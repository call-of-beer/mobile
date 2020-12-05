package call.ofbeer.api

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import call.ofbeer.activities.LoginActivity
import call.ofbeer.activities.MainActivity
import java.util.concurrent.TimeUnit

class SessionManager {

    var pref: SharedPreferences
    var editor: SharedPreferences.Editor
    var context: Context

    constructor(context: Context) {
        this.context = context
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = pref.edit()
    }

    val TOKEN: String
        get() {
            return "Bearer " + pref.getString(
                "TOKEN",
                null
            ) //jak było TOKEN to nie działało, ale nie przypominam sobie, bym coś zmieniała... czarna magia
        }

    val EMAIL: String?
        get() {
            return pref.getString("EMAIL", null)
        }


    companion object {
        val PREF_NAME = "rateThisBook"
        val IS_LOGIN: String = "isLoggedIn"
        private var mInstance: SessionManager? = null

        @Synchronized
        fun getInstance(context: Context): SessionManager {
            if (mInstance == null) {
                mInstance =
                    SessionManager(context)
            }
            return mInstance as SessionManager

        }
    }

    fun createLoginSession(token: String) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putString("TOKEN", token)
        editor.commit()
    }

    val cookie: String?
        get() {
            return pref.getString("cookie", null)
        }

    fun createCookieSession(cookie: String?) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putString("cookie", cookie)
        editor.commit()
    }

    fun checkLogin() //to wołać jak trzeba będzie się logować :D
    {
        if (!this.isLoggedIn()) {
            var i = Intent(context, LoginActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }
    }


    fun getDetailOfUser(email: String) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putString("EMAIL", email)
        editor.putLong("ExpiredDate", System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(60));
        editor.commit()
    }


    fun Logout() {
        editor.clear()
        editor.commit()

        val i = Intent(context, MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)
    }

    fun isLoggedIn(): Boolean {
        return pref.getBoolean(IS_LOGIN, false)
    }

}