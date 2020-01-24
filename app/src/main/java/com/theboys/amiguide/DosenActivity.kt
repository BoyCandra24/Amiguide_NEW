package com.theboys.amiguide

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.theboys.amiguide.model.DosenModel
import com.theboys.amiguide.viewholder.DosenViewHolder
import kotlinx.android.synthetic.main.activity_dosen.*

class DosenActivity : AppCompatActivity() {

        private lateinit var mAdapter: FirestorePagingAdapter<DosenModel, DosenViewHolder>
        private val mFirestore = FirebaseFirestore.getInstance()
        private val mDosenCollection = mFirestore.collection("dosen")

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_dosen)

            rv_Dosen.setHasFixedSize(true)
            rv_Dosen.layoutManager = LinearLayoutManager(this)

            setupAdapter()
            srlDosen.setOnRefreshListener {
                mAdapter.refresh()
            }
        }

    private fun setupAdapter() {
        val query = mDosenCollection.orderBy("namaDosen", Query.Direction.ASCENDING)
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPrefetchDistance(0)
            .setPageSize(3)
            .build()
        val option = FirestorePagingOptions.Builder<DosenModel>()
            .setLifecycleOwner(this)
            .setQuery(query, config, DosenModel::class.java)
            .build()

        mAdapter = object : FirestorePagingAdapter<DosenModel, DosenViewHolder>(option) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DosenViewHolder =
                DosenViewHolder(layoutInflater.inflate(R.layout.item_dosen, parent, false))


            override fun onBindViewHolder(
                holder: DosenViewHolder,
                position: Int,
                model: DosenModel
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
                    PagedList.LoadType.START -> srlDosen.isRefreshing = true
                    PagedList.LoadType.REFRESH -> srlDosen.isRefreshing = true
                    PagedList.LoadType.END -> srlDosen.isRefreshing = false
                }
            }
        }
        rv_Dosen.adapter = mAdapter
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
