// Generated code from Butter Knife. Do not modify!
package com.editmypdffree.rdtl.fragment.texttopdf;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dd.morphingbutton.MorphingButton;
import com.editmypdffree.rdtl.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TextToPdfFragment_ViewBinding implements Unbinder {
  private TextToPdfFragment target;

  private View view7f0a0203;

  private View view7f0a009d;

  private View view7f0a0204;

  @UiThread
  public TextToPdfFragment_ViewBinding(final TextToPdfFragment target, View source) {
    this.target = target;

    View view;
    target.mTextEnhancementOptionsRecycleView = Utils.findRequiredViewAsType(source, R.id.enhancement_options_recycle_view_text, "field 'mTextEnhancementOptionsRecycleView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.selectFile, "field 'mSelectFile' and method 'selectTextFile'");
    target.mSelectFile = Utils.castView(view, R.id.selectFile, "field 'mSelectFile'", MorphingButton.class);
    view7f0a0203 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectTextFile();
      }
    });
    view = Utils.findRequiredView(source, R.id.createtextpdf, "field 'mCreateTextPdf' and method 'openCreateTextPdf'");
    target.mCreateTextPdf = Utils.castView(view, R.id.createtextpdf, "field 'mCreateTextPdf'", MorphingButton.class);
    view7f0a009d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.openCreateTextPdf();
      }
    });
    target.view = Utils.findRequiredView(source, R.id.view, "field 'view'");
    target.tvAdi = Utils.findRequiredViewAsType(source, R.id.aditionid, "field 'tvAdi'", TextView.class);
    view = Utils.findRequiredView(source, R.id.selectFile2, "field 'mSelectFile2' and method 'selectTextFile2'");
    target.mSelectFile2 = Utils.castView(view, R.id.selectFile2, "field 'mSelectFile2'", MorphingButton.class);
    view7f0a0204 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectTextFile2();
      }
    });
    target.popup = Utils.findRequiredViewAsType(source, R.id.popup, "field 'popup'", LinearLayout.class);
    target.popup2 = Utils.findRequiredViewAsType(source, R.id.popup2, "field 'popup2'", LinearLayout.class);
    target.NestedScrollView = Utils.findRequiredViewAsType(source, R.id.NestedScrollView, "field 'NestedScrollView'", NestedScrollView.class);
    target.openpdf = Utils.findRequiredViewAsType(source, R.id.openpdf, "field 'openpdf'", MorphingButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TextToPdfFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTextEnhancementOptionsRecycleView = null;
    target.mSelectFile = null;
    target.mCreateTextPdf = null;
    target.view = null;
    target.tvAdi = null;
    target.mSelectFile2 = null;
    target.popup = null;
    target.popup2 = null;
    target.NestedScrollView = null;
    target.openpdf = null;

    view7f0a0203.setOnClickListener(null);
    view7f0a0203 = null;
    view7f0a009d.setOnClickListener(null);
    view7f0a009d = null;
    view7f0a0204.setOnClickListener(null);
    view7f0a0204 = null;
  }
}
