package com.credoapp.parent.adapter;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.credoapp.parent.R;
import com.credoapp.parent.model.Notifications;
import com.credoapp.parent.ui.NotificationsScreen;
import com.credoapp.parent.databinding.ItemNotificationBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private NotificationsScreen notificationsScreen;
    private List<Notifications> notifications;

    public NotificationAdapter(NotificationsScreen notificationsScreen,
                               List<Notifications> notifications) {
        this.notificationsScreen = notificationsScreen;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_notification, parent, false);
        ItemNotificationBinding binding = (ItemNotificationBinding) viewDataBinding;
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notifications notifications = this.notifications.get(position);
        holder.binding.titleText.setText(notifications.title);
        holder.binding.descriptionText.setText(notifications.description);
//        holder.binding.dateTextNotification.setText(notifications.date);
        try {
            //2018-07-30 15:52:39
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(notifications.date);
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            holder.binding.dateText.setText("" + instance.get(Calendar.DATE));
            holder.binding.monthText.setText("" + new SimpleDateFormat("MMMM").format(date).toUpperCase());
            int mont = instance.get(Calendar.MONTH);
            holder.binding.dateTextNotification.setText(instance.get(Calendar.DATE)+"-"+(mont+1)+"-"+instance.get(Calendar.YEAR));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ItemNotificationBinding binding;

        public ViewHolder(ItemNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
