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

package com.vnesic.htetest.ui.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.vnesic.htetest.R;
import com.vnesic.htetest.cache.database.Post;
import com.vnesic.htetest.ui.UserTextFragment;
import com.vnesic.htetest.ui.elements.PostItem;
import com.vnesic.htetest.util.Utility;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private static final String TAG = String.valueOf(CustomAdapter.class);

    private List<Post> mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final PostItem postItem;

        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    UserTextFragment myFragment = new UserTextFragment();
                    Bundle userData = new Bundle();
                    Log.d(TAG,"SET FULL TEX "+String.valueOf(postItem.getFullText()));
                    userData.putString(Utility.kUSER_ID,postItem.getUserId());
                    userData.putString(Utility.kTEXT_BODY, String.valueOf(postItem.getFullText()));
                    userData.putString(Utility.kTITLE, (String) postItem.getHeader().getText());
                    userData.putString(Utility.kPOST_ID, String.valueOf(postItem.getId()));

                    myFragment.setArguments(userData);// TODO pass data here
                    activity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.sample_content_fragment, myFragment).addToBackStack(null).commit();

                    //on transaction fragment
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            postItem = v.findViewById(R.id.postView);
        }

        public PostItem getPostItem() {
            return postItem;
        }
    }


    public void updatePostList(final List<Post> userArrayList) {
        if(mDataSet!=null)
            this.mDataSet.clear();
        this.mDataSet = userArrayList;
        notifyDataSetChanged();
    }


    public CustomAdapter(List<Post> dataSet) {
        Log.d(TAG, "constructor");
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "CustomData " + position + " set.");

        String[] result = mDataSet.get(position).getBody().split("\n", 2);

        viewHolder.getPostItem().setBodyText(result[0]);
        viewHolder.getPostItem().setFullText(mDataSet.get(position).getBody());
        viewHolder.getPostItem().setHeaderText(mDataSet.get(position).getTitle());
        viewHolder.getPostItem().setUserId(mDataSet.get(position).getUserId());
        viewHolder.getPostItem().setId((int) mDataSet.get(position).getId());

    }

    @Override
    public int getItemCount() {
        if(mDataSet!=null) {
            return mDataSet.size();
        }else {
            return 0;
        }
    }
}
