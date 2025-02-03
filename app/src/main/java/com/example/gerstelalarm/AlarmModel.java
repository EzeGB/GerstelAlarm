package com.example.gerstelalarm;

public class AlarmModel {
    String alarmName;
    String alarmTime;

    public AlarmModel(String alarmName, String alarmTime) {
        this.alarmName = alarmName;
        this.alarmTime = alarmTime;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }
}
