package com.copy.lms.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class InvoiceListModel extends BaseObservable {
    private String noDataLable, noDataMessage, noDataButtonText;
    private ArrayList<InvoiceModel> arrayList;
    private boolean apiCallActive = true;
    private Drawable noDataImage;

    @Bindable
    public boolean isApiCallActive() {
        return apiCallActive;
    }

    public void setApiCallActive(boolean apiCallActive) {
        this.apiCallActive = apiCallActive;
        notifyChange();
    }

    public String getNoDataButtonText() {
        return noDataButtonText;
    }

    public void setNoDataButtonText(String noDataButtonText) {
        this.noDataButtonText = noDataButtonText;
    }

    public Drawable getNoDataImage() {
        return noDataImage;
    }

    public void setNoDataImage(Drawable noDataImage) {
        this.noDataImage = noDataImage;
    }

    public String getNoDataLable() {
        return noDataLable;
    }

    public void setNoDataLable(String noDataLable) {
        this.noDataLable = noDataLable;
    }

    public String getNoDataMessage() {
        return noDataMessage;
    }

    public void setNoDataMessage(String noDataMessage) {
        this.noDataMessage = noDataMessage;
    }

    @Bindable
    public ArrayList<InvoiceModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<InvoiceModel> arrayList) {
        this.arrayList = arrayList;
        notifyChange();
    }
}
