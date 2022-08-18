package com.example.nobintest.AppPages.UserProfile;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nobintest.DataManegment.AppDataManager;
import com.example.nobintest.DataManegment.DataManager;
import com.example.nobintest.R;
import com.example.nobintest.nobitex.dataTypes.BankAccount;
import com.example.nobintest.nobitex.dataTypes.BankCard;
import com.example.nobintest.nobitex.dataTypes.UserData;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class ProfileActivity extends AppCompatActivity {

    private RelativeLayout personalInfo, userBankCard, userAccounts;
    private TextView personalInfoBtn, userBankCardsBtn, userAccountsBtn;
    private ProgressBar progressBarPersonalInfo, progressBarBankCard, progressBarUserAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setViews();
        init();
    }

    private void setViews() {
        personalInfo = findViewById(R.id.profile_user_info);
        userBankCard = findViewById(R.id.profile_user_bank_cards);
        userAccounts = findViewById(R.id.profile_user_accounts);
        personalInfoBtn = findViewById(R.id.profile_user_info_btn);
        userBankCardsBtn = findViewById(R.id.profile_bank_cards_btn);
        userAccountsBtn = findViewById(R.id.profile_bank_accounts_btn);
        progressBarPersonalInfo = findViewById(R.id.progress_bar_profile_info);
        progressBarBankCard = findViewById(R.id.progress_bar_bank_cards);
        progressBarUserAccounts = findViewById(R.id.progress_bar_accounts);
    }

    private void init() {
        /*making personal info visible*/
        personalInfo.setVisibility(View.VISIBLE);
        userBankCard.setVisibility(View.GONE);
        userAccounts.setVisibility(View.GONE);

        personalInfoBtn.setTextColor(getResources().getColor(R.color.blue));
        userBankCardsBtn.setTextColor(getResources().getColor(R.color.grey));
        userAccountsBtn.setTextColor(getResources().getColor(R.color.grey));

        progressBarPersonalInfo.setVisibility(View.VISIBLE);
        progressBarBankCard.setVisibility(View.GONE);
        progressBarUserAccounts.setVisibility(View.GONE);

        getData();
    }

    private void getData() {
        DataManager dataManager = DataManager.getInstance();
        AppDataManager appDataManager = AppDataManager.getInstance(getApplication());

        if (!appDataManager.getProfileState().equals(AppDataManager.USER_STATE_SIGNED_IN)) {
            Toast.makeText(getApplication(), "FIRST SIGN IN", Toast.LENGTH_SHORT);
            progressBarPersonalInfo.setVisibility(View.GONE);
            progressBarBankCard.setVisibility(View.GONE);
            progressBarUserAccounts.setVisibility(View.GONE);
        } else if (dataManager.isNetworkConnected(getApplication())) {
            DataManager.ProfileHandler profileHandler = DataManager.getProfileHandlerInstance();
            profileHandler.setProfileActivityWeakReference(new WeakReference<ProfileActivity>(ProfileActivity.this));
            dataManager.getProfile();
        } else {
            Toast.makeText(getApplication(), "NO INTERNET", Toast.LENGTH_SHORT);
            progressBarPersonalInfo.setVisibility(View.GONE);
            progressBarBankCard.setVisibility(View.GONE);
            progressBarUserAccounts.setVisibility(View.GONE);
        }
    }

    public void showUserInfo(View view) {
        personalInfo.setVisibility(View.VISIBLE);
        userBankCard.setVisibility(View.GONE);
        userAccounts.setVisibility(View.GONE);

        personalInfoBtn.setTextColor(getResources().getColor(R.color.blue));
        userBankCardsBtn.setTextColor(getResources().getColor(R.color.grey));
        userAccountsBtn.setTextColor(getResources().getColor(R.color.grey));

        progressBarPersonalInfo.setVisibility(View.VISIBLE);
        progressBarBankCard.setVisibility(View.GONE);
        progressBarUserAccounts.setVisibility(View.GONE);

        getData();
    }

    public void showBankCards(View view) {
        personalInfo.setVisibility(View.GONE);
        userBankCard.setVisibility(View.VISIBLE);
        userAccounts.setVisibility(View.GONE);

        personalInfoBtn.setTextColor(getResources().getColor(R.color.grey));
        userBankCardsBtn.setTextColor(getResources().getColor(R.color.blue));
        userAccountsBtn.setTextColor(getResources().getColor(R.color.grey));

        progressBarPersonalInfo.setVisibility(View.GONE);
        progressBarBankCard.setVisibility(View.VISIBLE);
        progressBarUserAccounts.setVisibility(View.GONE);

        getData();
    }


    public void showAccounts(View view) {
        personalInfo.setVisibility(View.GONE);
        userBankCard.setVisibility(View.GONE);
        userAccounts.setVisibility(View.VISIBLE);

        personalInfoBtn.setTextColor(getResources().getColor(R.color.grey));
        userBankCardsBtn.setTextColor(getResources().getColor(R.color.grey));
        userAccountsBtn.setTextColor(getResources().getColor(R.color.blue));

        progressBarPersonalInfo.setVisibility(View.GONE);
        progressBarBankCard.setVisibility(View.GONE);
        progressBarUserAccounts.setVisibility(View.VISIBLE);

        getData();
    }

    public void setPersonBankAccountsView(UserData userData) {
        try {
            ArrayList<BankAccount> bankAccounts = userData.getBankAccounts();
            RecyclerView bankCardsRecycler = findViewById(R.id.user_account_recycler);

            BankAccountsAdapter bankAccountsAdapter = new BankAccountsAdapter(bankAccounts);
            bankCardsRecycler.setLayoutManager(new LinearLayoutManager(getApplication()));
            bankCardsRecycler.setAdapter(bankAccountsAdapter);

            progressBarUserAccounts.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPersonBankCardsView(UserData userData) {
        try {
            ArrayList<BankCard> bankCards = userData.getBankCards();
            RecyclerView bankCardsRecycler = findViewById(R.id.bank_cards_recycler);

            BankCardsAdapter bankCardsAdapter = new BankCardsAdapter(bankCards);
            bankCardsRecycler.setLayoutManager(new LinearLayoutManager(getApplication()));
            bankCardsRecycler.setAdapter(bankCardsAdapter);

            progressBarBankCard.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPersonDataView(UserData userData) {
        try {
            TextView mobile, phone, email, city, name, username;
            mobile = findViewById(R.id.profile_mobile);
            phone = findViewById(R.id.profile_phone);
            email = findViewById(R.id.profile_mail);
            city = findViewById(R.id.profile_location);
            name = findViewById(R.id.profile_name_family);
            username = findViewById(R.id.profile_username);

            mobile.setText(userData.getMobile());
            phone.setText(userData.getPhone());
            email.setText(userData.getEmail());
            city.setText(userData.getCity());
            String fullName = userData.getFirstName() + " " + userData.getLastName();
            name.setText(fullName);
            username.setText(userData.getUsername());

            progressBarPersonalInfo.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addNewBankAccount(View view) {

        BankAccountCustomDialog bankAccountCustomDialog = new BankAccountCustomDialog(ProfileActivity.this);
        bankAccountCustomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bankAccountCustomDialog.show();

        /*LayoutInflater inflater = this.getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialod_add_bank_acount, null);
        final EditText bankName = dialogLayout.findViewById(R.id.account_bank_input);
        final EditText bankNumber = dialogLayout.findViewById(R.id.account_number_input);
        final EditText bankShaba = dialogLayout.findViewById(R.id.account_shaba_input);

        new AlertDialog.Builder(ProfileActivity.this)
            .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DataManager dataManager = DataManager.getInstance();
                    AppDataManager appDataManager = AppDataManager.getInstance(getApplication());

                    if(!appDataManager.getProfileState().equals(AppDataManager.USER_STATE_SIGNED_IN)) {
                        Toast.makeText(getApplication(),"FIRST SIGN IN",Toast.LENGTH_SHORT).show();
                    } else if (dataManager.isNetworkConnected(getApplication())) {
                        DataManager.ProfileHandler profileHandler = DataManager.getProfileHandlerInstance();

                        profileHandler.setProfileActivityWeakReference(new WeakReference<ProfileActivity>(ProfileActivity.this));
                        dataManager.addNewAccount(bankNumber.getText().toString(), bankShaba.getText().toString(),
                            bankName.getText().toString());
                        Toast.makeText(getApplication(), "Request has been sent", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplication(),"NO INTERNET CONNECTION",Toast.LENGTH_SHORT).show();
                    }
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    Toast.makeText(ProfileActivity.this,"Canceled",Toast.LENGTH_SHORT).show();
                }
            })
            .setView(dialogLayout)
            .show();*/

    }

    public void addNewBankCard(View view) {

        BankCardCustomDialog bankCardCustomDialog = new BankCardCustomDialog(ProfileActivity.this);
        bankCardCustomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bankCardCustomDialog.show();

    }

}
