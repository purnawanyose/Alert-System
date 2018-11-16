package com.semenindonesia.sisi.mtbf_mttr.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.semenindonesia.sisi.mtbf_mttr.R;
import com.semenindonesia.sisi.mtbf_mttr.constructor.consEvent;

import java.util.List;

/**
 * Created by yosep on 8/24/2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private List<consEvent> notiflist;
    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ws, timestamp, status;
        public ImageView img, imgstatus;
        public LinearLayout messageContainer;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            ws = (TextView) view.findViewById(R.id.from);
            timestamp = (TextView) view.findViewById(R.id.timestamp);
            status = (TextView) view.findViewById(R.id.txt_secondary);
            img = (ImageView) view.findViewById(R.id.circleimg);
            imgstatus = (ImageView) view.findViewById(R.id.imgstatus);
            messageContainer = (LinearLayout) view.findViewById(R.id.message_container);
        }
    }

    public NotificationAdapter(List<consEvent> notiflist){
        this.notiflist = notiflist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_list, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final consEvent notif = notiflist.get(position);
        holder.ws.setText(notif.getWorkstation());
        holder.timestamp.setText(notif.getTimestamp());
        holder.status.setText(notif.getStatus());
        String opco = notif.getOpco();
        String status = notif.getStatus();

        if (opco.equalsIgnoreCase("4000")){
            holder.img.setImageResource(R.drawable.tonasabesar);
        }else if (opco.equalsIgnoreCase("6000")){
            holder.img.setImageResource(R.drawable.tlbesar);
        }else if (opco.equalsIgnoreCase("7000")){
            holder.img.setImageResource(R.drawable.sgbesar);
        }else if (opco.equalsIgnoreCase("3000")){
            holder.img.setImageResource(R.drawable.padangbesar);
        }else {}

        applyReadStatus(holder, notif);
        if (status.equalsIgnoreCase("off")){
            holder.imgstatus.setColorFilter(Color.rgb(231, 76, 60));
            applyClickEvents(holder,position);
        }else if (status.equalsIgnoreCase("on")){
            holder.ws.setTypeface(null, Typeface.NORMAL);
            holder.imgstatus.setColorFilter(Color.rgb(46, 204, 113));
        }else {}

    }

    @Override
    public int getItemCount() {
        return notiflist.size();
    }

    private void applyClickEvents(MyViewHolder holder, final int position) {
        holder.messageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final consEvent notif = notiflist.get(position);
                //======== Menuju ke form reason ========
                Log.d("Notification: ", "workstation: "+ notif.getWorkstation());
                /*Intent intent = new Intent(context, FormActivity.class);
                intent.putExtra("idrekap",notif.getIdevent());
                intent.putExtra("workcenter", notif.getWorkstation());
                intent.putExtra("opco", notif.getOcop());
                intent.putExtra("timestamp", notif.getTimestamp());
                intent.putExtra("idmesin", notif.getIdmesin());
                intent.putExtra("id", notif.getId());

                context = view.getContext();
                db = new SQLiteHandler(context);
                db.updateRead(notif.getId());
                view.getContext().startActivity(intent);*/
            }
        });
    }

    private void applyReadStatus(MyViewHolder holder, consEvent notif) {
        if (notif.getIsRead() == 1) {
            holder.ws.setTypeface(null, Typeface.NORMAL);
        } else {
            holder.ws.setTypeface(null, Typeface.BOLD);
        }
    }


}
