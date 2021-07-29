package com.editmypdffree.rdtl.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.editmypdffree.rdtl.R;

public class AboutUsFragment extends Fragment {

    private Activity mActivity;

    LinearLayout web;
    LinearLayout fb;
    LinearLayout rate;
    LinearLayout more;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about_us, container, false);
        ButterKnife.bind(this, rootView);
        try {
            PackageInfo packageInfo = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0);
            TextView versionText = rootView.findViewById(R.id.version_value);
            String version = versionText.getText().toString() + " " + packageInfo.versionName;
            versionText.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        web = (LinearLayout) rootView.findViewById(R.id.aboutweb);
        web.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Uri uri = Uri.parse("https://radissonbd.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);


            }
        });

        fb = (LinearLayout) rootView.findViewById(R.id.aboutfb);
        fb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Uri uri = Uri.parse("https://www.facebook.com/rdtl.info/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);


            }
        });

        rate = (LinearLayout) rootView.findViewById(R.id.aboutrate);
        rate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.editmypdffree.rdtl");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);


            }
        });

        more = (LinearLayout) rootView.findViewById(R.id.aboutmore);
        more.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Uri uri = Uri.parse("https://play.google.com/store/apps/developer?id=Radisson+Digital+Technologies+Ltd");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);


            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

}
