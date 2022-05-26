package com.example.weatherdemo.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherdemo.R;
import com.example.weatherdemo.dal.SQLiteHelper;
import com.example.weatherdemo.model.Item;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ItemViewHolder> {
    private List<Item> list;
    private ItemListener itemListener;
    private int[] imgs = {R.drawable.sun_rain, R.drawable.moon_wind, R.drawable.night_rain, R.drawable.tornado, R.drawable.sunny};

    public RecycleViewAdapter() {
        list = new ArrayList<>();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setList(List<Item> list) {
        Log.d("setlist", list.size() + "");
        this.list = list;
        notifyDataSetChanged();
    }

    public Item getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = list.get(position);
        Log.d("holder", item.getWeather());

        holder.country.setText(item.getCity());
        holder.temperature.setText(item.getTemperature() + "°");
        holder.weather.setText(item.getWeather());
        holder.high.setText("H: " + item.getHigh() + "°");
        holder.low.setText("L: " + item.getLow() + "°");
        holder.img.setImageResource(item.getImage());

        holder.btRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("remove: ", item.getId()+"");
                SQLiteHelper db = new SQLiteHelper(view.getContext());
                db.delete(item.getId());
                list.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView country, temperature, weather, high, low;
        private ImageView img, btRemove;

        public ItemViewHolder(@NonNull View view) {
            super(view);
            country = view.findViewById(R.id.country);
            temperature = view.findViewById(R.id.temperature);
            weather = view.findViewById(R.id.weather);
            high = view.findViewById(R.id.highTemp);
            low = view.findViewById(R.id.lowTemp);
            img = view.findViewById(R.id.img);
            btRemove = view.findViewById(R.id.btRemove);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemListener != null) {
                itemListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface ItemListener {
        void onItemClick(View view, int position);
    }
}
