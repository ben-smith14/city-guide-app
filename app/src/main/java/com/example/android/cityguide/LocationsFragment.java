package com.example.android.cityguide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class LocationsFragment extends Fragment {

    private FragmentActivity mParentActivity;
    private ArrayList<CityLocation> mCityLocationsList;

    public LocationsFragment() { // Required empty public constructor
    }

    /*
    To prevent calls to getActivity() returning null in onCreateView, we can store a reference
    to the activity in onAttach, which is called before onCreateView, and then use this in
    onCreateView to retrieve the activity
    */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mParentActivity = getActivity();
    }

    /*
    To then prevent memory leaks, we need to dereference the activity pointer when the
    fragment is detached from its parent activity or destroyed
    */
    @Override
    public void onDetach() {
        super.onDetach();
        mParentActivity = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mParentActivity = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.layout_citylocation_list, container, false);

        // Retrieve the current tab position from the arguments that were passed to the new fragment
        Bundle adapterArguments = getArguments();
        int tabPosition;
        if (adapterArguments != null) {
            tabPosition = adapterArguments.getInt(Constants.TAB_POSITION_KEY, 0);
        } else {
            tabPosition = 0;
        }

        // Populate an ArrayList with our CityLocation objects based on which tab we are in and
        // then sort them by name
        switch (tabPosition) {
            case 1:
                createCoffeeList();
                break;
            case 2:
                createNightlifeList();
                break;
            case 3:
                createFunList();
                break;
            case 4:
                createSightsList();
                break;
            case 5:
                createTransportList();
                break;
            default:
                createFoodList();
                break;
        }

        Collections.sort(mCityLocationsList);

        // Instantiate our subclass of the ArrayAdapter and attach it to the ListView
        CityLocationAdapter adapter = new CityLocationAdapter(mParentActivity, mCityLocationsList);
        final ListView locationsListView = rootView.findViewById(R.id.location_list);
        if (locationsListView != null) {
            locationsListView.setAdapter(adapter);

            // Create a click listener for each item that will open up an activity that shows more
            // details for the clicked location
            locationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent detailsIntent = new Intent(mParentActivity, DetailsActivity.class);
                    detailsIntent.putExtra(Constants.CLICKED_LOCATION_KEY, mCityLocationsList.get(i));
                    startActivity(detailsIntent);
                }
            });
        }

        return rootView;
    }

    /*
    Each CityLocation object is defined using an image resource ID, followed by an item name,
    then an address, followed by an array of tags, then a contact number, a website and finally
    a price range. Additional constructors are available to use if not all of this information is
    available
    */
    private void createFoodList() {
        mCityLocationsList = new ArrayList<>();

        mCityLocationsList.add(new CityLocation(
                R.drawable.menu_gordon_jones,
                getString(R.string.menu_gordon_jones),
                getString(R.string.menu_gordon_jones_address),
                new String[]{getString(R.string.modern_tag), getString(R.string.european_tag),
                        getString(R.string.stylish_tag), getString(R.string.contemporary_tag)},
                getString(R.string.menu_gordon_jones_number),
                getString(R.string.menu_gordon_jones_website),
                Constants.HIGH_PRICE));
        mCityLocationsList.add(new CityLocation(
                R.drawable.hudson_steakhouse,
                getString(R.string.hudson_steakhouse),
                getString(R.string.hudson_steakhouse_address),
                new String[]{getString(R.string.steak_tag), getString(R.string.casual_tag),
                        getString(R.string.fireplace_tag), getString(R.string.cocktails_tag)},
                getString(R.string.hudson_steakhouse_number),
                getString(R.string.hudson_steakhouse_website),
                Constants.MED_PRICE));
        mCityLocationsList.add(new CityLocation(
                R.drawable.sotto_sotto,
                getString(R.string.sotto_sotto),
                getString(R.string.sotto_sotto_address),
                new String[]{getString(R.string.italian_tag), getString(R.string.classic_tag),
                        getString(R.string.contemporary_tag), getString(R.string.candlelit_tag)},
                getString(R.string.sotto_sotto_number),
                getString(R.string.sotto_sotto_website),
                Constants.MED_PRICE));
        mCityLocationsList.add(new CityLocation(
                R.drawable.mcdonalds,
                getString(R.string.mcdonalds),
                getString(R.string.mcdonalds_address),
                new String[]{getString(R.string.fast_food_tag), getString(R.string.burgers_tag),
                        getString(R.string.fries_tag), getString(R.string.milkshakes_tag)},
                getString(R.string.mcdonalds_number),
                getString(R.string.mcdonalds_website),
                Constants.LOW_PRICE));
        mCityLocationsList.add(new CityLocation(
                R.drawable.mission_burrito,
                getString(R.string.mission_burrito),
                getString(R.string.mission_burrito_address),
                new String[]{getString(R.string.mexican_tag), getString(R.string.fast_food_tag),
                        getString(R.string.casual_tag), getString(R.string.burrito_tag)},
                getString(R.string.mission_burrito_number),
                getString(R.string.mission_burrito_website),
                Constants.LOW_PRICE));
        mCityLocationsList.add(new CityLocation(
                R.drawable.loch_fyne,
                getString(R.string.loch_fyne),
                getString(R.string.loch_fyne_address),
                new String[]{getString(R.string.fish_tag), getString(R.string.seafood_tag),
                        getString(R.string.upscale_tag), getString(R.string.british_tag)},
                getString(R.string.loch_fyne_number),
                getString(R.string.loch_fyne_website),
                Constants.MED_PRICE));
        mCityLocationsList.add(new CityLocation(
                R.drawable.the_globe,
                getString(R.string.the_globe),
                getString(R.string.the_globe_address),
                new String[]{getString(R.string.pub_tag), getString(R.string.rustic_tag),
                        getString(R.string.british_tag), getString(R.string.casual_tag)},
                getString(R.string.the_globe_number),
                getString(R.string.the_globe_website),
                Constants.MED_PRICE));
        mCityLocationsList.add(new CityLocation(
                R.drawable.the_olive_tree,
                getString(R.string.the_olive_tree),
                getString(R.string.the_olive_tree_address),
                new String[]{getString(R.string.european_tag), getString(R.string.british_tag),
                        getString(R.string.fine_dining_tag), getString(R.string.contemporary_tag)},
                getString(R.string.the_olive_tree_number),
                getString(R.string.the_olive_tree_website),
                Constants.HIGH_PRICE));
    }

    private void createCoffeeList() {
        mCityLocationsList = new ArrayList<>();

        mCityLocationsList.add(new CityLocation(
                R.drawable.boston_tea_party,
                getString(R.string.boston_tea_party),
                getString(R.string.boston_tea_party_address),
                new String[]{getString(R.string.cafe_tag), getString(R.string.british_tag),
                        getString(R.string.cosy_tag), getString(R.string.gluten_free_tag)},
                getString(R.string.boston_tea_party_number),
                getString(R.string.boston_tea_party_website),
                Constants.MED_PRICE));
        mCityLocationsList.add(new CityLocation(
                R.drawable.cafe_lucca,
                getString(R.string.cafe_lucca),
                getString(R.string.cafe_lucca_address),
                new String[]{getString(R.string.cafe_tag), getString(R.string.european_tag),
                        getString(R.string.british_tag), getString(R.string.gluten_free_tag)},
                getString(R.string.cafe_lucca_number),
                getString(R.string.cafe_lucca_website),
                Constants.MED_PRICE));
        mCityLocationsList.add(new CityLocation(
                R.drawable.mokoko_coffee,
                getString(R.string.mokoko_coffee),
                getString(R.string.mokoko_coffee_address),
                new String[]{getString(R.string.cafe_tag), getString(R.string.british_tag),
                        getString(R.string.friendly_tag), getString(R.string.gluten_free_tag)},
                getString(R.string.mokoko_coffee_number),
                getString(R.string.mokoko_coffee_website),
                Constants.LOW_PRICE));
        mCityLocationsList.add(new CityLocation(
                R.drawable.patisserie_valerie,
                getString(R.string.patisserie_valerie),
                getString(R.string.patisserie_valerie_address),
                new String[]{getString(R.string.cafe_tag), getString(R.string.european_tag),
                        getString(R.string.casual_tag), getString(R.string.cosy_tag)},
                getString(R.string.patisserie_valerie_number),
                getString(R.string.patisserie_valerie_website),
                Constants.MED_PRICE));
        mCityLocationsList.add(new CityLocation(
                R.drawable.cafe_au_lait,
                getString(R.string.cafe_au_lait),
                getString(R.string.cafe_au_lait_address),
                new String[]{getString(R.string.cafe_tag), getString(R.string.british_tag),
                        getString(R.string.cosy_tag), getString(R.string.gluten_free_tag)},
                getString(R.string.cafe_au_lait_number),
                getString(R.string.cafe_au_lait_website),
                Constants.LOW_PRICE));
        mCityLocationsList.add(new CityLocation(
                R.drawable.the_mad_hatters_tea_party,
                getString(R.string.the_mad_hatters_tea_party),
                getString(R.string.the_mad_hatters_tea_party_address),
                new String[]{getString(R.string.cafe_tag), getString(R.string.british_tag),
                        getString(R.string.cosy_tag), getString(R.string.casual_tag)},
                getString(R.string.the_mad_hatters_tea_party_number),
                getString(R.string.the_mad_hatters_tea_party_website),
                Constants.MED_PRICE));
        mCityLocationsList.add(new CityLocation(
                R.drawable.the_colombian_company,
                getString(R.string.the_colombian_company),
                getString(R.string.the_colombian_company_address),
                new String[]{getString(R.string.cafe_tag), getString(R.string.colombian_tag),
                        getString(R.string.south_american_tag), getString(R.string.casual_tag)},
                getString(R.string.the_colombian_company_number),
                getString(R.string.the_colombian_company_website),
                Constants.LOW_PRICE));
        mCityLocationsList.add(new CityLocation(
                R.drawable.comins_tea,
                getString(R.string.comins_tea),
                getString(R.string.comins_tea_address),
                new String[]{getString(R.string.japanese_tag), getString(R.string.indian_tag),
                        getString(R.string.cafe_tag), getString(R.string.asian_tag)},
                getString(R.string.comins_tea_number),
                getString(R.string.comins_tea_website),
                Constants.MED_PRICE));
    }

    private void createNightlifeList() {
        mCityLocationsList = new ArrayList<>();

        mCityLocationsList.add(new CityLocation(
                R.drawable.the_second_bridge,
                getString(R.string.the_second_bridge),
                getString(R.string.the_second_bridge_address),
                new String[]{getString(R.string.nightclub_tag), getString(R.string.bar_tag),
                        getString(R.string.groups_tag)},
                getString(R.string.the_second_bridge_number),
                getString(R.string.the_second_bridge_website)));
        mCityLocationsList.add(new CityLocation(
                R.drawable.zero_zero,
                getString(R.string.zero_zero),
                getString(R.string.zero_zero_address),
                new String[]{getString(R.string.nightclub_tag), getString(R.string.bar_tag),
                        getString(R.string.cocktails_tag), getString(R.string.groups_tag)},
                getString(R.string.zero_zero_number),
                getString(R.string.zero_zero_website)));
        mCityLocationsList.add(new CityLocation(
                R.drawable.komedia,
                getString(R.string.komedia),
                getString(R.string.komedia_address),
                new String[]{getString(R.string.nightclub_tag), getString(R.string.performances_tag),
                        getString(R.string.concerts_tag), getString(R.string.cafe_tag)},
                getString(R.string.komedia_number),
                getString(R.string.komedia_website)));
        mCityLocationsList.add(new CityLocation(
                R.drawable.sub13,
                getString(R.string.sub13),
                getString(R.string.sub13_address),
                new String[]{getString(R.string.bar_tag), getString(R.string.cocktails_tag),
                        getString(R.string.casual_tag), getString(R.string.outdoor_seating_tag)},
                getString(R.string.sub13_number),
                getString(R.string.sub13_website)));
        mCityLocationsList.add(new CityLocation(
                R.drawable.cosy_club,
                getString(R.string.cosy_club),
                getString(R.string.cosy_club_address),
                new String[]{getString(R.string.bar_tag), getString(R.string.food_tag),
                        getString(R.string.british_tag), getString(R.string.chic_tag)},
                getString(R.string.cosy_club_number),
                getString(R.string.cosy_club_website)));
        mCityLocationsList.add(new CityLocation(
                R.drawable.all_bar_one,
                getString(R.string.all_bar_one),
                getString(R.string.all_bar_one_address),
                new String[]{getString(R.string.bar_tag), getString(R.string.food_tag),
                        getString(R.string.international_tag), getString(R.string.british_tag)},
                getString(R.string.all_bar_one_number),
                getString(R.string.all_bar_one_website)));
        mCityLocationsList.add(new CityLocation(
                R.drawable.revolution_bath,
                getString(R.string.revolution_bath),
                getString(R.string.revolution_bath_address),
                new String[]{getString(R.string.bar_tag), getString(R.string.british_tag),
                        getString(R.string.american_tag), getString(R.string.food_tag)},
                getString(R.string.revolution_bath_number),
                getString(R.string.revolution_bath_website)));
        mCityLocationsList.add(new CityLocation(
                R.drawable.the_king_of_wessex,
                getString(R.string.the_king_of_wessex),
                getString(R.string.the_king_of_wessex_address),
                new String[]{getString(R.string.bar_tag), getString(R.string.pub_tag),
                        getString(R.string.british_tag), getString(R.string.food_tag)},
                getString(R.string.the_king_of_wessex_number),
                getString(R.string.the_king_of_wessex_website),
                Constants.LOW_PRICE));
    }

    private void createFunList() {
        mCityLocationsList = new ArrayList<>();

        mCityLocationsList.add(new CityLocation(
                R.drawable.theatre_royal,
                getString(R.string.theatre_royal),
                getString(R.string.theatre_royal_address),
                new String[]{getString(R.string.theatre_tag), getString(R.string.concerts_tag),
                        getString(R.string.georgian_tag)},
                getString(R.string.theatre_royal_number),
                getString(R.string.theatre_royal_website)));
        mCityLocationsList.add(new CityLocation(
                R.drawable.odeon_cinema,
                getString(R.string.odeon_cinema),
                getString(R.string.odeon_cinema_address),
                new String[]{getString(R.string.cinema_tag), getString(R.string.three_dimen_tag),
                        getString(R.string.student_deals_tag)},
                getString(R.string.odeon_cinema_number),
                getString(R.string.odeon_cinema_website)));
        mCityLocationsList.add(new CityLocation(
                R.drawable.the_mission_theatre,
                getString(R.string.the_mission_theatre),
                getString(R.string.the_mission_theatre_address),
                new String[]{getString(R.string.cinema_tag), getString(R.string.three_dimen_tag),
                        getString(R.string.student_deals_tag)},
                getString(R.string.the_mission_theatre_number),
                getString(R.string.the_mission_theatre_website)));
        mCityLocationsList.add(new CityLocation(
                R.drawable.bath_sports_and_leisure,
                getString(R.string.bath_sports_and_leisure),
                getString(R.string.bath_sports_and_leisure_address),
                new String[]{getString(R.string.sports_tag), getString(R.string.fitness_tag),
                        getString(R.string.swimming_pool_tag), getString(R.string.gym_tag)},
                getString(R.string.bath_sports_and_leisure_number),
                getString(R.string.bath_sports_and_leisure_website)));
        mCityLocationsList.add(new CityLocation(
                R.drawable.bath_rugby,
                getString(R.string.bath_recreation_limited),
                getString(R.string.bath_recreation_limited_address),
                new String[]{getString(R.string.rugby_tag), getString(R.string.sports_tag),
                        getString(R.string.bar_tag)},
                getString(R.string.bath_recreation_limited_number),
                getString(R.string.bath_recreation_limited_website)));
        mCityLocationsList.add(new CityLocation(
                R.drawable.bizarre_bath,
                getString(R.string.bizarre_bath),
                getString(R.string.bizarre_bath_address),
                new String[]{getString(R.string.comedy_tag), getString(R.string.tour_tag),
                        getString(R.string.performances_tag), getString(R.string.walking_tag)},
                getString(R.string.bizarre_bath_number),
                getString(R.string.bizarre_bath_website)));
    }

    private void createSightsList() {
        mCityLocationsList = new ArrayList<>();

        mCityLocationsList.add(new CityLocation(
                R.drawable.roman_baths,
                getString(R.string.roman_baths),
                getString(R.string.roman_baths_address),
                new String[]{getString(R.string.bathhouse_tag), getString(R.string.temple_tag),
                        getString(R.string.food_tag), getString(R.string.spa_tag)},
                getString(R.string.roman_baths_number),
                getString(R.string.roman_baths_website)));
        mCityLocationsList.add(new CityLocation(
                R.drawable.royal_crescent,
                getString(R.string.royal_crescent),
                getString(R.string.royal_crescent_address),
                new String[]{getString(R.string.architecture_tag), getString(R.string.landmark_tag),
                        getString(R.string.sights_tag)},
                getString(R.string.royal_crescent_number),
                getString(R.string.royal_crescent_website)));
        mCityLocationsList.add(new CityLocation(
                R.drawable.bath_abbey,
                getString(R.string.bath_abbey),
                getString(R.string.bath_abbey_address),
                new String[]{getString(R.string.architecture_tag), getString(R.string.religion_tag),
                        getString(R.string.landmark_tag), getString(R.string.gothic_tag)},
                getString(R.string.bath_abbey_number),
                getString(R.string.bath_abbey_website)));
        mCityLocationsList.add(new CityLocation(
                R.drawable.the_jane_austen_centre,
                getString(R.string.the_jane_austen_centre),
                getString(R.string.the_jane_austen_centre_address),
                new String[]{getString(R.string.museum_tag), getString(R.string.speciality_tag),
                        getString(R.string.literature_tag), getString(R.string.tourism_tag)},
                getString(R.string.the_jane_austen_centre_number),
                getString(R.string.the_jane_austen_centre_website)));
        mCityLocationsList.add(new CityLocation(
                R.drawable.bath_skyline,
                getString(R.string.bath_skyline),
                getString(R.string.bath_skyline_address),
                new String[]{getString(R.string.walking_tag), getString(R.string.nature_tag),
                        getString(R.string.sights_tag)},
                getString(R.string.bath_skyline_number),
                getString(R.string.bath_skyline_website)));
        mCityLocationsList.add(new CityLocation(
                R.drawable.university_of_bath,
                getString(R.string.university_of_bath),
                getString(R.string.university_of_bath_address),
                new String[]{getString(R.string.education_tag), getString(R.string.university_tag),
                        getString(R.string.sports_tag)},
                getString(R.string.university_of_bath_number),
                getString(R.string.university_of_bath_website)));
        mCityLocationsList.add(new CityLocation(
                R.drawable.parade_gardens,
                getString(R.string.parade_gardens),
                getString(R.string.parade_gardens_address),
                new String[]{getString(R.string.park_tag), getString(R.string.cafe_tag),
                        getString(R.string.concerts_tag), getString(R.string.plantlife_tag)},
                getString(R.string.parade_gardens_number),
                getString(R.string.parade_gardens_website)));
        mCityLocationsList.add(new CityLocation(
                R.drawable.royal_victoria_park,
                getString(R.string.royal_victoria_park),
                getString(R.string.royal_victoria_park_address),
                new String[]{getString(R.string.park_tag), getString(R.string.plantlife_tag)},
                getString(R.string.royal_victoria_park_number),
                getString(R.string.royal_victoria_park_website)));
    }

    private void createTransportList() {
        mCityLocationsList = new ArrayList<>();

        mCityLocationsList.add(new CityLocation(
                R.drawable.bath_bus_station,
                getString(R.string.bath_bus_station),
                getString(R.string.bath_bus_station_address),
                new String[]{getString(R.string.bus_station_tag)},
                getString(R.string.bath_bus_station_number),
                getString(R.string.bath_bus_station_website)));
        mCityLocationsList.add(new CityLocation(
                R.drawable.bath_spa_station,
                getString(R.string.bath_spa_station),
                getString(R.string.bath_spa_station_address),
                new String[]{getString(R.string.railway_station_tag)},
                getString(R.string.bath_spa_station_number),
                getString(R.string.bath_spa_station_website)));
        mCityLocationsList.add(new CityLocation(
                R.drawable.oldfield_park,
                getString(R.string.oldfield_park_station),
                getString(R.string.oldfield_park_station_address),
                new String[]{getString(R.string.railway_station_tag)},
                getString(R.string.oldfield_park_station_number),
                getString(R.string.oldfield_park_station_website)));
        mCityLocationsList.add(new CityLocation(
                R.drawable.odd_down_park_ride,
                getString(R.string.odd_down_park_ride),
                getString(R.string.odd_down_park_ride_address),
                new String[]{getString(R.string.park_and_ride_tag)},
                getString(R.string.odd_down_park_ride_number),
                getString(R.string.odd_down_park_ride_website)));
    }
}
