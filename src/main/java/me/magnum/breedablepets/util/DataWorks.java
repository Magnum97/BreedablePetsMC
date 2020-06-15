package me.magnum.breedablepets.util;

import de.leonhard.storage.sections.FlatFileSection;
import lombok.NoArgsConstructor;
import me.magnum.Breedable;
import org.bukkit.Material;

import java.util.HashMap;

@NoArgsConstructor
public class DataWorks {

	public HashMap <Material, Integer> getMaterialsMap () throws InvalidMaterialException {
		FlatFileSection section = Breedable.getPlugin().getCfg().getSection("parrot.egg-lay.modifiers");
		HashMap <Material, Integer> map = new HashMap <>();

		for (String material : section.keySet()) {
			Material mat;
			try {
				mat = Material.valueOf(material);
				int value = section.getInt(material);
				map.put(mat, value);
			}
			catch (IllegalArgumentException e) {
				throw new InvalidMaterialException(material, "Invalid Material in settings.yml", e);
			}
		}
		return map;
	}

	public void save () { // Future use
	}
}


