package com.trainh.assignmentprm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trainh.assignmentprm.R;
import com.trainh.assignmentprm.model.NotificationDTO;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<NotificationDTO> notificationList;

    public NotificationAdapter(List<NotificationDTO> notificationList) {
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.custom_notify_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        NotificationDTO notify = notificationList.get(position);

        holder.Title.setText(notify.getTitle());
        holder.Body.setText(notify.getDescription());

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView Title, Body;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.txt_title);
            Body = (TextView) itemView.findViewById(R.id.txt_body);
        }
    }
}
