package de.stynxyxy.betterminecraft.enchantments.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class Testenchantment extends Enchantment {
    public Testenchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        pTarget.setGlowingTag(true);
        if (!pAttacker.getLevel().isClientSide) {
            ServerLevel world = (ServerLevel) pAttacker.getLevel();

            if (pTarget instanceof LivingEntity) {
                LivingEntity target = (LivingEntity) pTarget;

                target.addEffect(new MobEffectInstance(MobEffects.LEVITATION,pLevel*10,pLevel));

            }
        }
        super.doPostAttack(pAttacker, pTarget, pLevel);
    }
}
