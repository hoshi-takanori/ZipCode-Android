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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (resultInfo != null) {
            builder.append("resultInfo.count = " + resultInfo.count);
        } else {
            builder.append("resultInfo = null");
        }

        if (feature != null) {
            for (int i = 0; i < feature.length; i++) {
                if (feature[i].property != null) {
                    builder.append("\nfeature[" + i + "].property.address = " + feature[i].property.address);
                } else {
                    builder.append("\nfeature[" + i + "].property = null");
                }

                if (feature[i].geometry != null) {
                    builder.append("\nfeature[" + i + "].geometry.coordinates = " + feature[i].geometry.coordinates);
                } else {
                    builder.append("\nfeature[" + i + "].geometry = null");
                }
            }
        } else {
            builder.append("\nfeature = null");
        }

        return builder.toString();
    }
}
