package nl.Steffion.BlockHunt.Listeners;

import nl.Steffion.BlockHunt.Arena;
import nl.Steffion.BlockHunt.ArenaHandler;
import nl.Steffion.BlockHunt.W;
import nl.Steffion.BlockHunt.Serializables.LocationSerializable;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerQuitEvent implements Listener {
	
	public ArrayList<String> leftPlayer = new ArrayList<String>();
	public LocationSerializable spawnWarp;
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuitEvent(PlayerQuitEvent event) {
		Player playerLeaving = event.getPlayer();
		for (Arena arena : W.arenaList) {
			if (arena.playersInArena.contains(playerLeaving)) {
				spawnWarp = arena.spawnWarp;
				leftPlayer.add(playerLeaving.getUniqueId().toString());
				ArenaHandler.playerLeaveArena(playerLeaving, true, true);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		Player playerJoining = event.getPlayer();
		if(leftPlayer.contains(playerJoining.getUniqueId().toString())) {
			playerJoining.teleport(spawnWarp);
			leftPlayer.remove(playerJoining.getUniqueId().toString());
		}
	}
}
