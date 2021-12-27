package com.vnesic.htetest.util;

import android.util.Log;

import com.vnesic.htetest.cache.database.Post;
import com.vnesic.htetest.network.retrofit.results.HtecResults;

import java.util.List;

public class Utility {

    public static String kUSER_ID = "userId";
    public static String kPOST_ID = "postId";
    public static String kTITLE = "title";
    public static String kTEXT_BODY = "text";


    public static Post[] resultToPostConverter(List<HtecResults> list){
        Post[] returnList = new Post[list.size()];
        for (int i=0;i<list.size();i++){
            Post post = new Post();
            post.setTitle(list.get(i).getTitle());
            post.setId(list.get(i).getId());
            post.setUserId(list.get(i).getUserId());
            post.setBody(list.get(i).getBody());
            returnList[i]=post;
        }
        return returnList;
    }

}
