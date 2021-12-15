package kuma.coinproject.di.module

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.GsonBuilder
import kuma.coinproject.R
import kuma.coinproject.data.AppRepository
import kuma.coinproject.data.adapter.model.CoinAdapterItem
import kuma.coinproject.data.api.CoinApiService
import kuma.coinproject.ui.adapter.CoinAdapter
import kuma.coinproject.ui.coin.CoinViewModel
import kuma.coinproject.ui.coin_detail.CoinDetailViewModel
import kuma.coinproject.utils.API_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        AppRepository(get())
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

}

val coinModule = module {

    single <DiffUtil.ItemCallback<CoinAdapterItem>> {
        (object : DiffUtil.ItemCallback<CoinAdapterItem>(){
            override fun areItemsTheSame(oldItem: CoinAdapterItem, newItem: CoinAdapterItem): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: CoinAdapterItem,
                newItem: CoinAdapterItem
            ): Boolean = oldItem.id == newItem.id
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

val moduleList = listOf(appModule, apiModule, coinModule, coinDetailModule)