package com.android.webbrowser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.webbrowser.adapter.HistoryAdapter
import com.android.webbrowser.databinding.ActivityHistoryBinding
import com.android.webbrowser.model.History
import com.android.webbrowser.viewModel.HistoryViewModel

class HistoryActivity : BaseActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var viewModel: HistoryViewModel
    private lateinit var historyAdapter: HistoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HistoryViewModel::class.java)
        setContentView(binding.root)
        setUpViewModel()
        binding.searchEditText.afterTextChanged {
            viewModel.searchHistory(it)
        }
    }

    private fun setUpViewModel() {
        viewModel.getAllHistory()
        viewModel.browserHistory.observe(this as LifecycleOwner, { historys ->

            loadView(historys)

        })

        viewModel.searchResult.observe(this as LifecycleOwner, { searchHistory ->

            historyAdapter.update(searchHistory)

        })
    }

    private fun loadView(items: List<History>) {
        historyAdapter = HistoryAdapter(context, items, viewModel, alertDialog) { history ->
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("HISTORY", history)
            startActivity(intent)
            finish()
        }
        binding.historyRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            hasFixedSize()
            adapter = historyAdapter

        }
    }
}


private fun AppCompatEditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            afterTextChanged.invoke(s.toString())
        }

        override fun afterTextChanged(editable: Editable?) {

        }
    })
}