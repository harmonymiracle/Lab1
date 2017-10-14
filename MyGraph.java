
package lab_one;

import java.lang.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/*
 * first modify in mygraph.java, branch : master
 * 
 * B1 first modify
 * C4 first modify
 */
public class MyGraph {
	
	private HashMap<String, MyValue> m_graphMap;
	private HashMap<String, MyValue> m_signMap;
	
	private String m_bridge;
	
	public String Get_Bridge()
	{
		return m_bridge;
	}
	
	public HashMap<String, MyValue> GetGraph(){
		
		return m_graphMap;
	}
	
	public MyGraph(){
		m_graphMap = new HashMap<String, MyValue>();
		m_signMap = new HashMap<String, MyValue>();
	}
	
	
	public static void main(String[] args) {
		MyGraph graph = new MyGraph();
		String text = "";
		File file = new File("E:\\Eclipse\\workspace\\graph\\1.txt");
		try
		{
			BufferedReader buffer = new BufferedReader(new FileReader(file));
			String read = "";
			while((read = buffer.readLine()) != null)
				text += read;
			buffer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		graph.CreateMap(text);
		graph.showDirectedGraph("graph_image");
		graph.calShortestPath("the", "");
		
		System.out.println("........................");
		// can test module here
	}
	
	
	public void CreateMap(String text){
		
		this.m_graphMap.clear();
		this.m_signMap.clear();
		this.m_bridge = "";
		String treated_text = TreatFile(text);
		System.out.println(treated_text);
		String splited_text[] = treated_text.split(" ");
		
		String header = splited_text[0];
		String tail = "";
		
		if(splited_text.length > 1){
			for(Integer i = 1; i < splited_text.length; ++i){
				tail = splited_text[i];
				if(!m_graphMap.containsKey(header))							//如果是第一个词，则不进行相关构图操作
				{
					m_graphMap.put(header, new MyValue(tail, 1)); 			// 如果该词第一次出现，构造它的相关类
				}
				else
				{
					m_graphMap.get(header).AddEdge(tail);		// 如果该词已出现，进行加边、加权
				}
				header = tail;                  //为下一个将当前后值 作为下次 的前值
			}
		}
		
		if(!m_graphMap.containsKey(header))
			m_graphMap.put(header, new MyValue("", 0));
	}
	
	
	public Bridge_Status queryBridgeWords(String word1, String word2)
	{
		
		String header = word1.toLowerCase();
		String tail = word2.toLowerCase();
		
		StringBuffer bufferTemp = new StringBuffer();
		m_bridge = "";
		if(!m_graphMap.containsKey(header) || !m_graphMap.containsKey(tail)){
			
			if(!m_graphMap.containsKey(header) && !m_graphMap.containsKey(tail))
				return Bridge_Status.ERRORONEANDTWO;
			else if(!m_graphMap.containsKey(header))
				return Bridge_Status.ERRORONE;
			else if(!m_graphMap.containsKey(tail))
				return Bridge_Status.ERRORTWO;
			else
				System.out.println("unknown bridge error");
			
		}
		
		
			
		HashMap<String, Integer> edgeInfo = m_graphMap.get(header).GetEdgeInfo();    //获得word1的  边-权重 Map
		
		int bridge_cnt = 0;
		for(Iterator it = edgeInfo.keySet().iterator(); it.hasNext(); ){            //查询所属Map中所有条目，检测是否构成桥接
			String mightBridge = (String)it.next();
			
			// avoid NullPointerException, because the word dont have out edge will cause this case
			if(m_graphMap.get(mightBridge) != null){
				if(m_graphMap.get(mightBridge).GetEdgeInfo().containsKey(tail)){
					bufferTemp.append(mightBridge);
					bufferTemp.append(' ');
					bridge_cnt++;
				}
			}
		}
		
		m_bridge = TreatFile(bufferTemp.toString());
		if(m_bridge.equals(null)){
			System.out.println("m_bridge null");
		}
		if(bridge_cnt == 0)
			return Bridge_Status.NONE_BRIDGE;
		else if(bridge_cnt == 1)
			return Bridge_Status.ONE_BRIDGE;
		else
			return Bridge_Status.MULTI_BRIDGE;
	}
	
	
	public String generateNewText(String inputText)
	{
		StringBuffer ans = new StringBuffer();
		
		String split_text[] = TreatFile(inputText).split(" ");
		String header = split_text[0];
		String tail = "";
		
		Random rand = new Random();
		if(split_text.length > 1){
			for(Integer i = 1; i < split_text.length; ++i)
			{
				tail = split_text[i];
				ans.append(header);
				ans.append(" ");	
				
				Bridge_Status status = queryBridgeWords(header, tail);
				if(status.equals(Bridge_Status.ONE_BRIDGE) || status.equals(Bridge_Status.MULTI_BRIDGE)){
					
					String bridges[] = Get_Bridge().split(" ");
					ans.append(bridges[Math.abs(rand.nextInt()) % bridges.length]);
					ans.append(" ");
				}
				header = tail;
			}
		}
		
		ans.append(header);
		return TreatFile(ans.toString());
		
	}

	private void showAllShortestPath(HashMap<String, MyValue>graph_cpy, HashMap<String, String> preString, String header){
		
		String cur_path = System.getProperty("user.dir");
		
		File file = new File(cur_path + "\\allshortestpath.txt");
		try
		{
			if(!file.exists())
				file.createNewFile();
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
				
				m_signMap.put(tail, new MyValue("", 0));
				
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
			
			buffer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public String calShortestPath(String word1, String word2)
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
		
		if(!m_graphMap.containsKey(header) || (!m_graphMap.containsKey(tail) && !tail.equals(""))){
			System.out.println("there is no path from \"" + header + "\" to \"" + tail + "\"");
			return "";
		}
		
		
		HashSet<String> visited = new HashSet<String>();     // check if the word has been visited
		HashMap<String, String> preString = new HashMap<String, String>(); //use to print short path, from tail to header		
		visited.add(header);
		
		// deep copy, save the MyValue data
		HashMap<String, MyValue> graph_cpy = (HashMap<String, MyValue>)m_graphMap.clone();
		for(Iterator it = m_graphMap.entrySet().iterator(); it.hasNext();)
		{
			Map.Entry entry = (Map.Entry)it.next();
			graph_cpy.put((String)entry.getKey(), (MyValue)((MyValue)entry.getValue()).clone());
		}
		
		
		//generate a virtual edge from header to every edge if there is no edge between header to key
		//and set up the weight to MAX_VALUE represent this is a virtual edge
		//because when weight(v, k) + weight(k, u) < weight(v, u), need to update the distance from
		//v to u, so need to guarantee the edge <v, u> exist
		for(Iterator it = graph_cpy.keySet().iterator(); it.hasNext();)
		{
			String key = (String)it.next();
			if(key == header)   continue;
			
			if(!graph_cpy.get(header).ContainsKey(key))
				graph_cpy.get(header).SetWeight(key, Integer.MAX_VALUE);
		}
		
		Boolean find = false;
		String tmp_node = "";
		while(visited.size() <= graph_cpy.size()){
			
			//first: find the min edge from v to u
			//and the v in the found_set and the u in the unfound_set
			Integer cur_min_path = Integer.MAX_VALUE;
			String cur_found_node = "";
			for(Iterator it = graph_cpy.keySet().iterator(); it.hasNext();)
			{
				tmp_node = (String)it.next();
				if(visited.contains(tmp_node))
					continue;
	
				if(graph_cpy.get(header).ContainsKey(tmp_node) &&
						graph_cpy.get(header).GetWeight(tmp_node) < cur_min_path)
				{
					cur_min_path = (Integer)graph_cpy.get(header).GetWeight(tmp_node);
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
			for(Iterator it = graph_cpy.keySet().iterator(); it.hasNext();)
			{
				String un_found_node = (String)it.next();
				if(visited.contains(un_found_node))
					continue;

				if(graph_cpy.get(cur_found_node).ContainsKey(un_found_node) &&
						graph_cpy.get(header).GetWeight(un_found_node) > 
						cur_min_path + graph_cpy.get(cur_found_node).GetWeight(un_found_node))
				{
					Integer distance = graph_cpy.get(header).GetWeight(cur_found_node) + 
							graph_cpy.get(cur_found_node).GetWeight(un_found_node);
					
					//record the pre_node(cur_found_node) for this update_node(un_found_node)
					//used to print shortest path
					
					preString.put(un_found_node, cur_found_node);
					
					//update the weight
					graph_cpy.get(header).SetWeight(un_found_node, distance);
				}		
			}
		}

		m_signMap.clear();
		
		if(tail.equals(""))
		{
			showAllShortestPath(graph_cpy, preString, header);
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
			m_signMap.put(tail, new MyValue("", 0));
			
			String shortest_path = tail;
			String cur_node = tail;
			String pre_node = "";
			while(preString.containsKey(cur_node)  && !preString.get(cur_node).equals(header))
			{
				pre_node = preString.get(cur_node);
				shortest_path = pre_node + "-->" + shortest_path;
				if(m_signMap.containsKey(pre_node))
				{
					m_signMap.get(pre_node).AddEdge(cur_node);
				}
				else
				{
					m_signMap.put(pre_node, new MyValue(cur_node, 0));
				}
			//	m_signMap.put(pre_node, cur_node);
				cur_node = pre_node;
			}
			shortest_path = header + "-->" + shortest_path;
			//m_signMap.put(header, cur_node);
			if(m_signMap.containsKey(header))
			{
				m_signMap.get(header).AddEdge(cur_node);
			}
			else
			{
				m_signMap.put(header, new MyValue(cur_node, 0));
			}
			Integer shortest_path_distance =  graph_cpy.get(header).GetWeight(tail);
			System.out.println("shortest path :" + shortest_path);
			System.out.println("shortest_distance :" + shortest_path_distance);
			System.out.println("");
			return shortest_path;
		}
	}
	
	
	public boolean CheckValidity(char c){
		
		if(c >= 'a' && c <= 'z')
			return true;
		else{
			return false;
		}
	}

	private Boolean checkDotResult(String graph_filename)
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
	
	private void deletegvfile(String graph_filename)
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
	
	public Boolean showDirectedGraph(String graph_filename){
		
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
			
			for(Iterator it = m_graphMap.keySet().iterator(); it.hasNext();)
			{
				String header = (String)it.next();
				generate_graphviz_file(visited, myFile, header);
			}
			
			for(Iterator it = m_signMap.keySet().iterator(); it.hasNext();)
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
			String dot_path = "E:\\new_study_soft\\Java\\Graphviz\\bin\\dot.exe";  // 王晨阳
			//String dot_path = "E:\\graphviz\\bin\\dot.exe";  //王鹏
			Process p = run.exec(dot_path + " -Tpng "+ 
					cur_path + "\\" + graph_filename+ ".gv -o " + 
					cur_path + "\\" + graph_filename + ".png");
			p.waitFor();
			deletegvfile(graph_filename);
			System.out.println("dot.exe run over...");
			/*	BufferedReader stdout = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
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

	//dfs 
	private void generate_graphviz_file(HashSet<String> visited, PrintWriter myFile, String header)
	{
		if(!m_graphMap.containsKey(header)){
			System.out.println("have get the end");
			return;
		}
		if(visited.contains(header)){
			return;
		}
			
		
		visited.add(header);
		
		for(Iterator it = m_graphMap.get(header).GetKeySetIterator(); it.hasNext();)
		{
			String tail = (String)it.next();
			
			if(tail.equals("")){
				continue;
			}
			
			//if the <header, tail> is shortest path or random walk
			if(m_signMap.containsKey(header) && m_signMap.get(header).ContainsKey(tail)) //m_signMap.get(header).equals(tail))
			{
				myFile.println(header + "->" + tail + " [color=blue,label=" + m_graphMap.get(header).GetWeight(tail) + "];");
			}
			else
			{
				myFile.println(header + "->" + tail + " [label=" + m_graphMap.get(header).GetWeight(tail) + "];");
			}
		}
			
		for(Iterator it = m_graphMap.get(header).GetKeySetIterator(); it.hasNext();)
		{
			 header = (String)it.next();
			 generate_graphviz_file(visited, myFile, header);
		} 

	}
	
	
	public String randomWalk(){
		
		m_signMap.clear();
		
		StringBuffer bufferTemp = new StringBuffer();
		Random ran = new Random();
		HashMap<String, MyValue> randomEdge = new HashMap<String, MyValue>();
		
		
		Integer choice = Math.abs(ran.nextInt())% m_graphMap.size(); 
		String nextWord = "";
		String start =  (String)m_graphMap.keySet().toArray()[choice];
		
		bufferTemp.append(start);
		
		while(m_graphMap.containsKey(start)){
			
			choice = m_graphMap.get(start).GetOutDegree();
			nextWord = (String)m_graphMap.get(start).GetEdgeInfo().keySet().toArray()[Math.abs(ran.nextInt()) % choice];
			
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
			if(m_signMap.containsKey(start))
			{
				m_signMap.get(start).AddEdge(nextWord);
			}
			else
			{
				m_signMap.put(start, new MyValue(nextWord, 0));
			}
			//m_signMap.put(start, nextWord);
			start = nextWord;
		}
		
		//m_signMap.put(start, "");
		
		
		String path = System.getProperty("user.dir");
		
		File walkAns = new File(path + "/walkResult.txt");
		try{
			
			PrintWriter pw = new  PrintWriter(new FileWriter(walkAns));
			pw.print(bufferTemp.toString());
			
			pw.close();
		}
		catch(Exception e){
			
		}
		
		
		
		return bufferTemp.toString();
	}
	
	// make the text formal
	public String TreatFile(String text)
	{
		// this is for building new string 
		StringBuffer bufferTemp = new StringBuffer("");
		
		String real = text.toLowerCase();
		
		char lastChar = 'a';    // for culling the redundant blank space, can not be ' '
		int lastIndex = 0;
		
		
		// dont delete it, if dont have blank space in last position, will lack last word
		int i = 0;            
		
		for(i = 0; i < real.length(); i++){
			
			char temp = real.charAt(i);
			if(!CheckValidity(temp)){
				if(lastChar != ' '){
					
					bufferTemp.append(real.substring(lastIndex, i));
					bufferTemp.append(' ');
				}
				lastChar = ' ';
				lastIndex = i+1;
			}
			else
				lastChar = temp;
		}
		
		
		bufferTemp.append(real.substring(lastIndex, i));   // for add the last word
		
		
		return bufferTemp.toString();
	}
	

}























