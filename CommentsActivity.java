package com.sarthak.amigo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;

public class CommentsActivity extends AppCompatActivity
{
    private RecyclerView commentsLists;
    private ImageButton PostCommentButton;
    private EditText CommentInputText;

    private String Post_Key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Post_Key=getIntent().getExtras().get("PostKey").toString() ;

        commentsLists=(RecyclerView)findViewById(R.id.comments_lists);
        commentsLists.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        commentsLists.setLayoutManager(linearLayoutManager);

        CommentInputText=(EditText)findViewById(R.id.comment_inputs);
        PostCommentButton=(ImageButton)findViewById(R.id.post_comment_button);

    }
}
