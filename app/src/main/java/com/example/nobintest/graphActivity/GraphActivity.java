package com.example.nobintest.graphActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nobintest.AppPages.HomeFragments.CryptoPrice.CryptoPriceFragment;
import com.example.nobintest.R;
import com.example.nobintest.graphActivity.candlaDataSets.Candle;
import com.example.nobintest.graphActivity.candlaDataSets.LatestCandleData;
import com.example.nobintest.graphActivity.candlaDataSets.Range;
import com.example.nobintest.graphActivity.graphUtil.CustomMarkerView;
import com.example.nobintest.graphActivity.sparkLineDataSets.SparkLineData;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GraphActivity extends AppCompatActivity {


    // chart bottom data.
    private EditText totalCount;
    private EditText totalVolume;
    String underChartBarString = "GoTo CandleChart";


    private static int DRAW_CANDLE_CHART = 0;
    private static int DRAW_SPARKLINE_CHART = 1;
    private static int UPDATE_BOTTOM_CHART = 2;

    private AtomicBoolean acquired;
    private AtomicBoolean isInCandleGraph;
    private Range state;

    private ChartActivityHandler chartActivityHandler;
    private String symbol;

    ArrayList<CandleEntry> candleEntries;
    ArrayList<Entry> sparkLineEntries;

    CandleStickChart candleStickChart;
    LineChart lineChart;
    ProgressBar progressBar;

    private static String COIN_IO_API_KEY = "589A23FE-18AF-4C5B-B0C9-D309475167C5"; // person2: "68FB4D30-DCD9-4C95-A79F-7BD273843CD9" // person3: "0B5A7152-5376-499C-928F-BCBBF53BD7DC";;
    private static String NOMICS_API_KEY = "e67721f8c44476acd000d6495d4184c0";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_layout);

        progressBar = findViewById(R.id.progress_bar);
        totalCount = findViewById(R.id.chart_total_count);
        totalVolume = findViewById(R.id.chart_total_volume);
        EditText chartSymbol = findViewById(R.id.chart_symbol);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            symbol = extras.getString("SYMBOL");
        } else {
            symbol = "BTC";
        }

        chartSymbol.setText(symbol);
        totalCount.setText(underChartBarString);
        totalVolume.setText(underChartBarString);

        // to check the correctness of clicked symbol.
        // Toast.makeText(this, symbol, Toast.LENGTH_LONG).show();

        acquired = new AtomicBoolean(false);
        isInCandleGraph = new AtomicBoolean(false);

        chartActivityHandler = new ChartActivityHandler(this);

        setUpCandleChart();
        setUpLineChart();

        //getCandles(Range.oneMonth);
        state = Range.oneMonth;
        getSparkLineData(state);

    }

    public void resetUnderLieChart() {
        totalCount.setText(underChartBarString);
        totalVolume.setText(underChartBarString);
    }

    public void setUpLineChart() {

        // todo -> format x Axis.

        lineChart = findViewById(R.id.spark_line_chart);
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
        CustomMarkerView mv = new CustomMarkerView(getApplicationContext(), R.layout.custom_marker_view);
        mv.setChartView(lineChart);
        lineChart.setMarker(mv);

        sparkLineEntries = new ArrayList<>();

        addChartFeatures();

    }

    public void addChartFeatures() {


        // Description
        Description description = new Description();
        description.setText("SparkLineData");
        lineChart.setDescription(description);

        // BackGround
        lineChart.setDrawGridBackground(false);

        // GridLine
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);

        // Labels
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setEnabled(true);

        YAxis leftAxis = lineChart.getAxisLeft();
        YAxis rightAxis = lineChart.getAxisRight();

        leftAxis.setEnabled(true);
        rightAxis.setEnabled(true);

    }

    private void addDataToChart(String minMax) {

        // DataSet UI format.
        LineDataSet set1 = new LineDataSet(sparkLineEntries, "DataSet");
        set1.setColor(Color.DKGRAY);
        set1.setCircleColor(Color.DKGRAY);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);
        set1.setFormLineWidth(1f);
        set1.setFormSize(15.f);

        // set DC and Range.

        float min = Float.parseFloat(minMax.substring(0, minMax.indexOf("#")));
        float max = Float.parseFloat(minMax.substring(minMax.indexOf("#") + 1, minMax.indexOf("*")));

        String chartDescription = minMax.substring(minMax.indexOf("*") + 1);

        Description description = new Description();
        description.setText(chartDescription);
        lineChart.setDescription(description);

        YAxis yLeftAxis = lineChart.getAxisLeft();
        YAxis yRightAxis = lineChart.getAxisRight();

        yLeftAxis.setStartAtZero(false);
        yRightAxis.setStartAtZero(false);

        float range = max - min;

        yLeftAxis.setStartAtZero(false);
        yRightAxis.setStartAtZero(false);

        yLeftAxis.setAxisMinimum(min - 0.15f * range); // start at zero
        yLeftAxis.setAxisMaximum(max + 0.15f * range); // the axis maximum is 100

        yRightAxis.setAxisMinimum(min - 0.15f * range); // start at zero
        yRightAxis.setAxisMaximum(max + 0.15f * range); // the axis maximum is 100

        XAxis xAxis = lineChart.getXAxis();

        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(sparkLineEntries.size() * (1.04f));


        // attaching to dataSet.
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        lineChart.setData(data);
        progressBar.setVisibility(View.GONE);
        lineChart.fitScreen();
        lineChart.invalidate();
        acquired.set(false);

    }

    public void setUpCandleChart() {

        // todo -> format x Axis.

        candleStickChart = findViewById(R.id.candle_stick_chart);
        candleStickChart.setVisibility(View.GONE);

        candleEntries = new ArrayList<CandleEntry>();

        candleStickChart.setHighlightPerDragEnabled(true);

        candleStickChart.setDrawBorders(true);
        candleStickChart.setBorderColor(getResources().getColor(R.color.dark));

        YAxis yLeftAxis = candleStickChart.getAxisLeft();
        YAxis yRightAxis = candleStickChart.getAxisRight();
        yLeftAxis.setDrawGridLines(false);
        yRightAxis.setDrawGridLines(false);

        candleStickChart.requestDisallowInterceptTouchEvent(true);

        XAxis xAxis = candleStickChart.getXAxis();
        xAxis.setDrawGridLines(false);// disable x axis grid lines
        xAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);

        yRightAxis.setTextColor(Color.DKGRAY);
        yLeftAxis.setDrawLabels(true);


        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setAvoidFirstLastClipping(true);

        Legend l = candleStickChart.getLegend();
        l.setEnabled(true);

    }

    public void getCandles(Range range) {

        Log.v(CryptoPriceFragment.TAG, "start Requesting");
        OkHttpClient okHttpClient = new OkHttpClient();

        String miniUrl;
        final String description;
        switch (range) {

            case daily:
                miniUrl = "period_id=1HRS".concat("&time_end=".concat(getCurrentDate()).concat("&limit=24"));
                description = "Hourly candles from now";
                break;

            case weekly:
                miniUrl = "period_id=1DAY".concat("&time_end=".concat(getCurrentDate()).concat("&limit=7"));
                description = "Daily candles from now";
                break;

            case oneMonth:
                miniUrl = "period_id=1DAY".concat("&time_end=".concat(getCurrentDate()).concat("&limit=30"));
                description = "Daily candles from now";
                break;

            case threeMonth:
                miniUrl = "period_id=1DAY".concat("&time_end=".concat(getCurrentDate()).concat("&limit=90"));
                description = "Daily candles from now";
                break;

            case sixMonth:
                miniUrl = "period_id=1DAY".concat("&time_end=".concat(getCurrentDate()).concat("&limit=180"));
                description = "Daily candles from now";
                break;

            case yearly:
                miniUrl = "period_id=1DAY".concat("&time_end=".concat(getCurrentDate()).concat("&limit=365"));
                description = "Daily candles from now";
                break;

            default:
                Toast.makeText(GraphActivity.this, "Invalid State", Toast.LENGTH_SHORT).show();
                miniUrl = "";
                description = "";

        }

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://rest.coinapi.io/v1/ohlcv/".concat(symbol).concat("/USD/history?".concat(miniUrl)))
            .newBuilder();

        String url = urlBuilder.build().toString();

        final Request request = new Request.Builder().url(url)
            .addHeader("X-CoinAPI-Key", COIN_IO_API_KEY)
            .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("TAG", e.getMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if (!response.isSuccessful()) {
                    // todo Toast: ServerNotResponding
                    acquired.set(false);
                    throw new IOException("Unexpected code " + response);
                } else {
                    extractCandle(response.body().string(), description);
                }
            }
        });

    }


    public void extractCandle(String inputData, String description) {

        inputData = "{\"candleList\":".concat(inputData).concat("}");
        LatestCandleData latestCandleData = new Gson().fromJson(inputData, LatestCandleData.class);

        latestCandleData.getCandleList().sort(new Comparator<Candle>() {
            @Override
            public int compare(Candle o1, Candle o2) {
                return o1.compareTo(o2);
            }
        });

        int counter = 0;
        float min = 0;
        float max = 0;

        long totalCount = 0;
        double volumeTraded = 0.0d;

        Log.v("Size", "" + latestCandleData.getCandleList().size());

        candleEntries.clear();

        for (Candle candle : latestCandleData.getCandleList()) {

            Log.v("Candle", candle.toString());

            candleEntries.add(new CandleEntry(counter,
                candle.getPrice_high(),
                candle.getPrice_low(),
                candle.getPrice_open(),
                candle.getPrice_close()));
            counter++;

            if (counter == 1) {
                totalCount = candle.getTrades_count();
                volumeTraded = candle.getVolume_traded();
                min = candle.min();
                max = candle.max();
                continue;
            }

            min = Math.min(min, candle.min());
            max = Math.max(max, candle.max());
            totalCount += candle.getTrades_count();
            volumeTraded += candle.getVolume_traded();

        }

        Message updateMessage = new Message();
        updateMessage.what = UPDATE_BOTTOM_CHART;
        updateMessage.obj = String.valueOf(totalCount).concat("#").concat(String.valueOf(volumeTraded));
        chartActivityHandler.sendMessage(updateMessage);

        Message message = new Message();
        message.obj = String.valueOf(min).concat("#")
            .concat(String.valueOf(max)).concat("*".concat(description));
        message.what = DRAW_CANDLE_CHART;
        chartActivityHandler.sendMessage(message);

    }

    public void setChartData(String minMax) {

        CandleDataSet set = new CandleDataSet(candleEntries, "DataSet");
        set.setColor(Color.rgb(80, 80, 80));
        set.setShadowColor(getResources().getColor(R.color.dark));
        set.setShadowWidth(1.5f);

        set.setDecreasingColor(getResources().getColor(R.color.candle_red));
        set.setDecreasingPaintStyle(Paint.Style.FILL);
        set.setIncreasingColor(getResources().getColor(R.color.candle_green));
        set.setIncreasingPaintStyle(Paint.Style.FILL);
        set.setNeutralColor(Color.LTGRAY);
        set.setDrawValues(false);

        // set DC and range

        float min = Float.parseFloat(minMax.substring(0, minMax.indexOf("#")));
        float max = Float.parseFloat(minMax.substring(minMax.indexOf("#") + 1, minMax.indexOf("*")));

        String chartDescription = minMax.substring(minMax.indexOf("*") + 1);

        Description description = new Description();
        description.setText(chartDescription);
        candleStickChart.setDescription(description);


        YAxis yLeftAxis = candleStickChart.getAxisLeft();
        YAxis yRightAxis = candleStickChart.getAxisRight();

        yLeftAxis.setStartAtZero(false);
        yRightAxis.setStartAtZero(false);

        float range = max - min;

        yLeftAxis.setAxisMinimum(min - 0.15f * range); // start at zero
        yLeftAxis.setAxisMaximum(max + 0.05f * range); // the axis maximum is 100


        yRightAxis.setAxisMinimum(min - 0.15f * range); // start at zero
        yRightAxis.setAxisMaximum(max + 0.05f * range); // the axis maximum is 100

        // create a data object with the dataSets
        CandleData data = new CandleData(set);
        // set data
        candleStickChart.setData(data);

        progressBar.setVisibility(View.GONE);

        candleStickChart.fitScreen();
        candleStickChart.invalidate();
        acquired.set(false);

    }

    public void daily(View view) {

        if (acquired.get()) {
            return;
        }
        if (!isInCandleGraph.get()) {
            String resId = "Sorry,day price isn't available please go to candleMode";
            Toast.makeText(getApplicationContext(), resId, Toast.LENGTH_LONG).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (isInCandleGraph.get()) {
                    state = Range.daily;
                    acquired.set(true);
                    getCandles(Range.daily);
                }
            }
        });
        thread.start();

    }

    public void weekly(View view) {

        if (acquired.get()) {
            return;
        }
        acquired.set(true);
        state = Range.weekly;

        progressBar.setVisibility(View.VISIBLE);
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (isInCandleGraph.get()) {
                    getCandles(Range.weekly);
                } else {
                    getSparkLineData(Range.weekly);
                }
            }
        });
        thread.start();

    }

    public void monthly(View view) {
        if (acquired.get()) {
            return;
        }
        acquired.set(true);
        state = Range.oneMonth;

        progressBar.setVisibility(View.VISIBLE);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (isInCandleGraph.get()) {
                    getCandles(Range.oneMonth);
                } else {
                    getSparkLineData(Range.oneMonth);
                }
            }
        });
        thread.start();
    }

    public void threeMonth(View view) {
        if (acquired.get()) {
            return;
        }
        acquired.set(true);
        state = Range.threeMonth;

        progressBar.setVisibility(View.VISIBLE);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (isInCandleGraph.get()) {
                    getCandles(Range.threeMonth);
                } else {
                    getSparkLineData(Range.threeMonth);
                }
            }
        });
        thread.start();
    }

    public void sixMonth(View view) {
        if (acquired.get()) {
            return;
        }
        acquired.set(true);
        state = Range.sixMonth;

        progressBar.setVisibility(View.VISIBLE);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (isInCandleGraph.get()) {
                    getCandles(Range.sixMonth);
                } else {
                    getSparkLineData(Range.sixMonth);
                }
            }
        });
        thread.start();
    }

    public void yearly(View view) {
        if (acquired.get()) {
            return;
        }
        acquired.set(true);
        state = Range.yearly;

        progressBar.setVisibility(View.VISIBLE);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (isInCandleGraph.get()) {
                    getCandles(Range.yearly);
                } else {
                    getSparkLineData(Range.yearly);
                }
            }
        });
        thread.start();
    }

    public void changeChart(View view) {

        acquired.set(false);
        progressBar.setVisibility(View.VISIBLE);
        if (isInCandleGraph.get()) {

            resetUnderLieChart();

            isInCandleGraph.set(false);
            candleStickChart.setVisibility(View.GONE);
            candleEntries.clear();
            candleStickChart.clear();

            lineChart.setVisibility(View.VISIBLE);

            if (state == Range.daily) {
                state = Range.weekly;
            }
            getSparkLineData(state);


        } else {

            isInCandleGraph.set(true);

            lineChart.setVisibility(View.GONE);
            sparkLineEntries.clear();
            lineChart.clear();

            candleStickChart.setVisibility(View.VISIBLE);

            getCandles(state);
        }

    }

    private static class ChartActivityHandler extends Handler {

        private WeakReference<GraphActivity> graphActivityWeakReference;

        public ChartActivityHandler(GraphActivity graphActivity) {
            this.graphActivityWeakReference = new WeakReference<>(graphActivity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {

            GraphActivity graphActivity = graphActivityWeakReference.get();
            if (graphActivity == null) {
                return;
            }

            if (msg.what == DRAW_CANDLE_CHART) {
                graphActivity.setChartData((String) msg.obj);
            } else if (msg.what == DRAW_SPARKLINE_CHART) {
                graphActivity.addDataToChart((String) msg.obj);
            } else if (msg.what == UPDATE_BOTTOM_CHART) {
                String message = (String) msg.obj;
                String totalCount = message.substring(0, message.indexOf("#"));
                String volumeTraded = message.substring(message.indexOf("#") + 1);
                graphActivity.updateBottomChart(totalCount, volumeTraded);
            }

        }

    }

    private void updateBottomChart(String totalCount, String volumeTraded) {
        this.totalCount.setText(String.valueOf(totalCount));
        this.totalVolume.setText(String.valueOf(volumeTraded));

    }

    private String getCurrentDate() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String result = simpleDateFormat.format(date);
        result = result.substring(0, result.indexOf(" ")).concat("T").concat(result.substring(result.indexOf(" ") + 1));
        return result;

    }

    private void getSparkLineData(Range range) {


        Log.v(CryptoPriceFragment.TAG, "start Requesting sparkLine");
        OkHttpClient okHttpClient = new OkHttpClient();

        String currentTime = getCurrentDate();
        String dt = currentTime.substring(0, currentTime.indexOf("T"));  // now: Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();

        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String start; // start Time
        String end; // end Time
        final String miniUrl;
        final String description;

        switch (range) {

            /* DISABLED.
            case daily:
                description = "Hourly candles from now";
                break;
            */

            case weekly:

                c.add(Calendar.DATE, -7);
                start = sdf.format(c.getTime()).concat("T00%3A00%3A00Z");
                end = dt.concat("T00%3A00%3A00Z");
                Log.v("Date", start + "{}" + end);
                miniUrl = "start=".concat(start).concat("&").concat("end=").concat(end);
                description = "Daily pricePoints from now";
                break;

            case oneMonth:

                c.add(Calendar.DATE, -30);
                start = sdf.format(c.getTime()).concat("T00%3A00%3A00Z");
                end = dt.concat("T00%3A00%3A00Z");
                Log.v("Date", start + "{}" + end);
                miniUrl = "start=".concat(start).concat("&").concat("end=").concat(end);
                description = "Daily pricePoints from now";
                break;

            case threeMonth:

                c.add(Calendar.DATE, -90);
                start = sdf.format(c.getTime()).concat("T00%3A00%3A00Z");
                end = dt.concat("T00%3A00%3A00Z");
                Log.v("Date", start + "{}" + end);
                miniUrl = "start=".concat(start).concat("&").concat("end=").concat(end);
                description = "some pricePoints since last three Months";
                break;

            case sixMonth:

                c.add(Calendar.DATE, -180);
                start = sdf.format(c.getTime()).concat("T00%3A00%3A00Z");
                end = dt.concat("T00%3A00%3A00Z");
                Log.v("Date", start + "{}" + end);
                miniUrl = "start=".concat(start).concat("&").concat("end=").concat(end);
                description = "some pricePoints since last six Months";
                break;

            case yearly:

                c.add(Calendar.DATE, -365);
                start = sdf.format(c.getTime()).concat("T00%3A00%3A00Z");
                end = dt.concat("T00%3A00%3A00Z");
                Log.v("Date", start + "{}" + end);
                miniUrl = "start=".concat(start).concat("&").concat("end=").concat(end);
                description = "some pricePoints since last year";
                break;

            default:
                //todo toast invalid state.
                miniUrl = "";
                description = "";

        }

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.nomics.com/v1/currencies/sparkline?"
            .concat("key="
                .concat(NOMICS_API_KEY)
                .concat("&ids=" + symbol + "&")
                .concat(miniUrl)))
            .newBuilder();

        String url = urlBuilder.build().toString();

        final Request request = new Request.Builder().url(url)
            .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("TAG", e.getMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if (!response.isSuccessful()) {
                    acquired.set(false);
                    // todo Toast: serverNotResponding.
                    throw new IOException("Unexpected code " + response);
                } else {
                    String body = response.body().string();
                    Log.v("TAG", body);
                    extractSparkLine(body, description);
                }
            }
        });

    }

    private void extractSparkLine(String inputData, String description) {

        Log.v("TAG", "Extracting");
        inputData = inputData.substring(1, inputData.length() - 2);
        Log.v("TAG", inputData);
        SparkLineData sparkLineData = new Gson().fromJson(inputData, SparkLineData.class);

        int counter = 0;
        float min = 0;
        float max = 0;

        Log.v("Size", "" + sparkLineData.getTimestamps().size());

        sparkLineEntries.clear();

        for (Float f : sparkLineData.getPrices()) {

            Log.v("price", "" + f);

            sparkLineEntries.add(new Entry(counter + 1, f));
            counter++;

            if (counter == 1) {
                min = f;
                max = f;
                continue;
            }

            min = Math.min(min, f);
            max = Math.max(max, f);

        }

        Message message = new Message();
        message.obj = String.valueOf(min).concat("#").concat(String.valueOf(max)).concat("*".concat(description));
        message.what = DRAW_SPARKLINE_CHART;
        chartActivityHandler.sendMessage(message);

    }

}
