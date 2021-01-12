package call.ofbeer.api

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toolbar
import call.ofbeer.activities.LoginActivity
import call.ofbeer.activities.MainActivity
import call.ofbeer.activities.WelcomeActivity
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

    val ExpiredDate: Long
        get(){
            return pref.getLong("ExpiredDate", 0)
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
        editor.putLong("ExpiredDate", System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(60));
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



    val userID :Int
        get(){
            return pref.getInt("userID", 0)
        }

    fun getIdUser(id:Int){
        editor.putBoolean(IS_LOGIN,true)
        editor.putInt("userID",id)
        editor.commit()
    }

    val userEmail :String?
        get(){
            return pref.getString("userEmail", null)
        }

    fun getUserEmail(email:String?){
        editor.putBoolean(IS_LOGIN,true)
        editor.putString("userEmail",email)
        editor.putLong("ExpiredDate", System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(60));
        editor.commit()
    }

    val userSurname :String?
        get(){
            return pref.getString("userSurname", null)
        }

    fun getUserSurname(surname:String?){
        editor.putBoolean(IS_LOGIN,true)
        editor.putString("userSurname",surname)
        editor.commit()
    }

    val userName :String?
        get(){
            return pref.getString("userName", null)
        }

    fun getUserName(userName:String?){
        editor.putBoolean(IS_LOGIN,true)
        editor.putString("userName",userName)
        editor.commit()
    }

    val beerAlcVolume :String?
        get(){
            return pref.getString("beerAlcVolume", null)
        }

    fun getbeerAlcVolume(name:String?){
        editor.putBoolean(IS_LOGIN,true)
        editor.putString("beerAlcVolume",beerAlcVolume)
        editor.commit()
    }



    fun getDetailOfUser(email: String) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putString("EMAIL", email)
        editor.putLong("ExpiredDate", System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(60));
        editor.commit()
    }

    val search :String?
        get(){
            return pref.getString("search", null)
        }

    fun searchBeer(search:String?){
        editor.putBoolean(IS_LOGIN,true)
        editor.putString("search",search)
        editor.commit()
    }

    val goupID :Int
        get(){
            return pref.getInt("goupID", 0)
        }

    fun getIdOfGroup(goupID :Int) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putInt("goupID", goupID)
        editor.commit()
    }

    val groupName :String?
        get(){
            return pref.getString("groupName", null)
        }

    fun getNameOfGroup(groupName :String) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putString("groupName", groupName)
        editor.commit()
    }



    val fragmentRedirect :Int
        get(){
            return pref.getInt("fragmentRedirect", 0)
        }

    fun getfragmentRedirect(fragmentRedirect :Int) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putInt("fragmentRedirect", fragmentRedirect)
        editor.commit()
    }



    val differentOption :Int
        get(){
            return pref.getInt("differentOption", 0)
        }

    fun getdifferentOption(differentOption :Int) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putInt("differentOption", differentOption)
        editor.commit()
    }


    val countryId :Int
        get(){
            return pref.getInt("countryId", 0)
        }

    fun getcountryId(countryId :Int) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putInt("countryId", countryId)
        editor.commit()
    }

    val typeId :Int
        get(){
            return pref.getInt("typeId", 0)
        }

    fun gettypeId(typeId :Int) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putInt("typeId", typeId)
        editor.commit()
    }

    val beerId :Int
        get(){
            return pref.getInt("beerId", 0)
        }

    fun getbeerId(beerId :Int) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putInt("beerId", beerId)
        editor.commit()
    }


    val tastingName :String?
        get(){
            return pref.getString("tastingName", null)
        }

    fun gettastingName(tastingName :String) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putString("tastingName", tastingName)
        editor.commit()
    }


    val tastingId :Int
        get(){
            return pref.getInt("tastingId", 0)
        }

    fun gettastingId(tastingId :Int) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putInt("tastingId", tastingId)
        editor.commit()
    }


    val tastingDescription :String?
        get(){
            return pref.getString("tastingDescription", null)
        }

    fun gettastingDescription(tastingDescription :String) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putString("tastingDescription", tastingDescription)
        editor.commit()
    }

    val beerName :String?
        get(){
            return pref.getString("beerName", null)
        }

    fun getbeerName(beerName :String) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putString("beerName", beerName)
        editor.commit()
    }

    val aromaRate :Float
        get(){
            return pref.getFloat("aromaRate", 0F)
        }

    fun getaromaRate(aromaRate :Float) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putFloat("aromaRate", aromaRate)
        editor.commit()
    }

    val colorRate :Float
        get(){
            return pref.getFloat("colorRate", 0F)
        }

    fun getcolorRate(colorRate :Float) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putFloat("colorRate", colorRate)
        editor.commit()
    }

    val tasteRate :Float
        get(){
            return pref.getFloat("tasteRate", 0F)
        }

    fun gettasteRate(tasteRate :Float) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putFloat("tasteRate", tasteRate)
        editor.commit()
    }

    val bitternessRate :Float
        get(){
            return pref.getFloat("bitternessRate", 0F)
        }

    fun getbitternessRate(bitternessRate :Float) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putFloat("bitternessRate", bitternessRate)
        editor.commit()
    }

    val textureRate :Float
        get(){
            return pref.getFloat("textureRate", 0F)
        }

    fun gettextureRate(textureRate :Float) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putFloat("textureRate", textureRate)
        editor.commit()
    }




    fun Logout() {
        editor.clear()
        editor.commit()

        val i = Intent(context, WelcomeActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)
    }

    fun isLoggedIn(): Boolean {
        return pref.getBoolean(IS_LOGIN, false)
    }

}