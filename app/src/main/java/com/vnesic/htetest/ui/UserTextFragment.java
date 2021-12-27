package com.vnesic.htetest.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.vnesic.htetest.R;
import com.vnesic.htetest.cache.CacheHandler;
import com.vnesic.htetest.cache.database.Post;
import com.vnesic.htetest.network.retrofit.RetrofitClient;
import com.vnesic.htetest.network.retrofit.results.HtecUser;
import com.vnesic.htetest.util.Utility;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserTextFragment extends Fragment {
    private static final String TAG = "UserTextFragment";
    private TextView mName;
    private TextView mEmail;
    private TextView mTitle;
    private TextView mScrolableText;
    private Button mDeleteButton;

    String title;
    String body;
    int userId;
    String id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.user_frag, container, false);
        rootView.setTag(TAG);

        mName = rootView.findViewById(R.id.name);
        mEmail = rootView.findViewById(R.id.email);
        mScrolableText = rootView.findViewById(R.id.bodyText);
        mDeleteButton = rootView.findViewById(R.id.deletePost);

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CacheHandler.getIntance().deleteSpecificPost(id);
                getActivity().onBackPressed();
            }
        });

        mTitle = rootView.findViewById(R.id.title);

        Bundle bundle = this.getArguments();

        userId = Integer.parseInt(bundle.getString(Utility.kUSER_ID));
        title = bundle.getString(Utility.kTITLE);
        body = bundle.getString(Utility.kTEXT_BODY);
        id = bundle.getString(Utility.kPOST_ID);

        mScrolableText.setText(body);
        mTitle.setText(title);

        populateViews(userId);

        return rootView;
    }

    private void populateViews(int userId){
        Log.d(TAG,"populateViews "+userId);

        RetrofitClient.getInstance().getMyApi().getUserData(String.valueOf(userId)).subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new DisposableObserver<HtecUser>() {


                    @Override
                    public void onNext(@NonNull HtecUser htecResults) {
                        Log.d(TAG,"NEW SHIT "+htecResults.toString());
                        mName.setText(htecResults.getName());
                        mEmail.setText(htecResults.getEmail());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG,"onError "+e);

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG,"onComplete ");

                    }
                });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
    }

}
