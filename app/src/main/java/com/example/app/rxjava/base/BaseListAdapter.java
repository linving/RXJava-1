package com.example.app.rxjava.base;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app.rxjava.R;
import com.example.app.rxjava.base.gesture.ItemTouchHelperAdapter;
import com.example.app.rxjava.base.gesture.ItemTouchHelperViewHolder;

import java.util.Collections;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link T} and makes a call to the
 * specified {@link OnFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public abstract class BaseListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {
    public final List<T> mValues;
    public final OnFragmentInteractionListener mListener;

    //数据项类型
    public static final int TYPE_ITEM0 = 0;
    public static final int TYPE_ITEM1 = 1;

    public BaseListAdapter(List<T> items, OnFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.empty, null);
            return new EmptyViewHolder(view);
        } else if (viewType == TYPE_ITEM0) {
            return createViewHolder(parent);
        }
        return null;
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

    public class BaseItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        public final View mView;
        public T mItem;

        public BaseItemViewHolder(View view) {
            super(view);
            mView = view;
        }

        @Override
        public String toString() {
            return super.toString();
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

    /** 有数据时对应的ViewHolder
     *
     */
    public abstract RecyclerView.ViewHolder createViewHolder(ViewGroup parent);

    /** 无数据时对应的ViewHolder
     *
     */
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
