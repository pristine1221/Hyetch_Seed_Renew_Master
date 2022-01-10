package com.example.pristineseed.global.signalr;

import android.util.Log;

import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;
import com.microsoft.signalr.TransportEnum;

import java.util.concurrent.TimeUnit;

public class NotificationSignalRService {
    public static HubConnection hubConnection;
    public SessionManagement sessionManagement;

    public NotificationSignalRService(SessionManagement sessionManagement){
        this.sessionManagement=sessionManagement;
    }
    public boolean isConnectionState() {
        try {
            return hubConnection.getConnectionState() == HubConnectionState.CONNECTED;
        } catch (Exception e) {
            return false;
        }
    }

    public void Connect() {
        try {
            String url = ApiUtils.BASE_URL + ApiUtils.notificationSignalRURL + "?email=" + sessionManagement.getUserEmail();
            hubConnection = HubConnectionBuilder.create(url).withTransport(TransportEnum.WEBSOCKETS).build();
            if (!isConnectionState()) {
                hubConnection.start().onErrorComplete(error -> {
                     TimeUnit.SECONDS.sleep(15);
                     Connect();//todo do not touch
                    return false;
                }).doOnComplete(() -> {
                    Log.e("Notification Connection", hubConnection.getConnectionState().name());
                }).blockingAwait();
            }
            hubConnection.onClosed(exception -> {
                Log.e("Notification Connection", hubConnection.getConnectionState().name());
                if (sessionManagement.getUserEmail() != null && !sessionManagement.getUserEmail().equalsIgnoreCase("")) {
                    Connect();//todo do not touch
                }
            });
            //todo recevie message from server
        } catch (Exception e) {
            Log.e("Parent Exception ", e.getMessage());
        }
    }


    public void disconectHub() {
        try {
            hubConnection.stop();
        }catch (Exception e){}
    }

    public String gethubConnetionState() {
        return hubConnection.getConnectionState().name();
    }

    public void sendMessageToServer(String message) {
        hubConnection.send("ClientRecevie", message);
    }

}
