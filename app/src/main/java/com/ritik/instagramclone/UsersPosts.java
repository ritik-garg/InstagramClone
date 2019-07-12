package com.ritik.instagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class UsersPosts extends AppCompatActivity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_posts);

        linearLayout = findViewById(R.id.linearLayout);

        Intent receivedIntentObject = getIntent();
        final String receivedUsername = receivedIntentObject.getStringExtra("username");

        setTitle(receivedUsername + "'s Posts");

        ParseQuery<ParseObject> parseQuery = new ParseQuery<>("Photo");
        parseQuery.whereEqualTo("username", receivedUsername);
        parseQuery.orderByDescending("createdAt");

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(objects.size() > 0 && e == null) {
                    for(ParseObject post : objects) {
                        final TextView postDescription = new TextView(UsersPosts.this);
                        postDescription.setText(post.get("img_des") + "");

                        ParseFile postPicture = (ParseFile) post.get("picture");
                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(data != null && e == null) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    ImageView postImageView = new ImageView(UsersPosts.this);
                                    LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    imageViewParams.setMargins(8, 8, 8, 8);
                                    postImageView.setLayoutParams(imageViewParams);
                                    postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImageView.setImageBitmap(bitmap);

                                    LinearLayout.LayoutParams des_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    des_params.setMargins(8, 8, 8, 15);
                                    postDescription.setLayoutParams(des_params);
                                    postDescription.setGravity(Gravity.LEFT);
                                    postDescription.setBackgroundColor(Color.LTGRAY);
                                    postDescription.setTextSize(14f);

                                    linearLayout.addView(postImageView);
                                    linearLayout.addView(postDescription);
                                }
                            }
                        });
                    }
                }
                else {
                    FancyToast.makeText(UsersPosts.this, "No Posts of This User", FancyToast.LENGTH_LONG, FancyToast.INFO, false).show();
                    finish();
                }
                progressDialog.dismiss();
            }
        });
    }
}
