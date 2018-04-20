package com.gage.petfish_android.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gage.petfish_android.BuildConfig;
import com.gage.petfish_android.R;
import com.gage.petfish_android.adapter.BaseListPageAdapter;

/**
 * 上拉加载，下拉刷新
 */
@SuppressLint("ClickableViewAccessibility")
public class RefreshLoadListView extends ListView implements AbsListView.OnScrollListener{
    private final String TAG = this.getClass().getSimpleName();

    private final int DOWN_PULL_REFRESH = 0; // 下拉刷新状态
    private final int RELEASE_REFRESH = 1; // 松开刷新
    //    private final int REFRESHING = 2; // 正在刷新中
    private int firstVisibleItemPosition; // 屏幕显示在第一个的item的索引
    private int downY; // 按下时y轴的偏移量
    private int headerViewHeight; // 头布局的高度
    private View headerView; // 头布局的对象

    private int currentState = DOWN_PULL_REFRESH; // 头布局的状态: 默认为下拉刷新状态

    private ProgressBar mProgressBar; // 头布局的进度条
    private TextView tvState; // 头布局的状态


    private OnRefreshListener mOnRefreshListener;
    private boolean isScrollToBottom; // 是否滑动到底部
    private View footerView; // 脚布局的对象
    private ProgressBar mFooterProgressBar; //脚布局的进度条
    private TextView mFooterTipTextView;    //脚布局的提示信息
    private int footerViewHeight; // 脚布局的高度
    private boolean isLoadingMore = false; // 是否正在加载更多中
    private boolean isLoaded = false; //是否加载完所有的数据

    @SuppressWarnings("rawtypes")
    private BaseListPageAdapter adapter;

    public RefreshLoadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
        initFooterView();
        this.setOnScrollListener(this);
        setCacheColorHint(0);
        onLoading();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void setAdapter(ListAdapter adapter) {
        if (adapter instanceof BaseListPageAdapter) {
            this.adapter = (BaseListPageAdapter) adapter;
        } else {
            throw new RuntimeException("该适配器必须得继承于BaseListPageAdapter");
        }
        super.setAdapter(adapter);
    }


    /**
     * 初始化脚布局
     */
    private void initFooterView() {
        footerView = View.inflate(getContext(), R.layout.listview_footer, null);
        footerView.measure(0, 0);
        footerViewHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0, -footerViewHeight, 0, 0);
        mFooterProgressBar = (ProgressBar) footerView.findViewById(R.id.footerProgressBar);
        mFooterTipTextView = (TextView) footerView.findViewById(R.id.footerTipTextView);
        mFooterTipTextView.setClickable(false);
        this.addFooterView(footerView);
    }

    /**
     * 初始化头布局
     */
    private void initHeaderView() {
        headerView = View.inflate(getContext(), R.layout.listview_header, null);
        headerView.setClickable(false);
        mProgressBar = (ProgressBar) headerView
                .findViewById(R.id.pb_listview_header);
        tvState = (TextView) headerView
                .findViewById(R.id.tv_listview_header_state);

        headerView.measure(0, 0); // 系统会帮我们测量出headerView的高度
        headerViewHeight = headerView.getMeasuredHeight();
        headerView.setPadding(0, -headerViewHeight, 0, 0);
        this.addHeaderView(headerView); // 向ListView的顶部添加一个view对象
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mOnRefreshListener == null) {
            if (BuildConfig.DEBUG) {
                Log.w(TAG, adapter.getClass().getSimpleName() + "  The ListView no listener");
            }
            return super.onTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getY();
                // 移动中的y - 按下的y = 间距.
                int diff = (moveY - downY) / 2;
                // -头布局的高度 + 间距 = paddingTop
                int paddingTop = -headerViewHeight + diff;
                // 如果: -头布局的高度 > paddingTop的值 执行super.onTouchEvent(ev);
                if (firstVisibleItemPosition == 0
                        && -headerViewHeight < paddingTop) {
                    if (paddingTop > 0 && currentState == DOWN_PULL_REFRESH) { // 完全显示了.
                        currentState = RELEASE_REFRESH;
                        refreshHeaderView();
                    } else if (paddingTop < 0
                            && currentState == RELEASE_REFRESH) { // 没有显示完全
                        currentState = DOWN_PULL_REFRESH;
                        refreshHeaderView();
                    }
                    // 下拉头布局
                    headerView.setPadding(0, paddingTop, 0, 0);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                // 判断当前的状态是松开刷新还是下拉刷新
                if (currentState == RELEASE_REFRESH) {
                    // 把头布局设置为完全显示状态
                    headerView.setPadding(0, 0, 0, 0);
                    // 进入到正在刷新中状态
//                    currentState = REFRESHING;
                    refreshHeaderView();

                    if (mOnRefreshListener != null) {
                        mOnRefreshListener.onDownPullRefresh(this, adapter); // 调用使用者的监听方法
                        return false;
                    }
                } else if (currentState == DOWN_PULL_REFRESH) {
                    // 隐藏头布局
                    headerView.setPadding(0, -headerViewHeight, 0, 0);
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 根据currentState刷新头布局的状态
     */
    private void refreshHeaderView() {
        switch (currentState) {
            case DOWN_PULL_REFRESH: // 下拉刷新状态
                tvState.setText("下拉刷新");
                break;
            case RELEASE_REFRESH: // 松开刷新状态
                tvState.setText("松开刷新");
                break;
//            case REFRESHING: // 正在刷新中状态
//                mProgressBar.setVisibility(View.VISIBLE);
//                isLoaded = false;
//                footerView.setPadding(0, -footerViewHeight, 0, 0);
//                tvState.setText("正在刷新中...");
//                break;
            default:
                break;
        }
    }

    /**
     * 当滚动状态改变时回调
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (scrollState == SCROLL_STATE_IDLE
                || scrollState == SCROLL_STATE_FLING) {
            // 判断当前是否已经到了底部
            if (isScrollToBottom && !isLoadingMore && !isLoaded) {
                isLoadingMore = true;
                mFooterProgressBar.setVisibility(View.VISIBLE);
                mFooterTipTextView.setText("加载更多...");
                // 当前到底部
                footerView.setPadding(0, 0, 0, 0);
                this.setSelection(this.getCount());
                if (mOnRefreshListener != null) {
                    mOnRefreshListener.onLoadingMore(this, adapter);
                }
            }
        }
    }

    /**
     * 当滚动时调用
     *
     * @param firstVisibleItem 当前屏幕显示在顶部的item的position
     * @param visibleItemCount 当前屏幕显示了多少个条目的总数
     * @param totalItemCount   ListView的总条目的总数
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        firstVisibleItemPosition = firstVisibleItem;

        isScrollToBottom = getLastVisiblePosition() == (totalItemCount - 1);
    }

    /**
     * 告知ListView刷新完成
     */
    public void onRefreshCompile() {
        isLoaded = false;
        footerView.setPadding(0, 0, 0, 0);
        hideFooterView();
        hideHeaderView();
    }

    /**
     * 告知ListView加载完成
     */
    public void onLoadCompile() {
        footerView.setEnabled(false);
        mFooterTipTextView.setEnabled(false);
        if(adapter.getPage()==1){
            onLoadCompile("");
        }else{
            onLoadCompile("已显示全部数据");
        }
//        Toast.makeText(getContext(), "已经在最底了", Toast.LENGTH_LONG).show();
//        hideFooterView();
    }

    public void onLoadError() {
        onLoadCompile("加载失败，请下拉进行重新加载");
    }

    private void onLoading() {
        onLoadCompile("");//加载中...
    }

    private void onLoadCompile(String tip) {
        isLoaded = true;
        hideFooterView();
        hideHeaderView();
        footerView.setPadding(0, 0, 0, 0);
        mFooterProgressBar.setVisibility(View.GONE);
        mFooterTipTextView.setText(tip);
    }

    /**
     * 设置刷新监听事件
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        mOnRefreshListener = listener;
    }

    /**
     * 隐藏头布局
     */
    private void hideHeaderView() {
        headerView.setPadding(0, -headerViewHeight, 0, 0);
        mProgressBar.setVisibility(View.GONE);
        tvState.setText("下拉刷新");
        currentState = DOWN_PULL_REFRESH;
    }


    /**
     * 隐藏脚布局
     */
    private void hideFooterView() {
        footerView.setPadding(0, -footerViewHeight, 0, 0);
        isLoadingMore = false;
    }


    public interface OnRefreshListener {

        /**
         * 下拉刷新
         */
        void onDownPullRefresh(RefreshLoadListView v, @SuppressWarnings("rawtypes") BaseListPageAdapter adapter);

        /**
         * 上拉加载更多
         */
        void onLoadingMore(RefreshLoadListView v, @SuppressWarnings("rawtypes") BaseListPageAdapter adapter);
    }

}
