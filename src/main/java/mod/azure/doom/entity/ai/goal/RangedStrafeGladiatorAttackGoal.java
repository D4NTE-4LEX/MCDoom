package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;

import mod.azure.doom.entity.attack.AbstractDoubleRangedAttack;
import mod.azure.doom.entity.tierboss.GladiatorEntity;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RangedStrafeGladiatorAttackGoal extends Goal {
	private final GladiatorEntity entity;
	private int attackTime = -1;
	private int summonTime = -1;
	private AbstractDoubleRangedAttack attack;

	public RangedStrafeGladiatorAttackGoal(GladiatorEntity mob, AbstractDoubleRangedAttack attack) {
		this.entity = mob;
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		this.attack = attack;
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state
	 * necessary for execution in this method as well.
	 */
	public boolean canStart() {
		return this.entity.getTarget() != null;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean shouldContinue() {
		return this.canStart();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void start() {
		super.start();
		this.entity.setAttacking(true);
		this.entity.setSilent(false);
		this.entity.setTextureState(0);
		this.entity.world.createExplosion(this.entity, this.entity.getX(), this.entity.getY() + 5D, this.entity.getZ(),
				3.0F, false, World.ExplosionSourceType.BLOCK);
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by
	 * another one
	 */
	public void stop() {
		super.stop();
		this.entity.setAttacking(false);
		this.entity.setAttackingState(0);
		this.entity.setTextureState(0);
		this.attackTime = -1;
		this.entity.stopUsingItem();
		this.entity.setSilent(false);
		this.entity.world.createExplosion(this.entity, this.entity.getX(), this.entity.getY() + 5D, this.entity.getZ(),
				3.0F, false, World.ExplosionSourceType.BLOCK);
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void tick() {
		LivingEntity livingentity = this.entity.getTarget();
		if (livingentity != null) {
			boolean inLineOfSight = this.entity.getVisibilityCache().canSee(livingentity);
			this.attackTime++;
			this.summonTime++;
			this.entity.lookAtEntity(livingentity, 30.0F, 30.0F);
			if (inLineOfSight) {
				if (this.entity.distanceTo(livingentity) >= 3.0D) {
					if (this.entity.getDeathState() == 0 && this.summonTime > 10) {
						this.entity.getNavigation().stop();
						if (this.attackTime == 1) {
							this.entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 80, -20));
							this.entity.setAttackingState(1);
							this.entity.setTextureState(1);
						}
						if (this.attackTime == 20) {
							this.entity.setTextureState(2);
						}
						if (this.attackTime == 23) {
							AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(entity.world,
									entity.getX(), entity.getY(), entity.getZ());
							areaeffectcloudentity.setParticleType(ParticleTypes.SOUL_FIRE_FLAME);
							areaeffectcloudentity.setRadius(3.0F);
							areaeffectcloudentity.setDuration(10);
							areaeffectcloudentity.setPos(entity.getX(), entity.getY(), entity.getZ());
							entity.world.spawnEntity(areaeffectcloudentity);
							this.attack.shoot();

							boolean isInsideWaterBlock = entity.world.isWater(entity.getBlockPos());
							entity.spawnLightSource(this.entity, isInsideWaterBlock);
							this.entity.setTextureState(0);
							this.entity.getNavigation().startMovingTo(livingentity, 1.0);
							this.summonTime = -40;
						}
						if (this.attackTime == 48) {
							this.entity.setAttackingState(0);
							this.entity.setTextureState(0);
						}
						if (this.attackTime >= 83) {
							this.attackTime = -5;
							this.entity.setTextureState(0);
							this.entity.setAttackingState(0);
							this.entity.getNavigation().startMovingTo(livingentity, 1.0);
						}
					} else if (this.entity.getDeathState() == 1 && this.summonTime > 10) {
						if (this.attackTime == 1) {
							this.entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 13, -20));
							this.entity.getNavigation().stop();
							this.entity.setTextureState(0);
							this.entity.setAttackingState(0);
						}
						if (this.attackTime == 5) {
							this.entity.setAttackingState(4);
						}
						if (this.attackTime == 8) {
							this.attack.shoot2();

							boolean isInsideWaterBlock = entity.world.isWater(entity.getBlockPos());
							entity.spawnLightSource(this.entity, isInsideWaterBlock);
							this.summonTime = -8;
						}
						if (this.attackTime >= 13) {
							this.entity.getNavigation().startMovingTo(livingentity, 1.0);
							this.entity.setTextureState(0);
							this.entity.setAttackingState(0);
							this.attackTime = -5;
						}
					} else {
						this.attackTime = -25;
						this.entity.setTextureState(0);
						this.entity.setAttackingState(0);
						this.entity.getNavigation().startMovingTo(livingentity, 1.0);
					}
				} else {
					this.entity.getNavigation().startMovingTo(livingentity,
							this.entity.getDeathState() == 0 ? 1.0 : 1.25);
					if (this.attackTime == 1) {
						this.summonTime++;
						this.entity.setAttackingState(3);
						Vec3d vec3d = this.entity.getVelocity();
						Vec3d vec3d2 = new Vec3d(livingentity.getX() - this.entity.getX(), 0.0,
								livingentity.getZ() - this.entity.getZ());
						vec3d2 = vec3d2.normalize().multiply(0.4).add(vec3d.multiply(0.4));
						this.entity.setVelocity(vec3d2.x, 0.5F, vec3d2.z);
					}
					if (this.attackTime == 18) {
						this.entity.getNavigation().stop();
						AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(entity.world,
								entity.getX(), entity.getY(), entity.getZ());
						areaeffectcloudentity.setParticleType(ParticleTypes.SMOKE);
						areaeffectcloudentity.setRadius(3.0F);
						areaeffectcloudentity.setDuration(55);
						areaeffectcloudentity.setPos(entity.getX(), entity.getY(), entity.getZ());
						entity.world.spawnEntity(areaeffectcloudentity);
						this.entity.tryAttack(livingentity);
						livingentity.timeUntilRegen = 0;
					}
					if (this.attackTime == 19) {
						if (this.entity.getDeathState() == 1) {
							this.entity.tryAttack1(livingentity);
							livingentity.timeUntilRegen = 0;
						}
					}
					if (this.attackTime >= 25) {
						this.attackTime = -5;
						this.entity.setTextureState(0);
						this.entity.setAttackingState(0);
					}
				}
			}
		}

	}

	protected double getAttackReachSqr(LivingEntity attackTarget) {
		return (double) (this.entity.getWidth() * 2.0F * this.entity.getWidth() * 2.0F + attackTarget.getWidth());
	}
}
