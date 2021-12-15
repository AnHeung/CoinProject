package kuma.coinproject.ui.adapter

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import kuma.coinproject.data.adapter.model.CoinAdapterItem
import kuma.coinproject.databinding.ItemCoinBinding
import kuma.coinproject.ui.base.BaseListAdapter
import kuma.coinproject.ui.base.BaseViewHolder
import kuma.coinproject.ui.coin.CoinViewModel

class CoinAdapter(
    coinViewModel: CoinViewModel,
    clazz: Class<CoinViewHolder>,
    @LayoutRes layoutId : Int,
    comparator : DiffUtil.ItemCallback<CoinAdapterItem>,
) : BaseListAdapter<CoinAdapterItem, CoinAdapter.CoinViewHolder , CoinViewModel>(coinViewModel, clazz, layoutId, comparator){


    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind(getItem(position) , viewModel)
    }

    class CoinViewHolder(binding: ItemCoinBinding) :BaseViewHolder<ItemCoinBinding, CoinAdapterItem, CoinViewModel>(binding){

        override fun bind(item: CoinAdapterItem?, viewModel: CoinViewModel) {
            binding.apply {
                item?.apply {
                    coinViewModel = viewModel
                    coinId = id
                    itemCoinRank.text = rank
                    itemCoinName.text = name
                }
            }
        }
    }

}