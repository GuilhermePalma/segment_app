package com.guilhermepalma.companysegment.presenter.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.guilhermepalma.companysegment.domain.Segment

class SegmentAdapter(private val segment: List<Segment>) :
    RecyclerView.Adapter<SegmentAdapter.SegmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SegmentViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: SegmentViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return segment.size
    }

    // TODO: Change "bidingSegmentItem: View" to ViewBinding
    class SegmentViewHolder(var bidingSegmentItem: View) :
        RecyclerView.ViewHolder(bidingSegmentItem)
}