# Expandable-Recycler
[ ![Download](https://api.bintray.com/packages/infideap2/expandable-recycler/expandable-recycler/images/download.svg?version=0.0.1) ](https://bintray.com/infideap2/expandable-recycler/expandable-recycler/0.0.1/link)

A library provide expand & collapse state for recyclerview.

![Alt Text](https://raw.githubusercontent.com/shiburagi/ExpandableRecyclerViewExample/master/preview.gif)


---

<a href='https://ko-fi.com/A0A0FB3V' target='_blank'><img height='36' style='border:0px;height:36px;' src='https://az743702.vo.msecnd.net/cdn/kofi4.png?v=0' border='0' alt='Buy Me a Coffee at ko-fi.com' /></a>
[![paypal](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=D9JKYQL8452AL)

## Including In Your Project
If you are a Maven user you can easily include the library by specifying it as
a dependency:

#### Maven
``` xml
<dependency>
  <groupId>com.infideap.expandablerecycler</groupId>
  <artifactId>expandable-recycler</artifactId>
  <version>0.0.1</version>
  <type>pom</type>
</dependency>
```
#### Gradle
```groovy
dependencies {
   implementation 'com.infideap.expandablerecycler:expandable-recycler:0.0.1'
}
```

if **the gradle unable to sync**, you may include this line in project level gradle,
```groovy
repositories {
 maven{
   url "https://dl.bintray.com/infideap2/expandable-recycler"
 }
}
```

**or**,
you can include it by **download this project** and **import /expandable-recycler** as **module**.

## How to use
You can use **RecyclerView library provide by Google**, not necessary to use ExpandableRecycler in this library (Just gimmick).

Then, in java class, **use ExpandableRecycler.Adapter** as RecyclerView's adapter. Like below :

```java
public class PostExpandableRecyclerViewAdapter extends ExpandableRecycler.Adapter<PostExpandableRecyclerViewAdapter.ViewHolder> {
   
   ...
   
   @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        //call this method after assign holder's item
        super.onBindViewHolder(holder, position);


    }
    
    /**
     * 
     * @param position
     * @return number of child for specific position/index
     */
    @Override
    public int getChildCount(int position) {
        return mValues.get(position).comments.size();
    }
    
    ...
    
    
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
```


