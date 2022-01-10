package com.example.pristineseed;

import android.app.Activity;

public abstract class BackgroundTask {
    private Activity activity;
    private String flag;
    public BackgroundTask(Activity activity) {
        this.activity = activity;
    }
    public BackgroundTask(Activity activity,String flag){
        this.activity = activity;
        this.flag=flag;

    }

    public BackgroundTask() {

    }

    private void startBackground() {
        if (activity != null) {
            new Thread(new Runnable() {
                public void run() {
                    doInBackground();
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            onPostExecute();
                        }
                    });
                }

            }).start();
        }
    }

    public void execute(){
        startBackground();
    }

    public abstract void doInBackground();
    public abstract void onPostExecute();
}
