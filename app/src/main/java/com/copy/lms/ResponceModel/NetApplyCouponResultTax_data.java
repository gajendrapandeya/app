package com.copy.lms.ResponceModel;

import java.util.ArrayList;

public class NetApplyCouponResultTax_data {
    private ArrayList<NetApplyCouponResultTax_dataTaxes> taxes;
    private double total_tax;

    public ArrayList<NetApplyCouponResultTax_dataTaxes> getTaxes() {
        return this.taxes;
    }

    public void setTaxes(ArrayList<NetApplyCouponResultTax_dataTaxes> taxes) {
        this.taxes = taxes;
    }

    public double getTotal_tax() {
        return this.total_tax;
    }

    public void setTotal_tax(double total_tax) {
        this.total_tax = total_tax;
    }
}
