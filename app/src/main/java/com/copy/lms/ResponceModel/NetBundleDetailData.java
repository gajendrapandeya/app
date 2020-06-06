package com.copy.lms.ResponceModel;

public class NetBundleDetailData {
    private NetBundleDetailDataResult result;
    private String status;
    private boolean purchased_bundle;

    public boolean isPurchased_bundle() {
        return purchased_bundle;
    }

    public void setPurchased_bundle(boolean purchased_bundle) {
        this.purchased_bundle = purchased_bundle;
    }

    public NetBundleDetailDataResult getResult() {
        return this.result;
    }

    public void setResult(NetBundleDetailDataResult result) {
        this.result = result;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
