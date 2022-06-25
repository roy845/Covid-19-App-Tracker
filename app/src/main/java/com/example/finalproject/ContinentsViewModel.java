package com.example.finalproject;

import android.app.Application;
import android.content.Context;
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
import java.util.ArrayList;

public class ContinentsViewModel  extends AndroidViewModel {

    private static ContinentsViewModel instance;
    private MutableLiveData<ArrayList<Continent>> continentLiveData;
    static ArrayList<Continent> allContinents;
    MutableLiveData<Continent> indexItemSelected;
    static Context context;
    String icons[] ={"northamerica","asia","southamerica","europe","africa","australia"};


    public ContinentsViewModel(@NonNull Application application,Context context) {
        super(application);
        this.context=context;
        init(context);
    }

    public LiveData<ArrayList<Continent>> getContinentLiveData() {
        return continentLiveData;
    }
    public MutableLiveData<Continent> getItemSelected() {
        return indexItemSelected;
    }
    public void setItemSelect(Continent continent) {
        indexItemSelected.setValue(continent);
    }
    public void setCountryLiveData(ArrayList<Continent> list) {
        continentLiveData.setValue(list);
    }

    public static ContinentsViewModel getInstance(Application application,Context context) {
        if (instance == null) {
            instance = new ContinentsViewModel(application,context);
        }
        return instance;
    }

    public void init(Context context) {

        continentLiveData = new MutableLiveData<>();
        allContinents = new ArrayList<>();
        continentLiveData.setValue(parseContinents(context));
        indexItemSelected = new MutableLiveData<Continent>();

    }

    public void showAdapter(Context context)
    {
        ContinentFragment.continentAdapter = new ContinentAdapter(getApplication(),context);
        ContinentFragment.recyclerViewContinents.setAdapter(ContinentFragment.continentAdapter);
    }

    private ArrayList<Continent> parseContinents(Context context) {

        String urlGlobalStats = "https://corona.lmao.ninja/v2/continents/";
        StringRequest globalStatsRequest = new StringRequest(Request.Method.GET, urlGlobalStats,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Continent continent=null;

                            JSONArray jsonArray = new JSONArray((response));
                            for(int i = 0 ;i<jsonArray.length();i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
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
                                String continentName = jsonObject.getString("continent");
                                String activePerOneMillion = jsonObject.getString("activePerOneMillion");
                                String recoveredPerOneMillion = jsonObject.getString("recoveredPerOneMillion");
                                String criticalPerOneMillion = jsonObject.getString("criticalPerOneMillion");


                                continent = new Continent(cases,todayCases,deaths,todayDeaths,recovered,todayRecovered,active,critical,casesPerOneMillion,deathsPerOneMillion,tests,testsPerOneMillion,population,continentName,activePerOneMillion,recoveredPerOneMillion,criticalPerOneMillion,icons[i]);
                                allContinents.add(continent);
                            }


                            showAdapter(context);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication().getApplicationContext(),"There was an error loading data",Toast.LENGTH_LONG).show();
                error.getMessage();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(globalStatsRequest);

        return allContinents;
    }
}
