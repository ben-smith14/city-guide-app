package com.example.android.cityguide;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class CityLocation implements Comparable<CityLocation>, Parcelable {

    private int mImageResourceID;
    private String mLocationName;
    private String mLocationAddress;
    private String[] mLocationTags;
    private String mContactNumber;
    private String mLocationWebsite;
    private int mPriceRange;

    CityLocation(int imageResourceID, String locationName, String locationAddress, String[] locationTags,
                 String contactNumber, String locationWebsite, int priceRange) {
        this.mImageResourceID = imageResourceID;
        this.mLocationName = locationName;
        this.mLocationAddress = locationAddress;
        this.mLocationTags = locationTags;
        this.mContactNumber = contactNumber;
        this.mLocationWebsite = locationWebsite;
        this.mPriceRange = priceRange;
    }

    CityLocation(int imageResourceID, String locationName, String locationAddress, String[] locationTags,
                 String contactNumber, String locationWebsite) {
        this(imageResourceID, locationName, locationAddress, locationTags, contactNumber, locationWebsite, Constants.NO_VALUE);
    }

    // Use the name of each location as a means of comparing them
    @Override
    public int compareTo(@NonNull CityLocation cityLocation) {
        return this.getLocationName().compareTo(cityLocation.getLocationName());
    }

    // Implement the Parcelable interface so that the CityLocation object can be passed in an intent
    public static final Parcelable.Creator<CityLocation> CREATOR = new Parcelable.Creator<CityLocation>() {
        public CityLocation createFromParcel(Parcel in) {
            return new CityLocation(in);
        }

        public CityLocation[] newArray(int size) {
            return new CityLocation[size];
        }
    };

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mImageResourceID);
        out.writeString(mLocationName);
        out.writeString(mLocationAddress);
        out.writeStringArray(mLocationTags);
        out.writeString(mContactNumber);
        out.writeString(mLocationWebsite);
        out.writeInt(mPriceRange);
    }

    private CityLocation(Parcel in) {
        this.mImageResourceID = in.readInt();
        this.mLocationName = in.readString();
        this.mLocationAddress = in.readString();
        this.mLocationTags = in.createStringArray();
        this.mContactNumber = in.readString();
        this.mLocationWebsite = in.readString();
        this.mPriceRange = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public int getImageResourceID() {
        return mImageResourceID;
    }

    public String getLocationName() {
        return mLocationName;
    }

    public String getLocationAddress() {
        return mLocationAddress;
    }

    public String[] getLocationTags() {
        return mLocationTags;
    }

    public String getContactNumber() {
        return mContactNumber;
    }

    public String getLocationWebsite() {
        return mLocationWebsite;
    }

    public int getPriceRange() {
        return mPriceRange;
    }
}
