package com.odcode.rx.recylceradapter

sealed class RxAdapterChangeEvent<D : RxAdapterData> {
    data class Reloaded<D : RxAdapterData>(val newList: List<D>) : RxAdapterChangeEvent<D>()
    data class Removed<D : RxAdapterData>(val index: Int) : RxAdapterChangeEvent<D>()
    data class RemovedRange<D : RxAdapterData>(val startIndex: Int, val itemCount: Int) : RxAdapterChangeEvent<D>()
    data class Inserted<D : RxAdapterData>(val index: Int, val item: D) : RxAdapterChangeEvent<D>()
    data class InsertedRange<D : RxAdapterData>(val index: Int, val items: List<D>) : RxAdapterChangeEvent<D>()
    // TODO: rename to Change
    data class Replace<D : RxAdapterData>(val index: Int, val item: D) : RxAdapterChangeEvent<D>()
}
