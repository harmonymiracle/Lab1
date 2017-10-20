package lab_one;


import java.util.*;

public class MyValue  implements Cloneable {

    private HashMap<String, Integer> edgeToWeight;

    private Integer outDegree;

	public MyValue(String edgeEnd){
		edgeToWeight = new HashMap<String, Integer>();
		edgeToWeight.put(edgeEnd, 1);
		outDegree = 1;
	}

	public MyValue(String edgeEnd, Integer weight){
		edgeToWeight = new HashMap<String, Integer>();
		edgeToWeight.put(edgeEnd,  weight);
		outDegree = 1;
	}

	public final HashMap<String, Integer> getEdgeInfo(){
		return this.edgeToWeight;
	}

	@Override
    public final Object clone(){
		MyValue myvalue = null;
		try{
			myvalue = (MyValue)super.clone();
			myvalue.edgeToWeight = (HashMap<String, Integer>)edgeToWeight.clone();
		}catch(CloneNotSupportedException e){
			e.printStackTrace();
		}
		return myvalue;
	}

	public final Integer getOutDegree(){
		return outDegree;
	}



	public final void addEdge(String edgeEnd){
		if(this.edgeToWeight.containsKey(edgeEnd)){
			setWeight(edgeEnd,getWeight(edgeEnd) +1) ;
		} else{
			this.edgeToWeight.put(edgeEnd, 1);
			outDegree ++;
		}
	}


	public final Integer getWeight(String key){
		return edgeToWeight.get(key);
	}

	public final void setWeight(String key, Integer weight){
		edgeToWeight.put(key, weight);
	}

	public final Boolean containsKey(String key){
		return edgeToWeight.containsKey(key);
	}

	public final Iterator getKeySetIterator(){
		return edgeToWeight.keySet().iterator();
	}



}

