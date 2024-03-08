package com.congthiep.taskcrud.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.congthiep.taskcrud.R;
import com.congthiep.taskcrud.models.Job;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {
    private List<Job> jobList;
    private Context context;
    private static OnItemClickListener mListener;

    public JobAdapter(Context context, List<Job> jobList) {
        this.context = context;
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.job_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Job job = jobList.get(position);
        holder.imageViewGender.setImageResource(job.isGender() ? R.drawable.ic_woman : R.drawable.ic_man);
        holder.textViewJobTitle.setText(job.getJobTitle());
        holder.textViewJobContent.setText(job.getJobContent());
        holder.textViewCompletionDate.setText(job.getCompletionDate());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewGender;
        TextView textViewJobTitle;
        TextView textViewJobContent;
        TextView textViewCompletionDate;
        TextView buttonUpdate;
        TextView buttonDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewGender = itemView.findViewById(R.id.imageViewGender);
            textViewJobTitle = itemView.findViewById(R.id.textViewJobTitle);
            textViewJobContent = itemView.findViewById(R.id.textViewJobContent);
            textViewCompletionDate = itemView.findViewById(R.id.textViewCompletionDate);
            buttonUpdate = itemView.findViewById(R.id.textViewUpdate);
            buttonDelete = itemView.findViewById(R.id.textViewDelete);

            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
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
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
}
