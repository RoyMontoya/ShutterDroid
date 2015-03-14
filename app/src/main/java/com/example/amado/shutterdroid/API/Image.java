package com.example.amado.shutterdroid.API;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Amado on 11/03/2015.
 */
public class Image {
    @SerializedName("id")
   private String mId;
    @SerializedName("description")
   private String mDescription;

    @SerializedName("assets")
    private ImageAsset mImageAsset;

    public String getId() {
        return mId;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getLargeThumbnail(){
        return mImageAsset.mLargeThumb.mUrl;
    }

    private class ImageAsset{
        @SerializedName("preview")
        private Thumbnail mPreview;
        @SerializedName("small_thumb")
        private Thumbnail mSmallThumb;
        @SerializedName("large_thumb")
        private Thumbnail mLargeThumb;

    }

    private class Thumbnail{
        @SerializedName("url")
        private String mUrl;
    }


}
