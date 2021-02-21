package com.example.go4lunch.goforlunch.models;

public class TheOpeningHours {


    private int currentOpenDay;
    private int openingDayCase;
    private int nextOpenHour;
    private int closeHour;
    private boolean isOpen;
    private String description;
    private int nextOpenDay;
    private int lastCloseHour;

    public TheOpeningHours() {}

    public TheOpeningHours(int mCurrentOpenDay, int mOpeningDayCase, int mNextOpenHour, int mCloseHour,
                           boolean mIsOpen, String mDescription, int mNextOpenDay, int mLastCloseHour) {
        currentOpenDay = mCurrentOpenDay;
        openingDayCase = mOpeningDayCase;
        nextOpenHour = mNextOpenHour;
        closeHour = mCloseHour;
        isOpen = mIsOpen;
        description = mDescription;
        nextOpenDay = mNextOpenDay;
        lastCloseHour = mLastCloseHour;
    }

    public int getCurrentOpenDay() {
        return currentOpenDay;
    }

    public void setCurrentOpenDay(int mCurrentOpenDay) {
        currentOpenDay = mCurrentOpenDay;
    }

    public int getOpeningDayCase() {
        return openingDayCase;
    }

    public void setOpeningDayCase(int mOpeningDayCase) {
        openingDayCase = mOpeningDayCase;
    }

    public int getNextOpenHour() {
        return nextOpenHour;
    }

    public void setNextOpenHour(int mNextOpenHour) {
        nextOpenHour = mNextOpenHour;
    }

    public int getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(int mCloseHour) {
        closeHour = mCloseHour;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean mIsOpen) {
        isOpen = mIsOpen;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String mDescription) {
        description = mDescription;
    }

    public int getNextOpenDay() {
        return nextOpenDay;
    }

    public void setNextOpenDay(int mNextOpenDay) {
        nextOpenDay = mNextOpenDay;
    }

    public int getLastCloseHour() {
        return lastCloseHour;
    }

    public void setLastCloseHour(int mLastCloseHour) {
        lastCloseHour = mLastCloseHour;
    }
}