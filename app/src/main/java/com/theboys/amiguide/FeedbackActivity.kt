package com.theboys.amiguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.theboys.amiguide.model.FeedbackModel
import com.theboys.amiguide.viewholder.FeedbackViewHolder
import kotlinx.android.synthetic.main.activity_feedback.*
import javax.security.auth.Subject

class FeedbackActivity : AppCompatActivity() {

    private lateinit var mAdapter: FirestorePagingAdapter<FeedbackModel, FeedbackViewHolder>
    private val uid = FirebaseAuth.getInstance().currentUser?.uid
    private val mFirestore = FirebaseFirestore.getInstance()
    private val mFeedbackCollection = mFirestore.collection("feedbacks")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        rv_feedback.setHasFixedSize(true)
        rv_feedback.layoutManager = LinearLayoutManager(this)

        setupAdapter()
        srlFeedback.setOnRefreshListener {
            mAdapter.refresh()
        }

        fabFeedback.setOnClickListener {
            startActivity(Intent(this, AddFeedback::class.java))
        }

    }
    private fun setupAdapter() {
        val query = mFeedbackCollection.orderBy("input_nama", Query.Direction.ASCENDING)
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPrefetchDistance(0)
            .setPageSize(3)
            .build()
        val option = FirestorePagingOptions.Builder<FeedbackModel>()
            .setLifecycleOwner(this)
            .setQuery(query, config, FeedbackModel::class.java)
            .build()

        mAdapter = object : FirestorePagingAdapter<FeedbackModel, FeedbackViewHolder>(option) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackViewHolder =
                FeedbackViewHolder(layoutInflater.inflate(R.layout.item_feedback, parent, false))


            override fun onBindViewHolder(
                holder: FeedbackViewHolder,
                position: Int,
                model: FeedbackModel
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
                    PagedList.LoadType.START -> srlFeedback.isRefreshing = true
                    PagedList.LoadType.REFRESH -> srlFeedback.isRefreshing = true
                    PagedList.LoadType.END -> srlFeedback.isRefreshing = false
                }
            }
        }
        rv_feedback.adapter = mAdapter
    }

    override fun onStart() {
        super.onStart()
        mAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mAdapter.stopListening()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }
}
