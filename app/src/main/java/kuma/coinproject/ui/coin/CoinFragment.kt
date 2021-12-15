package kuma.coinproject.ui.coin

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kuma.coinproject.R
import kuma.coinproject.databinding.FragmentCoinBinding
import kuma.coinproject.ui.adapter.CoinAdapter
import kuma.coinproject.ui.base.BaseFragment
import kuma.coinproject.utils.isLiveDataResume
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinFragment(override var layoutId: Int = R.layout.fragment_coin) :
    BaseFragment<FragmentCoinBinding>() {

    private val coinViewModel: CoinViewModel by viewModel()
    private val coinAdapter: CoinAdapter by inject()


    override fun initBinding() {
        dataBinding.apply {
            viewModel = coinViewModel
            adapter = coinAdapter
        }
    }

    override fun bindingLiveData() {
        coinViewModel.apply {
            getCoinList()

            coinList.observe(viewLifecycleOwner, {
                println("넘어옴? ")
                coinAdapter.submitList(it)
            })

            coinClick.observe(viewLifecycleOwner ,  {coinId->
                println("왜 안됨?")
                if(isLiveDataResume()){
                    coinId?.let {
                        println("coin Click  $coinId")
                        findNavController().navigate(CoinFragmentDirections.actionCoinFragmentToCoinDetailFragment(coinId))
                    }
                }
            })
        }
    }
}