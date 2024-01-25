package com.lld.bookMyShow.model;

import com.lld.bookMyShow.enums.PaymentStatus;

import java.util.Date;

// Payment is an abstract class
public abstract class Payment {
    // Data members
    private double amount;

    // The Date datatype represents and deals with both date and time.
    private Date timestamp;
    private PaymentStatus status;

    public abstract boolean makePayment();
}

