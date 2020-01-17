package epita.marion_romain.cryptofile


import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import epita.marion_romain.cryptofile.Crypto.EntryModel
import epita.marion_romain.cryptofile.Crypto.Model


class CustomRecyclerAdapter(
    val context: Context,
    val data: EntryModel,
    private val onItemClickListener: View.OnClickListener) :
    RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        // create the row from a layout inflater
        val rowView = LayoutInflater
            .from(context)
            .inflate(R.layout.activity_list_item, p0, false)
        // attach the onclicklistener
        rowView.setOnClickListener(onItemClickListener)
        // create a ViewHolder using this rowview
        val viewHolder = ViewHolder(rowView)
        // return this ViewHolder. The system will make sure view holders
        // are used and recycled
        return viewHolder
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        // retrieve the item at the specified position
        val currentItem = data.models[p1]
        // put the data
        p0!!.filenameTextView.text = currentItem.file_name
        p0!!.encryptedButtonView.text = currentItem.encrypt.toString()
        if (currentItem.encrypt) {
            p0.encryptedButtonView.text = "Encrypted"
        }
        else {
            p0.encryptedButtonView.text = "Decrypted"
        }

        // store row position inside view tag
        p0.itemView.tag = p1
    }

    // the new RecyclerAdapter enforces the use of the ViewHolder performance pattern
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val nameTextView: TextView = itemView.findViewById(R.id.firstName)

        val filenameTextView = itemView.findViewById<TextView>(epita.marion_romain.cryptofile.R.id.filename)
        val encryptedButtonView = itemView.findViewById<TextView>(epita.marion_romain.cryptofile.R.id.filename)

    }
    override fun getItemCount(): Int {
        return data.models.size
    }

}