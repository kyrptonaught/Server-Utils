package net.kyrptonaught.serverutils.velocityserverswitch;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

public class VelocityServerSwitchMod {
    public static Identifier BUNGEECORD_ID = new Identifier("bungeecord", "main");


    public static void onInitialize() {
        CommandRegistrationCallback.EVENT.register(VelocityServerSwitchMod::register);
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean b) {
        dispatcher.register(CommandManager.literal("velocityserverswitch")
                .requires((source) -> source.hasPermissionLevel(0))
                .then(CommandManager.argument("servername", StringArgumentType.word())
                        .executes((commandContext) -> {
                            String servername = StringArgumentType.getString(commandContext, "servername");
                            ByteBufDataOutput output = new ByteBufDataOutput(new PacketByteBuf(Unpooled.buffer()));

                            output.writeUTF("Connect");
                            output.writeUTF(servername);
                            ServerPlayNetworking.send(commandContext.getSource().getPlayer(), BUNGEECORD_ID, output.getBuf());
                            return 1;
                        })));
    }
}
