package kuma.coinproject.ui.coin

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kuma.coinproject.R
import kuma.coinproject.databinding.FragmentCoinBinding
import kuma.coinproject.ui.adapter.CoinAdapter
import kuma.coinproject.ui.base.BaseFragment
import kuma.coinproject.ui.base.BaseViewModel
import kuma.coinproject.utils.PAGING_COUNT
import kuma.coinproject.utils.ScrollListener
import kuma.coinproject.utils.isLiveDataResume
import kuma.coinproject.utils.navigate
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinFragment(override var layoutId: Int = R.layout.fragment_coin) :
    BaseFragment<FragmentCoinBinding>() {

    private val coinViewModel: CoinViewModel by viewModel()
    private val coinAdapter: CoinAdapter by inject()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val scrollListener: ScrollListener by lazy {  //recyclerView 에 붙일 스크롤 리스너
        object : ScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                try {
                    val loadingNum = (page - 1) * PAGING_COUNT
                    val adapterListCount = coinAdapter.itemCount
                    println("loadingNum : $loadingNum adapterListCount : $adapterListCount")
                    if (loadingNum in 1..adapterListCount) {
                        coinViewModel.appendList(page)
                    }
                } catch (e: Exception) {
                    println("onLoadMore Exception : $e")
                }
            }

            override fun onBottom(isBottom: Boolean) {}
        }
    }

    override fun initBinding() {
        dataBinding.apply {
            viewModel = coinViewModel
            adapter = coinAdapter
            linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            with(coinRecv) {
                layoutManager = linearLayoutManager
                addOnScrollListener(scrollListener)
            }
        }
    }

    override fun bindingLiveData() {
        coinViewModel.apply {
            pagingCoins.observe(viewLifecycleOwner, {
                if (isLiveDataResume()) {
                    coinAdapter.submitList(it)
                }
            })

            coinClick.observe(viewLifecycleOwner, { item ->
                if (isLiveDataResume()) {
                    navigate(
                        CoinFragmentDirections.actionCoinFragmentToCoinDetailFragment(
                            item.id,
                            item.name
                        )
                    )
                }
            })

            snackBar.observe(viewLifecycleOwner, { result ->
                if (isLiveDataResume()) {
                    result?.apply {
                        when (this) {
                            BaseViewModel.Result.SUCCESS -> showSnackBar(getString(R.string.app_name))
                            BaseViewModel.Result.FAIL -> showSnackBar(getString(R.string.app_name))
                        }
                    }
                }
            })
        }
    }
}