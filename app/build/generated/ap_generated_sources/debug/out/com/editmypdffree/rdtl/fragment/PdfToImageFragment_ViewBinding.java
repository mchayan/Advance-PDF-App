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

public class PdfToImageFragment_ViewBinding implements Unbinder {
  private PdfToImageFragment target;

  private View view7f0a01f6;

  private View view7f0a0098;

  private View view7f0a026a;

  private View view7f0a0208;

  private View view7f0a0268;

  private View view7f0a0269;

  @UiThread
  public PdfToImageFragment_ViewBinding(final PdfToImageFragment target, View source) {
    this.target = target;

    View view;
    target.mLottieProgress = Utils.findRequiredViewAsType(source, R.id.lottie_progress, "field 'mLottieProgress'", LottieAnimationView.class);
    target.mLayoutBottomSheet = Utils.findRequiredViewAsType(source, R.id.bottom_sheet, "field 'mLayoutBottomSheet'", LinearLayout.class);
    target.mUpArrow = Utils.findRequiredViewAsType(source, R.id.upArrow, "field 'mUpArrow'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.selectFile, "field 'mSelectFileButton' and method 'showFileChooser'");
    target.mSelectFileButton = Utils.castView(view, R.id.selectFile, "field 'mSelectFileButton'", MorphingButton.class);
    view7f0a01f6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showFileChooser();
      }
    });
    view = Utils.findRequiredView(source, R.id.createImages, "field 'mCreateImagesButton' and method 'parse'");
    target.mCreateImagesButton = Utils.castView(view, R.id.createImages, "field 'mCreateImagesButton'", MorphingButton.class);
    view7f0a0098 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.parse();
      }
    });
    target.mCreatedImages = Utils.findRequiredViewAsType(source, R.id.created_images, "field 'mCreatedImages'", RecyclerView.class);
    target.mCreateImagesSuccessText = Utils.findRequiredViewAsType(source, R.id.pdfToImagesText, "field 'mCreateImagesSuccessText'", TextView.class);
    target.options = Utils.findRequiredViewAsType(source, R.id.options, "field 'options'", LinearLayout.class);
    target.mLayout = Utils.findRequiredViewAsType(source, R.id.layout, "field 'mLayout'", RelativeLayout.class);
    target.mRecyclerViewFiles = Utils.findRequiredViewAsType(source, R.id.recyclerViewFiles, "field 'mRecyclerViewFiles'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.viewImagesInGallery, "method 'onImagesInGalleryClick'");
    view7f0a026a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onImagesInGalleryClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.shareImages, "method 'onShareFilesClick'");
    view7f0a0208 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onShareFilesClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.viewFiles, "method 'onViewFilesClick'");
    view7f0a0268 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewFilesClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.viewImages, "method 'onViewImagesClicked'");
    view7f0a0269 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewImagesClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    PdfToImageFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLottieProgress = null;
    target.mLayoutBottomSheet = null;
    target.mUpArrow = null;
    target.mSelectFileButton = null;
    target.mCreateImagesButton = null;
    target.mCreatedImages = null;
    target.mCreateImagesSuccessText = null;
    target.options = null;
    target.mLayout = null;
    target.mRecyclerViewFiles = null;

    view7f0a01f6.setOnClickListener(null);
    view7f0a01f6 = null;
    view7f0a0098.setOnClickListener(null);
    view7f0a0098 = null;
    view7f0a026a.setOnClickListener(null);
    view7f0a026a = null;
    view7f0a0208.setOnClickListener(null);
    view7f0a0208 = null;
    view7f0a0268.setOnClickListener(null);
    view7f0a0268 = null;
    view7f0a0269.setOnClickListener(null);
    view7f0a0269 = null;
  }
}
