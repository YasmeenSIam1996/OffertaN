package user.offerta.com.FirebaseUtils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import user.offerta.com.Activities.HomeActivity;
import user.offerta.com.R;


public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String TAG = "MyFirebaseMsgService";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e("MyFirebaseIIDService2", "recieved");


        String title = "";
        if (remoteMessage.getNotification().getTitle() != null) {
            title = remoteMessage.getNotification().getTitle();
        }

        String message = "";
        if (remoteMessage.getNotification().getBody() != null) {
            message = remoteMessage.getNotification().getBody();
        }


        sendNotification(title, message);

    }

    @TargetApi(Build.VERSION_CODES.O)
    private void sendNotification(String title, String message) {

        Intent intent = new Intent(this, HomeActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        Notification notify = new Notification.Builder(this)
//                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
//                        R.drawable.back_splash))
//                .setContentTitle(title)
//                .setContentText(message)
//                .setAutoCancel(true)
//
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent).build();

//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notify.flags = Notification.FLAG_INSISTENT | Notification.FLAG_AUTO_CANCEL;
//        notificationManager.cancelAll();
//
//        notificationManager.notify(0, notify);

    }



}
