// Generated code from Butter Knife. Do not modify!
package com.editmypdffree.rdtl.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.editmypdffree.rdtl.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ImageFiltersAdapter$ViewHolder_ViewBinding implements Unbinder {
  private ImageFiltersAdapter.ViewHolder target;

  @UiThread
  public ImageFiltersAdapter$ViewHolder_ViewBinding(ImageFiltersAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.img = Utils.findRequiredViewAsType(source, R.id.filter_preview, "field 'img'", ImageView.class);
    target.name = Utils.findRequiredViewAsType(source, R.id.filter_Name, "field 'name'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ImageFiltersAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.img = null;
    target.name = null;
  }
}
