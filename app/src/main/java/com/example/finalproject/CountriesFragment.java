package com.example.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;


public class CountriesFragment extends Fragment implements TextWatcher {

    public static final String PREFS_NAME = "MyPrefs";
    static EditText searchCountries;
    static RecyclerView recyclerView;
    static CountryAdapter countryAdapter;
    CountriesViewModel mViewModel;
    CoordinatorLayout coordinatorLayout;
    CheckBox doNotShowAgain;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout countries for this fragment
        return inflater.inflate(R.layout.activity_affected_countries, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchCountries = (EditText) view.findViewById(R.id.editSearch);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        coordinatorLayout = view.findViewById(R.id.countries_main);
        mViewModel =  CountriesViewModel.getInstance(getActivity().getApplication(),getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        countryAdapter = new CountryAdapter(getActivity().getApplication(),getContext());
        recyclerView.setAdapter(countryAdapter);
        searchCountries.addTextChangedListener(this);

        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if(nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            recyclerView.setBackgroundColor(Color.parseColor("#424242"));
            view.setBackgroundColor(Color.parseColor("#424242"));
            searchCountries.setBackgroundColor(Color.parseColor("#424242"));
        }
        else {
            recyclerView.setBackgroundColor(Color.parseColor("#F1F1F1"));
            view.setBackgroundColor(Color.parseColor("#F1F1F1"));
            searchCountries.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.edittext_shadow));
        }

        enableSwipeToDeleteAndUndo();
        registerReceivers();

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

    @Override
    public void onResume() {
        initialScreen();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        searchCountries.setText("");
        mViewModel.saveRemovedCountriesToSP();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        searchCountries.setText("");
        mViewModel.saveRemovedCountriesToSP();

    }
    

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        countryAdapter.getFilter().filter(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    public void registerReceivers()
    {
        IntentFilter batteryChanged = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        getContext().registerReceiver(new BatteryBroadcastReceiver(), batteryChanged);

        IntentFilter batteryOK = new IntentFilter(Intent.ACTION_BATTERY_OKAY);
        getContext().registerReceiver(new BatteryBroadcastReceiver(), batteryOK);

        IntentFilter batteryLOW =new IntentFilter(Intent.ACTION_BATTERY_LOW);
        getContext().registerReceiver(new BatteryBroadcastReceiver(), batteryLOW);

        IntentFilter powerConnected = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
        getContext().registerReceiver(new BatteryBroadcastReceiver(), powerConnected);

        IntentFilter powerDisconnected = new IntentFilter(Intent.ACTION_POWER_DISCONNECTED);
        getContext().registerReceiver(new BatteryBroadcastReceiver(), powerDisconnected);
    }




    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int itemPosition = viewHolder.getAdapterPosition();
                Country country = countryAdapter.countryArrayList.get(itemPosition);
                if(direction == ItemTouchHelper.LEFT) {
                    Log.i("Swipe direction : ", "Left");

                    Snackbar snackbar = Snackbar.make(((CountryAdapter.MyViewHolder)viewHolder).itemView,"You removed "+countryAdapter.countryArrayList.get(itemPosition).getCountry(),Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            countryAdapter.restoreItem(country,itemPosition);
                        }
                    });

                    new AlertDialog.Builder(getContext())
                            .setMessage("Do you want to delete \"" + countryAdapter.countryArrayList.get(itemPosition).getCountry() + "\"?")
                            .setPositiveButton("Delete", (dialog, which) -> {
                                countryAdapter.removeItem(itemPosition);
                                System.out.println(itemPosition);
                                snackbar.show();
                            })
                            .setNegativeButton("Cancel", (dialog, which) -> countryAdapter.notifyItemChanged(itemPosition))
                            .setOnCancelListener(dialogInterface -> countryAdapter.notifyItemChanged(itemPosition))
                            .create().show();
                }


            }
        };
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

}
