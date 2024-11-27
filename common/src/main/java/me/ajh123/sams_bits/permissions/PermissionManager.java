package me.ajh123.sams_bits.permissions;

import java.util.*;

class PermissionManager {
    private final Map<UUID, Set<Role>> userRoles;

    public PermissionManager() {
        this.userRoles = new HashMap<>();
    }

    public void addRoleToUser(UUID userId, Role role) {
        userRoles.computeIfAbsent(userId, k -> new HashSet<>()).add(role);
    }

    public void removeRoleFromUser(UUID userId, Role role) {
        Set<Role> roles = userRoles.get(userId);
        if (roles != null) {
            roles.remove(role);
            if (roles.isEmpty()) {
                userRoles.remove(userId);
            }
        }
    }

    public boolean hasPermission(UUID userId, Permission permission) {
        Set<Role> roles = userRoles.get(userId);
        if (roles == null) return false;

        for (Role role : roles) {
            if (role.hasPermission(permission)) {
                return true;
            }
        }
        return false;
    }
}