package com.github.rahulpat.cinemaroomrestservice.model;

import java.util.UUID;

public class Purchases {
    private UUID uuid;
    private Seat seat;

    public Purchases(Seat seat) {
        this.uuid = UUID.randomUUID();
        this.seat = seat;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}

