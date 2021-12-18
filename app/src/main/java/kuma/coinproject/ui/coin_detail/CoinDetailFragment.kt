package kuma.coinproject.ui.coin_detail

import android.text.method.ScrollingMovementMethod
import androidx.navigation.fragment.navArgs
import kuma.coinproject.R
import kuma.coinproject.databinding.FragmentCoinDetailBinding
import kuma.coinproject.ui.base.BaseFragment
import kuma.coinproject.ui.base.BaseViewModel
import kuma.coinproject.utils.isLiveDataResume
import kuma.coinproject.utils.navigateUp
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinDetailFragment(override var layoutId: Int = R.layout.fragment_coin_detail) :
    BaseFragment<FragmentCoinDetailBinding>() {

    private val navArgs: CoinDetailFragmentArgs by navArgs()
    private val coinId: String by lazy { navArgs.coinId } //코인 페이지에서 눌렀을 시 넘어오는 아이디
    private val coinName: String by lazy { navArgs.coinName } //코인 페이지에서 눌렀을 시 넘어오는 이름(타이틀용)
    private val coinDetailViewModel: CoinDetailViewModel by viewModel()

    override fun bindingLiveData() {
        coinDetailViewModel.apply {
            getCoinDetailItem(coinId)

            snackBar.observe(viewLifecycleOwner, { result ->
                if (isLiveDataResume()) {
                    result?.apply {
                        when (this) {
                            BaseViewModel.Result.SUCCESS -> showSnackBar(getString(R.string.get_coin_detail_success_msg))
                            BaseViewModel.Result.FAIL -> showSnackBar(getString(R.string.get_coin_detail_fail_msg))
                        }
                    }
                }
            })

        }
    }

    override fun initBinding() {
        dataBinding.apply {
            viewModel = coinDetailViewModel
            coinDetailDescriptionTxt.movementMethod = ScrollingMovementMethod()
            detailToolBar.apply {
                setNavigationOnClickListener { navigateUp() }
                title = "$coinName(${coinId})"
            }
        }
    }
}