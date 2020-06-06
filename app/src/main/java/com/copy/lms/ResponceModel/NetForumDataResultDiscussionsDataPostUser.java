package com.copy.lms.ResponceModel;

import android.os.Parcel;
import android.os.Parcelable;

public class NetForumDataResultDiscussionsDataPostUser implements Parcelable {
    private String country;
    private String last_login_at;
    private String gender;
    private String confirmation_code;
    private String city;
    private String timezone;
    private String avatar_location;
    private String created_at;
    private String uuid;
    private boolean confirmed;
    private String avatar_type;
    private String updated_at;
    private String last_login_ip;
    private int id;
    private String state;
    private String first_name;
    private String email;
    private String pincode;
    private String image;
    private String password_changed_at;
    private String address;
    private String last_name;
    private boolean active;
    private String deleted_at;
    private String full_name;
    private String phone;
    private String dob;

    protected NetForumDataResultDiscussionsDataPostUser(Parcel in) {
        country = in.readString();
        last_login_at = in.readString();
        gender = in.readString();
        confirmation_code = in.readString();
        city = in.readString();
        timezone = in.readString();
        avatar_location = in.readString();
        created_at = in.readString();
        uuid = in.readString();
        confirmed = in.readByte() != 0;
        avatar_type = in.readString();
        updated_at = in.readString();
        last_login_ip = in.readString();
        id = in.readInt();
        state = in.readString();
        first_name = in.readString();
        email = in.readString();
        pincode = in.readString();
        image = in.readString();
        password_changed_at = in.readString();
        address = in.readString();
        last_name = in.readString();
        active = in.readByte() != 0;
        deleted_at = in.readString();
        full_name = in.readString();
        phone = in.readString();
        dob = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(country);
        dest.writeString(last_login_at);
        dest.writeString(gender);
        dest.writeString(confirmation_code);
        dest.writeString(city);
        dest.writeString(timezone);
        dest.writeString(avatar_location);
        dest.writeString(created_at);
        dest.writeString(uuid);
        dest.writeByte((byte) (confirmed ? 1 : 0));
        dest.writeString(avatar_type);
        dest.writeString(updated_at);
        dest.writeString(last_login_ip);
        dest.writeInt(id);
        dest.writeString(state);
        dest.writeString(first_name);
        dest.writeString(email);
        dest.writeString(pincode);
        dest.writeString(image);
        dest.writeString(password_changed_at);
        dest.writeString(address);
        dest.writeString(last_name);
        dest.writeByte((byte) (active ? 1 : 0));
        dest.writeString(deleted_at);
        dest.writeString(full_name);
        dest.writeString(phone);
        dest.writeString(dob);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NetForumDataResultDiscussionsDataPostUser> CREATOR = new Creator<NetForumDataResultDiscussionsDataPostUser>() {
        @Override
        public NetForumDataResultDiscussionsDataPostUser createFromParcel(Parcel in) {
            return new NetForumDataResultDiscussionsDataPostUser(in);
        }

        @Override
        public NetForumDataResultDiscussionsDataPostUser[] newArray(int size) {
            return new NetForumDataResultDiscussionsDataPostUser[size];
        }
    };

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLast_login_at() {
        return this.last_login_at;
    }

    public void setLast_login_at(String last_login_at) {
        this.last_login_at = last_login_at;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getConfirmation_code() {
        return this.confirmation_code;
    }

    public void setConfirmation_code(String confirmation_code) {
        this.confirmation_code = confirmation_code;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTimezone() {
        return this.timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getAvatar_location() {
        return this.avatar_location;
    }

    public void setAvatar_location(String avatar_location) {
        this.avatar_location = avatar_location;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean getConfirmed() {
        return this.confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getAvatar_type() {
        return this.avatar_type;
    }

    public void setAvatar_type(String avatar_type) {
        this.avatar_type = avatar_type;
    }

    public String getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getLast_login_ip() {
        return this.last_login_ip;
    }

    public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFirst_name() {
        return this.first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPincode() {
        return this.pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword_changed_at() {
        return this.password_changed_at;
    }

    public void setPassword_changed_at(String password_changed_at) {
        this.password_changed_at = password_changed_at;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLast_name() {
        return this.last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDeleted_at() {
        return this.deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getFull_name() {
        return this.full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return this.dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
