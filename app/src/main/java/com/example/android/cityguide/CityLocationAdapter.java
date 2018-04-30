package com.example.android.cityguide;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CityLocationAdapter extends ArrayAdapter<CityLocation> {

    private Context mContext;

    CityLocationAdapter(@NonNull Context context, @NonNull List<CityLocation> objects) {
        super(context, 0, objects);
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        /*
        First, check if an existing view is being reused. If there isn't one available,
        inflate a new view from the layout file
        */
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_citylocation, parent, false);
        }

        // Get the CityLocation object located at this position in the list
        CityLocation currentCityLocation = getItem(position);

        if (currentCityLocation != null) {
            ImageView itemImage = convertView.findViewById(R.id.item_image);
            itemImage.setImageResource(currentCityLocation.getImageResourceID());

            TextView locationName = convertView.findViewById(R.id.location_name);
            locationName.setText(currentCityLocation.getLocationName());

            TextView locationAddress = convertView.findViewById(R.id.location_address);
            locationAddress.setText(currentCityLocation.getLocationAddress());

            /*
            Display a constant number of tags for each location, or all of the tags if
            there is less than the constant value in the current CityLocation object
            */
            TextView locationTags = convertView.findViewById(R.id.tags_text);
            String[] tagsArray = currentCityLocation.getLocationTags();

            if (tagsArray != null) {
                int numOfTags;
                if (tagsArray.length < Constants.TAG_DISPLAY_COUNT) {
                    numOfTags = tagsArray.length;
                } else {
                    numOfTags = Constants.TAG_DISPLAY_COUNT;
                }

                /*
                Use the StringBuilder class to add all of the tags into a single string and
                separate them using the | character
                */
                StringBuilder tagsBuilder = new StringBuilder();
                for (int i = 0; i < numOfTags; i++) {
                    tagsBuilder.append(tagsArray[i]);
                    if (i < numOfTags - 1) {
                        tagsBuilder.append(" | ");
                    }
                }

                locationTags.setText(tagsBuilder.toString());
                locationTags.setVisibility(View.VISIBLE);
            } else {
                locationTags.setVisibility(View.GONE);
            }

            /*
            Display the price range of each location that has one by using 3 pound signs with
            varying characters in bold font
            */
            TextView priceRange = convertView.findViewById(R.id.price_range);
            int rangeValue = currentCityLocation.getPriceRange();

            // If the current location has no price range, hide the TextView
            if (rangeValue == Constants.NO_VALUE) {
                priceRange.setVisibility(View.GONE);
            } else {
                /*
                Otherwise, use SpannableStringBuilder and StyleSpan objects to apply the bold
                font style to certain characters in the price range string
                */
                SpannableStringBuilder poundSigns = new SpannableStringBuilder(mContext.getString(R.string.pound_signs));
                StyleSpan boldStyle = new StyleSpan(Typeface.BOLD);

                if (rangeValue == Constants.LOW_PRICE) {
                    poundSigns.setSpan(boldStyle, 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                } else if (rangeValue == Constants.MED_PRICE) {
                    poundSigns.setSpan(boldStyle, 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                } else if (rangeValue == Constants.HIGH_PRICE) {
                    poundSigns.setSpan(boldStyle, 0, 3, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                }

                priceRange.setText(poundSigns);
                priceRange.setVisibility(View.VISIBLE);
            }
        }

        return convertView;
    }
}
