package com.android.webbrowser.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.webbrowser.R
import com.android.webbrowser.alert.AlertClick
import com.android.webbrowser.alert.CustomAlert
import com.android.webbrowser.databinding.HistoryListLayoutBinding
import com.android.webbrowser.model.History
import com.android.webbrowser.viewModel.HistoryViewModel

class HistoryAdapter(private val context: Context,private var items:List<History>, private val viewModel:HistoryViewModel, private val alert: CustomAlert, val callback: (History) -> Unit) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.history_list_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = items[position]
        val rowBinding = HistoryListLayoutBinding.bind(holder.itemView)
        rowBinding.urlTextView.text = history.urlString
        rowBinding.dateTextView.text = history.date
        rowBinding.thumbImageView.setImageBitmap(viewModel.getImageFromBase64(history.image))
        rowBinding.deleteImageView.setOnClickListener {
            alert.setShowAlert("Alert!","Do you want to delete it",object:AlertClick{
                override fun setClick(done: Boolean) {
                   if (done){
                       viewModel.deleteHistory(history.id)
                   }
                }
            })

        }

        rowBinding.rootLayout.setOnClickListener {
            callback(history)
        }
    }

    override fun getItemCount(): Int {
       return items.size
    }

    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {

    }

    fun update(items:List<History>){
        this.items = items
        notifyDataSetChanged()
    }


}