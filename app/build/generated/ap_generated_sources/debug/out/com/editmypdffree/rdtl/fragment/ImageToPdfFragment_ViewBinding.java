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
import com.dd.morphingbutton.MorphingButton;
import com.editmypdffree.rdtl.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ImageToPdfFragment_ViewBinding implements Unbinder {
  private ImageToPdfFragment target;

  private View view7f0a019c;

  private View view7f0a019d;

  private View view7f0a004c;

  @UiThread
  public ImageToPdfFragment_ViewBinding(final ImageToPdfFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.pdfCreate, "field 'mCreatePdf' and method 'pdfCreateClicked'");
    target.mCreatePdf = Utils.castView(view, R.id.pdfCreate, "field 'mCreatePdf'", MorphingButton.class);
    view7f0a019c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.pdfCreateClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.pdfOpen, "field 'mOpenPdf' and method 'openPdf'");
    target.mOpenPdf = Utils.castView(view, R.id.pdfOpen, "field 'mOpenPdf'", MorphingButton.class);
    view7f0a019d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.openPdf();
      }
    });
    target.mEnhancementOptionsRecycleView = Utils.findRequiredViewAsType(source, R.id.enhancement_options_recycle_view, "field 'mEnhancementOptionsRecycleView'", RecyclerView.class);
    target.mNoOfImages = Utils.findRequiredViewAsType(source, R.id.tvNoOfImages, "field 'mNoOfImages'", TextView.class);
    view = Utils.findRequiredView(source, R.id.addImages, "field 'addImages' and method 'startAddingImages'");
    target.addImages = Utils.castView(view, R.id.addImages, "field 'addImages'", MorphingButton.class);
    view7f0a004c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.startAddingImages();
      }
    });
    target.AddImageBtn = Utils.findRequiredViewAsType(source, R.id.AddImageBtn, "field 'AddImageBtn'", ImageView.class);
    target.rlBtmCreate = Utils.findRequiredViewAsType(source, R.id.relativebtmcreate, "field 'rlBtmCreate'", RelativeLayout.class);
    target.PopUp = Utils.findRequiredViewAsType(source, R.id.popup, "field 'PopUp'", LinearLayout.class);
    target.rltvall = Utils.findRequiredViewAsType(source, R.id.rltvall, "field 'rltvall'", RelativeLayout.class);
    target.adition = Utils.findRequiredViewAsType(source, R.id.adition, "field 'adition'", TextView.class);
    target.view = Utils.findRequiredView(source, R.id.view, "field 'view'");
    target.openpdf = Utils.findRequiredViewAsType(source, R.id.openpdf, "field 'openpdf'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ImageToPdfFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mCreatePdf = null;
    target.mOpenPdf = null;
    target.mEnhancementOptionsRecycleView = null;
    target.mNoOfImages = null;
    target.addImages = null;
    target.AddImageBtn = null;
    target.rlBtmCreate = null;
    target.PopUp = null;
    target.rltvall = null;
    target.adition = null;
    target.view = null;
    target.openpdf = null;

    view7f0a019c.setOnClickListener(null);
    view7f0a019c = null;
    view7f0a019d.setOnClickListener(null);
    view7f0a019d = null;
    view7f0a004c.setOnClickListener(null);
    view7f0a004c = null;
  }
}
