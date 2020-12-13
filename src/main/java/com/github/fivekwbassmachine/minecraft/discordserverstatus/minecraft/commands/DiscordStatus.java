package com.github.fivekwbassmachine.minecraft.discordserverstatus.minecraft.commands;

import com.github.fivekwbassmachine.minecraft.discordserverstatus.DiscordServerStatus;
import com.github.fivekwbassmachine.minecraft.discordserverstatus.util.Utils;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.*;

import java.util.List;

public class DiscordStatus extends Command {

    @Override
    public String getCommandName() {
        return "discordstatus";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/discordstatus <status> [<activity> [<url>] <message>]";
    }

    @Override
    public List getCommandAliases() {
        return null;
    }

    @Override
    public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_) {
        ChatComponentTranslation message;
        if (p_71515_2_.length == 0) {
            errorUsage(p_71515_1_);
            return;
        }
        else if (p_71515_2_.length == 1 && p_71515_2_[0].equals("unset")) {
            if (DiscordServerStatus.unsetBotStatus()) {
                message = new ChatComponentTranslation("chat.success.discordstatus.unset");
                message.getChatStyle().setColor(EnumChatFormatting.GRAY);
            }
            else {
                message = new ChatComponentTranslation("chat.error.discordstatus");
                message.getChatStyle().setColor(EnumChatFormatting.RED);
            }
        }
        else {
            OnlineStatus status;
            switch (p_71515_2_[0]) {
                case "online":
                    status = OnlineStatus.ONLINE;
                    break;
                case "idle":
                    status = OnlineStatus.IDLE;
                    break;
                case "busy":
                    status = OnlineStatus.DO_NOT_DISTURB;
                    break;
                default:
                    errorUsage(p_71515_1_);
                    return;
            }
            Activity activity = null;
            if (p_71515_2_.length > 2) {
                switch (p_71515_2_[1]) {
                    case "playing":

                        activity = Activity.playing(Utils.buildString(p_71515_2_, 2));
                        break;
                    case "listening":
                        activity = Activity.listening(Utils.buildString(p_71515_2_, 2));
                        break;
                    case "watching":
                        activity = Activity.watching(Utils.buildString(p_71515_2_, 2));
                        break;
                    case "streaming":
                        if (p_71515_2_.length == 3) {
                            errorUsage(p_71515_1_);
                            return;
                        }
                        activity = Activity.streaming(Utils.buildString(p_71515_2_, 3), p_71515_2_[2]);
                        break;
                    default:
                        errorUsage(p_71515_1_);
                        return;
                }
            }
            if (DiscordServerStatus.setBotStatus(status, activity)) {
                message = new ChatComponentTranslation("chat.success.discordstatus.set");
                message.getChatStyle().setColor(EnumChatFormatting.GRAY);
            } else {
                message = new ChatComponentTranslation("chat.error.discordstatus");
                message.getChatStyle().setColor(EnumChatFormatting.RED);
            }
        }
        p_71515_1_.addChatMessage(message);
    }

    @Override
    public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
        return false;
    }

    @Override
    public int compareTo(Object object) {
        return 0;
    }

    private void errorUsage(ICommandSender p_71515_1_) {
        final ChatComponentText message = new ChatComponentText(this.getCommandUsage(p_71515_1_));
        message.getChatStyle().setColor(EnumChatFormatting.RED);
        p_71515_1_.addChatMessage(message);
    }
}
