package com.copy.lms.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;


public class SignInModel extends BaseObservable {

    private String email;
    private String pass;
    private String userName;


    private String mobail;
    private BtnClick signInBtnClick;


    public interface BtnClick {

        void onSignUpBtnClick();

    }


    public SignInModel(BtnClick signInBtnClick) {
        this.signInBtnClick = signInBtnClick;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
//        notifyPropertyChanged(BR.email);
        notifyChange();
    }

    @Bindable
    public String getPass() {
        return pass;
    }


    public void setPass(String pass) {
        this.pass = pass;
//        notifyPropertyChanged(BR.pass);
        notifyChange();
    }

    @Bindable
    public String getMobail() {
        return mobail;
    }

    public void setMobail(String mobail) {
        this.mobail = mobail;
        notifyChange();

    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyChange();
    }


    private boolean isnum(String email) {
        try {
            long mobile = Long.parseLong(email);
            return true;
        } catch (NumberFormatException nfe) {

            return false;

        }

    }

    public void signUpBtn() {
        if (signInBtnClick != null) {
            signInBtnClick.onSignUpBtnClick();
        }
    }


}
