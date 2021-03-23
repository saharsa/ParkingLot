package com.saharsa.model;

import java.util.Objects;

public class ParkingSpot implements Comparable{

    private Integer spotId;
    private boolean isFree;
    private Vehicle vehicle;

    public ParkingSpot(int spotId) {
        this.spotId = spotId;
    }

    public Integer getSpotId() {
        return spotId;
    }

    public void setSpotId(Integer spotId) {
        this.spotId = spotId;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void assignVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.isFree = false;
    }

    public void removeVehicle() {
        this.vehicle = null;
        this.isFree = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSpot that = (ParkingSpot) o;
        return spotId.equals(that.spotId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spotId);
    }

    @Override
    public int compareTo(Object parkingSpot) {
        return this.getSpotId().compareTo(((ParkingSpot)parkingSpot).getSpotId());
    }
}
