package eu.horyzon.driven.core.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import eu.horyzon.driven.core.DrivenPlugin;

public abstract class ConfigAdapter<C> {
	protected final DrivenPlugin plugin;
	protected final File file;

	public ConfigAdapter(DrivenPlugin plugin, String name) {
		this(plugin, new File(plugin.getDataFolder(), name));
	}

	public ConfigAdapter(DrivenPlugin plugin, File file) {
		this.plugin = plugin;
		this.file = file;
		reload();
	}

	public void load() throws Exception {
		if (!file.isFile())
			try (InputStream in = plugin.getResource(file.getName())) {
				Files.copy(in, file.toPath());
			} catch (IOException e) {
				if (file.getParentFile() != null)
					file.getParentFile().mkdirs();

				file.createNewFile();
			}

		getKeys(null, new ArrayList<>()).forEach((section) -> {
			set(section, null);
		});
	}

	protected abstract void set(String section, Object value);

	public boolean loadSafety() {
		try {
			load();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			plugin.getLogger().log(Level.WARNING, "I/O error while loading the file \"{0}\". Is the file in use?", file.getName());
		} catch (Exception e) {
			e.printStackTrace();
			plugin.getLogger().log(Level.WARNING, "Invalid YAML configuration for the file \"{0}\". Please look at the error above, or use an online YAML parser (google is your friend).", file.getName());
		}

		return false;
	}

	public boolean exist() {
		return file.exists() && file.isFile();
	}

	public abstract void save() throws IOException;

	public boolean saveSafety() {
		try {
			save();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			plugin.getLogger().log(Level.WARNING, "I/O error while saving the file \"{0}\". Is the file in use?", file.getName());
		}

		return false;
	}

	public DrivenPlugin getPlugin() {
		return plugin;
	}

	public abstract void reload();

	public abstract C getSection(String path);

	public abstract String getString(String path, String def);

	public abstract int getInteger(String path, int def);

	public abstract boolean getBoolean(String path, boolean def);

	public abstract List<String> getStringList(String path, List<String> def);

	public abstract List<String> getKeys(String path, List<String> def);

	public abstract Map<String, String> getStringMap(String path, Map<String, String> def);
}