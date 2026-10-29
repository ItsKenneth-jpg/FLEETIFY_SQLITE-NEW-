package com.scc.model;

public record vehicle (int id, String plate_number, int capacity, String type, String status) {

    @Override
    public String toString() {
        return String.format("[%d] %s %d %s %s", id, plate_number, capacity, type, status);
    }
}