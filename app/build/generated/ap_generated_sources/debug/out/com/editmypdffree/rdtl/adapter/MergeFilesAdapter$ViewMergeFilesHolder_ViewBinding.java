// Generated code from Butter Knife. Do not modify!
package com.editmypdffree.rdtl.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatCheckBox;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.editmypdffree.rdtl.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MergeFilesAdapter$ViewMergeFilesHolder_ViewBinding implements Unbinder {
  private MergeFilesAdapter.ViewMergeFilesHolder target;

  private View view7f0a0129;

  @UiThread
  public MergeFilesAdapter$ViewMergeFilesHolder_ViewBinding(
      final MergeFilesAdapter.ViewMergeFilesHolder target, View source) {
    this.target = target;

    View view;
    target.mFileName = Utils.findRequiredViewAsType(source, R.id.fileName, "field 'mFileName'", TextView.class);
    target.mEncryptionImage = Utils.findRequiredViewAsType(source, R.id.encryptionImage, "field 'mEncryptionImage'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.itemMerge_checkbox, "field 'mCheckbox' and method 'onCheckboxClick'");
    target.mCheckbox = Utils.castView(view, R.id.itemMerge_checkbox, "field 'mCheckbox'", AppCompatCheckBox.class);
    view7f0a0129 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onCheckboxClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    MergeFilesAdapter.ViewMergeFilesHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mFileName = null;
    target.mEncryptionImage = null;
    target.mCheckbox = null;

    view7f0a0129.setOnClickListener(null);
    view7f0a0129 = null;
  }
}
