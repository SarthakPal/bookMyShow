package com.lld.bookMyShow.services.notification;

import com.lld.bookMyShow.model.User;

import java.util.Date;

public abstract class Notification {
    private int notificationId;
    // The Date datatype represents and deals with both date and time.
    private Date createdOn;
    private String content;

    // person here refers to an instance of the Person class
    public abstract void sendNotification(User user);
}
