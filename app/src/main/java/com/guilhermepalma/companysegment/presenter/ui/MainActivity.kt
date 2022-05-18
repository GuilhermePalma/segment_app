package com.guilhermepalma.companysegment.presenter.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.guilhermepalma.companysegment.R
import com.guilhermepalma.companysegment.data.SegmentAPI
import com.guilhermepalma.companysegment.databinding.ActivityMainBinding
import com.guilhermepalma.companysegment.domain.MerchantCategory
import com.guilhermepalma.companysegment.domain.Segment
import com.guilhermepalma.companysegment.presenter.adapter.MerchantCategoryAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var segmentsList: List<Segment>
    private lateinit var segmentCategoryAdapter: MerchantCategoryAdapter
    private var onlySegmentsList: ArrayList<String> = ArrayList()
    private lateinit var segmentAPI: SegmentAPI
    private var currentPage: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHttpClient()
        getListSegment()
    }

    private fun setupHttpClient() {
        segmentAPI = Retrofit.Builder()
            .baseUrl("https://api-segmentos.touchone.com.br/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(SegmentAPI::class.java)
    }

    private fun getListSegment() {
        segmentAPI.getSegments(currentPage).enqueue(object : Callback<Segment> {
            override fun onFailure(call: Call<Segment>, t: Throwable) {
                showErrorMessage()
                Log.println(Log.ERROR, "FAILED API", t.toString())
            }

            override fun onResponse(call: Call<Segment>, response: Response<Segment>) {
                if (response.isSuccessful) {
                    segmentsList = response.body()!!.segmentItems
                    setupSelectSegment()
                } else showErrorMessage()
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupSelectSegment() {
        getOnlySegmentsList()
        binding.activitymainAutoComplete.setAdapter(
            ArrayAdapter(
                this, android.R.layout.simple_expandable_list_item_1,
                onlySegmentsList
            )
        )

        binding.activitymainAutoComplete.setOnItemClickListener { _, _, position, _ ->
            val text: String = onlySegmentsList[position]

            if (text == getString(R.string.txt_otherSegment)) {
                switchDynamicLayout(false)
                binding.activitymainAutoComplete.setText("")

                currentPage = if (currentPage == 4) 1 else currentPage + 1
                getListSegment()
            } else {
                val merchantCategoryList: List<MerchantCategory> =
                    segmentsList.filter { segment -> segment.name == text }[0].merchantCategory
                switchDynamicLayout(true)

                if (binding.activitymainRecyclerSegment.adapter == null) {
                    setupRecyclerView(merchantCategoryList)
                } else {
                    segmentCategoryAdapter.run {
                        setData(merchantCategoryList)
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private fun getOnlySegmentsList() {
        onlySegmentsList.clear()
        segmentsList.forEach { onlySegmentsList.add(it.name) }
        onlySegmentsList.add(getString(R.string.txt_otherSegment))
    }

    private fun setupRecyclerView(merchantCategoryList: List<MerchantCategory>) {
        val cloneInstanceList: List<MerchantCategory> = merchantCategoryList.subList(
            0, merchantCategoryList.size - 1
        )

        segmentCategoryAdapter =
            MerchantCategoryAdapter(cloneInstanceList as MutableList<MerchantCategory>)
        binding.activitymainRecyclerSegment.adapter = segmentCategoryAdapter
        binding.activitymainRecyclerSegment.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun switchDynamicLayout(showRecyclerView: Boolean) {
        val visibilityRecyclerView: Int =
            if (binding.activitymainRecyclerSegment.visibility == View.GONE) View.VISIBLE
            else View.GONE

        val visibilityTextSelect: Int =
            if (visibilityRecyclerView == View.GONE) View.VISIBLE
            else View.GONE

        if (showRecyclerView && binding.activitymainRecyclerSegment.visibility != View.VISIBLE ||
            !showRecyclerView && binding.activitymainTxtSelectSegment.visibility != View.VISIBLE
        ) {
            binding.activitymainRecyclerSegment.visibility = visibilityRecyclerView
            binding.activitymainTxtSelectSegment.visibility = visibilityTextSelect
        }
    }

    private fun showErrorMessage() {
        Snackbar.make(binding.root, R.string.txt_errorAPI, Snackbar.LENGTH_LONG).show()
    }

}