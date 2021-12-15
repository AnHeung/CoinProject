package kuma.coinproject.ui.coin_detail

import androidx.navigation.fragment.navArgs
import kuma.coinproject.R
import kuma.coinproject.databinding.FragmentCoinDetailBinding
import kuma.coinproject.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinDetailFragment(override var layoutId: Int = R.layout.fragment_coin_detail) : BaseFragment<FragmentCoinDetailBinding>() {

    private val navArgs: CoinDetailFragmentArgs by navArgs()
    private val coinId: String by lazy { navArgs.coinId }
    private val coinDetailViewModel:CoinDetailViewModel by viewModel()

    override fun bindingLiveData() {
        coinDetailViewModel.apply {
            println("coinID: $coinId")
            getCoinDetailItem(coinId)
        }
    }

    override fun initBinding() {
        dataBinding.apply {
            viewModel = coinDetailViewModel
        }
    }
}