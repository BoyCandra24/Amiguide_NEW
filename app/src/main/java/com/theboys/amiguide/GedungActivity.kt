package com.theboys.amiguide

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.theboys.amiguide.model.GedungModel
import com.theboys.amiguide.viewholder.GedungViewHolder
import kotlinx.android.synthetic.main.activity_gedung.*

class GedungActivity : AppCompatActivity() {
    private lateinit var mAdapter: FirestorePagingAdapter<GedungModel, GedungViewHolder>
    private val mFirestore = FirebaseFirestore.getInstance()
    private val mGedungCollection = mFirestore.collection("gedungs")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gedung)

        rv_Gedung.setHasFixedSize(true)
        rv_Gedung.layoutManager = LinearLayoutManager(this)

        setupAdapter()
        srlGedung.setOnRefreshListener {
            mAdapter.refresh()
        }
    }

    private fun setupAdapter() {
        val query = mGedungCollection.orderBy("namaGedung", Query.Direction.ASCENDING)
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPrefetchDistance(0)
            .setPageSize(3)
            .build()
        val options = FirestorePagingOptions.Builder<GedungModel>()
            .setLifecycleOwner(this)
            .setQuery(query, config, GedungModel::class.java)
            .build()

        mAdapter = object : FirestorePagingAdapter<GedungModel, GedungViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GedungViewHolder =
                GedungViewHolder(layoutInflater.inflate(R.layout.item_gedung, parent, false))

            override fun onBindViewHolder(
                holder: GedungViewHolder,
                position: Int,
                model: GedungModel
            ) {
                holder.bind(model)
            }

            override fun onLoadStateChanged(
                type: PagedList.LoadType,
                state: PagedList.LoadState,
                error: Throwable?
            ) {
                super.onLoadStateChanged(type, state, error)
                when (type) {
                    PagedList.LoadType.START -> srlGedung.isRefreshing = true
                    PagedList.LoadType.REFRESH -> srlGedung.isRefreshing = true
                    PagedList.LoadType.END -> srlGedung.isRefreshing = false
                }
            }
        }
        rv_Gedung.adapter = mAdapter
    }


    override fun onStart() {
        super.onStart()
        mAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mAdapter.stopListening()
    }
}

