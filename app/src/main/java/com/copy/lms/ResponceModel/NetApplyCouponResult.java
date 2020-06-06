package com.copy.lms.ResponceModel;

import java.util.ArrayList;

public class NetApplyCouponResult {
    private ArrayList<NetApplyCouponResultData> data;
    private double subtotal;
    private NetApplyCouponResultCoupon_data coupon_data;
    private double final_total;
    private NetApplyCouponResultTax_data tax_data;

    public ArrayList<NetApplyCouponResultData> getData() {
        return this.data;
    }

    public void setData(ArrayList<NetApplyCouponResultData> data) {
        this.data = data;
    }

    public double getSubtotal() {
        return this.subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public NetApplyCouponResultCoupon_data getCoupon_data() {
        return this.coupon_data;
    }

    public void setCoupon_data(NetApplyCouponResultCoupon_data coupon_data) {
        this.coupon_data = coupon_data;
    }

    public double getFinal_total() {
        return this.final_total;
    }

    public void setFinal_total(double final_total) {
        this.final_total = final_total;
    }

    public NetApplyCouponResultTax_data getTax_data() {
        return this.tax_data;
    }

    public void setTax_data(NetApplyCouponResultTax_data tax_data) {
        this.tax_data = tax_data;
    }
}
