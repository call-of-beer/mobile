package call.ofbeer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import call.ofbeer.R
import call.ofbeer.api.SessionManager
import call.ofbeer.fragments.TastingDetailsFragment
import call.ofbeer.models.Tasting
import kotlinx.android.synthetic.main.fragment_tasting_item.view.*

class TastingAdapter(var context: Context, var tastings: List<Tasting> = arrayListOf()) :
    RecyclerView.Adapter<TastingAdapter.ViewHolder>() {

    lateinit var session: SessionManager


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TastingAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_tasting_item, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int = tastings.size

    override fun onBindViewHolder(holder: TastingAdapter.ViewHolder, position: Int) {
        holder.bindTasting(tastings[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var seeBtn = view.findViewById<Button>(R.id.btn_see)
        var context: Context = itemView.context
        var fragmentManager =
            (view.context as FragmentActivity).supportFragmentManager //to handle context


        fun bindTasting(tastings: Tasting) {

            session = SessionManager(context)

            itemView.name_of_tasting.text = tastings.title
            itemView.add_info.text = tastings.description

            seeBtn.setOnClickListener {
                session.getbeerName(tastings.beer.name)
                session.getbeerAlcVolume(tastings.beer.alcoholVolume)
                session.getaromaRate(tastings.beer.avgAroma)
                session.getcolorRate(tastings.beer.avgColor)
                session.gettasteRate(tastings.beer.avgTaste)
                session.getbitternessRate(tastings.beer.avgBitterness)
                session.gettextureRate(tastings.beer.avgTexture)
                session.gettastingId(tastings.id)
                session.getbeerId(tastings.beer.id)


                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.nav_host_fragment, TastingDetailsFragment())
                    .addToBackStack(null)
                    .commit()
            }


        }
    }


}