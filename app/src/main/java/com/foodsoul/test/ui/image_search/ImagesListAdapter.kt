package com.foodsoul.test.ui.image_search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.foodsoul.test.R
import com.foodsoul.test.data.image_search.model.Value
import com.foodsoul.test.databinding.ImageSearchItemBinding

class ImagesListAdapter(
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ImagesListAdapter.ItemViewHolder>() {

    private var diffCallback: DiffUtil.Callback
    private lateinit var result: DiffUtil.DiffResult
    private var list: List<Value> = java.util.ArrayList()
    private var newList: List<Value> = java.util.ArrayList()

    init {
        diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return list.size
            }
            override fun getNewListSize(): Int {
                return newList.size
            }
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return (list[oldItemPosition].imageId == newList[newItemPosition].imageId)
            }
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return list[oldItemPosition].imageId == newList[newItemPosition].imageId
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemBinding = ImageSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = list[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ItemViewHolder(private val itemBinding: ImageSearchItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: Value) {

            Glide.with(itemBinding.imageView.context)
                .load(item.thumbnailUrl)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.no_image)
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(itemBinding.imageView)

            itemBinding.imageView.setOnClickListener {
                onItemClickListener.onItemClick(item)
            }
        }
    }

    fun setNewList(newData: List<Value>) {
        newList = newData
        result = DiffUtil.calculateDiff(diffCallback)
        list = newList
        result.dispatchUpdatesTo(this)
    }

    interface OnItemClickListener {
        fun onItemClick(item: Value)
    }

}