package com.example.app.rxjava.module.main.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.rxjava.R;
import com.example.app.rxjava.base.OnFragmentInteractionListener;
import com.example.app.rxjava.base.gesture.ItemTouchHelperAdapter;
import com.example.app.rxjava.base.gesture.ItemTouchHelperViewHolder;
import com.example.app.rxjava.bean.weather.WeatherData;

import java.util.Collections;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link WeatherData} and makes a call to the
 * specified {@link OnFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class WeatherListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {
    private final List<WeatherData> mValues;
    private final OnFragmentInteractionListener mListener;

    //数据项类型
    private static final int TYPE_ITEM0 = 0;
    private static final int TYPE_ITEM1 = 1;

    public WeatherListAdapter(List<WeatherData> items, OnFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM0) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_item, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_ITEM1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.empty, null);
            return new EmptyViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.mItem = mValues.get(position);
            itemViewHolder.mIdView.setText(String.valueOf(mValues.get(position).getId()));
            itemViewHolder.mContentView.setText(mValues.get(position).getName());

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

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mValues, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mValues.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= 0) {
            return TYPE_ITEM0;
        } else {
            return TYPE_ITEM1;
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public WeatherData mItem;

        public ItemViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public EmptyViewHolder(View view) {
            super(view);
            mView = view;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
