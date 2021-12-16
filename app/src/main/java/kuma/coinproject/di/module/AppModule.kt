package kuma.coinproject.di.module

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kuma.coinproject.R
import kuma.coinproject.repository.AppRepository
import kuma.coinproject.data.api.CoinApiService
import kuma.coinproject.data.db.CoinRoomDatabase
import kuma.coinproject.data.db.model.Coin
import kuma.coinproject.ui.adapter.CoinAdapter
import kuma.coinproject.ui.coin.CoinViewModel
import kuma.coinproject.ui.coin_detail.CoinDetailViewModel
import kuma.coinproject.utils.API_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        AppRepository(get(),get())
    }

    single(named("scope")) {
        CoroutineScope(SupervisorJob())
    }
}

val apiModule = module {

    single {
        Retrofit.Builder()
            .run {
                baseUrl(API_URL)
                client(get())
                addConverterFactory(GsonConverterFactory.create(get()))
                build()
            }.create(CoinApiService::class.java)
    }

    single {
        GsonBuilder()
            .apply {
                setLenient()
            }.create()
    }

    single {
        OkHttpClient().newBuilder()
            .apply {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
            .build()
    }


}

val dbModule = module {
    single {
        CoinRoomDatabase.getDatabase(androidContext(), get(named("scope"))).coinDao()
    }
    single {
        CoinRoomDatabase.getDatabase(androidContext(), get(named("scope")))
    }
}

val coinModule = module {

    single <DiffUtil.ItemCallback<Coin>> {
        (object : DiffUtil.ItemCallback<Coin>(){
            override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Coin,
                newItem: Coin
            ): Boolean = oldItem == newItem
        })
    }

    factory {
        CoinAdapter(get(), CoinAdapter.CoinViewHolder::class.java , R.layout.item_coin, get())
    }

    single {
        CoinViewModel(get())
    }
}

val coinDetailModule = module {

    viewModel {
        CoinDetailViewModel(get())
    }

}

val moduleList = listOf(appModule, apiModule, coinModule, coinDetailModule, dbModule)