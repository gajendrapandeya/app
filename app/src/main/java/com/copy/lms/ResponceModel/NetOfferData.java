package com.copy.lms.ResponceModel;

import java.util.ArrayList;

public class NetOfferData {
    private ArrayList<NetOfferDataCoupons> coupons;
    private String status;

    public ArrayList<NetOfferDataCoupons> getCoupons() {
        return this.coupons;
    }

    public void setCoupons(ArrayList<NetOfferDataCoupons> coupons) {
        this.coupons = coupons;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
