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
import call.ofbeer.fragments.GroupSeeDetailsFragment
import call.ofbeer.fragments.ManageGroupFragment
import call.ofbeer.fragments.TastingShowFragment
import call.ofbeer.models.Group
import kotlinx.android.synthetic.main.fragment_group_item.view.*

class GroupAdapter(var context: Context, var groups: List<Group> = arrayListOf()) :
    RecyclerView.Adapter<GroupAdapter.ViewHolder>() {

    private var isVisible = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_group_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = groups.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(isVisible){
            holder.btn_see.visibility = View.GONE
            holder.btn_edit.visibility = View.VISIBLE
        }else{
            holder.btn_see.visibility = View.VISIBLE
            holder.btn_edit.visibility = View.GONE
        }
        holder.bindGroup(groups[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var session: SessionManager
        var btn_see = view.findViewById<Button>(R.id.btn_see)
        var btn_edit = view.findViewById<Button>(R.id.btn_edit)
        var context: Context = itemView.context

        var fragmentManager =
            (view.context as FragmentActivity).supportFragmentManager //to handle context


        fun bindGroup(group: Group){

            session = SessionManager(context)
            val fragmentTransaction = fragmentManager?.beginTransaction()

            btn_see.setOnClickListener{
                if(session.differentOption==1)
                {
                    fragmentTransaction.replace(R.id.nav_host_fragment, TastingShowFragment())
                        .addToBackStack(null)
                        .commit()
                    session.getIdOfGroup(group.id)
                    session.getNameOfGroup(group.name)

                }else {
                    fragmentTransaction.replace(R.id.nav_host_fragment, GroupSeeDetailsFragment())
                        .addToBackStack(null)
                        .commit()
                    session.getIdOfGroup(group.id)
                    session.getNameOfGroup(group.name)
                }
            }

            btn_edit.setOnClickListener{
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.nav_host_fragment, ManageGroupFragment())
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.commit()
                session.getIdOfGroup(group.id)
                session.getNameOfGroup(group.name)

            }

            itemView.name_of_group.text = group.name

        }
    }

    fun setVisibility() {
        isVisible = true
        notifyDataSetChanged()
    }

}