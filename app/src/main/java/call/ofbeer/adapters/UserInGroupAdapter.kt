package call.ofbeer.adapters

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import call.ofbeer.R
import call.ofbeer.api.RetrofitClient
import call.ofbeer.api.SessionManager
import call.ofbeer.api.SuccesfulResponse
import call.ofbeer.fragments.ManageGroupFragment
import call.ofbeer.models.User
import kotlinx.android.synthetic.main.fragment_beer_item.view.*
import retrofit2.Call
import retrofit2.Response

class UserInGroupAdapter(var context: Context, var users: List<User> = arrayListOf()) :
    RecyclerView.Adapter<UserInGroupAdapter.ViewHolder>() {

    private var isVisible = false
    lateinit var session: SessionManager

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserInGroupAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_users_in_group_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserInGroupAdapter.ViewHolder, position: Int) {
        if (isVisible) {
            holder.btn_remove.visibility = View.VISIBLE
        } else {
            holder.btn_remove.visibility = View.GONE
        }
        holder.bindUser(users[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var btn_remove = view.findViewById<Button>(R.id.btn_remove)
        var context: Context = itemView.context

        var fragmentManager =
            (view.context as FragmentActivity).supportFragmentManager //to handle context


        fun bindUser(user: User) {

            session = SessionManager(context)

            btn_remove.setOnClickListener {
                val dialogBuilder = AlertDialog.Builder(context)
                dialogBuilder.setMessage("Napewno chcesz usunąć tego użytkownika z grupy?")
                    .setCancelable(false)
                    .setPositiveButton("Usuń") { dialog, id ->
                        RetrofitClient.instance.leaveGroup(session.TOKEN, session.goupID, user.id)
                            .enqueue(object : retrofit2.Callback<SuccesfulResponse> {
                                override fun onFailure(
                                    call: Call<SuccesfulResponse>,
                                    t: Throwable
                                ) {
                                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()
                                }

                                override fun onResponse(
                                    call: Call<SuccesfulResponse>,
                                    response: Response<SuccesfulResponse>
                                ) {
                                    Toast.makeText(
                                        context,
                                        "Użytkownik usunięty z grupy",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val fragmentTransaction = fragmentManager.beginTransaction()
                                    fragmentTransaction.replace(
                                        R.id.nav_host_fragment,
                                        ManageGroupFragment()
                                    )
                                        .addToBackStack(null)
                                        .commit()
                                    dialog.dismiss()
                                }

                            })

                    }
                    .setNegativeButton("Anuluj") { dialog, id ->
                        dialog.dismiss()
                    }

                val alert = dialogBuilder.create()
                alert.show()

                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED)
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GREEN)


            }

            itemView.main_info.text = user.firstname
            itemView.add_info.text = user.email
        }


    }

    fun setVisibility() {
        isVisible = true
        notifyDataSetChanged()
    }
}