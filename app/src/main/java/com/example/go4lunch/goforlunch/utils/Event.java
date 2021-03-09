package com.example.go4lunch.goforlunch.utils;

public class Event<T> {

    private T content;
    private Boolean hasBeenHandled = false;

    public Event(T content){
        if(content == null){
            throw new IllegalArgumentException("null values in Event are not allowed.");
        }
        this.content = content;
    }

    public T getContentIfNotHandle(){
        if(hasBeenHandled){
            return null;
        } else {
            hasBeenHandled = true;
            return content;
        }
    }

    public Boolean getHasBeenHandled(){
        return hasBeenHandled;
    }
}