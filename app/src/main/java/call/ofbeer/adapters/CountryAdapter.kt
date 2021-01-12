package call.ofbeer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import call.ofbeer.R
import call.ofbeer.models.Country
import kotlinx.android.synthetic.main.fragment_country_item.view.*

class CountryAdapter (var context: Context, var countries: List<Country> = arrayListOf()) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_country_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = countries.size

    override fun onBindViewHolder(holder: CountryAdapter.ViewHolder, position: Int) {
        holder.bindCountry(countries[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindCountry(country: Country){
            itemView.main_info.text = country.name
        }
    }

    }