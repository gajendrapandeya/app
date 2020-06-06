package com.copy.lms.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * Created by rahul.patel on 12/1/2016.
 */

public class SignUpModel extends BaseObservable {

    private String email, conformPass, userType, address, language, name, languageId;
    private String pass;
    private String userName,lastName;
    private int userId;


    private String mobail;
    private BtnClick onSignUpBtnClick;


    public interface BtnClick {
        void onSignInBtnClick();


        void onGuestUser();


    }

    @Bindable
    public String getConformPass() {
        return conformPass;
    }

    public void setConformPass(String conformPass) {
        this.conformPass = conformPass;
        notifyChange();
    }

    @Bindable
    public String getUserType() {
        return userType;

    }

    public void setUserType(String userType) {
        this.userType = userType;
        notifyChange();
    }

    @Bindable
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
        notifyChange();
    }

    @Bindable
    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
        notifyChange();
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyChange();
    }

    @Bindable
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
        notifyChange();
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyChange();
    }


    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyChange();
    }

    public SignUpModel(BtnClick onSignUpBtnClick) {
        this.onSignUpBtnClick = onSignUpBtnClick;
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

    public void signInBtn() {
        if (onSignUpBtnClick != null) {
            onSignUpBtnClick.onSignInBtnClick();
        }
    }

    public void guestUser() {
        if (onSignUpBtnClick != null) {
            onSignUpBtnClick.onGuestUser();
        }
    }


}
