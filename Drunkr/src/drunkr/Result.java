package drunkr;

import com.google.appengine.api.datastore.Entity;

public class Result {

	private int weight;
	private Entity e;
	
	public Result(Entity e)
	{
		this.e = e;
		this.weight = 0;
	}
	public void incrementWeight()
	{
		this.weight++;
	}
	public int getWeight()
	{
		return this.weight;
	}
	public Entity getEntity()
	{
		return this.e;
	}
	/* Override contains for ArrayList to compare the Entity for 
	 * existence. Entity uses the Keys of each Entity to do this
	 * comparison already.
	 */
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Entity)
		{
			Entity candidate = (Entity) o;
			return this.equals(candidate);
		}
		
		return false;
	}
	
}
