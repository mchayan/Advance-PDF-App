// Generated code from Butter Knife. Do not modify!
package com.editmypdffree.rdtl.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
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

public class MergeFilesFragment_ViewBinding implements Unbinder {
  private MergeFilesFragment target;

  private View view7f0a0156;

  private View view7f0a0206;

  private View view7f0a018a;

  private View view7f0a0277;

  @UiThread
  public MergeFilesFragment_ViewBinding(final MergeFilesFragment target, View source) {
    this.target = target;

    View view;
    target.mLottieProgress = Utils.findRequiredViewAsType(source, R.id.lottie_progress, "field 'mLottieProgress'", LottieAnimationView.class);
    view = Utils.findRequiredView(source, R.id.mergebtn, "field 'mergeBtn' and method 'mergeFiles'");
    target.mergeBtn = Utils.castView(view, R.id.mergebtn, "field 'mergeBtn'", MorphingButton.class);
    view7f0a0156 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.mergeFiles(p0);
      }
    });
    target.mRecyclerViewFiles = Utils.findRequiredViewAsType(source, R.id.recyclerViewFiles, "field 'mRecyclerViewFiles'", RecyclerView.class);
    target.mUpArrow = Utils.findRequiredViewAsType(source, R.id.upArrow, "field 'mUpArrow'", ImageView.class);
    target.mDownArrow = Utils.findRequiredViewAsType(source, R.id.downArrow, "field 'mDownArrow'", ImageView.class);
    target.mLayout = Utils.findRequiredViewAsType(source, R.id.layout, "field 'mLayout'", RelativeLayout.class);
    target.layoutBottomSheet = Utils.findRequiredViewAsType(source, R.id.bottom_sheet, "field 'layoutBottomSheet'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.selectFiles, "field 'mSelectFiles' and method 'startAddingPDF'");
    target.mSelectFiles = Utils.castView(view, R.id.selectFiles, "field 'mSelectFiles'", Button.class);
    view7f0a0206 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.startAddingPDF(p0);
      }
    });
    target.mSelectedFiles = Utils.findRequiredViewAsType(source, R.id.selected_files, "field 'mSelectedFiles'", RecyclerView.class);
    target.mEnhancementOptionsRecycleView = Utils.findRequiredViewAsType(source, R.id.enhancement_options_recycle_view, "field 'mEnhancementOptionsRecycleView'", RecyclerView.class);
    target.mNestedScrollView = Utils.findRequiredViewAsType(source, R.id.idNestedSV, "field 'mNestedScrollView'", NestedScrollView.class);
    target.mrelativebtmcreate = Utils.findRequiredViewAsType(source, R.id.relativebtmcreate, "field 'mrelativebtmcreate'", RelativeLayout.class);
    target.popup1s = Utils.findRequiredViewAsType(source, R.id.popup, "field 'popup1s'", LinearLayout.class);
    target.popup2n = Utils.findRequiredViewAsType(source, R.id.popup2, "field 'popup2n'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.openpdf, "field 'mopenpdf' and method 'opnpdf'");
    target.mopenpdf = Utils.castView(view, R.id.openpdf, "field 'mopenpdf'", MorphingButton.class);
    view7f0a018a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.opnpdf();
      }
    });
    target.midCard1stWhite = Utils.findRequiredViewAsType(source, R.id.idCard1stWhite, "field 'midCard1stWhite'", CardView.class);
    view = Utils.findRequiredView(source, R.id.viewFiles, "method 'onViewFilesClick'");
    view7f0a0277 = view;
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
    MergeFilesFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLottieProgress = null;
    target.mergeBtn = null;
    target.mRecyclerViewFiles = null;
    target.mUpArrow = null;
    target.mDownArrow = null;
    target.mLayout = null;
    target.layoutBottomSheet = null;
    target.mSelectFiles = null;
    target.mSelectedFiles = null;
    target.mEnhancementOptionsRecycleView = null;
    target.mNestedScrollView = null;
    target.mrelativebtmcreate = null;
    target.popup1s = null;
    target.popup2n = null;
    target.mopenpdf = null;
    target.midCard1stWhite = null;

    view7f0a0156.setOnClickListener(null);
    view7f0a0156 = null;
    view7f0a0206.setOnClickListener(null);
    view7f0a0206 = null;
    view7f0a018a.setOnClickListener(null);
    view7f0a018a = null;
    view7f0a0277.setOnClickListener(null);
    view7f0a0277 = null;
  }
}
