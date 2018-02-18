package com.odcode.rx.recylceradapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import io.reactivex.functions.Consumer

class RxAdapter<D : RxAdapterData> constructor(
        private val delegate: Delegate<D>
) : RecyclerView.Adapter<RxAdapterViewHolder<D>>(), Consumer<RxAdapterChangeEvent<D>> {

    private var mDataSet: MutableList<D> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RxAdapterViewHolder<D>
            = delegate.viewHolderForViewType(parent, viewType)

    override fun onBindViewHolder(holder: RxAdapterViewHolder<D>, position: Int) {
        val item = mDataSet[position]
        holder.onBindItem(item, position)
    }

    override fun getItemCount(): Int = mDataSet.count()

    override fun getItemViewType(position: Int): Int = delegate.getItemViewType(position, mDataSet[position])

    override fun accept(event: RxAdapterChangeEvent<D>) {
        println(" RxAdapterChangeEvents : $event")
        when (event) {
            is RxAdapterChangeEvent.Reloaded -> {
                mDataSet.clear()
                mDataSet.addAll(event.newList)
                notifyDataSetChanged()
            }
            is RxAdapterChangeEvent.Removed -> {
                mDataSet.removeAt(event.index)
                notifyItemRemoved(event.index)
            }
            is RxAdapterChangeEvent.RemovedRange -> {
                for (i in (event.itemCount - 1) downTo 0) {
                    mDataSet.removeAt(i)
                }
                notifyItemRangeRemoved(event.startIndex, event.itemCount)
            }
            is RxAdapterChangeEvent.Inserted -> {
                mDataSet.add(event.index, event.item)
                notifyItemInserted(event.index)
            }
            is RxAdapterChangeEvent.InsertedRange -> {
                mDataSet.addAll(event.index, event.items)
                notifyItemRangeInserted(event.index, event.items.count())
            }
            is RxAdapterChangeEvent.Replace -> {
                mDataSet[event.index] = event.item
                notifyItemChanged(event.index)
            }
        }
    }

    // FIXME: 임시
    interface Delegate<D : RxAdapterData> {
        fun getItemViewType(position: Int, item: D): Int
        fun viewHolderForViewType(parent: ViewGroup, viewType: Int): RxAdapterViewHolder<D>
    }
}
