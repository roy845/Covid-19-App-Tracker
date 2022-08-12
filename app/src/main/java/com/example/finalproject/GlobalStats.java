package com.example.finalproject;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.leo.simplearcloader.SimpleArcLoader;
import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;


public class GlobalStats extends Fragment {

    TextView textViewCases,textViewRecovered,textViewCritical,textViewActive,textViewTodayCases,textViewTotalDeaths,textTodayDeaths
            ,textViewAffectedCountries;
    ScrollView scrollViewStats;
    PieChart pieChartStats;
    SimpleArcLoader simpleArcLoader;
    Button trackBtn;
    BarChart barChartStats;
    CardView cardViewPieStats,cardViewBarChart;
    TabLayout tabLayout;
    boolean showGlobalGraphs,cases,recovered,critical,deaths,active,todayCases,todayDeaths,affectedCountries;

    boolean casesPerOneMillion,todayRecovered,deathsPerOneMillion,tests,testsPerOneMillion,population,continent,oneCasePerPeople,oneDeathPerPeople
            ,oneTestPerPeople,activePerOneMillion,recoveredPerOneMillion,criticalPerOneMillion;

    LinearLayout casesLinearLayout,recoveredLinearLayout,criticalLinearLayout,deathsLinearLayout,activeLinearLayout;

    RelativeLayout relativeLayoutCases,relativeLayoutRecovered,relativeLayoutCritical,relativeLayoutActive,relativeLayoutTodayCases
            ,relativeLayoutTotalDeaths,
            relativeLayoutTodayDeaths,relativeLayoutAffectedCountries;

    RelativeLayout relativeLayoutTodayRecovered,relativeLayoutCasesPerOneMillion,relativeLayoutDeathsPerOneMillion,
            relativeLayoutTests,relativeLayoutTestsPerOneMillion,relativeLayoutPopulation,relativeLayoutOneCasePerPeople,
            relativeLayoutOneDeathPerPeople,relativeLayoutOneTestPerPeople,relativeLayoutActivePerOneMillion,
            relativeLayoutRecoveredPerOneMillion,relativeLayoutCriticalPerOneMillion;

    TextView todayRecoveredTextView,casesPerOneMillionTextView,deathsPerOneMillionTextView,testsTextView,testsPerOneMillionTextView
            ,populationTextView,oneCasePerPeopleTextView,oneDeathPerPeopleTextView
            ,oneTestPerPeopleTextView,activePerOneMillionTextView,recoveredPerOneMillionTextView,criticalPerOneMillionTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout globalStats for this fragment
        return inflater.inflate(R.layout.globalstats, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        textViewCases = view.findViewById(R.id.textViewCasesDetailsGlobal);
        textViewRecovered = view.findViewById(R.id.textViewRecoveredDetailsGlobal);
        textViewCritical = view.findViewById(R.id.textViewCriticalDetailsGlobal);
        textViewActive = view.findViewById(R.id.textViewActiveDetailsGlobal);
        textViewTodayCases = view.findViewById(R.id.textViewTodayCasesDetailsGlobal);
        textViewTotalDeaths = view.findViewById(R.id.textViewTotalDeathsDetailsGlobal);
        textTodayDeaths = view.findViewById(R.id.textViewTodayDeathsDetailsGlobal);
        textViewAffectedCountries = view.findViewById(R.id.textViewAffectedCountriesDetailsGlobal);
        todayRecoveredTextView = view.findViewById(R.id.textViewTodayRecoveredDetailsGlobal);
        casesPerOneMillionTextView = view.findViewById(R.id.textViewCasesPerOneMillionDetailsGlobal);
        deathsPerOneMillionTextView = view.findViewById(R.id.textViewDeathsPerOneMillionDetailsGlobal);
        testsTextView = view.findViewById(R.id.textViewTestsDetailsGlobal);
        testsPerOneMillionTextView = view.findViewById(R.id.textViewTestsPerOneMillionDetailsGlobal);
        populationTextView = view.findViewById(R.id.textViewPopulationDetailsGlobal);
        oneCasePerPeopleTextView = view.findViewById(R.id.textViewOneCasePerPeopleDetailsGlobal);
        oneDeathPerPeopleTextView = view.findViewById(R.id.textViewOneDeathPerPeopleDetailsGlobal);
        oneTestPerPeopleTextView = view.findViewById(R.id.textViewOneTestPerPeopleDetailsGlobal);
        activePerOneMillionTextView = view.findViewById(R.id.textViewActivePerOneMillionDetailsGlobal);
        recoveredPerOneMillionTextView = view.findViewById(R.id.textViewRecoveredPerOneMillionDetailsGlobal);
        criticalPerOneMillionTextView = view.findViewById(R.id.textViewCriticalPerOneMillionDetailsGlobal);

        simpleArcLoader = view.findViewById(R.id.simple_arc_loader);
        scrollViewStats = view.findViewById(R.id.scrollStats);
        pieChartStats = view.findViewById(R.id.piechart);
        barChartStats = view.findViewById(R.id.barchart);
        cardViewPieStats = view.findViewById(R.id.cardViewCountries);
        cardViewBarChart = view.findViewById(R.id.cardViewGraphCountryBar);
        tabLayout = view.findViewById(R.id.tabLayoutGlobal);
        trackBtn = view.findViewById(R.id.btnTrack);

        relativeLayoutCases = view.findViewById(R.id.relativeLayoutCasesGlobal);
        relativeLayoutRecovered = view.findViewById(R.id.relativeLayoutRecoveredGlobal);
        relativeLayoutCritical = view.findViewById(R.id.relativeLayoutCriticalGlobal);
        relativeLayoutActive = view.findViewById(R.id.relativeLayoutActiveGlobal);
        relativeLayoutTodayCases = view.findViewById(R.id.relativeLayoutTodayCasesGlobal);
        relativeLayoutTotalDeaths = view.findViewById(R.id.relativeLayoutTotalDeathsGlobal);
        relativeLayoutTodayDeaths = view.findViewById(R.id.relativeLayoutTodayDeathsGlobal);
        relativeLayoutAffectedCountries = view.findViewById(R.id.relativeLayoutAffectedCountries);
        relativeLayoutTodayRecovered = view.findViewById(R.id.relativeLayoutTodayRecoveredGlobal);
        relativeLayoutCasesPerOneMillion = view.findViewById(R.id.relativeLayoutCasesPerOneMillionGlobal);
        relativeLayoutDeathsPerOneMillion = view.findViewById(R.id.relativeLayoutDeathsPerOneMillionGlobal);
        relativeLayoutTests = view.findViewById(R.id.relativeLayoutTestsGlobal);
        relativeLayoutTestsPerOneMillion = view.findViewById(R.id.relativeLayoutTestsPerOneMillionGlobal);
        relativeLayoutPopulation = view.findViewById(R.id.relativeLayoutPopulationGlobal);
        relativeLayoutOneCasePerPeople = view.findViewById(R.id.relativeLayoutOneCasePerPeopleGlobal);
        relativeLayoutOneDeathPerPeople = view.findViewById(R.id.relativeLayoutOneDeathPerPeopleGlobal);
        relativeLayoutOneTestPerPeople = view.findViewById(R.id.relativeLayoutOneTestPerPeopleGlobal);
        relativeLayoutActivePerOneMillion = view.findViewById(R.id.relativeLayoutActivePerOneMillionGlobal);
        relativeLayoutRecoveredPerOneMillion = view.findViewById(R.id.relativeLayoutRecoveredPerOneMillionGlobal);
        relativeLayoutCriticalPerOneMillion = view.findViewById(R.id.relativeLayoutCriticalPerOneMillionGlobal);

        casesLinearLayout = view.findViewById(R.id.casesLinearLayoutGlobal);
        recoveredLinearLayout = view.findViewById(R.id.recoveredLinearLayoutGlobal);
        criticalLinearLayout = view.findViewById(R.id.criticalLinearLayoutGlobal);
        deathsLinearLayout = view.findViewById(R.id.deathsLinearLayoutGlobal);
        activeLinearLayout = view.findViewById(R.id.activeLinearLayoutGlobal);
       // swipeRefreshLayout = view.findViewById(R.id.swipeRefreshDetails);

        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if(nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            trackBtn.setBackgroundColor(Color.parseColor("#6f6f6f"));
            view.setBackgroundColor(Color.parseColor("#424242"));
        }
        else
        {
            trackBtn.setBackgroundColor(Color.parseColor("#6200EE"));
            view.setBackgroundColor(Color.parseColor("#71CCE6"));
        }

        trackBtn.setOnClickListener(new HandleClick());
        getGlobalStats();
        initSwitchPreferences();


        cardViewPieStats.setVisibility(View.GONE);
        cardViewBarChart.setVisibility(View.GONE);



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0) {
                    cardViewPieStats.setVisibility(View.VISIBLE);
                    cardViewBarChart.setVisibility(View.GONE);

                    pieChartStats.startAnimation();
                }

                if(tab.getPosition() == 1) {
                    cardViewBarChart.setVisibility(View.VISIBLE);
                    cardViewPieStats.setVisibility(View.GONE);

                    barChartStats.startAnimation();
                }
                if(tab.getPosition() == 2){
                    cardViewPieStats.setVisibility(View.VISIBLE);
                    cardViewBarChart.setVisibility(View.VISIBLE);

                    barChartStats.startAnimation();
                    pieChartStats.startAnimation();
                }

                if(tab.getPosition() == 3){
                    cardViewPieStats.setVisibility(View.GONE);
                    cardViewBarChart.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    public void initSwitchPreferences()
    {
        cases = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("cases", false);
        recovered = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("recovered", false);
        critical = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("critical", false);
        deaths =  PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("totalDeaths", false);
        active = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("active", false);
        todayCases = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("todayCases", false);
        todayDeaths = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("todayDeaths", false);
        affectedCountries = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("affectedCountries", false);
        showGlobalGraphs = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("showGlobalGraphs", false);
        todayRecovered = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("todayRecovered", false);
        casesPerOneMillion = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("casesPerOneMillion", false);
        deathsPerOneMillion = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("deathsPerOneMillion", false);
        tests = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("tests", false);
        testsPerOneMillion = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("testsPerOneMillion", false);
        population = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("population", false);
        oneCasePerPeople = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("oneCasePerPeople", false);
        oneDeathPerPeople = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("oneDeathPerPeople", false);
        oneTestPerPeople = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("oneTestPerPeople", false);
        activePerOneMillion = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("activePerOneMillion", false);
        recoveredPerOneMillion = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("recoveredPerOneMillion", false);
        criticalPerOneMillion = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("criticalPerOneMillion", false);


    }


    private class HandleClick implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("https://www.worldometers.info/coronavirus/?utm_campaign=homeAdvegas1?"));
            startActivity(intent);
        }
    }



    private void getGlobalStats() {

        String urlGlobalStats = "https://disease.sh/v3/covid-19/all";
        simpleArcLoader.start();
        StringRequest globalStatsRequest = new StringRequest(Request.Method.GET, urlGlobalStats,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject((response.toString()));

                            if(cases) {
                                casesLinearLayout.setVisibility(View.VISIBLE);
                                relativeLayoutCases.setVisibility(View.VISIBLE);
                                textViewCases.setText(jsonObject.getString("cases"));
                                pieChartStats.addPieSlice(new PieModel("Cases", Integer.parseInt(textViewCases.getText().toString()), Color.parseColor("#FFA726")));
                                barChartStats.addBar(new BarModel("Cases", Integer.parseInt(textViewCases.getText().toString()), Color.parseColor("#FFA726")));
                            }
                            else{
                                casesLinearLayout.setVisibility(View.GONE);
                                relativeLayoutCases.setVisibility(View.GONE);
                            }
                            if(recovered) {
                                recoveredLinearLayout.setVisibility(View.VISIBLE);
                                relativeLayoutRecovered.setVisibility(View.VISIBLE);
                                textViewRecovered.setText(jsonObject.getString("recovered"));
                                pieChartStats.addPieSlice(new PieModel("Recovered", Integer.parseInt(textViewRecovered.getText().toString()), Color.parseColor("#66BB6A")));
                                barChartStats.addBar(new BarModel("Recovered", Integer.parseInt(textViewRecovered.getText().toString()), Color.parseColor("#66BB6A")));
                            }
                            else{
                                recoveredLinearLayout.setVisibility(View.GONE);
                                relativeLayoutRecovered.setVisibility(View.GONE);
                            }
                            if(critical) {
                                criticalLinearLayout.setVisibility(View.VISIBLE);
                                relativeLayoutCritical.setVisibility(View.VISIBLE);
                                textViewCritical.setText(jsonObject.getString("critical"));
                                pieChartStats.addPieSlice(new PieModel("Critical", Integer.parseInt(textViewCritical.getText().toString()), Color.parseColor("#3a243b")));
                                barChartStats.addBar(new BarModel("Critical", Integer.parseInt(textViewCritical.getText().toString()), Color.parseColor("#3a243b")));
                            }
                            else{
                                relativeLayoutCritical.setVisibility(View.GONE);
                                criticalLinearLayout.setVisibility(View.GONE);
                            }
                            if(deaths) {
                                deathsLinearLayout.setVisibility(View.VISIBLE);
                                relativeLayoutTotalDeaths.setVisibility(View.VISIBLE);
                                textViewTotalDeaths.setText(jsonObject.getString("deaths"));
                                pieChartStats.addPieSlice(new PieModel("Deaths", Integer.parseInt(textViewTotalDeaths.getText().toString()), Color.parseColor("#EF5350")));
                                barChartStats.addBar(new BarModel("Deaths", Integer.parseInt(textViewTotalDeaths.getText().toString()), Color.parseColor("#EF5350")));
                            }
                            else{
                                deathsLinearLayout.setVisibility(View.GONE);
                                relativeLayoutTotalDeaths.setVisibility(View.GONE);
                            }
                            if(active) {
                                activeLinearLayout.setVisibility(View.VISIBLE);
                                relativeLayoutActive.setVisibility(View.VISIBLE);
                                textViewActive.setText(jsonObject.getString("active"));
                                pieChartStats.addPieSlice(new PieModel("Active", Integer.parseInt(textViewActive.getText().toString()), Color.parseColor("#29B6F6")));
                                barChartStats.addBar(new BarModel("Active", Integer.parseInt(textViewActive.getText().toString()), Color.parseColor("#29B6F6")));
                            }
                            else{
                                activeLinearLayout.setVisibility(View.GONE);
                                relativeLayoutActive.setVisibility(View.GONE);
                            }
                            if(todayCases){
                                relativeLayoutTodayCases.setVisibility(View.VISIBLE);
                                textViewTodayCases.setText(jsonObject.getString("todayCases"));
                            }
                            else{
                                relativeLayoutTodayCases.setVisibility(View.GONE);
                            }

                            if(todayDeaths){
                                relativeLayoutTodayDeaths.setVisibility(View.VISIBLE);
                                textTodayDeaths.setText(jsonObject.getString("todayDeaths"));
                            }
                            else{
                                relativeLayoutTodayDeaths.setVisibility(View.GONE);
                            }

                            if(affectedCountries) {
                            relativeLayoutAffectedCountries.setVisibility(View.VISIBLE);
                            textViewAffectedCountries.setText(jsonObject.getString("affectedCountries"));
                            }
                            else{
                                relativeLayoutAffectedCountries.setVisibility(View.GONE);
                            }


                           if(todayRecovered){
                               relativeLayoutTodayRecovered.setVisibility(View.VISIBLE);
                               todayRecoveredTextView.setText(jsonObject.getString("todayRecovered"));
                           }

                           else{
                               relativeLayoutTodayRecovered.setVisibility(View.GONE);
                           }

                            if(casesPerOneMillion){
                                relativeLayoutCasesPerOneMillion.setVisibility(View.VISIBLE);
                                casesPerOneMillionTextView.setText(jsonObject.getString("casesPerOneMillion"));
                            }

                            else{
                                relativeLayoutCasesPerOneMillion.setVisibility(View.GONE);
                            }

                            if(deathsPerOneMillion){
                                relativeLayoutDeathsPerOneMillion.setVisibility(View.VISIBLE);
                                deathsPerOneMillionTextView.setText(jsonObject.getString("deathsPerOneMillion"));
                            }

                            else{
                                relativeLayoutDeathsPerOneMillion.setVisibility(View.GONE);
                            }

                            if(tests){
                                relativeLayoutTests.setVisibility(View.VISIBLE);
                                testsTextView.setText(jsonObject.getString("tests"));
                            }

                            else{
                                relativeLayoutTests.setVisibility(View.GONE);
                            }

                            if(testsPerOneMillion){
                                relativeLayoutTestsPerOneMillion.setVisibility(View.VISIBLE);
                                testsPerOneMillionTextView.setText(jsonObject.getString("testsPerOneMillion"));
                            }

                            else{
                                relativeLayoutTestsPerOneMillion.setVisibility(View.GONE);
                            }

                            if(population){
                                relativeLayoutPopulation.setVisibility(View.VISIBLE);
                                populationTextView.setText(jsonObject.getString("population"));
                            }

                            else{
                                relativeLayoutPopulation.setVisibility(View.GONE);
                            }

                            if(oneCasePerPeople){
                                relativeLayoutOneCasePerPeople.setVisibility(View.VISIBLE);
                                oneCasePerPeopleTextView.setText(jsonObject.getString("oneCasePerPeople"));
                            }

                            else{
                                relativeLayoutOneCasePerPeople.setVisibility(View.GONE);
                            }

                            if(oneDeathPerPeople){
                                relativeLayoutOneDeathPerPeople.setVisibility(View.VISIBLE);
                                oneDeathPerPeopleTextView.setText(jsonObject.getString("oneDeathPerPeople"));
                            }

                            else{
                                relativeLayoutOneDeathPerPeople.setVisibility(View.GONE);
                            }

                            if(oneTestPerPeople){
                                relativeLayoutOneTestPerPeople.setVisibility(View.VISIBLE);
                                oneTestPerPeopleTextView.setText(jsonObject.getString("oneTestPerPeople"));
                            }

                            else{
                                relativeLayoutOneTestPerPeople.setVisibility(View.GONE);
                            }

                            if(activePerOneMillion){
                                relativeLayoutActivePerOneMillion.setVisibility(View.VISIBLE);
                                activePerOneMillionTextView.setText(jsonObject.getString("activePerOneMillion"));
                            }

                            else{
                                relativeLayoutActivePerOneMillion.setVisibility(View.GONE);
                            }

                            if(recoveredPerOneMillion){
                                relativeLayoutRecoveredPerOneMillion.setVisibility(View.VISIBLE);
                                recoveredPerOneMillionTextView.setText(jsonObject.getString("recoveredPerOneMillion"));
                            }

                            else{
                                relativeLayoutRecoveredPerOneMillion.setVisibility(View.GONE);
                            }

                            if(criticalPerOneMillion){
                                relativeLayoutCriticalPerOneMillion.setVisibility(View.VISIBLE);
                                criticalPerOneMillionTextView.setText(jsonObject.getString("criticalPerOneMillion"));
                            }

                            else{
                                relativeLayoutCriticalPerOneMillion.setVisibility(View.GONE);
                            }

                            if(cases||recovered||critical||deaths||active){
                                tabLayout.setVisibility(View.VISIBLE);
                            }

                            else{
                                showGlobalGraphs=false;
                                tabLayout.setVisibility(View.GONE);
                            }

                            if(showGlobalGraphs){
                                tabLayout.setVisibility(View.VISIBLE);
                            }

                            else{
                                tabLayout.setVisibility(View.GONE);
                            }

                            pieChartStats.startAnimation();
                            barChartStats.startAnimation();
                            simpleArcLoader.stop();

                            simpleArcLoader.setVisibility(View.GONE);
                            scrollViewStats.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                            scrollViewStats.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                scrollViewStats.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(),"There was an error loading data",Toast.LENGTH_LONG).show();
                error.getMessage();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(globalStatsRequest);
    }
}
