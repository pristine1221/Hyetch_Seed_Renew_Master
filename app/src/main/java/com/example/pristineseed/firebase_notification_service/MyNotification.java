package com.example.pristineseed.firebase_notification_service;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.pristineseed.GlobalNotification.NotificationUtils;
import com.example.pristineseed.R;
import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.StaticMethods;
import com.example.pristineseed.ui.bootmMainScreen.BottomMainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MyNotification extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    private NotificationUtils notificationUtils;
    public static int notification_id;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "onMessage Received:called");
            if (remoteMessage.getNotification() != null && remoteMessage.getData().size() > 0) {
                Log.d(TAG, "Data Payload: " + remoteMessage.getData().toString());
                Map<String,String> map_string=remoteMessage.getData();
                Log.d(TAG,map_string.toString());
                JSONObject object = new JSONObject(map_string);

                try {
                    String bodyData=object.getString("body");
                    String title=object.getString("title");
                    JSONArray jsonArray=new JSONArray(bodyData);
                    for(int i=0;i<jsonArray.length();i++){
                       String production_lot_no= jsonArray.getJSONObject(i).getString("production_lot_no");
                       String inspection_type=jsonArray.getJSONObject(i).getString("inspection_type");
                       String production_center_name= jsonArray.getJSONObject(i).getString("production_centre_name");

                       String body_string=production_lot_no+" : "+"Insp_type : "+inspection_type+"pro"+production_center_name;
                        //todo create channnel id
                        notification_id= StaticMethods.generateRandomNumber();
                        sendNotification(body_string,notification_id,title);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.d(TAG, "Data Payload:->else " + remoteMessage.getData().toString());
                Map<String,String> map_string=remoteMessage.getData();
                Log.d(TAG,map_string.toString());
                JSONObject object = new JSONObject(map_string);

                try {
                    String bodyData=object.getString("body");
                    String title=object.getString("title");
                    JSONArray jsonArray=new JSONArray(bodyData);
                    for(int i=0;i<jsonArray.length();i++){
                        String production_lot_no= jsonArray.getJSONObject(i).getString("production_lot_no");
                        String inspection_type=jsonArray.getJSONObject(i).getString("inspection_type");
                        String production_center_name= jsonArray.getJSONObject(i).getString("production_centre_name");

                        String body_string=production_lot_no+" : "+"Insp_type : "+inspection_type;
                        //todo create channnel id
                        notification_id= StaticMethods.generateRandomNumber();
                        sendNotification(body_string,notification_id,title);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

    }

    private void sendNotification(String messageBody,int channel_id,String title) {
        Intent intent = new Intent(this, BottomMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this.getApplicationContext(), String.valueOf(channel_id))
                        .setSmallIcon(R.mipmap.hytech_logo)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        //.setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(String.valueOf(channel_id),
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            notificationBuilder.setChannelId(String.valueOf(channel_id));
        }
        notificationManager.notify(channel_id, notificationBuilder.build());
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d(TAG,s.toString());
        try {
             SessionManagement sessionManagement=new SessionManagement(getApplicationContext());;
             sessionManagement.setFcmToken(s);
         } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
       // Log.d(TAG,"delete method:called");
    }

    private void handleNotification(String message){
        Log.e("enter Notification", message);
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent("PUSH_NOTIFICATION");
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }
    }

    private void handleDataMessage(JSONObject json){
        Log.e(TAG, "push json: " + json.toString());
        try {
            JSONObject data =  json.getJSONObject("data");
            String title = data.getString("title");
            String message = data.getString("message");
            String img=data.getString("image");

            // boolean isBackground = data.getBoolean("is_background");
            String imageUrl = "";
            String timestamp = "";

            Log.d("title", title);
            Log.d("message",message);

            JSONObject payloaddata=data.getJSONObject("payload");
            imageUrl = payloaddata.getString("large_image");
            String screen=payloaddata.getString("screen");
            Log.d("largeImg",imageUrl);
            Intent resultIntent;
            resultIntent = new Intent(getApplicationContext(), BottomMainActivity.class);
            resultIntent.putExtra("message", message);
            if (TextUtils.isEmpty(imageUrl)){
               // showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
            } else {
                //showNotificationMessageWithBigImage(getApplicationContext(), title,message, timestamp, resultIntent, imageUrl,screen);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception:- " + e.getMessage());
        }
    }

    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent,String channel_id) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent,channel_id);
    }

    private void showNotificationMessageWithBigImage(Context context, String title,String message, String timeStamp, Intent intent, String imageUrl,String imgText,String channel_id) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl,imgText,channel_id);
    }

}
