package kuma.coinproject.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

abstract class BaseListAdapter<T, VH : RecyclerView.ViewHolder,VM : ViewModel>(
    val viewModel: VM,
    private val clazz: Class<VH>,
    @LayoutRes private val layoutId: Int,
    diffUtil: DiffUtil.ItemCallback<T>
) : androidx.recyclerview.widget.ListAdapter<T, VH>(diffUtil) {

    //최초 onCreateViewHolder 쪽에서 viewDataBinding 을 만드록 viewHolder 를 만드는 함수  
    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val view: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)

        lateinit var viewHolder: VH

        val constructors = clazz.declaredConstructors

        try {
            for (i in constructors.indices) {
                for (j in constructors[i].parameterTypes.indices) {
                    val parameterClass = constructors[i].declaringClass.declaredConstructors[i].parameterTypes[j]

                    if (parameterClass?.superclass!!.isAssignableFrom(ViewDataBinding::class.java)) {
                        viewHolder = constructors[i].declaringClass.declaredConstructors[i].newInstance(view) as VH
                        break
                    }
                }
            }
        } catch (e: Exception) {
            Timber.e("onCreateViewHolder $e")
        }
        return viewHolder
    }

    open fun removeItem(position :Int) = submitList(currentList - currentList[position])

    open fun addItem(item :T) = submitList(currentList + item)

}