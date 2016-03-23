package com.example.app.rxjava.module.main.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.app.rxjava.AppApplication;
import com.example.app.rxjava.R;
import com.example.app.rxjava.base.BaseFragment;
import com.example.app.rxjava.base.OnFragmentInteractionListener;
import com.example.app.rxjava.base.gesture.OnStartDragListener;
import com.example.app.rxjava.base.gesture.SimpleItemTouchHelperCallback;
import com.example.app.rxjava.bean.weather.WeatherData;
import com.example.app.rxjava.bean.weather.WeatherListData;
import com.example.app.rxjava.module.main.adapter.WeatherListAdapter;
import com.example.app.rxjava.module.main.presenter.WeatherListPresenter;
import com.example.app.rxjava.module.main.view.ia.WeatherListViewIA;

import java.util.ArrayList;
import java.util.List;

import cn.easydone.swiperefreshendless.EndlessRecyclerOnScrollListener;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class WeatherListFragment extends BaseFragment implements OnFragmentInteractionListener, WeatherListViewIA,
        OnStartDragListener, OnRefreshListener {

    private OnFragmentInteractionListener mListener;
    private List<WeatherData> dataList = new ArrayList<WeatherData>();
    private WeatherListAdapter adapter;
    private EndlessRecyclerOnScrollListener onScrollListener;

    private WeatherListPresenter presenter;

    //刷新广播监听
    private RefreshActionReceiver refreshActionReceiver = new RefreshActionReceiver();

    //页面控件
    private SwipeRefreshLayout mSwipeRefreshWidget;

    //拖拽
    private ItemTouchHelper mItemTouchHelper;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WeatherListFragment() {
    }

    public static WeatherListFragment newInstance() {
        WeatherListFragment fragment = new WeatherListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new WeatherListPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof SwipeRefreshLayout) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);
            adapter = new WeatherListAdapter(dataList, mListener);

            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            onScrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
                @Override
                public void onLoadMore(int currentPage) {
                    presenter.loadData(++currentPage);
                }
            };
            recyclerView.addOnScrollListener(onScrollListener);

            mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
            mSwipeRefreshWidget.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                    android.R.color.holo_orange_light, android.R.color.holo_red_light);
            mSwipeRefreshWidget.setOnRefreshListener(this);

            // 这句话是为了，第一次进入页面的时候显示加载进度条
            mSwipeRefreshWidget.setProgressViewOffset(false, 0, (int) TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                            .getDisplayMetrics()));

            //拖拽
            ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
            mItemTouchHelper = new ItemTouchHelper(callback);
            mItemTouchHelper.attachToRecyclerView(recyclerView);

            presenter.loadData(0);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (this instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) this;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
        IntentFilter filter = new IntentFilter(AppApplication.REFRESH_ACTION);
        getActivity().registerReceiver(refreshActionReceiver, filter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        onScrollListener = null;
        getActivity().unregisterReceiver(refreshActionReceiver);
    }

    @Override
    public void showProgressDialog() {
        mSwipeRefreshWidget.setRefreshing(true);
    }
    @Override
    public void hideProgressDialog() {
        mSwipeRefreshWidget.setRefreshing(false);
    }

    @Override
    public void refresh(List<WeatherData> data) {
        dataList.clear();
        dataList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadNews(List<WeatherData> data) {
        dataList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFragmentInteraction(Object object) {
        WeatherData data = (WeatherData) object;
        Toast.makeText(getActivity(), data.getName(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onRefresh() {
        onScrollListener.reset();
        presenter.loadData(0);
    }

    private class RefreshActionReceiver extends BroadcastReceiver {

        // 子类收到广播后的逻辑
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(AppApplication.REFRESH_ACTION)) {
                presenter.loadData(0);
            }
        }
    }
}
