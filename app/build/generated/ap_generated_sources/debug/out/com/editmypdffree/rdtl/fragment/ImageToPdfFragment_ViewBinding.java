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
import com.dd.morphingbutton.MorphingButton;
import com.editmypdffree.rdtl.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ImageToPdfFragment_ViewBinding implements Unbinder {
  private ImageToPdfFragment target;

  private View view7f0a01a9;

  private View view7f0a01aa;

  private View view7f0a004c;

  @UiThread
  public ImageToPdfFragment_ViewBinding(final ImageToPdfFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.pdfCreate, "field 'mCreatePdf' and method 'pdfCreateClicked'");
    target.mCreatePdf = Utils.castView(view, R.id.pdfCreate, "field 'mCreatePdf'", MorphingButton.class);
    view7f0a01a9 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.pdfCreateClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.pdfOpen, "field 'mOpenPdf' and method 'openPdf'");
    target.mOpenPdf = Utils.castView(view, R.id.pdfOpen, "field 'mOpenPdf'", MorphingButton.class);
    view7f0a01aa = view;
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
    target.addImages2 = Utils.findRequiredViewAsType(source, R.id.addImages2, "field 'addImages2'", MorphingButton.class);
    target.AddImageBtn = Utils.findRequiredViewAsType(source, R.id.AddImageBtn, "field 'AddImageBtn'", ImageView.class);
    target.rlBtmCreate = Utils.findRequiredViewAsType(source, R.id.relativebtmcreate, "field 'rlBtmCreate'", RelativeLayout.class);
    target.PopUp = Utils.findRequiredViewAsType(source, R.id.popup, "field 'PopUp'", LinearLayout.class);
    target.rltvall = Utils.findRequiredViewAsType(source, R.id.rltvall, "field 'rltvall'", RelativeLayout.class);
    target.adition = Utils.findRequiredViewAsType(source, R.id.adition, "field 'adition'", TextView.class);
    target.view = Utils.findRequiredView(source, R.id.view, "field 'view'");
    target.openpdf = Utils.findRequiredViewAsType(source, R.id.popup2, "field 'openpdf'", LinearLayout.class);
    target.btnC1 = Utils.findRequiredViewAsType(source, R.id.idbtnCard1st, "field 'btnC1'", CardView.class);
    target.btnC2 = Utils.findRequiredViewAsType(source, R.id.idbtnCard2nd, "field 'btnC2'", CardView.class);
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
    target.addImages2 = null;
    target.AddImageBtn = null;
    target.rlBtmCreate = null;
    target.PopUp = null;
    target.rltvall = null;
    target.adition = null;
    target.view = null;
    target.openpdf = null;
    target.btnC1 = null;
    target.btnC2 = null;

    view7f0a01a9.setOnClickListener(null);
    view7f0a01a9 = null;
    view7f0a01aa.setOnClickListener(null);
    view7f0a01aa = null;
    view7f0a004c.setOnClickListener(null);
    view7f0a004c = null;
  }
}
