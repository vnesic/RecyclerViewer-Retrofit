package com.vnesic.htetest.ui.elements;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.vnesic.htetest.R;

public class PostItem extends LinearLayout {

    private TextView body;
    private TextView header;
    private int userId;
    private int postId;
    private String fullText;

    public PostItem(Context context, AttributeSet set) {
        super(context, set);
        initLayout(context);
    }

    public PostItem(Context context) {
        super(context);
        initLayout(context);
    }

    private void initLayout(Context context){
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v =inflater.inflate(R.layout.post_item_layout, this);
        body = v.findViewById(R.id.bodyText);
        header = v.findViewById(R.id.headerText);
    }

    public void setBodyText(String text){
        body.setText(text);
    }

    public void setHeaderText(String text){
        header.setText(text);
    }

    public void setUserId(int id){
        userId = id;
    }

    public String getUserId(){
        return String.valueOf(userId);
    }

    public TextView getBody() {
        return body;
    }

    public TextView getHeader() {
        return header;
    }

    public String getPostId() {
        return  String.valueOf(postId);
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getFullText() {
        return  fullText;
    }

    public void setFullText(String text) {
        fullText = text;
    }

}
