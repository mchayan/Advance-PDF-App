package com.editmypdffree.rdtl.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.editmypdffree.rdtl.R;
import com.editmypdffree.rdtl.activity.MainActivity;
import com.editmypdffree.rdtl.adapter.RecentListAdapter;
import com.editmypdffree.rdtl.customviews.MyCardView;
import com.editmypdffree.rdtl.fragment.texttopdf.TextToPdfFragment;
import com.editmypdffree.rdtl.model.HomePageItem;
import com.editmypdffree.rdtl.util.AdLoader;
import com.editmypdffree.rdtl.util.CommonCodeUtils;
import com.editmypdffree.rdtl.util.RecentUtil;

import static android.content.ContentValues.TAG;
import static com.editmypdffree.rdtl.util.Constants.ADD_IMAGES;
import static com.editmypdffree.rdtl.util.Constants.ADD_PWD;
import static com.editmypdffree.rdtl.util.Constants.ADD_WATERMARK;
import static com.editmypdffree.rdtl.util.Constants.ADD_WATERMARK2;
import static com.editmypdffree.rdtl.util.Constants.BUNDLE_DATA;
import static com.editmypdffree.rdtl.util.Constants.COMPRESS_PDF;
import static com.editmypdffree.rdtl.util.Constants.EXTRACT_IMAGES;
import static com.editmypdffree.rdtl.util.Constants.PDF_TO_IMAGES;
import static com.editmypdffree.rdtl.util.Constants.REMOVE_PAGES;
import static com.editmypdffree.rdtl.util.Constants.REMOVE_PWd;
import static com.editmypdffree.rdtl.util.Constants.REORDER_PAGES;
import static com.editmypdffree.rdtl.util.Constants.ROTATE_PAGE;
import static com.editmypdffree.rdtl.util.Constants.ROTATE_PAGES;

public class  HomeFragment extends Fragment implements View.OnClickListener {

    private Activity mActivity;
    @BindView(R.id.images_to_pdf)
    MyCardView imagesToPdf;
    @BindView(R.id.qr_barcode_to_pdf)
    MyCardView qrbarcodeToPdf;
    @BindView(R.id.text_to_pdf)
    MyCardView textToPdf;
    @BindView(R.id.view_files)
    MyCardView viewFiles;
    @BindView(R.id.view_history)
    MyCardView viewHistory;
    @BindView(R.id.split_pdf)
    MyCardView splitPdf;
    @BindView(R.id.merge_pdf)
    MyCardView mergePdf;
    @BindView(R.id.compress_pdf)
    MyCardView compressPdf;
    @BindView(R.id.remove_pages)
    MyCardView removePages;
    @BindView(R.id.rearrange_pages)
    MyCardView rearrangePages;
    @BindView(R.id.extract_images)
    MyCardView extractImages;
    @BindView(R.id.pdf_to_images)
    MyCardView mPdfToImages;
    @BindView(R.id.add_password)
    MyCardView addPassword;
    @BindView(R.id.remove_password)
    MyCardView removePassword;
    @BindView(R.id.rotate_pages)
    MyCardView rotatePdf;
    @BindView(R.id.add_watermark)
    MyCardView addWatermark;
    @BindView(R.id.add_images)
    MyCardView addImages;
    @BindView(R.id.remove_duplicates_pages_pdf)
    MyCardView removeDuplicatePages;
    @BindView(R.id.invert_pdf)
    MyCardView invertPdf;
    @BindView(R.id.zip_to_pdf)
    MyCardView zipToPdf;
    @BindView(R.id.excel_to_pdf)
    MyCardView excelToPdf;
    @BindView(R.id.extract_text)
    MyCardView extractText;
    @BindView(R.id.add_text)
    MyCardView addText;

    @BindView(R.id.recent_list)
    RecyclerView recentList;

    @BindView(R.id.recent_lbl)
    View recentLabel;

    @BindView(R.id.recent_list_lay)
    ViewGroup recentLayout;

    @BindView(R.id.relative)
    RelativeLayout relative;
    @BindView(R.id.toggle)
    ImageView toggle;
    @BindView(R.id.toggle1)
    ImageView toggle1;
    @BindView(R.id.ScrlView)
    ScrollView scrollView1;

    private int oldScrolly;
    private Map<Integer, HomePageItem> mFragmentPositionMap;
    private RecentListAdapter mAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_home_, container, false);
        ButterKnife.bind(this, rootview);
        mFragmentPositionMap = CommonCodeUtils.getInstance().fillNavigationItemsMap(true);

//        ScrollView scrollView=(ScrollView)rootview.findViewById(R.id.ScrlView);
//        scrollView1.setOnScrollChangeListener(new TouchDetectableScrollView.OnMyScrollChangeListener() {
//
//            public void onScrollUp() {
//                //Toast.makeText(getActivity(), "Scrolling up", Toast.LENGTH_SHORT).show();
//                Log.d("scroll","up");
//
//            }
//
//
//            public void onScrollDown() {
//                // Toast.makeText(getActivity(), "Scrolling down", Toast.LENGTH_SHORT).show();
//                Log.d("scroll","down");
//
//            }
//        });

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            scrollView1.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//                    Toast.makeText(getActivity(), "Scrolling down", Toast.LENGTH_SHORT).show();
//                }
////                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
////
////                }
//                public void onScrollDown() {
//                 Toast.makeText(getActivity(), "Scrolling down", Toast.LENGTH_SHORT).show();
//                Log.d("scroll","down");
//
//            }
//
//            });
//        }


        imagesToPdf.setOnClickListener(this);
        qrbarcodeToPdf.setOnClickListener(this);
        textToPdf.setOnClickListener(this);
        viewFiles.setOnClickListener(this);
        viewHistory.setOnClickListener(this);
        splitPdf.setOnClickListener(this);
        mergePdf.setOnClickListener(this);
        compressPdf.setOnClickListener(this);
        removePages.setOnClickListener(this);
        rearrangePages.setOnClickListener(this);
        extractImages.setOnClickListener(this);
        mPdfToImages.setOnClickListener(this);
        addPassword.setOnClickListener(this);
        removePassword.setOnClickListener(this);
        rotatePdf.setOnClickListener(this);
        addWatermark.setOnClickListener(this);
        addImages.setOnClickListener(this);
        removeDuplicatePages.setOnClickListener(this);
        invertPdf.setOnClickListener(this);
        zipToPdf.setOnClickListener(this);
        excelToPdf.setOnClickListener(this);
        extractText.setOnClickListener(this);
        addText.setOnClickListener(this);
        mAdapter =  new RecentListAdapter(this);
        recentList.setAdapter(mAdapter);

        return rootview;
    }


    @Override public void onViewCreated(
            @NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        try {
            LinkedHashMap<String, Map<String, String>> mRecentList = RecentUtil.getInstance()
                    .getList(PreferenceManager.getDefaultSharedPreferences(mActivity));
            if (!mRecentList.isEmpty()) {
                recentLabel.setVisibility(View.VISIBLE);
                recentLayout.setVisibility(View.VISIBLE);
                List<String> featureItemIds = new ArrayList<>(mRecentList.keySet());
                List<Map<String, String>> featureItemList = new ArrayList<>(mRecentList.values());
                mAdapter.updateList(featureItemIds, featureItemList);
                mAdapter.notifyDataSetChanged();
            } else {
                recentLabel.setVisibility(View.GONE);
                recentLayout.setVisibility(View.GONE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        toggle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (relative.getVisibility() == View.VISIBLE)
                {
                    relative.setVisibility(View.GONE);
                }
                else
                {
                    relative.setVisibility(View.VISIBLE);
                }


                if (toggle1.getVisibility() == View.GONE)
                {
                    toggle1.setVisibility(View.VISIBLE);
                }
                else
                {
                    toggle1.setVisibility(View.VISIBLE);
                }

            }
        });

        toggle1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (toggle1.getVisibility() == View.VISIBLE)
                {
                    toggle1.setVisibility(View.GONE);
                }
                else
                {
                    toggle1.setVisibility(View.VISIBLE);
                }


                if (relative.getVisibility() == View.VISIBLE)
                {
                    relative.setVisibility(View.INVISIBLE);
                }
                else
                {
                    relative.setVisibility(View.VISIBLE);
                }


            }
        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onClick(View v) {

        Fragment fragment = null;
        FragmentManager fragmentManager = getFragmentManager();
        Bundle bundle = new Bundle();

        highlightNavigationDrawerItem(mFragmentPositionMap.get(v.getId()).getNavigationItemId());
        setTitleFragment(mFragmentPositionMap.get(v.getId()).getTitleString());

        Map<String, String> feature = new HashMap<>();
        feature.put(
                String.valueOf(mFragmentPositionMap.get(v.getId()).getTitleString()),
                String.valueOf(mFragmentPositionMap.get(v.getId()).getmDrawableId()));

        try {
            RecentUtil.getInstance().addFeatureInRecentList(PreferenceManager
                    .getDefaultSharedPreferences(mActivity), v.getId(), feature);
        } catch (JSONException e) {
            e.printStackTrace();
        }



        switch (v.getId()) {
            case R.id.images_to_pdf:
                AdLoader.getAds().showFBFirst(mActivity);
                //Toast.makeText(mActivity, "images_to_pdf", Toast.LENGTH_SHORT).show();
                fragment = new ImageToPdfFragment();

                break;
            case R.id.qr_barcode_to_pdf:
                AdLoader.getAds().showFBFirst(mActivity);
                //Toast.makeText(mActivity, "qr_barcode_to_pdf", Toast.LENGTH_SHORT).show();
                fragment = new QrBarcodeScanFragment();
                break;
            case R.id.text_to_pdf:
                AdLoader.getAds().showFBFirst(mActivity);
                //Toast.makeText(mActivity, "text_to_pdf", Toast.LENGTH_SHORT).show();
                fragment = new TextToPdfFragment();
                break;
            case R.id.view_files:
                AdLoader.getAds().showFBFirst(mActivity);
                //Toast.makeText(mActivity, "view_files", Toast.LENGTH_SHORT).show();
                fragment = new ViewFilesFragment();
                break;
            case R.id.view_history:
                AdLoader.getAds().showFBFirst(mActivity);
                //Toast.makeText(mActivity, "view_history", Toast.LENGTH_SHORT).show();
                fragment = new HistoryFragment();
                break;
            case R.id.merge_pdf:
                AdLoader.getAds().showFBFirst(mActivity);
                //Toast.makeText(mActivity, "merge_pdf", Toast.LENGTH_SHORT).show();
                fragment = new MergeFilesFragment();
                break;
            case R.id.split_pdf:
                AdLoader.getAds().showFBFirst(mActivity);
                //Toast.makeText(mActivity, "split_pdf", Toast.LENGTH_SHORT).show();
                fragment = new SplitFilesFragment();
                break;
            case R.id.compress_pdf:
                AdLoader.getAds().showFBFirst(mActivity);
                //Toast.makeText(mActivity, "compress_pdf", Toast.LENGTH_SHORT).show();
                fragment = new RemovePagesFragment();
                bundle.putString(BUNDLE_DATA, COMPRESS_PDF);
                fragment.setArguments(bundle);
                break;
            case R.id.extract_images:
                AdLoader.getAds().showFBFirst(mActivity);
                //Toast.makeText(mActivity, "extract_images", Toast.LENGTH_SHORT).show();
                fragment = new PdfToImageFragment();
                bundle.putString(BUNDLE_DATA, EXTRACT_IMAGES);
                fragment.setArguments(bundle);
                break;
            case R.id.pdf_to_images:
                AdLoader.getAds().showFBFirst(mActivity);
                //Toast.makeText(mActivity, "pdf_to_images", Toast.LENGTH_SHORT).show();
                fragment = new PdfToImageFragment();
                bundle.putString(BUNDLE_DATA, PDF_TO_IMAGES);
                fragment.setArguments(bundle);
                break;
            case R.id.remove_pages:
                AdLoader.getAds().showFBFirst(mActivity);
                //Toast.makeText(mActivity, "remove_pages", Toast.LENGTH_SHORT).show();
                fragment = new RemovePagesFragment();
                bundle.putString(BUNDLE_DATA, REMOVE_PAGES);
                fragment.setArguments(bundle);
                break;
            case R.id.rearrange_pages:
                AdLoader.getAds().showFBFirst(mActivity);
                //Toast.makeText(mActivity, "rearrange_pages", Toast.LENGTH_SHORT).show();
                fragment = new RemovePagesFragment();
                bundle.putString(BUNDLE_DATA, REORDER_PAGES);
                fragment.setArguments(bundle);
                break;
            case R.id.add_password:
                AdLoader.getAds().showFBFirst(mActivity);
                //Toast.makeText(mActivity, "add_password", Toast.LENGTH_SHORT).show();
                fragment = new RemovePagesFragment();
                bundle.putString(BUNDLE_DATA, ADD_PWD);
                fragment.setArguments(bundle);
                break;
            case R.id.remove_password:
                AdLoader.getAds().showFBFirst(mActivity);
                //Toast.makeText(mActivity, "remove_password", Toast.LENGTH_SHORT).show();
                fragment = new RemovePagesFragment();
                bundle.putString(BUNDLE_DATA, REMOVE_PWd);
                fragment.setArguments(bundle);
                break;
            case R.id.rotate_pages:
                AdLoader.getAds().showFBFirst(mActivity);
                //Toast.makeText(mActivity, "rotate_pages", Toast.LENGTH_SHORT).show();
//                fragment = new ViewFilesFragment();
//                bundle.putInt(BUNDLE_DATA, ROTATE_PAGES);
//                fragment.setArguments(bundle);

                fragment = new RemovePagesFragment();
                bundle.putString(BUNDLE_DATA, ROTATE_PAGE);
                fragment.setArguments(bundle);
                break;
            case R.id.add_watermark:
                AdLoader.getAds().showFBFirst(mActivity);
                //Toast.makeText(mActivity, "add_watermark", Toast.LENGTH_SHORT).show();
//                fragment = new ViewFilesFragment();
//                bundle.putInt(BUNDLE_DATA, ADD_WATERMARK);
//                fragment.setArguments(bundle);

                //Toast.makeText(mActivity, "clicked", Toast.LENGTH_SHORT).show();

                fragment = new RemovePagesFragment();
                bundle.putString(BUNDLE_DATA, ADD_WATERMARK2);
                fragment.setArguments(bundle);
                break;
            case R.id.add_images:
                AdLoader.getAds().showFBFirst(mActivity);
                //Toast.makeText(mActivity, "add_images", Toast.LENGTH_SHORT).show();
                fragment = new AddImagesFragment();
                bundle.putString(BUNDLE_DATA, ADD_IMAGES);
                fragment.setArguments(bundle);
                break;
            case R.id.remove_duplicates_pages_pdf:
                //Toast.makeText(mActivity, "remove_duplicates_pages_pdf", Toast.LENGTH_SHORT).show();
                AdLoader.getAds().showFBFirst(mActivity);
                fragment = new RemoveDuplicatePagesFragment();
                break;
            case R.id.invert_pdf:
                //Toast.makeText(mActivity, "invert_pdf", Toast.LENGTH_SHORT).show();
                AdLoader.getAds().showFBFirst(mActivity);
                fragment = new InvertPdfFragment();
                break;
            case R.id.zip_to_pdf:
                AdLoader.getAds().showFBFirst(mActivity);
                //Toast.makeText(mActivity, "zip_to_pdf", Toast.LENGTH_SHORT).show();
                fragment = new ZipToPdfFragment();
                break;
            case R.id.excel_to_pdf:
                AdLoader.getAds().showFBFirst(mActivity);
                //Toast.makeText(mActivity, "excel_to_pdf", Toast.LENGTH_SHORT).show();
                fragment = new ExceltoPdfFragment();
                break;
            case R.id.extract_text:
                AdLoader.getAds().showFBFirst(mActivity);
                //Toast.makeText(mActivity, "extract_text", Toast.LENGTH_SHORT).show();
                fragment = new ExtractTextFragment();
                break;
            case R.id.add_text:
                AdLoader.getAds().showFBFirst(mActivity);
                //Toast.makeText(mActivity, "add_text", Toast.LENGTH_SHORT).show();
                fragment = new AddTextFragment();
                break;
        }

        try {
            if (fragment != null && fragmentManager != null)
                fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Highligh navigation drawer item
     * @param id - item id to be hjighlighted
     */
    private void highlightNavigationDrawerItem(int id) {
        if (mActivity instanceof MainActivity)
            ((MainActivity) mActivity).setNavigationViewSelection(id);
    }

    /**
     * Sets the title on action bar
     * @param title - title of string to be shown
     */
    private void setTitleFragment(int title) {
        if (title != 0)
            mActivity.setTitle(title);
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

}
