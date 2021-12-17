package kuma.coinproject.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.orhanobut.logger.Logger
import kotlin.math.ceil

abstract class ScrollListener : RecyclerView.OnScrollListener {
    //로딩하기 전에 현재 스크롤 위치 아래에 있는 최소한의 아이템 수
    private var visibleThreshold = 3

    // 현재 로드한 데이터의 오프셋(처음 위치부터 변한위치까지의 변위차) 인덱스
    var currentPage = 1

    // 최종 로드 후 데이터 집합의 총 항목 수
    private var previousTotalItemCount = 0

    // 마지막 데이터셋이 로드 될떄 까지 기다리면 true 아님 false
    private var loading = true

    // 시작 페이지 인덱스
    private val startingPageIndex = 1
    private var layoutManager: RecyclerView.LayoutManager

    constructor(layoutManager: LinearLayoutManager) {
        this.layoutManager = layoutManager
    }

    constructor(layoutManager: GridLayoutManager) {
        this.layoutManager = layoutManager
        visibleThreshold *= layoutManager.spanCount
    }

    constructor(layoutManager: StaggeredGridLayoutManager) {
        this.layoutManager = layoutManager
        visibleThreshold *= layoutManager.spanCount
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0 || lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    //스크롤시 이벤트 받아서 처리
    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0
        val totalItemCount: Int = (view.layoutManager as RecyclerView.LayoutManager).itemCount
        when (view.layoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions: IntArray = (view.layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
                //리스트의 최대 요소를 얻음
                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
            }
            is GridLayoutManager -> {
                lastVisibleItemPosition = (view.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
            }
            is LinearLayoutManager -> {
                lastVisibleItemPosition = (view.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            }

            //총 항목수가 0이고 이전 값이 아니면 list가 무효화 되서 초기상태로 다시 설정해야함.
            //여전히 로드중인 경우 데이터 셋 개수가 있는지 확인
            //변경 되었으면 페이지를 로드하고 업데이트하는것으로 결론

            //더 많은 데이터를 다시 로드하는 경우 onLoadMore를 실행해 데이터를 가져옴
            //임계값은 총 칼럼수(열)를 반영
        }
        //총 항목수가 0이고 이전 값이 아니면 list가 무효화 되서 초기상태로 다시 설정해야함.
        if (totalItemCount < previousTotalItemCount) {
            currentPage = startingPageIndex
            previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                loading = true
            }
        }
        //여전히 로드중인 경우 데이터 셋 개수가 있는지 확인
        //변경 되었으면 페이지를 로드하고 업데이트하는것으로 결론
        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }
        //더 많은 데이터를 다시 로드하는 경우 onLoadMore를 실행해 데이터를 가져옴
        //임계값은 총 칼럼수(열)를 반영
        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onLoadMore(currentPage, totalItemCount, view)
            loading = true
        }
        try {
            val layoutHeight = layoutManager.height.toDouble()
            val childItemHeight = if (layoutManager.childCount > 0) layoutManager.getChildAt(0)?.height?.toDouble()?:0.toDouble() else 0.toDouble()

            val acceptableChildCount = ceil(layoutHeight / childItemHeight)
            if (totalItemCount >= acceptableChildCount && !view.canScrollVertically(1)) {
                onBottom(true)
            } else {
                onBottom(false)
            }
        } catch (e: NullPointerException) {
            Logger.e("BottomDetectedScrollListener NullPoint : " + e.message)
        }
    }

    //새로운 검색 수행시 마다 메소드 호출
    fun resetState() {
        currentPage = startingPageIndex
        previousTotalItemCount = 0
        loading = true
    }

    //페이지를 기반으로 실제 더 많은 데이터를 로드하는 프로세스 정의
    abstract fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?)
    abstract fun onBottom(isBottom: Boolean)
}