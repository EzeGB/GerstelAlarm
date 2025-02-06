package com.example.gerstelalarm;

public class AlarmModel {
    String alarmName, alarmHours, alarmMinutes;

    public AlarmModel(String alarmName, String alarmTime, String alarmMinutes) {
        this.alarmName = alarmName;
        this.alarmHours = alarmTime;
        this.alarmMinutes = alarmMinutes;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getAlarmHours() {
        return alarmHours;
    }

    public void setAlarmHours(String alarmHours) {
        this.alarmHours = alarmHours;
    }

    public String getAlarmMinutes() {
        return alarmMinutes;
    }

    public void setAlarmMinutes(String alarmMinutes) {
        this.alarmMinutes = alarmMinutes;
    }

    public void setAll(String alarmName, String alarmTime, String alarmMinutes) {
        this.alarmName = alarmName;
        this.alarmHours = alarmTime;
        this.alarmMinutes = alarmMinutes;
    }
}
