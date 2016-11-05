package com.app.infideap.expandablerecyclerviewexample;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.infideap.expandablerecyclerview.ExpandableRecycler;
import com.app.infideap.expandablerecyclerviewexample.PostFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Post} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PostExpandableRecyclerViewAdapter extends ExpandableRecycler.Adapter<PostExpandableRecyclerViewAdapter.ViewHolder> {

    private final List<Post> mValues;
    private final OnListFragmentInteractionListener mListener;

    int[] colors = {
            Color.parseColor("#1abc9c"),
            Color.parseColor("#2ecc71"),
            Color.parseColor("#3498db"),
            Color.parseColor("#9b59b6"),
            Color.parseColor("#34495e"),
            Color.parseColor("#16a085"),
            Color.parseColor("#27ae60"),
            Color.parseColor("#2980b9"),
            Color.parseColor("#8e44ad"),
            Color.parseColor("#2c3e50"),
    };

    public PostExpandableRecyclerViewAdapter(List<Post> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;

        setToggleDrawable(null);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent.getContext(), parent);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        super.onBindViewHolder(holder, position);

    }

    @Override
    public int getChildCount(int position) {
        return mValues.get(position).comments.size();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends ExpandableRecycler.ViewHolder {
        View view;
        TextView titleView;
        TextView postView;
        View childView;
        View parentView;

        public Post mItem;

        public ViewHolder(Context context, ViewGroup parent) {
            super(context, parent);
            view = itemView;

        }

        @Override
        public View getView(Context context, ViewGroup parent) {

            parentView = LayoutInflater.from(context).inflate(R.layout.fragement_post_parent, parent, false);

            titleView = (TextView) parentView.findViewById(R.id.textView_title);
            postView = (TextView) parentView.findViewById(R.id.textView_post);

            titleView.setText(mItem.title);
            postView.setText(mItem.body);

            return parentView;
        }

        @Override
        public View getChildView(Context context, ViewGroup parent, int childPosition) {
            childView = LayoutInflater.from(context).inflate(R.layout.fragment_post_child, parent, false);
            TextView emailTextView = (TextView) childView.findViewById(R.id.textView_email);
            TextView commentTextView = (TextView) childView.findViewById(R.id.textView_comment);
            emailTextView.setText(mItem.comments.get(childPosition).email);
            commentTextView.setText(mItem.comments.get(childPosition).body);

            childView.findViewById(R.id.bookmark).setBackgroundColor(colors[childPosition % colors.length]);
            return childView;
        }


        @Override
        public String toString() {
            return super.toString() + " '" + postView.getText() + "'";
        }
    }
}
