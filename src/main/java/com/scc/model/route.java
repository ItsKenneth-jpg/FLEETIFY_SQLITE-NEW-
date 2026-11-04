package com.scc.model;

public record route(String id, String origin, String destination, String status) {
    @Override
    public String toString(){return String.format("%s %s %s %s", id, origin, destination, status);}
}
