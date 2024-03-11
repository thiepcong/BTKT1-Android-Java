package com.congthiep.tour_crud.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.congthiep.tour_crud.R;
import com.congthiep.tour_crud.models.Tour;

import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.ViewHolder> {

    private List<Tour> tourList;
    private Context context;
    private static OnItemClickListener mListener;

    public TourAdapter(Context context, List<Tour> tourList) {
        this.context = context;
        this.tourList = tourList;
    }

    public TourAdapter(Context context) {
        this.context = context;
    }

    public void setTourList(List<Tour> tourList) {
        this.tourList = tourList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tour, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tour tour = tourList.get(position);

        holder.imageViewVehicle.setImageResource(tour.getVehicle().getImageResourceId());
        holder.textViewItinerary.setText(tour.getItinerary());
        holder.textViewDuration.setText(tour.getDuration());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewVehicle;
        TextView textViewItinerary;
        TextView textViewDuration;
        TextView buttonUpdate;
        TextView buttonDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewVehicle = itemView.findViewById(R.id.imageViewVehicle);
            textViewItinerary = itemView.findViewById(R.id.textViewItinerary);
            textViewDuration = itemView.findViewById(R.id.textViewDuration);
            buttonUpdate = itemView.findViewById(R.id.textViewUpdate);
            buttonDelete = itemView.findViewById(R.id.textViewDelete);

            // Xử lý sự kiện click cho nút "Update"
            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAbsoluteAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onUpdateClick(position);
                        }
                    }
                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAbsoluteAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
}

