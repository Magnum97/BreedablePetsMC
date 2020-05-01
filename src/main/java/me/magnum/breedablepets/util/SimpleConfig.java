package me.magnum.breedablepets.util;
/*
 *  Base with comment and header features created by Log-out
 *  https://bukkit.org/threads/tut-custom-yaml-configurations-with-comments.142592/
 *  Updated by Magnum1997 to auto update config files from resources
 */

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.mineacademy.fo.Common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class SimpleConfig extends YamlConfiguration {

	private final JavaPlugin plugin;
	private int comments;
	private final SimpleConfigManager manager;

	private File file;
	private FileConfiguration config;
	private final boolean useDefaults;
	private final YamlConfiguration defaults;

	public SimpleConfig (InputStreamReader configStream, File configFile, int comments, boolean useDefaults, JavaPlugin plugin) {
		this.plugin = plugin;
		this.comments = comments;
		this.manager = new SimpleConfigManager(plugin);

		this.file = configFile;
		this.config = YamlConfiguration.loadConfiguration(configStream);
		this.useDefaults = useDefaults;

		if (useDefaults) {
			this.defaults = YamlConfiguration.loadConfiguration(new InputStreamReader(SimpleConfig.class.getResourceAsStream("/" + file.getName()), StandardCharsets.UTF_8));
			Objects.requireNonNull(defaults, "Could not find the default " + file.getName() + " in jar file.");
		}
		else
			this.defaults = null;
		this.file = extract(file.getName());


	}

	// Extract the file from your jar to the plugins/YourPlugin folder.
	// Does nothing if the file exists
	private File extract (String fileName) {
		File file = new File(plugin.getDataFolder(), fileName);

		if (file.exists())
			return file;
		createPath(fileName);

		if (defaults != null)
			try (InputStream inputStream = plugin.getResource(fileName)) {
				Objects.requireNonNull(inputStream, "File not found in archive: +" + fileName);
				Files.copy(inputStream, Paths.get(file.toURI()), StandardCopyOption.REPLACE_EXISTING);
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		return file;
	}


	private File createPath (String fileName) {
		final File datafolder = plugin.getDataFolder();
		final int lastIndex = fileName.lastIndexOf("/");
		final File directory = new File(datafolder, fileName.substring(0, Math.max(lastIndex, 0)));
		directory.mkdirs();
		final File destination = new File(datafolder, fileName);
		try {
			destination.createNewFile();
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("File creation failed: " + fileName);
		}

		return destination;
	}

	public Object get (String path) {
		return get(path, null);
	}


	/**
	 * Gets an unspecified value from your file, so you must cast it to your desired value (example: (boolean) get("disable.this.feature", true))
	 * The "def" is the default value, must be null since we use default values from your file in your .jar.
	 */
	@Override
	public Object get (String path, Object def) {
		if (defaults != null) {

			if (def != null && ! def.getClass().isPrimitive() && ! PrimitiveWrapper.isWrapperType(def.getClass()))
				throw new IllegalArgumentException("The default value must be null since we use defaults from file inside of the plugin! Path: " + path + ", default called: " + def);

			if (super.get(path, null) == null) {
				final Object defaultValue = defaults.get(path);
				Objects.requireNonNull(defaultValue, "Default " + file.getName() + " in your .jar lacks a key at '" + path + "' path");

				Common.log("&fUpdating &a" + file.getName() + "&f. Set '&b" + path + "&f' to '" + defaultValue + "'");
				set(path, defaultValue);
				saveConfig();
				reloadConfig();
			}
		}

/*
		//  prevent infinite loop due to how get works in the parent class
		final String m = new Throwable().getStackTrace()[1].getMethodName();

		// Add path prefix, but only when the default file doesn't exist
		if (defaults == null && pathPrefix != null && !m.equals("getConfigurationSection") && !m.equals("get"))
			path = pathPrefix + "." + path;
*/

		//		return super.get(path, null);
		return this.config.get(path, null);
	}

	public String getString (String path) {
		return this.config.getString(path);
	}

	public String getString (String path, String def) {
		return this.config.getString(path, def);
	}

	public int getInt (String path) {
		return this.config.getInt(path);
	}

	public int getInt (String path, int def) {
		return this.config.getInt(path, def);
	}

	public boolean getBoolean (String path) {
		return this.config.getBoolean(path);
	}

	public boolean getBoolean (String path, boolean def) {
		return this.config.getBoolean(path, def);
	}


/*
	public void createSection (String path) {
		this.config.createSection(path);
	}
*/

	public ConfigurationSection getConfigurationSection (String path) {
		return this.config.getConfigurationSection(path);
	}

	public double getDouble (String path) {
		return this.config.getDouble(path);
	}

	public double getDouble (String path, double def) {
		return this.config.getDouble(path, def);
	}

	public List <?> getList (String path) {
		return this.config.getList(path);
	}

	public List <?> getList (String path, List <?> def) {
		return this.config.getList(path, def);
	}

	public boolean contains (String path) {
		return this.config.contains(path);
	}

	public void removeKey (String path) {
		this.config.set(path, null);
	}

	public void set (String path, Object value) {
		this.config.set(path, value);
	}

	public void set (String path, Object value, String comment) {
		if (! this.config.contains(path)) {
			this.config.set(manager.getPluginName() + "_COMMENT_" + comments, " " + comment);
			comments++;
		}

		this.config.set(path, value);

	}

	public void set (String path, Object value, String[] comment) {

		for (String comm : comment) {

			if (! this.config.contains(path)) {
				this.config.set(manager.getPluginName() + "_COMMENT_" + comments, " " + comm);
				comments++;
			}

		}

		this.config.set(path, value);

	}

	public void setHeader (String[] header) {
		manager.setHeader(this.file, header);
		this.comments = header.length + 2;
		this.reloadConfig();
	}

	public void reloadConfig () {
		this.config = YamlConfiguration.loadConfiguration(manager.getConfigContent(file));
	}

	public void saveConfig () {
		String config = this.config.saveToString();
		manager.saveConfig(config, this.file);

	}

	public Set <String> getKeys () {
		return this.config.getKeys(false);
	}

	// A helper class
	private static final class PrimitiveWrapper {

		private static final Set <Class <?>> WRAPPER_TYPES = getWrapperTypes();

		private static boolean isWrapperType (Class <?> clazz) {
			return WRAPPER_TYPES.contains(clazz);
		}

		private static Set <Class <?>> getWrapperTypes () {
			final Set <Class <?>> ret = new HashSet <>();
			ret.add(Boolean.class);
			ret.add(Character.class);
			ret.add(Byte.class);
			ret.add(Short.class);
			ret.add(Integer.class);
			ret.add(Long.class);
			ret.add(Float.class);
			ret.add(Double.class);
			ret.add(Void.class);
			return ret;
		}
	}
}
