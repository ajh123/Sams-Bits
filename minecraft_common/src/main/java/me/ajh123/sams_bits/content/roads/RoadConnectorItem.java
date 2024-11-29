package me.ajh123.sams_bits.content.roads;

import me.ajh123.sams_bits.content.registry.ModComponents;
import me.ajh123.sams_bits.maths.Position;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class RoadConnectorItem extends Item {
    public RoadConnectorItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        LinkingComponent linking = stack.getOrDefault(ModComponents.LINKING_COMPONENT, null);
        if (linking != null) {
            if (linking.getSource() != null) {
                Position pos = linking.getSource();
                tooltip.add(Text.translatable("itemTooltip.sams_bits.linking", pos.getX(), pos.getY(), pos.getZ()).formatted(Formatting.GOLD));
            }
        }
    }
}
