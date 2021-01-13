package com.example.go4lunch.goforlunch.DI;

import com.example.go4lunch.goforlunch.service.Api;
import com.example.go4lunch.goforlunch.service.Go4LunchService;

public class DI {

    private static final Api service = new Go4LunchService();

    public static Api getGo4LunchApiService(){ return service; }

    //Will serve for the unit test
    public static Api getNewInstanceApiService() {
        return new Go4LunchService();
    }
}