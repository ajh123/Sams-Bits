package me.ajh123.sams_bits;

import me.ajh123.sams_bits.roads.RoadManager;

public abstract class SamsBitsCommon {
    public static SamsBitsCommon INSTANCE;
    private final RoadManager roadManager;

    protected SamsBitsCommon() {
        INSTANCE = this;
        this.roadManager = new RoadManager(this);
    }

    public static SamsBitsCommon getInstance() {
        return INSTANCE;
    }

    public RoadManager getRoadManager() {
        return roadManager;
    }

    public abstract void log_warn(String message);

    public abstract void log_info(String message);

    public abstract void log_debug(String message);
}
