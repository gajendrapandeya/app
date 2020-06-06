package com.copy.lms;

import android.os.Environment;

import java.io.File;

public class Config {
    /*Google Signin Secret*/
    public static final String CLIENT_SECRET = "qX7yLHyjxtUPEWhklo1WCxqTIqlwIe30fkuelpXq";
    public static final String CLIENT_ID = "1";


    /*For Youtube Video play*/
    public static final String DEVELOPER_KEY = "AIzaSyAsgtOvy1dr8jcVSUFqy63wB2X8KW4TFT0";


    /*Twitter Keys*/
    public static final String CONSUMER_KEY = "LHsc14ugIKld1D1RYKxDT8ys1";
    public static final String CONSUMER_SECRET = "CCW189NHTIZUrJzGzAwswSH1eMyqOSndf5k2tL72VSAsjc2oB6";

    /*Gmail Social login*/
    public static final String GOOGLE_SERVER_CLIENT_ID = "q-27jvpjg8npldch4sb3sp5l1aqjsi71vk.apps.googleusercontent.com";

    //TODO also change in string.xml file
    public static final String FACEBOOK_APP_ID = "583314612304158";
    public static final String FACEBOOK_LOGIN_PROTOCOL = "fb583314612304158";



    /*payment id*/
    public static final String STRIPE_PUBLISHER_KEY = "q";
    public static final String PAYPAL_CLIENT_ID = "q";


    /*Base Urls*/
    public static final String BASE_URL = "https://app.copykalam.com";
    public static final String API_VERSION = "/api/v1";
    public static final String HOME_IMAGE_URL = "/storage/uploads/placeholder-3.jpg";


    /*Folder Name*/
    public static final String FOLDER_NAME = "Lms";
    public static final File LmsFolder = new File(Environment.getExternalStorageDirectory() + "/" + FOLDER_NAME + "/LMS/");

    /*social or payment functionality*/
    public static final boolean ISFACEBOOK = true;
    public static final boolean ISGOOGLE = false;
    public static final boolean ISTWITTER = false ;
    public static final boolean ISSTRIPE = false;
    public static final boolean ISPAYPAL = false;
    public static final boolean ISOFFLINE = true;
}