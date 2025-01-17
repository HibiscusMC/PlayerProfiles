package me.aglerr.playerprofiles.utils;

import me.aglerr.mclibs.libs.Common;
import me.aglerr.mclibs.xseries.XSound;
import me.aglerr.playerprofiles.PlayerProfiles;
import me.aglerr.playerprofiles.configs.ConfigManager;
import me.aglerr.playerprofiles.manager.profile.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Utils {

    public static boolean hasCustomModelData(){
        return Integer.parseInt(Bukkit.getVersion().split("\\.")[1]) >= 14;
    }

    public static String tryParsePAPI(@NotNull String message, Player player, Player target){
        // Get the profile manager
        ProfileManager profileManager = PlayerProfiles.getInstance().getProfileManager();
        // Player profile status
        String playerStatus = profileManager.isProfileLocked(player) ? "&cLocked" : "&aUnlocked";
        // Target profile status
        String targetStatus = profileManager.isProfileLocked(target) ? "&cLocked" : "&aUnlocked";
        // Finally, return the value with parsed player and target
        return Common.tryParsePAPI(player, message)
                .replace("{player}", player.getName())
                .replace("{target}", target.getName())
                .replace("{player_status}", playerStatus)
                .replace("{target_status}", targetStatus)
                .replace("{player_health}", player.getHealth() + "")
                .replace("{target_health}", target.getHealth() + "")
                .replace("{player_exp}", player.getExp() + "")
                .replace("{target_exp}", target.getExp() + "")
                .replace("{player_level}", player.getLevel() + "")
                .replace("{target_level}", target.getLevel() + "")
                .replace("{player_uuid}", player.getUniqueId().toString())
                .replace("{target_uuid}", target.getUniqueId().toString())
                .replace("{player_world}", player.getWorld().getName())
                .replace("{target_world}", target.getWorld().getName());
    }

    public static List<String> tryParsePAPI(@NotNull List<String> messages, Player player, Player target){
        return messages.stream().map(message -> tryParsePAPI(message, player, target)).collect(Collectors.toList());
    }

    public static void playSound(Player player, String soundPath){
        // Get the config.yml
        FileConfiguration config = ConfigManager.CONFIG.getConfig();
        // Get the path to the sound
        String path = "sounds." + soundPath;
        // Return if the sound isn't enabled
        if(!config.getBoolean(path + ".enabled"))
            return;
        // Get the 1.8 - 1.17 sound support optional
        Optional<XSound> optional = XSound.matchXSound(config.getString(path + ".sound"));
        // If the sound is invalid, return
        if(!optional.isPresent())
            return;
        // Get the player location
        Location location = player.getLocation();
        // Get the sound from the optional XSound
        Sound sound = optional.get().parseSound();
        // Get the volume
        float volume = (float) config.getDouble(path + ".volume");
        // Get the pitch
        float pitch = (float) config.getDouble(path + ".pitch");
        // Finally play the sound to the player
        player.playSound(location, sound, volume, pitch);
    }

}
