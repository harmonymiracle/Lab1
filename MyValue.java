package lab_one;

import java.lang.*;
import java.util.*;

/*
 * first modify in myvalue.java, branch : master
 * 
 * B1 first modify
 * C4 first modify
 */
public class MyValue  implements Cloneable {
	
	private HashMap<String, Integer> edge_to_weight;

	private Integer outDegree;
	
	
	
	public MyValue(String edgeEnd)
	{
		edge_to_weight = new HashMap<String, Integer>();
		edge_to_weight.put(edgeEnd, 1);
		outDegree = 1;
	}
	
	
	
	public MyValue(String edgeEnd, Integer weight)
	{
		edge_to_weight = new HashMap<String, Integer>();
		edge_to_weight.put(edgeEnd,  weight);
		outDegree = 1;
	}
	
	public HashMap<String, Integer> GetEdgeInfo(){
		return this.edge_to_weight;
	}
	
	
	@Override
	public Object clone()
	{
		MyValue myvalue = null;
		try
		{
			myvalue = (MyValue)super.clone();
			myvalue.edge_to_weight = (HashMap<String, Integer>)edge_to_weight.clone();
		}
		catch(CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return myvalue;
	}
	
	public Integer GetOutDegree(){
		return outDegree;
	}
	
	public void RemoveEdge(String edgeEnd){
		
	}
	
	public void AddEdge(String edgeEnd){
		if(this.edge_to_weight.containsKey(edgeEnd)){
			SetWeight(edgeEnd,GetWeight(edgeEnd) +1) ;
		}
		else{
			this.edge_to_weight.put(edgeEnd, 1);
			outDegree ++;
		}
	}

	
	public Integer GetWeight(String key)
	{
		return edge_to_weight.get(key);
	}
	
	public void SetWeight(String key, Integer weight)
	{
		edge_to_weight.put(key, weight);
	}
	
	public Boolean ContainsKey(String key)
	{
		return edge_to_weight.containsKey(key);
	}
	
	
	public Iterator GetKeySetIterator()
	{
		return edge_to_weight.keySet().iterator();
	}


	
	
}
