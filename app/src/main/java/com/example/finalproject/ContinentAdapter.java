package com.example.finalproject;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;
import java.io.IOException;
import java.util.ArrayList;

public class ContinentAdapter extends RecyclerView.Adapter<ContinentAdapter.MyViewHolder> {
    ArrayList<Continent> continentArrayList;
    private IContinentsAdapterListener listener;
    int row_index_continent = -1;
    ContinentsViewModel continentViewModel;
    Application application;
    Context context;

    public ContinentAdapter(Application application,Context context){
        this.application = application;
        this.context = context;
        continentViewModel = ContinentsViewModel.getInstance(application, context);
        continentArrayList = continentViewModel.getContinentLiveData().getValue();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        listener = (IContinentsAdapterListener) parent.getContext();
        View countryView = LayoutInflater.from(parent.getContext()).inflate(R.layout.continent_item, parent, false);
        return new MyViewHolder(countryView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Continent continent = continentArrayList.get(position);

        holder.row_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index_continent = position;
                notifyItemChanged(row_index_continent);
                notifyDataSetChanged();
                continentViewModel.setItemSelect(continent);
                listener.continentClicked();
            }
        });
        try {
            holder.bindData(continent.getContinent(),continent.getIcon());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return continentArrayList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{


        final Context context;
        final View continentItem;
        final TextView nameTextView;
        LinearLayout row_linearlayout;
        ImageView continentImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context             = itemView.getContext();
            continentItem         = itemView.findViewById(R.id.continentItem);
            nameTextView        =  itemView.findViewById(R.id.continentName);
            continentImageView = itemView.findViewById(R.id.flagImageViewContinent);
            row_linearlayout    =    (LinearLayout)itemView.findViewById(R.id.linearLayoutContinent);



            int nightMode = AppCompatDelegate.getDefaultNightMode();
            if(nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                continentImageView.setBackgroundColor(Color.parseColor("#424242"));
                row_linearlayout.setBackgroundColor(Color.parseColor("#424242"));
                continentItem.setBackgroundColor(Color.parseColor("#424242"));
            }
            else
            {
                continentImageView.setBackgroundColor(Color.parseColor("#F1F1F1"));
                row_linearlayout.setBackgroundColor(Color.parseColor("#F1F1F1"));
                continentItem.setBackgroundColor(Color.parseColor("#F1F1F1"));
            }

        }


        public void bindData(String name,String icon) throws IOException {
            nameTextView.setText(name);
            int resID =  context.getResources().getIdentifier(icon,"drawable",  context.getPackageName());
            continentImageView.setImageResource(resID);


        }


    }

    public interface IContinentsAdapterListener {
        void continentClicked();
    }
}
