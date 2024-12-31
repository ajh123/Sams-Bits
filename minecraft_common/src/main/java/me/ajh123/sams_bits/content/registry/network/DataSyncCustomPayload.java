package me.ajh123.sams_bits.content.registry.network;

import io.netty.buffer.ByteBuf;
import me.ajh123.sams_bits.SamsBits;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;

import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record DataSyncCustomPayload(DataSyncAction action, DataSyncStatus status, int messageID, PacketByteBuf content) implements CustomPayload {
    public static final PacketCodec<PacketByteBuf, DataSyncCustomPayload> CODEC = CustomPayload.codecOf(DataSyncCustomPayload::write, DataSyncCustomPayload::new);
    public static final CustomPayload.Id<DataSyncCustomPayload> ID =  new CustomPayload.Id<DataSyncCustomPayload>(Identifier.of(SamsBits.MOD_ID, "data_sync"));

    public enum DataSyncAction {
        INVALID("invalid"),
        GET_NODE_ID("get_node_id"),
        GET_NODE_POSITION("get_node_position"),
        GET_DESTINATIONS_OF_NODE("get_destinations_of_node");

        private final Identifier identifier;

        private DataSyncAction(Identifier identifier) {
            this.identifier = identifier;
        }

        private DataSyncAction(String identifier) {
            this(Identifier.of(SamsBits.MOD_ID, identifier));
        }

        public Identifier getIdentifier() {
            return this.identifier;
        }

        public static DataSyncAction of(Identifier identifier) {
            DataSyncAction[] actions = DataSyncAction.values();
            for (DataSyncAction action : actions) {
                if (action.identifier.equals(identifier)) {
                    return action;
                }
            }
            return DataSyncAction.INVALID;
        }
    }

    public enum DataSyncStatus {
        INVALID(0),
        SUCCESS(200),
        NOT_FOUND(404),
        INTERNAL_ERROR(500);

        private final int status_code;

        private DataSyncStatus(int status_code) {
            this.status_code = status_code;
        }

        public int getStatusCode() {
            return this.status_code;
        }

        public static DataSyncStatus of(int status_code) {
            DataSyncStatus[] statuses = DataSyncStatus.values();
            for (DataSyncStatus status : statuses) {
                if (status.status_code == status_code) {
                    return status;
                }
            }
            return DataSyncStatus.INVALID;
        }
    }

    private DataSyncCustomPayload(PacketByteBuf buf) {
        this(readAction(buf), readStatus(buf), buf.readInt(), readContent(buf));
    }

    private void write(PacketByteBuf buf) {
        writeAction(buf, action);
        writeStatus(buf, status);
        buf.writeInt(messageID);
        writeContent(buf, content);
    }

    static DataSyncStatus readStatus(PacketByteBuf buf) {
        int code = buf.readInt();
        DataSyncStatus status = DataSyncStatus.of(code);
        return status;
    }

    static void writeStatus(PacketByteBuf buf, DataSyncStatus status) {
        buf.writeInt(status.status_code);
    }

    static PacketByteBuf readContent(PacketByteBuf buf) {
        int length = buf.readInt(); // Read the length of the content
        ByteBuf byteBuf = buf.readBytes(length); // Read the bytes
        return new PacketByteBuf(byteBuf); // Wrap the ByteBuf into a PacketByteBuf
    }
    
    static void writeContent(PacketByteBuf buf, PacketByteBuf content) {
        byte[] bytes = new byte[content.readableBytes()];
        content.readBytes(bytes); // Read all bytes into an array
        buf.writeInt(bytes.length); // Write the length
        buf.writeBytes(bytes); // Write the content bytes
    }

    static DataSyncAction readAction(PacketByteBuf buf) {
        Identifier identifier = buf.readIdentifier();
        DataSyncAction action = DataSyncAction.of(identifier);
        return action;
    }

    static void writeAction(PacketByteBuf buf, DataSyncAction action) {
        buf.writeIdentifier(action.getIdentifier());
    }

    public CustomPayload.Id<DataSyncCustomPayload> getId() {
        return ID;
    }
}
