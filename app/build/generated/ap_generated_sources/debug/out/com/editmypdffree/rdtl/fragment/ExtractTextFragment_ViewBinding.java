// Generated code from Butter Knife. Do not modify!
package com.editmypdffree.rdtl.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

public class ExtractTextFragment_ViewBinding implements Unbinder {
  private ExtractTextFragment target;

  private View view7f0a00d5;

  private View view7f0a0271;

  private View view7f0a0205;

  @UiThread
  public ExtractTextFragment_ViewBinding(final ExtractTextFragment target, View source) {
    this.target = target;

    View view;
    target.mTextView = Utils.findRequiredViewAsType(source, R.id.tv_extract_text_bottom, "field 'mTextView'", TextView.class);
    view = Utils.findRequiredView(source, R.id.extract_text, "field 'extractText' and method 'openExtractText'");
    target.extractText = Utils.castView(view, R.id.extract_text, "field 'extractText'", MorphingButton.class);
    view7f0a00d5 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.openExtractText();
      }
    });
    target.layoutBottomSheet = Utils.findRequiredViewAsType(source, R.id.bottom_sheet, "field 'layoutBottomSheet'", LinearLayout.class);
    target.mRecyclerViewFiles = Utils.findRequiredViewAsType(source, R.id.recyclerViewFiles, "field 'mRecyclerViewFiles'", RecyclerView.class);
    target.mUpArrow = Utils.findRequiredViewAsType(source, R.id.upArrow, "field 'mUpArrow'", ImageView.class);
    target.mLayout = Utils.findRequiredViewAsType(source, R.id.layout, "field 'mLayout'", RelativeLayout.class);
    target.mLottieProgress = Utils.findRequiredViewAsType(source, R.id.lottie_progress, "field 'mLottieProgress'", LottieAnimationView.class);
    view = Utils.findRequiredView(source, R.id.viewFiles, "method 'onViewFilesClick'");
    view7f0a0271 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewFilesClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.select_pdf_file, "method 'selectPdfFile'");
    view7f0a0205 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectPdfFile();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ExtractTextFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTextView = null;
    target.extractText = null;
    target.layoutBottomSheet = null;
    target.mRecyclerViewFiles = null;
    target.mUpArrow = null;
    target.mLayout = null;
    target.mLottieProgress = null;

    view7f0a00d5.setOnClickListener(null);
    view7f0a00d5 = null;
    view7f0a0271.setOnClickListener(null);
    view7f0a0271 = null;
    view7f0a0205.setOnClickListener(null);
    view7f0a0205 = null;
  }
}
