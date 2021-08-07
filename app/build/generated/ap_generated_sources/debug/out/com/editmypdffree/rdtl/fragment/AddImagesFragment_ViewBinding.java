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

public class AddImagesFragment_ViewBinding implements Unbinder {
  private AddImagesFragment target;

  private View view7f0a01fe;

  private View view7f0a01aa;

  private View view7f0a004c;

  @UiThread
  public AddImagesFragment_ViewBinding(final AddImagesFragment target, View source) {
    this.target = target;

    View view;
    target.mLottieProgress = Utils.findRequiredViewAsType(source, R.id.lottie_progress, "field 'mLottieProgress'", LottieAnimationView.class);
    view = Utils.findRequiredView(source, R.id.selectFile, "field 'selectFileButton' and method 'showFileChooser'");
    target.selectFileButton = Utils.castView(view, R.id.selectFile, "field 'selectFileButton'", MorphingButton.class);
    view7f0a01fe = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showFileChooser();
      }
    });
    view = Utils.findRequiredView(source, R.id.pdfCreate, "field 'mCreatePdf' and method 'parse'");
    target.mCreatePdf = Utils.castView(view, R.id.pdfCreate, "field 'mCreatePdf'", MorphingButton.class);
    view7f0a01aa = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.parse();
      }
    });
    view = Utils.findRequiredView(source, R.id.addImages, "field 'addImages' and method 'startAddingImages'");
    target.addImages = Utils.castView(view, R.id.addImages, "field 'addImages'", MorphingButton.class);
    view7f0a004c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.startAddingImages();
      }
    });
    target.layoutBottomSheet = Utils.findRequiredViewAsType(source, R.id.bottom_sheet, "field 'layoutBottomSheet'", LinearLayout.class);
    target.mUpArrow = Utils.findRequiredViewAsType(source, R.id.upArrow, "field 'mUpArrow'", ImageView.class);
    target.mLayout = Utils.findRequiredViewAsType(source, R.id.layout, "field 'mLayout'", RelativeLayout.class);
    target.mRecyclerViewFiles = Utils.findRequiredViewAsType(source, R.id.recyclerViewFiles, "field 'mRecyclerViewFiles'", RecyclerView.class);
    target.mNoOfImages = Utils.findRequiredViewAsType(source, R.id.tvNoOfImages, "field 'mNoOfImages'", TextView.class);
    target.selectImageLL = Utils.findRequiredViewAsType(source, R.id.idSelectImgLL, "field 'selectImageLL'", LinearLayout.class);
    target.PopUp = Utils.findRequiredViewAsType(source, R.id.popup, "field 'PopUp'", LinearLayout.class);
    target.rltvall = Utils.findRequiredViewAsType(source, R.id.rltvall, "field 'rltvall'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddImagesFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLottieProgress = null;
    target.selectFileButton = null;
    target.mCreatePdf = null;
    target.addImages = null;
    target.layoutBottomSheet = null;
    target.mUpArrow = null;
    target.mLayout = null;
    target.mRecyclerViewFiles = null;
    target.mNoOfImages = null;
    target.selectImageLL = null;
    target.PopUp = null;
    target.rltvall = null;

    view7f0a01fe.setOnClickListener(null);
    view7f0a01fe = null;
    view7f0a01aa.setOnClickListener(null);
    view7f0a01aa = null;
    view7f0a004c.setOnClickListener(null);
    view7f0a004c = null;
  }
}
