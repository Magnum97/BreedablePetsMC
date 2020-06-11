package me.magnum.breedablepets.util;

public class InvalidMaterialException extends Throwable {

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
