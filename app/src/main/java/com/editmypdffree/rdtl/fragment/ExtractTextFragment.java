package com.editmypdffree.rdtl.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;

import com.editmypdffree.rdtl.interfaces.IOnBackPressed;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.airbnb.lottie.LottieAnimationView;
import com.dd.morphingbutton.MorphingButton;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.File;
import java.io.FileWriter;
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
import com.editmypdffree.rdtl.util.Constants;
import com.editmypdffree.rdtl.util.DialogUtils;
import com.editmypdffree.rdtl.util.FileUtils;
import com.editmypdffree.rdtl.util.MorphButtonUtility;
import com.editmypdffree.rdtl.util.PermissionsUtils;
import com.editmypdffree.rdtl.util.RealPathUtil;
import com.editmypdffree.rdtl.util.StringUtils;

import static android.app.Activity.RESULT_OK;
import static com.editmypdffree.rdtl.util.Constants.READ_WRITE_PERMISSIONS;
import static com.editmypdffree.rdtl.util.Constants.STORAGE_LOCATION;
import static com.editmypdffree.rdtl.util.Constants.textExtension;

public class ExtractTextFragment extends Fragment implements MergeFilesAdapter.OnClickListener,
        BottomSheetPopulate, IOnBackPressed, OnBackPressedInterface {

    private Activity mActivity;
    private FileUtils mFileUtils;
    private Uri mExcelFileUri;
    private String mRealPath;
    private BottomSheetUtils mBottomSheetUtils;
    private BottomSheetBehavior mSheetBehavior;

    @BindView(R.id.tv_extract_text_bottom)
    TextView mTextView;
    @BindView(R.id.extract_text)
    MorphingButton extractText;
    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;
    @BindView(R.id.recyclerViewFiles)
    RecyclerView mRecyclerViewFiles;
    @BindView(R.id.upArrow)
    ImageView mUpArrow;
    @BindView(R.id.layout)
    RelativeLayout mLayout;
    @BindView(R.id.lottie_progress)
    LottieAnimationView mLottieProgress;

    private SharedPreferences mSharedPreferences;
    private MorphButtonUtility mMorphButtonUtility;
    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE_RESULT = 1;
    private boolean mPermissionGranted = false;
    private boolean mButtonClicked = false;
    private String mFileName;
    private final int mFileSelectCode = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_extract_text, container,
                false);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
        mPermissionGranted = PermissionsUtils.getInstance().checkRuntimePermissions(this, READ_WRITE_PERMISSIONS);
        mMorphButtonUtility = new MorphButtonUtility(mActivity);
        ButterKnife.bind(this, rootView);
        mSheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        mMorphButtonUtility.morphToGrey(extractText, mMorphButtonUtility.integer());
        extractText.setEnabled(false);
        mBottomSheetUtils.populateBottomSheetWithPDFs(this);
        mLottieProgress.setVisibility(View.VISIBLE);
        mSheetBehavior.setBottomSheetCallback(new BottomSheetCallback(mUpArrow, isAdded()));
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        mFileUtils = new FileUtils(mActivity);
        mBottomSheetUtils = new BottomSheetUtils(mActivity);
    }

    @OnClick(R.id.viewFiles)
    void onViewFilesClick(View view) {
        mBottomSheetUtils.showHideSheet(mSheetBehavior);
    }

    @OnClick(R.id.select_pdf_file)
    public void selectPdfFile() {
        if (!mButtonClicked) {
            Uri uri = Uri.parse(Environment.getRootDirectory() + "/");
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setDataAndType(uri, "*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            try {
                startActivityForResult(
                        Intent.createChooser(intent, String.valueOf(R.string.select_file)),
                        mFileSelectCode);
                mButtonClicked = true;
            } catch (android.content.ActivityNotFoundException ex) {
                StringUtils.getInstance().showSnackbar(mActivity, R.string.install_file_manager);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mButtonClicked = false;
        if (requestCode == mFileSelectCode && resultCode == RESULT_OK) {
            mExcelFileUri = data.getData();
            mRealPath = RealPathUtil.getInstance().getRealPath(getContext(), data.getData());
            StringUtils.getInstance().showSnackbar(mActivity, getResources().getString(R.string.snackbar_pdfselected));
            mFileName = mFileUtils.getFileName(mExcelFileUri);
            if (mFileName != null && !mFileName.endsWith(Constants.pdfExtension)) {
                StringUtils.getInstance().showSnackbar(mActivity, R.string.extension_not_supported);
                return;
            }
            mFileName = mActivity.getResources().getString(R.string.pdf_selected)
                    + mFileName;
            mTextView.setText(mFileName);
            mTextView.setVisibility(View.VISIBLE);
            extractText.setEnabled(true);
            mMorphButtonUtility.morphToSquare(extractText, mMorphButtonUtility.integer());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length < 1)
            return;
        if (requestCode == PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE_RESULT) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPermissionGranted = true;
                openExtractText();
                StringUtils.getInstance().showSnackbar(mActivity, R.string.snackbar_permissions_given);
            } else
                StringUtils.getInstance().showSnackbar(mActivity, R.string.snackbar_insufficient_permissions);
        }
    }

    /**
     * This function is used to open up the Dialog box to enter the
     * file name.
     */
    @OnClick(R.id.extract_text)
    public void openExtractText() {
        if (!mPermissionGranted) {
            PermissionsUtils.getInstance().requestRuntimePermissions(this,
                    READ_WRITE_PERMISSIONS,
                    PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE_RESULT);
            return;
        }

        new MaterialDialog.Builder(mActivity)
                .title(R.string.creating_txt)
                .content(R.string.enter_file_name)
                .input(getString(R.string.example), null, (dialog, input) -> {
                    if (StringUtils.getInstance().isEmpty(input)) {
                        StringUtils.getInstance().showSnackbar(mActivity, R.string.snackbar_name_not_blank);
                    } else {
                        final String inputName = input.toString();
                        if (!mFileUtils.isFileExist(inputName + textExtension)) {
                            extractTextFromPdf(inputName);
                        } else {
                            MaterialDialog.Builder builder = DialogUtils.getInstance().createOverwriteDialog(mActivity);
                            builder.onPositive((dialog12, which) -> extractTextFromPdf(inputName))
                                    .onNegative((dialog1, which) -> openExtractText())
                                    .show();
                        }
                    }
                })
                .show();
    }

    /**
     * This function is used to extract the text from a PDF and store
     * it in a new text file.
     *
     * @param inputName -  input pdf filename
     */
    private void extractTextFromPdf(String inputName) {
        String mStorePath = mSharedPreferences.getString(STORAGE_LOCATION,
                StringUtils.getInstance().getDefaultStorageLocation());
        String mPath = mStorePath + inputName + textExtension;
        try {
            StringBuilder parsedText = new StringBuilder();
            PdfReader reader = new PdfReader(mRealPath);
            int n = reader.getNumberOfPages();
            for (int i = 0; i < n; i++) {
                parsedText.append(PdfTextExtractor.getTextFromPage(reader, i + 1)
                        .trim()).append("\n"); //Extracting the content from the different pages
            }
            reader.close();
            // Check whether there is no text found from the PDF Doc
            if (TextUtils.isEmpty(parsedText.toString().trim())) {
                StringUtils.getInstance().showSnackbar(mActivity, R.string.snack_bar_empty_txt_in_pdf);
                return;
            }
            File textFile = new File(mStorePath, inputName + textExtension);
            FileWriter writer = new FileWriter(textFile);
            writer.append(parsedText.toString());
            writer.flush();
            writer.close();
            StringUtils.getInstance().getSnackbarwithAction(mActivity, R.string.snackbar_txtExtracted)
                    .setAction(R.string.snackbar_viewAction,
                            v -> mFileUtils.openFile(mPath, FileUtils.FileType.e_TXT))
                    .show();
            mTextView.setVisibility(View.GONE);
            mButtonClicked = false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mMorphButtonUtility.morphToGrey(extractText, mMorphButtonUtility.integer());
            extractText.setEnabled(false);
            mRealPath = null;
            mExcelFileUri = null;
        }
    }

    @Override
    public void onPopulate(ArrayList<String> paths) {
        CommonCodeUtils.getInstance().populateUtil(mActivity, paths,
                this, mLayout, mLottieProgress, mRecyclerViewFiles);
    }

    @Override
    public void onItemClick(String path) {
        mSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mRealPath = path;
        mFileName = FileUtils.getFileName(path);
        mFileName = getResources().getString(R.string.pdf_selected) + mFileName;
        mTextView.setText(mFileName);
        mTextView.setVisibility(View.VISIBLE);
        extractText.setEnabled(true);
        mMorphButtonUtility.morphToSquare(extractText, mMorphButtonUtility.integer());
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
    public boolean onBackPressed() {

        //IOnBackPressed,

        if (mRealPath != null) {
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
            mRealPath = null;
            return false;
        }
    }
}
