package me.ajh123.sams_bits;

import java.util.HashMap;
import java.util.Map;

import me.ajh123.sams_bits.roads.RoadManager;

public abstract class SamsBitsCommon {
    public static SamsBitsCommon INSTANCE;
    private final Map<String, RoadManager> roadManagers = new HashMap<>();

    protected SamsBitsCommon() {
        INSTANCE = this;
    }

    public static SamsBitsCommon getInstance() {
        return INSTANCE;
    }

    public RoadManager getRoadManager(String world) {
        RoadManager manager = roadManagers.get(world);
        if (manager == null) {
            manager = new RoadManager(INSTANCE);
            roadManagers.put(world, manager);
        }
        return manager;
    }

    public abstract void log_warn(String message);

    public abstract void log_info(String message);

    public abstract void log_debug(String message);
}
