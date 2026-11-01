package net.devtech.arrp.json.blockstate;

import net.minecraft.resources.ResourceLocation;

public class JBlockModel implements Cloneable {
	private final ResourceLocation model;
	private Integer x;
	private Integer y;
	private Boolean uvlock;
	private Integer weight;

	@Deprecated
	public JBlockModel(String model) {
		this(ResourceLocation.tryParse(model));
	}

	/**
	 * @see JState#model(String)
	 */
	public JBlockModel(ResourceLocation model) {
		this.model = model;
	}

	@Override
	public JBlockModel clone() {
		try {
			return (JBlockModel) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}

	public JBlockModel x(int x) {
		this.x = x;
		return this;
	}

	public JBlockModel y(int y) {
		this.y = y;
		return this;
	}

	public JBlockModel uvlock() {
		this.uvlock = true;
		return this;
	}

	public JBlockModel weight(int weight) {
		this.weight = weight;
		return this;
	}

}
