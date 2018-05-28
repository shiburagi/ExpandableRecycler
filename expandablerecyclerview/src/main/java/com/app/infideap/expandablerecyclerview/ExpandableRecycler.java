package com.app.infideap.expandablerecyclerview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

/**
 */
public class ExpandableRecycler extends RecyclerView {

    public ExpandableRecycler(Context context) {
        super(context);
    }

    public ExpandableRecycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandableRecycler(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public abstract static class Adapter<T extends ViewHolder> extends RecyclerView.Adapter<T> {


        private Drawable drawable;
        private int dividerColor = Color.parseColor("#7f8c8d");
        private boolean showToggle = true;
        private ToggleListener listener;

        @Override
        public abstract T onCreateViewHolder(ViewGroup parent, int viewType);

        @Override
        public void onBindViewHolder(T holder, int position) {

            holder.load(getChildCount(position), drawable, dividerColor, showToggle);
            holder.setToggleListener(listener);
            holder.setIsRecyclable(false);
            holder.expendableView.setInRecyclerView(true);
        }

        public void setToggleDrawable(Drawable drawable) {
            this.drawable = drawable;
        }

        public void setShowToggle(boolean show) {
            showToggle = show;
        }

        public void setDividerColor(int color) {
            this.dividerColor = color;
        }

        public abstract int getChildCount(int position);

        public void setToggleListener(ToggleListener listener) {
            this.listener = listener;
            notifyItemRangeChanged(0, getItemCount());
        }
    }


    public abstract static class ViewHolder extends RecyclerView.ViewHolder {

        final ExpandableLinearLayout expendableView;
        private final ImageView toggleView;
        private final FrameLayout parentView;
        private final Context context;
        private final LinearLayout childView;
        private final View line1View;
        private final View line2View;
        private ToggleListener listener;

        private Boolean isExpand = null;

        public ViewHolder(Context context, final ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.fragment_expandable, parent, false));

            this.context = context;

            parentView = (FrameLayout) itemView.findViewById(R.id.content_parent);
            childView = (LinearLayout) itemView.findViewById(R.id.content_child);
            expendableView = (ExpandableLinearLayout) itemView.findViewById(R.id.view_expendable);
            toggleView = (ImageView) itemView.findViewById(R.id.imageView_toggle);

            line1View = itemView.findViewById(R.id.line1);
            line2View = itemView.findViewById(R.id.line2);
            expendableView.setListener(new ExpandableLayoutListenerAdapter() {
                @Override
                public void onPreOpen() {
                    createRotateAnimator(toggleView, 0f, 180f).start();
                }

                @Override
                public void onPreClose() {
                    createRotateAnimator(toggleView, 180f, 0f).start();
                }

                @Override
                public void onOpened() {
                    if (listener != null) listener.onExpand(getAdapterPosition());
                }

                @Override
                public void onClosed() {
                    if (listener != null) listener.onCollapse(getAdapterPosition());
                }
            });
            parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expendableView.toggle();
                }
            });


        }


        public void toggle() {
            if (isExpand != null)
                expendableView.toggle();
            isExpand = !expendableView.isExpanded();

        }


        public void collapse() {
            if (isExpand != null)
                expendableView.collapse();
            isExpand = false;

        }


        public void expand() {
            if (isExpand != null)
                expendableView.expand();
            isExpand = true;

        }


        public boolean isExpanded() {
            return expendableView.isExpanded();
        }

        void load(int childCount, Drawable drawable, int dividerColor, boolean showToggle) {
            if (!showToggle)
                toggleView.setVisibility(GONE);
            else {
                if (drawable == null) {
                    drawable = ContextCompat.getDrawable(context, R.drawable.ic_expand_more_gray_24dp);
                }
                toggleView.setVisibility(VISIBLE);
                toggleView.setImageDrawable(drawable);
            }
            line1View.setBackgroundColor(dividerColor);
            line2View.setBackgroundColor(dividerColor);

            parentView.removeAllViews();
            parentView.addView(getView(context, parentView));
            childView.removeAllViews();
            for (int i = 0; i < childCount; i++) {
                childView.addView(getChildView(context, childView, i));
            }

            expendableView.setExpanded(isExpand != null && isExpand);

            if (isExpand == null || !isExpand)
                expendableView.initLayout();

        }

        public abstract View getView(Context context, ViewGroup parent);

        public abstract View getChildView(Context context, ViewGroup parent, int childPosition);

        void setToggleListener(ToggleListener listener) {
            this.listener = listener;
        }
    }

    public interface ToggleListener {
        public void onExpand(int position);

        public void onCollapse(int position);
    }


    /**
     * For toggle rotation animation
     *
     * @param target
     * @param from
     * @param to
     * @return
     */
    public static ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

}
