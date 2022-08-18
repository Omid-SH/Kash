package com.example.nobintest.DataManegment;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.nobintest.R;

import java.util.ArrayList;

public class AppDataManager {
    private static AppDataManager instance = null;
    private SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor = null;

    public static AppDataManager getInstance(Context context) {
        if (instance == null) {
            instance = new AppDataManager(context);
        }
        return instance;
    }

    private AppDataManager(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.user_file_key), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static final String PROFILE_KEY = "ProfileKey";
    public static final String SECURITY_KEY = "SecurityKey";
    public static final String PIN_CODE_KEY = "PinUserKey";
    public static final String USER_NAME_KEY = "UserNameKey";
    public static final String PASS_WORD_KEY = "PassWordKey";

    public static final String USER_STATE_NEW = "NewUser126102";
    public static final String USER_STATE_GUEST = "GustUser00191";
    public static final String USER_STATE_SIGNED_IN = "SignedIn";

    public static final String SECURITY_STATE_NON = "None";
    public static final String SECURITY_STATE_PIN = "Pin";
    public static final String SECURITY_STATE_FINGERPRINT = "FingerPrint";

    public static final String BUY_CURRENCY_MARKET_KEY = "BuyCurrencyMarket";
    public static final String SELL_CURRENCY_MARKET_KEY = "SellCurrencyMarket";
    public static final String PREFERENCE_Chart_CURRENCY = "ChartCurrency";


    public String getProfileState() {
        return sharedPreferences.getString(PROFILE_KEY, USER_STATE_NEW);
    }

    public void setProfileState(String state) {
        editor.putString(PROFILE_KEY, state);
        editor.commit();
    }

    public String getSecurityState() {
        return sharedPreferences.getString(SECURITY_KEY, SECURITY_STATE_NON);
    }

    public void setUserName(String username) {
        editor.putString(USER_NAME_KEY, username);
        editor.commit();
    }

    public void setPassWord(String passWord) {
        editor.putString(PASS_WORD_KEY, passWord);
        editor.commit();
    }

    public String getUserName() {
        return sharedPreferences.getString(USER_NAME_KEY, "Guest");
    }

    public String getPassWord() {
        return sharedPreferences.getString(PASS_WORD_KEY, "1234");
    }

    public static final String[] getChartCoinList() {
        return new String[]{"BTC", "ETH", "USDT", "LTC", "XRP", "BCH", "BNB", "EOS", "XLM", "ETC", "TRX"
            , "BSV", "ADA", "CRO", "LINK", "EOS", "XTZ", "XMR", "LEO", "HT", "VET", "NEO", "DASH", "ZEC", "MKR"
            , "COMP", "REP"};
    }

    public static final String[] getNobitexMarketCoinList() {
        return new String[]{"usd", "rls", "btc", "eth", "ltc", "usdt", "xrp", "bch", "bnb", "eos", "doge",
            "xlm", "trx", "ada", "xmr", "xem", "iota", "etc", "dash", "zec", "neo", "qtum", "xtz"};
    }

    public String getBuyMarket() {
        return sharedPreferences.getString(BUY_CURRENCY_MARKET_KEY, "BTC / IRT");
    }

    public void setBuyMarket(String string) {
        editor.putString(BUY_CURRENCY_MARKET_KEY, string);
        editor.commit();
    }

    public String getSellMarket() {
        return sharedPreferences.getString(SELL_CURRENCY_MARKET_KEY, "BTC / IRT");
    }

    public void setSellMarket(String string) {
        editor.putString(SELL_CURRENCY_MARKET_KEY, string);
        editor.commit();
    }

    public static ArrayList<String> getAllMarketList() {
        ArrayList<String> arrayList = new ArrayList<String>() {{
            add("BTC / IRT");
            add("ETH / IRT");
            add("LTC / IRT");
            add("XRP / IRT");
            add("BCH / IRT");
            add("BNB / IRT");
            add("EOS / IRT");
            add("XLM / IRT");
            add("ETC / IRT");
            add("TRX / IRT");
            add("USDT / IRT");
        }};
        arrayList.addAll(new ArrayList<String>() {{
            add("BTC / USDT");
            add("ETH / USDT");
            add("LTC / USDT");
            add("XRP / USDT");
            add("BCH / USDT");
            add("BNB / USDT");
            add("EOS / USDT");
            add("XLM / USDT");
            add("ETC / USDT");
            add("TRX / USDT");
        }});
        return arrayList;
    }
}
