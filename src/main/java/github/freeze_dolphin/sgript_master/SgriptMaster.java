package github.freeze_dolphin.sgript_master;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SgriptMaster extends JavaPlugin {
	
	public static Plugin instance;
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		try {
			Class.forName("org.codehaus.groovy.ant.Groovy");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			severe("Groovy runtime does not exist!");
			this.setEnabled(false);
		}
		
	}
	
	public static void severe(String msg) {
		instance.getLogger().severe(msg);
	}

	public static void warn(String msg) {
		instance.getLogger().warning(msg);
	}

	public static void info(String msg) {
		instance.getLogger().info(msg);
	}
	
}
