// Generated code from Butter Knife. Do not modify!
package com.editmypdffree.rdtl.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.editmypdffree.rdtl.R;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CropImageActivity_ViewBinding implements Unbinder {
  private CropImageActivity target;

  private View view7f0a009e;

  private View view7f0a01e0;

  private View view7f0a0178;

  private View view7f0a01b3;

  @UiThread
  public CropImageActivity_ViewBinding(CropImageActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CropImageActivity_ViewBinding(final CropImageActivity target, View source) {
    this.target = target;

    View view;
    target.mImageCount = Utils.findRequiredViewAsType(source, R.id.imagecount, "field 'mImageCount'", TextView.class);
    target.mCropImageView = Utils.findRequiredViewAsType(source, R.id.cropImageView, "field 'mCropImageView'", CropImageView.class);
    view = Utils.findRequiredView(source, R.id.cropButton, "field 'cropImageButton' and method 'cropButtonClicked'");
    target.cropImageButton = Utils.castView(view, R.id.cropButton, "field 'cropImageButton'", Button.class);
    view7f0a009e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.cropButtonClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.rotateButton, "method 'rotateButtonClicked'");
    view7f0a01e0 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.rotateButtonClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.nextimageButton, "method 'nextImageClicked'");
    view7f0a0178 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.nextImageClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.previousImageButton, "method 'prevImgBtnClicked'");
    view7f0a01b3 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.prevImgBtnClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    CropImageActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImageCount = null;
    target.mCropImageView = null;
    target.cropImageButton = null;

    view7f0a009e.setOnClickListener(null);
    view7f0a009e = null;
    view7f0a01e0.setOnClickListener(null);
    view7f0a01e0 = null;
    view7f0a0178.setOnClickListener(null);
    view7f0a0178 = null;
    view7f0a01b3.setOnClickListener(null);
    view7f0a01b3 = null;
  }
}
