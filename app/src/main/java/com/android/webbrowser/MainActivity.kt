package com.android.webbrowser

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.android.webbrowser.databinding.ActivityMainBinding
import com.android.webbrowser.model.History
import com.android.webbrowser.viewModel.HistoryViewModel
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView

class MainActivity : BaseActivity(), View.OnClickListener , SpeedDialView.OnActionSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: HistoryViewModel
    private lateinit var history:History

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HistoryViewModel::class.java)
        setContentView(binding.root)
        binding.goButton.setOnClickListener(this)
        setUpViewModel()
        binding.floatingMenuSpeedDial.inflate(R.menu.action_menu)
        binding.floatingMenuSpeedDial.setOnActionSelectedListener(this)

        if(intent.extras!=null){
            history = intent.getSerializableExtra("HISTORY") as History
            binding.loadImageView.visibility = View.VISIBLE
            binding.loadImageView.setImageBitmap(viewModel.getImageFromBase64(history.image))
            binding.webUrlEditText.setText(history.urlString)
            getWebView(history.urlString)
        }

    }

    private fun getWebView(urlString:String) {
        val webView = getWebView(binding.urlWebView)
        webView.webViewClient = MyWebViewClient(binding.loadImageView, binding.progressBar)
        viewModel.loadWebView(webView,urlString)
    }

    private fun setUpViewModel(){
        viewModel.browserUrl.observe(this as LifecycleOwner, { urlString ->
            if (urlString.isNotEmpty()){
                getWebView(urlString)
            }
        })

        viewModel.emptyUrl.observe(this as LifecycleOwner, { isError ->
            if (isError) {
                showSnackBar(binding.rootLayout,"Url is Empty")
                viewModel.emptyUrl.postValue(false)
            }
        })

        viewModel.invalideUrl.observe(this as LifecycleOwner, { isError ->
            if (isError) {
                showSnackBar(binding.rootLayout,"Invalid URL")
                viewModel.invalideUrl.postValue(false)
            }
        })
    }



    override fun onClick(view: View?) {
      when(view?.id){
          R.id.goButton -> {
              viewModel.isUrlEmpty(binding.webUrlEditText.text.toString().trim())
              hideKeyboard()
          }
      }
    }

    override fun onActionSelected(actionItem: SpeedDialActionItem?): Boolean {
        if (actionItem != null) {
            when(actionItem.id) {
                R.id.history -> {

                    val intent = Intent(context, HistoryActivity::class.java)
                    startActivity(intent)
                    binding.floatingMenuSpeedDial.close()
                }

                R.id.capture -> {
                    binding.floatingMenuSpeedDial.close()
                    val urlString = binding.webUrlEditText.text.toString().trim()
                    if(urlString.isNotEmpty()){
                       viewModel.getBase64String(binding.containerLinearLayout, this){ base64String ->
                           viewModel.saveCapture(urlString,base64String)
                       }

                    }else{
                        showSnackBar(binding.rootLayout, "First Load WebView")
                    }

                }
            }
        }
        return true
    }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
