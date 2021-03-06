package mcpecommander.mobultion.entity.entities.spiders;

import mcpecommander.mobultion.Reference;
import mcpecommander.mobultion.entity.animation.AnimationLookAt;
import mcpecommander.mobultion.entity.entityAI.spidersAI.EntityAIHypnoBallAttack;
import mcpecommander.mobultion.entity.entityAI.spidersAI.EntityAISpiderAttack;
import mcpecommander.mobultion.entity.entityAI.spidersAI.EntityAISpiderTarget;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityHypnoSpider extends EntityAnimatedSpider{
	
	static {
    	EntityHypnoSpider.animHandler.addAnim(Reference.MOD_ID, "spider_move", "hypno_spider", false);
    	EntityHypnoSpider.animHandler.addAnim(Reference.MOD_ID, "hypno_rotate", "hypno_spider", false);
    	EntityHypnoSpider.animHandler.addAnim(Reference.MOD_ID, "lookat", new AnimationLookAt("Head"));
	}

	public EntityHypnoSpider(World worldIn) {
		super(worldIn);
		this.setSize(1.4f, 0.9f);
	}

	private boolean isMoving(EntityLiving entity){
    	if(!entity.getMoveHelper().isUpdating()){
    		return Math.abs(entity.moveForward) > 0.01f || Math.abs(entity.moveStrafing) > 0.1f || Math.abs(entity.moveVertical) > 0.1f;
    	}return true;
    }
	
	@Override
    protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(5, new EntityAISpiderAttack(this));
        this.tasks.addTask(4, new EntityAIHypnoBallAttack(this));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAISpiderTarget(this, EntityPlayer.class));
        this.targetTasks.addTask(3, new EntityAISpiderTarget(this, EntityIronGolem.class));
    }
    
    @Override
    public double getMountedYOffset()
    {
        return (double)(this.height * 0.6F);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    }
    
    @Override
    public void onLivingUpdate() {
    	super.onLivingUpdate();
    	if(this.isWorldRemote()){
    		if(!this.getAnimationHandler().isAnimationActive(Reference.MOD_ID, "lookat", this)){
    			this.getAnimationHandler().startAnimation(Reference.MOD_ID, "lookat", 0, this);
    		}
    		if(!this.getAnimationHandler().isAnimationActive(Reference.MOD_ID, "spider_move", this) && this.isMoving()){
    			this.getAnimationHandler().startAnimation(Reference.MOD_ID, "spider_move", 0, this);
    		}
    		if(!this.getAnimationHandler().isAnimationActive(Reference.MOD_ID, "hypno_rotate", this)){
    			this.getAnimationHandler().startAnimation(Reference.MOD_ID, "hypno_rotate", 0, this);
    		}
    	}
    }

    @Override
    public float getEyeHeight()
    {
        return 0.65F;
    }
    
}
