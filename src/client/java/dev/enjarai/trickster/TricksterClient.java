package dev.enjarai.trickster;

import dev.enjarai.trickster.block.ModBlocks;
import dev.enjarai.trickster.cca.DisguiseComponent;
import dev.enjarai.trickster.render.fleck.FleckRenderer;
import dev.enjarai.trickster.cca.ModEntityComponents;
import dev.enjarai.trickster.item.ModItems;
import dev.enjarai.trickster.item.ScrollAndQuillItem;
import dev.enjarai.trickster.net.IsEditingScrollPacket;
import dev.enjarai.trickster.net.ModClientNetworking;
import dev.enjarai.trickster.net.ModNetworking;
import dev.enjarai.trickster.particle.ModParticles;
import dev.enjarai.trickster.particle.ProtectedBlockParticle;
import dev.enjarai.trickster.particle.SpellParticle;
import dev.enjarai.trickster.render.*;
import dev.enjarai.trickster.screen.ModHandledScreens;
import dev.enjarai.trickster.screen.ScrollAndQuillScreen;
import dev.enjarai.trickster.screen.SignScrollScreen;
import dev.enjarai.trickster.screen.owo.GlyphComponent;
import dev.enjarai.trickster.screen.owo.ItemTagComponent;
import dev.enjarai.trickster.screen.owo.SpellPreviewComponent;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.owo.ui.parsing.UIParsing;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.world.ClientWorld;

public class TricksterClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ScrollAndQuillItem.screenOpener = (text, hand) -> {
			MinecraftClient.getInstance().setScreen(new SignScrollScreen(text, hand));
		};

		FleckRenderer.register();

		ModHandledScreens.register();
		ModKeyBindings.register();
		ModClientNetworking.register();

		BlockEntityRendererFactories.register(ModBlocks.SPELL_CIRCLE_ENTITY, SpellCircleBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(ModBlocks.SCROLL_SHELF_ENTITY, ScrollShelfBlockEntityRenderer::new);

		UIParsing.registerFactory(Trickster.id("glyph"), GlyphComponent::parseTrick);
		UIParsing.registerFactory(Trickster.id("pattern"), GlyphComponent::parseList);
		UIParsing.registerFactory(Trickster.id("spell-preview"), SpellPreviewComponent::parse);
		UIParsing.registerFactory(Trickster.id("item-tag"), ItemTagComponent::parse);

		ParticleFactoryRegistry.getInstance().register(ModParticles.PROTECTED_BLOCK, ProtectedBlockParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.SPELL, SpellParticle.Factory::new);

		AccessoriesRendererRegistry.registerRenderer(ModItems.TOP_HAT, HoldableHatRenderer::new);
		AccessoriesRendererRegistry.registerRenderer(ModItems.WITCH_HAT, HoldableHatRenderer::new);
		AccessoriesRendererRegistry.registerRenderer(ModItems.FEZ, HoldableHatRenderer::new);

		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SPELL_RESONATOR, RenderLayer.getCutout());

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null) {
				var editing = client.currentScreen instanceof ScrollAndQuillScreen;
				var serverEditing = ModEntityComponents.IS_EDITING_SCROLL.get(client.player).isEditing();
				if (editing != serverEditing) {
					ModNetworking.CHANNEL.clientHandle().send(new IsEditingScrollPacket(editing));
				}
			}
		});

		WorldRenderEvents.AFTER_ENTITIES.register(FlecksRenderer::render);

		HudRenderCallback.EVENT.register(BarsRenderer::render);
		HudRenderCallback.EVENT.register(CircleErrorRenderer::render);

		EntityModelLayerRegistry.registerModelLayer(ScrollShelfBlockEntityRenderer.MODEL_LAYER, ScrollShelfBlockEntityRenderer::getTexturedModelData);

		DisguiseComponent.entityAdder = (world, entity) -> ((ClientWorld) world).addEntity(entity);
	}
}
