package com.app.infideap.expandablerecyclerviewexample;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.infideap.expandablerecyclerviewexample.ExpandableFragment.OnListFragmentInteractionListener;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Post} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ExpandableRecyclerViewAdapter extends RecyclerView.Adapter<ExpandableRecyclerViewAdapter.ViewHolder> {

    private final List<Post> mValues;
    private final OnListFragmentInteractionListener mListener;
    private SparseBooleanArray expandState = new SparseBooleanArray();

    public ExpandableRecyclerViewAdapter(List<Post> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_expandable, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.titleView.setText(mValues.get(position).title);
        holder.postView.setText(mValues.get(position).body);

        holder.childView.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                createRotateAnimator(holder.toggleView, 0f, 180f).start();
                expandState.put(position, true);
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(holder.toggleView, 180f, 0f).start();
                expandState.put(position, false);
            }
        });
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.childView.toggle();
//                mListener.onListFragmentInteraction(holder.mView);
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });

        holder.childView.post(new Runnable() {
            @Override
            public void run() {
                holder.childView.collapse();
            }
        });

//        holder.childView.collapse();
    }

    /**
     * For toggle rotation animation
     *
     * @param target
     * @param from
     * @param to
     * @return
     */
    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView titleView;
        final TextView postView;
        final ExpandableLinearLayout childView;
        final View parentView;
        final ImageView toggleView;

        public Post mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            titleView = (TextView) view.findViewById(R.id.textView_title);
            postView = (TextView) view.findViewById(R.id.textView_post);

            parentView = view.findViewById(R.id.view_parent);
            childView = (ExpandableLinearLayout) view.findViewById(R.id.view_child);
            toggleView = (ImageView) view.findViewById(R.id.imageView_toggle);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + postView.getText() + "'";
        }
    }
}
