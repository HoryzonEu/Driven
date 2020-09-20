package eu.horyzon.driven.bungee;

import java.io.InputStream;

import eu.horyzon.driven.core.DrivenPlugin;
import eu.horyzon.driven.core.command.Command;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeDriven extends Plugin implements DrivenPlugin {

	@Override
	public void onEnable() {

	}

	@Override
	public void registerCommand(Command command) {
		// TODO Auto-generated method stub

	}

	@Override
	public InputStream getResource(String fileName) {
		return getResourceAsStream(fileName);
	}
}