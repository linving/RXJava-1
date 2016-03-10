package com.example.app.rxjava.module.main.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.app.rxjava.R;
import com.example.app.rxjava.base.BaseFragment;
import com.example.app.rxjava.base.OnFragmentInteractionListener;
import com.example.app.rxjava.base.gesture.OnStartDragListener;
import com.example.app.rxjava.base.gesture.SimpleItemTouchHelperCallback;
import com.example.app.rxjava.base.layout.SpacesItemDecoration;
import com.example.app.rxjava.bean.weather.WeatherData;
import com.example.app.rxjava.module.main.adapter.WaterfallAdapter;
import com.example.app.rxjava.module.main.presenter.WaterfallPresenter;
import com.example.app.rxjava.module.main.view.ia.WaterfallViewIA;

import java.util.ArrayList;
import java.util.List;

import cn.easydone.swiperefreshendless.EndlessRecyclerOnScrollListener;

import static android.support.v4.widget.SwipeRefreshLayout.*;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
/**
 * A placeholder fragment containing a simple view.
 */
public class WaterfallFragment extends BaseFragment implements OnFragmentInteractionListener, WaterfallViewIA, OnRefreshListener, OnStartDragListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private OnFragmentInteractionListener mListener;
    private List<String> dataList = new ArrayList<String>();
    private WaterfallAdapter adapter;
    private WaterfallPresenter presenter;
    private EndlessRecyclerOnScrollListener onScrollListener;

    //页面控件
    private SwipeRefreshLayout mSwipeRefreshWidget;

    //拖拽
    private ItemTouchHelper mItemTouchHelper;


    public WaterfallFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static WaterfallFragment newInstance() {
        WaterfallFragment fragment = new WaterfallFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new WaterfallPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waterfall, container, false);

        if (view instanceof SwipeRefreshLayout) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
            //设置layoutManager
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            //设置adapter
            adapter = new WaterfallAdapter(dataList);
            recyclerView.setAdapter(adapter);
            //设置item之间的间隔
            SpacesItemDecoration decoration = new SpacesItemDecoration(16);
            recyclerView.addItemDecoration(decoration);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            //滑动操作监听
            onScrollListener = new EndlessRecyclerOnScrollListener(staggeredGridLayoutManager) {
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public void refresh(List<String> data) {
        dataList.clear();
        dataList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadNews(List<String> data) {
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

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}
