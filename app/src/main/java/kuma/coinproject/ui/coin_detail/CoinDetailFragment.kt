package kuma.coinproject.ui.coin_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import kuma.coinproject.R
import kuma.coinproject.databinding.FragmentCoinDetailBinding
import kuma.coinproject.ui.base.BaseFragment

class CoinDetailFragment(override var layoutId: Int = R.layout.fragment_coin_detail) : BaseFragment<FragmentCoinDetailBinding>() {

    private val navArgs: CoinDetailFragmentArgs by navArgs()

    private val coinId: String by lazy { navArgs.coinId }


    override fun bindingLiveData() {
        println("coinId $coinId")
    }

    override fun initBinding() {

    }
}