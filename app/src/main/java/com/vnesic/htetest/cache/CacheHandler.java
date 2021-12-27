package com.vnesic.htetest.cache;

import static com.vnesic.htetest.util.Utility.resultToPostConverter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.vnesic.htetest.cache.database.DBWrapper;
import com.vnesic.htetest.cache.database.Post;
import com.vnesic.htetest.network.retrofit.RetrofitClient;
import com.vnesic.htetest.network.retrofit.results.HtecResults;

import java.util.List;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CacheHandler {

    private static String TAG = String.valueOf(CacheHandler.class);
    private static Context mContext;
    private DBWrapper mDBWrapper;
    private Thread mRefreshThread;
    private volatile boolean mUpdated = false;
    private Semaphore mSemaphore = new Semaphore(0);
    private Object mObject = new Object();
    private static CacheHandler mInstance;

    CacheHandler(Context context){
        mContext = context;
        mDBWrapper = new DBWrapper(mContext);
        refreshCache();
    }

    public static  CacheHandler getIntance(){
        if(mInstance == null){
            mInstance = new CacheHandler(mContext);
        }
        return mInstance;
    }

    public static CacheHandler getIntance(Context context){
        mInstance = new CacheHandler(context);
        mContext = context;
        return mInstance;
    }

    public List<Post> getPostsBlocking(){
        Log.d(TAG, "getPostsBlocking");
        if (!mUpdated) {
            Log.d(TAG, "getPostsBlocking DOWNLOAD");

            downloadPosts();
           try {
                mSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "getPostsBlocking FROM CACHE");
        return mDBWrapper.getAll();
    }

    public void deletePost(Post p) {
        Log.d(TAG, "deletePost for id "+p);
        mDBWrapper.deletePost(p);

    }
    public Void deleteSpecificPost(String id){
        Log.d(TAG, "downloadPosts for id "+id);
        mDBWrapper.deleteSpecificPost(id);
        return null;
    }

    public  void downloadPosts() {
        Log.d(TAG, "downloadPosts");
        synchronized (mObject) {
            RetrofitClient.getInstance().getMyApi().getPosts().subscribeOn(Schedulers.newThread())
                    .observeOn(Schedulers.newThread())
                    .subscribe(new DisposableObserver<List<HtecResults>>() {

                        @Override
                        public void onNext(@NonNull List<HtecResults> htecResults) {
                            Log.d(TAG, "downloadPosts onNext(");
                            DBWrapper.insertAll(resultToPostConverter(htecResults));
                            mUpdated = true;
                            mSemaphore.release();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.d(TAG, "downloadPosts onError");

                            mUpdated = false;
                            mSemaphore.release();
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "downloadPosts onComplete");

                        }
                    });
        }
    }

    public Void refreshCache(){
        Log.d(TAG,"refreshCache");
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    if(mRefreshThread != null)
                    mRefreshThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mRefreshThread = new RefreshThread();
                mRefreshThread.start();
                return null;
            }
        }.execute();
        return null;
    }

    class RefreshThread extends Thread{
        @Override
        public void run() {
            super.run();
            while (true) {
                mUpdated = false;
                Log.d("RefreshT","refresh thread");
                deleteAll();
                downloadPosts();
                try {
                    //sleeps for 5 minutes
                    sleep(5 * 10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Void deleteAll(){
        Log.d(TAG, "deleteAll ");
        DBWrapper.deleteAll();
        return null;
    }

}
