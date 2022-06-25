package com.example.finalproject;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;


public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.MyViewHolder> implements Filterable {


    static int rowIndex = -1;
    private ICountriesAdapterListener listener;
    private CountriesViewModel viewModel;
    Application application;
    Context context;
    String countryRemoved;
    static Set<String> mRemovedCountries = null;
    static  ArrayList<Country> countryArrayList;
    static ArrayList<Country> countryArrayListFull;
    Set<String> removedItemsSP;

    public CountryAdapter(Application application, Context context) {
        this.application = application;
        this.context = context;
        viewModel = CountriesViewModel.getInstance(application, context);
        countryArrayList = viewModel.getCountriesLiveData().getValue();
        countryArrayListFull = new ArrayList<>(countryArrayList);
        if(mRemovedCountries == null) {
            mRemovedCountries = new LinkedHashSet<>();
        }

        boolean countriesRemovedFilter =  PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean("rememberRemovedCountries", false);

        if(countriesRemovedFilter) {
            removedItemsSP = viewModel.getRemovedCountriesFromSP();
            System.out.println("1.CountriesRemoved OK"+removedItemsSP);
            for(String item:removedItemsSP){
            for(int i =0 ; i<countryArrayListFull.size();i++) {

                System.out.println(countryArrayListFull.get(i).getCountry());

                    if(item.equals(countryArrayListFull.get(i).getCountry())) {
                        System.out.println(countryArrayListFull.get(i).getCountry());
                        countryArrayListFull.remove(i);

                    }

                }

            }
            countryArrayList.clear();
            countryArrayList.addAll(countryArrayListFull);
            notifyDataSetChanged();

        }

        else{
            SharedPreferences sharedPref = application.getApplicationContext().getSharedPreferences("removedCountries",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.apply();

        }


    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        listener = (ICountriesAdapterListener) parent.getContext();
        View countryView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(countryView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Country country = countryArrayList.get(position);

        //Regular Click
        holder.row_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rowIndex = position;
                notifyItemChanged(rowIndex);
                notifyDataSetChanged();
                viewModel.setItemSelect(country);
                listener.countryClicked();
            }
        });


        try {
            holder.bindData(country.getCountry(), country.getFlag());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {

        return countryArrayList.size();
    }

    public void removeItem(int position) {

        countryRemoved = countryArrayList.remove(position).getCountry();
        System.out.println("item removed:"+countryArrayListFull.get(position).getCountry());

        for(int i = 0 ; i<countryArrayListFull.size();i++)
        {
            if(countryArrayListFull.get(i).getCountry().equals(countryRemoved)){
                countryArrayListFull.remove(i);
            }
        }

        mRemovedCountries.add(countryRemoved);
        notifyItemChanged(position);
        notifyDataSetChanged();
        System.out.println(countryRemoved);
        System.out.println(mRemovedCountries);

        notifyItemRangeChanged(position, countryArrayList.size());
        viewModel.setCountryLiveData(countryArrayList);

        if (position < rowIndex) {
            rowIndex--;
            notifyItemChanged(rowIndex);
            notifyDataSetChanged();
        } else if (position == rowIndex) {
            rowIndex = -1;
            notifyItemChanged(rowIndex);
            notifyDataSetChanged();
        }
    }


    public void restoreItem(Country country,int pos)
    {
        countryArrayList.add(pos, country);
        countryArrayListFull.add(pos, country);
        countryArrayList.sort(new Comparator<Country>() {
            @Override
            public int compare(Country o1, Country o2) {
              return o1.getCountry().compareTo(o2.getCountry());

            }
        });

        countryArrayListFull.sort(new Comparator<Country>() {
            @Override
            public int compare(Country o1, Country o2) {
                return o1.getCountry().compareTo(o2.getCountry());

            }
        });

        notifyDataSetChanged();

    }


    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Country> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0) {
                filteredList.addAll(countryArrayListFull);
            }
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Country country:countryArrayListFull) {
                    if(country.getCountry().toLowerCase().contains(filterPattern))
                        filteredList.add(country);

                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            countryArrayList.clear();
            countryArrayList.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    public  class MyViewHolder extends RecyclerView.ViewHolder{


         final Context   context;
         final View      countryItem;
         final ImageView flagImageView;
         final TextView nameTextView;
         LinearLayout row_linearlayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context             = itemView.getContext();
            countryItem         = itemView.findViewById(R.id.countryItem);
            flagImageView       =  itemView.findViewById(R.id.imageFlag);
            nameTextView        =  itemView.findViewById(R.id.countryName);
            row_linearlayout    =    (LinearLayout)itemView.findViewById(R.id.linearLayoutContinent);

            int nightMode = AppCompatDelegate.getDefaultNightMode();
            if(nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                flagImageView.setBackgroundColor(Color.parseColor("#424242"));

            }
            else
            {
                flagImageView.setBackgroundColor(Color.parseColor("#F1F1F1"));
                row_linearlayout.setBackgroundColor(Color.parseColor("#F1F1F1"));
            }

        }

        public void bindData(String name, String flagUrl) throws IOException {
            nameTextView.setText(name);
            Picasso.get().load(flagUrl).into(flagImageView);

        }

    }

    public interface ICountriesAdapterListener {
        void countryClicked();
    }
}
