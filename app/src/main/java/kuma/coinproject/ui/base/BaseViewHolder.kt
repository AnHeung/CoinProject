package kuma.coinproject.ui.base

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<V:ViewDataBinding , T , VM:ViewModel>(val binding:V) : RecyclerView.ViewHolder(binding.root){

    abstract fun bind(item : T?, viewModel: VM )
}