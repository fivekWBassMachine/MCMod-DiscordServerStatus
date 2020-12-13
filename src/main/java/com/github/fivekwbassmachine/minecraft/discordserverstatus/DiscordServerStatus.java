package com.github.fivekwbassmachine.minecraft.discordserverstatus;

import com.github.fivekwbassmachine.minecraft.discordserverstatus.minecraft.commands.DiscordStatus;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.minecraft.server.MinecraftServer;

import java.io.File;
import java.io.IOException;

@Mod(modid = DiscordServerStatus.MODID, version = DiscordServerStatus.VERSION, acceptableRemoteVersions = "*", name = DiscordServerStatus.NAME)
public class DiscordServerStatus extends ListenerAdapter {
    public static final String MODID = "fivek_discordserverstatus";
    public static final String VERSION = "1.0.0";
    public static final String NAME = "DiscordServerStatus";

    private static boolean botIsRunning = false;
    private static boolean presenceLocked = false;
    private static JDA jda;
    private static int maxPlayers;

    public static boolean setBotStatus(OnlineStatus status, Activity activity) {
        if (botIsRunning) {
            jda.getPresence().setPresence(status, activity);
            presenceLocked = true;
            return true;
        }
        return false;
    }

    public static boolean unsetBotStatus() {
        if (botIsRunning) {
            presenceLocked = false;
            updateBotStatus();
            return true;
        }
        return false;
    }

    public static void updateBotStatus() {
        updateBotStatus(0);
    }
    public static void updateBotStatus(int playerOffset) {
        if (!botIsRunning || presenceLocked) return;
        System.out.println("Updating Bot Status");
        jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.watching((MinecraftServer.getServer().getAllUsernames().length + playerOffset) + "/" + maxPlayers + " Players"));
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        // Read config
        File tokenFile = new File("./config/DiscordServerStatus.token");
        String token = FileUtils.readFile(tokenFile);
        if (token.isEmpty()) {
            System.err.println("Can't init Bot, missing config");
        }
        else {
            try {
                JDABuilder builder = JDABuilder.createDefault(token);
                builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
                builder.setActivity(Activity.watching("Server starting... | v" + VERSION));
                jda = builder.build();
                jda.awaitReady();
                botIsRunning = true;
            }
            catch (Throwable e) {
                System.err.println("Can't init Bot:");
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(new PlayerEvents());
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        // Register commands
        event.registerServerCommand(new DiscordStatus());
    }

    @EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        maxPlayers = MinecraftServer.getServer().getMaxPlayers();
        if (botIsRunning) jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.watching("0/" + maxPlayers + " Players"));
    }
}
