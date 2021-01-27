package io.freeze_dolphin.sgript_master;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import io.izzel.taboolib.loader.Plugin;
import io.izzel.taboolib.loader.PluginBase;
import io.izzel.taboolib.module.config.TConfig;
import io.izzel.taboolib.module.dependency.Dependency;
import io.izzel.taboolib.module.inject.TInject;

@Dependency(maven = "org.codehaus.groovy:groovy-all:3.0.7")
public class SgriptMaster extends Plugin {

	@TInject("settings.yml")
	private TConfig conf;

	@Override
	public void onEnable() {

		InputStream is = getPlugin().getClass().getClassLoader().getResourceAsStream("/scripts");
		File f = new File(getPlugin().getDataFolder().getPath() + File.separator + "scripts");

		try {
			if (!f.exists()) {
				f.mkdirs();
			}

			FileOutputStream fos = new FileOutputStream(f);

			int ch = 0;
			while ((ch = is.read()) != -1) {
				fos.write(ch);
			}

			fos.close();
			is.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			getPlugin().getLogger().severe("Could not create plugin data, self-disabling...");
			PluginBase.setDisabled(true);
		}

		super.onEnable();
	}

}
