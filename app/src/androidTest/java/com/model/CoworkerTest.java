package com.model;

import androidx.annotation.Nullable;

import com.example.go4lunch.goforlunch.models.Coworker;

import junit.framework.TestCase;

import org.junit.Before;

import java.util.List;

public class CoworkerTest extends TestCase {

    private Coworker coworker;
    private String uid = "uid";
    private String name = "name";
    @Nullable
    private String email = "email";
    @Nullable
    private String photoUrl = "photoUrl";
    @Nullable
    private List<String> likedRestaurants;

    @Before
    public void setup() {}

    public void testSetAndGetUid() {
        coworker = new Coworker(uid,name,email,photoUrl);
        assertEquals(uid, coworker.getUid());

        String setData = "uid";
        coworker.setUid(setData);

        String getData = coworker.getUid();
        assertNotNull(getData);
        assertEquals(setData,getData);
    }

    public void testSetAndGetName() {
        coworker = new Coworker(uid,name,email,photoUrl);
        assertEquals(name, coworker.getName());

        String setData = "name";
        coworker.setName(setData);

        String getData = coworker.getName();
        assertNotNull(getData);
        assertEquals(setData,getData);
    }

    public void testSetAndGetEmail() {
        coworker = new Coworker(uid,name,email,photoUrl);
        assertEquals(email, coworker.getEmail());

        String setData = "email";
        coworker.setEmail(setData);

        String getData = coworker.getEmail();
        assertNotNull(getData);
        assertEquals(setData,getData);
    }

    public void testSetAndGetPhotoUrl() {
        coworker = new Coworker(uid,name,email,photoUrl);
        assertEquals(photoUrl, coworker.getPhotoUrl());

        String setData = "photoUrl";
        coworker.setPhotoUrl(setData);

        String getData = coworker.getPhotoUrl();
        assertNotNull(getData);
        assertEquals(setData,getData);
    }

    public void testGetLikedRestaurants() {
        coworker = new Coworker(uid,name,email,photoUrl);
        assertNull(coworker.getLikedRestaurants());

        String setData = "uid";
        coworker.setName(setData);

        String getData = coworker.getName();
        assertNotNull(getData);
        assertEquals(setData,getData);
    }
}