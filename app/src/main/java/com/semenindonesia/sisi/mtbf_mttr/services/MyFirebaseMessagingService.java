package com.semenindonesia.sisi.mtbf_mttr.services;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.semenindonesia.sisi.mtbf_mttr.activities.LoginActivity;
import com.semenindonesia.sisi.mtbf_mttr.config.Config;
import com.semenindonesia.sisi.mtbf_mttr.constructor.consEvent;
import com.semenindonesia.sisi.mtbf_mttr.database.Event;
import com.semenindonesia.sisi.mtbf_mttr.database.RealmController;
import com.semenindonesia.sisi.mtbf_mttr.database.User;
import com.semenindonesia.sisi.mtbf_mttr.utils.NotificationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by yosep on 8/10/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    //private SQLiteHandler db;

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    Realm realm = Realm.getDefaultInstance();
    int results, tampil;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }else{
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());
        Realm.init(MyFirebaseMessagingService.this);
        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String mesin = data.getString("mesin");
            String idmesin = data.getString("idmesin");
            String opco = data.getString("opco");
            final String id = data.getString("idrekap");
            String status = data.getString("status");
            String timestamp = data.getString("timestamp");
            boolean isRead = data.getBoolean("isRead");
            JSONObject payload = data.getJSONObject("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "mesin: " + mesin);
            Log.e(TAG, "idmesin: " + idmesin);
            Log.e(TAG, "opco: " + opco);
            Log.e(TAG, "status: " + status);
            Log.e(TAG, "isRead: " + isRead);
            Log.e(TAG, "timestamp: " + timestamp);

            // SQLite database handler
//            db = new SQLiteHandler(getApplicationContext());

            if (mesin.equalsIgnoreCase("null") || status.equalsIgnoreCase("null")){
                Log.e(TAG, "test: ");
            }else {
//               db.addNotification(Integer.parseInt(id), mesin, opco, status, timestamp, idmesin);

                Event event = new Event();
                RealmController.with(getApplication()).refresh();

                Realm backgroundRealm = Realm.getDefaultInstance();
                backgroundRealm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        results = realm.where(Event.class).findAll().size();
                    }
                });

                Log.e("error: " ,"size event: "+ results);
                event.setId(results  + 1);
                event.setIdevent(id);
                event.setIdmesin(idmesin);
                event.setOpco(opco);
                event.setStatus(status);
                event.setDatecreated(timestamp);
                event.setIsRead(0);
                event.setDateupdateat("");
                event.setDateupdated("");

                if (status.equalsIgnoreCase("on")){
                    backgroundRealm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            RealmResults<Event> results2 = realm.where(Event.class)
                                    .contains("idevent", id)
                                    .findAll();
                            tampil = results2.get(0).getTampil();
                        }
                    });
                    Log.e("error: " ,"tampil event: "+ tampil);

                    if (tampil == 0){
                        event.setTampil(0);
                        event.setDateupdateat(timestamp);
                        realm.beginTransaction();
                        realm.copyToRealm(event);
                        realm.commitTransaction();
                        Log.e("error: " ,"success: "+ tampil);
                    }else {
                        event.setTampil(1);
                        event.setDateupdateat(timestamp);
                        realm.beginTransaction();
                        realm.copyToRealm(event);
                        realm.commitTransaction();
                        Log.e("error: " ,"success: "+ tampil);
                    }
                }else {
                    event.setTampil(1);
                    realm.beginTransaction();
                    realm.copyToRealm(event);
                    realm.commitTransaction();
                    Log.e("error: " ,"success: "+ tampil);
                }

            }

            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                //play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {
                // app is in background, show the notification in notification tray
                if (status.equalsIgnoreCase("off")){
                    /*Intent resultIntent = new Intent(getApplicationContext(), FormActivity.class);
                    resultIntent.putExtra("message", message);
                    resultIntent.putExtra("idrekap",id);
                    resultIntent.putExtra("workcenter", mesin);
                    resultIntent.putExtra("opco", opco);
                    resultIntent.putExtra("timestamp",timestamp);
                    resultIntent.putExtra("idmesin", idmesin);
                    resultIntent.putExtra("id", "1");
                    // check for image attachment
                    if (TextUtils.isEmpty(imageUrl)) {
                        showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                    } else {
                        // image is present, show notification with image
                        showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                    }*/
                }else {
                    /*Intent resultIntent = new Intent(getApplicationContext(), LogActivity.class);
                    resultIntent.putExtra("message", message);
                    resultIntent.putExtra("idrekap",id);
                    resultIntent.putExtra("workcenter", mesin);
                    resultIntent.putExtra("opco", opco);
                    resultIntent.putExtra("timestamp",timestamp);
                    resultIntent.putExtra("idmesin", idmesin);
                    resultIntent.putExtra("id", "1");
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);*/
                }

            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }

}
