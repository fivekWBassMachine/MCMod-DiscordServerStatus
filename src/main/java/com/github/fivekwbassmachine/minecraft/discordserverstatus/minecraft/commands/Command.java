package com.github.fivekwbassmachine.minecraft.discordserverstatus.minecraft.commands;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;

public abstract class Command implements ICommand {

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
        if (p_71519_1_ instanceof DedicatedServer) {
            // Command is sent from the server console
            return true;
        }
        else if (p_71519_1_ instanceof EntityPlayerMP) {
            // Command is sent from a player on a server.
            /**
             * I FUCKING HATE ALL FORGE DEVELOPERS FOR THIS "PERMISSION API" HOW THE HELL?!?
             *
             * This beautiful line of code gets all OPs from the server:
             * MinecraftServer.getServer().getConfigurationManager().func_152603_m()
             * as an {@link net.minecraft.server.management.UserListOps}
             * {@link net.minecraft.server.management.UserListOps#func_152683_b(Object)} gets a single operator
             * while the parameter is a {@link com.mojang.authlib.GameProfile}
             * which is received from the sender of the command, parsed to an {@link EntityPlayerMP}
             *
             * Beautiful, isn't it?
             * Something like MinecraftServer.getServer().getOperators().exists(((EntityPlayerMP) p_71519_1_).getGameProfile()); would be too easy, huh?
             * OR the most elegant way, a Method in the {@link ICommand} interface to return the permission level of this command...
             */
            return MinecraftServer.getServer().getConfigurationManager().func_152603_m().func_152683_b(((EntityPlayerMP) p_71519_1_).getGameProfile()) != null;
        } else {
            return false;
        }
    }

}
