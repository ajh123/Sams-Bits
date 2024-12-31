package me.ajh123.sams_bits.maths;

public class MercatorProjection {
    private static final double EARTH_RADIUS = 6371000; // Earth's radius in metres
    private static final double HORIZONTAL_LIMIT = 30000000; // Minecraft horizontal limit, TODO: get it from the world it self.
    private static final double MAX_LATITUDE = 85.05112878; // Practical Mercator limit in degrees

    // Converts Minecraft X to Longitude
    public static double minecraftToLongitude(double x) {
        return (x / HORIZONTAL_LIMIT) * 180.0; // Scale X to ±180°
    }

    // Converts Minecraft Z to Latitude using Mercator's inverse projection
    public static double minecraftToLatitude(double z) {
        double normalisedZ = (-z / HORIZONTAL_LIMIT) * Math.PI; // Scale Z to ±π radians
        double latRadians = Math.atan(Math.sinh(normalisedZ)); // Inverse Mercator
        return Math.toDegrees(latRadians); // Convert to degrees
    }

    // Converts Latitude to Minecraft Z (Optional)
    public static double latitudeToMinecraftZ(double latitude) {
        latitude = Math.max(-MAX_LATITUDE, Math.min(MAX_LATITUDE, latitude)); // Clamp to Mercator bounds
        double latRadians = Math.toRadians(latitude);
        double mercatorY = Math.log(Math.tan(Math.PI / 4 + latRadians / 2));
        return -(mercatorY / Math.PI) * HORIZONTAL_LIMIT; // Scale back to Minecraft Z
    }
}