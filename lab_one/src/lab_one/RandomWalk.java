package lab_one;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;

public class RandomWalk {
	
    public  String randomWalk(){
        
        HashMap<String, MyValue> signMap = new HashMap<String, MyValue>();
        HashMap<String, MyValue> graphMap = Graph.GetGraph();
        StringBuffer bufferTemp = new StringBuffer();
        Random ran = new Random();
        HashMap<String, MyValue> randomEdge = new HashMap<String, MyValue>();
        
        Integer choice = Math.abs(ran.nextInt())% graphMap.size(); 
        String nextWord = "";
        String start =  (String)graphMap.keySet().toArray()[choice];
        
        bufferTemp.append(start);
        
        while(graphMap.containsKey(start)){
            
            choice = graphMap.get(start).GetOutDegree();
            nextWord = (String)graphMap.get(start).GetEdgeInfo().keySet().toArray()[Math.abs(ran.nextInt()) % choice];
            
            if(randomEdge.containsKey(start)){
                if(randomEdge.get(start).GetEdgeInfo().containsKey(nextWord)){
                    break;
                }
                else{
                    randomEdge.get(start).AddEdge(nextWord);
                }
                
            }
            else{
                MyValue nextArray = new MyValue(nextWord);
                randomEdge.put(start, nextArray);
            }
            
            if(nextWord != "")
                bufferTemp.append("-->");
            
            bufferTemp.append(nextWord);
            if(graphMap.containsKey(start))
            {
            	graphMap.get(start).AddEdge(nextWord);
            }
            else
            {
            	graphMap.put(start, new MyValue(nextWord, 0));
            }
            //signMap.put(start, nextWord);
            start = nextWord;
        }
        
        //signMap.put(start, "");
        
        
        String path = System.getProperty("user.dir");
        
        File walkAns = new File(path + "/walkResult.txt");
        try{
            
            PrintWriter pw = new  PrintWriter(new FileWriter(walkAns));
            pw.print(bufferTemp.toString());
            
            pw.close();
        }
        catch(Exception e){
            
        }
        
        
        Graph.SetSignMap(signMap);;
        return bufferTemp.toString();
    }
}
