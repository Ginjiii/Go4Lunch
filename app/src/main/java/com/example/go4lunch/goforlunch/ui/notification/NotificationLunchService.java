package com.example.go4lunch.goforlunch.ui.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.SaveDataRepository;
import com.example.go4lunch.goforlunch.utils.TextUtil;
import com.go4lunch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class NotificationLunchService extends BroadcastReceiver {

    private SaveDataRepository saveDataRepository;
    private String restaurantName;
    private String restaurantAddress;
    private String usersJoining;
    private Context context;
    private Coworker currentUser;
    private String currentUserId;
    private List<Coworker> users;

    private final int NOTIFICATION_ID = 001;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("tag", "onReceive: we step in");
        this.context = context;
        this.configureRepositories();
    }

    // -------------------
    // CONFIGURE DATA
    // -------------------

    private void configureRepositories(){
        CoworkerRepository.getInstance();
        saveDataRepository = SaveDataRepository.getInstance();
        saveDataRepository.configureContext(context);
        currentUserId = saveDataRepository.getUserId();
        if (currentUserId == null)
        {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            currentUserId = user.getUid();
        }
        Log.d("tag", "configurRepositories: are we good??");
        if(saveDataRepository.getNotificationSettings(currentUserId)
                && getCurrentUser() != null){
            this.fetchUsers();
        }
    }

    private void fetchUsers() {
         users = new ArrayList<>();
        CoworkerRepository.getAllCoworker()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                        Coworker user = documentSnapshot.toObject(Coworker.class);
                        if(user != null && user.getRestaurantUid() != null) {
                            if (user.getUid().equals(currentUserId)) {
                                currentUser = user;
                            } else {
                                users.add(user);
                            }
                        }
                    }
                    fetchUsersGoing();
                });
    }

    private void fetchUsersGoing(){
        if(currentUser != null) {
            List<String> usersName = new ArrayList<>();
            for (Coworker user : users) {
                if (user.getRestaurantUid().equals(currentUser.getRestaurantUid())){
                    usersName.add(user.getCoworkerName());
                }
            }
            restaurantName = currentUser.getRestaurantName();
            restaurantAddress = currentUser.getRestaurantAddress();
            usersJoining = TextUtil.convertListToString(usersName);
            showNotification();
        }
    }

    @Nullable
    private FirebaseUser getCurrentUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    // -------------------
    // SHOW NOTIFICATION
    // -------------------

    private void showNotification(){
        String channelId = context.getString(R.string.notificationChannel);
        String message;
        String messageBody;
        if(usersJoining != null && usersJoining.length() > 0) {
            message = context.getString(R.string.notification_message);
            messageBody = String.format(message, restaurantName, usersJoining, restaurantAddress);
        } else {
            message = context.getString(R.string.message_notification_alone);
            messageBody = String.format(message, restaurantName, restaurantAddress);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_logo_go4lunch)
                .setContentTitle(context.getString(R.string.title_notification))
                .setContentText(context.getString(R.string.subtitle_notification))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody));
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }
}