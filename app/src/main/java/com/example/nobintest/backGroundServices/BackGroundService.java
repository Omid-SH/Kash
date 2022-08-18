package com.example.nobintest.backGroundServices;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.nobintest.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BackGroundService extends Service {

    private static boolean alive = true;
    private HashMap<String, Thread> symbolThread;
    private HashMap<String, String> symbolNotificationId;

    private static String PRICE_GETTER_API_KEY = "cb4faa1b91bf335c168d1463bc7d7cf92347325a1a847b07d99d2cc6d232f4fc";

    private static final String CHANNEL_ID = "BackGroundService";
    private static final String TAG = "Service";
    private NotificationManager manager;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        symbolThread = new HashMap<>();
        symbolNotificationId = new HashMap<>();

        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            createNotificationChannel();
        else {
            //ServiceCompat.stopForeground(this, ServiceCompat.STOP_FOREGROUND_DETACH);
            startForeground(1, new Notification());
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.v(TAG, "Destroyed");

        // writing to shared preferences.
        this.getSharedPreferences
            ("Service", Context.MODE_PRIVATE)
            .edit().putString("service", "dead").apply();

        Log.v("Service", "Done");

        stopSelf();
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channelName = "My Background Service";
            NotificationChannel chan = new NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
            Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.mipmap.notification_alarm)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();

            startForeground(1, notification);
        }

    }

    private final IBinder mBinder = new BackGroundService.LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    public class LocalBinder extends Binder {

        public BackGroundService getService() {
            // Return this instance of SleepModeService so clients can call public methods
            return BackGroundService.this;
        }

    }

    private void checkForceDead() {
        if (symbolThread == null) {
            symbolThread = new HashMap<>();
        }
        if (symbolNotificationId == null) {
            symbolNotificationId = new HashMap<>();
        }
    }

    public synchronized void addNotificationChannel(String symbol) {

        checkForceDead();
        String id = String.valueOf(symbolNotificationId.size() + 2);
        String channelName = symbol.concat("'s Alarm");
        NotificationChannel chan = new NotificationChannel(id, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);
        symbolNotificationId.put(symbol, id);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, id);
        Notification notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.notification_alarm)
            .setContentTitle(symbol.concat("'s NotificationCenter"))
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build();
        Log.v("Service", id);
        this.startForeground(Integer.valueOf(id), notification);

    }

    public void addSymbol(String symbol, String downLimit, String upLimit) {
        addNotificationChannel(symbol);
        Thread thread = new CoinThread(symbol, BackGroundService.this, downLimit, upLimit);
        symbolThread.put(symbol, thread);
        thread.start();
    }

    private static class CoinThread extends Thread {

        private String symbol;
        private String downLimit;
        private String upLimit;
        private WeakReference<BackGroundService> backGroundServiceWeakReference;

        public CoinThread(String symbol, BackGroundService service, String downLimit, String upLimit) {
            this.symbol = symbol;
            this.downLimit = downLimit;
            this.upLimit = upLimit;
            backGroundServiceWeakReference = new WeakReference<>(service);
        }

        @Override
        public void run() {

            BackGroundService backGroundService = backGroundServiceWeakReference.get();
            if (backGroundService == null) {
                return;
            }

            while (BackGroundService.alive) {

                // waiting until connecting to Internet.
                while (!backGroundService.isNetworkConnected()) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                backGroundService.getPrice(symbol, downLimit, upLimit);
            }
        }
    }


    public void getPrice(final String symbol, final String downLimit, final String upLimit) {

        Log.v(TAG, "start Requesting");
        OkHttpClient okHttpClient = new OkHttpClient();

        HttpUrl.Builder urlBuilder;
        urlBuilder = HttpUrl.parse("https://min-api.cryptocompare.com/data/price")
            .newBuilder();

        // index >= 1
        urlBuilder.addQueryParameter("fsym", symbol);
        urlBuilder.addQueryParameter("tsyms", "USD");
        String url = urlBuilder.build().toString();


        Request request = new Request.Builder().url(url)
            .addHeader("authorization", PRICE_GETTER_API_KEY)
            .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v(TAG, e.getMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {

                    Log.v(TAG, "ResponseGot");
                    String body = response.body().string();

                    try {
                        String price = new JSONObject(body).getString("USD");

                        String status = getState(price, downLimit, upLimit);
                        postNotification(symbol, price, status);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.v(TAG, body);

                }

            }
        });
    }


    private String getState(String price, String downLimit, String upLimit) {

        Log.v("Service", "#" + price + "#" + downLimit + "#" + upLimit + "#");
        double cPrice = Double.parseDouble(price);
        double dPrice = Double.parseDouble(downLimit);
        double uPrice = Double.parseDouble(upLimit);

        if (uPrice >= cPrice && cPrice >= dPrice) {
            return "In the Safe Zone";
        } else if (cPrice > uPrice) {
            return "Coin has reached his UpperLimit";
        } else {
            return "Coin has broke his DownLimit";
        }
    }

    private void postNotification(String symbol, String price, String status) {

        String channelId = this.symbolNotificationId.get(symbol);
        Log.v("Service", channelId);
        Notification notification = new NotificationCompat.Builder(
            this,
            CHANNEL_ID)
            .setSmallIcon(R.mipmap.notification_alarm)
            .setContentTitle(symbol.concat("'s priceAlarm: ").concat(price).concat("$"))
            .setContentText(symbol.concat(": ").concat(status))
            .setDefaults(NotificationCompat.DEFAULT_SOUND)
            .build();

        synchronized (notification) {
            Log.v("Service", "Here");
            this.manager.notify(Integer.valueOf(channelId), notification);
        }
    }

    public void resetData() {
        symbolNotificationId.clear();
        symbolThread.clear();
    }

    public static void kill() {
        alive = false;
    }

    public static void alive() {
        alive = true;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
