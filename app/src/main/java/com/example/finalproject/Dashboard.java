package com.example.finalproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity implements CountryAdapter.ICountriesAdapterListener,ContinentAdapter.IContinentsAdapterListener{

    TextView dashboardHeader;
   CardView exitCardView,returnToNormalMode;
   RelativeLayout relativeLayoutDashboard;
   LinearLayout linearLayoutScroll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        exitCardView = findViewById(R.id.exitCardView);
        returnToNormalMode = findViewById(R.id.returnToNormalModeCardView);
        relativeLayoutDashboard = findViewById(R.id.dashboard_main);
        linearLayoutScroll = findViewById(R.id.linearLayoutScroll);
        dashboardHeader = findViewById(R.id.dashboardHeader);

        onReturnToNormalViewClicked();
        onExitCardViewClicked();
        setNightMode();
        setActionBar();
    }

    public void onExitCardViewClicked() {
        exitCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInfo = new Intent();
                intentInfo.setAction(Intent.ACTION_VIEW);
                intentInfo.addCategory(Intent.CATEGORY_BROWSABLE);
                intentInfo.setData(Uri.parse("https://govextra.gov.il/ministry-of-health/corona/corona-virus/"));
                startActivity(intentInfo);
            }
        });
    }

    public void onReturnToNormalViewClicked() {
        returnToNormalMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, MainActivity.class);
                //Clear all activities and start new task
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    public void setNightMode() {
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if(nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            relativeLayoutDashboard.setBackgroundColor(Color.parseColor("#424242"));
            linearLayoutScroll.setBackgroundColor(Color.parseColor("#424242"));
            dashboardHeader.setTextColor(Color.parseColor("#BCBCBC"));
        }
        else {
            relativeLayoutDashboard.setBackgroundColor(Color.parseColor("#FFFFFF"));
            linearLayoutScroll.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

   public void setActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#212121"));
        actionBar.setBackgroundDrawable(colorDrawable);

        actionBar.setTitle("Covid-19 App - Dashboard");
        actionBar.hide();
    }

    @Override
    public void countryClicked() {
        CountriesDetailsFragment fragB;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragB = new CountriesDetailsFragment();
            getSupportFragmentManager().beginTransaction().
                    add(R.id.dashboard_main, fragB).//add on top of the static fragment
                    addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                    commit();
            getSupportFragmentManager().executePendingTransactions();
        }
        else //I am in portrait
        {
            fragB = new CountriesDetailsFragment();
            getSupportFragmentManager().beginTransaction().
                    add(R.id.dashboard_main, fragB).//add on top of the static fragment
                    addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                    commit();
            getSupportFragmentManager().executePendingTransactions();
        }
    }

    @Override
    public void continentClicked() {
        ContinentsDetailsFragment fragB;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragB = new ContinentsDetailsFragment();
            getSupportFragmentManager().beginTransaction().
                    add(R.id.dashboard_main, fragB).//add on top of the static fragment
                    addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                    commit();
            getSupportFragmentManager().executePendingTransactions();
        }
        else //I am in portrait
        {
            fragB = new ContinentsDetailsFragment();
            getSupportFragmentManager().beginTransaction().
                    add(R.id.dashboard_main, fragB).//add on top of the static fragment
                    addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                    commit();
            getSupportFragmentManager().executePendingTransactions();
        }
    }
}
