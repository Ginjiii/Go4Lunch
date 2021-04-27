package com.example.go4lunch.goforlunch.ui.setting;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.goforlunch.base.BaseViewModel;
import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.SaveDataRepository;
import com.example.go4lunch.goforlunch.utils.Actions;
import com.example.go4lunch.goforlunch.utils.Event;
import com.go4lunch.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import static com.example.go4lunch.goforlunch.utils.Actions.DELETE_FIREBASE_USER;
import static com.example.go4lunch.goforlunch.utils.Actions.DELETE_USER;
import static com.example.go4lunch.goforlunch.utils.Actions.UPDATE_FIREBASE_INFO_USER;
import static com.example.go4lunch.goforlunch.utils.Actions.UPDATE_PHOTO;
import static com.example.go4lunch.goforlunch.utils.Actions.UPDATE_PHOTO_FIREBASE;
import static com.example.go4lunch.goforlunch.utils.Actions.UPDATE_USER;
import static com.example.go4lunch.goforlunch.utils.TextUtil.isTextLongEnough;
import static com.go4lunch.R.string.incorrect_username;

public class SettingViewModel extends BaseViewModel {

    //----- PUBLIC LIVE DATA -----
    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> urlPicture = new MutableLiveData<>();
    public MutableLiveData<Boolean> isNotificationEnabled = new MutableLiveData<>();
    public MutableLiveData<Boolean> isUsernameError = new MutableLiveData<>();
    public MutableLiveData<Integer> errorMessageUsername = new MutableLiveData<>();

    //----- PRIVATE LIVE DATA -----
    private final MutableLiveData<Event<Object>> deleteUser = new MutableLiveData<>();
    private final MutableLiveData<Event<Object>> openDialog = new MutableLiveData<>();

    //----- GETTER -----
    public LiveData<Event<Object>> getDeleteUser(){
        return deleteUser;
    }
    public LiveData<Event<Object>> getOpenDialog() {
        return openDialog;
    }

    private String newUsername;
    private String newPhotoUrl;
    private String urlPhotoSelected;
    private SaveDataRepository saveDataRepository;

    public SettingViewModel(CoworkerRepository coworkerRepository, SaveDataRepository saveDataRepository) {
        this.coworkerRepository = coworkerRepository;
        this.saveDataRepository = saveDataRepository;
        coworker = coworkerRepository.getCoworker();
    }

    // --------------------
    // START
    // --------------------

    public void configureUser(){
        isLoading.setValue(true);
        this.configureInfoUser();

    }

    public void configureSaveDataRepo(Context context){
        if(saveDataRepository.getPreferences() == null){
            saveDataRepository.configureContext(context);
        }
    }

    // --------------------
    // UPDATE BINDING INFO
    // --------------------

    private void configureInfoUser(){
        if (coworker != null) {
            username.setValue(coworker.getCoworkerName());
            email.setValue(coworker.getCoworkerEmail());
            urlPicture.setValue(coworker.getCoworkerPhotoUrl());
            isLoading.setValue(false);
            isNotificationEnabled.setValue(saveDataRepository.getNotificationSettings(coworker.getUid()));
        }
    }

    // --------------------
    // GET USER ACTION
    // --------------------

    public void notificationStateChanged(boolean enabled){
        if(enabled){
            enableNotification();
            isNotificationEnabled.setValue(true);
        } else {
            disableNotification();
            isNotificationEnabled.setValue(false);
        }
    }

    public void updateUserInfo(){
        isLoading.setValue(true);
        isUsernameError.setValue(false);
        newUsername = username.getValue();
        if(isNewUserInfoCorrect(newUsername)){
            CoworkerRepository.updateUsername(newUsername, coworker.getUid())
                    .addOnFailureListener(this.onFailureListener(UPDATE_FIREBASE_INFO_USER))
                    .addOnSuccessListener(this.onSuccessListener(UPDATE_USER));
        } else {
            isLoading.setValue(false);
        }
    }

    public void deleteUserFromFirebaseRequest() {
        openDialog.setValue(new Event<>(new Object()));
    }

    public void deleteUserFromFirebase() {
        isLoading.setValue(true);
        CoworkerRepository.deleteCoworker(getCurrentUserUid())
                .addOnSuccessListener(this.onSuccessListener(DELETE_USER))
                .addOnFailureListener(this.onFailureListener(DELETE_FIREBASE_USER));
    }

    public void updateUserPhoto(String urlPhoto) {
        isLoading.setValue(true);
        urlPhotoSelected = urlPhoto;
        uploadPhotoInFirebase(urlPhoto);
    }

    // --------------------
    // SET NOTIFICATION
    // --------------------

    private void disableNotification(){
        openDialog.setValue(new Event<>(R.string.notification_disabled));
        saveDataRepository.saveNotificationSettings(false, coworker.getUid());
    }

    private void enableNotification(){
        openDialog.setValue(new Event<>(R.string.notifications_enabled));
        saveDataRepository.saveNotificationSettings(true, coworker.getUid());
    }

    // --------------------
    // SET NEW PICTURE
    // --------------------

    private void uploadPhotoInFirebase(final String urlPhoto) {
        String uuid = UUID.randomUUID().toString();
        StorageReference imageRef = FirebaseStorage.getInstance().getReference(uuid);
        imageRef.putFile(Uri.parse(urlPhoto))
                .addOnSuccessListener(this::getUrlPhotoFromFirebase)
                .addOnFailureListener(this.onFailureListener(UPDATE_PHOTO_FIREBASE));
    }

    private void getUrlPhotoFromFirebase(UploadTask.TaskSnapshot taskSnapshot){
        taskSnapshot.getMetadata().getReference().getDownloadUrl()
                .addOnSuccessListener(uri -> {
                    newPhotoUrl = uri.toString();
                    coworkerRepository.updateUrlPicture(newPhotoUrl, getCurrentUserUid())
                            .addOnSuccessListener(onSuccessListener(UPDATE_PHOTO))
                            .addOnFailureListener(this.onFailureListener(UPDATE_PHOTO_FIREBASE));
                }).addOnFailureListener(this.onFailureListener(UPDATE_PHOTO_FIREBASE));
    }

    // --------------------
    // UTILS
    // --------------------

    private boolean isNewUserInfoCorrect(String username){
                if(!isTextLongEnough(username, 3)){
            isUsernameError.setValue(true);
            errorMessageUsername.setValue(incorrect_username);
            return false;
        }
        return true;
    }

    private OnSuccessListener<Void> onSuccessListener(final Actions actions){
        return aVoid -> {
            switch (actions){
                case UPDATE_USER:
                    isLoading.setValue(false);
                    coworker.setCoworkerName(newUsername);
                    break;
                case DELETE_USER:
                    deleteUser.setValue(new Event<>(new Object()));
                    isLoading.setValue(false);
                    break;
                case UPDATE_PHOTO:
                    isLoading.setValue(false);
                    urlPicture.setValue(newPhotoUrl);
                    coworker.setCoworkerPhotoUrl(newPhotoUrl);
                    break;
            }
        };
    }

    @Override
    public void action(Actions actions) {
        switch (actions){
            case UPDATE_PHOTO_FIREBASE:
                updateUserPhoto(urlPhotoSelected);
                break;
            case UPDATE_FIREBASE_INFO_USER:
                updateUserInfo();
                break;
            case DELETE_FIREBASE_USER:
                deleteUserFromFirebase();
                break;
        }
    }
}
