package mod.azure.doom.entity.tierambient;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.util.registry.DoomEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GoreNestEntity extends DemonEntity implements GeoEntity {

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	public int spawnTimer = 0;

	public GoreNestEntity(EntityType<? extends GoreNestEntity> entityType, World worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			if (this.dead || this.getHealth() < 0.01 || this.isDead())
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			event.getController().setAnimationSpeed(0.25);
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	public void takeKnockback(double strength, double x, double z) {
		super.takeKnockback(0, 0, 0);
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected void tickCramming() {
	}

	public static boolean spawning(EntityType<GoreNestEntity> p_223337_0_, World p_223337_1_, SpawnReason reason,
			BlockPos p_223337_3_, Random p_223337_4_) {
		return p_223337_1_.getDifficulty() != Difficulty.PEACEFUL;
	}

	@Override
	protected void updatePostDeath() {
		++this.deathTime;
		if (this.deathTime == 80) {
			this.remove(Entity.RemovalReason.KILLED);
			this.dropXp();
		}
	}

	@Override
	protected void applyDamage(DamageSource source, float amount) {
		if (source == DamageSource.OUT_OF_WORLD)
			this.remove(Entity.RemovalReason.KILLED);

		if (!(source.getSource() instanceof PlayerEntity))
			this.setHealth(5.0F);

		this.remove(Entity.RemovalReason.KILLED);
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, DoomConfig.gorenest_health)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0D).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0D);
	}

	@Override
	public EntityData initialize(ServerWorldAccess serverWorldAccess, LocalDifficulty difficulty,
			SpawnReason spawnReason, EntityData entityData, NbtCompound entityTag) {
		entityData = super.initialize(serverWorldAccess, difficulty, spawnReason, entityData, entityTag);
		return entityData;
	}

	@Override
	public void tickMovement() {
		if (this.world.isClient) {
			this.world.addParticle(DustParticleEffect.DEFAULT, this.getParticleX(0.5D), this.getRandomBodyY() - 0.25D,
					this.getParticleZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(),
					(this.random.nextDouble() - 0.5D) * 2.0D);
			this.world.addParticle(ParticleTypes.SOUL, this.getParticleX(0.2D), this.getRandomBodyY(),
					this.getParticleZ(0.5D), 0.0D, 0D, 0D);
		}
		++this.spawnTimer;
		final Box aabb = new Box(this.getBlockPos()).expand(64D);
		int i = this.world.getEntitiesByType(TypeFilter.instanceOf(DemonEntity.class), aabb, Entity::isAlive).size();
		if (this.spawnTimer == 800 && i <= 15) {
			this.spawnWave();
		}
		if (this.spawnTimer >= 810)
			this.spawnTimer = 0;
		super.tickMovement();
	}

	public void spawnWave() {
		List<EntityType<?>> givenList = Arrays.asList(DoomEntities.HELLKNIGHT, DoomEntities.POSSESSEDSCIENTIST,
				DoomEntities.IMP, DoomEntities.PINKY, DoomEntities.CACODEMON, DoomEntities.CHAINGUNNER,
				DoomEntities.GARGOYLE, DoomEntities.HELLKNIGHT2016, DoomEntities.LOST_SOUL,
				DoomEntities.POSSESSEDSOLDIER, DoomEntities.SHOTGUNGUY, DoomEntities.UNWILLING, DoomEntities.ZOMBIEMAN,
				DoomEntities.ARACHNOTRON, DoomEntities.ARCHVILE, DoomEntities.MECHAZOMBIE, DoomEntities.PAIN,
				DoomEntities.MANCUBUS);
		int r = this.random.nextBetween(-3, 3);

		for (int k = 1; k < 5; ++k) {
			for (int i = 0; i < 1; i++) {
				int randomIndex = this.random.nextInt(givenList.size());
				EntityType<?> randomElement = givenList.get(randomIndex);
				Entity fireballentity = randomElement.create(world);
				fireballentity.refreshPositionAndAngles(this.getX() + r, this.getY() + 0.5D, this.getZ() + r, 0, 0);
				world.spawnEntity(fireballentity);
			}
		}
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance) {
		return true;
	}
}