package com.example.app.rxjava.module.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.rxjava.R;
import com.example.app.rxjava.base.BaseListAdapter;
import com.example.app.rxjava.base.OnFragmentInteractionListener;
import com.example.app.rxjava.base.gesture.ItemTouchHelperAdapter;
import com.example.app.rxjava.bean.weather.WeatherData;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link WeatherData} and makes a call to the
 * specified {@link OnFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class WeatherListAdapter extends BaseListAdapter implements ItemTouchHelperAdapter {

    public WeatherListAdapter(List items, OnFragmentInteractionListener listener) {
        super(items, listener);
    }

    @Override
    public RecyclerView.ViewHolder createViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BaseListAdapter.BaseItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            WeatherData data = (WeatherData) mValues.get(position);
            itemViewHolder.mItem = data;
            itemViewHolder.mIdView.setText(String.valueOf(data.getId()));
            itemViewHolder.mContentView.setText(data.getName());

            itemViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onFragmentInteraction(itemViewHolder.mItem);
                    }
                }
            });
        }
    }

    public class ItemViewHolder extends BaseItemViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;

        public ItemViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }
    }
}
