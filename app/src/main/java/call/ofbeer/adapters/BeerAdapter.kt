package call.ofbeer.adapters

import android.app.AlertDialog
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import call.ofbeer.R


//import call.ofbeer.fragments.BeerFragment.OnListFragmentInteractionListener
import call.ofbeer.models.Beer

import kotlinx.android.synthetic.main.fragment_beer.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class BeerAdapter(var context: Context, var beers: List<Beer> = arrayListOf()) :
    RecyclerView.Adapter<BeerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_beer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindBeer(beers[position])
    }

    override fun getItemCount(): Int = beers.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var seeBtn = view.findViewById<Button>(R.id.btn_see)

        fun bindBeer(beer: Beer) {

            seeBtn.setOnClickListener {

                val dialogBuilder = AlertDialog.Builder(context)
                with(dialogBuilder)
                {
                    setTitle("User details")
                    setMessage(
                        "Name: " + beer.name + System.lineSeparator() +
                                "Alcohol Volume: " + beer.alcoholVolume + System.lineSeparator() +
                                "Country: " + beer.country + System.lineSeparator() +
                                "Desctription: " + beer.description + System.lineSeparator()
                    )
                }
                    .setCancelable(false)
                    .setPositiveButton("Ok") { dialog, id ->
                        dialog.dismiss()
                    }
                val alert = dialogBuilder.create()
                alert.show()
            }

            itemView.main_info.text = beer.name
            itemView.add_info.text = beer.alcoholVolume
            itemView.more_info.text = beer.description
        }
    }
}
