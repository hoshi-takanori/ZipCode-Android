package io.hoshi.zipcode;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hoshi on 2016/04/28.
 */
public class ZipCodeResult {
    @SerializedName("ResultInfo")
    public ResultInfo resultInfo;

    @SerializedName("Feature")
    public Feature[] feature;

    public static class ResultInfo {
        @SerializedName("Count")
        public int count;
    }

    public static class Geometry {
        @SerializedName("Coordinates")
        public String coordinates;
    }

    public static class Property {
        @SerializedName("Address")
        public String address;
    }

    public static class Feature {
        @SerializedName("Geometry")
        public Geometry geometry;

        @SerializedName("Property")
        public Property property;
    }
}
