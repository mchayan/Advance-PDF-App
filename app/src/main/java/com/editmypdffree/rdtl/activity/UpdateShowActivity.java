package com.editmypdffree.rdtl.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.editmypdffree.rdtl.R;
import com.editmypdffree.rdtl.util.FeedbackUtils;

public class UpdateShowActivity extends AppCompatActivity {


    ImageView ivUpdateapp;

    private FeedbackUtils mFeedbackUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_show);


        ivUpdateapp = findViewById(R.id.pdfOpen);

        mFeedbackUtils = new FeedbackUtils(this);

        ivUpdateapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                try {
//                    getApplicationContext().startActivity(new Intent(Intent.ACTION_VIEW,
//                            Uri.parse("market://details?id=" +
//                                    getApplicationContext().getPackageName())));
//                } catch (Exception e) {
//                    openWebPage("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
//                }

//                mFeedbackUtils.rateUs();

                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.editmypdffree.rdtl");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

//    public void openWebPage(String url) {
//        Uri uri = Uri.parse(url);
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        if (intent.resolveActivity(getApplicationContext().getPackageManager()) != null)
//            getApplicationContext().startActivity(intent);
//    }
}