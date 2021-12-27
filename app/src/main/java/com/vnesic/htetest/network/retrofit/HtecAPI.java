package com.vnesic.htetest.network.retrofit;

import com.vnesic.htetest.network.retrofit.results.HtecResults;
import com.vnesic.htetest.network.retrofit.results.HtecUser;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface HtecAPI {

    String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @GET("posts")
    public Observable<List<HtecResults>> getPosts();

    @GET("users/{userId}")
    public Observable<HtecUser> getUserData(@Path("userId") String id);
}
