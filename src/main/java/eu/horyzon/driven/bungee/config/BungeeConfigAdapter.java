package eu.horyzon.driven.bungee.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import eu.horyzon.driven.core.DrivenPlugin;
import eu.horyzon.driven.core.config.ConfigAdapter;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class BungeeConfigAdapter extends ConfigAdapter<Configuration> {
	private Configuration configuration;

	public BungeeConfigAdapter(DrivenPlugin plugin, File file) {
		super(plugin, file);
	}

	@Override
	public void reload() {
		try {
			configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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
		return Optional.ofNullable(configuration.getStringList(path)).orElse(def);
	}

	@Override
	public List<String> getKeys(String path, List<String> def) {
		Configuration section = configuration.getSection(path);
		if (section == null)
			return def;

		return Optional.of((List<String>) new ArrayList<>(section.getKeys())).orElse(def);
	}

	@Override
	public Map<String, String> getStringMap(String path, Map<String, String> def) {
		Map<String, String> map = new HashMap<>();
		Configuration section = configuration.getSection(path);
		if (section == null)
			return def;

		for (String key : section.getKeys())
			map.put(key, section.get(key).toString());

		return map;
	}

	@Override
	public Configuration getSection(String path) {
		return configuration.getSection(path);
	}

	@Override
	public void save() throws IOException {
		ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
	}

	@Override
	public void load() throws Exception {
		super.load();
		configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
	}

	@Override
	protected void set(String section, Object value) {
		configuration.set(section, value);
	}
}