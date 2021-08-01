package com.editmypdffree.rdtl.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dd.morphingbutton.MorphingButton;
import com.editmypdffree.rdtl.adapter.RecyclerViewAdapter;
import com.editmypdffree.rdtl.adapter.SettersAndGetters;
import com.editmypdffree.rdtl.interfaces.IOnBackPressed;
import com.github.danielnilsson9.colorpickerview.view.ColorPickerView;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.theartofdev.edmodo.cropper.CropImage;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.editmypdffree.rdtl.R;
import com.editmypdffree.rdtl.activity.CropImageActivity;
import com.editmypdffree.rdtl.activity.ImageEditor;
import com.editmypdffree.rdtl.activity.PreviewActivity;
import com.editmypdffree.rdtl.activity.RearrangeImages;
import com.editmypdffree.rdtl.adapter.EnhancementOptionsAdapter;
import com.editmypdffree.rdtl.database.DatabaseHelper;
import com.editmypdffree.rdtl.interfaces.OnItemClickListener;
import com.editmypdffree.rdtl.interfaces.OnPDFCreatedInterface;
import com.editmypdffree.rdtl.model.EnhancementOptionsEntity;
import com.editmypdffree.rdtl.model.ImageToPDFOptions;
import com.editmypdffree.rdtl.model.Watermark;
import com.editmypdffree.rdtl.util.Constants;
import com.editmypdffree.rdtl.util.CreatePdf;
import com.editmypdffree.rdtl.util.DialogUtils;
import com.editmypdffree.rdtl.util.FileUtils;
import com.editmypdffree.rdtl.util.ImageEnhancementOptionsUtils;
import com.editmypdffree.rdtl.util.ImageUtils;
import com.editmypdffree.rdtl.util.MorphButtonUtility;
import com.editmypdffree.rdtl.util.PageSizeUtils;
import com.editmypdffree.rdtl.util.PermissionsUtils;
import com.editmypdffree.rdtl.util.StringUtils;

import static com.editmypdffree.rdtl.util.Constants.AUTHORITY_APP;
import static com.editmypdffree.rdtl.util.Constants.DEFAULT_BORDER_WIDTH;
import static com.editmypdffree.rdtl.util.Constants.DEFAULT_COMPRESSION;
import static com.editmypdffree.rdtl.util.Constants.DEFAULT_IMAGE_BORDER_TEXT;
import static com.editmypdffree.rdtl.util.Constants.DEFAULT_IMAGE_SCALE_TYPE_TEXT;
import static com.editmypdffree.rdtl.util.Constants.DEFAULT_PAGE_COLOR;
import static com.editmypdffree.rdtl.util.Constants.DEFAULT_PAGE_SIZE;
import static com.editmypdffree.rdtl.util.Constants.DEFAULT_PAGE_SIZE_TEXT;
import static com.editmypdffree.rdtl.util.Constants.DEFAULT_QUALITY_VALUE;
import static com.editmypdffree.rdtl.util.Constants.IMAGE_SCALE_TYPE_ASPECT_RATIO;
import static com.editmypdffree.rdtl.util.Constants.MASTER_PWD_STRING;
import static com.editmypdffree.rdtl.util.Constants.OPEN_SELECT_IMAGES;
import static com.editmypdffree.rdtl.util.Constants.READ_WRITE_CAMERA_PERMISSIONS;
import static com.editmypdffree.rdtl.util.Constants.RESULT;
import static com.editmypdffree.rdtl.util.Constants.STORAGE_LOCATION;
import static com.editmypdffree.rdtl.util.Constants.appName;
import static com.editmypdffree.rdtl.util.WatermarkUtils.getStyleNameFromFont;
import static com.editmypdffree.rdtl.util.WatermarkUtils.getStyleValueFromName;

/**
 * ImageToPdfFragment fragment to start with creating PDF
 */
public class ImageToPdfFragment extends Fragment implements OnItemClickListener, IOnBackPressed,
        OnPDFCreatedInterface {

    private static final int INTENT_REQUEST_APPLY_FILTER = 10;
    private static final int INTENT_REQUEST_PREVIEW_IMAGE = 11;
    private static final int INTENT_REQUEST_REARRANGE_IMAGE = 12;
    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    private static final int INTENT_REQUEST_GET_IMAGES2 = 132;
    private static final int REQUEST_PERMISSIONS_CODE = 124;

    RecyclerView myr;
    RecyclerViewAdapter myAdapter;

    //List<SettersAndGetters> lstPress;

    @BindView(R.id.pdfCreate)
    MorphingButton mCreatePdf;
    @BindView(R.id.pdfOpen)
    MorphingButton mOpenPdf;
    @BindView(R.id.enhancement_options_recycle_view)
    RecyclerView mEnhancementOptionsRecycleView;
    @BindView(R.id.tvNoOfImages)
    TextView mNoOfImages;
    //@BindView(R.id.imageadd)
    LinearLayout imageAdd;
    @BindView(R.id.addImages)
    MorphingButton addImages;
    @BindView(R.id.addImages2)
    MorphingButton addImages2;
    @BindView(R.id.AddImageBtn)
    ImageView AddImageBtn;
    @BindView(R.id.relativebtmcreate)
    RelativeLayout rlBtmCreate;
    @BindView(R.id.popup)
    LinearLayout PopUp;
    @BindView(R.id.rltvall)
    RelativeLayout rltvall;
    @BindView(R.id.adition)
    TextView adition;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.popup2)
    LinearLayout openpdf;
    @BindView(R.id.idbtnCard1st)
    CardView btnC1;
    @BindView(R.id.idbtnCard2nd)
    CardView btnC2;

//    @BindView(R.id.deleteImage)
//    ImageView DeleteImage;


    private MorphButtonUtility mMorphButtonUtility;
    private Activity mActivity;
    public static ArrayList<String> mImagesUri = new ArrayList<>();
    private static final ArrayList<String> mUnarrangedImagesUri = new ArrayList<>();
    private String mPath;
    private SharedPreferences mSharedPreferences;
    private FileUtils mFileUtils;
    private PageSizeUtils mPageSizeUtils;
    private int mPageColor;
    private boolean mIsButtonAlreadyClicked = false;
    private ImageToPDFOptions mPdfOptions;
    private MaterialDialog mMaterialDialog;
    private String mHomePath;
    private int mMarginTop = 50;
    private int mMarginBottom = 38;
    private int mMarginLeft = 50;
    private int mMarginRight = 38;
    private String mPageNumStyle;
    private int mChoseId;
    private boolean mPermissionGranted = false;
    int i = 0;

    ImageView btnAddImage;
    //ArrayList<String> cars;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_images_to_pdf, container, false);
        ButterKnife.bind(this, root);




        myr=(RecyclerView)root.findViewById(R.id.myrecycler_view);
        btnAddImage = root.findViewById(R.id.AddImageBtn);
        btnC2 = root.findViewById(R.id.idbtnCard2nd);
        //cars = new ArrayList<String>();

        mCreatePdf.setVisibility(View.GONE);
        rlBtmCreate.setVisibility(View.GONE);


        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImages2();
            }
        });


//        if (i == 1) {
//            addImages2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    selectImages2();
//                }
//            });
//        }

        addImages2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImages2();
            }
        });


        //Horizontal RecyclerView
        //  myr.setLayoutManager(new LinearLayoutManager(mActivity.getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
//        LinearLayoutManager layoutManager
//                = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
//        myr.setLayoutManager(layoutManager);

        // Initialize variables
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
        mPermissionGranted = PermissionsUtils.getInstance().checkRuntimePermissions(this,
                READ_WRITE_CAMERA_PERMISSIONS);
        mMorphButtonUtility = new MorphButtonUtility(mActivity);
        mFileUtils = new FileUtils(mActivity);
        mPageSizeUtils = new PageSizeUtils(mActivity);
        mPageColor = mSharedPreferences.getInt(Constants.DEFAULT_PAGE_COLOR_ITP,
                DEFAULT_PAGE_COLOR);
        mHomePath = mSharedPreferences.getString(STORAGE_LOCATION,
                StringUtils.getInstance().getDefaultStorageLocation());

        // Get default values & show enhancement options
        resetValues();

        // Check for the images received
        checkForImagesInBundle();

        if (mImagesUri.size() > 0) {
            mNoOfImages.setText(String.format(mActivity.getResources()
                    .getString(R.string.images_selected), mImagesUri.size()));
            mNoOfImages.setVisibility(View.VISIBLE);
            mMorphButtonUtility.morphToSquare(mCreatePdf, mMorphButtonUtility.integer());
            mCreatePdf.setEnabled(true);
            StringUtils.getInstance().showSnackbar(mActivity, R.string.successToast);
        } else {
            mNoOfImages.setVisibility(View.GONE);
            mMorphButtonUtility.morphToGrey(mCreatePdf, mMorphButtonUtility.integer());
        }

        return root;
    }

    /**
     * Adds images (if any) received in the bundle
     */
    private void checkForImagesInBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.getBoolean(OPEN_SELECT_IMAGES))
                startAddingImages();
            // startAddingImages1();
            ArrayList<Parcelable> uris = bundle.getParcelableArrayList(getString(R.string.bundleKey));
            if (uris == null)
                return;
            for (Parcelable p : uris) {
                Uri uri = (Uri) p;
                if (mFileUtils.getUriRealPath(uri) == null) {
                    StringUtils.getInstance().showSnackbar(mActivity, R.string.whatsappToast);
                } else {
                    mImagesUri.add(mFileUtils.getUriRealPath(uri));

                }
            }
        }
    }

    /**
     * Shows enhancement options
     */
    private void showEnhancementOptions() {
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(mActivity, 2);
        mEnhancementOptionsRecycleView.setLayoutManager(mGridLayoutManager);
        ImageEnhancementOptionsUtils imageEnhancementOptionsUtilsInstance = ImageEnhancementOptionsUtils.getInstance();
        ArrayList<EnhancementOptionsEntity> list = imageEnhancementOptionsUtilsInstance.getEnhancementOptions(mActivity,
                mPdfOptions);
        EnhancementOptionsAdapter adapter =
                new EnhancementOptionsAdapter(this, list);
        mEnhancementOptionsRecycleView.setAdapter(adapter);
    }

    /**
     * Adding Images to PDF
     */
    @OnClick(R.id.addImages)
    void startAddingImages() {



        if (!mPermissionGranted) {
            getRuntimePermissions();
            return;
        }
        Log.e("tf",""+mIsButtonAlreadyClicked);
        if (!mIsButtonAlreadyClicked) {
            selectImages();
            mIsButtonAlreadyClicked = true;
        }
    }

//    @OnClick(R.id.Close)
//    void startAddingImages1() {
//        if (addImages.getVisibility() == View.GONE)
//        {
//            addImages.setVisibility(View.VISIBLE);
//        }
//
//        if (imageAdd.getVisibility() == View.VISIBLE)
//        {
//            imageAdd.setVisibility(View.GONE);
//        }
//
//        if (!mPermissionGranted) {
//            getRuntimePermissions();
//            return;
//        }
//        if (!mIsButtonAlreadyClicked) {
//            selectImages();
//            mIsButtonAlreadyClicked = true;
//        }
//    }

    /**
     * Create Pdf of selected images
     */
    @OnClick(R.id.pdfCreate)
    void pdfCreateClicked() {
        if (!mImagesUri.isEmpty())
            createPdf(false);
    }

    private void createPdf(boolean isGrayScale) {



        mPdfOptions.setImagesUri(mImagesUri);
        mPdfOptions.setPageSize(PageSizeUtils.mPageSize);
        mPdfOptions.setImageScaleType(ImageUtils.getInstance().mImageScaleType);
        mPdfOptions.setPageNumStyle(mPageNumStyle);
        mPdfOptions.setMasterPwd(mSharedPreferences.getString(MASTER_PWD_STRING, appName));
        mPdfOptions.setPageColor(mPageColor);
        ////////////////////////////////////////////
        Log.e("cpdf", ""+mImagesUri);
        Log.e("cpdf", ""+PageSizeUtils.mPageSize);
        Log.e("cpdf", ""+ImageUtils.getInstance().mImageScaleType);
        Log.e("cpdf", ""+mPageNumStyle);
        Log.e("cpdf", ""+mSharedPreferences.getString(MASTER_PWD_STRING, appName));
        Log.e("cpdf", ""+mPageColor);

        String preFillName = mFileUtils.getLastFileName(mImagesUri);

        Log.e("cpdf", ""+preFillName);

        MaterialDialog.Builder builder = DialogUtils.getInstance().createCustomDialog(mActivity,
                R.string.creating_pdf, R.string.enter_file_name);
        builder.input(getString(R.string.example), preFillName, (dialog, input) -> {
            if (StringUtils.getInstance().isEmpty(input)) {
                StringUtils.getInstance().showSnackbar(mActivity, R.string.snackbar_name_not_blank);
            } else {
                final String filename = input.toString();

                Log.e("cpdf", ""+filename);

                FileUtils utils = new FileUtils(mActivity);
                if (!utils.isFileExist(filename + getString(R.string.pdf_ext))) {

                    Log.e("cpdf", ""+utils.isFileExist(filename + getString(R.string.pdf_ext)));

                    mPdfOptions.setOutFileName(filename);
                    if (isGrayScale)
                        saveImagesInGrayScale();

                    Log.e("cpdf2", ""+mPdfOptions);
                    Log.e("cpdf2", ""+mHomePath);


                    new CreatePdf(mPdfOptions, mHomePath,
                            ImageToPdfFragment.this).execute();
                    onCreatePdfClick();

                } else {
                    MaterialDialog.Builder builder2 = DialogUtils.getInstance().createOverwriteDialog(mActivity);
                    builder2.onPositive((dialog2, which) -> {
                        mPdfOptions.setOutFileName(filename);
                        if (isGrayScale)
                            saveImagesInGrayScale();
                        new CreatePdf(mPdfOptions, mHomePath, ImageToPdfFragment.this).execute();
                        onCreatePdfClick();
                    }).onNegative((dialog1, which) -> createPdf(isGrayScale)).show();
                }
            }
        }).show();
    }

    @OnClick(R.id.pdfOpen)
    void openPdf() {
        mFileUtils.openFile(mPath, FileUtils.FileType.e_PDF);
    }


    /**
     * Called after user is asked to grant permissions
     *
     * @param requestCode  REQUEST Code for opening permissions
     * @param permissions  permissions asked to user
     * @param grantResults bool array indicating if permission is granted
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

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



        Log.e("tf",""+mIsButtonAlreadyClicked);
        mIsButtonAlreadyClicked = false;
        Log.e("tf",""+mIsButtonAlreadyClicked);
        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        switch (requestCode) {
            case INTENT_REQUEST_GET_IMAGES:
                mImagesUri.clear();
                mUnarrangedImagesUri.clear();
                mImagesUri.addAll(Matisse.obtainPathResult(data));
                mUnarrangedImagesUri.addAll(mImagesUri);
//                if (openpdf.getVisibility() == View.GONE)
//                {
//                    openpdf.setVisibility(View.VISIBLE);
//                }


                ////farhad

                onSelectImage();


                /////sobur
//                if (PopUp.getVisibility() == View.VISIBLE)
//                {
//                    PopUp.setVisibility(View.GONE);
//                }
//
//
//                if (rltvall.getVisibility() == View.GONE)
//                {
//                    rltvall.setVisibility(View.VISIBLE);
//                }
//
//                if (AddImageBtn.getVisibility() == View.GONE)
//                {
//                    AddImageBtn.setVisibility(View.VISIBLE);
//                }

                if (mImagesUri.size() > 0) {
                    mNoOfImages.setText(String.format(mActivity.getResources()
                            .getString(R.string.images_selected), mImagesUri.size()));
                    mNoOfImages.setVisibility(View.VISIBLE);
                    //  StringUtils.getInstance().showSnackbar(mActivity, "R.string.snackbar_images_added");

                    mCreatePdf.setVisibility(View.VISIBLE);
                    rlBtmCreate.setVisibility(View.VISIBLE);
                    mCreatePdf.setEnabled(true);
                    mCreatePdf.unblockTouch();



                    //lstPress=new ArrayList<>();



                    recyclerImges();



                }
                mMorphButtonUtility.morphToSquare(mCreatePdf, mMorphButtonUtility.integer());
              //  mOpenPdf.setVisibility(View.GONE);
                //addImages.unblockTouch();
                break;

            case INTENT_REQUEST_GET_IMAGES2:
//                mImagesUri.clear();
//                mUnarrangedImagesUri.clear();
                mImagesUri.addAll(Matisse.obtainPathResult(data));
                mUnarrangedImagesUri.addAll(mImagesUri);


//                if (PopUp.getVisibility() == View.GONE)
//                {
//                    PopUp.setVisibility(View.VISIBLE);
//                }
//                if (PopUp.getVisibility() == View.VISIBLE)
//                {
//                    PopUp.setVisibility(View.GONE);
//                }

                if (rltvall.getVisibility() == View.GONE)
                {
                    rltvall.setVisibility(View.VISIBLE);
                }

                if (mImagesUri.size() > 0) {
                    mNoOfImages.setText(String.format(mActivity.getResources()
                            .getString(R.string.images_selected), mImagesUri.size()));
                    mNoOfImages.setVisibility(View.VISIBLE);
                    //  StringUtils.getInstance().showSnackbar(mActivity, "R.string.snackbar_images_added");

                    mCreatePdf.setEnabled(true);
                    mCreatePdf.unblockTouch();



                    recyclerImges();



                }
                mMorphButtonUtility.morphToSquare(mCreatePdf, mMorphButtonUtility.integer());
               // mOpenPdf.setVisibility(View.GONE);
                break;

            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                HashMap<Integer, Uri> croppedImageUris =
                        (HashMap) data.getSerializableExtra(CropImage.CROP_IMAGE_EXTRA_RESULT);

                for (int i = 0; i < mImagesUri.size(); i++) {
                    if (croppedImageUris.get(i) != null) {
                        mImagesUri.set(i, croppedImageUris.get(i).getPath());
                        StringUtils.getInstance().showSnackbar(mActivity, R.string.snackbar_imagecropped);
                    }
                }
                recyclerImges();
                break;

            case INTENT_REQUEST_APPLY_FILTER:
                mImagesUri.clear();
                ArrayList<String> mFilterUris = data.getStringArrayListExtra(RESULT);
                int size = mFilterUris.size() - 1;
                for (int k = 0; k <= size; k++)
                    mImagesUri.add(mFilterUris.get(k));
                recyclerImges();
                break;

            case INTENT_REQUEST_PREVIEW_IMAGE:
                mImagesUri = data.getStringArrayListExtra(RESULT);
                if (mImagesUri.size() > 0) {
                    mNoOfImages.setText(String.format(mActivity.getResources()
                            .getString(R.string.images_selected), mImagesUri.size()));
                } else {
                    mNoOfImages.setVisibility(View.GONE);
                    mMorphButtonUtility.morphToGrey(mCreatePdf, mMorphButtonUtility.integer());
                    mCreatePdf.setEnabled(false);
                }
                recyclerImges();

                break;

            case INTENT_REQUEST_REARRANGE_IMAGE:
                mImagesUri = data.getStringArrayListExtra(RESULT);
                if (!mUnarrangedImagesUri.equals(mImagesUri) && mImagesUri.size() > 0) {
                    mNoOfImages.setText(String.format(mActivity.getResources()
                            .getString(R.string.images_selected), mImagesUri.size()));
                    StringUtils.getInstance().showSnackbar(mActivity, R.string.images_rearranged);
                    mUnarrangedImagesUri.clear();
                    mUnarrangedImagesUri.addAll(mImagesUri);
                }
                if (mImagesUri.size() == 0) {
                    mNoOfImages.setVisibility(View.GONE);
                    mMorphButtonUtility.morphToGrey(mCreatePdf, mMorphButtonUtility.integer());
                    mCreatePdf.setEnabled(false);
                }
                recyclerImges();
                break;


        }
    }


    @Override
    public void onItemClick(int position) {

        if (mImagesUri.size() == 0) {
            StringUtils.getInstance().showSnackbar(mActivity, R.string.snackbar_no_images);
            return;
        }
        switch (position) {
            case 0:
                passwordProtectPDF();
                break;
            case 1:
                cropImage();
                break;
            case 2:
                compressImage();
                break;
            case 3:
                startActivityForResult(ImageEditor.getStartIntent(mActivity, mImagesUri),
                        INTENT_REQUEST_APPLY_FILTER);
                break;
            case 4:
                mPageSizeUtils.showPageSizeDialog(false);
                break;
            case 5:
                ImageUtils.getInstance().showImageScaleTypeDialog(mActivity, false);
                break;
            case 6:
                startActivityForResult(PreviewActivity.getStartIntent(mActivity, mImagesUri),
                        INTENT_REQUEST_PREVIEW_IMAGE);
                break;
            case 7:
                addBorder();
                break;
            case 8:
                startActivityForResult(RearrangeImages.getStartIntent(mActivity, mImagesUri),
                        INTENT_REQUEST_REARRANGE_IMAGE);
                break;
            case 9:
                createPdf(true);
                break;
            case 10:
                addMargins();
                break;
            case 11:
                addPageNumbers();
                break;
            case 12:
                addWatermark();
                break;
            case 13:
                setPageColor();
                break;
        }
    }


    /**
     * Saves Images with gray scale filter
     */
    private void saveImagesInGrayScale() {
        ArrayList<String> tempImageUri = new ArrayList<>();
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + "/PDFfilter");
            dir.mkdirs();

            int size = mImagesUri.size();
            for (int i = 0; i < size; i++) {
                @SuppressLint("StringFormatMatches") String fileName = String.format(getString(R.string.filter_file_name),
                        System.currentTimeMillis(), i + "_grayscale");
                File outFile = new File(dir, fileName);
                String imagePath = outFile.getAbsolutePath();

                File f = new File(mImagesUri.get(i));
                FileInputStream fis = new FileInputStream(f);
                Bitmap bitmap = BitmapFactory.decodeStream(fis);
                Bitmap grayScaleBitmap = ImageUtils.getInstance().toGrayscale(bitmap);

                File file = new File(imagePath);
                file.createNewFile();
                FileOutputStream ostream = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(ostream, 1024 * 8);
                grayScaleBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.close();
                ostream.close();
                tempImageUri.add(imagePath);
            }
            mImagesUri.clear();
            mImagesUri.addAll(tempImageUri);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    private void addBorder() {
        DialogUtils.getInstance().createCustomDialogWithoutContent(mActivity)
                .customView(R.layout.dialog_border_image, true)
                .onPositive((dialog1, which) -> {
                    View view = dialog1.getCustomView();
                    final EditText input = view.findViewById(R.id.border_width);
                    int value = 0;
                    try {
                        value = Integer.parseInt(String.valueOf(input.getText()));
                        if (value > 200 || value < 0) {
                            StringUtils.getInstance().showSnackbar(mActivity, R.string.invalid_entry);
                        } else {
                            mPdfOptions.setBorderWidth(value);
                            showEnhancementOptions();
                        }
                    } catch (NumberFormatException e) {
                        StringUtils.getInstance().showSnackbar(mActivity, R.string.invalid_entry);
                    }
                    final CheckBox cbSetDefault = view.findViewById(R.id.cbSetDefault);
                    if (cbSetDefault.isChecked()) {
                        SharedPreferences.Editor editor = mSharedPreferences.edit();
                        editor.putInt(Constants.DEFAULT_IMAGE_BORDER_TEXT, value);
                        editor.apply();
                    }
                }).build().show();
    }

    private void compressImage() {
        DialogUtils.getInstance().createCustomDialogWithoutContent(mActivity)
                .customView(R.layout.compress_image_dialog, true)
                .onPositive((dialog1, which) -> {
                    final EditText qualityInput = dialog1.getCustomView().findViewById(R.id.quality);
                    final CheckBox cbSetDefault = dialog1.getCustomView().findViewById(R.id.cbSetDefault);
                    int check;
                    try {
                        check = Integer.parseInt(String.valueOf(qualityInput.getText()));
                        if (check > 100 || check < 0) {
                            StringUtils.getInstance().showSnackbar(mActivity, R.string.invalid_entry);
                        } else {
                            mPdfOptions.setQualityString(String.valueOf(check));
                            if (cbSetDefault.isChecked()) {
                                SharedPreferences.Editor editor = mSharedPreferences.edit();
                                editor.putInt(DEFAULT_COMPRESSION, check);
                                editor.apply();
                            }
                            showEnhancementOptions();
                        }
                    } catch (NumberFormatException e) {
                        StringUtils.getInstance().showSnackbar(mActivity, R.string.invalid_entry);
                    }
                }).show();
    }

    private void passwordProtectPDF() {
        final MaterialDialog dialog = new MaterialDialog.Builder(mActivity)
                .customView(R.layout.custom_dialog, true)

                .backgroundColor(getResources().getColor(R.color.alrtbtmclr))
                .positiveColor(getResources().getColor(R.color.white_color))
                .negativeColor(getResources().getColor(R.color.white_color))
                .neutralColor(getResources().getColor(R.color.ntrclr))
                .titleColor(getResources().getColor(R.color.white_color))
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .neutralText(R.string.remove_dialog)
                .build();

        final View positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        final View neutralAction = dialog.getActionButton(DialogAction.NEUTRAL);
        final EditText passwordInput = dialog.getCustomView().findViewById(R.id.password);
        passwordInput.setText(mPdfOptions.getPassword());
        passwordInput.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        positiveAction.setEnabled(s.toString().trim().length() > 0);
                    }

                    @Override
                    public void afterTextChanged(Editable input) {
                    }
                });

        positiveAction.setOnClickListener(v -> {
            if (StringUtils.getInstance().isEmpty(passwordInput.getText())) {
                StringUtils.getInstance().showSnackbar(mActivity, R.string.snackbar_password_cannot_be_blank);
            } else {
                mPdfOptions.setPassword(passwordInput.getText().toString());
                mPdfOptions.setPasswordProtected(true);
                showEnhancementOptions();
                dialog.dismiss();
            }
        });

        if (StringUtils.getInstance().isNotEmpty(mPdfOptions.getPassword())) {
            neutralAction.setOnClickListener(v -> {
                mPdfOptions.setPassword(null);
                mPdfOptions.setPasswordProtected(false);
                showEnhancementOptions();
                dialog.dismiss();
                StringUtils.getInstance().showSnackbar(mActivity, R.string.password_remove);
            });
        }
        dialog.show();
        positiveAction.setEnabled(false);
    }

    private void addWatermark() {
        final MaterialDialog dialog = new MaterialDialog.Builder(mActivity)
                .title(R.string.add_watermark)
                .customView(R.layout.add_watermark_dialog, true)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .neutralText(R.string.remove_dialog)
                .build();

        final View positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        final View neutralAction = dialog.getActionButton(DialogAction.NEUTRAL);

        final Watermark watermark = new Watermark();

        final EditText watermarkTextInput = dialog.getCustomView().findViewById(R.id.watermarkText);
        final EditText angleInput = dialog.getCustomView().findViewById(R.id.watermarkAngle);
        final ColorPickerView colorPickerInput = dialog.getCustomView().findViewById(R.id.watermarkColor);
        final EditText fontSizeInput = dialog.getCustomView().findViewById(R.id.watermarkFontSize);
        final Spinner fontFamilyInput = dialog.getCustomView().findViewById(R.id.watermarkFontFamily);
        final Spinner styleInput = dialog.getCustomView().findViewById(R.id.watermarkStyle);

        ArrayAdapter fontFamilyAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_dropdown_item,
                Font.FontFamily.values());
        fontFamilyInput.setAdapter(fontFamilyAdapter);

        ArrayAdapter styleAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_dropdown_item,
                mActivity.getResources().getStringArray(R.array.fontStyles));
        styleInput.setAdapter(styleAdapter);


        if (mPdfOptions.isWatermarkAdded()) {
            watermarkTextInput.setText(mPdfOptions.getWatermark().getWatermarkText());
            angleInput.setText(String.valueOf(mPdfOptions.getWatermark().getRotationAngle()));
            fontSizeInput.setText(String.valueOf(mPdfOptions.getWatermark().getTextSize()));
            BaseColor color = this.mPdfOptions.getWatermark().getTextColor();
            colorPickerInput.setColor(Color.argb(
                    color.getAlpha(),
                    color.getRed(),
                    color.getGreen(),
                    color.getBlue()
            ));
            fontFamilyInput.setSelection(fontFamilyAdapter.getPosition(mPdfOptions.getWatermark().getFontFamily()));
            styleInput.setSelection(styleAdapter.getPosition(
                    getStyleNameFromFont(mPdfOptions.getWatermark().getFontStyle())));
        } else {
            angleInput.setText("0");
            fontSizeInput.setText("50");
        }
        watermarkTextInput.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        positiveAction.setEnabled(s.toString().trim().length() > 0);
                    }

                    @Override
                    public void afterTextChanged(Editable input) {
                        if (StringUtils.getInstance().isEmpty(input)) {
                            StringUtils.getInstance().
                                    showSnackbar(mActivity, R.string.snackbar_watermark_cannot_be_blank);
                        } else {
                            watermark.setWatermarkText(input.toString());
                            showEnhancementOptions();
                        }
                    }
                });

        neutralAction.setEnabled(this.mPdfOptions.isWatermarkAdded());
        positiveAction.setEnabled(this.mPdfOptions.isWatermarkAdded());

        neutralAction.setOnClickListener(v -> {
            mPdfOptions.setWatermarkAdded(false);
            showEnhancementOptions();
            dialog.dismiss();
            StringUtils.getInstance().showSnackbar(mActivity, R.string.watermark_remove);
        });

        positiveAction.setOnClickListener(v -> {
            watermark.setWatermarkText(watermarkTextInput.getText().toString());
            watermark.setFontFamily(((Font.FontFamily) fontFamilyInput.getSelectedItem()));
            watermark.setFontStyle(getStyleValueFromName(((String) styleInput.getSelectedItem())));

            if (StringUtils.getInstance().isEmpty(angleInput.getText())) {
                watermark.setRotationAngle(0);
            } else {
                watermark.setRotationAngle(Integer.parseInt(angleInput.getText().toString()));
            }

            if (StringUtils.getInstance().isEmpty(fontSizeInput.getText())) {
                watermark.setTextSize(50);
            } else {
                watermark.setTextSize(Integer.parseInt(fontSizeInput.getText().toString()));
            }

            watermark.setTextColor((new BaseColor(
                    Color.red(colorPickerInput.getColor()),
                    Color.green(colorPickerInput.getColor()),
                    Color.blue(colorPickerInput.getColor()),
                    Color.alpha(colorPickerInput.getColor())
            )));
            mPdfOptions.setWatermark(watermark);
            mPdfOptions.setWatermarkAdded(true);
            showEnhancementOptions();
            dialog.dismiss();
            StringUtils.getInstance().showSnackbar(mActivity, R.string.watermark_added);
        });

        dialog.show();
    }

    private void setPageColor() {
        MaterialDialog materialDialog = new MaterialDialog.Builder(mActivity)
                .title(R.string.page_color)
                .customView(R.layout.dialog_color_chooser, true)
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .onPositive((dialog, which) -> {
                    View view = dialog.getCustomView();
                    ColorPickerView colorPickerView = view.findViewById(R.id.color_picker);
                    CheckBox defaultCheckbox = view.findViewById(R.id.set_default);
                    mPageColor = colorPickerView.getColor();
                    if (defaultCheckbox.isChecked()) {
                        SharedPreferences.Editor editor = mSharedPreferences.edit();
                        editor.putInt(Constants.DEFAULT_PAGE_COLOR_ITP, mPageColor);
                        editor.apply();
                    }
                })
                .build();
        ColorPickerView colorPickerView = materialDialog.getCustomView().findViewById(R.id.color_picker);
        colorPickerView.setColor(mPageColor);
        materialDialog.show();
    }

    @Override
    public void onPDFCreationStarted() {
        mMaterialDialog = DialogUtils.getInstance().createAnimationDialog(mActivity);
        mMaterialDialog.show();
    }

    @Override
    public void onPDFCreated(boolean success, String path) {
        if (mMaterialDialog != null && mMaterialDialog.isShowing())
            mMaterialDialog.dismiss();

        if (!success) {
            StringUtils.getInstance().showSnackbar(mActivity, R.string.snackbar_folder_not_created);
            return;
        }
        new DatabaseHelper(mActivity).insertRecord(path, mActivity.getString(R.string.created));
//        StringUtils.getInstance().getSnackbarwithAction(mActivity, R.string.snackbar_pdfCreated)
//                .setAction(R.string.snackbar_viewAction,
//                        v -> mFileUtils.openFile(mPath, FileUtils.FileType.e_PDF)).show();
        openpdf.setVisibility(View.VISIBLE);
        PopUp.setVisibility(View.GONE);

        mMorphButtonUtility.morphToSuccess(mCreatePdf);
        mCreatePdf.blockTouch();
        mPath = path;
        resetValues();
    }

    private void cropImage() {
        Intent intent = new Intent(mActivity, CropImageActivity.class);
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    private void getRuntimePermissions() {
        PermissionsUtils.getInstance().requestRuntimePermissions(this,
                READ_WRITE_CAMERA_PERMISSIONS,
                REQUEST_PERMISSIONS_CODE);
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

    private void selectImages2() {
        Matisse.from(this)
                .choose(MimeType.ofImage(), false)
                .countable(true)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, AUTHORITY_APP))
                .maxSelectable(1000)
                .imageEngine(new PicassoEngine())
                .forResult(INTENT_REQUEST_GET_IMAGES2);
    }

    /**
     * Resets pdf creation related values & show enhancement options
     */
    private void resetValues() {
        mPdfOptions = new ImageToPDFOptions();
        mPdfOptions.setBorderWidth(mSharedPreferences.getInt(DEFAULT_IMAGE_BORDER_TEXT,
                DEFAULT_BORDER_WIDTH));
        mPdfOptions.setQualityString(
                Integer.toString(mSharedPreferences.getInt(DEFAULT_COMPRESSION,
                        DEFAULT_QUALITY_VALUE)));
        mPdfOptions.setPageSize(mSharedPreferences.getString(DEFAULT_PAGE_SIZE_TEXT,
                DEFAULT_PAGE_SIZE));
        mPdfOptions.setPasswordProtected(false);
        mPdfOptions.setWatermarkAdded(false);
        mImagesUri.clear();
        showEnhancementOptions();
        mNoOfImages.setVisibility(View.GONE);
        ImageUtils.getInstance().mImageScaleType = mSharedPreferences.getString(DEFAULT_IMAGE_SCALE_TYPE_TEXT,
                IMAGE_SCALE_TYPE_ASPECT_RATIO);
        mPdfOptions.setMargins(0, 0, 0, 0);
        mPageNumStyle = mSharedPreferences.getString (Constants.PREF_PAGE_STYLE, null);
        mPageColor = mSharedPreferences.getInt(Constants.DEFAULT_PAGE_COLOR_ITP,
                DEFAULT_PAGE_COLOR);
    }

    private void addMargins() {
        MaterialDialog materialDialog = new MaterialDialog.Builder(mActivity)
                .title(R.string.add_margins)
                .customView(R.layout.add_margins_dialog, false)
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .onPositive(((dialog, which) -> {
                    View view = dialog.getCustomView();
                    EditText top = view.findViewById(R.id.topMarginEditText);
                    EditText bottom = view.findViewById(R.id.bottomMarginEditText);
                    EditText right = view.findViewById(R.id.rightMarginEditText);
                    EditText left = view.findViewById(R.id.leftMarginEditText);
                    if (top.getText().toString().isEmpty())
                        mMarginTop = 0;
                    else
                        mMarginTop = Integer.parseInt(top.getText().toString());
                    if (bottom.getText().toString().isEmpty())
                        mMarginBottom = 0;
                    else
                        mMarginBottom = Integer.parseInt(bottom.getText().toString());
                    if (right.getText().toString().isEmpty())
                        mMarginRight = 0;
                    else
                        mMarginRight = Integer.parseInt(right.getText().toString());
                    if (left.getText().toString().isEmpty())
                        mMarginLeft = 0;
                    else
                        mMarginLeft = Integer.parseInt(left.getText().toString());
                    mPdfOptions.setMargins(mMarginTop, mMarginBottom, mMarginRight, mMarginLeft);
                })).build();
        materialDialog.show();
    }


    private void addPageNumbers() {

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        mPageNumStyle = mSharedPreferences.getString (Constants.PREF_PAGE_STYLE, null);
        mChoseId = mSharedPreferences.getInt (Constants.PREF_PAGE_STYLE_ID, -1);

        RelativeLayout dialogLayout = (RelativeLayout) getLayoutInflater ()
                .inflate (R.layout.add_pgnum_dialog, null);

        RadioButton rbOpt1 = dialogLayout.findViewById(R.id.page_num_opt1);
        RadioButton rbOpt2 = dialogLayout.findViewById(R.id.page_num_opt2);
        RadioButton rbOpt3 = dialogLayout.findViewById(R.id.page_num_opt3);
        RadioGroup rg = dialogLayout.findViewById(R.id.radioGroup);
        CheckBox cbDefault = dialogLayout.findViewById (R.id.set_as_default);

        if (mChoseId > 0) {
            cbDefault.setChecked (true);
            rg.clearCheck ();
            rg.check (mChoseId);
        }

        MaterialDialog materialDialog = new MaterialDialog.Builder(mActivity)
                .title(R.string.choose_page_number_style)
                .customView(dialogLayout, false)
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .neutralText(R.string.remove_dialog)
                .onPositive(((dialog, which) -> {

                    int checkedRadioButtonId = rg.getCheckedRadioButtonId ();
                    mChoseId = checkedRadioButtonId;
                    if (checkedRadioButtonId == rbOpt1.getId ()) {
                        mPageNumStyle = Constants.PG_NUM_STYLE_PAGE_X_OF_N;
                    } else if (checkedRadioButtonId == rbOpt2.getId ()) {
                        mPageNumStyle = Constants.PG_NUM_STYLE_X_OF_N;
                    } else if (checkedRadioButtonId == rbOpt3.getId ()) {
                        mPageNumStyle = Constants.PG_NUM_STYLE_X;
                    }
                    if (cbDefault.isChecked ()) {

                        editor.putString(Constants.PREF_PAGE_STYLE, mPageNumStyle);
                        editor.putInt(Constants.PREF_PAGE_STYLE_ID, mChoseId);
                        editor.apply();
                    } else {

                        editor.putString(Constants.PREF_PAGE_STYLE, null);
                        editor.putInt(Constants.PREF_PAGE_STYLE_ID, -1);
                        editor.apply();
                    }
                }))
                .onNeutral(((dialog, which) -> mPageNumStyle = null))
                .build();
        materialDialog.show();
    }

//    public class CustomAdapter extends BaseAdapter {
//        @Override
//        public int getCount() {
//            return mImagesUri.size();
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            ViewHolder holder;
//            if (view == null) {
//                holder = new ViewHolder();
//                view = getLayoutInflater().inflate(R.layout.imagelist_layout, null);
//                holder.imageview = (ImageView) view.findViewById(R.id.idListIV);
//                view.setTag(holder);
//            }
//            else {
//                holder = (ViewHolder) view.getTag();
//            }
//            try {
//
//                // getting null exception
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.parse(mImagesUri.get(i)));
//                Log.e("jjjj", ""+Uri.parse(mImagesUri.get(i)) );
//                holder.imageview.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//
//            return view;
//        }
//
//        class ViewHolder {
//            private ImageView imageview;
//        }
//
//    }

    private void recyclerImges(){
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);

        myAdapter=new RecyclerViewAdapter(getContext(),mImagesUri);
        myr.setLayoutManager(layoutManager);
        myr.setAdapter(myAdapter);

        hndlr();

    }


    private void hndlr(){

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms

                onArrayZero();



                hndlr();
            }
        }, 300);
    }

    public boolean onBackPressed() {
        if (mImagesUri.size() !=0) {
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
                            fragmentTransaction.addToBackStack(null);
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
            return false;
        }
    }


    private void onSelectImage(){

        PopUp.setVisibility(View.GONE);
        rltvall.setVisibility(View.VISIBLE);
        AddImageBtn.setVisibility(View.VISIBLE);

    }

    private void onArrayZero(){

        if (myAdapter.getItemCount()==0) {
            mCreatePdf.setVisibility(View.GONE);
            rlBtmCreate.setVisibility(View.GONE);
            AddImageBtn.setVisibility(View.GONE);
            PopUp.setVisibility(View.VISIBLE);
            rltvall.setVisibility(View.GONE);
            mEnhancementOptionsRecycleView.setVisibility(View.GONE);
            adition.setVisibility(View.GONE);
            view.setVisibility(View.GONE);


            ////new logic
//                    btnC1.setVisibility(View.GONE);
//                    btnC2.setVisibility(View.VISIBLE);
//                    btnC2.setTranslationZ(10);



//                    ImageToPdfFragment myfragment;
//                    myfragment = new ImageToPdfFragment();
//                    FragmentManager fm = getFragmentManager();
//                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
//                    fragmentTransaction.replace(R.id.content, myfragment);
//                    //  fragmentTransaction.addToBackStack(null);
//                    try {
//                        fragmentTransaction.commit();
//                    }
//                    catch (Exception e){
//
//                    }


//                    HomeFragment myfragment;
//                    myfragment = new HomeFragment();
//                    FragmentManager fm = getFragmentManager();
//                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
//                    fragmentTransaction.replace(R.id.content, myfragment);
//                    //fragmentTransaction.addToBackStack(null);
//                    resetValues();
//                    fragmentTransaction.commit();


            //  openpdf.setVisibility(View.VISIBLE);
            //addImages.blockTouch();
        }
        else {
            mCreatePdf.setVisibility(View.VISIBLE);
            rlBtmCreate.setVisibility(View.VISIBLE);
            AddImageBtn.setVisibility(View.VISIBLE);
            mEnhancementOptionsRecycleView.setVisibility(View.VISIBLE);
        }
    }

    private void onCreatePdfClick(){

        PopUp.setVisibility(View.GONE);
        rltvall.setVisibility(View.GONE);
        openpdf.setVisibility(View.VISIBLE);
       // Toast.makeText(mActivity, "cccccccccp", Toast.LENGTH_SHORT).show();
    }
    private void onReCreate(){

    }
}
