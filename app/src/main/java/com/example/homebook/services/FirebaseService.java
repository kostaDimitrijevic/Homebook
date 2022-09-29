package com.example.homebook.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.homebook.MainActivity;
import com.example.homebook.R;
import com.example.homebook.data.firebase.Catalog;
import com.example.homebook.data.firebase.Request;
import com.example.homebook.data.frienddata.Friend;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

public class FirebaseService extends Service {

    @Inject
    public ExecutorService executorService;

    private boolean serviceStarted = false;
    private boolean newData;
    private boolean newRequest;
    private boolean newAccept;

    private DatabaseReference reference;
    private DatabaseReference requestReference;
    private DatabaseReference acceptReference;

    // new catalog listener
    private final ValueEventListener postListener = new ValueEventListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.getValue(Catalog.class) != null && FirebaseAuth.getInstance().getCurrentUser() != null){
               newData = false;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Catalog catalog = data.child("catalog").getValue(Catalog.class);
                    if (catalog.getToUserEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                        MainActivity.firebaseCatalogList.add(catalog);
                        reference.child(data.getKey()).removeValue();
                        newData = true;
                    }
                }

                if (newData){
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getBaseContext());
                    notificationManager.notify(NOTIFICATION_ID, getNotification());
                    newData = false;
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    // friend request listener
    private final ValueEventListener requestListener = new ValueEventListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.getValue(Request.class) != null && FirebaseAuth.getInstance().getCurrentUser() != null){
                newRequest = false;
                for (DataSnapshot data : snapshot.getChildren()) {
                    Request request = data.child("friend_request").getValue(Request.class);
                    if (request.getTo().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                        MainActivity.pendingFriends.add(new Friend(0, request.getFrom(), request.getFirstname(), request.getLastname(), 0));
                        requestReference.child(data.getKey()).removeValue();
                        newRequest = true;
                    }
                }

                if (newRequest){
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getBaseContext());
                    notificationManager.notify(REQUEST_NOTIFICATION_ID, getNewRequestNotification());
                    newRequest = false;
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    //accept listener
    private final ValueEventListener acceptListener = new ValueEventListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.getValue(Request.class) != null && FirebaseAuth.getInstance().getCurrentUser() != null){
                newAccept = false;
                for (DataSnapshot data : snapshot.getChildren()) {
                    Request request = data.child("accept_request").getValue(Request.class);
                    if (request.getTo().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                        MainActivity.acceptedFriends.add(new Friend(0, request.getFrom(), request.getFirstname(), request.getLastname(), 1));
                        acceptReference.child(data.getKey()).removeValue();
                        newAccept = true;
                    }
                }

                if (newAccept){
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getBaseContext());
                    notificationManager.notify(ACCEPT_NOTIFICATION_ID, getAcceptNotification());
                    newAccept = false;
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    private static final String NOTIFICATION_CHANNEL_ID = "homebook-notification-channel";
    private static final int NOTIFICATION_ID = 1;
    private static final int REQUEST_NOTIFICATION_ID = 2;
    private static final int ACCEPT_NOTIFICATION_ID = 3;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        createNotificationChannel();

        if(!serviceStarted){
            serviceStarted = true;
            startFirebase();
        }

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void startFirebase(){
        reference = FirebaseDatabase.getInstance("https://homebook-e8d20-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Catalogs");
        reference.addValueEventListener(postListener);

        requestReference = FirebaseDatabase.getInstance("https://homebook-e8d20-default-rtdb.europe-west1.firebasedatabase.app/").getReference("FriendRequests");
        requestReference.addValueEventListener(requestListener);

        acceptReference = FirebaseDatabase.getInstance("https://homebook-e8d20-default-rtdb.europe-west1.firebasedatabase.app/").getReference("AcceptRequests");
        acceptReference.addValueEventListener(acceptListener);
    }

    private void createNotificationChannel() {

        NotificationChannelCompat notificationChannel = new NotificationChannelCompat
                .Builder(NOTIFICATION_CHANNEL_ID, NotificationManagerCompat.IMPORTANCE_DEFAULT)
                .setName(getString(R.string.homebook_notification_channel_name))
                .build();

        NotificationManagerCompat.from(this).createNotificationChannel(notificationChannel);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Notification getNotification(){

        Intent intent = new Intent(this, MainActivity.class);
        intent.setAction(MainActivity.INTENT_ACTION_NOTIFICATION);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        return new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.outline_receipt_24)
                .setContentText(getString(R.string.homebook_notification_content_title))
                .setContentText(getString(R.string.homebook_notification_content_text))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setColorized(true)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(this, R.color.teal_200))
                .build();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Notification getNewRequestNotification(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setAction(MainActivity.INTENT_ACTION_REQUEST_NOTIFICATION);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        return new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.outline_receipt_24)
                .setContentText("New Request")
                .setContentText("You have new request in the friends section!")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setColorized(true)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(this, R.color.teal_200))
                .build();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Notification getAcceptNotification(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setAction(MainActivity.INTENT_ACTION_REQUEST_NOTIFICATION);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        return new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.outline_receipt_24)
                .setContentText("Accept")
                .setContentText("User accepted your request!")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setColorized(true)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(this, R.color.teal_200))
                .build();
    }
}
