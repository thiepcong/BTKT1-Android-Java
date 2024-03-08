package com.congthiep.tour_crud.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.congthiep.tour_crud.R;
import com.congthiep.tour_crud.models.Vehicle;

import java.util.ArrayList;

public class VehicleAdapter extends ArrayAdapter<Vehicle> {

    public VehicleAdapter(Context context, ArrayList<Vehicle> vehicles) {
        super(context, 0, vehicles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    private View createView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_vehicle_spinner, parent, false);
        }

        Vehicle currentVehicle = getItem(position);

        ImageView imageView = convertView.findViewById(R.id.imageViewVehicle);
        imageView.setImageResource(currentVehicle.getImageResourceId());

        TextView textView = convertView.findViewById(R.id.textViewVehicleName);
        textView.setText(currentVehicle.getName());

        return convertView;
    }
}

