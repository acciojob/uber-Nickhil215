package com.driver.model;

import com.driver.model.Driver;

import javax.persistence.*;

@Entity
@Table(name = "Cabs")
public class Cab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CabID;

    private int perKmRate;
    @Column(columnDefinition = "TINYINT(1)")
    private boolean available;

    @OneToOne
    @JoinColumn
    private Driver driver;

    public int getCabID() {
        return CabID;
    }

    public void setCabID(int cabID) {
        CabID = cabID;
    }

    public int getPerKmRate() {
        return perKmRate;
    }

    public void setPerKmRate(int perKmRate) {
        this.perKmRate = perKmRate;
    }

    public boolean isAvailable( boolean available) {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Cab(int perKmRate, boolean available) {
        this.perKmRate = perKmRate;
        this.available = available;
    }

    public Cab() {
    }
}