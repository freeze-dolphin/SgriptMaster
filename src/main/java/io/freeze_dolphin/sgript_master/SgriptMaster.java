package io.freeze_dolphin.sgript_master;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;

import io.izzel.taboolib.loader.Plugin;
import io.izzel.taboolib.module.config.TConfig;
import io.izzel.taboolib.module.dependency.Dependency;
import io.izzel.taboolib.module.inject.TInject;

import org.bukkit.Bukkit;

@Dependency(maven = "org.codehaus.groovy:groovy-all:3.0.7")
public class SgriptMaster extends Plugin {

    @TInject("settings.yml")
    private TConfig conf;

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void onEnable() {

        InputStream is = getPlugin().getClass().getClassLoader().getResourceAsStream("/scripts");
        File f = new File(getPlugin().getDataFolder().getPath() + File.separator + "scripts");

        try {
            if (!f.exists()) {
                f.mkdirs();

                try (FileOutputStream fos = new FileOutputStream(f)) {
                    byte[] b = new byte[is.available()];
                    while (is.read(b) != -1) {
                        fos.write(b);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            getPlugin().getLogger().severe("Could not create plugin data, self-disabling...");
            Bukkit.getPluginManager().disablePlugin(Plugin.getInstance().getPlugin());
        }

        super.onEnable();
    }

}
