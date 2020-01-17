 package epita.marion_romain.cryptofile.Crypto

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import epita.marion_romain.cryptofile.Crypto.EntryModel
import epita.marion_romain.cryptofile.Crypto.Model
import epita.marion_romain.cryptofile.R

class MyAdapter(val data :EntryModel, val context : Context): BaseAdapter()
     {
         class ViewHolder(rowView : View) {
             val filenameTextView = rowView.findViewById<TextView>(R.id.filename)
             val encryptedTextView = rowView.findViewById<TextView>(R.id.encryptbutton)
         }
         override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
             var rowView : View
             var holder : ViewHolder
             if (convertView == null) {
                 rowView = LayoutInflater.from(context).inflate(R.layout.filedisplay_bloc, parent, false)
                 holder = ViewHolder(rowView)
                 rowView.tag = holder
             }
             else {
                 rowView = convertView
                 holder = convertView.tag as ViewHolder
             }
             val currentItem = getItem(position)



             holder.filenameTextView.text = currentItem.file_name
             holder.encryptedTextView.text = currentItem.encrypt.toString()

             if (currentItem.encrypt) {
                 holder.encryptedTextView.text = "Encrypted"
             }
             else {
                 holder.encryptedTextView.text = "Not Encrypted"
             }

             return rowView
         }

         override fun getItem(position: Int): Model {
             return data.models[position]
         }
         override fun getItemId(position: Int): Long {
             return position.toLong()
         }

         override fun getCount(): Int {
             return data.models.size
         }
}
