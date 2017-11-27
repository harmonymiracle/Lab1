package lab_one;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class GraphViz {
	
    public  Boolean showDirectedGraph(String graph_filename){
        
        System.out.println(graph_filename);
        String cur_path = System.getProperty("user.dir");
        if(checkDotResult(graph_filename) == true)
        {
            File file = new File(cur_path + "\\" + graph_filename + ".png");
            try
            {
                if(!file.getParentFile().exists())
                {
                    file.getParentFile().mkdirs();
                }
                if(file.exists())
                {
                    file.delete();
                    System.out.println("delete over...");
                    checkDotResult(graph_filename);
                }
                    
            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("check whether *.png exist error...");
                return false;
            }
        }
        
        File file = new File(cur_path + "\\" + graph_filename +  ".gv");
        try
        {
            if(!file.getParentFile().exists())
            {
                file.getParentFile().mkdirs();
            }
            if(!file.exists())
                file.createNewFile();
            FileWriter resultFile = new FileWriter(file);
            PrintWriter myFile = new PrintWriter(resultFile);
            
            HashSet<String> visited = new HashSet<String>();
            
            myFile.println("digraph mygraph {");
            
            HashMap<String, MyValue> graphMap = Graph.GetGraph();
            HashMap<String, MyValue> signMap = Graph.GetSign();
            for(Iterator it = graphMap.keySet().iterator(); it.hasNext();)
            {
                String header = (String)it.next();
                generate_graphviz_file(graphMap, signMap, visited, myFile, header);
            }
            
            for(Iterator it = signMap.keySet().iterator(); it.hasNext();)
            {
                String node = (String)it.next();
                myFile.println(node + " [style=filled,fillcolor=blue];");
            }
            
            myFile.println("}");
            resultFile.close();
            
        }
        catch(Exception e)
        {
            System.out.println("create file error...");
            e.printStackTrace();
            return false;
        }
        
        Runtime run = Runtime.getRuntime();
        try
        {
            //run dot.ext to generate image
            //String dot_path = "E:\\new_study_soft\\Java\\Graphviz\\bin\\dot.exe";  // Õı≥ø—Ù
            String dot_path = "E:\\graphviz\\bin\\dot.exe";  //Õı≈Ù
            Process p = run.exec(dot_path + " -Tpng "+ 
                    cur_path + "\\" + graph_filename+ ".gv -o " + 
                    cur_path + "\\" + graph_filename + ".png");
            p.waitFor();
            deletegvfile(graph_filename);
            System.out.println("dot.exe run over...");
            /*  BufferedReader stdout = new BufferedReader(new InputStreamReader(p.getInputStream()));
            
            String line = null;
            while((line = stdout.readLine()) != null)
            {
                System.out.println(line);
            }
            stdout.close();*/
            
        }
        catch(Exception e)
        {
            System.out.println("carry out dot.ext error...");
            e.printStackTrace();
            return false;
        }
        
        return checkDotResult(graph_filename);
        
    }
    private  Boolean checkDotResult(String graph_filename)
    {
        String cur_path = System.getProperty("user.dir");
        File file = new File(cur_path + "\\" + graph_filename + ".png");
        try
        {
            if(file.exists())
            {           
                return true;
            }else
            {
                return false;
            }
                
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        
    }
    
    private  void deletegvfile(String graph_filename)
    {
        String cur_path = System.getProperty("user.dir");
        File file = new File(cur_path + "\\" + graph_filename + ".gv");
        try
        {
            if(file.exists() && file.isFile())
                file.delete();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    //dfs 
    private  void generate_graphviz_file(
    		HashMap<String, MyValue> graphMap, HashMap<String, MyValue> signMap,
    		HashSet<String> visited, PrintWriter myFile, String header)
    {
        if(!graphMap.containsKey(header)){
            System.out.println("have get the end");
            return;
        }
        if(visited.contains(header)){
            return;
        }
            
        
        visited.add(header);
        
        for(Iterator it = graphMap.get(header).GetKeySetIterator(); it.hasNext();)
        {
            String tail = (String)it.next();
            
            if(tail.equals("")){
                continue;
            }
            
            //if the <header, tail> is shortest path or random walk
            if(signMap.containsKey(header) && signMap.get(header).ContainsKey(tail)) //m_signMap.get(header).equals(tail))
            {
                myFile.println(header + "->" + tail + " [color=blue,label=" + graphMap.get(header).GetWeight(tail) + "];");
            }
            else
            {
                myFile.println(header + "->" + tail + " [label=" + graphMap.get(header).GetWeight(tail) + "];");
            }
        }
            
        for(Iterator it = graphMap.get(header).GetKeySetIterator(); it.hasNext();)
        {
             header = (String)it.next();
             generate_graphviz_file(graphMap, signMap, visited, myFile, header);
        } 

    }
}
