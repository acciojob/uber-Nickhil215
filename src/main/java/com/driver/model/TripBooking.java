package com.driver.model;

import javax.persistence.*;


@Entity
@Table(name = "tripBooking")
public class TripBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int TripBookingId;

    private String fromLocation;

    private String toLocation;

    private int distanceInKm;
    @Enumerated(value = EnumType.STRING)
    private TripStatus tripStatus;

    private int bill;


    @ManyToOne
    @JoinColumn
    private Driver driver;


    @ManyToOne
    @JoinColumn
    private Customer customer;

    public TripBooking(String fromLocation, String toLocation, int distanceInKm, TripStatus tripStatus) {
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.distanceInKm = distanceInKm;
        this.tripStatus = tripStatus;
    }

    public TripBooking(String fromLocation, String toLocation, int distanceInKm, TripStatus tripStatus, int bill) {
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.distanceInKm = distanceInKm;
        this.tripStatus = tripStatus;
        this.bill = bill;

    }

    public int getTripBookingId() {
        return TripBookingId;
    }

    public void setTripBookingId(int tripBookingId) {
        TripBookingId = tripBookingId;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public int getDistanceInKm() {
        return distanceInKm;
    }

    public void setDistanceInKm(int distanceInKm) {
        this.distanceInKm = distanceInKm;
    }

    public TripStatus getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(TripStatus tripStatus) {
        this.tripStatus = tripStatus;
    }

    public int getBill() {
        return bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }



    public TripBooking() {
    }
}
