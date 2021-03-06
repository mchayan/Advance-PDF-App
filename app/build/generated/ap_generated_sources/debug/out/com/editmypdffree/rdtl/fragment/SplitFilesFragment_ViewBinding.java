// Generated code from Butter Knife. Do not modify!
package com.editmypdffree.rdtl.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.airbnb.lottie.LottieAnimationView;
import com.dd.morphingbutton.MorphingButton;
import com.editmypdffree.rdtl.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SplitFilesFragment_ViewBinding implements Unbinder {
  private SplitFilesFragment target;

  private View view7f0a0200;

  private View view7f0a0223;

  private View view7f0a0273;

  @UiThread
  public SplitFilesFragment_ViewBinding(final SplitFilesFragment target, View source) {
    this.target = target;

    View view;
    target.mLottieProgress = Utils.findRequiredViewAsType(source, R.id.lottie_progress, "field 'mLottieProgress'", LottieAnimationView.class);
    view = Utils.findRequiredView(source, R.id.selectFile, "field 'selectFileButton' and method 'showFileChooser'");
    target.selectFileButton = Utils.castView(view, R.id.selectFile, "field 'selectFileButton'", MorphingButton.class);
    view7f0a0200 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showFileChooser();
      }
    });
    view = Utils.findRequiredView(source, R.id.splitFiles, "field 'splitFilesButton' and method 'parse'");
    target.splitFilesButton = Utils.castView(view, R.id.splitFiles, "field 'splitFilesButton'", MorphingButton.class);
    view7f0a0223 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.parse();
      }
    });
    target.layoutBottomSheet = Utils.findRequiredViewAsType(source, R.id.bottom_sheet, "field 'layoutBottomSheet'", LinearLayout.class);
    target.mUpArrow = Utils.findRequiredViewAsType(source, R.id.upArrow, "field 'mUpArrow'", ImageView.class);
    target.mDownArrow = Utils.findRequiredViewAsType(source, R.id.downArrow, "field 'mDownArrow'", ImageView.class);
    target.mLayout = Utils.findRequiredViewAsType(source, R.id.layout, "field 'mLayout'", RelativeLayout.class);
    target.mRecyclerViewFiles = Utils.findRequiredViewAsType(source, R.id.recyclerViewFiles, "field 'mRecyclerViewFiles'", RecyclerView.class);
    target.mSplittedFiles = Utils.findRequiredViewAsType(source, R.id.splitted_files, "field 'mSplittedFiles'", RecyclerView.class);
    target.splitFilesSuccessText = Utils.findRequiredViewAsType(source, R.id.splitfiles_text, "field 'splitFilesSuccessText'", TextView.class);
    target.mSplitConfitEditText = Utils.findRequiredViewAsType(source, R.id.split_config, "field 'mSplitConfitEditText'", EditText.class);
    target.mlocationtext = Utils.findRequiredViewAsType(source, R.id.locationtext, "field 'mlocationtext'", LinearLayout.class);
    target.mlcTxt = Utils.findRequiredViewAsType(source, R.id.tv_extract_text_bottom, "field 'mlcTxt'", TextView.class);
    target.mrelativebtmcreate = Utils.findRequiredViewAsType(source, R.id.relativebtmcreate, "field 'mrelativebtmcreate'", RelativeLayout.class);
    target.mpopup2 = Utils.findRequiredViewAsType(source, R.id.popup, "field 'mpopup2'", LinearLayout.class);
    target.midNstdSV = Utils.findRequiredViewAsType(source, R.id.idNstdSV, "field 'midNstdSV'", NestedScrollView.class);
    view = Utils.findRequiredView(source, R.id.viewFiles, "method 'onViewFilesClick'");
    view7f0a0273 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewFilesClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SplitFilesFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLottieProgress = null;
    target.selectFileButton = null;
    target.splitFilesButton = null;
    target.layoutBottomSheet = null;
    target.mUpArrow = null;
    target.mDownArrow = null;
    target.mLayout = null;
    target.mRecyclerViewFiles = null;
    target.mSplittedFiles = null;
    target.splitFilesSuccessText = null;
    target.mSplitConfitEditText = null;
    target.mlocationtext = null;
    target.mlcTxt = null;
    target.mrelativebtmcreate = null;
    target.mpopup2 = null;
    target.midNstdSV = null;

    view7f0a0200.setOnClickListener(null);
    view7f0a0200 = null;
    view7f0a0223.setOnClickListener(null);
    view7f0a0223 = null;
    view7f0a0273.setOnClickListener(null);
    view7f0a0273 = null;
  }
}
