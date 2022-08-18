package com.example.nobintest.DataManegment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.nobintest.AppPages.AppActivity;
import com.example.nobintest.AppPages.HomeFragments.NobitexCryptoPriceFragment;
import com.example.nobintest.AppPages.SignInUp.LoginActivity;
import com.example.nobintest.AppPages.SignInUp.SplashScrActivity;
import com.example.nobintest.AppPages.TradeFragments.TradeBuyFragment;
import com.example.nobintest.AppPages.TradeFragments.TradeOrderListFragment;
import com.example.nobintest.AppPages.TradeFragments.TradeSellFragment;
import com.example.nobintest.AppPages.UserProfile.ProfileActivity;
import com.example.nobintest.AppPages.WalletFragments.WalletFragment;
import com.example.nobintest.R;
import com.example.nobintest.nobitex.apiThreads.BankAccountAdder;
import com.example.nobintest.nobitex.apiThreads.BankCardAdder;
import com.example.nobintest.nobitex.apiThreads.NobitexNewsGetter;
import com.example.nobintest.nobitex.apiThreads.OrderAdder;
import com.example.nobintest.nobitex.apiThreads.OrderBookGetter;
import com.example.nobintest.nobitex.apiThreads.OrderStatusGetter;
import com.example.nobintest.nobitex.apiThreads.OrderUpdater;
import com.example.nobintest.nobitex.apiThreads.ProfileGetter;
import com.example.nobintest.nobitex.apiThreads.TokenGetter;
import com.example.nobintest.nobitex.apiThreads.WalletGetter;
import com.example.nobintest.nobitex.dataTypes.Order;
import com.example.nobintest.nobitex.dataTypes.UserData;
import com.example.nobintest.nobitex.dataTypes.Wallet;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class DataManager {

    private static DataManager instance = null;

    public static DataManager getInstance() {

        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public static final int NO_INTERNET = 0;
    public static final int PROFILE_GOT = 1;
    public static final int NOBITEXT_NEWS_GOT = 2;
    public static final int ORDER_STATUS_GOT = 3;
    public static final int WALLETS_GOT = 4;
    public static final int ORDER_BOOK_GOT = 5;
    public static final int BANK_ACCOUNT_ADD = 6;
    public static final int BANK_CARD_ADD = 7;
    public static final int ADD_ORDER = 8;
    public static final int TOKEN_GOT = 9;
    public static final int UPDATE_ORDER = 10;


    private static BuyPageHandler buyPageHandler = null;
    private static SellPageHandler sellPageHandler = null;
    private static OrderPageHandler orderPageHandler = null;
    private static WalletHandler walletHandler = null;
    private static ProfileHandler profileHandler = null;
    private static NobitexMarketPriceHandler nobitexMarketPriceHandler = null;
    private static SignInHandler signInHandler = null;

    public static BuyPageHandler getBuyPageHandlerInstance() {
        if (buyPageHandler == null) {
            buyPageHandler = new BuyPageHandler();
        }
        return buyPageHandler;
    }

    public static SellPageHandler getSellPageHandlerInstance() {
        if (sellPageHandler == null) {
            sellPageHandler = new SellPageHandler();
        }
        return sellPageHandler;
    }

    public static OrderPageHandler getOrderPageHandlerInstance() {
        if (orderPageHandler == null) {
            orderPageHandler = new OrderPageHandler();
        }
        return orderPageHandler;
    }

    public static WalletHandler getWalletHandlerInstance() {
        if (walletHandler == null) {
            walletHandler = new WalletHandler();
        }
        return walletHandler;
    }

    public static ProfileHandler getProfileHandlerInstance() {
        if (profileHandler == null) {
            profileHandler = new ProfileHandler();
        }
        return profileHandler;
    }

    public static NobitexMarketPriceHandler getNobitexMarketPriceHandlerInstance() {
        if (nobitexMarketPriceHandler == null) {
            nobitexMarketPriceHandler = new NobitexMarketPriceHandler();
        }
        return nobitexMarketPriceHandler;
    }

    public static SignInHandler getSignInHandlerInstance() {
        if (signInHandler == null) {
            signInHandler = new SignInHandler();
        }
        return signInHandler;
    }

    private DataManager() {

        //initialize and cache whatever you want here

    }

    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void getProfile() {
        new ProfileGetter(getProfileHandlerInstance()).start();
        // This should give me user profile and bankCards and bankAccounts
    }

    public void getNobitexMarket(String srcCurrency, String dstCurrency) {
        new NobitexNewsGetter(srcCurrency, dstCurrency, getNobitexMarketPriceHandlerInstance()).start();
        // This should give me stats of that pair of price as an array of string or response object
        // It works with NobitexMarketPriceHandler
    }

    public void getOrder(String id) {
        new OrderStatusGetter(id, getOrderPageHandlerInstance()).start();
        // This should give me order information as an array of string or response object
        // It works with OrderPageHandler
    }

    public void getWallets() {
        new WalletGetter(getWalletHandlerInstance()).start();
        // This should return activeBalance, rialBalance for all nonzero currency wallets
        // It works with WalletHandler
    }

    public void getGlobalOrderBook(String symbol, String page) {
        Handler handler;

        if (page.equals("SellPage")) {
            handler = getSellPageHandlerInstance();
        } else {
            handler = getBuyPageHandlerInstance();
        }

        new OrderBookGetter(symbol, handler).start();
        // This should give me two sorted array of bids and asks
        //It should notify and pass answer to both OrderPageHandler and SellPageHandler. At least one of them
        // should be alive.
    }


    public void addNewAccount(String bankNumber, String bankShaba, String bankName) {
        new BankAccountAdder(bankNumber, bankShaba, bankName, getProfileHandlerInstance()).start();
        // This should add new account. It use ProfileHandler to give response what happened.
    }

    public void addNewCard(String bankName, String bankNumber) {
        new BankCardAdder(bankNumber, bankName, getProfileHandlerInstance()).start();
        // This should add new card. It use ProfileHandler to give response what happened.
    }

    public void addNewOrder(String type, String execution, String srcCurrency, String dstCurrency,
                            String amount, String price) {
        Handler handler;
        if (type == "buy") {
            handler = getBuyPageHandlerInstance();
        } else {
            handler = getSellPageHandlerInstance();
        }

        new OrderAdder(type, execution, srcCurrency, dstCurrency, amount, price, handler).start();
        // This should add new order. It use OrderPageHandler to give response what happened.
        // This should give me order id  at the message.obj

    }

    public void getUserToken(String username, String password) {
        new TokenGetter(username, password, getSignInHandlerInstance()).start();
    }

    public void updateOrder(String orderId, String status) {
        // status should be active or canceled or new.
        new OrderUpdater(orderId, status, getOrderPageHandlerInstance()).start();
    }

    public static class BuyPageHandler extends Handler {
        private WeakReference<TradeBuyFragment> tradeBuyFragmentWeakReference = null;

        public void setTradeBuyFragmentWeakReference(WeakReference<TradeBuyFragment> tradeBuyFragmentWeakReference) {
            this.tradeBuyFragmentWeakReference = tradeBuyFragmentWeakReference;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.i("TAG", "I GOT ERROR YEAH ");

            if (tradeBuyFragmentWeakReference == null) {
                return;
            }

            if (msg.what == ORDER_BOOK_GOT) {
                if (msg.obj != null) {
                    OrderBookGetter.OrderBook orderBook = (OrderBookGetter.OrderBook) msg.obj;
                    tradeBuyFragmentWeakReference.get().setBuysRecycler(orderBook.getAsks());
                    tradeBuyFragmentWeakReference.get().setSellsRecycler(orderBook.getBids());
                } else {
                    Toast.makeText(tradeBuyFragmentWeakReference.get().getActivity(), "Can not get market order", Toast.LENGTH_LONG).show();
                }
            } else if (msg.what == ADD_ORDER) {
                if ((msg.obj) instanceof OrderAdder.OrderError) {
                    String errorText = ((OrderAdder.OrderError) msg.obj).getMessage();
                    Toast.makeText(tradeBuyFragmentWeakReference.get().getActivity(), errorText, Toast.LENGTH_LONG).show();
                } else {
                    Float orderId = ((Order) msg.obj).getId();
                    // toDo manage Id here and save it.
                    Toast.makeText(tradeBuyFragmentWeakReference.get().getActivity(), "Pushed order successfully!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public static class SellPageHandler extends Handler {
        private WeakReference<TradeSellFragment> tradeSellFragmentWeakReference = null;

        public void setTradeSellFragmentWeakReference(WeakReference<TradeSellFragment> tradeSellFragmentWeakReference) {
            this.tradeSellFragmentWeakReference = tradeSellFragmentWeakReference;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (tradeSellFragmentWeakReference == null) {
                return;
            }

            if (msg.what == ORDER_BOOK_GOT) {
                if (msg.obj != null) {
                    OrderBookGetter.OrderBook orderBook = (OrderBookGetter.OrderBook) msg.obj;
                    tradeSellFragmentWeakReference.get().setBuysRecycler(orderBook.getAsks());
                    tradeSellFragmentWeakReference.get().setSellsRecycler(orderBook.getBids());
                } else {
                    Toast.makeText(tradeSellFragmentWeakReference.get().getActivity(), "Can not get market order", Toast.LENGTH_SHORT).show();
                }
            } else if (msg.what == ADD_ORDER) {
                if ((msg.obj) instanceof OrderAdder.OrderError) {
                    String errorText = ((OrderAdder.OrderError) msg.obj).getMessage();
                    Toast.makeText(tradeSellFragmentWeakReference.get().getActivity(), errorText, Toast.LENGTH_SHORT).show();
                } else {
                    Float orderId = ((Order) msg.obj).getId();
                    // toDo manage Id here and save it.
                    Toast.makeText(tradeSellFragmentWeakReference.get().getActivity(), "Pushed order successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    public static class OrderPageHandler extends Handler {
        private WeakReference<TradeOrderListFragment> TradeOrderListFragment = null;

        public void setOrderPageHandlerWeakReference(WeakReference<TradeOrderListFragment> tradeOrderListFragment) {
            this.TradeOrderListFragment = tradeOrderListFragment;
        }
    }

    public static class WalletHandler extends Handler {
        private WeakReference<WalletFragment> walletFragmentWeakReference = null;

        public void setWalletFragmentWeakReference(WeakReference<WalletFragment> walletFragmentWeakReference) {
            this.walletFragmentWeakReference = walletFragmentWeakReference;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (walletFragmentWeakReference == null) {
                return;
            }

            if (msg.what == WALLETS_GOT) {
                if (msg.obj != null) {
                    ArrayList<Wallet> wallets = (ArrayList<Wallet>) msg.obj;
                    ArrayList<String> currency = new ArrayList<>(), balance = new ArrayList<>(), rialBalance = new ArrayList<>();
                    for (Wallet item : wallets) {
                        currency.add(item.getCurrency());
                        balance.add(item.getBalance());
                        rialBalance.add(Float.toString(item.getRialBalance()));
                    }
                    walletFragmentWeakReference.get().setRecyclerView(currency.toArray(new String[0]),
                        balance.toArray(new String[0]), rialBalance.toArray(new String[0]));
                } else {
                    Toast.makeText(walletFragmentWeakReference.get().getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public static class ProfileHandler extends Handler {
        private WeakReference<ProfileActivity> profileActivityWeakReference = null;

        public void setProfileActivityWeakReference(WeakReference<ProfileActivity> profileActivityWeakReference) {
            this.profileActivityWeakReference = profileActivityWeakReference;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (profileActivityWeakReference == null) {
                return;
            }

            if (msg.what == PROFILE_GOT) {
                if (msg.obj != null) {
                    UserData userData = (UserData) msg.obj;
                    profileActivityWeakReference.get().setPersonDataView(userData);
                    profileActivityWeakReference.get().setPersonBankCardsView(userData);
                    profileActivityWeakReference.get().setPersonBankAccountsView(userData);
                    Toast.makeText(profileActivityWeakReference.get().getApplication(), "DONE", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(profileActivityWeakReference.get().getApplication(), "Error!", Toast.LENGTH_SHORT).show();
                }
            } else if (msg.what == BANK_ACCOUNT_ADD) {
                Log.i("TAG", "Status:" + msg.obj);
                if (((String) msg.obj).equals("ok")) {
                    Toast.makeText(profileActivityWeakReference.get().getApplication(), "Bank Account has been added",
                        Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(profileActivityWeakReference.get().getApplication(), "Add bank account Error!",
                        Toast.LENGTH_SHORT).show();
                }
            } else if (msg.what == BANK_CARD_ADD) {
                Log.i("TAG", "Status:" + msg.obj);
                if (((String) msg.obj).equals("ok")) {
                    Toast.makeText(profileActivityWeakReference.get().getApplication(), "Bank card has been added",
                        Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(profileActivityWeakReference.get().getApplication(), "Add bank card Error!",
                        Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public static class NobitexMarketPriceHandler extends Handler {
        private WeakReference<NobitexCryptoPriceFragment> nobitexCryptoPriceFragmentWeakReference = null;

        public void setNobitexCryptoPriceFragmentWeakReference(WeakReference<NobitexCryptoPriceFragment> nobitexCryptoPriceFragmentWeakReference) {
            this.nobitexCryptoPriceFragmentWeakReference = nobitexCryptoPriceFragmentWeakReference;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (nobitexCryptoPriceFragmentWeakReference == null) {
                return;
            }

            if (msg.what == NOBITEXT_NEWS_GOT) {
                if (msg.obj != null) {
                    TextView bstSell, bstBuy, dayHigh, dayLow, volume, dayChange;
                    bstSell = nobitexCryptoPriceFragmentWeakReference.get().getView().findViewById(R.id.best_sell);
                    bstBuy = nobitexCryptoPriceFragmentWeakReference.get().getView().findViewById(R.id.best_buy);
                    dayHigh = nobitexCryptoPriceFragmentWeakReference.get().getView().findViewById(R.id.day_high);
                    dayLow = nobitexCryptoPriceFragmentWeakReference.get().getView().findViewById(R.id.day_low);
                    volume = nobitexCryptoPriceFragmentWeakReference.get().getView().findViewById(R.id.volume);
                    dayChange = nobitexCryptoPriceFragmentWeakReference.get().getView().findViewById(R.id.day_change);

                    NobitexNewsGetter.NobitexNewsAnswer nobitexNewsAnswer = (NobitexNewsGetter.NobitexNewsAnswer) msg.obj;

                    bstSell.setText(nobitexNewsAnswer.getBestSell());
                    bstBuy.setText(nobitexNewsAnswer.getBestBuy());
                    dayHigh.setText(nobitexNewsAnswer.getDayHigh());
                    dayLow.setText(nobitexNewsAnswer.getDayLow());
                    volume.setText(nobitexNewsAnswer.getVolumeSrc());
                    dayChange.setText(nobitexNewsAnswer.getDayChange());
                } else {
                    Toast.makeText(nobitexCryptoPriceFragmentWeakReference.get().getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    public static class SignInHandler extends Handler {
        private WeakReference<LoginActivity> loginActivityWeakReference = null;
        private WeakReference<SplashScrActivity> splashScrActivityWeakReference = null;

        public void setLoginActivityWeakReference(WeakReference<LoginActivity> loginActivityWeakReference) {
            this.loginActivityWeakReference = loginActivityWeakReference;
        }

        public void setSplashScrActivityWeakReference(WeakReference<SplashScrActivity> splashScrActivityWeakReference) {
            this.splashScrActivityWeakReference = splashScrActivityWeakReference;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (loginActivityWeakReference == null && splashScrActivityWeakReference == null) {
                return;
            } else if (loginActivityWeakReference != null && loginActivityWeakReference.get() != null) {
                if (msg.what == TOKEN_GOT) {
                    if (msg.obj != null) {
                        //Toast.makeText(loginActivityWeakReference.get(), "GOT TOKEN SUCCESSFULLY", Toast.LENGTH_SHORT).show();

                        AppDataManager appDataManager = AppDataManager.getInstance(loginActivityWeakReference.get());
                        appDataManager.setProfileState(AppDataManager.USER_STATE_SIGNED_IN);

                        loginActivityWeakReference.get().finish();
                        loginActivityWeakReference.get().startActivity(new Intent(loginActivityWeakReference.get(),
                            AppActivity.class));
                    } else {
                        Toast.makeText(loginActivityWeakReference.get(), "WRONG USERNAME OR PASSWORD", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(loginActivityWeakReference.get(), "UNKNOWN ERROR! ", Toast.LENGTH_SHORT).show();
                }
            } else if (splashScrActivityWeakReference != null && splashScrActivityWeakReference.get() != null) {
                if (msg.what == TOKEN_GOT) {
                    if (msg.obj != null) {
                        splashScrActivityWeakReference.get().startNextActivity(false);
                        //Toast.makeText(splashScrActivityWeakReference.get(), "GOT TOKEN AGAIN SUCCESSFULLY",
                        //    Toast.LENGTH_SHORT).show();
                        AppDataManager appDataManager = AppDataManager.getInstance(splashScrActivityWeakReference.get());
                        appDataManager.setProfileState(AppDataManager.USER_STATE_SIGNED_IN);
                    } else {
                        //Toast.makeText(splashScrActivityWeakReference.get(), "Username or password problem. Sign in inside the App!",
                        //    Toast.LENGTH_SHORT).show();
                        AppDataManager appDataManager = AppDataManager.getInstance(splashScrActivityWeakReference.get());
                        appDataManager.setProfileState(AppDataManager.USER_STATE_GUEST);
                        splashScrActivityWeakReference.get().startNextActivity(false);
                    }
                }
            }
        }
    }
}
