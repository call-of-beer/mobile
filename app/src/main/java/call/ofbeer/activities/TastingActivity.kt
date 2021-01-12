package call.ofbeer.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import call.ofbeer.R
import call.ofbeer.fragments.TastingChooseGroupFragment
import call.ofbeer.fragments.TastingFragment
import call.ofbeer.fragments.TastingSetNameFragment

class TastingActivity: AppCompatActivity() {

    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasting)

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentTasting, TastingSetNameFragment())
        fragmentTransaction.commit()
    }
}