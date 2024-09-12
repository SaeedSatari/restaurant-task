package com.altointelligence.model;

public class Table {
    public final int size;
    private int availableSeats;

    public Table(int size) {
        this.size = size;
        this.availableSeats = size;
    }

    public boolean canSeat(ClientsGroup group) {
        return availableSeats >= group.size;
    }

    public void seatGroup(ClientsGroup group) {
        if (canSeat(group)) {
            availableSeats -= group.size;
        }
    }

    public void removeGroup(ClientsGroup group) {
        availableSeats += group.size;
    }

    public boolean isFullyAvailable() {
        return availableSeats == size;
    }
}