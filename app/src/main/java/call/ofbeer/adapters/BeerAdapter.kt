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
import call.ofbeer.fragments.BeerSeeDetailsFragment
import call.ofbeer.models.Beer
import kotlinx.android.synthetic.main.fragment_beer_item.view.*

class BeerAdapter(var context: Context, var beers: List<Beer> = arrayListOf()) :
    RecyclerView.Adapter<BeerAdapter.ViewHolder>() {

    lateinit var session: SessionManager
    private var isVisible = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_beer_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (isVisible) {
            holder.seeBtn.visibility = View.GONE
        } else {
            holder.seeBtn.visibility = View.VISIBLE
        }
        holder.bindBeer(beers[position])
    }

    override fun getItemCount(): Int = beers.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var seeBtn: Button = view.findViewById(R.id.btn_see)
        var context: Context = itemView.context
        var fragmentManager =
            (view.context as FragmentActivity).supportFragmentManager //to handle context

        fun bindBeer(beer: Beer) {

            session = SessionManager(context)

            seeBtn.setOnClickListener {

                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.nav_host_fragment, BeerSeeDetailsFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()

                session.getbeerName(beer.name)
                session.getbeerAlcVolume(beer.alcoholVolume)
                session.getaromaRate(beer.avgAroma)
                session.getcolorRate(beer.avgColor)
                session.gettasteRate(beer.avgTaste)
                session.getbitternessRate(beer.avgBitterness)
                session.gettextureRate(beer.avgTexture)
                session.getbeerId(beer.id)
                session.getbeerAlcVolume(beer.alcoholVolume)
            }

            itemView.main_info.text = beer.name
            itemView.add_info.text = beer.alcoholVolume
            itemView.more_info.text = beer.description
        }
    }

    fun setVisibility() {
        isVisible = true
        notifyDataSetChanged()
    }
}
