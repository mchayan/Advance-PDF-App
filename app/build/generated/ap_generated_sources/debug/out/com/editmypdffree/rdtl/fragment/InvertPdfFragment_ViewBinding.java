// Generated code from Butter Knife. Do not modify!
package com.editmypdffree.rdtl.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.airbnb.lottie.LottieAnimationView;
import com.dd.morphingbutton.MorphingButton;
import com.editmypdffree.rdtl.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class InvertPdfFragment_ViewBinding implements Unbinder {
  private InvertPdfFragment target;

  private View view7f0a0200;

  private View view7f0a0120;

  private View view7f0a0273;

  @UiThread
  public InvertPdfFragment_ViewBinding(final InvertPdfFragment target, View source) {
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
    view = Utils.findRequiredView(source, R.id.invert, "field 'invertPdfButton' and method 'parse'");
    target.invertPdfButton = Utils.castView(view, R.id.invert, "field 'invertPdfButton'", MorphingButton.class);
    view7f0a0120 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.parse();
      }
    });
    target.layoutBottomSheet = Utils.findRequiredViewAsType(source, R.id.bottom_sheet, "field 'layoutBottomSheet'", LinearLayout.class);
    target.mUpArrow = Utils.findRequiredViewAsType(source, R.id.upArrow, "field 'mUpArrow'", ImageView.class);
    target.mLayout = Utils.findRequiredViewAsType(source, R.id.layout, "field 'mLayout'", RelativeLayout.class);
    target.mRecyclerViewFiles = Utils.findRequiredViewAsType(source, R.id.recyclerViewFiles, "field 'mRecyclerViewFiles'", RecyclerView.class);
    target.mViewPdf = Utils.findRequiredViewAsType(source, R.id.view_pdf, "field 'mViewPdf'", Button.class);
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
    InvertPdfFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLottieProgress = null;
    target.selectFileButton = null;
    target.invertPdfButton = null;
    target.layoutBottomSheet = null;
    target.mUpArrow = null;
    target.mLayout = null;
    target.mRecyclerViewFiles = null;
    target.mViewPdf = null;

    view7f0a0200.setOnClickListener(null);
    view7f0a0200 = null;
    view7f0a0120.setOnClickListener(null);
    view7f0a0120 = null;
    view7f0a0273.setOnClickListener(null);
    view7f0a0273 = null;
  }
}
