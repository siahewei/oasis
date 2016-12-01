package cn.com.earth.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedHashSet;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/11/28 下午6:54
 */

public class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    private View contentView;
    private SparseArray<View> views;
    private LinkedHashSet<Integer> childClickViewIds;
    private LinkedHashSet<Integer> childLongClickViewIds;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.contentView = itemView;
        views = new SparseArray<>();
        childClickViewIds = new LinkedHashSet<>();
        childLongClickViewIds = new LinkedHashSet<>();
    }

    public LinkedHashSet<Integer> getChildClickViewIds() {
        return childClickViewIds;
    }

    public LinkedHashSet<Integer> getChildLongClickViewIds() {
        return childLongClickViewIds;
    }

    // tools
    public boolean bindData(T data, int viewPos, int realPos) {
        return false;
    }

    public BaseViewHolder<T> setText(int viewId, CharSequence value) {
        TextView view = getView(viewId);
        if (view != null) {
            view.setText(value);
        }

        return this;
    }

    public BaseViewHolder<T> setText(int viewId, @StringRes int strId) {
        TextView view = getView(viewId);
        if (view != null) {
            view.setText(strId);
        }

        return this;
    }

    public BaseViewHolder<T> setImageResource(int viewId, @DrawableRes int imageResId) {
        ImageView imageView = getView(viewId);
        if (imageView != null) {
            imageView.setImageResource(imageResId);
        }

        return this;
    }

    public BaseViewHolder<T> setBackgroudColor(int viewId, int color) {
        View view = getView(viewId);
        if (view != null) {
            view.setBackgroundColor(color);
        }
        return this;
    }

    public BaseViewHolder<T> setBackgroundRes(int viewId, @DrawableRes int backgroundRes) {
        View view = getView(viewId);
        if (view != null) {
            view.setBackgroundResource(backgroundRes);
        }
        return this;
    }

    public BaseViewHolder<T> setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        if (view != null) {
            view.setTextColor(textColor);
        }
        return this;
    }

    public BaseViewHolder<T> setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        if (view != null) {
            view.setImageDrawable(drawable);
        }
        return this;
    }

    public BaseViewHolder<T> setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public BaseViewHolder<T> setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        if (view != null) {
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public BaseViewHolder<T> setChecked(int viewId, boolean checked) {
        View view = getView(viewId);
        // View unable cast to Checkable
        if (view instanceof CompoundButton) {
            ((CompoundButton) view).setChecked(checked);
        } else if (view instanceof CheckedTextView) {
            ((CheckedTextView) view).setChecked(checked);
        }
        return this;
    }

    public  <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = contentView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

}
