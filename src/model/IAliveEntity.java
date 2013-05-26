package model;

public interface IAliveEntity extends IEntity{

	/**
	 * @return 	the current health value of this alive entity
	 */
	public int getHp();
	
	/**
	 * @param hp 	the new health of this alive entity
	 */
	public void setHp(int hp);
	
	/**
	 * @return 	the maximal health value that this alive entity can have
	 */
	public int getMaxHp();
	
	/**
	 * Hurts this alive entity with the given value.
	 * @param hpDecrement	the damage that will be done to this alive entity
	 */
	public void hurt(int hpDecrement);
}
