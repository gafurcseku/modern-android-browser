package com.android.webbrowser.viewModel

import android.R.attr
import android.content.Context
import android.webkit.WebView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.webbrowser.database.AppDatabase
import java.net.URL
import android.graphics.drawable.Drawable

import android.view.View
import android.R.attr.bitmap
import android.app.Activity
import android.graphics.*
import android.os.Handler
import android.util.Base64
import android.util.Log
import android.view.PixelCopy
import com.android.webbrowser.model.History
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.math.roundToInt


class HistoryViewModel(val context:Context,val database: AppDatabase):ViewModel() {
    val emptyUrl = MutableLiveData<Boolean>()
    val invalideUrl = MutableLiveData<Boolean>()
    val browserUrl = MutableLiveData<String>()

    fun isUrlEmpty(url:String){
        if(url.isEmpty()){
            emptyUrl.postValue(true)
        }else{
            var modificationUrl = ""
            val wwUrl = url.lowercase().contains("www")
            modificationUrl = if (url.lowercase().contains("www")){
                url.lowercase().replace("www.","http://")
            }else{
                url
            }

             if(!modificationUrl.contains("http",true)){
                modificationUrl = "http://${modificationUrl}"
            }

            if(!modificationUrl.contains("http",true) || !modificationUrl.contains(".",true)){
                invalideUrl.postValue(true)
            }else{
                browserUrl.postValue(modificationUrl)
            }

        }
    }

    fun loadWebView(webView:WebView, urlString: String){

        webView.loadUrl(urlString)
    }

    private fun getBitmapFromView(view: View, activity: Activity, callback: (Bitmap) -> Unit) {
        activity.window?.let { window ->
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val locationOfViewInWindow = IntArray(2)
            view.getLocationInWindow(locationOfViewInWindow)
            try {
                PixelCopy.request(window, Rect(locationOfViewInWindow[0], locationOfViewInWindow[1], locationOfViewInWindow[0] + view.width, locationOfViewInWindow[1] + view.height), bitmap, { copyResult ->
                    if (copyResult == PixelCopy.SUCCESS) {
                        callback(bitmap)
                    }
                }, Handler())
            } catch (e: IllegalArgumentException) {
                // PixelCopy may throw IllegalArgumentException, make sure to handle it
                e.printStackTrace()
            }
        }
    }

    open fun getBitmapFromView(view: View,callback: (Bitmap) -> Unit) {
        var bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        var canvas = Canvas(bitmap)
        view.draw(canvas)
        callback(bitmap)
    }


    fun getBase64String(view:View, activity: Activity, callback: (String) -> Unit) {
        getBitmapFromView(view){ bitmap ->
           val bas = ByteArrayOutputStream()
           bitmap.compress(Bitmap.CompressFormat.PNG, 100, bas)
           val byteArray = bas.toByteArray()
           val string = Base64.encodeToString(byteArray, Base64.DEFAULT)
           callback(string)
           Log.i("BitMap", string)
       }

    }

     fun getImageFromBase64(imageString: String): Bitmap {
        val imageBytes = Base64.decode(imageString, 0)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }



    fun saveCapture(urlString:String, image:String){
        val history = History(0,image,urlString,Date().toString())
        database.historyDao().insert(history)
    }

    val browserHistory = MutableLiveData<List<History>>()

    fun getAllHistory(){
       val history = database.historyDao().getAll()
        browserHistory.postValue(history)
    }

    val searchResult = MutableLiveData<List<History>>()

    fun searchHistory(keyword:String){
        val history = database.historyDao().loadHistory(keyword)
        searchResult.postValue(history)
    }

    fun deleteHistory(id:Int) {
        val delete = database.historyDao().deleteById(id)
        if(delete>0){
            val history = database.historyDao().getAll()
            browserHistory.postValue(history)
        }
    }
}