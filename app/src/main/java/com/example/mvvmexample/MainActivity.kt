package com.example.mvvmexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmexample.adapter.NoteRecyclerAdapter
import com.example.mvvmexample.model.Blog
import com.example.mvvmexample.viewmodel.MainViewModel
import com.example.mvvmexample.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private var viewManager = LinearLayoutManager(this)
    private lateinit var viewModel: MainViewModel
    private lateinit var mainrecycler: RecyclerView
    private lateinit var but: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainrecycler = findViewById(R.id.recycler)
        val application = requireNotNull(this).application
        val factory = MainViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
        but = findViewById(R.id.button)
        but.setOnClickListener{
            addData()
        }

        initializeAdapter()
        }

        private fun initializeAdapter(){
            mainrecycler.layoutManager = viewManager
            observeData()
    }

    fun observeData(){
        viewModel.lst.observe(this, Observer{
            Log.i("data", it.toString())
            mainrecycler.adapter = NoteRecyclerAdapter(viewModel,it,this)

        })
    }

    fun addData(){
        var txtplce = findViewById<EditText>(R.id.titletxt)
        var title = txtplce.text.toString()
        if(title.isNullOrBlank()) {
            Toast.makeText(this, "Enter value!", Toast.LENGTH_LONG).show()
        }else{
            var blog = Blog(title)
            viewModel.add(blog)
            txtplce.text.clear()
            mainrecycler.adapter?.notifyDataSetChanged()
        }
    }
}