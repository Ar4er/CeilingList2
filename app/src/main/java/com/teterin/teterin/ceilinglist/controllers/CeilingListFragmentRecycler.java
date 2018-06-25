package com.teterin.teterin.ceilinglist.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teterin.teterin.ceilinglist.R;
import com.teterin.teterin.ceilinglist.logic.CeilingLab;
import com.teterin.teterin.ceilinglist.logic.SuspendCeiling;
import com.teterin.teterin.ceilinglist.slideAnimHelper.ItemTouchHelperAdapter;
import com.teterin.teterin.ceilinglist.slideAnimHelper.ItemTouchHelperViewHolder;
import com.teterin.teterin.ceilinglist.slideAnimHelper.OnStartDragListener;
import com.teterin.teterin.ceilinglist.slideAnimHelper.SimpleItemTouchHelperCallbak;

import java.util.Collections;
import java.util.List;

/**
 * Created by ar4er25 on 3/5/2017.
 */

public class CeilingListFragmentRecycler extends Fragment implements OnStartDragListener {
    private RecyclerView mCeilingRecyclerView;
    private CeilingAdapter mAdapter;
    private LinearLayout mEmptyView;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
       /* interstitialAd = Ads.loadInterstitialAd(getActivity());
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                startCalculator();
            }
        });*/
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ceiling_list_recycler, container, false);
        //Ads.showBottomBanner(getActivity(), view);
        mEmptyView = (LinearLayout) view.findViewById(R.id.empty_view);
        Button newButton = (Button) view.findViewById(R.id.new_button);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCalculator();
            }
        });
        mCeilingRecyclerView = (RecyclerView) view.findViewById(R.id.ceiling_recycler_view);
        mCeilingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI() {
        List<SuspendCeiling> ceilings = CeilingLab.getCeilingLab(getActivity()).getList();
        mAdapter = new CeilingAdapter(ceilings);
        mCeilingRecyclerView.setAdapter(mAdapter);
        mAdapter.setCeilings(ceilings);
        mAdapter.notifyDataSetChanged();
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallbak(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mCeilingRecyclerView);
        setVisibleEmptyView();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new_ceiling:
                /*if (interstitialAd.isLoaded()) {
                    Ads.showInterstitialAd(interstitialAd);
                } else*/
                    startCalculator();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startCalculator() {
        Intent intent = new Intent(getActivity(), CalculatorActivity.class);
        startActivity(intent);
    }

    private void setVisibleEmptyView() {
        if (mAdapter.getCeilings().size() == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.INVISIBLE);
        }
    }

    private class CeilingHoder extends RecyclerView.ViewHolder implements View.OnClickListener, ItemTouchHelperViewHolder {
        private TextView mTitleTextView;
        private TextView mAreaTextView;
        private TextView mSizesTextView;
        private SuspendCeiling mCeiling;

        public CeilingHoder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_title_string);
            mAreaTextView = (TextView) itemView.findViewById(R.id.list_item_area);
            mSizesTextView = (TextView) itemView.findViewById(R.id.list_item_sizes_string);
        }

        public void bindCeiling(SuspendCeiling ceiling) {
            mCeiling = ceiling;
            mTitleTextView.setText(mCeiling.getName());
            mSizesTextView.setText(mCeiling.toString());
            mAreaTextView.setText(mCeiling.areaToString());
        }

        @Override
        public void onClick(View v) {
            Intent intent = PagerCeilingsActivity.newIntent(getActivity(), mCeiling.getId());
            startActivity(intent);
        }

        @Override
        public void onItemSelected() {

        }

        @Override
        public void onItemClear() {
        }
    }

    private class CeilingAdapter extends RecyclerView.Adapter<CeilingHoder> implements ItemTouchHelperAdapter {

        private List<SuspendCeiling> mCeilings;

        public CeilingAdapter(List<SuspendCeiling> ceilings) {
            mCeilings = ceilings;
        }

        @Override
        public CeilingHoder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_layout, parent, false);
            return new CeilingHoder(view);
        }

        public void setCeilings(List<SuspendCeiling> ceilings) {
            mCeilings = ceilings;
        }

        @Override
        public void onBindViewHolder(CeilingHoder holder, int position) {

            holder.bindCeiling(mCeilings.get(position));
        }

        @Override
        public int getItemCount() {
            return mCeilings.size();
        }

        @Override
        public boolean onItemMove(int fromPosition, int toPosition) {
            Collections.swap(mCeilings, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onItemDismiss(int position) {
            SuspendCeiling delC = mCeilings.get(position);
            CeilingLab.getCeilingLab(getActivity()).deliteCeiling(delC);
            mCeilings.remove(position);
            notifyItemRemoved(position);
            setVisibleEmptyView();
        }

        public List<SuspendCeiling> getCeilings() {
            return mCeilings;
        }
    }


}
