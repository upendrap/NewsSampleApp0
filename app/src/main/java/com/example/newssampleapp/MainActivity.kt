package com.example.newssampleapp

import android.content.Context
import android.os.Bundle
import android.widget.LinearLayout.VERTICAL
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.newssampleapp.archhelpers.Event
import com.example.newssampleapp.databinding.ActivityMainBinding
import com.example.newssampleapp.ext.gone
import com.example.newssampleapp.ext.show
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.loading_overlay.*
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val newsViewModel: NewsViewModel by viewModels(factoryProducer = { viewModelFactory })
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)
        ActivityMainBinding.inflate(layoutInflater).let {
            setContentView(it.root)
            adapter = NewsAdapter(NewsItemDiffCallback())
            it.rvNewsItems.adapter = adapter
            it.rvNewsItems.addItemDecoration(DividerItemDecoration(this, VERTICAL))
            it.swipeRefreshLayout.setOnRefreshListener { newsViewModel.load(refresh = true) }
        }
        newsViewModel.title.observe(this, Observer { title = it })
        newsViewModel.news.observe(this, Observer { adapter.submitList(it) })
        newsViewModel.isPullDownToRefreshLoading.observe(
            this,
            Observer { swipeRefreshLayout.isRefreshing = it })
        newsViewModel.isLoading.observe(
            this,
            Observer { if (it) flLoading.show() else flLoading.gone() })
        newsViewModel.error.observe(this, Observer { handleError(it) })
    }

    private fun handleError(errorEvent: Event<Throwable>) {
        errorEvent.getContentIfNotHandled()?.let {
            Snackbar.make(
                rvNewsItems,
                errorMessage(context = this, throwable = it),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}

/**
 * @param throwable specific error message is returned
 * Specific screen can wrap this call to handle custom error types
 * */
fun errorMessage(context: Context, throwable: Throwable): String {
    return context.getString(
        when (throwable) {
            is UnknownHostException ->
                R.string.error_network
            is SocketTimeoutException ->
                R.string.error_network
            else -> R.string.error_unknown
        }
    )
}

