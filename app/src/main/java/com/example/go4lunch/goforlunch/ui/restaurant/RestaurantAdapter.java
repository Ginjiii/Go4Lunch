package com.example.go4lunch.goforlunch.ui.restaurant;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.models.TheOpeningHours;
import com.example.go4lunch.goforlunch.models.places.RestaurantDetail;
import com.example.go4lunch.goforlunch.ui.restaurantDetail.RestaurantDetailActivity;
import com.example.go4lunch.goforlunch.utils.Go4LunchHelper;
import com.go4lunch.R;
import com.go4lunch.databinding.RestaurantItemLayoutBinding;

import java.util.List;

import static com.example.go4lunch.goforlunch.ui.restaurantDetail.RestaurantDetailActivity.RESTAURANT_PLACE_ID;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private static final String TAG = "TAG";
    private List<Restaurant> restaurantList;
    private TheOpeningHours theOpeningHours;
    private Context context;

    private List<Restaurant> restaurants;
    private RequestManager glide;

    /**
     * Declarations for the opening hours display
     */
    public static final int CASE_0_CLOSED_TODAY_OPEN_AT = 0;
    public static final int CASE_1_CLOSED_OPEN_AT_ON = 1;
    public static final int CASE_2_CLOSED_OPEN_AT = 2;
    public static final int CASE_3_OPEN_UNTIL = 3;
    public static final int CASE_4_CLOSING_SOON = 4;
    public static final int CASE_5_CLOSED_TODAY_OPEN_AT_ON = 5;
    public static final int CASE_6_OPEN_24_7 = 6;
    public static final int CASE_7_OPEN_UNTIL_CLOSING_AM = 7;

    public RestaurantAdapter(List<Restaurant> restaurants, RequestManager glide) {
        this.restaurants = restaurants;
        this.glide = glide;
    }

    public void setRestaurantList(List<Restaurant> mRestaurantList) {
        restaurantList = mRestaurantList;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RestaurantItemLayoutBinding binding = RestaurantItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new RestaurantViewHolder(binding);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder restaurantViewHolder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        restaurantViewHolder.binding.restaurantItemListName.setText(String.valueOf(restaurant.getRestaurantName()));
        int numberOfCoworker = 0;
        context = restaurantViewHolder.itemView.getContext();


        if (restaurant.getRestaurantDistance() > 0) {
            restaurantViewHolder.binding.restaurantItemListDistance.setText(String.valueOf((int)restaurant.getRestaurantDistance())+" m");
        }
        if (restaurant.getRestaurantAddress() != null) {
            restaurantViewHolder.binding.restaurantItemListAddress.setText(restaurant.getRestaurantAddress());
        }
        if (restaurant.getRestaurantCoworkerList() != null) {
            numberOfCoworker = restaurant.getRestaurantCoworkerList().size();
        }
        restaurantViewHolder.binding.restaurantItemListParticipantsNumber.setText("(" + numberOfCoworker + ")");

        displayOpeningHour(restaurantViewHolder, position);
        displayRestaurantImage(restaurantViewHolder, position);

        restaurantViewHolder.binding.restaurantItemListRate.setRating((float) restaurant.getRestaurantRating());
        Log.d(TAG, "onBindViewHolder:restaurantViewHolder.binding.restaurantItemListRate.getRating() ");
        restaurantViewHolder.binding.restaurantItemListRate.getNumStars();
        displayRestaurantImage(restaurantViewHolder, position);

        restaurantViewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RestaurantDetailActivity.class);
            intent.putExtra(RESTAURANT_PLACE_ID, restaurantList.get(position).getRestaurantPlaceId());
            context.startActivity(intent);
        });
    }

    private void displayOpeningHour(RestaurantViewHolder restaurantViewHolder, int position) {
        if (restaurantList.get(position).getRestaurantOpeningHours() != null) {

            TheOpeningHours openingStatus = getRestaurantOpeningHoursStatus(context, restaurantList.get(position));
            if (openingStatus.isOpen()) {
                restaurantViewHolder.binding.restaurantItemListInfo.setText(openingStatus.getDescription());
                restaurantViewHolder.binding.restaurantItemListInfo.setTextColor(context.getResources().getColor(R.color.colorTextBlack));
                restaurantViewHolder.binding.restaurantItemListInfo.setTypeface(null, Typeface.ITALIC);
            } else {
                restaurantViewHolder.binding.restaurantItemListInfo.setText(openingStatus.getDescription());
                restaurantViewHolder.binding.restaurantItemListInfo.setTextColor(context.getResources().getColor(R.color.colorTextRed));
                restaurantViewHolder.binding.restaurantItemListInfo.setTypeface(null, Typeface.BOLD);
            }
        } else {
            restaurantViewHolder.binding.restaurantItemListInfo.setText("");
        }
    }

    private TheOpeningHours getRestaurantOpeningHoursStatus(Context context, Restaurant restaurant) {
        String nextOpenDay = null;
        String time = null;
        int currentDay;

        RestaurantDetail.OpeningHours restaurantHours = restaurant.getRestaurantOpeningHours();
        theOpeningHours = new TheOpeningHours(9, 0, 9, 9, false, null, 9, 9);

        //Get the current number day
        currentDay = Go4LunchHelper.getCurrentDayInt() - 1;

        //Get the list of the opening hours on the current and following day
        if (restaurantHours.getPeriods().size() > 0) {
            theOpeningHours.setCurrentOpenDay(currentDay);

            if (restaurantHours.getPeriods().get(0).getClose() == null) {
                theOpeningHours.setOpeningDayCase(CASE_6_OPEN_24_7);
                theOpeningHours.setIsOpen(true);
            } else {
                getInfoDay(restaurantHours, currentDay);

                // Next Opening
                if (theOpeningHours.getOpeningDayCase() < 2) {
                    searchNextOpenDay(restaurantHours, currentDay);
                }
                //Closed and is not going to open the next Day
                nextOpenDay = Go4LunchHelper.getDayString(theOpeningHours.getNextOpenDay());
                //Closed and can open the next day or more
                if ((theOpeningHours.getOpeningDayCase() < 3) || (theOpeningHours.getOpeningDayCase() == CASE_5_CLOSED_TODAY_OPEN_AT_ON)) {
                    time = Go4LunchHelper.getCurrentTimeFormatted(theOpeningHours.getNextOpenHour());
                }
                //Open and close at :
                if ((theOpeningHours.getOpeningDayCase() == CASE_3_OPEN_UNTIL) || (theOpeningHours.getOpeningDayCase() == CASE_7_OPEN_UNTIL_CLOSING_AM)) {
                    time = Go4LunchHelper.getCurrentTimeFormatted(theOpeningHours.getCloseHour());
                }
            }
            theOpeningHours.setDescription(getDescriptionText(context, theOpeningHours.getOpeningDayCase(), time, nextOpenDay));
        }
        return theOpeningHours;
    }

    /**
     * Get the text description in function of the case of the restaurant
     * @param context : object : Context
     * @param mCase : int : case of the restaurant
     * @param time : string : time for closing or opening
     * @param nextOpenDay : string : next opening day
     * @return string : the description
     */

    private String getDescriptionText(Context context, int mCase, @Nullable String time, @Nullable String nextOpenDay) {
        Resources resources = context.getResources();
        switch (mCase) {
            case CASE_0_CLOSED_TODAY_OPEN_AT:   // closed today but open tomorrow
                return resources.getString(R.string.restaurant_closed_today) + " " + time;
            case CASE_1_CLOSED_OPEN_AT_ON:  // closed (end of day) open on an other day than tomorrow
                return resources.getString(R.string.restaurant_closed_open_at) + " " + time
                        + " " + context.getResources().getString(R.string.text_restaurant_closed_open_at_on) + " " + nextOpenDay;
            case CASE_2_CLOSED_OPEN_AT:  // closed (end of day or not end of day) and open tomorrow
                return resources.getString(R.string.restaurant_closed_open_at) + " " + time;
            case CASE_3_OPEN_UNTIL:   // open until
            case CASE_7_OPEN_UNTIL_CLOSING_AM:   // open until the closing on the next day in the morning
                return resources.getString(R.string.restaurant_open_until) + " " + time;
            case CASE_4_CLOSING_SOON:   //closing soon
                return resources.getString(R.string.restaurant_closing_soon);
            case CASE_5_CLOSED_TODAY_OPEN_AT_ON:   // closed today but open on an other day than tomorrow
                return resources.getString(R.string.restaurant_closed_today) + " " + time
                        + " " + resources.getString(R.string.restaurant_on) + " " + nextOpenDay;
            case CASE_6_OPEN_24_7:   // open 24/7
                return resources.getString(R.string.restaurant_open_h24);
            default:
                return "";
        }
    }


    private void searchNextOpenDay(RestaurantDetail.OpeningHours restaurantHours, int currentDay) {
        int addDay = 0;
        int searchDay = currentDay;

        while (theOpeningHours.getNextOpenDay() == 9) {
            if (searchDay == 6) {
                searchDay = 0;
            } else {
                searchDay++;
            }
            addDay++;
            theOpeningHours = getNextOpenDay(restaurantHours, theOpeningHours, searchDay);
            if (addDay > 1) {
                if (theOpeningHours.getOpeningDayCase() == CASE_2_CLOSED_OPEN_AT) {
                    theOpeningHours.setOpeningDayCase(CASE_1_CLOSED_OPEN_AT_ON);  //closed at end of the day and next opening isn't tomorrow
                } else if (theOpeningHours.getOpeningDayCase() != CASE_7_OPEN_UNTIL_CLOSING_AM) {
                    //closed today next opening day isn't tomorrow
                    theOpeningHours.setOpeningDayCase(CASE_5_CLOSED_TODAY_OPEN_AT_ON);
                }
            } else if ((addDay == 1) && (theOpeningHours.getOpeningDayCase() == CASE_1_CLOSED_OPEN_AT_ON)) {
                theOpeningHours.setOpeningDayCase(CASE_2_CLOSED_OPEN_AT);  //next opening is tomorrow
            } else if (((theOpeningHours.getNextOpenDay() - searchDay) != 0) && (theOpeningHours.getOpeningDayCase() != CASE_7_OPEN_UNTIL_CLOSING_AM)) {
                theOpeningHours.setOpeningDayCase(CASE_5_CLOSED_TODAY_OPEN_AT_ON);
            }
        }
    }

    private TheOpeningHours getNextOpenDay(RestaurantDetail.OpeningHours restaurantHours, TheOpeningHours status, int day) {
        int periodMax = restaurantHours.getPeriods().size();
        for (int index = 0; index < periodMax; index++) {
            int nextOpenDay = restaurantHours.getPeriods().get(index).getOpen().getDay();
            if (nextOpenDay >= day) {
                status.setNextOpenDay(nextOpenDay);
                return searchInfo(restaurantHours, index, nextOpenDay);
            }
        }
        return status;
    }

    private TheOpeningHours searchInfo(RestaurantDetail.OpeningHours restaurantHours, int index, int openDay) {

        //Find the next service which is not on the same day
        if (openDay == 6) {  //next service on a sunday => index = 0
            index = 0;
        }
        if (restaurantHours.getPeriods().size() <= index) {
            index = 0;
        }
        if (theOpeningHours.getOpeningDayCase() == CASE_7_OPEN_UNTIL_CLOSING_AM) { // next closing hour
            theOpeningHours.setCloseHour(Integer.parseInt(restaurantHours.getPeriods().get(index).getClose().getTime()));
        } else {  // next opening hour
            theOpeningHours.setNextOpenHour(Integer.parseInt(restaurantHours.getPeriods().get(index).getOpen().getTime()));
        }
        theOpeningHours.setNextOpenDay(restaurantHours.getPeriods().get(index).getOpen().getDay());

        return theOpeningHours;
    }

    private void getInfoDay(RestaurantDetail.OpeningHours restaurantHours, int day) {
        int info = 0;
        int infoCloseTime = 0;
        int infoPreviousDay;
        for (int index = 0; index < restaurantHours.getPeriods().size(); index++) {
            int openDay = restaurantHours.getPeriods().get(index).getOpen().getDay();
            int closeDay = restaurantHours.getPeriods().get(index).getClose().getDay();
            if (openDay == day) {
                info++;
                int openTime = Integer.parseInt(restaurantHours.getPeriods().get(index).getOpen().getTime());
                int closeTime = Integer.parseInt(restaurantHours.getPeriods().get(index).getClose().getTime());
                theOpeningHours.setCurrentOpenDay(day);
                if (info == 2) {
                    infoCloseTime = Integer.parseInt(restaurantHours.getPeriods().get(index - 1).getClose().getTime());
                }
                if (day == 0) {
                    infoPreviousDay = 6;
                } else {
                    infoPreviousDay = day - 1;
                }
                if (openDay != closeDay) {
                    theOpeningHours.setLastCloseHour(searchPreviousDay(restaurantHours, infoPreviousDay));
                }
                if (theOpeningHours.getOpeningDayCase() < 2) {
                    defineCase(openTime, closeTime, infoCloseTime);
                }
            }
        }
    }

    private void defineCase(int openTime, int closeTime, int infoCloseTime) {
        boolean isClosingSoon;
        int currentTime = Go4LunchHelper.getCurrentTime();

        if ((theOpeningHours.getOpeningDayCase() == CASE_1_CLOSED_OPEN_AT_ON) && ((infoCloseTime < currentTime) && (currentTime < openTime))) {
            theOpeningHours.setOpeningDayCase(CASE_2_CLOSED_OPEN_AT); // Closed. Open at
            theOpeningHours.setNextOpenDay(openTime);
        } else if ((theOpeningHours.getOpeningDayCase() == CASE_1_CLOSED_OPEN_AT_ON) && (currentTime == closeTime)) {
            theOpeningHours.setOpeningDayCase(CASE_2_CLOSED_OPEN_AT); // Closed. Open at
            theOpeningHours.setNextOpenHour(openTime);
        } else if (currentTime < openTime) {
            if ((theOpeningHours.getLastCloseHour() < openTime) && (currentTime < theOpeningHours.getLastCloseHour())) {
                isClosingSoon = verifyClosingSoonCase(theOpeningHours.getLastCloseHour(), currentTime);
                if (isClosingSoon) {
                    theOpeningHours.setOpeningDayCase(CASE_4_CLOSING_SOON);
                } else {
                    theOpeningHours.setOpeningDayCase(CASE_7_OPEN_UNTIL_CLOSING_AM);
                    theOpeningHours.setCloseHour(theOpeningHours.getLastCloseHour());
                }
            } else if ((theOpeningHours.getLastCloseHour() < openTime) && (currentTime > theOpeningHours.getLastCloseHour())) {
                theOpeningHours.setOpeningDayCase(CASE_2_CLOSED_OPEN_AT);
                theOpeningHours.setNextOpenHour(openTime);
            } else if ((closeTime < openTime) && (currentTime >= closeTime)) {
                theOpeningHours.setOpeningDayCase(CASE_2_CLOSED_OPEN_AT);
                theOpeningHours.setNextOpenHour(openTime);
            } else if (closeTime < openTime) {
                isClosingSoon = verifyClosingSoonCase(closeTime, currentTime);
                if (isClosingSoon) {
                    theOpeningHours.setOpeningDayCase(CASE_4_CLOSING_SOON);
                } else {
                    theOpeningHours.setOpeningDayCase(CASE_3_OPEN_UNTIL);
                    theOpeningHours.setCloseHour(closeTime);
                }
            } else {
                theOpeningHours.setOpeningDayCase(CASE_2_CLOSED_OPEN_AT); // Closed. Open at
                theOpeningHours.setNextOpenHour(openTime);
            }
        } else if ((currentTime < closeTime) || ((closeTime < openTime) && (currentTime > openTime))) {
            // 3 - Open until or 4 - Open. Closing soon
            isClosingSoon = verifyClosingSoonCase(closeTime, currentTime);
            if (isClosingSoon) {
                theOpeningHours.setOpeningDayCase(CASE_4_CLOSING_SOON);
            } else {
                theOpeningHours.setOpeningDayCase(CASE_3_OPEN_UNTIL);
            }
            theOpeningHours.setCloseHour(closeTime);
        } else if (closeTime <= currentTime) {
            theOpeningHours.setOpeningDayCase(CASE_1_CLOSED_OPEN_AT_ON);  //Closed
        } else {
            Log.i(TAG, "defineCase: nothing changed ");
        }
    }

    /**
     * Verify if the restaurant is going to close soon
     * Update the restaurant status is open or not
     * @param closeTime : int : close time
     * @param currentTime : int : current time
     * @return : boolean : true if restaurant is closing soon
     */

    private boolean verifyClosingSoonCase(int closeTime, int currentTime) {

        closeTime = Go4LunchHelper.convertTimeInMinutes(closeTime);
        int closingTimeSoon = closeTime - 30;
        currentTime = Go4LunchHelper.convertTimeInMinutes(currentTime);

        if ((currentTime >= closingTimeSoon) && (currentTime < closeTime)) {
            theOpeningHours.setIsOpen(false);
            return true;
        } else {
            theOpeningHours.setIsOpen(true);
            return false;
        }
    }

    private int searchPreviousDay(RestaurantDetail.OpeningHours restaurantHours, int infoPreviousDay) {
        int periodMax = restaurantHours.getPeriods().size();
        for (int index = periodMax -1; index >= 0; index--) {
            int previousDay = restaurantHours.getPeriods().get(index).getOpen().getDay();
            if (previousDay == infoPreviousDay) {
                return (Integer.parseInt(restaurantHours.getPeriods().get(index).getClose().getTime()));
            }
        }
        return 9;
    }


    private void displayRestaurantImage(RestaurantViewHolder restaurantViewHolder, int position) {
        if (restaurantList.get(position).getRestaurantPhotoUrl() != null) {
            Glide.with(restaurantViewHolder.binding.restaurantImagePhotoItem.getContext())
                    .load(restaurantList.get(position).getRestaurantPhotoUrl())
                    .apply(RequestOptions.centerCropTransform())
                    .into(restaurantViewHolder.binding.restaurantImagePhotoItem);
        } else {
            Glide.with(restaurantViewHolder.binding.restaurantImagePhotoItem.getContext())
                    .load(R.drawable.restaurant2)
                    .apply(RequestOptions.centerCropTransform())
                    .into(restaurantViewHolder.binding.restaurantImagePhotoItem);
        }
    }

    @Override
    public int getItemCount() {
        if (restaurantList != null)
            return restaurantList.size();
        return 0;
    }

    void update(List<Restaurant> restaurants){
        this.restaurants = restaurants;
        notifyDataSetChanged();
    }

    Restaurant getRestaurant(int position) {
        return this.restaurants.get(position);
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {

        private final RestaurantItemLayoutBinding binding;

        public RestaurantViewHolder(RestaurantItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}