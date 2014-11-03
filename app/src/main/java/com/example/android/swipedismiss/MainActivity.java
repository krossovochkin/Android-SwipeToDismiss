/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.swipedismiss;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private static final int ITEMS_COUNT = 20;
    private List<String> mItems;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter<CustomViewHolder> mAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initRecyclerView();
    }

    private void initData() {
        mItems = new ArrayList<String>();
        for (int i = 0; i < ITEMS_COUNT; i++) {
            mItems.add("Item " + (i + 1));
        }
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerView.Adapter<CustomViewHolder>() {
            @Override
            public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_1
                        , viewGroup, false);
                return new CustomViewHolder(view);
            }

            @Override
            public void onBindViewHolder(CustomViewHolder viewHolder, int i) {
                viewHolder.mTextView.setText(mItems.get(i));
            }

            @Override
            public int getItemCount() {
                return mItems.size();
            }
        };
        mRecyclerView.setAdapter(mAdapter);

        SwipeDismissRecyclerViewTouchListener touchListener =
                new SwipeDismissRecyclerViewTouchListener(
                        mRecyclerView,
                        new SwipeDismissRecyclerViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
//                                    mLayoutManager.removeView(mLayoutManager.getChildAt(position));
                                    mItems.remove(position);
                                    mAdapter.notifyItemRemoved(position);
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        });
        mRecyclerView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        mRecyclerView.setOnScrollListener(touchListener.makeScrollListener());
    }

    private class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public CustomViewHolder(View itemView) {
            super(itemView);

            mTextView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}
