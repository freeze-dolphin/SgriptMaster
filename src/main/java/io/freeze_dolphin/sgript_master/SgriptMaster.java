package io.freeze_dolphin.sgript_master;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;

public class SgriptMaster extends JavaPlugin {

	private Config config = new Config(this);
	private Plugin plug = this;

	@Override
	public void onEnable() {

		getLogger().info("Loading internal modules...");
		for (String s : config.getStringList("enabled-modules")) {
			try {
				Class<?> c = Class.forName("io.freeze_dolphin.arctic_dandelion.modules." + s);
				c.newInstance();
			} catch (ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
				getLogger().severe("Internal module '" + "io.freeze_dolphin.arctic_dandelion.modules." + s + "' is not found!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		getLogger().info("Loading groovy modules...");
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("groovy");
		Compilable compEngine = (Compilable) engine;
		String current = "";
		try {
			for (File f : new File(getDataFolder().getPath() + File.pathSeparator + "groovy-modules").listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".groovy") && !name.startsWith("-");
				}
			})) {
				current = f.getName();
				CompiledScript script = compEngine.compile(new FileReader(f));
				script.eval();
			}
		} catch (ScriptException se) {
			se.printStackTrace();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
			getLogger().severe("Groovy module '" + current + "' is not found!");
		}
	}

	public Config getC() {
		return config;
	}

	public Plugin getInstance() {
		return plug;
	}

}
