package com.app.infideap.expandablerecyclerviewexample;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.infideap.expandablerecyclerviewexample.ExpandableFragment.OnListFragmentInteractionListener;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Post} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
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


        @Override
        public abstract T onCreateViewHolder(ViewGroup parent, int viewType);

        @Override
        public void onBindViewHolder(T holder, int position){
            holder.load(getChildCount(position));
        }

        public abstract int getChildCount(int position);
    }


    public abstract static class ViewHolder extends RecyclerView.ViewHolder {

        private final ExpandableLinearLayout expendableView;
        private final ImageView toggleView;
        private final FrameLayout parentView;
        private final Context context;
        private final LinearLayout childView;

        public ViewHolder(Context context, ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.fragment_expandable, parent, false));

            this.context = context;
            parentView = (FrameLayout) itemView.findViewById(R.id.content_parent);
            childView = (LinearLayout) itemView.findViewById(R.id.content_child);
            expendableView = (ExpandableLinearLayout) itemView.findViewById(R.id.view_expendable);
            toggleView = (ImageView) itemView.findViewById(R.id.imageView_toggle);

            expendableView.setListener(new ExpandableLayoutListenerAdapter() {
                @Override
                public void onPreOpen() {
                    createRotateAnimator(toggleView, 0f, 180f).start();
                }

                @Override
                public void onPreClose() {
                    createRotateAnimator(toggleView, 180f, 0f).start();
                }
            });
            parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expendableView.toggle();
//                mListener.onListFragmentInteraction(view);
                }
            });

            expendableView.post(new Runnable() {
                @Override
                public void run() {
                    expendableView.collapse();
                }
            });

        }

        public void load(int childCount) {
            parentView.removeAllViews();
            parentView.addView(getView(context, parentView));
            childView.removeAllViews();
            for (int i = 0; i < childCount; i++) {
                childView.addView(getChildView(context, childView, i));
            }
        }

        public abstract View getView(Context context, ViewGroup parent);

        public abstract View getChildView(Context context, ViewGroup parent, int childPosition);
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
