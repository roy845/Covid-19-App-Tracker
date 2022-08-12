package com.example.finalproject;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;


public class CountriesViewModel extends AndroidViewModel {

    private static CountriesViewModel instance;
    private MutableLiveData<ArrayList<Country>> countryLiveData;
    static ArrayList<Country> allCountries;
    MutableLiveData<Country> indexItemSelected;
    static Context context;
    String FILENAME = "date.txt";
    public static final String MYTAG = "MYTAG";
    private static final String COUNTRY_SET = "countriesSet";

    public CountriesViewModel(@NonNull Application application, Context context) {
        super(application);
        this.context=context;
        init(context);
    }

    public LiveData<ArrayList<Country>> getCountriesLiveData() {
        return countryLiveData;
    }
    public MutableLiveData<Country> getItemSelected() {
        return indexItemSelected;
    }
    public void setItemSelect(Country country) {
        indexItemSelected.setValue(country);
    }
    public void setCountryLiveData(ArrayList<Country> list) {
        countryLiveData.setValue(list);
    }

    public static CountriesViewModel getInstance(Application application,Context context) {
        if (instance == null) {
            instance = new CountriesViewModel(application,context);
        }
        return instance;
    }


    public void init(Context context) {

        countryLiveData = new MutableLiveData<>();
        allCountries = new ArrayList<>();
        countryLiveData.setValue(parseCountries(context));
        indexItemSelected = new MutableLiveData<Country>();

    }


    public void showAdapter(Context context)
    {
        CountriesFragment.countryAdapter = new CountryAdapter(getApplication(),context);
        CountriesFragment.recyclerView.setAdapter(CountriesFragment.countryAdapter);
    }


    public  ArrayList<Country> parseCountries(Context context) {

        String urlGlobalStats = "https://disease.sh/v3/covid-19/countries";
        StringRequest globalStatsRequest = new StringRequest(Request.Method.GET, urlGlobalStats,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {

                            Country country=null;
                            JSONArray jsonArray = new JSONArray((response));

                            for(int i = 0 ;i<jsonArray.length();i++) {

                                  JSONObject jsonObject = jsonArray.getJSONObject(i);
                                  String countryName = jsonObject.getString("country");
                                  String cases = jsonObject.getString("cases");
                                  String todayCases = jsonObject.getString("todayCases");
                                  String deaths = jsonObject.getString("deaths");
                                  String todayDeaths = jsonObject.getString("todayDeaths");
                                  String recovered = jsonObject.getString("recovered");
                                  String active = jsonObject.getString("active");
                                  String critical = jsonObject.getString("critical");
                                  String casesPerOneMillion = jsonObject.getString("casesPerOneMillion");
                                  String todayRecovered = jsonObject.getString("todayRecovered");
                                  String deathsPerOneMillion = jsonObject.getString("deathsPerOneMillion");
                                  String tests = jsonObject.getString("tests");
                                  String testsPerOneMillion = jsonObject.getString("testsPerOneMillion");
                                  String population = jsonObject.getString("population");
                                  String continent = jsonObject.getString("continent");
                                  String oneCasePerPeople = jsonObject.getString("oneCasePerPeople");
                                  String oneDeathPerPeople = jsonObject.getString("oneDeathPerPeople");
                                  String oneTestPerPeople = jsonObject.getString("oneTestPerPeople");
                                  String activePerOneMillion = jsonObject.getString("activePerOneMillion");
                                  String recoveredPerOneMillion = jsonObject.getString("recoveredPerOneMillion");
                                  String criticalPerOneMillion = jsonObject.getString("criticalPerOneMillion");

                                  JSONObject obj = jsonObject.getJSONObject("countryInfo");
                                  String flagUrl = obj.getString("flag");


                                  country = new Country(flagUrl, countryName, cases, todayCases, deaths, todayDeaths, recovered, active, critical,
                                          casesPerOneMillion,todayRecovered,deathsPerOneMillion,tests,testsPerOneMillion,population,continent,oneCasePerPeople,
                                          oneDeathPerPeople,oneTestPerPeople,activePerOneMillion,recoveredPerOneMillion,criticalPerOneMillion);

                                    allCountries.add(country);

                            }
                            showAdapter(context);


                        } catch (JSONException e) {

                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
                Toast.makeText(getApplication().getApplicationContext(),"There was an error loading countries data",Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(globalStatsRequest);

        return allCountries;

    }

    public void saveSwitchVisibility()
    {
        SharedPreferences sharedPref = getApplication().getApplicationContext().getSharedPreferences("SwitchVisibilityShared",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("SwitchVisibility",QuarantineFragment.switchVisibility);
        editor.apply();
    }

    public boolean getSwitchVisibility()
    {
        boolean switchVisibility = false;
        SharedPreferences sharedPref = getApplication().getApplicationContext().getSharedPreferences("SwitchVisibilityShared",Context.MODE_PRIVATE);
        switchVisibility = sharedPref.getBoolean("SwitchVisibility", switchVisibility);

        return switchVisibility;
    }


    public void saveReportOnSiteBtnVisibility()
    {
        SharedPreferences sharedPref = getApplication().getApplicationContext().getSharedPreferences("ReportOnSiteBtnVisibilityShared",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("ReportOnSiteButtonVisibility",QuarantineFragment.reportOnSiteBtnVisibility);
        editor.apply();
    }

    public boolean getReportOnSiteBtnVisibility()
    {
        boolean ReportOnSiteButtonVisibility = false;
        SharedPreferences sharedPref = getApplication().getApplicationContext().getSharedPreferences("ReportOnSiteBtnVisibilityShared",Context.MODE_PRIVATE);
        ReportOnSiteButtonVisibility = sharedPref.getBoolean("ReportOnSiteButtonVisibility", ReportOnSiteButtonVisibility);

        return ReportOnSiteButtonVisibility;
    }



    public void saveCallToReportBtnVisibility()
    {
        SharedPreferences sharedPref = getApplication().getApplicationContext().getSharedPreferences("callToReportVisibilityShared",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("callToReportButtonVisibility",QuarantineFragment.callToReportBtnVisibility);
        editor.apply();
    }

    public boolean getCallToReportBtnVisibility()
    {
        boolean callToReportBtnVisibility = false;
        SharedPreferences sharedPref = getApplication().getApplicationContext().getSharedPreferences("callToReportVisibilityShared",Context.MODE_PRIVATE);
        callToReportBtnVisibility = sharedPref.getBoolean("callToReportButtonVisibility", callToReportBtnVisibility);

        return callToReportBtnVisibility;
    }


    public void saveClearBtnVisibility()
    {
        SharedPreferences sharedPref = getApplication().getApplicationContext().getSharedPreferences("clearBtnVisibilityShared",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("clearButtonVisibility",QuarantineFragment.clearBtnVisibility);
        editor.apply();
    }

    public boolean getClearBtnVisibility()
    {
        boolean clearBtnVisibility = false;
        SharedPreferences sharedPref = getApplication().getApplicationContext().getSharedPreferences("clearBtnVisibilityShared",Context.MODE_PRIVATE);
        clearBtnVisibility = sharedPref.getBoolean("clearButtonVisibility", clearBtnVisibility);

        return clearBtnVisibility;
    }


    public void saveHeaderText()
    {
        SharedPreferences sharedPref = getApplication().getApplicationContext().getSharedPreferences("headerTextShared",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("headerTxt",QuarantineFragment.flagText);
        editor.apply();
    }

    public boolean getHeaderText()
    {
        boolean headerTxtFlag = false;
        SharedPreferences sharedPref = getApplication().getApplicationContext().getSharedPreferences("headerTextShared",Context.MODE_PRIVATE);
        headerTxtFlag = sharedPref.getBoolean("headerTxt", headerTxtFlag);

        return headerTxtFlag;
    }

    public void saveClearButtonState()
    {
        SharedPreferences sharedPref = getApplication().getApplicationContext().getSharedPreferences("clearBtn",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("clearBtnFlag",QuarantineFragment.flagClear);
        editor.apply();
    }

    public boolean getClearButtonState()
    {
        boolean clearBtnFlag = false;
        SharedPreferences sharedPref = getApplication().getApplicationContext().getSharedPreferences("clearBtn",Context.MODE_PRIVATE);
        clearBtnFlag = sharedPref.getBoolean("clearBtnFlag", clearBtnFlag);

        return clearBtnFlag;
    }


    public void saveRemovedCountriesToSP()
    {
        Log.i(MYTAG, "Writing removed countries to SP. (Method 3).");
        SharedPreferences sharedPref = getApplication().getApplicationContext().getSharedPreferences("removedCountries",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Set<String> removedItems = getRemovedCountriesFromSP();
        removedItems.addAll(CountryAdapter.mRemovedCountries);
        System.out.println("save:"+removedItems);
        editor.putStringSet(COUNTRY_SET,  removedItems);

        editor.clear();
        editor.apply();
    }

    public Set<String> getRemovedCountriesFromSP()
    {
        Set<String> removedCountries = new LinkedHashSet<>();

        Log.i(MYTAG, "Reading removed countries from SP. (Method 3).");
        SharedPreferences sharedPref = getApplication().getApplicationContext().getSharedPreferences("removedCountries",Context.MODE_PRIVATE);
        removedCountries = sharedPref.getStringSet(COUNTRY_SET, removedCountries);

        return removedCountries;

    }

    public void saveDataToFile() {

        String myFormat = "dd/MM/yy";
        TimeZone timeZone = TimeZone.getTimeZone("Israel");
        QuarantineFragment.myCalendar.setTimeZone(timeZone);
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String startDate = sdf.format( QuarantineFragment.myCalendar.getTime());
        String endDate = sdf.format( QuarantineFragment.myCalendarEndQuarantine.getTime());
        String withTwoCoronaTests  =  QuarantineFragment.switchCoronaTest.isChecked() ? "true": "false";

        try {
            final String separator = System.getProperty("line.separator");
            FileOutputStream fos = context.getApplicationContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            System.out.println("Saving data to file");
            //saving data to file
            writer.write(startDate + separator);
            writer.write(endDate + separator);
            writer.write(withTwoCoronaTests + separator);


            writer.close();
            fos.close();

        } catch (Exception e) {
            e.getMessage();
        }

    }

       public String[] getDataFromFile() throws IOException {
        String startDate;
        String endDate;
        Boolean withTwoCoronaTests;
           FileInputStream fin;
        String twoCoronaTests;
        String [] data;
      try {
     fin = context.getApplicationContext().openFileInput(FILENAME);
      }
     catch (FileNotFoundException e)
     {
      return null;
     }
           BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
        //reading data from file
           startDate = reader.readLine();
           endDate = reader.readLine();
           withTwoCoronaTests = reader.readLine().equals("true") ? true : false;
           twoCoronaTests = withTwoCoronaTests == true ? "true":"false";

        //saving data to array
        data = new String[]{startDate,endDate,twoCoronaTests};

        return data;
    }
}
