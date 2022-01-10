package com.techchallengers.myresume.activities.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techchallengers.myresume.R
import com.techchallengers.myresume.activities.SoldProductDetailsActivity
import com.techchallengers.myresume.databinding.ItemCartLayoutBinding
import com.techchallengers.myresume.databinding.ItemListLayoutBinding


import com.techchallengers.myresume.model.SoldProduct
import com.techchallengers.myresume.utils.Constants
import com.techchallengers.myresume.utils.GlideLoader


/**
 * A adapter class for sold products list items.
 */
open class SoldProductsListAdapter(
    private val context: Context,
    private var list: ArrayList<SoldProduct>
) : RecyclerView.Adapter<SoldProductsListAdapter.ViewHolder>() {


    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class ViewHolder(val binding : ItemListLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list_layout,parent,false)
        return ViewHolder(ItemListLayoutBinding.bind(view))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        GlideLoader(context).loadProductPicture(model.image,holder.binding.ivItemImage)

        holder.binding.tvItemName.text = model.title
        holder.binding.tvItemPrice.text = "Rs.${model.price}"

        holder.binding.ibDeleteProduct.visibility = View.GONE

        holder.itemView.setOnClickListener{
            val intent = Intent(context,SoldProductDetailsActivity::class.java)
            intent.putExtra(Constants.EXTRA_SOLD_PRODUCT_DETAILS,model)
            context.startActivity(intent)
        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return list.size
    }

}
