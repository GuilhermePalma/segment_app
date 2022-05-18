package com.guilhermepalma.companysegment.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.guilhermepalma.companysegment.databinding.ItemSegmentBinding
import com.guilhermepalma.companysegment.domain.Segment

class SegmentAdapter(private val segmentList: List<Segment>) :
    RecyclerView.Adapter<SegmentAdapter.SegmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SegmentViewHolder {
        return SegmentViewHolder(
            ItemSegmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SegmentViewHolder, position: Int) {
        val segmentItem: Segment = segmentList[position]

        holder.bidingSegmentItem.itemsegmentName.text = segmentItem.name
    }

    override fun getItemCount(): Int {
        return segmentList.size
    }

    class SegmentViewHolder(var bidingSegmentItem: ItemSegmentBinding) :
        RecyclerView.ViewHolder(bidingSegmentItem.root)
}