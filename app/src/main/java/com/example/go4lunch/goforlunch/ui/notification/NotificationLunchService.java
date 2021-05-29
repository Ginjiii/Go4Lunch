package com.example.go4lunch.goforlunch.ui.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.SaveDataRepository;
import com.example.go4lunch.goforlunch.utils.Utils;
import com.go4lunch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class NotificationLunchService extends BroadcastReceiver {

    private final String TAG = NotificationLunchService.class.getSimpleName();

    private SaveDataRepository saveDataRepository;
    private CoworkerRepository coworkerRepository;
    private String restaurantName;
    private String restaurantAddress;
    private String usersJoining;
    private Context context;
    private Coworker currentUser;
    private String currentUserId;
    private List<Coworker> coworkers;

    private final int NOTIFICATION_ID = 001;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        this.configureRepositories();
    }

    // -------------------
    // CONFIGURE DATA
    // -------------------

    private void configureRepositories() {
        coworkerRepository = CoworkerRepository.getInstance();
        saveDataRepository = SaveDataRepository.getInstance();
        saveDataRepository.configureContext(context);
        currentUserId = coworkerRepository.getActualUser().getUid();
        if (saveDataRepository.getNotificationSettings(currentUserId)
                && getCurrentUser() != null) {
            this.fetchUsers();
        }
    }

    private void fetchUsers() {
        coworkers = new ArrayList<>();
        coworkerRepository.getAllCoworker()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                        Coworker coworker = documentSnapshot.toObject(Coworker.class);
                        if (coworker != null && coworker.getCoworkerRestaurantChoice().getRestaurantId() != null) {
                            if (coworker.getUid().equals(currentUserId)) {
                                currentUser = coworker;
                            } else {
                                coworkers.add(coworker);
                            }
                        }
                    }
                    fetchUsersGoing();
                });
    }

    private void fetchUsersGoing() {
        if (currentUser != null) {
            List<String> usersName = new ArrayList<>();
            for (Coworker coworker : coworkers) {
                if (coworker.getCoworkerRestaurantChoice() != null && currentUser.getCoworkerRestaurantChoice() != null &&
                        coworker.getCoworkerRestaurantChoice().getRestaurantId().equals(currentUser.getCoworkerRestaurantChoice().getRestaurantId())) {
                    usersName.add(coworker.getName());
                }
            }
            restaurantName = currentUser.getName();
            restaurantAddress = currentUser.getCoworkerRestaurantChoice().getRestaurantAddress();
            usersJoining = Utils.convertListToString(usersName);
            showNotification();
        }
    }

    @Nullable
    private FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    // -------------------
    // SHOW NOTIFICATION
    // -------------------

    private void showNotification() {
        String channelId = context.getString(R.string.notificationChannel);
        String message;
        String messageBody;
        if (usersJoining != null && usersJoining.length() > 0) {
            message = context.getString(R.string.notification_message);
            messageBody = String.format(message, restaurantName, usersJoining, restaurantAddress);
        } else {
            message = context.getString(R.string.message_notification_alone);
            messageBody = String.format(message, restaurantName, restaurantAddress);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_logo_go4lunch)
                .setContentTitle(context.getString(R.string.title_notification))
                .setContentText(messageBody)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody));
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }
}