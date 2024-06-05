package org.osj.gemhuntplugin.USER.COMMAND;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.osj.gemhuntplugin.GemHuntPlugin;

public class AddWhiteListCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        String inviteName = args[0].toLowerCase();
        GemHuntPlugin.getConfigManager().getConfig("whitelist").set("players." + inviteName, true);
        GemHuntPlugin.getConfigManager().saveConfig("whitelist");
        GemHuntPlugin.getConfigManager().reloadConfig("whitelist");
        return false;
    }
}
