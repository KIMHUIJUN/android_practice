package com.example.ch16_provider

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.ch16_provider.databinding.ItemImageBinding
import com.example.ch16_provider.db.ImageEntity

class ImageRecyclerViewAdapter(private val ImageList: ArrayList<ImageEntity>,
private val listener : OnItemLongClickListener)
    : RecyclerView.Adapter<ImageRecyclerViewAdapter.MyViewHolder>(){

        inner class MyViewHolder(binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root){
            val tv_image = binding.Image
            val tv_title = binding.tvTitle


            val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding:ItemImageBinding =
            ItemImageBinding.inflate(LayoutInflater.from(parent.context),
                parent,false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ImageData = ImageList[position]

        holder.tv_title.text = ImageData.title
        holder.tv_image.setImageBitmap(ImageData.image)
        holder.root.setOnClickListener {
            listener.onLongClick(position)
            false
        }
    }
    override fun getItemCount(): Int {
        return ImageList.size
    }
}