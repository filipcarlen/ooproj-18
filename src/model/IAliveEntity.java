package model;

public interface IAliveEntity extends IEntity{

	/**
	 * 
	 * @return The Current health point of the Alive Entity
	 */
	public int getHp();
	
	/**
	 * sets the health points of an entity
	 * @param hp 
	 */
	public void setHp(int hp);
	
	/**
	 * Hurts the entity
	 * @param hpDecrement
	 */
	public void hurt(int hpDecrement);
	
	/**
	 * 
	 * @return the maximal health point that the entity can have
	 */
	public int getMaxHp();
}
