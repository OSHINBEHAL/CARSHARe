package com.example.raman.carshare.activity;

public class SingleRowRider {
     String name,source,dest,phone,charges,time;
     int memberId;

    public SingleRowRider(String name, String source,String dest, String phone,String charges,String time,int memberId) {
        this.name = name;
        this.source = source;
        this.dest = dest;
        this.time=time;
        this.phone=phone;
        this.charges=charges;
        this.memberId=memberId;

    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public String getDest() {
        return dest;
    }

    public String getPhone() {
        return phone;
    }

    public String getCharges() {
        return charges;
    }

    public String getTime() {
        return time;
    }

    public int getMemberId() {
        return memberId;
    }
}