package com.example.finalproject;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;


public class CountriesDetailsFragment extends Fragment{

    PieChart pieChartStats;
    BarChart barChartStats;
    CardView cardViewPieStats,cardViewBarChart;
    TabLayout tabLayout;
    TabItem pieChartItem,barChartItem,bothChartItem,noneChartItem;


    private TextView countryNameTextView;

    TextView textViewCases,textViewRecovered,textViewCritical,textViewActive,textViewTodayCases,textViewTotalDeaths,textTodayDeaths;

    private CountriesViewModel myViewModel;

    boolean cases,recovered,critical,active,todayCases,totalDeaths,todayDeaths;

    RelativeLayout relativeLayoutCases,relativeLayoutRecovered,relativeLayoutCritical,relativeLayoutActive,relativeLayoutTodayCases,relativeLayoutTotalDeaths,relativeLayoutTodayDeaths;

    TextView textViewCasesPerOneMillion,textViewTodayRecovered,textViewDeathsPerOneMillion,textViewTests,textViewTestsPerOneMillion,textViewPopulation
            ,textViewContinent,textViewOneCasePerPeople,textViewOneDeathPerPeople
            ,textViewOneTestPerPeople,textViewActivePerOneMillion,textViewRecoveredPerOneMillion,textViewCriticalPerOneMillion;

    RelativeLayout relativeLayoutCasesPerOneMillion,relativeLayoutTodayRecovered,relativeLayoutDeathsPerOneMillion,relativeLayoutTests,
            relativeLayoutTestsPerOneMillion,relativeLayoutPopulation,relativeLayoutContinent,relativeLayoutOneCasePerPeople,
            relativeLayoutOneDeathPerPeople
            ,relativeLayoutOneTestPerPeople,relativeLayoutActivePerOneMillion,relativeLayoutRecoveredPerOneMillion,
            relativeLayoutCriticalPerOneMillion;

    LinearLayout totalCasesLinearLayout,recoveredLinearLayout,criticalLinearLayout,totalDeathsLinearLayout,activeLinearLayout;


    boolean casesPerOneMillion,todayRecovered,deathsPerOneMillion,tests,testsPerOneMillion,population,continent,oneCasePerPeople,oneDeathPerPeople
            ,oneTestPerPeople,activePerOneMillion,recoveredPerOneMillion,criticalPerOneMillion;
    boolean showCountriesGraph;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreateView(inflater,container,savedInstanceState);
        return inflater.inflate(R.layout.countriesdetailsfrag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        countryNameTextView = view.findViewById(R.id.countryName);
        textViewCases = view.findViewById(R.id.textViewCasesDetails);
        textViewRecovered = view.findViewById(R.id.textViewRecoveredDetails);
        textViewCritical = view.findViewById(R.id.textViewCriticalDetails);
        textViewActive = view.findViewById(R.id.textViewActiveDetails);
        textViewTodayCases = view.findViewById(R.id.textViewTodayCasesDetails);
        textViewTotalDeaths = view.findViewById(R.id.textViewTotalDeathsDetails);
        textTodayDeaths = view.findViewById(R.id.textViewTodayDeathsDetails);
        textViewCasesPerOneMillion = view.findViewById(R.id.textViewCasesPerOneMillion);
        textViewTodayRecovered = view.findViewById(R.id.textViewTodayRecovered);
        textViewDeathsPerOneMillion = view.findViewById(R.id.textViewDeathsPerOneMillion);
        textViewTests = view.findViewById(R.id.textViewTests);
        textViewTestsPerOneMillion = view.findViewById(R.id.textViewTestsPerOneMillion);
        textViewPopulation = view.findViewById(R.id.textViewPopulation);
        textViewContinent = view.findViewById(R.id.textViewContinent);
        textViewOneCasePerPeople = view.findViewById(R.id.textViewOneCasePerPeople);
        textViewOneTestPerPeople = view.findViewById(R.id.textViewOneTestPerPeople);
        textViewActivePerOneMillion = view.findViewById(R.id.textViewActivePerOneMillion);
        textViewOneDeathPerPeople = view.findViewById(R.id.textViewOneDeathPerPeople);
        textViewRecoveredPerOneMillion = view.findViewById(R.id.textViewRecoveredPerOneMillion);
        textViewCriticalPerOneMillion = view.findViewById(R.id.textViewCriticalPerOneMillion);


        relativeLayoutCases = view.findViewById(R.id.relativeCases);
        relativeLayoutRecovered = view.findViewById(R.id.relativeRecovered);
        relativeLayoutCritical = view.findViewById(R.id.relativeCrititcal);
        relativeLayoutActive = view.findViewById(R.id.relativeActive);
        relativeLayoutTodayCases = view.findViewById(R.id.relativeTodayCases);
        relativeLayoutTotalDeaths = view.findViewById(R.id.relativeTotalDeaths);
        relativeLayoutTodayDeaths = view.findViewById(R.id.relativeTodayDeaths);
        relativeLayoutCasesPerOneMillion = view.findViewById(R.id.relativeCasesPerOneMillion);
        relativeLayoutTodayRecovered = view.findViewById(R.id.relativeTodayRecovered);
        relativeLayoutDeathsPerOneMillion = view.findViewById(R.id.relativeDeathsPerOneMillion);
        relativeLayoutTests = view.findViewById(R.id.relativeTests);
        relativeLayoutTestsPerOneMillion = view.findViewById(R.id.relativeTestsPerOneMillion);
        relativeLayoutPopulation = view.findViewById(R.id.relativePopulation);
        relativeLayoutContinent = view.findViewById(R.id.relativeContinent);
        relativeLayoutOneCasePerPeople = view.findViewById(R.id.relativeOneCasePerPeople);
        relativeLayoutOneDeathPerPeople = view.findViewById(R.id.relativeOneDeathPerPeople);
        relativeLayoutOneTestPerPeople = view.findViewById(R.id.relativeOneTestPerPeople);
        relativeLayoutActivePerOneMillion = view.findViewById(R.id.relativeActivePerOneMillion);
        relativeLayoutRecoveredPerOneMillion = view.findViewById(R.id.relativeRecoveredPerOneMillion);
        relativeLayoutCriticalPerOneMillion = view.findViewById(R.id.relativeCriticalPerOneMillion);

        totalCasesLinearLayout = (LinearLayout)view.findViewById(R.id.casesLinearLayoutGlobal);
        recoveredLinearLayout = (LinearLayout)view.findViewById(R.id.recoveredLinearLayoutGlobal);
        criticalLinearLayout = (LinearLayout) view.findViewById(R.id.criticalLinearLayoutGlobal);
        totalDeathsLinearLayout =(LinearLayout) view.findViewById(R.id.deathsLinearLayoutGlobal);
        activeLinearLayout = (LinearLayout) view.findViewById(R.id.activeLinearLayoutGlobal);

        pieChartStats = view.findViewById(R.id.piechart);
        barChartStats = view.findViewById(R.id.barchart);
        cardViewPieStats = view.findViewById(R.id.cardViewGraphCountryPieChart);
        cardViewBarChart = view.findViewById(R.id.cardViewGraphCountryBar);

        tabLayout = view.findViewById(R.id.tabLayoutCountries);
        pieChartItem = view.findViewById(R.id.piechartItem);
        barChartItem = view.findViewById(R.id.barchartItem);
        bothChartItem = view.findViewById(R.id.bothChartItem);
        noneChartItem = view.findViewById(R.id.noneChartItem);



        myViewModel = CountriesViewModel.getInstance(getActivity().getApplication(),getContext());


        initSwitchPreferenceState();



        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if(nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            view.setBackgroundColor(Color.parseColor("#424242"));
        }
        else {
            view.setBackgroundColor(Color.parseColor("#F1F1F1"));
        }


        Observer<Country> userListUpdateObserver = new Observer<Country>() {

            @Override
            public void onChanged(Country selectedCountry) {

                String countryName = selectedCountry.getCountry();
                String casesNumber = selectedCountry.getCases();
                String todayCasesNumber = selectedCountry.getTodayCases();
                String totalDeathsNumber = selectedCountry.getDeaths();
                String todayDeathsNumber = selectedCountry.getTodayDeaths();
                String recoveredNumber =selectedCountry.getRecovered();
                String activeNumber =selectedCountry.getActive();
                String criticalNumber = selectedCountry.getCritical();
                String casesPerOneMillionNumber = selectedCountry.getCasesPerOneMillion();
                String todayRecoveredNumber = selectedCountry.getTodayRecovered();
                String deathsPerOneMillionNumber = selectedCountry.getDeathsPerOneMillion();
                String testsNumber = selectedCountry.getTests();
                String testsPerOneMillionNumber = selectedCountry.getTestsPerOneMillion();
                String populationNumber = selectedCountry.getPopulation();
                String continentName = selectedCountry.getContinent();
                String oneCasePerPeopleNumber = selectedCountry.getOneCasePerPeople();
                String oneDeathPerPeopleNumber = selectedCountry.getOneDeathPerPeople();
                String oneTestPerPeopleNumber = selectedCountry.getOneTestPerPeople();
                String activePerOneMillionNumber = selectedCountry.getActivePerOneMillion();
                String recoveredPerOneMillionNumber = selectedCountry.getRecoveredPerOneMillion();
                String criticalPerOneMillionNumber = selectedCountry.getCriticalPerOneMillion();

                countryNameTextView.setText(countryName);

                if(cases) {
                    relativeLayoutCases.setVisibility(View.VISIBLE);
                    textViewCases.setText(casesNumber);
                }
                else {
                    relativeLayoutCases.setVisibility(View.GONE);
                }

                if(recovered) {
                    relativeLayoutRecovered.setVisibility(View.VISIBLE);
                    textViewRecovered.setText(recoveredNumber);
                }
                else {
                    relativeLayoutRecovered.setVisibility(View.GONE);
                }

                if(critical) {
                    relativeLayoutCritical.setVisibility(View.VISIBLE);
                    textViewCritical.setText(criticalNumber);
                }
                else{
                   relativeLayoutCritical.setVisibility(View.GONE);
                }

                if(active) {
                    relativeLayoutActive.setVisibility(View.VISIBLE);
                    textViewActive.setText(activeNumber);
                }
                else{
                    relativeLayoutActive.setVisibility(View.GONE);
                }
                if(todayCases) {
                    relativeLayoutTodayCases.setVisibility(View.VISIBLE);
                    textViewTodayCases.setText(todayCasesNumber);
                }
                else{
                    relativeLayoutTodayCases.setVisibility(View.GONE);
                }
                if(totalDeaths){
                    relativeLayoutTotalDeaths.setVisibility(View.VISIBLE);
                    textViewTotalDeaths.setText(totalDeathsNumber);
                }
               else {
                    relativeLayoutTotalDeaths.setVisibility(View.GONE);
                }
               if(todayDeaths) {
                   relativeLayoutTodayDeaths.setVisibility(View.VISIBLE);
                   textTodayDeaths.setText(todayDeathsNumber);
               }
               else{
                   relativeLayoutTodayDeaths.setVisibility(View.GONE);
               }

                if(casesPerOneMillion) {
                    relativeLayoutCasesPerOneMillion.setVisibility(View.VISIBLE);
                    textViewCasesPerOneMillion.setText(casesPerOneMillionNumber);
                }
                else{
                    relativeLayoutCasesPerOneMillion.setVisibility(View.GONE);
                }

                if(todayRecovered) {
                    relativeLayoutTodayRecovered.setVisibility(View.VISIBLE);
                    textViewTodayRecovered.setText(todayRecoveredNumber);
                }
                else{
                    relativeLayoutTodayRecovered.setVisibility(View.GONE);
                }

                if(deathsPerOneMillion) {
                    relativeLayoutDeathsPerOneMillion.setVisibility(View.VISIBLE);
                    textViewDeathsPerOneMillion.setText(deathsPerOneMillionNumber);
                }
                else{
                    relativeLayoutDeathsPerOneMillion.setVisibility(View.GONE);
                }

                if(casesPerOneMillion) {
                    relativeLayoutTests.setVisibility(View.VISIBLE);
                    textViewTests.setText(testsNumber);
                }
                else{
                    relativeLayoutTests.setVisibility(View.GONE);
                }

                if(testsPerOneMillion) {
                    relativeLayoutTestsPerOneMillion.setVisibility(View.VISIBLE);
                    textViewTestsPerOneMillion.setText(testsPerOneMillionNumber);
                }
                else{
                    relativeLayoutTestsPerOneMillion.setVisibility(View.GONE);
                }

                if(population) {
                    relativeLayoutPopulation.setVisibility(View.VISIBLE);
                    textViewPopulation.setText(populationNumber);
                }
                else{
                    relativeLayoutPopulation.setVisibility(View.GONE);
                }

                if(continent) {
                    relativeLayoutContinent.setVisibility(View.VISIBLE);
                    textViewContinent.setText(continentName);
                }
                else{
                    relativeLayoutContinent.setVisibility(View.GONE);
                }

                if(oneCasePerPeople) {
                    relativeLayoutOneCasePerPeople.setVisibility(View.VISIBLE);
                    textViewOneCasePerPeople.setText(oneCasePerPeopleNumber);
                }
                else{
                    relativeLayoutOneCasePerPeople.setVisibility(View.GONE);
                }

                if(oneDeathPerPeople) {
                    relativeLayoutOneDeathPerPeople.setVisibility(View.VISIBLE);
                    textViewOneDeathPerPeople.setText(oneDeathPerPeopleNumber);
                }
                else{
                    relativeLayoutOneDeathPerPeople.setVisibility(View.GONE);
                }

                if(oneTestPerPeople) {
                    relativeLayoutOneTestPerPeople.setVisibility(View.VISIBLE);
                    textViewOneTestPerPeople.setText(oneTestPerPeopleNumber);
                }
                else{
                    relativeLayoutOneTestPerPeople.setVisibility(View.GONE);
                }

                if(activePerOneMillion) {
                    relativeLayoutActivePerOneMillion.setVisibility(View.VISIBLE);
                    textViewActivePerOneMillion.setText(activePerOneMillionNumber);
                }
                else{
                    relativeLayoutActivePerOneMillion.setVisibility(View.GONE);
                }

                if(recoveredPerOneMillion) {
                    relativeLayoutRecoveredPerOneMillion.setVisibility(View.VISIBLE);
                    textViewRecoveredPerOneMillion.setText(recoveredPerOneMillionNumber);
                }
                else{
                    relativeLayoutRecoveredPerOneMillion.setVisibility(View.GONE);
                }

                if(criticalPerOneMillion) {
                    relativeLayoutCriticalPerOneMillion.setVisibility(View.VISIBLE);
                    textViewCriticalPerOneMillion.setText(criticalPerOneMillionNumber);
                }
                else{
                    relativeLayoutCriticalPerOneMillion.setVisibility(View.GONE);
                }

                if(cases) {
                    totalCasesLinearLayout.setVisibility(View.VISIBLE);
                    pieChartStats.addPieSlice(new PieModel("Cases", Integer.parseInt(casesNumber), Color.parseColor("#FFA726")));
                    barChartStats.addBar(new BarModel("Cases", Integer.parseInt(casesNumber), Color.parseColor("#FFA726")));
                }
                else{
                    totalCasesLinearLayout.setVisibility(View.GONE);
                }
                if(recovered) {
                    pieChartStats.addPieSlice(new PieModel("Recovered", Integer.parseInt(recoveredNumber), Color.parseColor("#66BB6A")));
                    barChartStats.addBar(new BarModel("Recovered", Integer.parseInt(recoveredNumber), Color.parseColor("#66BB6A")));
                }
                else{
                    recoveredLinearLayout.setVisibility(View.GONE);
                }
                if(critical) {
                    pieChartStats.addPieSlice(new PieModel("Critical", Integer.parseInt(criticalNumber), Color.parseColor("#3a243b")));
                    barChartStats.addBar(new BarModel("Critical", Integer.parseInt(criticalNumber), Color.parseColor("#3a243b")));
                }
                else{
                    criticalLinearLayout.setVisibility(View.GONE);
                }
                if(totalDeaths) {
                    pieChartStats.addPieSlice(new PieModel("Deaths", Integer.parseInt(totalDeathsNumber), Color.parseColor("#EF5350")));
                    barChartStats.addBar(new BarModel("Deaths", Integer.parseInt(totalDeathsNumber), Color.parseColor("#EF5350")));
                }
                else{
                    totalDeathsLinearLayout.setVisibility(View.GONE);
                }
                if(active) {
                    pieChartStats.addPieSlice(new PieModel("Active", Integer.parseInt(activeNumber), Color.parseColor("#29B6F6")));
                    barChartStats.addBar(new BarModel("Active", Integer.parseInt(activeNumber), Color.parseColor("#29B6F6")));
                }
                else{
                    activeLinearLayout.setVisibility(View.GONE);
                }


                if(cases||recovered||critical||totalDeaths||active){
                    tabLayout.setVisibility(View.VISIBLE);
                }
                else{
                    showCountriesGraph=false;
                    tabLayout.setVisibility(View.GONE);
                }

                if(showCountriesGraph){
                    tabLayout.setVisibility(View.VISIBLE);
                }
                else{
                    tabLayout.setVisibility(View.GONE);
                }

                pieChartStats.startAnimation();
                barChartStats.startAnimation();

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
        };

        myViewModel.getItemSelected().observe(getViewLifecycleOwner(), userListUpdateObserver);

        super.onViewCreated(view, savedInstanceState);
    }


    public void initSwitchPreferenceState() {

        cases = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("cases", false);
        todayCases = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("todayCases", false);
        totalDeaths =  PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("totalDeaths", false);
        todayDeaths = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("todayDeaths", false);
        recovered = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("recovered", false);
        active = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("active", false);
        critical = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("critical", false);
        casesPerOneMillion = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("casesPerOneMillion", false);
        todayRecovered = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("todayRecovered", false);
        deathsPerOneMillion = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("deathsPerOneMillion", false);
        tests = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("tests", false);
        testsPerOneMillion = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("testsPerOneMillion", false);
        population = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("population", false);
        continent = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("continent", false);
        oneCasePerPeople = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("oneCasePerPeople", false);
        oneDeathPerPeople = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("oneDeathPerPeople", false);
        oneTestPerPeople = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("oneTestPerPeople", false);
        activePerOneMillion = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("activePerOneMillion", false);
        recoveredPerOneMillion = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("recoveredPerOneMillion", false);
        criticalPerOneMillion = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("criticalPerOneMillion", false);
        showCountriesGraph = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("showCountriesGraphs", false);

    }


}
