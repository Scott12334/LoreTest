package net.newlife.lore.network.messages;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ServerBoundUpdatePacket {
    public final BlockPos spawnPos;
    public ServerBoundUpdatePacket(BlockPos pos){
        this.spawnPos=pos;
    }
    public ServerBoundUpdatePacket (FriendlyByteBuf buffer){
        this(buffer.readBlockPos());
    }
    public void encode(FriendlyByteBuf buffer){
        buffer.writeBlockPos(spawnPos);
    }
    public boolean handle(Supplier<NetworkEvent.Context> ctx){
        final var success = new AtomicBoolean(false);
        ctx.get().enqueueWork(()-> {
            ctx.get().getSender().level.addFreshEntity(new LightningBolt(EntityType.LIGHTNING_BOLT,ctx.get().getSender().level));
            success.set(true);
        });

        ctx.get().setPacketHandled(true);
        return success.get();
    }
}
