package sbu.mad.android2rails;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GCMNotificationIntentService extends IntentService {

	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;

	public GCMNotificationIntentService() {
		super("GcmIntentService");
	}

	public static final String TAG = "GCMNotificationIntentService";

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) {
			
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
				
				sendNotification("Send error: " + extras.toString());
		
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
				
				sendNotification("Deleted messages on server: " + extras.toString());
				
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

				for (int i = 0; i < 3; i++) {
					Log.i(TAG, "Working... " + (i + 1) + "/5 @ " + SystemClock.elapsedRealtime());
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}

				}
				Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());

				sendNotification(String.valueOf(extras.get(Config.MESSAGE_KEY)));
				
				Log.i(TAG, "Received: " + extras.toString());
			}
		}
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	private void sendNotification(String msg) {
		
		Log.d(TAG, "Preparing to send notification...: " + msg);
		
		
			
			Toast.makeText(this, "Funcionou", Toast.LENGTH_LONG).show();

		
		Intent notificationIntent = new Intent(this, MainActivity.class);
		notificationIntent.putExtra("Marco", "Polo");
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		NotificationManager nm = (NotificationManager) this .getSystemService(Context.NOTIFICATION_SERVICE);
		Resources res = this.getResources();
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setContentIntent(contentIntent)
		       .setSmallIcon(R.drawable.ic_stat_cloud)
		       .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_stat_cloud))
		       .setTicker("Notifica‹o")
		       .setWhen(System.currentTimeMillis())
		       .setAutoCancel(true)
		       .setContentTitle("Message")
		       .setContentText(msg);
		Notification n = builder.build();
		
		n.defaults |= Notification.DEFAULT_ALL;
		nm.notify(0, n);

		
		Log.d(TAG, "Notification sent successfully.");
	}
}
