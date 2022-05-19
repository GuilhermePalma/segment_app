package com.guilhermepalma.companysegment.presenter.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
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
    private lateinit var segmentCategoryAdapter: MerchantCategoryAdapter
    private lateinit var segmentAPI: SegmentAPI
    private lateinit var segmentSelected: Segment
    private lateinit var segmentsList: List<Segment>
    private lateinit var merchantCategoryList: List<MerchantCategory>
    private var onlySegmentsList: ArrayList<String> = ArrayList()
    private var currentPage: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHttpClient()
        getListSegment()

        binding.activitymainBtnEdit.setOnClickListener {
            switchSelectedLayout(false)
        }

        binding.mainactivityBtnBack.setOnClickListener {
            this.onBackPressed()
        }

        clickFinishRegister()
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
                showSnackBar(getString(R.string.txt_errorAPI))
                Log.println(Log.ERROR, "FAILED API", t.toString())
            }

            override fun onResponse(call: Call<Segment>, response: Response<Segment>) {
                if (response.isSuccessful) {
                    segmentsList = response.body()!!.segmentItems
                    setupSelectSegment()
                } else showSnackBar(getString(R.string.txt_errorAPI))
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
                merchantCategoryList =
                    segmentsList.filter { segment -> segment.name == text }[0].merchantCategory
                switchDynamicLayout(true)

                if (binding.activitymainRecyclerSegment.adapter == null) {
                    setupRecyclerView()
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

    private fun setupRecyclerView() {
        val cloneInstanceList: List<MerchantCategory> = merchantCategoryList.subList(
            0, merchantCategoryList.size
        )

        segmentCategoryAdapter = MerchantCategoryAdapter(
            cloneInstanceList as MutableList<MerchantCategory>
        ) { position -> onItemClicked(position) }
        binding.activitymainRecyclerSegment.adapter = segmentCategoryAdapter
        binding.activitymainRecyclerSegment.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun onItemClicked(position: Int) {
        segmentSelected = segmentsList.filter { value ->
            value.merchantCategory.contains(merchantCategoryList[position])
        }[0]
        segmentSelected.merchantCategory = listOf(merchantCategoryList[position])

        showSnackBar(
            getString(R.string.label_segmentSelected) + " ${segmentSelected.merchantCategory[0].group}"
        )

        binding.activitymainTxtSegmentSelected.text = segmentSelected.name
        binding.activitymainTxtIdSegment.text =
            getString(R.string.txt_segmentID, segmentSelected.id)
        binding.activitymainTxtMccName.text =
            getString(R.string.txt_mccName, segmentSelected.merchantCategory[0].group)
        binding.activitymainTxtMccCode.text =
            getString(R.string.txt_merchantID, segmentSelected.merchantCategory[0].code.toString())

        switchSelectedLayout(true)
    }

    private fun switchSelectedLayout(showSelectedLayout: Boolean) {
        if (showSelectedLayout && !viewIsVisible(binding.activitymainTxtSegmentSelected) ||
            !showSelectedLayout && !viewIsVisible(binding.activitymainLayoutAutoComplete)
        ) {
            invertVisibility(
                listOf(
                    binding.activitymainLayoutAutoComplete,
                    binding.activitymainRecyclerSegment,
                    binding.activitymainTxtSegmentSelected,
                    binding.activitymainBtnEdit,
                    binding.activitymainTxtIdSegment,
                    binding.activitymainTxtMccName,
                    binding.activitymainTxtMccCode,
                )
            )
        }
    }

    private fun switchDynamicLayout(showRecyclerView: Boolean) {
        if (showRecyclerView && !viewIsVisible(binding.activitymainRecyclerSegment) ||
            !showRecyclerView && !viewIsVisible(binding.activitymainTxtSelectSegment)
        ) {
            invertVisibility(
                listOf(
                    binding.activitymainRecyclerSegment,
                    binding.activitymainTxtSelectSegment
                )
            )
        }
    }

    private fun clickFinishRegister() {
        binding.mainactivityBtnFinish.setOnClickListener {
            if (this::segmentSelected.isInitialized && binding.activitymainTxtSegmentSelected.isVisible) {
                showSnackBar(getString(R.string.txt_finishRegister))
                this.onBackPressed()
            } else showSnackBar(getString(R.string.txt_selectSegment))
        }
    }

    private fun viewIsVisible(view: View): Boolean {
        return view.isVisible
    }

    private fun invertVisibility(view: List<View>) {
        view.forEach {
            it.visibility = if (it.visibility == View.GONE) View.VISIBLE else View.GONE
        }
    }

    private fun showSnackBar(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(R.string.txt_titleRefres)
        alertDialog.setMessage(R.string.txt_messageRefres)
        alertDialog.setPositiveButton(R.string.txt_start) { _, _ ->
            this.recreate()
        }
        alertDialog.setNegativeButton(R.string.txt_exit) { _, _ ->
            this.finish()
        }

        alertDialog.create().show()
    }
}