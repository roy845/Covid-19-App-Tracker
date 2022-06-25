package com.example.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class DashBoardFragment extends Fragment {

    public static final String PREFS_NAME = "MyPrefs";
    CheckBox doNotShowAgain;
    CardView globalStatsCardView,affectedCountriesCardView,israelCoronaDashBoardCardView,globalCoronaDashBoardCardView
            ,settingsCardView,quarantineCardView,affectedContinentsCardView,mapCardView;
    LinearLayout linearLayoutMain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout countries for this fragment
        return inflater.inflate(R.layout.dashboard_frag, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        globalStatsCardView = view.findViewById(R.id.globalStatsCardView);
        affectedCountriesCardView = view.findViewById(R.id.affectedCountriesCardView);
        israelCoronaDashBoardCardView = view.findViewById(R.id.israelCoronaDashBoardCardView);
        globalCoronaDashBoardCardView = view.findViewById(R.id.globalCoronaDashBoardCardView);
        settingsCardView = view.findViewById(R.id.settingsCardView);
        quarantineCardView = view.findViewById(R.id.quarantineCardView);
        linearLayoutMain = view.findViewById(R.id.dashboard_main_frag);
        affectedContinentsCardView = view.findViewById(R.id.continentsCardView);
        mapCardView = view.findViewById(R.id.cardViewMap);

        initialScreen();
        onGlobalStatsCardViewClicked();
        onAffectedCountriesCardViewClicked();
        onIsraeliCoronaDashboardCardViewClicked();
        onGlobalCoronaDashboardCardViewClicked();
        onSettingsCardViewClicked();
        onQuarantineCardViewClicked();
        onAffectedContinentsCardViewClicked();
        onMapCardViewClicked();
        setNightMode();
    }



    public void initialScreen()
    {
        AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
        LayoutInflater adbInflater = LayoutInflater.from(getContext());
        View eulaLayout = adbInflater.inflate(R.layout.checkbox, null);
        SharedPreferences settings = getContext().getSharedPreferences(PREFS_NAME, 0);
        String skipMessage = settings.getString("skipMessage", "NOT checked");

        doNotShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
        adb.setView(eulaLayout);
        adb.setTitle("Covid-19 App");
        adb.setMessage(R.string.initialScreenTxt);

        adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String checkBoxResult = "NOT checked";

                if (doNotShowAgain.isChecked()) {
                    checkBoxResult = "checked";
                }

                SharedPreferences settings = getContext().getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();

                editor.putString("skipMessage", checkBoxResult);
                editor.commit();

                return;
            }
        });

        if (!skipMessage.equals("checked")) {
            adb.show();
        }
    }

    public void onMapCardViewClicked(){
        mapCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.com/maps";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });
    }

    public void onAffectedContinentsCardViewClicked(){
        affectedContinentsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContinentFragment continentFragment;
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    continentFragment = new ContinentFragment();
                    getFragmentManager().beginTransaction().
                            add(R.id.dashboard_main, continentFragment).//add on top of the static fragment
                            addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                            commit();
                    getFragmentManager().executePendingTransactions();
                }
                else //I am in portrait
                {

                    continentFragment = new ContinentFragment();
                    getFragmentManager().beginTransaction().
                            add(R.id.dashboard_main, continentFragment).//add on top of the static fragment
                            addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                            commit();
                    getFragmentManager().executePendingTransactions();
                }
            }

        });
    }


    public void onSettingsCardViewClicked() {
        settingsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new MainActivity.PrefsFragment())
                        .addToBackStack("BBB")
                        .commit();
            }
        });
    }

    public void onQuarantineCardViewClicked() {
        quarantineCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountriesFragment.searchCountries.setText("");
                QuarantineFragment QuarantineFragB;
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    QuarantineFragB = new QuarantineFragment();
                    getFragmentManager().beginTransaction().
                            add(R.id.dashboard_main, QuarantineFragB).//add on top of the static fragment
                            addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                            commit();
                    getFragmentManager().executePendingTransactions();
                }
                else //I am in portrait
                {
                    CountriesFragment.searchCountries.setText("");
                    QuarantineFragB = new QuarantineFragment();
                    getFragmentManager().beginTransaction().
                            add(R.id.dashboard_main, QuarantineFragB).//add on top of the static fragment
                            addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                            commit();
                    getFragmentManager().executePendingTransactions();
                }
            }
        });
    }



    public void onIsraeliCoronaDashboardCardViewClicked()
    {
        israelCoronaDashBoardCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentIsrael = new Intent();
                intentIsrael.setAction(Intent.ACTION_VIEW);
                intentIsrael.addCategory(Intent.CATEGORY_BROWSABLE);
                intentIsrael.setData(Uri.parse("https://datadashboard.health.gov.il/COVID-19/general"));
                startActivity(intentIsrael);
            }
        });
    }

    public void onGlobalCoronaDashboardCardViewClicked()
    {
        globalCoronaDashBoardCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGlobal = new Intent();
                intentGlobal.setAction(Intent.ACTION_VIEW);
                intentGlobal.addCategory(Intent.CATEGORY_BROWSABLE);
                intentGlobal.setData(Uri.parse("https://covid19.who.int/?gclid=Cj0KCQiAgomBBhDXARIsAFNyUqPjh_F4femcUZrZA1NdH64DFMIRvDy5i6G-KKucd52prcbbDd_EYbQaAgnUEALw_wcB"));
                startActivity(intentGlobal);
            }
        });
    }


    public void onAffectedCountriesCardViewClicked()
    {
        affectedCountriesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountriesFragment countriesFragment;
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    countriesFragment = new CountriesFragment();
                    getFragmentManager().beginTransaction().
                            add(R.id.dashboard_main, countriesFragment).//add on top of the static fragment
                            addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                            commit();
                    getFragmentManager().executePendingTransactions();
                }
                else //I am in portrait
                {
                    countriesFragment = new CountriesFragment();
                    getFragmentManager().beginTransaction().
                            add(R.id.dashboard_main, countriesFragment).//add on top of the static fragment
                            addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                            commit();
                    getFragmentManager().executePendingTransactions();
                }
            }
        });
    }

    public void onGlobalStatsCardViewClicked()
    {
        globalStatsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalStats GlobalStatsFragB;
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    GlobalStatsFragB = new GlobalStats();
                    getFragmentManager().beginTransaction().
                            add(R.id.dashboard_main, GlobalStatsFragB).//add on top of the static fragment
                            addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                            commit();
                    getFragmentManager().executePendingTransactions();
                }
                else //I am in portrait
                {
                    GlobalStatsFragB = new GlobalStats();
                    getFragmentManager().beginTransaction().
                            add(R.id.dashboard_main, GlobalStatsFragB).//add on top of the static fragment
                            addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                            commit();
                    getFragmentManager().executePendingTransactions();
                }
            }
        });
    }

    public void setNightMode()
    {
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if(nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            linearLayoutMain.setBackgroundColor(Color.parseColor("#424242"));
        }
        else {
            linearLayoutMain.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }
}
