package call.ofbeer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import call.ofbeer.R
import call.ofbeer.models.Type
import kotlinx.android.synthetic.main.fragment_beer_item.view.*

class TypeAdapter(var context: Context, var types: List<Type> = arrayListOf()) :
    RecyclerView.Adapter<TypeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_type_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = types.size

    override fun onBindViewHolder(holder: TypeAdapter.ViewHolder, position: Int) {
        holder.bindType(types[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindType(type: Type){

            itemView.main_info.text = type.name

        }
}
    }