package com.example.afinal;

import java.util.Date;

public class Ride {
    private String Start;
    private String Destination;
    private String Date;
    private String Time;
    private int Passengers;

    public String getTime() { return Time; }

    public void setTime(String time) { Time = time; }

    public String getStart() {
        return Start;
    }

    public void setStart(String start) {
        Start = start;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getPassengers() {
        return Passengers;
    }

    public void setPassengers(int passengers) {
        Passengers = passengers;
    }
}
