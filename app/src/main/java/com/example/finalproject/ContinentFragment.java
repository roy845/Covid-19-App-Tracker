package com.example.finalproject;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ContinentFragment extends Fragment {

    static RecyclerView recyclerViewContinents;
    static ContinentAdapter continentAdapter;
    ContinentsViewModel mViewModelContinents;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout countries for this fragment
        return inflater.inflate(R.layout.continents_frag, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewContinents = (RecyclerView) view.findViewById(R.id.continentsRecyclerView);
        mViewModelContinents =  ContinentsViewModel.getInstance(getActivity().getApplication(),getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewContinents.setLayoutManager(layoutManager);
        continentAdapter = new ContinentAdapter(getActivity().getApplication(),getContext());
        recyclerViewContinents.setAdapter(continentAdapter);

        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if(nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            recyclerViewContinents.setBackgroundColor(Color.parseColor("#424242"));
            view.setBackgroundColor(Color.parseColor("#424242"));

        }
        else {
            recyclerViewContinents.setBackgroundColor(Color.parseColor("#F1F1F1"));
            view.setBackgroundColor(Color.parseColor("#F1F1F1"));

        }
    }
}
