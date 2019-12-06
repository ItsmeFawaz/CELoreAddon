package me.bottleofglass.CELoreAddon.Listeners;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;

import me.bottleofglass.CELoreAddon.Main;

public class CommandListener implements Listener {
	
private Main plugin;
	
	public CommandListener(Main plugin) {
		//random stuff to get ur plugin workin
		this.plugin = plugin;
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
		
	}
	//a Set to hold a list of players with their timer running
	public static Set<Player> timerSet = new HashSet<>();
	
	//Array of all the Custom Enchants in CrazyEnchantments
	String[] Enchants = { "Mermaid", "Glowing", "Implants", "Commander", "Springs", "Gears", "Anti-Gravity", "Rocket", "Wings", "Burn-Shield","Overlord","Self-Destruct","Hulk","Ninja",
			"Enlightened","Freeze","Fortify","Molten","Pain-Giver","Savior", "Nursery","Insomnia","Valor","Smoke-Bomb", "Drunk","Voodoo", "Recover", "Angel","StormCaller","Leadership","Blizzard",
			"Intimidate","SandStorm","AcidRain","Radiant","Tamer","Guards","Necromancer","Infestation","Piercing","Doctor","Boom","Venom","Ice-Freeze","Lightning","Multi-Arrow","Pull","Vampire",
			"Life-Steal","Double-Damage","SlowMo","Blindness","Viper","Fast-Turn","Light-Weight","Confusion","Disarmer","Execute","Headless", "Inquisitive","Nutrition","Obliterate","Paralyze",
			"Skill-Swipe","Snare","Trap","Wither","Rage","Blessed", "FeedMe","Dizzy","Berserk","Cursed","Rekt", "Decapitation","Telepathy", "Haste", "Oxygenate","Auto-Smelt", "Experience","Blast",
			"Furnace","HellForged" };
	@EventHandler
	public void onSend(PlayerCommandPreprocessEvent e) {
		
		//Checks the command to see if it's one of the lore commands of SimpleRename
		if (e.getMessage().contains("/lore") || e.getMessage().contains("/relore") || e.getMessage().contains("/setlore") || e.getMessage().contains("/addlore")|| e.getMessage().contains("/removelore")) {
			
			//If a set has the current event's player in it meaning his timer is running. exit from the event. else run it
			if (timerSet.contains(e.getPlayer())) {
				timerSet.remove(e.getPlayer()); return; //Since the player is in the timerset and the command is used, He's therefore removed now so he gets warning for the next item
			}
			ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
			if(item.hasItemMeta()) {
				if(!item.getItemMeta().hasLore()) return;
			} else return;
			
			//nested for loop that checks each lore on item for each enabled custom enchant
			outerloop: 
			for(String enchant : Enchants) {
				for(String lore : e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore()) {
					//Checks if the current enchant on Enchants Array matches with the current lore on the Item
					if (lore.contains(enchant)) {
						//Cancel the event
						e.setCancelled(true);
						
						//Sends the warning message from the config file
						e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("warning_message")));
						
						//Adds Player to the timer set
						if (!timerSet.contains(e.getPlayer())) {
							timerSet.add(e.getPlayer());
						}
						
						//gets time from config file
						int t = plugin.getConfig().getInt("time");
						//Sets off the timer
						Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
							public void run() {
								//removes the player from the timer set after the tiemr runs out
								
								timerSet.remove(e.getPlayer());
								
							}
						}, t);
						//As one of the lore matched we don't need to check for more, so breaks out of for loop
						break outerloop;
					}
				}
			}
			//returns out of the method as both for loops ended running
			return;
		}
		
	}

}
