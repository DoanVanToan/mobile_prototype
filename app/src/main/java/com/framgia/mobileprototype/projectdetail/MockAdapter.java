package com.framgia.mobileprototype.projectdetail;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.mobileprototype.R;
import com.framgia.mobileprototype.data.model.Mock;
import com.framgia.mobileprototype.databinding.ItemMockBinding;
import com.framgia.mobileprototype.helper.ItemAdapterTouchHelper;
import com.framgia.mobileprototype.helper.OnStartDragListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by tuannt on 01/03/2017.
 * Project: mobile_prototype
 * Package: com.framgia.mobileprototype.projectdetail
 */
public class MockAdapter extends RecyclerView.Adapter<MockAdapter.ViewHolder> implements
    ItemAdapterTouchHelper {
    private List<Mock> mMocks = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private ProjectDetailContract.Presenter mListener;
    private final OnStartDragListener mDragStartListener;

    public MockAdapter(Context context, List<Mock> mocks,
                       ProjectDetailContract.Presenter listener,
                       OnStartDragListener onStartDragListener) {
        if (mocks != null) mMocks.addAll(mocks);
        mLayoutInflater = LayoutInflater.from(context);
        mListener = listener;
        mDragStartListener = onStartDragListener;
    }

    public void updateData(Mock mock) {
        if (mock != null) {
            mMocks.add(mock);
            notifyDataSetChanged();
        }
    }

    public void removeItem(int position) {
        mMocks.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mMocks, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private ItemMockBinding mItemMockBinding;
        private MockItemActionHandler mMockItemActionHandler;

        ViewHolder(ItemMockBinding itemMockBinding) {
            super(itemMockBinding.getRoot());
            mItemMockBinding = itemMockBinding;
            mMockItemActionHandler = new MockItemActionHandler(mListener);
            mItemMockBinding.setHandler(mMockItemActionHandler);
            mItemMockBinding.getRoot().setOnLongClickListener(this);
        }

        void bindData(Mock mock, int position) {
            if (mock == null) return;
            mItemMockBinding.setMock(mock);
            mItemMockBinding.setPosition(position);
            mItemMockBinding.executePendingBindings();
        }

        @Override
        public boolean onLongClick(View view) {
            mDragStartListener.onStartDrag(this);
            return true;
        }
    }

    @Override
    public MockAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMockBinding itemMockBinding =
            DataBindingUtil.inflate(mLayoutInflater, R.layout.item_mock, parent, false);
        return new MockAdapter.ViewHolder(itemMockBinding);
    }

    @Override
    public void onBindViewHolder(final MockAdapter.ViewHolder holder, final int position) {
        holder.bindData(mMocks.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null != mMocks ? mMocks.size() : 0;
    }
}
