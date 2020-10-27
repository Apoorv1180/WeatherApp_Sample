package com.example.weatherapp_sample.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp_sample.R;
import com.example.weatherapp_sample.data.model.WeatherList;

import java.util.ArrayList;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.AnimalsViewHolder> implements Filterable {

    private Context context;
    private List<WeatherList> nameList;
    private List<WeatherList> filteredNameList;

    WeatherAdapter(Context context, List<WeatherList> nameList) {
        super();
        this.context = context;
        this.nameList = nameList;
        this.filteredNameList = nameList;
    }

    @NonNull
    @Override
    public AnimalsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_recycler_view_item, viewGroup, false);
        return new AnimalsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalsViewHolder holder, int position) {
        holder.tvName.setText(filteredNameList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return filteredNameList.size();
    }

   
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSequenceString = constraint.toString();
                if (charSequenceString.isEmpty()) {
                    filteredNameList = nameList;
                } else {
                    List<WeatherList> filteredList = new ArrayList<>();
                    for (WeatherList name : nameList) {
                        if (name.getName().toLowerCase().contains(charSequenceString.toLowerCase())) {
                            filteredList.add(name);
                        }
                        filteredNameList = filteredList;
                    }

                }
               FilterResults results = new FilterResults();
               results.values = filteredNameList;
               return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredNameList = (List<WeatherList>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class AnimalsViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;

        AnimalsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.txt_city_name);
        }
    }

}