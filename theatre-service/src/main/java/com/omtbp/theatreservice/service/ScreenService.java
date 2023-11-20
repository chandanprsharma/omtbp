package com.omtbp.theatreservice.service;

import com.omtbp.theatreservice.entity.Screen;

import java.util.List;

public interface ScreenService {
    Screen saveScreen(Screen screen);
    List<Screen> saveScreens(List<Screen> screens);
    List<Screen> getScreenByScreenName(String screenName);
    List<Screen> getScreenByTheatreId(String theatreId);
    Screen getScreenById(Long screenId);
}