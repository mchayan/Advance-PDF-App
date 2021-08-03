package com.editmypdffree.rdtl.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.editmypdffree.rdtl.adapter.RecyclerViewAdapter;
import com.editmypdffree.rdtl.interfaces.IOnBackPressed;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.airbnb.lottie.LottieAnimationView;
import com.dd.morphingbutton.MorphingButton;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.editmypdffree.rdtl.R;
import com.editmypdffree.rdtl.adapter.MergeFilesAdapter;
import com.editmypdffree.rdtl.interfaces.BottomSheetPopulate;
import com.editmypdffree.rdtl.interfaces.OnBackPressedInterface;
import com.editmypdffree.rdtl.util.BottomSheetCallback;
import com.editmypdffree.rdtl.util.BottomSheetUtils;
import com.editmypdffree.rdtl.util.CommonCodeUtils;
import com.editmypdffree.rdtl.util.DialogUtils;
import com.editmypdffree.rdtl.util.FileUriUtils;
import com.editmypdffree.rdtl.util.FileUtils;
import com.editmypdffree.rdtl.util.MorphButtonUtility;
import com.editmypdffree.rdtl.util.PDFUtils;
import com.editmypdffree.rdtl.util.PermissionsUtils;
import com.editmypdffree.rdtl.util.StringUtils;

import static com.editmypdffree.rdtl.util.Constants.ADD_IMAGES;
import static com.editmypdffree.rdtl.util.Constants.AUTHORITY_APP;
import static com.editmypdffree.rdtl.util.Constants.BUNDLE_DATA;
import static com.editmypdffree.rdtl.util.Constants.READ_WRITE_CAMERA_PERMISSIONS;

public class AddImagesFragment extends Fragment implements BottomSheetPopulate, IOnBackPressed,
        MergeFilesAdapter.OnClickListener, OnBackPressedInterface {

    private Activity mActivity;
    private String mPath;
    private MorphButtonUtility mMorphButtonUtility;
    private FileUtils mFileUtils;
    private BottomSheetUtils mBottomSheetUtils;
    private PDFUtils mPDFUtils;
    private static final int INTENT_REQUEST_PICK_FILE_CODE = 10;
    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    private String mOperation;
    private static final int REQUEST_PERMISSIONS_CODE = 124;
    private static final ArrayList<String> mImagesUri = new ArrayList<>();
    private boolean mPermissionGranted = false;
    private BottomSheetBehavior mSheetBehavior;

    RecyclerView recyclerViewinaddimage;

    @BindView(R.id.lottie_progress)
    LottieAnimationView mLottieProgress;
    @BindView(R.id.selectFile)
    MorphingButton selectFileButton;
    @BindView(R.id.pdfCreate)
    MorphingButton mCreatePdf;
    @BindView(R.id.addImages)
    MorphingButton addImages;
    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;
    @BindView(R.id.upArrow)
    ImageView mUpArrow;
    @BindView(R.id.layout)
    RelativeLayout mLayout;
    @BindView(R.id.recyclerViewFiles)
    RecyclerView mRecyclerViewFiles;
    @BindView(R.id.tvNoOfImages)
    TextView mNoOfImages;
    @BindView(R.id.idSelectImgLL)
    LinearLayout selectImageLL;

//    @BindView(R.id.select_excel_file2)
//    MorphingButton selectexlfile2;

    @BindView(R.id.popup)
    LinearLayout PopUp;
//    @BindView(R.id.popup2)
//    LinearLayout PopUp2;
    @BindView(R.id.rltvall)
    RelativeLayout rltvall;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_images, container, false);
        ButterKnife.bind(this, rootView);

        recyclerViewinaddimage = rootView.findViewById(R.id.myrecycler_view2);


        mPermissionGranted = PermissionsUtils.getInstance()
                .checkRuntimePermissions(this, READ_WRITE_CAMERA_PERMISSIONS);
        mSheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        mSheetBehavior.setBottomSheetCallback(new BottomSheetCallback(mUpArrow, isAdded()));
        mOperation = getArguments().getString(BUNDLE_DATA);
        mLottieProgress.setVisibility(View.VISIBLE);
        mBottomSheetUtils.populateBottomSheetWithPDFs(this);

        resetValues();
        return rootView;
    }

    /**
     * Displays file chooser intent
     */
    @OnClick(R.id.selectFile)
    public void showFileChooser() {

        mBottomSheetUtils.showHideSheet(mSheetBehavior);
        mSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//        startActivityForResult(mFileUtils.getFileChooser(),
//                INTENT_REQUEST_PICK_FILE_CODE);
    }

    /**
     * Called after Matisse Activity is called
     *
     * @param requestCode REQUEST Code for opening Matisse Activity
     * @param resultCode  result code of the process
     * @param data        Data of the image selected
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        switch (requestCode) {

            case INTENT_REQUEST_GET_IMAGES:
                // mImagesUri.clear();
                mImagesUri.addAll(Matisse.obtainPathResult(data));

                if (mImagesUri.size() > 0) {
                    mNoOfImages.setText(String.format(mActivity.getResources()
                            .getString(R.string.images_selected), mImagesUri.size()));
                    mNoOfImages.setVisibility(View.VISIBLE);
                    // StringUtils.getInstance().showSnackbar(mActivity, R.string.snackbar_images_added);
                    mCreatePdf.setEnabled(true);
                    mCreatePdf.setVisibility(View.VISIBLE);
                    recyclerImges();

                } else {
                    mNoOfImages.setVisibility(View.GONE);
                }

                mMorphButtonUtility.morphToSquare(mCreatePdf, mMorphButtonUtility.integer());
                break;

            case INTENT_REQUEST_PICK_FILE_CODE:
                setTextAndActivateButtons(FileUriUtils.getInstance().getFilePath(data.getData()));
                break;
        }





    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length < 1)
            return;
        if (requestCode == REQUEST_PERMISSIONS_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPermissionGranted = true;
                selectImages();
                StringUtils.getInstance().showSnackbar(mActivity, R.string.snackbar_permissions_given);
            } else
                StringUtils.getInstance().showSnackbar(mActivity, R.string.snackbar_insufficient_permissions);
        }


    }

    @OnClick(R.id.pdfCreate)
    public void parse() {
        StringUtils.getInstance().hideKeyboard(mActivity);
        if (mOperation.equals(ADD_IMAGES))
            getFileName();
    }

    private void getFileName() {
        MaterialDialog.Builder builder = DialogUtils.getInstance().createCustomDialog(mActivity,
                R.string.creating_pdf, R.string.enter_file_name);
        builder.input(getString(R.string.example), null, (dialog, input) -> {
            if (StringUtils.getInstance().isEmpty(input)) {
                StringUtils.getInstance().showSnackbar(mActivity, R.string.snackbar_name_not_blank);
            } else {
                final String filename = input.toString();
                FileUtils utils = new FileUtils(mActivity);
                if (!utils.isFileExist(filename + getString(R.string.pdf_ext))) {
                    this.addImagesToPdf(filename);
                } else {
                    MaterialDialog.Builder builder2 = DialogUtils.getInstance().createOverwriteDialog(mActivity);
                    builder2.onPositive((dialog2, which) ->
                            this.addImagesToPdf(filename)).onNegative((dialog1, which) -> getFileName()).show();
                }
            }
        }).show();
    }

    /**
     * Adds images to existing PDF
     *
     * @param output - path of output PDF
     */
    private void addImagesToPdf(String output) {
        int index = mPath.lastIndexOf("/");
        String outputPath = mPath.replace(mPath.substring(index + 1),
                output + mActivity.getString(R.string.pdf_ext));

        if (mImagesUri.size() > 0) {
            MaterialDialog progressDialog = DialogUtils.getInstance().createAnimationDialog(mActivity);
            progressDialog.show();
            mPDFUtils.addImagesToPdf(mPath, outputPath, mImagesUri);
            mMorphButtonUtility.morphToSuccess(mCreatePdf);
            resetValues();
            progressDialog.dismiss();


        } else {
            StringUtils.getInstance().showSnackbar(mActivity, R.string.no_images_selected);
        }

    }

    private void resetValues() {
        mPath = null;
        mImagesUri.clear();
        mMorphButtonUtility.initializeButton2(selectFileButton, mCreatePdf);
        mMorphButtonUtility.initializeButton2(selectFileButton, addImages);
        mNoOfImages.setVisibility(View.GONE);
    }

    /**
     * Adding Images to PDF
     */
    @OnClick(R.id.addImages)
    void startAddingImages() {
        if (mPermissionGranted)
            selectImages();
        else {
            PermissionsUtils.getInstance().requestRuntimePermissions(this,
                    READ_WRITE_CAMERA_PERMISSIONS,
                    REQUEST_PERMISSIONS_CODE);
        }
    }

    /**
     * Opens Matisse activity to select Images
     */
    private void selectImages() {
        Matisse.from(this)
                .choose(MimeType.ofImage(), false)
                .countable(true)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, AUTHORITY_APP))
                .maxSelectable(1000)
                .imageEngine(new PicassoEngine())
                .forResult(INTENT_REQUEST_GET_IMAGES);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        mMorphButtonUtility = new MorphButtonUtility(mActivity);
        mFileUtils = new FileUtils(mActivity);
        mPDFUtils = new PDFUtils(mActivity);
        mBottomSheetUtils = new BottomSheetUtils(mActivity);
    }

    @Override
    public void onItemClick(String path) {

        mBottomSheetUtils.showHideSheet(mSheetBehavior);
        mSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        setTextAndActivateButtons(path);

//        if (addImages.getVisibility() == View.GONE)
//        {
//            addImages.setVisibility(View.VISIBLE);
//        }

        selectImageLL.setVisibility(View.VISIBLE);

//        PopUp.setVisibility(View.GONE);
//        rltvall.setVisibility(View.VISIBLE);
    }

    private void setTextAndActivateButtons(String path) {
        mPath = path;
        mMorphButtonUtility.setTextAndActivateButtons2(path,
                selectFileButton, addImages);

    }

    @Override
    public void onPopulate(ArrayList<String> paths) {
        CommonCodeUtils.getInstance().populateUtil(mActivity, paths,
                this, mLayout, mLottieProgress, mRecyclerViewFiles);
    }

    @Override
    public void closeBottomSheet() {
        CommonCodeUtils.getInstance().closeBottomSheetUtil(mSheetBehavior);
    }

    @Override
    public boolean checkSheetBehaviour() {
        return CommonCodeUtils.getInstance().checkSheetBehaviourUtil(mSheetBehavior);
    }


    private void recyclerImges(){
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);

        RecyclerViewAdapter myAdapter=new RecyclerViewAdapter(getContext(),mImagesUri);
        recyclerViewinaddimage.setLayoutManager(layoutManager);
        recyclerViewinaddimage.setAdapter(myAdapter);

        if (mCreatePdf.getVisibility() == View.GONE)
        {
            mCreatePdf.setVisibility(View.VISIBLE);
        }

        //abc
    }


    @Override
    public boolean onBackPressed() {

        //IOnBackPressed,

        if (mPath != null) {
            //action not popBackStack
            // Toast.makeText(mActivity, "backpress clicked", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("If You Discard Now, You'll Lose Changes You've Made to It." )
                    .setTitle("Discard PDF?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
//                            Intent intent=new Intent(getContext(), MainActivity.class);
//                            startActivity(intent);

                            HomeFragment myfragment;
                            myfragment = new HomeFragment();
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.content, myfragment);
                            //fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        } else {
            // Toast.makeText(mActivity, "clicked", Toast.LENGTH_SHORT).show();
            mPath = null;
            return false;
        }
    }


}
