package me.ajh123.sams_bits.content.roads;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.ajh123.sams_bits.maths.Position;

import java.util.Optional;

public class LinkingComponent {
    private Position source = null;

    public static final Codec<LinkingComponent> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.INT.optionalFieldOf("x").forGetter(component -> Optional.ofNullable(component.source).map(Position::getX)),
            Codec.INT.optionalFieldOf("y").forGetter(component -> Optional.ofNullable(component.source).map(Position::getY)),
            Codec.INT.optionalFieldOf("z").forGetter(component -> Optional.ofNullable(component.source).map(Position::getZ))
    ).apply(builder, (x, y, z) -> new LinkingComponent(
            x.orElse(null) != null && y.orElse(null) != null && z.orElse(null) != null ?
                    new Position(x.orElse(0), y.orElse(0), z.orElse(0)) : null
    )));

    // Constructors
    public LinkingComponent() {}

    public LinkingComponent(Position source) {
        this.source = source;
    }

    // Getters
    public Position getSource() {
        return source;
    }

    // Setters
    public void setSource(Position source) {
        this.source = source;
    }

   public static LinkingComponent empty() {
        return new LinkingComponent();
    }
}