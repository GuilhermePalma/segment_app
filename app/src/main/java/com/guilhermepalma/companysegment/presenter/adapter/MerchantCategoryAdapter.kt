package com.guilhermepalma.companysegment.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.guilhermepalma.companysegment.databinding.ItemSegmentBinding
import com.guilhermepalma.companysegment.domain.MerchantCategory

class MerchantCategoryAdapter(private val merchantCategoryList: MutableList<MerchantCategory>) :
    RecyclerView.Adapter<MerchantCategoryAdapter.MerchantCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MerchantCategoryViewHolder {
        return MerchantCategoryViewHolder(
            ItemSegmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MerchantCategoryViewHolder, position: Int) {
        val merchantCategory: MerchantCategory = merchantCategoryList[position]

        holder.bidingSegmentItem.itemsegmentName.text = merchantCategory.group
    }

    override fun getItemCount(): Int {
        return merchantCategoryList.size
    }

    fun setData(newData: List<MerchantCategory>) {
        merchantCategoryList.clear()
        merchantCategoryList.addAll(newData as MutableList<MerchantCategory>)
    }

    class MerchantCategoryViewHolder(var bidingSegmentItem: ItemSegmentBinding) :
        RecyclerView.ViewHolder(bidingSegmentItem.root)
}