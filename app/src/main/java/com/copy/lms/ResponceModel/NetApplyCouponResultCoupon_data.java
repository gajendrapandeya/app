package com.copy.lms.ResponceModel;

public class NetApplyCouponResultCoupon_data {
    private int amount;
    private String code;
    private String description;
    private String created_at;
    private int type;
    private String expires_at;
    private int min_price;
    private String updated_at;
    private int per_user_limit;
    private String name;
    private double total_coupon_discount;
    private int id;
    private int status;

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getExpires_at() {
        return this.expires_at;
    }

    public void setExpires_at(String expires_at) {
        this.expires_at = expires_at;
    }

    public int getMin_price() {
        return this.min_price;
    }

    public void setMin_price(int min_price) {
        this.min_price = min_price;
    }

    public String getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getPer_user_limit() {
        return this.per_user_limit;
    }

    public void setPer_user_limit(int per_user_limit) {
        this.per_user_limit = per_user_limit;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotal_coupon_discount() {
        return this.total_coupon_discount;
    }

    public void setTotal_coupon_discount(double total_coupon_discount) {
        this.total_coupon_discount = total_coupon_discount;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
