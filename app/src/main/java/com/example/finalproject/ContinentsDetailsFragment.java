package com.example.finalproject;

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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.android.material.tabs.TabLayout;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;



public class ContinentsDetailsFragment extends Fragment {

    PieChart pieChartStatsContinents;
    BarChart barChartStatsContinents;
    CardView cardViewPieStatsContinents,cardViewBarChartContinents;
    TabLayout tabLayoutContinents;
    
    ContinentsViewModel ViewModelContinents;
    private TextView continentNameTextView;
    TextView textViewCases,textViewTodayCases,textViewTotalDeaths,textViewTodayDeaths,textViewRecovered,textViewTodayRecovered,
    textViewActive,textViewCritical,textViewCasesPerOneMillion,textViewDeathsPerOneMillion,textViewTests,textViewTestsPerOneMillion,
            textViewPopulation,textViewActivePerOneMillion,textViewRecoveredPerOneMillion,textViewCriticalPerOneMillion;

    RelativeLayout relativeLayoutCases,relativeLayoutRecovered,relativeLayoutCritical,relativeLayoutActive
            ,relativeLayoutTodayCases,relativeLayoutTotalDeaths,relativeLayoutTodayDeaths;

    RelativeLayout relativeLayoutCasesPerOneMillion,relativeLayoutTodayRecovered,relativeLayoutDeathsPerOneMillion
            ,relativeLayoutTests,
            relativeLayoutTestsPerOneMillion,relativeLayoutPopulation
            ,relativeLayoutActivePerOneMillion,relativeLayoutRecoveredPerOneMillion,
            relativeLayoutCriticalPerOneMillion;

    LinearLayout totalCasesLinearLayout,recoveredLinearLayout,criticalLinearLayout,totalDeathsLinearLayout,activeLinearLayout;

    boolean cases,recovered,critical,active,todayCases,totalDeaths,todayDeaths;
    boolean casesPerOneMillion,todayRecovered,deathsPerOneMillion,tests,testsPerOneMillion,population
            ,activePerOneMillion,recoveredPerOneMillion,criticalPerOneMillion;
    boolean showContinentsGraph;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreateView(inflater,container,savedInstanceState);
        return inflater.inflate(R.layout.continentsdetailsfrag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

     continentNameTextView = view.findViewById(R.id.continentName);

        textViewCases = view.findViewById(R.id.textViewCasesDetailsContinents);
        textViewTodayCases = view.findViewById(R.id.textViewTodayCasesDetailsContinents);
        textViewTotalDeaths = view.findViewById(R.id.textViewTotalDeathsDetailsContinents);
        textViewTodayDeaths = view.findViewById(R.id.textViewTodayDeathsDetailsContinents);
        textViewRecovered = view.findViewById(R.id.textViewRecoveredDetailsContinents);
        textViewTodayRecovered = view.findViewById(R.id.textViewTodayRecoveredContinents);
        textViewActive = view.findViewById(R.id.textViewActiveDetailsContinents);
        textViewCritical = view.findViewById(R.id.textViewCriticalDetailsContinents);
        textViewCasesPerOneMillion = view.findViewById(R.id.textViewCasesPerOneMillionContinents);
        textViewDeathsPerOneMillion = view.findViewById(R.id.textViewDeathsPerOneMillionContinents);
        textViewTests = view.findViewById(R.id.textViewTestsContinents);
        textViewTestsPerOneMillion = view.findViewById(R.id.textViewTestsPerOneMillionContinents);
        textViewPopulation = view.findViewById(R.id.textViewPopulationContinents);
        textViewActivePerOneMillion = view.findViewById(R.id.textViewActivePerOneMillionContinents);
        textViewRecoveredPerOneMillion = view.findViewById(R.id.textViewRecoveredPerOneMillionContinents);
        textViewCriticalPerOneMillion = view.findViewById(R.id.textViewCriticalPerOneMillionContinents);

        relativeLayoutCases = view.findViewById(R.id.relativeCasesContinents);
        relativeLayoutTodayCases = view.findViewById(R.id.relativeTodayCasesContinents);
        relativeLayoutTotalDeaths = view.findViewById(R.id.relativeTotalDeathsContinents);
        relativeLayoutTodayDeaths = view.findViewById(R.id.relativeTodayDeathsContinents);
        relativeLayoutRecovered = view.findViewById(R.id.relativeRecoveredContinents);
        relativeLayoutTodayRecovered = view.findViewById(R.id.relativeTodayRecoveredContinents);
        relativeLayoutActive = view.findViewById(R.id.relativeActiveContinents);
        relativeLayoutCritical = view.findViewById(R.id.relativeCriticalContinents);
        relativeLayoutCasesPerOneMillion = view.findViewById(R.id.relativeCasesPerOneMillionContinents);
        relativeLayoutDeathsPerOneMillion = view.findViewById(R.id.relativeDeathsPerOneMillionContinents);
        relativeLayoutTests = view.findViewById(R.id.relativeTestsContinents);
        relativeLayoutTestsPerOneMillion = view.findViewById(R.id.relativeTestsPerOneMillionContinents);
        relativeLayoutPopulation = view.findViewById(R.id.relativePopulationContinents);
        relativeLayoutActivePerOneMillion = view.findViewById(R.id.relativeActivePerOneMillionContinents);
        relativeLayoutRecoveredPerOneMillion = view.findViewById(R.id.relativeRecoveredPerOneMillionContinents);
        relativeLayoutCriticalPerOneMillion = view.findViewById(R.id.relativeCriticalPerOneMillionContinents);


        pieChartStatsContinents = view.findViewById(R.id.piechartContinents);
        barChartStatsContinents = view.findViewById(R.id.barchartContinents);

        cardViewPieStatsContinents = view.findViewById(R.id.pieCard);
        cardViewBarChartContinents = view.findViewById(R.id.cardViewGraphContinentsBar);


        totalCasesLinearLayout = view.findViewById(R.id.casesLinearLayoutContinents);
        recoveredLinearLayout = view.findViewById(R.id.recoveredLinearLayoutContinents);
        criticalLinearLayout = view.findViewById(R.id.criticalLinearLayoutContinents);
        totalDeathsLinearLayout = view.findViewById(R.id.deathsLinearLayoutContinents);
        activeLinearLayout = view.findViewById(R.id.activeLinearLayoutContinents);

        tabLayoutContinents = view.findViewById(R.id.tabLayoutContinents);

        ViewModelContinents = ContinentsViewModel.getInstance(getActivity().getApplication(),getContext());

        initSwitchPreferenceState();

        Observer<Continent> userListUpdateObserver = new Observer<Continent>() {

            @Override
            public void onChanged(Continent selectedContinent) {
                String continentName = selectedContinent.getContinent();
                String casesNumber = selectedContinent.getCases();
                String todayCasesNumber = selectedContinent.getTodayCases();
                String totalDeathsNumber = selectedContinent.getDeaths();
                String todayDeathsNumber = selectedContinent.getTodayDeaths();
                String recoveredNumber =selectedContinent.getRecovered();
                String activeNumber =selectedContinent.getActive();
                String criticalNumber = selectedContinent.getCritical();
                String casesPerOneMillionNumber = selectedContinent.getCasesPerOneMillion();
                String todayRecoveredNumber = selectedContinent.getTodayRecovered();
                String deathsPerOneMillionNumber = selectedContinent.getDeathsPerOneMillion();
                String testsNumber = selectedContinent.getTests();
                String testsPerOneMillionNumber = selectedContinent.getTestsPerOneMillion();
                String populationNumber = selectedContinent.getPopulation();
                String activePerOneMillionNumber = selectedContinent.getActivePerOneMillion();
                String recoveredPerOneMillionNumber = selectedContinent.getRecoveredPerOneMillion();
                String criticalPerOneMillionNumber = selectedContinent.getCriticalPerOneMillion();


                continentNameTextView.setText(continentName);

                if(cases){
                    relativeLayoutCases.setVisibility(View.VISIBLE);
                    textViewCases.setText(casesNumber);
                }
                else{
                    relativeLayoutCases.setVisibility(View.GONE);
                }

                if(todayCases){
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
                else{
                    relativeLayoutTotalDeaths.setVisibility(View.GONE);
                }

                if(todayDeaths){
                    relativeLayoutTodayDeaths.setVisibility(View.VISIBLE);
                    textViewTodayDeaths.setText(todayDeathsNumber);
                }
                else{
                    relativeLayoutTodayDeaths.setVisibility(View.GONE);
                }

                if(recovered){
                    relativeLayoutRecovered.setVisibility(View.VISIBLE);
                    textViewRecovered.setText(recoveredNumber);
                }
                else{
                    relativeLayoutRecovered.setVisibility(View.GONE);
                }

                if(todayRecovered){
                    relativeLayoutTodayRecovered.setVisibility(View.VISIBLE);
                    textViewTodayRecovered.setText(todayRecoveredNumber);
                }
                else{
                    relativeLayoutTodayRecovered.setVisibility(View.GONE);
                }

                if(active){
                    relativeLayoutActive.setVisibility(View.VISIBLE);
                    textViewActive.setText(activeNumber);
                }
                else{
                    relativeLayoutActive.setVisibility(View.GONE);
                }

                if(critical){
                    relativeLayoutCritical.setVisibility(View.VISIBLE);
                    textViewCritical.setText(criticalNumber);
                }
                else{
                    relativeLayoutCritical.setVisibility(View.GONE);
                }

                if(casesPerOneMillion){
                    relativeLayoutCasesPerOneMillion.setVisibility(View.VISIBLE);
                    textViewCasesPerOneMillion.setText(casesPerOneMillionNumber);
                }
                else{
                    relativeLayoutCasesPerOneMillion.setVisibility(View.GONE);
                }

                if(deathsPerOneMillion){
                    relativeLayoutDeathsPerOneMillion.setVisibility(View.VISIBLE);
                    textViewDeathsPerOneMillion.setText(deathsPerOneMillionNumber);
                }
                else{
                    relativeLayoutDeathsPerOneMillion.setVisibility(View.GONE);
                }

                if(tests){
                    relativeLayoutTests.setVisibility(View.VISIBLE);
                    textViewTests.setText(testsNumber);
                }
                else{
                    relativeLayoutTests.setVisibility(View.GONE);
                }

                if(testsPerOneMillion){
                    relativeLayoutTestsPerOneMillion.setVisibility(View.VISIBLE);
                    textViewTestsPerOneMillion.setText(testsPerOneMillionNumber);
                }
                else{
                    relativeLayoutTestsPerOneMillion.setVisibility(View.GONE);
                }

                if(population){
                    relativeLayoutPopulation.setVisibility(View.VISIBLE);
                    textViewPopulation.setText(populationNumber);
                }

                else{
                    relativeLayoutPopulation.setVisibility(View.GONE);
                }

                if(activePerOneMillion){
                    relativeLayoutActivePerOneMillion.setVisibility(View.VISIBLE);
                    textViewActivePerOneMillion.setText(activePerOneMillionNumber);
                }
                else{
                    relativeLayoutActivePerOneMillion.setVisibility(View.GONE);
                }

                if(recoveredPerOneMillion){
                    relativeLayoutRecoveredPerOneMillion.setVisibility(View.VISIBLE);
                    textViewRecoveredPerOneMillion.setText(recoveredPerOneMillionNumber);
                }
                else{
                    relativeLayoutRecoveredPerOneMillion.setVisibility(View.GONE);
                }

                if(criticalPerOneMillion){
                    relativeLayoutCriticalPerOneMillion.setVisibility(View.VISIBLE);
                    textViewCriticalPerOneMillion.setText(criticalPerOneMillionNumber);
                }
                else{
                    relativeLayoutCriticalPerOneMillion.setVisibility(View.GONE);
                }

                if(cases) {
                    totalCasesLinearLayout.setVisibility(View.VISIBLE);
                    pieChartStatsContinents.addPieSlice(new PieModel("Cases", Integer.parseInt(casesNumber), Color.parseColor("#FFA726")));
                    barChartStatsContinents.addBar(new BarModel("Cases", Integer.parseInt(casesNumber), Color.parseColor("#FFA726")));
                }
                else{
                    totalCasesLinearLayout.setVisibility(View.GONE);
                }

                if(recovered) {
                    pieChartStatsContinents.addPieSlice(new PieModel("Recovered", Integer.parseInt(recoveredNumber), Color.parseColor("#66BB6A")));
                    barChartStatsContinents.addBar(new BarModel("Recovered", Integer.parseInt(recoveredNumber), Color.parseColor("#66BB6A")));
                }
                else{
                    recoveredLinearLayout.setVisibility(View.GONE);
                }
                if(critical) {
                    pieChartStatsContinents.addPieSlice(new PieModel("Critical", Integer.parseInt(criticalNumber), Color.parseColor("#3a243b")));
                    barChartStatsContinents.addBar(new BarModel("Critical", Integer.parseInt(criticalNumber), Color.parseColor("#3a243b")));
                }
                else{
                    criticalLinearLayout.setVisibility(View.GONE);
                }
                if(totalDeaths) {
                    pieChartStatsContinents.addPieSlice(new PieModel("Deaths", Integer.parseInt(totalDeathsNumber), Color.parseColor("#EF5350")));
                    barChartStatsContinents.addBar(new BarModel("Deaths", Integer.parseInt(totalDeathsNumber), Color.parseColor("#EF5350")));
                }
                else{
                    totalDeathsLinearLayout.setVisibility(View.GONE);
                }
                if(active) {
                    pieChartStatsContinents.addPieSlice(new PieModel("Active", Integer.parseInt(activeNumber), Color.parseColor("#29B6F6")));
                    barChartStatsContinents.addBar(new BarModel("Active", Integer.parseInt(activeNumber), Color.parseColor("#29B6F6")));
                }
                else{
                    activeLinearLayout.setVisibility(View.GONE);
                }


                if(cases||recovered||critical||totalDeaths||active){
                    tabLayoutContinents.setVisibility(View.VISIBLE);
                }
                else{
                    showContinentsGraph=false;
                    tabLayoutContinents.setVisibility(View.GONE);
                }

                if(showContinentsGraph){
                    tabLayoutContinents.setVisibility(View.VISIBLE);
                }
                else{
                    tabLayoutContinents.setVisibility(View.GONE);
                }

                pieChartStatsContinents.startAnimation();
                barChartStatsContinents.startAnimation();

                cardViewPieStatsContinents.setVisibility(View.GONE);
                cardViewBarChartContinents.setVisibility(View.GONE);

                tabLayoutContinents.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        if(tab.getPosition() == 0) {
                            cardViewPieStatsContinents.setVisibility(View.VISIBLE);
                            cardViewBarChartContinents.setVisibility(View.GONE);

                            pieChartStatsContinents.startAnimation();
                        }

                        if(tab.getPosition() == 1) {
                            cardViewBarChartContinents.setVisibility(View.VISIBLE);
                            cardViewPieStatsContinents.setVisibility(View.GONE);

                            barChartStatsContinents.startAnimation();
                        }
                        if(tab.getPosition() == 2){
                            cardViewPieStatsContinents.setVisibility(View.VISIBLE);
                            cardViewBarChartContinents.setVisibility(View.VISIBLE);

                            barChartStatsContinents.startAnimation();
                            pieChartStatsContinents.startAnimation();
                        }

                        if(tab.getPosition() == 3){
                            cardViewPieStatsContinents.setVisibility(View.GONE);
                            cardViewBarChartContinents.setVisibility(View.GONE);
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

        ViewModelContinents.getItemSelected().observe(getViewLifecycleOwner(), userListUpdateObserver);

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
        activePerOneMillion = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("activePerOneMillion", false);
        recoveredPerOneMillion = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("recoveredPerOneMillion", false);
        criticalPerOneMillion = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("criticalPerOneMillion", false);
        showContinentsGraph = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("showContinentsGraphs", false);

    }


}
