
package com.app.infideap.expandablerecyclerviewexample;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Post {

    @SerializedName("userId")
    @Expose
    public long userId;
    @SerializedName("id")
    @Expose
    public long id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("body")
    @Expose
    public String body;

}
