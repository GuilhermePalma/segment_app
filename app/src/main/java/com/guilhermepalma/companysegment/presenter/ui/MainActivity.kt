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
        getListSegment(currentPage)
    }

    private fun setupHttpClient() {
        segmentAPI = Retrofit.Builder()
            .baseUrl("https://api-segmentos.touchone.com.br/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(SegmentAPI::class.java)
    }

    private fun getListSegment(page: Int) {
        segmentAPI.getMatches(page).enqueue(object : Callback<Segment> {
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

            val merchantCategoryList: List<MerchantCategory> =
                segmentsList.filter { segment -> segment.name == text }[0].merchantCategory

            if (binding.activitymainRecyclerSegment.visibility == View.GONE) {
                switchDynamicLayout()
                setupRecyclerView(merchantCategoryList)
            } else {
                segmentCategoryAdapter.run {
                    setData(merchantCategoryList)
                    notifyDataSetChanged()
                }
            }
        }
    }

    private fun getOnlySegmentsList() {
        segmentsList.forEach { onlySegmentsList.add(it.name) }

        // TODO Adicionar Valor "Outro" para Avançar p/ Proxima Pagina
    }

    private fun setupRecyclerView(merchantCategoryList: List<MerchantCategory>) {
        val cloneInstanceList = merchantCategoryList.subList(
            0, merchantCategoryList.size - 1
        ) as MutableList<MerchantCategory>

        segmentCategoryAdapter = MerchantCategoryAdapter(cloneInstanceList)
        binding.activitymainRecyclerSegment.adapter = segmentCategoryAdapter
        binding.activitymainRecyclerSegment.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun switchDynamicLayout() {
        binding.activitymainRecyclerSegment.visibility =
            if (binding.activitymainRecyclerSegment.visibility == View.GONE) View.VISIBLE
            else View.GONE

        binding.activitymainTxtSelectSegment.visibility =
            if (binding.activitymainTxtSelectSegment.visibility == View.GONE) View.VISIBLE
            else View.GONE
    }

    private fun showErrorMessage() {
        Snackbar.make(binding.root, R.string.txt_errorAPI, Snackbar.LENGTH_LONG).show()
    }

}