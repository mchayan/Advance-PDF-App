package com.editmypdffree.rdtl.fragment.texttopdf;

import android.content.Context;
import androidx.annotation.NonNull;

import com.editmypdffree.rdtl.R;
import com.editmypdffree.rdtl.interfaces.Enhancer;
import com.editmypdffree.rdtl.model.EnhancementOptionsEntity;
import com.editmypdffree.rdtl.preferences.TextToPdfPreferences;
import com.editmypdffree.rdtl.util.PageSizeUtils;

/**
 * An {@link Enhancer} that lets you select page size.
 */
public class PageSizeEnhancer implements Enhancer {

    private final PageSizeUtils mPageSizeUtils;
    private final EnhancementOptionsEntity mEnhancementOptionsEntity;

    PageSizeEnhancer(@NonNull final Context context) {
        mPageSizeUtils = new PageSizeUtils(context);
        mEnhancementOptionsEntity = new EnhancementOptionsEntity(
                context, R.drawable.ic_image_size, R.string.set_page_size_text);

        PageSizeUtils.mPageSize = new TextToPdfPreferences(context).getPageSize();
    }

    @Override
    public void enhance() {
        mPageSizeUtils.showPageSizeDialog(false);
    }

    @Override
    public EnhancementOptionsEntity getEnhancementOptionsEntity() {
        return mEnhancementOptionsEntity;
    }

}
