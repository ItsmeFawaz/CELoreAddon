package me.bottleofglass.CELoreAddon;

import org.bukkit.plugin.java.JavaPlugin;

import me.bottleofglass.CELoreAddon.Listeners.CommandListener;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		new CommandListener(this);
		saveDefaultConfig();
		CommandListener.timerSet.clear();
	}
	public void onDisable() {
		CommandListener.timerSet.clear();
	}
	

}
