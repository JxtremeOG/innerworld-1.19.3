package net.jxtremeog.innerworld.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.jxtremeog.innerworld.InnerWorld;
import net.jxtremeog.innerworld.networking.packet.ItemStackSyncS2CPacket;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier ITEM_SYNC = new Identifier(InnerWorld.MOD_ID, "item_sync");

    public static void registerC2SPackets() {

    }
    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC, ItemStackSyncS2CPacket::receive);
    }
}
