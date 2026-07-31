package net.star.copperoverthrow.item;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.star.copperoverthrow.CopperOverthrow;
import net.star.copperoverthrow.item.custom.CopperChiselItem;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CopperOverthrow.MOD_ID);
    //Registering the Items into Neoforge, long list of all registries
    // "(CopperOverthrow.MOD_ID)" is telling under which MOD_ID Minecraft has to add the registries

    //Adding the item to the game without any properties YET, just adding the registry!
    public static final DeferredItem<Item> COPPER_NUGGET = ITEMS.register("copper_nugget",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> COPPER_CHISEL = ITEMS.register("copper_chisel",
            () -> new CopperChiselItem(new Item.Properties().durability(128).stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
    //Method to execute the registry in the MAIN
}
