package kuma.coinproject.ui.coin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kuma.coinproject.R
import kuma.coinproject.databinding.ActivityCoinBinding

class CoinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityCoinBinding>(this , R.layout.activity_coin)
    }
}