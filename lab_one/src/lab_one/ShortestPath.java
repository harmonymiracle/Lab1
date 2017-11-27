package lab_one;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class ShortestPath {

	
	public  String calShortestPath(String word1, String word2)
	{
        String header = word1.toLowerCase();
        String tail = word2.toLowerCase();
        
        if(header.equals(tail))
        {
            System.out.println("shortest path: " + header + "-->" + header);
            System.out.println("shortest distance: 0");
            System.out.println();
            return "";
        }
        
        if(!Graph.GetGraph().containsKey(header) || (!Graph.GetGraph().containsKey(tail) && !tail.equals(""))){
            System.out.println("there is no path from \"" + header + "\" to \"" + tail + "\"");
            return "";
        }
        
        
        HashSet<String> visited = new HashSet<String>();     // check if the word has been visited
        HashMap<String, String> preString = new HashMap<String, String>(); //use to print short path, from tail to header       
        visited.add(header);
        
        // deep copy, save the MyValue data
        HashMap<String, MyValue> graphMap = Graph.GetGraph();
        /*for(Iterator it = Graph.GetGraph().entrySet().iterator(); it.hasNext();)
        {
            Map.Entry entry = (Map.Entry)it.next();
            graph_cpy.put((String)entry.getKey(), (MyValue)((MyValue)entry.getValue()).clone());
        }
        */
        
        //generate a virtual edge from header to every edge if there is no edge between header to key
        //and set up the weight to MAX_VALUE represent this is a virtual edge
        //because when weight(v, k) + weight(k, u) < weight(v, u), need to update the distance from
        //v to u, so need to guarantee the edge <v, u> exist
        for(Iterator it = graphMap.keySet().iterator(); it.hasNext();)
        {
            String key = (String)it.next();
            if(key == header)   continue;
            
            if(!graphMap.get(header).ContainsKey(key))
            	graphMap.get(header).SetWeight(key, Integer.MAX_VALUE);
        }
        
        Boolean find = false;
        String tmp_node = "";
        while(visited.size() <= graphMap.size()){
            
            //first: find the min edge from v to u
            //and the v in the found_set and the u in the unfound_set
            Integer cur_min_path = Integer.MAX_VALUE;
            String cur_found_node = "";
            for(Iterator it = graphMap.keySet().iterator(); it.hasNext();)
            {
                tmp_node = (String)it.next();
                if(visited.contains(tmp_node))
                    continue;
    
                if(graphMap.get(header).ContainsKey(tmp_node) &&
                		graphMap.get(header).GetWeight(tmp_node) < cur_min_path)
                {
                    cur_min_path = (Integer)graphMap.get(header).GetWeight(tmp_node);
                    cur_found_node = tmp_node;
                }
            }
            
            //if the min edge is from header(word1) to tail(word2), represent find the shortest path
            if(cur_found_node.equals(tail))
            {
                find = true;
                break;
            }
            
            //if there is no min edge(all edge weight is MAX_VALUE), represent un_find the shortest path
            if(cur_found_node.equals(""))
            {
                find = false;
                break;
            }
            
            //second: if find min edge, add the u to found_set
            visited.add(cur_found_node);
            
            //third: update all edge weight from v(word1) to u(un_found node) if 
            //the weight(v, cur_found_node) + weight(cur_found_node, u) < weight(v, u)
            for(Iterator it = graphMap.keySet().iterator(); it.hasNext();)
            {
                String un_found_node = (String)it.next();
                if(visited.contains(un_found_node))
                    continue;

                if(graphMap.get(cur_found_node).ContainsKey(un_found_node) &&
                		graphMap.get(header).GetWeight(un_found_node) > 
                        cur_min_path + graphMap.get(cur_found_node).GetWeight(un_found_node))
                {
                    Integer distance = graphMap.get(header).GetWeight(cur_found_node) + 
                    		graphMap.get(cur_found_node).GetWeight(un_found_node);
                    
                    //record the pre_node(cur_found_node) for this update_node(un_found_node)
                    //used to print shortest path
                    
                    preString.put(un_found_node, cur_found_node);
                    
                    //update the weight
                    graphMap.get(header).SetWeight(un_found_node, distance);
                }       
            }
        }

 

        if(tail.equals(""))
        {
            showAllShortestPath(graphMap, preString, header);
            System.out.println("done");
            return "";
        }
        else
        {   
            if(find == false)
            {
                System.out.println("there is no path from \"" + header + "\" to \"" + tail + "\"");
                System.out.println();
                return "";
            }
            HashMap<String, MyValue> signMap = new HashMap<String, MyValue>();
            signMap.put(tail, new MyValue("", 0));
            
            String shortest_path = tail;
            String cur_node = tail;
            String pre_node = "";
            while(preString.containsKey(cur_node)  && !preString.get(cur_node).equals(header))
            {
                pre_node = preString.get(cur_node);
                shortest_path = pre_node + "-->" + shortest_path;
                if(signMap.containsKey(pre_node))
                {
                	signMap.get(pre_node).AddEdge(cur_node);
                }
                else
                {
                	signMap.put(pre_node, new MyValue(cur_node, 0));
                }
            //  m_signMap.put(pre_node, cur_node);
                cur_node = pre_node;
            }
            shortest_path = header + "-->" + shortest_path;
            //m_signMap.put(header, cur_node);
            if(signMap.containsKey(header))
            {
            	signMap.get(header).AddEdge(cur_node);
            }
            else
            {
            	signMap.put(header, new MyValue(cur_node, 0));
            }
            Integer shortest_path_distance =  graphMap.get(header).GetWeight(tail);
            System.out.println("shortest path :" + shortest_path);
            System.out.println("shortest_distance :" + shortest_path_distance);
            System.out.println("");
            
            Graph.SetSignMap(signMap);
            return shortest_path;
        }
    }
	
    private static void showAllShortestPath(HashMap<String, MyValue>graph_cpy, HashMap<String, String> preString, String header){
        
        String cur_path = System.getProperty("user.dir");
        
        File file = new File(cur_path + "\\allshortestpath.txt");
        try
        {
            if(!file.exists())
                file.createNewFile();
            HashMap<String, MyValue> signMap = new HashMap<String, MyValue>();
            BufferedWriter buffer = new BufferedWriter(new FileWriter(file));
            for(Iterator it = graph_cpy.keySet().iterator(); it.hasNext();)
            {
    
                String tail = (String)it.next();    
                Integer shortest_path_distance =  graph_cpy.get(header).GetWeight(tail);
                if(shortest_path_distance == Integer.MAX_VALUE)
                {
                    buffer.write("there is no path from \"" + header + "\" to \"" + tail + "\"\r\n");
                
                    continue;
                }
                
                signMap.put(tail, new MyValue("", 0));
                
                String shortest_path = tail;
                String cur_node = tail;
                String pre_node = "";
                while(preString.containsKey(cur_node)  && !preString.get(cur_node).equals(header))
                {
                    pre_node = preString.get(cur_node);
                    shortest_path = pre_node + "-->" + shortest_path;
                    cur_node = pre_node;
                }
                shortest_path = header + "-->" + shortest_path;

                buffer.write("shortest path :" + shortest_path + "\r\n");
                buffer.write("shortest_distance :" + shortest_path_distance + "\r\n");
                buffer.write("\r\n");
            }
            Graph.SetSignMap(signMap);
            buffer.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
