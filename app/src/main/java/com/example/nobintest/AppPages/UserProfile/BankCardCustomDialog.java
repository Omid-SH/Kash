package com.example.nobintest.AppPages.UserProfile;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nobintest.DataManegment.AppDataManager;
import com.example.nobintest.DataManegment.DataManager;
import com.example.nobintest.R;

import java.lang.ref.WeakReference;

public class BankCardCustomDialog extends Dialog implements View.OnClickListener {

    private Activity activity;
    private Button add, dismiss;
    private EditText cardNumber, cardBank;

    public BankCardCustomDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_bank_card);

        add = (Button) findViewById(R.id.add_action);
        dismiss = (Button) findViewById(R.id.dismiss_action);
        cardNumber = findViewById(R.id.card_number_input);
        cardBank = findViewById(R.id.card_bank_input);

        add.setOnClickListener(this);
        dismiss.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_action:
                action();
                break;
            case R.id.dismiss_action:
                Toast.makeText(activity, "Canceled", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    private void action() {
        DataManager dataManager = DataManager.getInstance();
        AppDataManager appDataManager = AppDataManager.getInstance(activity);

        if (!appDataManager.getProfileState().equals(AppDataManager.USER_STATE_SIGNED_IN)) {
            Toast.makeText(activity, "FIRST SIGN IN", Toast.LENGTH_SHORT).show();
        } else if (dataManager.isNetworkConnected(activity)) {
            DataManager.ProfileHandler profileHandler = DataManager.getProfileHandlerInstance();
            profileHandler.setProfileActivityWeakReference(new WeakReference<ProfileActivity>((ProfileActivity) activity));
            dataManager.addNewCard(cardBank.getText().toString(), cardNumber.getText().toString());
            Toast.makeText(activity, "Request has been sent", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity, "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
        }
    }

}
