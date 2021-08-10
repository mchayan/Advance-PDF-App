// Generated code from Butter Knife. Do not modify!
package com.editmypdffree.rdtl.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.airbnb.lottie.LottieAnimationView;
import com.dd.morphingbutton.MorphingButton;
import com.editmypdffree.rdtl.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddTextFragment_ViewBinding implements Unbinder {
  private AddTextFragment target;

  private View view7f0a0207;

  private View view7f0a020a;

  private View view7f0a01ac;

  private View view7f0a0186;

  private View view7f0a0273;

  @UiThread
  public AddTextFragment_ViewBinding(final AddTextFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.select_pdf_file, "field 'mSelectPDF' and method 'showPdfFileChooser'");
    target.mSelectPDF = Utils.castView(view, R.id.select_pdf_file, "field 'mSelectPDF'", MorphingButton.class);
    view7f0a0207 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showPdfFileChooser();
      }
    });
    view = Utils.findRequiredView(source, R.id.select_text_file, "field 'mSelectText' and method 'showTextFileChooser'");
    target.mSelectText = Utils.castView(view, R.id.select_text_file, "field 'mSelectText'", MorphingButton.class);
    view7f0a020a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showTextFileChooser();
      }
    });
    target.mCreateTextPDF = Utils.findRequiredViewAsType(source, R.id.create_pdf_added_text, "field 'mCreateTextPDF'", MorphingButton.class);
    view = Utils.findRequiredView(source, R.id.pdfCreate, "field 'mpdfCreate' and method 'openPdfNameDialog'");
    target.mpdfCreate = Utils.castView(view, R.id.pdfCreate, "field 'mpdfCreate'", MorphingButton.class);
    view7f0a01ac = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.openPdfNameDialog();
      }
    });
    target.layoutBottomSheet = Utils.findRequiredViewAsType(source, R.id.bottom_sheet, "field 'layoutBottomSheet'", LinearLayout.class);
    target.mRecyclerViewFiles = Utils.findRequiredViewAsType(source, R.id.recyclerViewFiles, "field 'mRecyclerViewFiles'", RecyclerView.class);
    target.mUpArrow = Utils.findRequiredViewAsType(source, R.id.upArrow, "field 'mUpArrow'", ImageView.class);
    target.mLayout = Utils.findRequiredViewAsType(source, R.id.layout, "field 'mLayout'", RelativeLayout.class);
    target.mLottieProgress = Utils.findRequiredViewAsType(source, R.id.lottie_progress, "field 'mLottieProgress'", LottieAnimationView.class);
    target.mTextEnhancementOptionsRecycleView = Utils.findRequiredViewAsType(source, R.id.enhancement_options_recycle_view_text, "field 'mTextEnhancementOptionsRecycleView'", RecyclerView.class);
    target.mfileLocation = Utils.findRequiredViewAsType(source, R.id.fileLocation, "field 'mfileLocation'", TextView.class);
    target.midlocLL = Utils.findRequiredViewAsType(source, R.id.idlocLL, "field 'midlocLL'", LinearLayout.class);
    target.mfileLocation2 = Utils.findRequiredViewAsType(source, R.id.fileLocation2, "field 'mfileLocation2'", TextView.class);
    target.midlocLL2 = Utils.findRequiredViewAsType(source, R.id.idlocLL2, "field 'midlocLL2'", LinearLayout.class);
    target.midCard1stWhite = Utils.findRequiredViewAsType(source, R.id.idCard1stWhite, "field 'midCard1stWhite'", CardView.class);
    target.popup2View = Utils.findRequiredViewAsType(source, R.id.popup2, "field 'popup2View'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.openpdf, "field 'openpdfTXt' and method 'openmypsf'");
    target.openpdfTXt = Utils.castView(view, R.id.openpdf, "field 'openpdfTXt'", MorphingButton.class);
    view7f0a0186 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.openmypsf();
      }
    });
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
    AddTextFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mSelectPDF = null;
    target.mSelectText = null;
    target.mCreateTextPDF = null;
    target.mpdfCreate = null;
    target.layoutBottomSheet = null;
    target.mRecyclerViewFiles = null;
    target.mUpArrow = null;
    target.mLayout = null;
    target.mLottieProgress = null;
    target.mTextEnhancementOptionsRecycleView = null;
    target.mfileLocation = null;
    target.midlocLL = null;
    target.mfileLocation2 = null;
    target.midlocLL2 = null;
    target.midCard1stWhite = null;
    target.popup2View = null;
    target.openpdfTXt = null;

    view7f0a0207.setOnClickListener(null);
    view7f0a0207 = null;
    view7f0a020a.setOnClickListener(null);
    view7f0a020a = null;
    view7f0a01ac.setOnClickListener(null);
    view7f0a01ac = null;
    view7f0a0186.setOnClickListener(null);
    view7f0a0186 = null;
    view7f0a0273.setOnClickListener(null);
    view7f0a0273 = null;
  }
}
