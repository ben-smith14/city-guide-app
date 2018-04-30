package com.example.android.cityguide;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Add up navigation to the action bar
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialise the layout views
        ImageView locationImage = findViewById(R.id.location_image);
        TextView locationName = findViewById(R.id.location_name);
        TextView locationPriceRange = findViewById(R.id.price_range);
        TextView locationTags = findViewById(R.id.location_tags);
        Button locationAddress = findViewById(R.id.location_address);
        Button locationNumber = findViewById(R.id.location_number);
        Button locationWebsite = findViewById(R.id.location_website);

        // Get the clicked CityLocation object from the intent
        CityLocation clickedLocation = getIntent().getParcelableExtra(Constants.CLICKED_LOCATION_KEY);

        // Set the location image and the location name
        locationImage.setImageResource(clickedLocation.getImageResourceID());
        locationName.setText(clickedLocation.getLocationName());

        // If the current location has no price range, hide the TextView
        int rangeValue = clickedLocation.getPriceRange();
        if (rangeValue == Constants.NO_VALUE) {
            locationPriceRange.setVisibility(View.GONE);
        } else {
            /*
            Otherwise, use SpannableStringBuilder and StyleSpan objects to apply the bold
            font style to certain characters in the price range string
            */
            SpannableStringBuilder poundSigns = new SpannableStringBuilder(getString(R.string.pound_signs));
            StyleSpan boldStyle = new StyleSpan(android.graphics.Typeface.BOLD);

            if (rangeValue == Constants.LOW_PRICE) {
                poundSigns.setSpan(boldStyle, 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            } else if (rangeValue == Constants.MED_PRICE) {
                poundSigns.setSpan(boldStyle, 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            } else if (rangeValue == Constants.HIGH_PRICE) {
                poundSigns.setSpan(boldStyle, 0, 3, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }

            locationPriceRange.setText(poundSigns);
            locationPriceRange.setVisibility(View.VISIBLE);
        }

        /*
        Use the StringBuilder class to add all of the tags into a single string if an array is
        available and then separate them using the | character
        */
        String[] tagsArray = clickedLocation.getLocationTags();
        if (tagsArray != null) {
            StringBuilder tagsBuilder = new StringBuilder();
            for (int i = 0; i < tagsArray.length; i++) {
                tagsBuilder.append(tagsArray[i]);
                if (i < tagsArray.length - 1) {
                    tagsBuilder.append(" | ");
                }
            }

            locationTags.setText(tagsBuilder.toString());
            locationTags.setVisibility(View.VISIBLE);
        } else {
            locationTags.setVisibility(View.GONE);
        }

        /*
        Set the text of the map opening button and then replace all of the spaces in the name
        and address Strings with a + in preparation for passing them to the map app via an intent
        */
        locationAddress.setText(getString(R.string.open_in_maps));
        final String nameToParse = clickedLocation.getLocationName().replaceAll(" ", "+");
        final String addressToParse = clickedLocation.getLocationAddress().replaceAll(" ", "+");

        locationAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Prepare a map opening intent using the new name and address Strings
                Uri mapIntentUri = Uri.parse("geo:0,0?q=" + nameToParse + ", " + addressToParse);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                /*
                Check that there is an app that can receive the intent and then start the
                activity if there is. If there isn't, indicate this with a Toast message
                */
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.no_map_app), Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*
        Set the text of the phone call button if a phone number is available, otherwise hide
        the button
        */
        final String phoneNumber = clickedLocation.getContactNumber();
        if (phoneNumber != null) {
            locationNumber.setText(phoneNumber);
            locationNumber.setVisibility(View.VISIBLE);

            locationNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*
                    Change the phone number to add the UK country code at the beginning and then
                    use this to prepare a phone opening intent
                    */
                    String numberToParse = phoneNumber.substring(1);
                    numberToParse = getString(R.string.uk_country_code) + numberToParse;
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + numberToParse));

                    /*
                    Check that there is an app that can receive the intent and then start the
                    activity if there is. If there isn't, indicate this with a Toast message
                    */
                    if (dialIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(dialIntent);
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.no_phone_app), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            locationNumber.setVisibility(View.INVISIBLE);
        }

        // Set the text of the web browser button if a website is available, otherwise hide the button
        final String websiteURL = clickedLocation.getLocationWebsite();
        if (websiteURL != null) {
            /*
            If the website URL extends past the domain suffix, cut off the rest of the URL for
            display purposes using the / character as a marker
            */
            if (websiteURL.contains("/")) {
                locationWebsite.setText(websiteURL.substring(0, websiteURL.indexOf("/")));
            } else {
                locationWebsite.setText(websiteURL);
                locationNumber.setVisibility(View.VISIBLE);
            }

            locationWebsite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Prepare a browser opening intent with the website URL
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www." + websiteURL));

                    /*
                    Check that there is an app that can receive the intent and then start the
                    activity if there is. If there isn't, indicate this with a Toast message
                    */
                    if (browserIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(browserIntent);
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.no_browser_app), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            locationWebsite.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
