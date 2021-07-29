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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.airbnb.lottie.LottieAnimationView;
import com.dd.morphingbutton.MorphingButton;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.editmypdffree.rdtl.R;
import com.editmypdffree.rdtl.adapter.EnhancementOptionsAdapter;
import com.editmypdffree.rdtl.adapter.MergeFilesAdapter;
import com.editmypdffree.rdtl.database.DatabaseHelper;
import com.editmypdffree.rdtl.interfaces.BottomSheetPopulate;
import com.editmypdffree.rdtl.interfaces.OnItemClickListener;
import com.editmypdffree.rdtl.interfaces.OnPDFCreatedInterface;
import com.editmypdffree.rdtl.model.EnhancementOptionsEntity;
import com.editmypdffree.rdtl.util.BottomSheetCallback;
import com.editmypdffree.rdtl.util.BottomSheetUtils;
import com.editmypdffree.rdtl.util.CommonCodeUtils;
import com.editmypdffree.rdtl.util.Constants;
import com.editmypdffree.rdtl.util.DialogUtils;
import com.editmypdffree.rdtl.util.ExcelToPDFAsync;
import com.editmypdffree.rdtl.util.FileUtils;
import com.editmypdffree.rdtl.util.MergePdfEnhancementOptionsUtils;
import com.editmypdffree.rdtl.util.MorphButtonUtility;
import com.editmypdffree.rdtl.util.PermissionsUtils;
import com.editmypdffree.rdtl.util.RealPathUtil;
import com.editmypdffree.rdtl.util.StringUtils;

import static android.app.Activity.RESULT_OK;
import static com.editmypdffree.rdtl.util.Constants.READ_WRITE_PERMISSIONS;
import static com.editmypdffree.rdtl.util.Constants.STORAGE_LOCATION;

public class ExceltoPdfFragment extends Fragment implements MergeFilesAdapter.OnClickListener,
        OnPDFCreatedInterface, OnItemClickListener, IOnBackPressed, BottomSheetPopulate {
    private Activity mActivity;
    private FileUtils mFileUtils;
    private Uri mExcelFileUri;
    private String mRealPath;
    private String mPath;

    private BottomSheetBehavior mSheetBehavior;

    @BindView(R.id.lottie_progress)
    LottieAnimationView mLottieProgress;
    @BindView(R.id.tv_excel_file_name_bottom)
    TextView mTextView;
    @BindView(R.id.open_pdf)
    MorphingButton mOpenPdf;
    @BindView(R.id.create_excel_to_pdf)
    MorphingButton mCreateExcelPdf;
    @BindView(R.id.enhancement_options_recycle_view)
    RecyclerView mEnhancementOptionsRecycleView;
    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;
    @BindView(R.id.upArrow)
    ImageView mUpArrow;
    @BindView(R.id.layout)
    RelativeLayout mLayout;
    @BindView(R.id.recyclerViewFiles)
    RecyclerView mRecyclerViewFiles;

    @BindView(R.id.select_excel_file)
    MorphingButton selectexlfile;


    private SharedPreferences mSharedPreferences;
    private MorphButtonUtility mMorphButtonUtility;
    private BottomSheetUtils mBottomSheetUtils;
    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE_RESULT = 1;
    private boolean mPermissionGranted = false;
    private boolean mButtonClicked = false;
    private final int mFileSelectCode = 0;
    private MaterialDialog mMaterialDialog;
    private ArrayList<EnhancementOptionsEntity> mEnhancementOptionsEntityArrayList;
    private EnhancementOptionsAdapter mEnhancementOptionsAdapter;
    private boolean mPasswordProtected = false;
    private String mPassword;

    public ExceltoPdfFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_excelto_pdf, container,
                false);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
        mPermissionGranted = PermissionsUtils.getInstance().checkRuntimePermissions(this, READ_WRITE_PERMISSIONS);
        mMorphButtonUtility = new MorphButtonUtility(mActivity);
        ButterKnife.bind(this, rootView);
        showEnhancementOptions();
        mMorphButtonUtility.morphToGrey(mCreateExcelPdf, mMorphButtonUtility.integer());
        mCreateExcelPdf.setEnabled(false);

        ButterKnife.bind(this, rootView);
        mSheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        mSheetBehavior.setBottomSheetCallback(new BottomSheetCallback(mUpArrow, isAdded()));
        mLottieProgress.setVisibility(View.VISIBLE);
        mBottomSheetUtils.populateBottomSheetWithExcelFiles(this);



        return rootView;
    }

    private void showEnhancementOptions() {

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(mActivity, 2);
        mEnhancementOptionsRecycleView.setLayoutManager(mGridLayoutManager);
        mEnhancementOptionsEntityArrayList = MergePdfEnhancementOptionsUtils.getInstance()
                .getEnhancementOptions(mActivity);
        mEnhancementOptionsAdapter = new EnhancementOptionsAdapter(
                this, mEnhancementOptionsEntityArrayList);
        mEnhancementOptionsRecycleView.setAdapter(mEnhancementOptionsAdapter);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        mFileUtils = new FileUtils(mActivity);
        mBottomSheetUtils = new BottomSheetUtils(mActivity);
    }

    @OnClick(R.id.select_excel_file)
    public void selectExcelFile() {
        if (!mButtonClicked) {
            Uri uri = Uri.parse(Environment.getRootDirectory() + "/");
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setDataAndType(uri, "*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            try {
                startActivityForResult(
                        Intent.createChooser(intent, String.valueOf(R.string.select_file)),
                        mFileSelectCode);
            } catch (android.content.ActivityNotFoundException ex) {
                StringUtils.getInstance().showSnackbar(mActivity, R.string.install_file_manager);
            }
            mButtonClicked = true;
        }

    }

    /**
     * This function opens a dialog to enter the file name of
     * the converted file.
     */
    @OnClick(R.id.create_excel_to_pdf)
    public void openExcelToPdf() {
        if (!mPermissionGranted)
            getRuntimePermissions();

        new MaterialDialog.Builder(mActivity)
                .title(R.string.creating_pdf)
                .content(R.string.enter_file_name)
                .input(getString(R.string.example), null, (dialog, input) -> {
                    if (StringUtils.getInstance().isEmpty(input)) {
                        StringUtils.getInstance().showSnackbar(mActivity, R.string.snackbar_name_not_blank);
                    } else {
                        final String inputName = input.toString();
                        if (!mFileUtils.isFileExist(inputName + getString(R.string.pdf_ext))) {
                            convertToPdf(inputName);
                        } else {
                            MaterialDialog.Builder builder = DialogUtils.getInstance().createOverwriteDialog(mActivity);
                            builder.onPositive((dialog12, which) -> convertToPdf(inputName))
                                    .onNegative((dialog1, which) -> openExcelToPdf())
                                    .show();
                        }
                    }
                })
                .show();
    }

    @OnClick(R.id.open_pdf)
    void openPdf() {
        mFileUtils.openFile(mPath, FileUtils.FileType.e_PDF);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mButtonClicked = false;
        if (requestCode == mFileSelectCode) {
            if (resultCode == RESULT_OK) {
                mExcelFileUri = data.getData();
                mRealPath = RealPathUtil.getInstance().getRealPath(getContext(), mExcelFileUri);
                processUri();
            }
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
                openExcelToPdf();
                StringUtils.getInstance().showSnackbar(mActivity, R.string.snackbar_permissions_given);
            } else
                StringUtils.getInstance().showSnackbar(mActivity, R.string.snackbar_insufficient_permissions);
        }
    }

    private void processUri() {
        StringUtils.getInstance().showSnackbar(mActivity, getResources().getString(R.string.excel_selected));
        String fileName = mFileUtils.getFileName(mExcelFileUri);
        if (fileName != null && !fileName.endsWith(Constants.excelExtension) &&
                !fileName.endsWith(Constants.excelWorkbookExtension)) {
            StringUtils.getInstance().showSnackbar(mActivity, R.string.extension_not_supported);
            return;

        }
        if (mCreateExcelPdf.getVisibility() == View.GONE)
        {
            mCreateExcelPdf.setVisibility(View.VISIBLE);
        }

        if (mEnhancementOptionsRecycleView.getVisibility() == View.GONE)
        {
            mEnhancementOptionsRecycleView.setVisibility(View.VISIBLE);
        }
        fileName = getResources().getString(R.string.excel_selected)
                +": "+ fileName;
        mTextView.setText(fileName);
        mTextView.setText("fffffffffffffff");
        mTextView.setVisibility(View.VISIBLE);
        mCreateExcelPdf.setEnabled(true);
        mCreateExcelPdf.unblockTouch();
        mMorphButtonUtility.morphToSquare(mCreateExcelPdf, mMorphButtonUtility.integer());
        mOpenPdf.setVisibility(View.GONE);
        selectexlfile.setVisibility(View.VISIBLE);
        selectexlfile.setText(fileName);
        mTextView.setVisibility(View.VISIBLE);
    }

    /**
     * This function is required to convert the chosen excel file
     * to PDF.
     *
     * @param mFilename - output PDF name
     */

    private void convertToPdf(String mFilename) {
        String mStorePath = mSharedPreferences.getString(STORAGE_LOCATION,
                StringUtils.getInstance().getDefaultStorageLocation());
        mPath = mStorePath + mFilename + mActivity.getString(R.string.pdf_ext);
        new ExcelToPDFAsync(mRealPath, mPath, ExceltoPdfFragment.this, mPasswordProtected, mPassword).execute();

    }

    private void getRuntimePermissions() {
        PermissionsUtils.getInstance().requestRuntimePermissions(this,
                READ_WRITE_PERMISSIONS,
                PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE_RESULT);
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
            StringUtils.getInstance().showSnackbar(mActivity, R.string.error_pdf_not_created);
            mTextView.setVisibility(View.GONE);
            mMorphButtonUtility.morphToGrey(mCreateExcelPdf, mMorphButtonUtility.integer());
            mCreateExcelPdf.setEnabled(false);
            mExcelFileUri = null;
            return;
        }
        StringUtils.getInstance().getSnackbarwithAction(mActivity, R.string.snackbar_pdfCreated)
                .setAction(R.string.snackbar_viewAction,
                        v -> mFileUtils.openFile(mPath, FileUtils.FileType.e_PDF))
                .show();
        new DatabaseHelper(mActivity).insertRecord(mPath, mActivity.getString(R.string.created));
        mTextView.setVisibility(View.GONE);
        mOpenPdf.setVisibility(View.VISIBLE);
        mMorphButtonUtility.morphToSuccess(mCreateExcelPdf);
        mCreateExcelPdf.blockTouch();
        mMorphButtonUtility.morphToGrey(mCreateExcelPdf, mMorphButtonUtility.integer());
        mExcelFileUri = null;
        mPasswordProtected = false;
        showEnhancementOptions();
    }

    @Override
    public void onItemClick(int position) {
        if (!mCreateExcelPdf.isEnabled()) {
            StringUtils.getInstance().showSnackbar(mActivity, R.string.no_excel_file);
            return;
        }
        if (position == 0) {
            setPassword();
        }
    }

    private void setPassword() {
        MaterialDialog.Builder builder = DialogUtils.getInstance().createCustomDialogWithoutContent(mActivity);
        final MaterialDialog dialog = builder
                .customView(R.layout.custom_dialog, true)
                .backgroundColor(getResources().getColor(R.color.alrtbtmclr))
                .positiveColor(getResources().getColor(R.color.white_color))
                .negativeColor(getResources().getColor(R.color.white_color))
                .neutralColor(getResources().getColor(R.color.ntrclr))
                .titleColor(getResources().getColor(R.color.white_color))
                .neutralText(R.string.remove_dialog)
                .build();

        final View positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        final View neutralAction = dialog.getActionButton(DialogAction.NEUTRAL);
        final EditText passwordInput = Objects.requireNonNull(dialog.getCustomView()).findViewById(R.id.password);
        passwordInput.setText(mPassword);
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
                        if (StringUtils.getInstance().isEmpty(input)) {
                            StringUtils.getInstance().
                                    showSnackbar(mActivity, R.string.snackbar_password_cannot_be_blank);
                        } else {
                            mPassword = input.toString();
                            mPasswordProtected = true;
                            onPasswordAdded();
                        }
                    }
                });
        if (StringUtils.getInstance().isNotEmpty(mPassword)) {
            neutralAction.setOnClickListener(v -> {
                mPassword = null;
                onPasswordRemoved();
                mPasswordProtected = false;
                dialog.dismiss();
                StringUtils.getInstance().showSnackbar(mActivity, R.string.password_remove);
            });
        }
        dialog.show();
        positiveAction.setEnabled(false);
    }

    private void onPasswordAdded() {
        mEnhancementOptionsEntityArrayList.get(0)
                .setImage(mActivity.getResources()
                        .getDrawable(R.drawable.baseline_done_24));
        mEnhancementOptionsAdapter.notifyDataSetChanged();
    }

    private void onPasswordRemoved() {
        mEnhancementOptionsEntityArrayList.get(0)
                .setImage(mActivity.getResources()
                        .getDrawable(R.drawable.ic_add_password));
        mEnhancementOptionsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(String path) {
        mSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mExcelFileUri = Uri.parse("file://" + path);
        mRealPath = path;
        processUri();
    }

    @Override
    public void onPopulate(ArrayList<String> paths) {
        CommonCodeUtils.getInstance().populateUtil(mActivity, paths,
                this, mLayout, mLottieProgress, mRecyclerViewFiles);
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
