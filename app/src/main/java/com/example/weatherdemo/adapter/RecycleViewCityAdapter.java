package com.example.weatherdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherdemo.R;
import com.example.weatherdemo.model.City;
import com.example.weatherdemo.model.Item;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewCityAdapter extends RecyclerView.Adapter<RecycleViewCityAdapter.CityViewHolder>{
    private List<City> list;
    private ItemListener itemListener;

    public RecycleViewCityAdapter(List<City> list) {
        this.list = list;
    }

    public RecycleViewCityAdapter() {
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setList(List<City> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public City getItem(int position){
        return list.get(position);
    }

    public List<City> getList() {
        return list;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        City item = list.get(position);
        holder.name.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name;

        public CityViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.countryName);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener!=null){
                itemListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
    public interface ItemListener{
        void onItemClick(View view, int position);
    }
}
