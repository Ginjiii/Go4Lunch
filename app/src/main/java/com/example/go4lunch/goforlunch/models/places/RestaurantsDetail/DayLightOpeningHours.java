package com.example.go4lunch.goforlunch.models.places.RestaurantsDetail;

public class DayLightOpeningHours {

    private int currentOpenDay;
    private int dayCase;
    private int nextOpenHour;
    private int closeHour;
    private boolean isOpen;
    private String description;
    private int nextOpenDay;
    private int lastCloseHour;

    public DayLightOpeningHours() {}

    public DayLightOpeningHours(int currentOpenDay, int dayCase, int nextOpenHour, int closeHour,
                           boolean isOpen, String description, int nextOpenDay, int lastCloseHour) {
        currentOpenDay = currentOpenDay;
        dayCase = dayCase;
        nextOpenHour = nextOpenHour;
        closeHour = closeHour;
        isOpen = isOpen;
        description = description;
        nextOpenDay = nextOpenDay;
        lastCloseHour = lastCloseHour;
    }

    public int getCurrentOpenDay() {
        return currentOpenDay;
    }

    public void setCurrentOpenDay(int currentOpenDay) {
        currentOpenDay = currentOpenDay;
    }

    public int getDayCase() {
        return dayCase;
    }

    public void setDayCase(int dayCase) {
        dayCase = dayCase;
    }

    public int getNextOpenHour() {
        return nextOpenHour;
    }

    public void setNextOpenHour(int nextOpenHour) {
        nextOpenHour = nextOpenHour;
    }

    public int getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(int closeHour) {
        closeHour = closeHour;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean isOpen) {
        isOpen = isOpen;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        description = description;
    }

    public int getNextOpenDay() {
        return nextOpenDay;
    }

    public void setNextOpenDay(int nextOpenDay) {
        nextOpenDay = nextOpenDay;
    }

    public int getLastCloseHour() {
        return lastCloseHour;
    }

    public void setLastCloseHour(int lastCloseHour) {
        lastCloseHour = lastCloseHour;
    }
}
