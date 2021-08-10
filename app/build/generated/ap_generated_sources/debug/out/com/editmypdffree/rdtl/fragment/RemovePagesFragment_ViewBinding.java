// Generated code from Butter Knife. Do not modify!
package com.editmypdffree.rdtl.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RemovePagesFragment_ViewBinding implements Unbinder {
  private RemovePagesFragment target;

  private View view7f0a0203;

  private View view7f0a01af;

  private View view7f0a0189;

  private View view7f0a0276;

  @UiThread
  public RemovePagesFragment_ViewBinding(final RemovePagesFragment target, View source) {
    this.target = target;

    View view;
    target.mLottieProgress = Utils.findRequiredViewAsType(source, R.id.lottie_progress, "field 'mLottieProgress'", LottieAnimationView.class);
    view = Utils.findRequiredView(source, R.id.selectFile, "field 'selectFileButton' and method 'showFileChooser'");
    target.selectFileButton = Utils.castView(view, R.id.selectFile, "field 'selectFileButton'", MorphingButton.class);
    view7f0a0203 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showFileChooser();
      }
    });
    view = Utils.findRequiredView(source, R.id.pdfCreate, "field 'createPdf' and method 'parse'");
    target.createPdf = Utils.castView(view, R.id.pdfCreate, "field 'createPdf'", MorphingButton.class);
    view7f0a01af = view;
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
    target.pagesInput = Utils.findRequiredViewAsType(source, R.id.pages, "field 'pagesInput'", EditText.class);
    target.mRecyclerViewFiles = Utils.findRequiredViewAsType(source, R.id.recyclerViewFiles, "field 'mRecyclerViewFiles'", RecyclerView.class);
    target.mInfoText = Utils.findRequiredViewAsType(source, R.id.infoText, "field 'mInfoText'", TextView.class);
    target.mCompressionInfoText = Utils.findRequiredViewAsType(source, R.id.compressionInfoText, "field 'mCompressionInfoText'", TextView.class);
    target.mViewPdf = Utils.findRequiredViewAsType(source, R.id.view_pdf, "field 'mViewPdf'", Button.class);
    target.tvloctionofpdf = Utils.findRequiredViewAsType(source, R.id.idloctionofpdf, "field 'tvloctionofpdf'", TextView.class);
    target.EdTxtCard = Utils.findRequiredViewAsType(source, R.id.idEdTxtCard, "field 'EdTxtCard'", CardView.class);
    target.loccationLV = Utils.findRequiredViewAsType(source, R.id.idLocationLV, "field 'loccationLV'", LinearLayout.class);
    target.choosefilePP = Utils.findRequiredViewAsType(source, R.id.popup, "field 'choosefilePP'", LinearLayout.class);
    target.openpdfPP = Utils.findRequiredViewAsType(source, R.id.popup2, "field 'openpdfPP'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.openpdf, "field 'opnPDF' and method 'opnmypdf'");
    target.opnPDF = Utils.castView(view, R.id.openpdf, "field 'opnPDF'", MorphingButton.class);
    view7f0a0189 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.opnmypdf();
      }
    });
    view = Utils.findRequiredView(source, R.id.viewFiles, "method 'onViewFilesClick'");
    view7f0a0276 = view;
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
    RemovePagesFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLottieProgress = null;
    target.selectFileButton = null;
    target.createPdf = null;
    target.layoutBottomSheet = null;
    target.mUpArrow = null;
    target.mDownArrow = null;
    target.mLayout = null;
    target.pagesInput = null;
    target.mRecyclerViewFiles = null;
    target.mInfoText = null;
    target.mCompressionInfoText = null;
    target.mViewPdf = null;
    target.tvloctionofpdf = null;
    target.EdTxtCard = null;
    target.loccationLV = null;
    target.choosefilePP = null;
    target.openpdfPP = null;
    target.opnPDF = null;

    view7f0a0203.setOnClickListener(null);
    view7f0a0203 = null;
    view7f0a01af.setOnClickListener(null);
    view7f0a01af = null;
    view7f0a0189.setOnClickListener(null);
    view7f0a0189 = null;
    view7f0a0276.setOnClickListener(null);
    view7f0a0276 = null;
  }
}
