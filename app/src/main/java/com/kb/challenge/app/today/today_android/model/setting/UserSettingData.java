package com.kb.challenge.app.today.today_android.model.setting;

import java.sql.Time;

/**
 * Created by shineeseo on 2018. 11. 13..
 */

public class UserSettingData {
    public String name;
    public String profile_url;
    public String goal;
    public int goal_money;
    public Time push_time;

    public UserSettingData(String name, String profile_url, String goal, int goal_money, Time push_time) {
        this.name = name;
        this.profile_url = profile_url;
        this.goal = goal;
        this.goal_money = goal_money;
        this.push_time = push_time;
    }
    public UserSettingData(String name,  String goal, int goal_money, Time push_time) {
        this.name = name;
        this.goal = goal;
        this.goal_money = goal_money;
        this.push_time = push_time;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public void setGoal_money(int goal_money) {
        this.goal_money = goal_money;
    }

    public void setPush_time(Time push_time) {
        this.push_time = push_time;
    }

    public String getName() {
        return name;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public String getGoal() {
        return goal;
    }

    public int getGoal_money() {
        return goal_money;
    }

    public Time getPush_time() {
        return push_time;
    }

    @Override
    public String toString() {
        return "UserSettingData{" +
                "name='" + name + '\'' +
                ", profile_url='" + profile_url + '\'' +
                ", goal='" + goal + '\'' +
                ", goal_money=" + goal_money +
                ", push_time=" + push_time +
                '}';
    }
}
