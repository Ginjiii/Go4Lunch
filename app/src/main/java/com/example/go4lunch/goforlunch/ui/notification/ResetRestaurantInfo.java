package com.example.go4lunch.goforlunch.ui.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.google.firebase.firestore.DocumentSnapshot;

public class ResetRestaurantInfo extends BroadcastReceiver {

    private CoworkerRepository coworkerRepository;

    @Override
    public void onReceive(Context context, Intent intent) {
        coworkerRepository = CoworkerRepository.getInstance();
        this.eraseRestaurantInfo();
    }

    private void eraseRestaurantInfo() {
        coworkerRepository.getAllCoworker()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                        Coworker coworker = documentSnapshot.toObject(Coworker.class);
                        if (coworker != null && coworker.getCoworkerRestaurantChoice().getRestaurantId() != null) {
                            coworkerRepository.updateRestaurantPicked(null, null, null, coworker.getUid());
                        }
                    }
                });
    }
}