package models;

import androidx.annotation.Nullable;

public class User {
    private String userId;
    private String username;
    @Nullable
    private String urlPicture;

    public User(String userId){
        this.userId = userId;
    }

    public User(String userId, String username, @Nullable String urlPicture) {
        this.userId = userId;
        this.username = username;
        this.urlPicture = urlPicture;
    }

    //--- GETTERS ---
    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    @Nullable
    public String getUrlPicture() { return urlPicture; }


    //--- SETTERS ---
    public void setUsername(String username) { this.username = username; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setUrlPicture(@Nullable String urlPicture) { this.urlPicture = urlPicture; }

}