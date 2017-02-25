package bjorn.vuylsteker.tyzer34.PPG.util;


import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

import bjorn.vuylsteker.tyzer34.PPG.MainActivity;
import bjorn.vuylsteker.tyzer34.PPG.util.db.DataHandler;

public class Global extends Application {

    private static String lastSearch = "";
    private static String lastPopURL = "";
    private static String lastCollectionURL = "";

    private static String mail = "";
    private static String pass = "";
    private static boolean loggedIn = false;
    private static String monetaryUnit = "€";
    private static int lastScannedCount = 10;
    private static int collectionCount = 0;
    private static int wantlistCount = 0;

    private static final String PARAM_LOGGEDIN = "loggedIn";
    private static final String PARAM_MONETUNIT = "monetaryUnit";
    private static final String PARAM_LASTSCANNEDCOUNT = "lastScannedCount";
    private static final String PARAM_COLLECTIONCOUNT = "collectionCount";
    private static final String PARAM_WANTLISTCOUNT = "wantlistCount";
    private static HashMap<String, String> settings = new HashMap<>();

    private static DataHandler dbHandler;

    public final static String filename = "settings";

    public static void loadSettings() {
        if (fileExistance(filename)) {
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(MainActivity.current.openFileInput(filename));
                BufferedReader r = new BufferedReader(inputStreamReader);
                String line;
                while ((line = r.readLine()) != null) {
                    String[] entry = TextUtils.split(line, ":");
                    if (entry.length > 1) {
                        switch (entry[0]) {
                            case PARAM_LOGGEDIN:
                                setLoggedIn(Boolean.parseBoolean(entry[1]));
                                break;
                            case PARAM_MONETUNIT:
                                setMonetaryUnit(entry[1].trim());
                                break;
                            case PARAM_LASTSCANNEDCOUNT:
                                setLastScannedCount(Integer.parseInt(entry[1]));
                                break;
                            case PARAM_COLLECTIONCOUNT:
                                setCollectionCount(Integer.parseInt(entry[1]));
                                break;
                            case PARAM_WANTLISTCOUNT:
                                setWantlistCount(Integer.parseInt(entry[1]));
                                break;
                        }
                    }
                }
                r.close();
                inputStreamReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Set Default Settings first timer
            setMonetaryUnit("$");
            setLastScannedCount(10);
            setLoggedIn(false);
            setCollectionCount(0);
            setWantlistCount(0);
        }
    }

    public static void storeSettings() {
        settings.clear();
        settings.put(PARAM_COLLECTIONCOUNT, String.valueOf(getCollectionCount()));
        settings.put(PARAM_WANTLISTCOUNT, String.valueOf(getWantlistCount()));
        settings.put(PARAM_LASTSCANNEDCOUNT, String.valueOf(getLastScannedCount()));
        settings.put(PARAM_MONETUNIT, getMonetaryUnit());
        settings.put(PARAM_LOGGEDIN, String.valueOf(isLoggedIn()));
        try {
            FileOutputStream outputStream = MainActivity.current.openFileOutput(filename, Context.MODE_PRIVATE);
            String write = "";
            for (int i = 0; i < settings.size(); i++){
                String key = (String) settings.keySet().toArray()[i];
                String value = settings.get(key);
                write += key + ":" + value + "\n";
            }
            outputStream.write(write.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean fileExistance(String fname){
        File file =  MainActivity.current.getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    public static void setLastSearch(String query){
        lastSearch = query;
    }
    public static String getLastSearch(){
        return lastSearch;
    }

    public static void setLastPopURL(String url) {
        lastPopURL = url;
    }
    public static String getLastPopURL(){
        return lastPopURL;
    }

    public static void setLastCollectionURL(String url) {
        lastCollectionURL = url;
    }
    public static String getLastCollectionURL(){
        return lastCollectionURL;
    }

    public static void setDbHandler(DataHandler dbh){
        dbHandler = dbh;
    }
    public static DataHandler getDbHandler(){
        return dbHandler;
    }

    public static String getPass() {
        return pass;
    }

    public static void setPass(String pass) {
        Global.pass = pass.trim();
    }

    public static String getMail() {
        return mail;
    }

    public static void setMail(String mail) {
        Global.mail = mail.trim();
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(boolean loggedIn) {
        Global.loggedIn = loggedIn;
        storeSettings();
        if (loggedIn) {
            MainActivity.loginItem.setTitle("Logout");
            MainActivity.collectionItem.setVisibility(true);
            MainActivity.wantlistItem.setVisibility(true);
            MainActivity.statsItem.setVisibility(true);
            MainActivity.friendsItem.setVisibility(true);
            MainActivity.registerItem.setVisibility(false);
        } else {
            MainActivity.loginItem.setTitle("Login");
            MainActivity.collectionItem.setVisibility(false);
            MainActivity.wantlistItem.setVisibility(false);
            MainActivity.statsItem.setVisibility(false);
            MainActivity.friendsItem.setVisibility(false);
            MainActivity.registerItem.setVisibility(true);
        }
        MainActivity.update();
    }

    public static String getMonetaryUnit() {
        return monetaryUnit;
    }

    public static void setMonetaryUnit(String monetaryUnit) {
        Global.monetaryUnit = monetaryUnit;
    }

    public static int getLastScannedCount() {
        return lastScannedCount;
    }

    public static void setLastScannedCount(int lastScannedCount) {
        Global.lastScannedCount = lastScannedCount;
    }

    public static int getCollectionCount() {
        return collectionCount;
    }

    public static void setCollectionCount(int collectionCount) {
        Global.collectionCount = collectionCount;
        MainActivity.collectionItem.setCount(String.valueOf(collectionCount));
    }

    public static int getWantlistCount() {
        return wantlistCount;
    }

    public static void setWantlistCount(int wantlistCount) {
        Global.wantlistCount = wantlistCount;
        MainActivity.wantlistItem.setCount(String.valueOf(wantlistCount));
    }
}