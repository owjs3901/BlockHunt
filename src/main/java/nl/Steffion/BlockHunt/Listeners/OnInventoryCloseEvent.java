package nl.Steffion.BlockHunt.Listeners;

import java.util.ArrayList;

import nl.Steffion.BlockHunt.Arena;
import nl.Steffion.BlockHunt.ArenaHandler;
import nl.Steffion.BlockHunt.MemoryStorage;
import nl.Steffion.BlockHunt.Managers.MessageManager;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class OnInventoryCloseEvent implements Listener {

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onInventoryCloseEvent(InventoryCloseEvent event) {
		Inventory inv = event.getInventory();
		InventoryView invView=event.getView();
		if (inv.getType().equals(InventoryType.CHEST)) {
			if (invView.getTitle().contains("DisguiseBlocks")) {
				String arenaname = inv.getItem(0).getItemMeta().getDisplayName().replaceAll(MessageManager.replaceAll("%NDisguiseBlocks of arena: %A"), "");

				Arena arena = null;
				for (Arena arena2 : MemoryStorage.arenaList) {
					if (arena2.arenaName.equalsIgnoreCase(arenaname)) {
						arena = arena2;
					}
				}

				ArrayList<ItemStack> blocks = new ArrayList<>();
				for (ItemStack item : inv.getContents()) {
					if (item != null) {
						if (!item.getType().equals(Material.PAPER)) {
							if (item.getType().equals(Material.FLOWER_POT)) {
								blocks.add(new ItemStack(Material.FLOWER_POT));
							} else {
								blocks.add(item);
							}
						}
					}
				}

				arena.disguiseBlocks = blocks;
				save(arena);
			}
		}
	}

	public void save(Arena arena) {
		MemoryStorage.arenas.getFile().set(arena.arenaName, arena);
		MemoryStorage.arenas.save();
		ArenaHandler.loadArenas();
	}
}
