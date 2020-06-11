package me.magnum.breedablepets.util;

import de.leonhard.storage.sections.FlatFileSection;
import lombok.NoArgsConstructor;
import me.magnum.Breedable;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.logging.Logger;

@NoArgsConstructor
public class DataWorks {

	public HashMap <Material, Integer> getMaterialsMap () throws InvalidMaterialException {
		FlatFileSection section = Breedable.getPlugin().getCfg().getSection("parrot.egg-lay.modifiers");
		HashMap <Material, Integer> map = new HashMap <>();

		for (String material : section.keySet()) {
			Logger log = Breedable.getPlugin().getLogger();
			Material mat = null;
			try {
				mat = Material.valueOf(material);
				log.info("Found " + material + " in settings.yml");
				int value = section.getInt(material);
				map.put(mat, value);
			}
			catch (IllegalArgumentException e) {
				throw new InvalidMaterialException(material, "Invalid Material in settings.yml", e);
//				log.severe("Invalid material name in settings.yml: " + material);
//				log.severe("Material names can be found at https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html");
//				e.printStackTrace();
			}
		}
		return map;
	}

	public void save () { // Future use
	}

/*
	private static class InvalidMaterialException extends Throwable {

		private String material;

		public InvalidMaterialException (String material, String message, Throwable cause) {
			super(message, cause);
			this.getMaterial(material);
		}

		public String getMaterial (String material) {
			return material;
		}

		public void setMaterial (String material) {
			this.material = material;
		}
	}
*/
}


