package com.android.webbrowser

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.webbrowser.base.ViewModelFactory
import com.android.webbrowser.database.AppDatabase
import android.webkit.WebView

import android.webkit.WebSettings

import android.webkit.WebChromeClient
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.android.webbrowser.alert.CustomAlert
import com.google.android.material.snackbar.Snackbar


open class BaseActivity : AppCompatActivity() {
    protected lateinit var context: Context
    protected lateinit var database: AppDatabase
    protected lateinit var viewModelFactory: ViewModelFactory
    protected lateinit var alertDialog: CustomAlert
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.context = this
        database = AppDatabase.getDatabase(context)
        viewModelFactory = ViewModelFactory(context,database)
        alertDialog = CustomAlert(context)
    }

    protected fun showToastMessage(message: String){
        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
    }

    protected fun showSnackBar(coordinatorLayout: CoordinatorLayout, message: String) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show()
    }

    protected open fun getWebView(webView: WebView): WebView {
        webView.clearCache(true)
        webView.webChromeClient = WebChromeClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.useWideViewPort = false
        webView.settings.loadWithOverviewMode = true
        webView.settings.builtInZoomControls = false
        webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        webView.isScrollbarFadingEnabled = false
        webView.setBackgroundColor(Color.TRANSPARENT)
        return webView
    }
}