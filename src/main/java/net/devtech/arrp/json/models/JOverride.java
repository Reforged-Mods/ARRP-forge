package net.devtech.arrp.json.models;

import net.devtech.arrp.json.loot.JCondition;
import net.devtech.arrp.util.BaseClonable;
import net.minecraft.resources.ResourceLocation;

public class JOverride extends BaseClonable<JOverride> {
	public final JCondition predicate;
	public final String model;

	/**
	 * @see JModel#override(JCondition, ResourceLocation)
	 */
	public JOverride(JCondition condition, String model) {
		this.predicate = condition;
		this.model = model;
	}
}
