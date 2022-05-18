package com.guilhermepalma.companysegment.presenter.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.guilhermepalma.companysegment.databinding.ActivityMainBinding
import com.guilhermepalma.companysegment.domain.MerchantCategory
import com.guilhermepalma.companysegment.domain.Segment
import com.guilhermepalma.companysegment.presenter.adapter.SegmentAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var segmentList: List<Segment> = ArrayList()
    private lateinit var matchesAdapter: SegmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListSegments()
    }

    private fun setupListSegments() {
        // TODO: Remover MockList e Implementar obtenção na API
        val mockList: List<Segment> = listOf(
            Segment(
                "01",
                "Teste 1",
                listOf(MerchantCategory("001", 1523, "Teste1.1"))
            ),
            Segment(
                "02",
                "Teste 2",
                listOf(MerchantCategory("002", 1521, "Teste1.2"))
            ),
            Segment(
                "01",
                "Teste 1",
                listOf(MerchantCategory("001", 1523, "Teste1.1"))
            ),
            Segment(
                "02",
                "Teste 2",
                listOf(MerchantCategory("002", 1521, "Teste1.2"))
            ),
            Segment(
                "01",
                "Teste 1",
                listOf(MerchantCategory("001", 1523, "Teste1.1"))
            ),
            Segment(
                "02",
                "Teste 2",
                listOf(MerchantCategory("002", 1521, "Teste1.2"))
            ),
            Segment(
                "01",
                "Teste 1",
                listOf(MerchantCategory("001", 1523, "Teste1.1"))
            ),
            Segment(
                "02",
                "Teste 2",
                listOf(MerchantCategory("002", 1521, "Teste1.2"))
            ),
            Segment(
                "01",
                "Teste 1",
                listOf(MerchantCategory("001", 1523, "Teste1.1"))
            ),
            Segment(
                "02",
                "Teste 2",
                listOf(MerchantCategory("002", 1521, "Teste1.2"))
            ),
        )

        segmentList = mockList
        matchesAdapter = SegmentAdapter(segmentList)
        binding.activitymainRecyclerSegment.layoutManager = LinearLayoutManager(applicationContext)
        binding.activitymainRecyclerSegment.adapter = matchesAdapter

    }
}