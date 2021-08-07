package com.editmypdffree.rdtl.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.editmypdffree.rdtl.adapter.ViewFilesAdapter;
import com.editmypdffree.rdtl.interfaces.IOnBackPressed;
import com.editmypdffree.rdtl.util.PDFRotationUtils;
import com.editmypdffree.rdtl.util.WatermarkUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.airbnb.lottie.LottieAnimationView;
import com.dd.morphingbutton.MorphingButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.editmypdffree.rdtl.R;
import com.editmypdffree.rdtl.activity.RearrangePdfPages;
import com.editmypdffree.rdtl.adapter.MergeFilesAdapter;
import com.editmypdffree.rdtl.database.DatabaseHelper;
import com.editmypdffree.rdtl.interfaces.BottomSheetPopulate;
import com.editmypdffree.rdtl.interfaces.OnBackPressedInterface;
import com.editmypdffree.rdtl.interfaces.OnPDFCompressedInterface;
import com.editmypdffree.rdtl.interfaces.OnPdfReorderedInterface;
import com.editmypdffree.rdtl.util.BottomSheetCallback;
import com.editmypdffree.rdtl.util.BottomSheetUtils;
import com.editmypdffree.rdtl.util.CommonCodeUtils;
import com.editmypdffree.rdtl.util.DialogUtils;
import com.editmypdffree.rdtl.util.FileUtils;
import com.editmypdffree.rdtl.util.MorphButtonUtility;
import com.editmypdffree.rdtl.util.PDFEncryptionUtility;
import com.editmypdffree.rdtl.util.PDFUtils;
import com.editmypdffree.rdtl.util.RealPathUtil;
import com.editmypdffree.rdtl.util.StringUtils;

import static android.app.Activity.RESULT_OK;
import static com.editmypdffree.rdtl.util.Constants.ADD_PWD;
import static com.editmypdffree.rdtl.util.Constants.ADD_WATERMARK2;
import static com.editmypdffree.rdtl.util.Constants.BUNDLE_DATA;
import static com.editmypdffree.rdtl.util.Constants.COMPRESS_PDF;
import static com.editmypdffree.rdtl.util.Constants.REMOVE_PAGES;
import static com.editmypdffree.rdtl.util.Constants.REMOVE_PWd;
import static com.editmypdffree.rdtl.util.Constants.REORDER_PAGES;
import static com.editmypdffree.rdtl.util.Constants.RESULT;
import static com.editmypdffree.rdtl.util.Constants.ROTATE_PAGE;
import static com.editmypdffree.rdtl.util.FileUtils.getFormattedSize;

public class RemovePagesFragment extends Fragment implements MergeFilesAdapter.OnClickListener,
        OnPDFCompressedInterface, BottomSheetPopulate, OnBackPressedInterface, IOnBackPressed, OnPdfReorderedInterface {

    private Activity mActivity;
    private String mPath;
    private MorphButtonUtility mMorphButtonUtility;
    private FileUtils mFileUtils;
    private BottomSheetUtils mBottomSheetUtils;
    private PDFUtils mPDFUtils;
    private static final int INTENT_REQUEST_PICKFILE_CODE = 10;
    private static final int INTENT_REQUEST_REARRANGE_PDF = 11;
    private String mOperation;
    private MaterialDialog mMaterialDialog;
    private BottomSheetBehavior mSheetBehavior;
    private PDFRotationUtils mPDFRotationUtils;
    private WatermarkUtils mWatermarkUtils;

    private static RemovePagesFragment instance;


    @BindView(R.id.lottie_progress)
    LottieAnimationView mLottieProgress;
    @BindView(R.id.selectFile)
    MorphingButton selectFileButton;
    @BindView(R.id.pdfCreate)
    MorphingButton createPdf;
    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;
    @BindView(R.id.upArrow)
    ImageView mUpArrow;
    @BindView(R.id.downArrow)
    ImageView mDownArrow;
    @BindView(R.id.layout)
    RelativeLayout mLayout;
    @BindView(R.id.pages)
    EditText pagesInput;
    @BindView(R.id.recyclerViewFiles)
    RecyclerView mRecyclerViewFiles;
    @BindView(R.id.infoText)
    TextView mInfoText;
    @BindView(R.id.compressionInfoText)
    TextView mCompressionInfoText;
    @BindView(R.id.view_pdf)
    Button mViewPdf;
    @BindView(R.id.idloctionofpdf)
    TextView tvloctionofpdf;
    @BindView(R.id.idEdTxtCard)
    CardView EdTxtCard;
    @BindView(R.id.idLocationLV)
    LinearLayout loccationLV;
    @BindView(R.id.popup)
    LinearLayout choosefilePP;
    @BindView(R.id.popup2)
    LinearLayout openpdfPP;
    @BindView(R.id.openpdf)
    MorphingButton opnPDF;
    private Uri mUri;
    String SuccessOrNot = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_remove_pages2, container, false);
        ButterKnife.bind(this, rootview);
        mPDFRotationUtils = new PDFRotationUtils(mActivity);
        mWatermarkUtils = new WatermarkUtils(mActivity);
        mSheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        mSheetBehavior.setBottomSheetCallback(new BottomSheetCallback(mUpArrow, isAdded()));
        mOperation = getArguments().getString(BUNDLE_DATA);
        mLottieProgress.setVisibility(View.VISIBLE);
        mBottomSheetUtils.populateBottomSheetWithPDFs(this);

        instance = this;

        resetValues();



        return rootview;
    }

    @OnClick(R.id.viewFiles)
    void onViewFilesClick(View view) {
        mBottomSheetUtils.showHideSheet(mSheetBehavior);
    }

    /**
     * Displays file chooser intent
     */
    @OnClick(R.id.selectFile)
    public void showFileChooser() {
//        startActivityForResult(mFileUtils.getFileChooser(),
//                INTENT_REQUEST_PICKFILE_CODE);

        mBottomSheetUtils.showHideSheet(mSheetBehavior);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) throws NullPointerException {
        if (data == null || resultCode != RESULT_OK)
            return;

        Log.e("req code","pk fl "+requestCode);

        if (requestCode == INTENT_REQUEST_PICKFILE_CODE) {
            mUri = data.getData();
            //Getting Absolute Path
            String path = RealPathUtil.getInstance().getRealPath(getContext(), data.getData());
            setTextAndActivateButtons(path);

        } else if (requestCode == INTENT_REQUEST_REARRANGE_PDF) {
            String pages = data.getStringExtra(RESULT);
            boolean sameFile = data.getBooleanExtra("SameFile", false);

            if (mPath == null)
                return;
            String outputPath;

            outputPath = setPath(pages);
            if (mPDFUtils.isPDFEncrypted(mPath)) {
                StringUtils.getInstance().showSnackbar(mActivity, R.string.encrypted_pdf);
                return;
            }

            if (!sameFile) {
                if (mPDFUtils.reorderRemovePDF(mPath, outputPath, pages)) {
                    viewPdfButton(outputPath);
                }
            } else {
                StringUtils.getInstance().showSnackbar(mActivity, R.string.file_order);
            }
            resetValues();
        }


    }

    /**
     * This method returns new  pdf name.
     *
     * @param pages The pages String that contains page numbers
     * @return Returns the new pdf name
     */
    private String setPath(String pages) {
        String outputPath;
        if (pages.length() > 50) {
            outputPath = mPath.replace(mActivity.getString(R.string.pdf_ext),
                    "_edited" + mActivity.getString(R.string.pdf_ext));
        } else {
            outputPath = mPath.replace(mActivity.getString(R.string.pdf_ext),
                    "_edited" + pages + mActivity.getString(R.string.pdf_ext));

        }
//        mFileUtils.openFile(mPath, FileUtils.FileType.e_PDF;
        Log.e("output",""+outputPath);
        return outputPath;
    }


    @OnClick(R.id.pdfCreate)
    public void parse() {
        StringUtils.getInstance().hideKeyboard(mActivity);
        if (mOperation.equals(COMPRESS_PDF)) {
            compressPDF();
            return;
        }

        PDFEncryptionUtility pdfEncryptionUtility = new PDFEncryptionUtility(mActivity);
        if (mOperation.equals(ADD_PWD)) {
            if (!mPDFUtils.isPDFEncrypted(mPath)) {
                pdfEncryptionUtility.setPassword(mPath, null);

            } else {
                StringUtils.getInstance().showSnackbar(mActivity, R.string.encrypted_pdf);

            }

            // delete file previous
            try {
                File filePath = new File(mPath);
                if (filePath.isFile()) {
                    String name = filePath.getName();
                    if (name.contains("_encrypted")) {
                        getContext().deleteFile(name);
                    } else {
                        Log.e("File", "File Contain no match: " + name);
                    }
                    Log.e("Delete", filePath.getAbsolutePath());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            //hndlr();
            return;
        }

        if (mOperation.equals(REMOVE_PWd)) {
            if (mPDFUtils.isPDFEncrypted(mPath)) {
                pdfEncryptionUtility.removePassword(mPath, null);
            } else {
                StringUtils.getInstance().showSnackbar(mActivity, R.string.not_encrypted);
            }
            return;
        }

        if (mOperation.equals(ROTATE_PAGE)) {

            mPDFRotationUtils.rotatePages(mPath, null);
            return;
        }

        if (mOperation.equals(ADD_WATERMARK2)) {
            mWatermarkUtils.setWatermark(mPath, null);
        }

        if (mOperation.equals(REORDER_PAGES)) {

            mPDFUtils.reorderPdfPages(mUri, mPath, this);
        }

        if (mOperation.equals(REMOVE_PAGES)) {


            mPDFUtils.reorderPdfPages(mUri, mPath, this);
//            mPDFUtils.reorderPdfPages(mUri, mPath, this);
        }





        //mPDFUtils.reorderPdfPages(mUri, mPath, this);
    }

    private void compressPDF() {
        String input = pagesInput.getText().toString();
        int check;
        try {
            check = Integer.parseInt(input);
            if (check > 100 || check <= 0 || mPath == null) {
                StringUtils.getInstance().showSnackbar(mActivity, R.string.invalid_entry);
            } else {
                String outputPath = mPath.replace(mActivity.getString(R.string.pdf_ext),
                        "_edited" + check + mActivity.getString(R.string.pdf_ext));
                mPDFUtils.compressPDF(mPath, outputPath, 100 - check, this);
            }
        } catch (NumberFormatException e) {
            StringUtils.getInstance().showSnackbar(mActivity, R.string.invalid_entry);
        }
    }

    private void resetValues() {
        mPath = null;
        pagesInput.setText(null);

        mMorphButtonUtility.initializeButton2(selectFileButton, createPdf);
        // tvloctionofpdf.setText(mPath);
        switch (mOperation) {
            case REORDER_PAGES:
            case REMOVE_PAGES:
            case ROTATE_PAGE:
            case ADD_PWD:
            case ADD_WATERMARK2:
            case REMOVE_PWd:
                mInfoText.setVisibility(View.GONE);
                pagesInput.setVisibility(View.GONE);
                break;
            case COMPRESS_PDF:
                mInfoText.setText(R.string.compress_pdf_prompt);
                break;
        }
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
        mSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        setTextAndActivateButtons(path);
        createPdf.setVisibility(View.VISIBLE);

        if (mPath != null) {
            loccationLV.setVisibility(View.VISIBLE);

            EdTxtCard.setVisibility(View.VISIBLE);
            if (mOperation.equals("Compress PDF")){
                mInfoText.setVisibility(View.VISIBLE);
            }

        } else {
            loccationLV.setVisibility(View.GONE);
            mInfoText.setVisibility(View.GONE);
            EdTxtCard.setVisibility(View.GONE);
        }

//        if (createPdf.getVisibility() == View.GONE)
//        {
//
//        }
    }

    @OnClick(R.id.openpdf)
    public void opnmypdf(){

        mFileUtils.openFile(SuccessOrNot, FileUtils.FileType.e_PDF);
    }

    private void setTextAndActivateButtons(String path) {
        if (path == null) {
            StringUtils.getInstance().showSnackbar(mActivity, R.string.file_access_error);
            resetValues();
            return;
        }
        mPath = path;
        mCompressionInfoText.setVisibility(View.GONE);
        mViewPdf.setVisibility(View.GONE);
        mMorphButtonUtility.setTextAndActivateButtons2(path,
                selectFileButton, createPdf);
        tvloctionofpdf.setText(mPath);
    }

    @Override
    public void pdfCompressionStarted() {
        mMaterialDialog = DialogUtils.getInstance().createAnimationDialog(mActivity);
        mMaterialDialog.show();
    }

    @Override
    public void pdfCompressionEnded(String path, Boolean success) {
        mMaterialDialog.dismiss();
        if (success && path != null && mPath != null) {
            StringUtils.getInstance().getSnackbarwithAction(mActivity, R.string.snackbar_pdfCreated)
                    .setAction(R.string.snackbar_viewAction,
                            v -> mFileUtils.openFile(path, FileUtils.FileType.e_PDF)).show();
            new DatabaseHelper(mActivity).insertRecord(path,
                    mActivity.getString(R.string.created));
            File input = new File(mPath);
            File output = new File(path);
            viewPdfButton(path);
            mCompressionInfoText.setVisibility(View.VISIBLE);
            mCompressionInfoText.setText(String.format(mActivity.getString(R.string.compress_info),
                    getFormattedSize(input),
                    getFormattedSize(output)));
        } else {
            StringUtils.getInstance().showSnackbar(mActivity, R.string.encrypted_pdf);
        }
        resetValues();
    }

    private void viewPdfButton(String path) {
        mViewPdf.setVisibility(View.VISIBLE);
        mViewPdf.setOnClickListener(v -> mFileUtils.openFile(path, FileUtils.FileType.e_PDF));
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

    @Override
    public void onPdfReorderStarted() {
        mMaterialDialog = DialogUtils
                .getInstance()
                .createCustomAnimationDialog(mActivity, mActivity.getString(R.string.reordering_pages_dialog));
        mMaterialDialog.show();
    }

    @Override
    public void onPdfReorderCompleted(List<Bitmap> bitmaps) {
        mMaterialDialog.dismiss();
        RearrangePdfPages.mImages = new ArrayList<>(bitmaps);
        bitmaps.clear(); //releasing memory
        startActivityForResult(RearrangePdfPages.getStartIntent(mActivity),
                INTENT_REQUEST_REARRANGE_PDF);
    }

    @Override
    public void onPdfReorderFailed() {
        mMaterialDialog.dismiss();
        StringUtils.getInstance().showSnackbar(mActivity, R.string.file_access_error);
    }

    @Override
    public boolean onBackPressed() {

        //IOnBackPressed,

        if (mPath != null) {
            //action not popBackStack
            // Toast.makeText(mActivity, "backpress clicked", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("If You Discard Now, You'll Lose Changes You've Made to It.")
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




    public static RemovePagesFragment getInstance() {
        return instance;
    }

    public void myMethod(String ppth) {
        // do something...

        choosefilePP.setVisibility(View.GONE);
        openpdfPP.setVisibility(View.VISIBLE);
        SuccessOrNot = ppth;

    }
}
