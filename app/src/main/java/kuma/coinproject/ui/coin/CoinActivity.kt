package kuma.coinproject.ui.coin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kuma.coinproject.R
import kuma.coinproject.databinding.ActivityCoinBinding

class CoinActivity : AppCompatActivity() {

    val navController: NavController by lazy {
        Navigation.findNavController(
            this,
            R.id.nav_host_fragment
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityCoinBinding>(this , R.layout.activity_coin)
        binding.apply {
            lifecycleOwner = this@CoinActivity
        }
    }
}