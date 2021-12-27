/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.vnesic.htetest.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vnesic.htetest.R;
import com.vnesic.htetest.cache.CacheHandler;
import com.vnesic.htetest.cache.database.Post;
import com.vnesic.htetest.ui.adapter.CustomAdapter;

import org.jetbrains.annotations.TestOnly;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewFragment extends Fragment {

    private static final String TAG = "RecyclerViewFragment";
    private static final int MOCK_DATASET_COUNT = 60;

    protected RecyclerView mRecyclerView;
    protected CustomAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected List<Post> mDataset;
    private Button mRefreshButton;
    private TextView mTextView;
    private List<Post> mData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        CacheHandler.getIntance(getActivity().getApplicationContext());
        initDataset();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");

        View rootView = inflater.inflate(R.layout.recycler_view_frag, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        mRefreshButton = rootView.findViewById(R.id.refreshButton);
        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CacheHandler.getIntance().refreshCache();
            }
        });

        mTextView = rootView.findViewById(R.id.downloadingText);
        mRecyclerView = rootView.findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL);

        mRecyclerView.addItemDecoration(dividerItemDecoration);
        new Thread(){
            @Override
            public void run() {
                super.run();
               Log.d(TAG,"getPostsBlocking");

                mData = CacheHandler.getIntance().getPostsBlocking();
                h.sendEmptyMessage(0);
            }
        }.start();


        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
    }

    Handler h = new Handler(){
        @Override
        public void handleMessage(Message msg){
            Log.d(TAG,"handleMessage");
            populateScreenWithTheGoodStuff();
        }
    };

    public void populateScreenWithTheGoodStuff(){
        Log.d(TAG,"populateScreenWithTheGoodStuff");


        mAdapter = new CustomAdapter(mData);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.scrollToPosition(0);

        mRecyclerView.setVisibility(View.VISIBLE);
    }



    @TestOnly
    private void initDataset() {
        mDataset = new ArrayList<>();
        for (int i = 0; i < MOCK_DATASET_COUNT; i++) {
            Post newPost = new Post();
            newPost.setBody("This is element #" + i);
            newPost.setTitle("Title #" + i);
            mDataset.add(newPost);
        }
    }
}
