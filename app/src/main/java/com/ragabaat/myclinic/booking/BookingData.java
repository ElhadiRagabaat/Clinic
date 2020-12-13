package com.ragabaat.myclinic.booking;

public class BookingData {

    private String name,phone,key,date,date1,time1;

    public BookingData() {
    }

    public BookingData(String name, String phone, String key, String date, String date1, String time1) {
        this.name = name;
        this.phone = phone;
        this.key = key;
        this.date = date;
        this.date1 = date1;
        this.time1 = time1;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}



