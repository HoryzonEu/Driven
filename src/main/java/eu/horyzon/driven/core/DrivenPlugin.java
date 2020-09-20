package eu.horyzon.driven.core;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Logger;

import eu.horyzon.driven.core.command.Command;

public interface DrivenPlugin {

	public abstract void registerCommand(Command command);

	public abstract File getDataFolder();

	public abstract Logger getLogger();

	public abstract InputStream getResource(String fileName);
}