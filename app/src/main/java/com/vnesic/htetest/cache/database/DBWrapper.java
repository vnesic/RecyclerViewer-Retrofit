package com.vnesic.htetest.cache.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.vnesic.htetest.cache.CacheHandler;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DBWrapper {
    private static String TAG = String.valueOf(DBWrapper.class);
    private static AppDatabase mDB;
    private static PostDao mPostDao;
    Context mContext;

    public DBWrapper(Context context){
        mContext = context;
        mDB = Room.databaseBuilder(mContext,
                AppDatabase.class, "fancyDB").build();
        mPostDao = mDB.userDao();
    }

    public  List<Post> getAll()  {
        return mPostDao.getAll();
    }

    public static void deleteSpecificPost(String id){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mPostDao.deleteSpecificPost(id);
                return null;
            }
        }.execute();
    }

    public static void insertAll(final Post[] posts){
        mPostDao.insertAll(posts);
    }

    public static void deleteAll(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mPostDao.palpatin_doIt();
                return null;
            }
        }.execute();
    }

    public static void deletePost(Post p) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mPostDao.delete(p);
                return null;
            }
        }.execute();
    }
}
