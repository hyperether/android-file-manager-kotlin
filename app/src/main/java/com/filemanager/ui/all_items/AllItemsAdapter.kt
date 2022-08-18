package com.filemanager.ui.all_items

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.filemanager.utils.Constants.TYPE_FOLDER
import com.filemanager.MainActivity
import com.filemanager.R
import com.filemanager.repository.db.item.Item

class AllItemsAdapter(
    private var items: List<Item>,
    private var activity: MainActivity
) :
    RecyclerView.Adapter<AllItemsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_section,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = items[position]
        holder.itemView.apply {
            holder.tvText.text = currentItem.name
        }
        if (currentItem.type == TYPE_FOLDER) {
            holder.itemIcon.setImageResource(R.drawable.ic_folder)
        } else {
            Handler(Looper.getMainLooper()).post {
                loadImageMainIcon(
                    activity.applicationContext,
                    holder.itemIcon,
                    currentItem.path
                )
            }
        }

        holder.itemView.setOnClickListener {
            if (currentItem.type == TYPE_FOLDER) {
                refreshFragment(currentItem.itemID)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvText: TextView
        var itemIcon: ImageView

        init {
            tvText = itemView.findViewById(R.id.tv_text)
            itemIcon = itemView.findViewById(R.id.item_icon)
        }
    }

    private fun refreshFragment(path: String) {
        val fragment = AllItemsFragment()
        activity.replaceFragment(fragment, path)
    }

    private fun loadImageMainIcon(
        context: Context?,
        imageView: ImageView?,
        imagePath: String
    ) {
        val circularProgressDrawable = CircularProgressDrawable(activity.applicationContext)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        context?.let {
            imageView?.let { it1 ->
                Glide.with(it)
                    .load(imagePath)
                    .fitCenter()
                    .placeholder(circularProgressDrawable)
                    .apply(
                        RequestOptions()
                            .override(100)
                    )
                    .into(it1)
            }
        }
    }
}