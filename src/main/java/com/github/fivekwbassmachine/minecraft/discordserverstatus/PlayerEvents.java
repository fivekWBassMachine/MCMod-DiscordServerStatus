package com.github.fivekwbassmachine.minecraft.discordserverstatus;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class PlayerEvents {

    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        DiscordServerStatus.updateBotStatus(-1);
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        DiscordServerStatus.updateBotStatus();
    }
}
