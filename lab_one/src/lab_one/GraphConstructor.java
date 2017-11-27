package lab_one;

import java.util.HashMap;

public class GraphConstructor {
	
    public void CreateMap(String text){
    	System.out.println("in CreateMap");
    	HashMap<String, MyValue> graphMap = new HashMap<String, MyValue>();
        String treated_text = CommonFunc.TreatFile(text);
        System.out.println(treated_text);
        String splited_text[] = treated_text.split(" ");
        
        String header = splited_text[0];
        String tail = "";
        
        if(splited_text.length > 1){
            for(Integer i = 1; i < splited_text.length; ++i){
                tail = splited_text[i];
                if(!graphMap.containsKey(header))                         //如果是第一个词，则不进行相关构图操作
                {
                	graphMap.put(header, new MyValue(tail, 1));           // 如果该词第一次出现，构造它的相关类
                }
                else
                {
                	graphMap.get(header).AddEdge(tail);       // 如果该词已出现，进行加边、加权
                }
                header = tail;                  //为下一个将当前后值 作为下次 的前值
            }
        }
        
        if(!graphMap.containsKey(header))
        	graphMap.put(header, new MyValue("", 0));
        
        Graph.SetGraphMap(graphMap);
        HashMap<String, MyValue> signMap = new HashMap<String, MyValue>();
        Graph.SetSignMap(signMap);
    }
}
