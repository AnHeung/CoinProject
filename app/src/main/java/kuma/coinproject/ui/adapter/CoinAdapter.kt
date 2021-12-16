package kuma.coinproject.ui.adapter

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import kuma.coinproject.data.db.model.Coin
import kuma.coinproject.databinding.ItemCoinBinding
import kuma.coinproject.ui.base.BaseListAdapter
import kuma.coinproject.ui.base.BaseViewHolder
import kuma.coinproject.ui.coin.CoinViewModel

class CoinAdapter(
    coinViewModel: CoinViewModel,
    clazz: Class<CoinViewHolder>,
    @LayoutRes layoutId : Int,
    comparator : DiffUtil.ItemCallback<Coin>,
) : BaseListAdapter<Coin, CoinAdapter.CoinViewHolder , CoinViewModel>(coinViewModel, clazz, layoutId, comparator){


    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind(getItem(position) , viewModel)
    }

    class CoinViewHolder(binding: ItemCoinBinding) :BaseViewHolder<ItemCoinBinding, Coin, CoinViewModel>(binding){

        override fun bind(item: Coin?, viewModel: CoinViewModel) {
            binding.apply {
                item?.let { coin->
                    coinViewModel = viewModel
                    coinItem = item
                    itemCoinRank.text = coin.rank
                    itemCoinName.text = coin.name
                }
            }
        }
    }
}