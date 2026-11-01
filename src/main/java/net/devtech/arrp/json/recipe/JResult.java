package net.devtech.arrp.json.recipe;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public class JResult {
	protected final String item;

	JResult(final String id) {
		this.item = id;
	}

	public static JResult item(final Item item) {
		return result(BuiltInRegistries.ITEM.getKey(item).toString());
	}

	public static JResult result(final String id) {
		return new JResult(id);
	}

	public static JStackedResult itemStack(final Item item, final int count) {
		return stackedResult(BuiltInRegistries.ITEM.getKey(item).toString(), count);
	}

	public static JStackedResult stackedResult(final String id, final int count) {
		final JStackedResult stackedResult = new JStackedResult(id);

		stackedResult.count = count;

		return stackedResult;
	}

	@Override
	protected JResult clone() {
		try {
			return (JResult) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}
}
