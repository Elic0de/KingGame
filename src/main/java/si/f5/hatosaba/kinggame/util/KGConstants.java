package si.f5.hatosaba.kinggame.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import si.f5.hatosaba.kinggame.KingGame;

public class KGConstants {

    public static ItemStack LANDMINES = ItemBuilder.of(Material.CREEPER_HEAD)
            .name("地雷")
            .build();

    public static ItemStack CHEST = ItemBuilder.of(Material.CHEST)
            .name("!")
            .build();

    public static ItemStack QUARTZ = ItemBuilder.of(Material.QUARTZ).build();

    public static ItemStack SHIELD = ItemBuilder.of(Material.SHIELD).build();

    public static ItemStack IRON_SWORD = ItemBuilder.of(Material.IRON_SWORD).build();

    public static ItemStack CHAT_HELMET = ItemBuilder.of(Material.DIAMOND_HELMET).build();

    public static ItemStack CROSSBOW = ItemBuilder.of(Material.CROSSBOW).build();

    public static ItemStack TOTEM = ItemBuilder.of(Material.TOTEM_OF_UNDYING).build();

    public static ItemStack ANESTHESIA_ARROW = ItemBuilder.of(Material.ARROW).build();

    public static ItemStack INV_POTION () {
        ItemStack potion = ItemBuilder.of(Material.POTION).build();
        PotionMeta m = (PotionMeta) potion.getItemMeta();
        m.addCustomEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 30, 0), true);
        return potion;
    }

    public static void initRecipes(){
        registerQuartzRecipe();
    }

    private static void registerQuartzRecipe(){
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(KingGame.getInstance(), "quartz"), CHEST)
                .shape("IIG","IRG","IIG")
                .setIngredient('I', Material.QUARTZ)
                .setIngredient('I', Material.QUARTZ)
                .setIngredient('R', Material.QUARTZ)
                .setIngredient('I', Material.QUARTZ);
        Bukkit.addRecipe(shapedRecipe);
    }


}