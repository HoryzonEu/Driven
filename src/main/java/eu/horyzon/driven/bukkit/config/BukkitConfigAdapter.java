package eu.horyzon.driven.bukkit.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import eu.horyzon.driven.core.DrivenPlugin;
import eu.horyzon.driven.core.config.ConfigAdapter;

public class BukkitConfigAdapter extends ConfigAdapter<ConfigurationSection> {
	private YamlConfiguration configuration;

	public BukkitConfigAdapter(DrivenPlugin plugin, File file) {
		super(plugin, file);
		reload();
	}

	@Override
	public void reload() {
		configuration = YamlConfiguration.loadConfiguration(file);
	}

	@Override
	public String getString(String path, String def) {
		return configuration.getString(path, def);
	}

	@Override
	public int getInteger(String path, int def) {
		return configuration.getInt(path, def);
	}

	@Override
	public boolean getBoolean(String path, boolean def) {
		return configuration.getBoolean(path, def);
	}

	@Override
	public List<String> getStringList(String path, List<String> def) {
		List<String> list = configuration.getStringList(path);
		return list == null ? def : list;
	}

	@Override
	public List<String> getKeys(String path, List<String> def) {
		ConfigurationSection section = getSection(path);
		if (section == null)
			return def;

		Set<String> keys = section.getKeys(false);
		return keys == null ? def : new ArrayList<>(keys);
	}

	@Override
	public Map<String, String> getStringMap(String path, Map<String, String> def) {
		Map<String, String> map = new HashMap<>();
		ConfigurationSection section = getSection(path);
		if (section == null)
			return def;

		for (String key : section.getKeys(false))
			map.put(key, section.getString(key));

		return map;
	}

	@Override
	public DrivenPlugin getPlugin() {
		return plugin;
	}

	@Override
	public ConfigurationSection getSection(String path) {
		return configuration.getConfigurationSection(path);
	}

	@Override
	public void save() throws IOException {
		configuration.save(file);
	}

	@Override
	public void load() throws Exception {
		super.load();
		configuration.load(file);
	}

	@Override
	protected void set(String section, Object value) {
		configuration.set(section, value);
	}
}