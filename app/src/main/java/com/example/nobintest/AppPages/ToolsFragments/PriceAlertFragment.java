package com.example.nobintest.AppPages.ToolsFragments;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieAnimationView;
import com.example.nobintest.DataManegment.AppDataManager;
import com.example.nobintest.R;
import com.example.nobintest.backGroundServices.BackGroundService;
import com.example.nobintest.graphActivity.GraphActivity;

import java.util.concurrent.atomic.AtomicBoolean;

public class PriceAlertFragment extends Fragment {

    private String symbol;

    private Intent backGroundServiceIntent;
    private BackGroundService backGroundService;
    private AtomicBoolean isConnected;
    private boolean firstLunch;
    private boolean serviceHasBeenStarted = false;
    private boolean flag = true; // it is the first time that service is creating.

    private EditText down;
    private EditText up;
    private TextView selectButton;
    private Button closeAll;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tools_price_alert, container, false);

        firstLunch = true;
        isConnected = new AtomicBoolean(false);
        backGroundServiceIntent = new Intent(getContext(), BackGroundService.class);

        setUp(view);

        SharedPreferences prefs = null;

        prefs = getActivity().getSharedPreferences(
            "Service", Context.MODE_PRIVATE);

        serviceHasBeenStarted = prefs.getString("service", "dead").equals("alive");

        if (serviceHasBeenStarted) {
            Log.v("Service", "alive");
            flag = false;
            // binding fragment to created service.
            getActivity().bindService(backGroundServiceIntent, mBackGroundServiceConnection, Context.BIND_AUTO_CREATE);

        } else {
            BackGroundService.alive();
            Log.v("Service", "dead");
        }

        return view;
    }

    private void setUp(View view) {

        selectButton = (TextView) view.findViewById(R.id.coin);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDataManager appDataManager = AppDataManager.getInstance(getActivity());
                final String[] strings = appDataManager.getChartCoinList();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pick a coin");
                builder.setItems(strings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        symbol = strings[which];
                        selectButton.setText(symbol);
                    }
                });
                builder.show();
            }
        });

        Button button = (Button) view.findViewById(R.id.setAlarm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!serviceHasBeenStarted) {
                    BackGroundService.alive();
                    Log.v("Service", "firstTimeGonnaCreateService");
                    // start the service for the first time..
                    backGroundServiceIntent = new Intent(getContext(), BackGroundService.class);
                    getActivity().startForegroundService(backGroundServiceIntent);
                    serviceHasBeenStarted = true;
                    getActivity().bindService(backGroundServiceIntent, mBackGroundServiceConnection, Context.BIND_AUTO_CREATE);

                    // writing to shared preferences.
                    getActivity().getSharedPreferences
                        ("Service", Context.MODE_PRIVATE)
                        .edit().putString("service", "alive").apply();

                } else {
                    if (backGroundService == null) {
                        Toast.makeText(getContext(), "Connecting to service,please wait and try some moments later", Toast.LENGTH_LONG).show();
                    } else {

                        if (validRequest()) {
                            backGroundService.addSymbol(symbol, down.getText().toString(), up.getText().toString());
                        } else {
                            Toast.makeText(getContext(), "Invalid input Values", Toast.LENGTH_LONG).show();
                        }
                    }
                }

            }
        });

        closeAll = (Button) view.findViewById(R.id.closeAll);
        closeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!serviceHasBeenStarted) {
                    return;
                }

                isConnected.set(false);
                serviceHasBeenStarted = false;
                firstLunch = true;
                flag = true;

                BackGroundService.kill();
                getActivity().unbindService(mBackGroundServiceConnection);
                getActivity().stopService(new Intent(getActivity(), BackGroundService.class));

                Log.v("Amir", "done");
                // writing to shared preferences.
                PriceAlertFragment.this.getActivity().getSharedPreferences
                    ("Service", Context.MODE_PRIVATE)
                    .edit().putString("service", "alive").apply();

            }
        });


        selectButton = (TextView) view.findViewById(R.id.coin);
        down = (EditText) view.findViewById(R.id.downLimit);
        up = (EditText) view.findViewById(R.id.upLimit);

    }

    private boolean validRequest() {
        try {
            double downLim = Double.parseDouble(down.getText().toString());
            double upLim = Double.parseDouble(up.getText().toString());
            return !selectButton.getText().equals("Choose");
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!firstLunch) {
            Log.v("Service", "binding");
            if (getContext() == null) {
                Log.v("Service", "null here");
            }
            getContext().bindService(backGroundServiceIntent, mBackGroundServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        firstLunch = false;
        if (isConnected.get()) {
            Log.v("Service", "unbinding");
            getActivity().unbindService(mBackGroundServiceConnection);
        }
    }


    private ServiceConnection mBackGroundServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConnected.set(false);
            Log.v("Service", "Disconnected");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isConnected.set(true);
            Log.v("Service", "Connected");
            BackGroundService.LocalBinder myBinder = (BackGroundService.LocalBinder) service;
            backGroundService = myBinder.getService();

            if (flag) {
                // firstEmergency time.
                flag = false;
                serviceHasBeenStarted = true;
                if (validRequest()) {
                    backGroundService.addSymbol(symbol, down.getText().toString(), up.getText().toString());
                }
            }

        }

    };

}
