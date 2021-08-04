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

public class ExceltoPdfFragment_ViewBinding implements Unbinder {
  private ExceltoPdfFragment target;

  private View view7f0a0182;

  private View view7f0a009a;

  private View view7f0a0202;

  private View view7f0a0203;

  @UiThread
  public ExceltoPdfFragment_ViewBinding(final ExceltoPdfFragment target, View source) {
    this.target = target;

    View view;
    target.mLottieProgress = Utils.findRequiredViewAsType(source, R.id.lottie_progress, "field 'mLottieProgress'", LottieAnimationView.class);
    target.mTextView = Utils.findRequiredViewAsType(source, R.id.tv_excel_file_name_bottom, "field 'mTextView'", TextView.class);
    view = Utils.findRequiredView(source, R.id.open_pdf, "field 'mOpenPdf' and method 'openPdf'");
    target.mOpenPdf = Utils.castView(view, R.id.open_pdf, "field 'mOpenPdf'", MorphingButton.class);
    view7f0a0182 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.openPdf();
      }
    });
    view = Utils.findRequiredView(source, R.id.create_excel_to_pdf, "field 'mCreateExcelPdf' and method 'openExcelToPdf'");
    target.mCreateExcelPdf = Utils.castView(view, R.id.create_excel_to_pdf, "field 'mCreateExcelPdf'", MorphingButton.class);
    view7f0a009a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.openExcelToPdf();
      }
    });
    target.mEnhancementOptionsRecycleView = Utils.findRequiredViewAsType(source, R.id.enhancement_options_recycle_view, "field 'mEnhancementOptionsRecycleView'", RecyclerView.class);
    target.layoutBottomSheet = Utils.findRequiredViewAsType(source, R.id.bottom_sheet, "field 'layoutBottomSheet'", LinearLayout.class);
    target.mUpArrow = Utils.findRequiredViewAsType(source, R.id.upArrow, "field 'mUpArrow'", ImageView.class);
    target.mLayout = Utils.findRequiredViewAsType(source, R.id.layout, "field 'mLayout'", RelativeLayout.class);
    target.mRecyclerViewFiles = Utils.findRequiredViewAsType(source, R.id.recyclerViewFiles, "field 'mRecyclerViewFiles'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.select_excel_file, "field 'selectexlfile' and method 'selectExcelFile'");
    target.selectexlfile = Utils.castView(view, R.id.select_excel_file, "field 'selectexlfile'", MorphingButton.class);
    view7f0a0202 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectExcelFile();
      }
    });
    view = Utils.findRequiredView(source, R.id.select_excel_file2, "field 'selectexlfile2' and method 'selectExcelFile2'");
    target.selectexlfile2 = Utils.castView(view, R.id.select_excel_file2, "field 'selectexlfile2'", MorphingButton.class);
    view7f0a0203 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectExcelFile2();
      }
    });
    target.PopUp = Utils.findRequiredViewAsType(source, R.id.popup, "field 'PopUp'", LinearLayout.class);
    target.PopUp2 = Utils.findRequiredViewAsType(source, R.id.popup2, "field 'PopUp2'", LinearLayout.class);
    target.rltvall = Utils.findRequiredViewAsType(source, R.id.rltvall, "field 'rltvall'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ExceltoPdfFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLottieProgress = null;
    target.mTextView = null;
    target.mOpenPdf = null;
    target.mCreateExcelPdf = null;
    target.mEnhancementOptionsRecycleView = null;
    target.layoutBottomSheet = null;
    target.mUpArrow = null;
    target.mLayout = null;
    target.mRecyclerViewFiles = null;
    target.selectexlfile = null;
    target.selectexlfile2 = null;
    target.PopUp = null;
    target.PopUp2 = null;
    target.rltvall = null;

    view7f0a0182.setOnClickListener(null);
    view7f0a0182 = null;
    view7f0a009a.setOnClickListener(null);
    view7f0a009a = null;
    view7f0a0202.setOnClickListener(null);
    view7f0a0202 = null;
    view7f0a0203.setOnClickListener(null);
    view7f0a0203 = null;
  }
}
