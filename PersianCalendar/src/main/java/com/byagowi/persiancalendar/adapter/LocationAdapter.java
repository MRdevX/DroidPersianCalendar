package com.byagowi.persiancalendar.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.byagowi.persiancalendar.R;
import com.byagowi.persiancalendar.Utils;
import com.byagowi.persiancalendar.entity.City;
import com.byagowi.persiancalendar.view.dialog.LocationPreferenceDialog;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private String locale;
    private List<City> cities;
    private Utils utils;
    LocationPreferenceDialog locationPreferenceDialog;
    LayoutInflater layoutInflater;

    public LocationAdapter(LocationPreferenceDialog locationPreferenceDialog) {
        Context context = locationPreferenceDialog.getContext();
        utils = Utils.getInstance(locationPreferenceDialog.getContext());
        this.layoutInflater = LayoutInflater.from(context);
        this.locationPreferenceDialog = locationPreferenceDialog;
        cities = utils.getAllCities(true);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.locale = prefs.getString("AppLanguage", "fa");
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView country;
        private TextView city;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            city = (TextView) itemView.findViewById(R.id.text1);
            country = (TextView) itemView.findViewById(R.id.text2);
        }

        @Override
        public void onClick(View view) {
            locationPreferenceDialog.selectItem(cities.get(getAdapterPosition()).getKey());
        }
    }

    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.list_item_city_name, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        utils.prepareTextView(holder.city);
        holder.city.setText(locale.equals("en")
                ? cities.get(position).getEn()
                : utils.shape(cities.get(position).getFa()));

        utils.prepareTextView(holder.country);
        holder.country.setText(locale.equals("en")
                ? cities.get(position).getCountryEn()
                : utils.shape(cities.get(position).getCountryFa()));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
}