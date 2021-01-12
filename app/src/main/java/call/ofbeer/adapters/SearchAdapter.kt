package call.ofbeer.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import call.ofbeer.R
import call.ofbeer.api.SessionManager
import call.ofbeer.models.Beer
import kotlinx.android.synthetic.main.fragment_beer_item.view.*

class SearchAdapter(var context: Context, var beers: List<Beer> = arrayListOf()) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_search_list, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = beers.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindBeer(beers[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var session: SessionManager

        var context: Context = itemView.context

        var fragmentManager =
            (view.context as FragmentActivity).supportFragmentManager //to handle context
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

