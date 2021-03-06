package com.editmypdffree.rdtl.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.editmypdffree.rdtl.interfaces.IOnBackPressed;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.airbnb.lottie.LottieAnimationView;
import com.dd.morphingbutton.MorphingButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.editmypdffree.rdtl.R;
import com.editmypdffree.rdtl.adapter.EnhancementOptionsAdapter;
import com.editmypdffree.rdtl.adapter.MergeFilesAdapter;
import com.editmypdffree.rdtl.adapter.MergeSelectedFilesAdapter;
import com.editmypdffree.rdtl.database.DatabaseHelper;
import com.editmypdffree.rdtl.interfaces.BottomSheetPopulate;
import com.editmypdffree.rdtl.interfaces.MergeFilesListener;
import com.editmypdffree.rdtl.interfaces.OnBackPressedInterface;
import com.editmypdffree.rdtl.interfaces.OnItemClickListener;
import com.editmypdffree.rdtl.model.EnhancementOptionsEntity;
import com.editmypdffree.rdtl.util.BottomSheetCallback;
import com.editmypdffree.rdtl.util.BottomSheetUtils;
import com.editmypdffree.rdtl.util.CommonCodeUtils;
import com.editmypdffree.rdtl.util.DialogUtils;
import com.editmypdffree.rdtl.util.FileUtils;
import com.editmypdffree.rdtl.util.MergePdf;
import com.editmypdffree.rdtl.util.MergePdfEnhancementOptionsUtils;
import com.editmypdffree.rdtl.util.MorphButtonUtility;
import com.editmypdffree.rdtl.util.RealPathUtil;
import com.editmypdffree.rdtl.util.StringUtils;
import com.editmypdffree.rdtl.util.ViewFilesDividerItemDecoration;

import static android.app.Activity.RESULT_OK;
import static com.editmypdffree.rdtl.util.Constants.MASTER_PWD_STRING;
import static com.editmypdffree.rdtl.util.Constants.STORAGE_LOCATION;
import static com.editmypdffree.rdtl.util.Constants.appName;

public class MergeFilesFragment extends Fragment implements MergeFilesAdapter.OnClickListener, MergeFilesListener,
        MergeSelectedFilesAdapter.OnFileItemClickListener, OnItemClickListener,
        BottomSheetPopulate, IOnBackPressed, OnBackPressedInterface {
    private Activity mActivity;
    private String mCheckbtClickTag = "";
    private static final int INTENT_REQUEST_PICK_FILE_CODE = 10;
    private MorphButtonUtility mMorphButtonUtility;
    private ArrayList<String> mFilePaths;
    private FileUtils mFileUtils;
    private BottomSheetUtils mBottomSheetUtils;
    private MergeSelectedFilesAdapter mMergeSelectedFilesAdapter;
    private MaterialDialog mMaterialDialog;
    private String mHomePath;
    private ArrayList<EnhancementOptionsEntity> mEnhancementOptionsEntityArrayList;
    private EnhancementOptionsAdapter mEnhancementOptionsAdapter;
    private boolean mPasswordProtected = false;
    private String mPassword;
    private SharedPreferences mSharedPrefs;
    private BottomSheetBehavior mSheetBehavior;
    String savepath=null;

    @BindView(R.id.lottie_progress)
    LottieAnimationView mLottieProgress;
    @BindView(R.id.mergebtn)
    MorphingButton mergeBtn;
    @BindView(R.id.recyclerViewFiles)
    RecyclerView mRecyclerViewFiles;
    @BindView(R.id.upArrow)
    ImageView mUpArrow;
    @BindView(R.id.downArrow)
    ImageView mDownArrow;
    @BindView(R.id.layout)
    RelativeLayout mLayout;
    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;
    @BindView(R.id.selectFiles)
    Button mSelectFiles;
    @BindView(R.id.selected_files)
    RecyclerView mSelectedFiles;
    @BindView(R.id.enhancement_options_recycle_view)
    RecyclerView mEnhancementOptionsRecycleView;
    @BindView(R.id.idNestedSV)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.relativebtmcreate)
    RelativeLayout mrelativebtmcreate;
    @BindView(R.id.popup)
    LinearLayout popup1s;
    @BindView(R.id.popup2)
    LinearLayout popup2n;
    @BindView(R.id.openpdf)
    MorphingButton mopenpdf;
    @BindView(R.id.idCard1stWhite)
    CardView midCard1stWhite;

    public MergeFilesFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_merge_files, container, false);
        ButterKnife.bind(this, root);
        showEnhancementOptions();
        mSheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        mFilePaths = new ArrayList<>();
        mMergeSelectedFilesAdapter = new MergeSelectedFilesAdapter(mActivity, mFilePaths, this);
        mMorphButtonUtility = new MorphButtonUtility(mActivity);
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
        mHomePath = mSharedPrefs.getString(STORAGE_LOCATION,
                StringUtils.getInstance().getDefaultStorageLocation());
        mLottieProgress.setVisibility(View.VISIBLE);
        mBottomSheetUtils.populateBottomSheetWithPDFs(this);

        mSelectedFiles.setAdapter(mMergeSelectedFilesAdapter);
        mSelectedFiles.addItemDecoration(new ViewFilesDividerItemDecoration(mActivity));

        mSheetBehavior.setBottomSheetCallback(new BottomSheetCallback(mUpArrow, isAdded()));
        setMorphingButtonState(false);

        mSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        return root;
    }

    /**
     * Function to show the enhancement options.
     */
    private void showEnhancementOptions() {
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(mActivity, 2);
        mEnhancementOptionsRecycleView.setLayoutManager(mGridLayoutManager);
        mEnhancementOptionsEntityArrayList = MergePdfEnhancementOptionsUtils.getInstance()
                .getEnhancementOptions(mActivity);
        mEnhancementOptionsAdapter = new EnhancementOptionsAdapter(this, mEnhancementOptionsEntityArrayList);
        mEnhancementOptionsRecycleView.setAdapter(mEnhancementOptionsAdapter);
    }

    @Override
    public void onItemClick(int position) {
        if (mFilePaths.size() == 0) {
            StringUtils.getInstance().showSnackbar(mActivity, R.string.snackbar_no_pdfs_selected);
            return;
        }
        if (position == 0) {
            setPassword();
        }
    }

    @OnClick(R.id.openpdf)
    public void opnpdf(){
        mFileUtils.openFile(savepath, FileUtils.FileType.e_PDF);
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
                            onPasswordStatusChanges(true);
                        }
                    }
                });
        if (StringUtils.getInstance().isNotEmpty(mPassword)) {
            neutralAction.setOnClickListener(v -> {
                mPassword = null;
                onPasswordStatusChanges(false);
                mPasswordProtected = false;
                dialog.dismiss();
                StringUtils.getInstance().showSnackbar(mActivity, R.string.password_remove);
            });
        }
        dialog.show();
        positiveAction.setEnabled(false);
    }

    private void onPasswordStatusChanges(boolean passwordAdded) {
        mEnhancementOptionsEntityArrayList.get(0)
                .setImage(mActivity.getResources()
                        .getDrawable(passwordAdded ?
                                R.drawable.baseline_done_24 : R.drawable.baseline_enhanced_encryption_24));
        mEnhancementOptionsAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.viewFiles)
    void onViewFilesClick(View view) {
        mBottomSheetUtils.showHideSheet(mSheetBehavior);
    }

    @OnClick(R.id.selectFiles)
    void startAddingPDF(View v) {
//        startActivityForResult(mFileUtils.getFileChooser(),
//                INTENT_REQUEST_PICK_FILE_CODE);
        mBottomSheetUtils.showHideSheet(mSheetBehavior);
    }

    @OnClick(R.id.mergebtn)
    void mergeFiles(final View view) {
        String[] pdfpaths = mFilePaths.toArray(new String[0]);
        String masterpwd = mSharedPrefs.getString(MASTER_PWD_STRING, appName);
        new MaterialDialog.Builder(mActivity)
                .title(R.string.creating_pdf)
                .content(R.string.enter_file_name)
                .input(getString(R.string.example), null, (dialog, input) -> {
                    if (StringUtils.getInstance().isEmpty(input)) {
                        StringUtils.getInstance().showSnackbar(mActivity, R.string.snackbar_name_not_blank);
                    } else {
                        if (!mFileUtils.isFileExist(input + getString(R.string.pdf_ext))) {
                            new MergePdf(input.toString(), mHomePath, mPasswordProtected,
                                    mPassword, this, masterpwd).execute(pdfpaths);
                            popup1s.setVisibility(View.GONE);
                            popup2n.setVisibility(View.VISIBLE);
                        } else {
                            MaterialDialog.Builder builder = DialogUtils.getInstance().createOverwriteDialog(mActivity);
                            builder.onPositive((dialog12, which) -> {
                                new MergePdf(input.toString(),
                                        mHomePath, mPasswordProtected, mPassword,
                                        this, masterpwd).execute(pdfpaths);
                                onpositiviefor2nd();
                            })
                                    .onNegative((dialog1, which) -> mergeFiles(view)).show();
                        }
                    }
                })
                .show();
    }

    private void onpositiviefor2nd(){
        popup1s.setVisibility(View.GONE);
        popup2n.setVisibility(View.VISIBLE);

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || resultCode != RESULT_OK || data.getData() == null)
            return;
        if (requestCode == INTENT_REQUEST_PICK_FILE_CODE) {
            //Getting Absolute Path
            String path = RealPathUtil.getInstance().getRealPath(getContext(), data.getData());
            mFilePaths.add(path);
            mMergeSelectedFilesAdapter.notifyDataSetChanged();
            StringUtils.getInstance().showSnackbar(mActivity, getString(R.string.pdf_added_to_list));
            if (mFilePaths.size() > 1 && !mergeBtn.isEnabled())
                setMorphingButtonState(true);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mCheckbtClickTag = savedInstanceState.getString("savText");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.btn_sav_text), mCheckbtClickTag);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        mFileUtils = new FileUtils(mActivity);
        mBottomSheetUtils = new BottomSheetUtils(mActivity);
    }

    @Override
    public void onItemClick(String path) {
        if (mFilePaths.contains(path)) {
            mFilePaths.remove(path);
            StringUtils.getInstance().showSnackbar(mActivity, getString(R.string.pdf_removed_from_list));
        } else {
            mFilePaths.add(path);
            StringUtils.getInstance().showSnackbar(mActivity, getString(R.string.pdf_added_to_list));
        }

        mMergeSelectedFilesAdapter.notifyDataSetChanged();
        if (mFilePaths.size() > 1) {
            if (!mergeBtn.isEnabled()) setMorphingButtonState(true);
            mNestedScrollView.setVisibility(View.VISIBLE);
            mrelativebtmcreate.setVisibility(View.VISIBLE);
        } else {
            if (mergeBtn.isEnabled()) setMorphingButtonState(false);
            mNestedScrollView.setVisibility(View.GONE);
            mrelativebtmcreate.setVisibility(View.GONE);
        }

        //addMoreHeight();
    }

    /**
     * resets fragment to initial stage
     */
    @Override
    public void resetValues(boolean isPDFMerged, String path) {
        mMaterialDialog.dismiss();

        if (isPDFMerged) {
            savepath = path;
            StringUtils.getInstance().getSnackbarwithAction(mActivity, R.string.pdf_merged)
                    .setAction(R.string.snackbar_viewAction,
                            v -> mFileUtils.openFile(path, FileUtils.FileType.e_PDF)).show();
            new DatabaseHelper(mActivity).insertRecord(path,
                    mActivity.getString(R.string.created));

            Toast.makeText(mActivity, "DLONE", Toast.LENGTH_SHORT).show();
        } else
            StringUtils.getInstance().showSnackbar(mActivity, R.string.file_access_error);

        setMorphingButtonState(false);
        mFilePaths.clear();
        mMergeSelectedFilesAdapter.notifyDataSetChanged();
        mPasswordProtected = false;
        showEnhancementOptions();
    }

    @Override
    public void mergeStarted() {
        mMaterialDialog = DialogUtils.getInstance().createAnimationDialog(mActivity);
        mMaterialDialog.show();
    }

    @Override
    public void viewFile(String path) {
        mFileUtils.openFile(path, FileUtils.FileType.e_PDF);
    }

    @Override
    public void removeFile(String path) {
        mFilePaths.remove(path);
        mMergeSelectedFilesAdapter.notifyDataSetChanged();
        StringUtils.getInstance().showSnackbar(mActivity, getString(R.string.pdf_removed_from_list));
        if (mFilePaths.size() < 2 && mergeBtn.isEnabled())
            setMorphingButtonState(false);
    }

    @Override
    public void moveUp(int position) {
        Collections.swap(mFilePaths, position, position - 1);
        mMergeSelectedFilesAdapter.notifyDataSetChanged();
    }

    @Override
    public void moveDown(int position) {
        Collections.swap(mFilePaths, position, position + 1);
        mMergeSelectedFilesAdapter.notifyDataSetChanged();
    }

    private void setMorphingButtonState(Boolean enabled) {
        if (enabled.equals(true))
            mMorphButtonUtility.morphToSquare(mergeBtn, mMorphButtonUtility.integer());
        else
            mMorphButtonUtility.morphToGrey(mergeBtn, mMorphButtonUtility.integer());

        mergeBtn.setEnabled(enabled);
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
    public boolean onBackPressed() {

        //IOnBackPressed,

        if (mFilePaths.size()!=0) {
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
            //mFilePaths = null;
            return false;
        }
    }

    private void addMoreHeight(){


//        midCard1stWhite.setMinimumHeight(midCard1stWhite.getHeight()+100);
        //midCard1stWhite.setLayoutParams(new RelativeLayout.LayoutParams(MATCH_PARENT, 650));
        midCard1stWhite.setLayoutParams(new LinearLayout.LayoutParams(midCard1stWhite.getWidth(),midCard1stWhite.getHeight()+100));
    }
}
