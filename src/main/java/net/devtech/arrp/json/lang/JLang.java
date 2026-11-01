package net.devtech.arrp.json.lang;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class JLang implements Cloneable {
    private final Map<String, String> lang = new HashMap<>();

    /**
     * @see #lang()
     */
    public JLang() {
    }

    public static JLang lang() {
        return new JLang();
    }

    /**
     * Adds a custom entry to the lang file. (deprecated: renamed to a more intuitive name)
     *
     * @param in  the translation string
     * @param out the in-game name of the object
     * @return the file with the new entry.
     * @deprecated use {@link #entry(String, String)} instead.
     */
    @Deprecated
    public JLang translate(String in, String out) {
        this.lang.put(in, out);
        return this;
    }

    private <T> JLang object(Registry<T> registry, String str, T t, String name) {
        return this.object(str,
                Objects.requireNonNull(registry.getKey(t), "register your item before calling this"),
                name);
    }

    private JLang object(String type, ResourceLocation identifier, String translation) {
        this.lang.put(type + '.' + identifier.getNamespace() + '.' + identifier.getPath(), translation);
        return this;
    }

    public JLang entry(String entry, String name) {
        this.lang.put(entry, name);
        return this;
    }


    /**
     * adds a translation key for an item, respects {@link Item#getDescriptionId()}
     */
    public JLang itemRespect(Item item, String name) {
        this.lang.put(item.getDescriptionId(), name);
        return this;
    }

    public JLang item(ItemStack stack, String name) {
        this.lang.put(stack.getDescriptionId(), name);
        return this;
    }

    /**
     * @see JLang#itemRespect(Item, String) uses the {@link Item#getDescriptionId()}}
     */
    @Deprecated
    public JLang item(Item item, String name) {
        return this.object(BuiltInRegistries.ITEM, "item", item, name);
    }

    /**
     * adds a translation key for an block, respects {@link Block#getDescriptionId()}
     */
    public JLang blockRespect(Block block, String name) {
        this.lang.put(block.getDescriptionId(), name);
        return this;
    }

    /**
     * @see JLang#blockRespect(Block, String) uses the {@link Block#getDescriptionId()}}
     */
    @Deprecated
    public JLang block(Block block, String name) {
        return this.object(BuiltInRegistries.BLOCK, "block", block, name);
    }

    public JLang fluid(Fluid fluid, String name) {
        return this.object(BuiltInRegistries.FLUID, "fluid", fluid, name);
    }

    /**
     * adds a translation key for an entity, respects {@link EntityType#getDescriptionId()}
     */
    public JLang entityRespect(EntityType<?> type, String name) {
        this.lang.put(type.getDescriptionId(), name);
        return this;
    }

    /**
     * @see JLang#entityRespect(EntityType, String) uses the {@link EntityType#getDescriptionId()}}
     */
    @Deprecated
    public JLang entity(EntityType<?> type, String name) {
        return this.object(BuiltInRegistries.ENTITY_TYPE, "entity_type", type, name);
    }



    public JLang item(ResourceLocation item, String name) {
        return this.object("item", item, name);
    }

    public JLang block(ResourceLocation block, String name) {
        return this.object("block", block, name);
    }

    public JLang fluid(ResourceLocation id, String name) {
        return this.object("fluid", id, name);
    }

    public JLang entity(ResourceLocation id, String name) {
        return this.object("entity_type", id, name);
    }

    public JLang enchantment(ResourceLocation id, String name) {
        return this.object("enchantment", id, name);
    }

    public JLang itemGroup(ResourceLocation id, String name) {
        return this.object("itemGroup", id, name);
    }

    public JLang sound(ResourceLocation id, String name) {
        return this.object("sound_event", id, name);
    }

    public JLang status(ResourceLocation id, String name) {
        return this.object("mob_effect", id, name);
    }

    /**
     * Like {@link JLang#allPotion}, but it adds in the prefixes automatically.
     */
    public JLang allPotionOf(ResourceLocation id, String effectName) {
        this.allPotion(id,
                "Potion of " + effectName,
                "Splash Potion of " + effectName,
                "Lingering Potion of " + effectName,
                "Tipped Arrow of " + effectName);
        return this;
    }

    public JLang allPotion(ResourceLocation id,
                           String drinkablePotionName,
                           String splashPotionName,
                           String lingeringPotionName,
                           String tippedArrowName) {
        return this.drinkablePotion(id, drinkablePotionName).splashPotion(id, splashPotionName)
                .lingeringPotion(id, lingeringPotionName).tippedArrow(id, tippedArrowName);
    }

    public JLang tippedArrow(ResourceLocation id, String name) {
        this.lang.put("item.minecraft.tipped_arrow.effect." + id.getPath(), name);
        return this;
    }

    public JLang lingeringPotion(ResourceLocation id, String name) {
        this.lang.put("item.minecraft.lingering_potion.effect." + id.getPath(), name);
        return this;
    }

    public JLang splashPotion(ResourceLocation id, String name) {
        this.lang.put("item.minecraft.splash_potion.effect." + id.getPath(), name);
        return this;
    }

    public JLang drinkablePotion(ResourceLocation id, String name) {
        this.lang.put("item.minecraft.potion.effect." + id.getPath(), "Potion of " + name);
        return this;
    }

    /**
     * Like {@link JLang#drinkablePotion}, but it adds in the "Potion of" automatically.
     */
    public JLang drinkablePotionOf(ResourceLocation id, String effectName) {
        this.lang.put("item.minecraft.potion.effect." + id.getPath(), "Potion of " + effectName);
        return this;
    }

    /**
     * Like {@link JLang#splashPotion}, but it adds in the "Splash Potion of" automatically.
     */
    public JLang splashPotionOf(ResourceLocation id, String effectName) {
        this.lang.put("item.minecraft.splash_potion.effect." + id.getPath(), "Splash Potion of " + effectName);
        return this;
    }

    /**
     * Like {@link JLang#lingeringPotion}, but it adds in the "Lingering Potion of" automatically.
     */
    public JLang lingeringPotionOf(ResourceLocation id, String effectName) {
        this.lang.put("item.minecraft.lingering_potion.effect." + id.getPath(), "Lingering Potion of " + effectName);
        return this;
    }

    /**
     * Like {@link JLang#tippedArrow}, but it adds in the "Tipped Arrow of" automatically.
     */
    public JLang tippedArrowOf(ResourceLocation id, String effectName) {
        this.lang.put("item.minecraft.tipped_arrow.effect." + id.getPath(), "Tipped Arrow of " + effectName);
        return this;
    }

    public JLang biome(ResourceLocation id, String name) {
        return this.object("biome", id, name);
    }

    public Map<String, String> getLang() {
        return this.lang;
    }

    @Override
    public JLang clone() {
        try {
            return (JLang) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }
}
