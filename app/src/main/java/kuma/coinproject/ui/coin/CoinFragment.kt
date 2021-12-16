package kuma.coinproject.ui.coin

import kuma.coinproject.R
import kuma.coinproject.databinding.FragmentCoinBinding
import kuma.coinproject.ui.adapter.CoinAdapter
import kuma.coinproject.ui.base.BaseFragment
import kuma.coinproject.utils.isLiveDataResume
import kuma.coinproject.utils.navigate
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
            coins.observe(viewLifecycleOwner, {
                coinAdapter.submitList(it.take(100))
            })

            coinClick.observe(viewLifecycleOwner, { item ->
                if (isLiveDataResume()) {
                    navigate(CoinFragmentDirections.actionCoinFragmentToCoinDetailFragment(item.id, item.name))
                }
            })
        }
    }
}