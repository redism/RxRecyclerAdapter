package com.odcode.rx.recylceradapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Item interface that can be element of this adapter.
 */
abstract class RxAdapterViewHolder<in D : RxAdapterData>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding: ViewDataBinding = DataBindingUtil.bind(itemView)

    abstract fun onBindItem(item: D, position: Int)
}
