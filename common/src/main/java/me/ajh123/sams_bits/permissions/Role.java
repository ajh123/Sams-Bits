package me.ajh123.sams_bits.permissions;

import java.util.HashSet;
import java.util.Set;

class Role {
    private final String name;
    private final Set<Permission> permissions;

    public Role(String name) {
        this.name = name;
        this.permissions = new HashSet<>();
    }

    public void addPermission(Permission permission) {
        permissions.add(permission);
    }

    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }

    public String getName() {
        return name;
    }
}