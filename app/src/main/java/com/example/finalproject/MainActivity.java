package com.example.finalproject;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.navigation.NavigationView;

import java.util.List;


public class MainActivity extends AppCompatActivity implements CountryAdapter.ICountriesAdapterListener, ContinentAdapter.IContinentsAdapterListener {


    static DrawerLayout drawerLayout;
    ActionBarDrawerToggle t;
    static NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.mainActivity);
        nv = (NavigationView)findViewById(R.id.nv);
        t = new ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout.addDrawerListener(t);
        initNavigationView();
        registerReceivers();
        t.syncState();
        setActionBar();
        setNightMode();
    }


    public void setNightMode()
    {
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if(nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            drawerLayout.setBackgroundColor(Color.parseColor("#424242"));
        }
        else {
            drawerLayout.setBackgroundColor(Color.parseColor("#F1F1F1"));
        }
    }

    public void initNavigationView()
    {
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.global_nav:
                        GlobalStats GlobalStatsFragB;
                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            GlobalStatsFragB = new GlobalStats();
                            getSupportFragmentManager().beginTransaction().
                                    add(R.id.mainActivity, GlobalStatsFragB).//add on top of the static fragment
                                    addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                                    commit();
                            getSupportFragmentManager().executePendingTransactions();
                        }
                        else //I am in portrait
                        {
                            GlobalStatsFragB = new GlobalStats();
                            getSupportFragmentManager().beginTransaction().
                                    add(R.id.mainActivity, GlobalStatsFragB).//add on top of the static fragment
                                    addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                                    commit();
                            getSupportFragmentManager().executePendingTransactions();
                        }
                        drawerLayout.closeDrawer(GravityCompat.START,true);

                        break;

                    case R.id.quarantine_nav:
                        CountriesFragment.searchCountries.setText("");
                        QuarantineFragment QuarantineFragB;
                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            QuarantineFragB = new QuarantineFragment();
                            getSupportFragmentManager().beginTransaction().
                                    add(R.id.mainActivity, QuarantineFragB).//add on top of the static fragment
                                    addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                                    commit();
                            getSupportFragmentManager().executePendingTransactions();
                        }
                        else //I am in portrait
                        {
                            CountriesFragment.searchCountries.setText("");
                            QuarantineFragB = new QuarantineFragment();
                            getSupportFragmentManager().beginTransaction().
                                    add(R.id.mainActivity, QuarantineFragB).//add on top of the static fragment
                                    addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                                    commit();
                            getSupportFragmentManager().executePendingTransactions();
                        }
                        drawerLayout.closeDrawer(GravityCompat.START,true);
                        break;

                    case R.id.setting_nav:
                        getSupportFragmentManager().beginTransaction()
                                .replace(android.R.id.content, new PrefsFragment())
                                .addToBackStack("BBB")
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START,true);
                        break;

                    case R.id.info_nav:
                        Intent intentInfo = new Intent();
                        intentInfo.setAction(Intent.ACTION_VIEW);
                        intentInfo.addCategory(Intent.CATEGORY_BROWSABLE);
                        intentInfo.setData(Uri.parse("https://govextra.gov.il/ministry-of-health/corona/corona-virus/"));
                        startActivity(intentInfo);
                        drawerLayout.closeDrawer(GravityCompat.START,true);


                        break;

                    case R.id.dashboard_nav:
                        Intent intentIsrael = new Intent();
                        intentIsrael.setAction(Intent.ACTION_VIEW);
                        intentIsrael.addCategory(Intent.CATEGORY_BROWSABLE);
                        intentIsrael.setData(Uri.parse("https://datadashboard.health.gov.il/COVID-19/general"));
                        startActivity(intentIsrael);
                        drawerLayout.closeDrawer(GravityCompat.START,true);
                        break;

                    case R.id.dashboard_global_nav:
                        Intent intentGlobal = new Intent();
                        intentGlobal.setAction(Intent.ACTION_VIEW);
                        intentGlobal.addCategory(Intent.CATEGORY_BROWSABLE);
                        intentGlobal.setData(Uri.parse("https://covid19.who.int/?gclid=Cj0KCQiAgomBBhDXARIsAFNyUqPjh_F4femcUZrZA1NdH64DFMIRvDy5i6G-KKucd52prcbbDd_EYbQaAgnUEALw_wcB"));
                        startActivity(intentGlobal);
                        drawerLayout.closeDrawer(GravityCompat.START,true);
                        break;

                    case R.id.dashboardMode_nav:
                        Intent intent = new Intent(MainActivity.this, Dashboard.class);
                        //Clear all activities and start new task
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    case R.id.continents_nav:
                        CountriesFragment.searchCountries.setText("");
                        ContinentFragment continentFragment;
                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            continentFragment = new ContinentFragment();
                            getSupportFragmentManager().beginTransaction().
                                    add(R.id.mainActivity, continentFragment).//add on top of the static fragment
                                    addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                                    commit();
                            getSupportFragmentManager().executePendingTransactions();
                        }
                        else //I am in portrait
                        {
                            CountriesFragment.searchCountries.setText("");
                            continentFragment = new ContinentFragment();
                            getSupportFragmentManager().beginTransaction().
                                    add(R.id.mainActivity, continentFragment).//add on top of the static fragment
                                    addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                                    commit();
                            getSupportFragmentManager().executePendingTransactions();
                        }
                        drawerLayout.closeDrawer(GravityCompat.START,true);
                        break;

                        case R.id.map_nav:
                            String uri = "http://maps.google.com/maps";
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                            startActivity(mapIntent);
                            drawerLayout.closeDrawer(GravityCompat.START,true);
                            break;
                    default:
                        return true;
                }
                return true;
            }
        });
    }




    public void registerReceivers()
    {
        IntentFilter filterBatteryChanged = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        getApplicationContext().registerReceiver(new BatteryBroadcastReceiver(), filterBatteryChanged);

        IntentFilter filterBatteryOK = new IntentFilter(Intent.ACTION_BATTERY_OKAY);
        getApplicationContext().registerReceiver(new BatteryBroadcastReceiver(), filterBatteryOK);

        IntentFilter filterBatteryLOW = new IntentFilter(Intent.ACTION_BATTERY_LOW);
        getApplicationContext().registerReceiver(new BatteryBroadcastReceiver(), filterBatteryLOW);

        IntentFilter filterPowerConnected = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
        getApplicationContext().registerReceiver(new BatteryBroadcastReceiver(), filterPowerConnected);

        IntentFilter filterPowerDisconnected = new IntentFilter(Intent.ACTION_POWER_DISCONNECTED);
        getApplicationContext().registerReceiver(new BatteryBroadcastReceiver(), filterPowerDisconnected);
    }

    @Override
    public void continentClicked() {

        ContinentsDetailsFragment fragB;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragB = new ContinentsDetailsFragment();
            getSupportFragmentManager().beginTransaction().
                    add(R.id.mainActivity, fragB).//add on top of the static fragment
                    addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                    commit();
            getSupportFragmentManager().executePendingTransactions();
        }
        else //I am in portrait
        {
            fragB = new ContinentsDetailsFragment();
            getSupportFragmentManager().beginTransaction().
                    add(R.id.mainActivity, fragB).//add on top of the static fragment
                    addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                    commit();
            getSupportFragmentManager().executePendingTransactions();
        }
    }


    public static class PrefsFragment extends PreferenceFragmentCompat {
        CharSequence casesOn = "Cases ON";
        CharSequence casesOff = "Cases OFF";

        CharSequence todayCasesOn = "Today Cases ON";
        CharSequence todayCasesOff = "Today Cases OFF";

        CharSequence totalDeathsOn = "Total Deaths ON";
        CharSequence totalDeathsOff = "Total Deaths Off";

        CharSequence todayDeathsOn = "Today Deaths ON";
        CharSequence todayDeathsOff = "Today Deaths OFF";

        CharSequence recoveredOn = "Recovered ON";
        CharSequence recoveredOff = "Recovered OFF";

        CharSequence activeOn = "Active ON";
        CharSequence activeOff = "Active OFF";

        CharSequence criticalOn = "Critical ON";
        CharSequence criticalOff = "Critical OFF";

        CharSequence casesPerOneMillionOn = "Cases Per One Million ON";
        CharSequence casesPerOneMillionOff = "Cases Per One Million OFF";

        CharSequence deathsPerOneMillionON = "Deaths Per One Million ON";
        CharSequence deathsPerOneMillionOFF = "Deaths Per One Million OFF";

        CharSequence todayRecoveredON = "Today Recovered ON";
        CharSequence todayRecoveredOFF = "TodayRecovered OFF";

        CharSequence testsON = "Tests ON";
        CharSequence testsOFF = "Tests OFF";

        CharSequence testsPerOneMillionON = "Tests Per One Million ON";
        CharSequence testsPerOneMillionOFF = "Tests Per One Million OFF";

        CharSequence populationON = "Population ON";
        CharSequence populationOFF = "Population OFF";

        CharSequence continentON = "Continent ON";
        CharSequence continentOFF = "Continent OFF";

        CharSequence oneCasePerPeopleON = "One Case Per People ON";
        CharSequence oneCasePerPeopleOFF = "One Case Per People OFF";

        CharSequence oneDeathPerPeopleON = "One Death Per People ON";
        CharSequence oneDeathPerPeopleOFF = "One Death Per People OFF";

        CharSequence oneTestPerPeopleON = "One Test Per People ON";
        CharSequence oneTestPerPeopleOFF = "One Test Per People OFF";

        CharSequence activePerOneMillionON = "Active Per One Million ON";
        CharSequence activePerOneMillionOFF = "Active Per One Million OFF";

        CharSequence recoveredPerOneMillionON = "Recovered Per OneMillion ON";
        CharSequence recoveredPerOneMillionOFF = "Recovered Per OneMillion OFF";

        CharSequence criticalPerOneMillionON = "Critical Per OneMillion ON";
        CharSequence criticalPerOneMillionOFF = "Critical Per OneMillion OFF";

        CharSequence affectedCountriesON = "Affected Countries ON";
        CharSequence affectedCountriesONOFF = "Affected Countries OFF";

        CharSequence showCountriesGraphsON = "Show Countries Graphs ON";
        CharSequence showCountriesGraphsOFF = "Show Countries Graphs OFF";

        CharSequence showContinentsGraphsON = "Show Continents Graphs ON";
        CharSequence showContinentsGraphsOFF = "Show Continents Graphs OFF";

        CharSequence showGlobalGraphsON = "Show Global Graphs ON";
        CharSequence showGlobalGraphsOFF = "Show Global Graphs OFF";

        CharSequence forgetCountries = "Forget Removed Countries";
        CharSequence rememberCountries = "Remember Removed Countries";

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = super.onCreateView(inflater, container, savedInstanceState);
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            if(nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                view.setBackgroundColor(Color.parseColor("#424242"));
            }
            else
            {
                view.setBackgroundColor(Color.WHITE);
            }

            SwitchPreference switchCases =
                    getPreferenceManager().findPreference("cases");
            switchCases.setSummaryOff(casesOff);
            switchCases.setSummaryOn(casesOn);

            SwitchPreference switchTodayCases =
                    getPreferenceManager().findPreference("todayCases");
            switchTodayCases.setSummaryOff(todayCasesOff);
            switchTodayCases.setSummaryOn(todayCasesOn);

            SwitchPreference switchTotalDeaths =
                    getPreferenceManager().findPreference("totalDeaths");
            switchTotalDeaths.setSummaryOff(totalDeathsOff);
            switchTotalDeaths.setSummaryOn(totalDeathsOn);

            SwitchPreference switchTodayDeaths =
                    getPreferenceManager().findPreference("todayDeaths");
            switchTodayDeaths.setSummaryOff(todayDeathsOff);
            switchTodayDeaths.setSummaryOn(todayDeathsOn);

            SwitchPreference switchRecovered =
                    getPreferenceManager().findPreference("recovered");
            switchRecovered.setSummaryOff(recoveredOff);
            switchRecovered.setSummaryOn(recoveredOn);

            SwitchPreference switchActive =
                    getPreferenceManager().findPreference("active");
            switchActive.setSummaryOff(activeOff);
            switchActive.setSummaryOn(activeOn);

            SwitchPreference switchCritical =
                    getPreferenceManager().findPreference("critical");
            switchCritical.setSummaryOff(criticalOff);
            switchCritical.setSummaryOn(criticalOn);

            SwitchPreference switchCasesPerOneMillion =
                    getPreferenceManager().findPreference("casesPerOneMillion");
            switchCasesPerOneMillion.setSummaryOff(casesPerOneMillionOff);
            switchCasesPerOneMillion.setSummaryOn(casesPerOneMillionOn);

            SwitchPreference switchDeathsPerOneMillion =
                    getPreferenceManager().findPreference("deathsPerOneMillion");
            switchDeathsPerOneMillion.setSummaryOff(deathsPerOneMillionOFF);
            switchDeathsPerOneMillion.setSummaryOn(deathsPerOneMillionON);

            SwitchPreference switchTodayRecovered =
                    getPreferenceManager().findPreference("todayRecovered");
            switchTodayRecovered.setSummaryOff(todayRecoveredOFF);
            switchTodayRecovered.setSummaryOn(todayRecoveredON);

            SwitchPreference switchTests =
                    getPreferenceManager().findPreference("tests");
            switchTests.setSummaryOff(testsOFF);
            switchTests.setSummaryOn(testsON);

            SwitchPreference switchTestsPerOneMillion =
                    getPreferenceManager().findPreference("testsPerOneMillion");
            switchTestsPerOneMillion.setSummaryOff(testsPerOneMillionOFF);
            switchTestsPerOneMillion.setSummaryOn(testsPerOneMillionON);

            SwitchPreference switchPopulation =
                    getPreferenceManager().findPreference("population");
            switchPopulation.setSummaryOff(populationOFF);
            switchPopulation.setSummaryOn(populationON);

            SwitchPreference switchCasesContinent =
                    getPreferenceManager().findPreference("continent");
            switchCasesContinent.setSummaryOff(continentOFF);
            switchCasesContinent.setSummaryOn(continentON);

            SwitchPreference switchCasesOneCasePerPeople =
                    getPreferenceManager().findPreference("oneCasePerPeople");
            switchCasesOneCasePerPeople.setSummaryOff(oneCasePerPeopleOFF);
            switchCasesOneCasePerPeople.setSummaryOn(oneCasePerPeopleON);

            SwitchPreference switchOneDeathPerPeople =
                    getPreferenceManager().findPreference("oneDeathPerPeople");
            switchOneDeathPerPeople.setSummaryOff(oneDeathPerPeopleOFF);
            switchOneDeathPerPeople.setSummaryOn(oneDeathPerPeopleON);

            SwitchPreference switchOneTestPerPeople =
                    getPreferenceManager().findPreference("oneTestPerPeople");
            switchOneTestPerPeople.setSummaryOff(oneTestPerPeopleOFF);
            switchOneTestPerPeople.setSummaryOn(oneTestPerPeopleON);

            SwitchPreference switchCasesActivePerOneMillion =
                    getPreferenceManager().findPreference("activePerOneMillion");
            switchCasesActivePerOneMillion.setSummaryOff(activePerOneMillionOFF);
            switchCasesActivePerOneMillion.setSummaryOn(activePerOneMillionON);

            SwitchPreference switchCasesRecoveredPerOneMillion =
                    getPreferenceManager().findPreference("recoveredPerOneMillion");
            switchCasesRecoveredPerOneMillion.setSummaryOff(recoveredPerOneMillionOFF);
            switchCasesRecoveredPerOneMillion.setSummaryOn(recoveredPerOneMillionON);

            SwitchPreference switchCriticalPerOneMillion =
                    getPreferenceManager().findPreference("criticalPerOneMillion");
            switchCriticalPerOneMillion.setSummaryOff(criticalPerOneMillionOFF);
            switchCriticalPerOneMillion.setSummaryOn(criticalPerOneMillionON);

            SwitchPreference switchAffectedCountries =
                    getPreferenceManager().findPreference("affectedCountries");
            switchAffectedCountries.setSummaryOff(affectedCountriesONOFF);
            switchAffectedCountries.setSummaryOn(affectedCountriesON);

            SwitchPreference showCountriesGraphsSwitch =
                    getPreferenceManager().findPreference("showCountriesGraphs");
            showCountriesGraphsSwitch.setSummaryOff(showCountriesGraphsOFF);
            showCountriesGraphsSwitch.setSummaryOn(showCountriesGraphsON);

            SwitchPreference showContinentsGraphsSwitch =
                    getPreferenceManager().findPreference("showContinentsGraphs");
            showContinentsGraphsSwitch.setSummaryOff(showContinentsGraphsOFF);
            showContinentsGraphsSwitch.setSummaryOn(showContinentsGraphsON);

            SwitchPreference showGlobalGraphsSwitch =
                    getPreferenceManager().findPreference("showGlobalGraphs");
            showGlobalGraphsSwitch.setSummaryOff(showGlobalGraphsOFF);
            showGlobalGraphsSwitch.setSummaryOn(showGlobalGraphsON);

            SwitchPreference switchCountryRemove =
                    getPreferenceManager().findPreference("rememberRemovedCountries");
            switchCountryRemove.setSummaryOff(forgetCountries);
            switchCountryRemove.setSummaryOn(rememberCountries);

            return view;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);

        }


    public void setActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#212121"));
        actionBar.setBackgroundDrawable(colorDrawable);

        actionBar.setTitle("Covid-19 App");
    }


    @Override
    public void countryClicked() {

        CountriesDetailsFragment fragB;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragB = new CountriesDetailsFragment();
            getSupportFragmentManager().beginTransaction().
                    add(R.id.mainActivity, fragB).//add on top of the static fragment
                    addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                    commit();
            getSupportFragmentManager().executePendingTransactions();
        }
        else //I am in portrait
        {
            fragB = new CountriesDetailsFragment();
            getSupportFragmentManager().beginTransaction().
                    add(R.id.mainActivity, fragB).//add on top of the static fragment
                    addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                    commit();
            getSupportFragmentManager().executePendingTransactions();
        }
    }

}