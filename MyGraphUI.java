
package lab_one;

import java.lang.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.invoke.*;
import java.lang.reflect.*;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

public class MyGraphUI extends JFrame {

	private MyGraph graph ;
	private HashMap<String, String> eventInfo;
	
	public Container contain ;
	public JFrame jf;
	
 	public MyGraphUI(){
		graph = new MyGraph();
		eventInfo = new HashMap<String, String>();
		jf = new JFrame();
	}
	
	
	public static void main(String[] args) {
		CreateWindow();
	}
	

	
	public static  void CreateWindow(){
		Integer width = 800;
		Integer height = 600;
		Integer xOffset = 500;
		Integer yOffset = 400;
		
		MyGraphUI myUI = new MyGraphUI();
		
		setDefaultLookAndFeelDecorated(true);
		myUI.contain = myUI.jf.getContentPane();
		
		myUI.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		myUI.jf.setTitle("Graph Solution");
		myUI.jf.setSize(width, height); 
		myUI.jf.setLocation(xOffset, yOffset);
		
		myUI.contain.setLayout(null);
		
		
		
		myUI.placeComponents(myUI.jf);
	
		myUI.jf.setVisible(true);
	}
		
	
	public void placeComponents(JFrame frame){
		
		Container container = frame.getContentPane();
		
		
		container.setLayout(new GridLayout(7,1));
		
		
		
		JPanel pane1 = new JPanel();
		JLabel cmd1Label = new JLabel("1、根据文件路径生成图"); 
		JButton createBtn = new JButton("生成");
		
		ButtonActionListener buttonAct = this.new ButtonActionListener();
		
		createBtn.setActionCommand("CreateMap");
		createBtn.addActionListener(buttonAct);
		
		pane1.setLayout(new GridLayout(2,1));
		pane1.add(cmd1Label);
		pane1.add(createBtn);
		
		
		
		JPanel pane2 = new JPanel();
		JLabel cmd2Label = new JLabel("2、展示已生成有向图");
		JButton displayBtn = new JButton("展示");
		displayBtn.setActionCommand("showDirectedGraph");
		displayBtn.addActionListener(buttonAct);
		
		pane2.setLayout(new GridLayout(2,1));
		pane2.add(cmd2Label);
		pane2.add(displayBtn);
		
		
		JPanel pane3 = new JPanel();
		JLabel cmd3Label = new JLabel("3、查询桥接词");
		JButton bridgeBtn = new JButton("查询");
		
		
		bridgeBtn.setActionCommand("queryBridgeWords");
		bridgeBtn.addActionListener(buttonAct);
		
		pane3.setLayout(new GridLayout(2,1));
		pane3.add(cmd3Label);
		pane3.add(bridgeBtn);
		
		
		
		JPanel pane4 = new JPanel();
		JLabel cmd4Label = new JLabel("4、生成新文本");
		JButton generateBtn = new JButton("生成");
		
		generateBtn.setActionCommand("generateNewText");
		generateBtn.addActionListener(buttonAct);
		
		pane4.setLayout(new GridLayout(2,1));
		pane4.add(cmd4Label);
		pane4.add(generateBtn);
		
		
		
		JPanel pane5 = new JPanel();
		JLabel cmd5Label = new JLabel("5、展示最短路径");
		JButton minPathBtn = new JButton("展示");		
		
		minPathBtn.setActionCommand("calShortestPath");
		minPathBtn.addActionListener(buttonAct);
		
		
		pane5.setLayout(new GridLayout(2,1));
		pane5.add(cmd5Label);
		pane5.add(minPathBtn);
		
		
		
		JPanel pane6 = new JPanel();
		JLabel cmd6Label = new JLabel("6、根据图随机游走,并存储");
		JButton randomWalkBtn = new JButton("随机游走");
	
		randomWalkBtn.setActionCommand("randomWalk");
		randomWalkBtn.addActionListener(buttonAct);
		
		pane6.setLayout(new GridLayout(2,1));
		pane6.add(cmd6Label);
		pane6.add(randomWalkBtn);
		
		
		
		container.add(pane1);
		container.add(pane2);
		container.add(pane3);
		container.add(pane4);
		container.add(pane5);
		container.add(pane6);
		
		
	}
	
	
	public class ButtonActionListener implements ActionListener{
	final String CLASSNAME = "lab_one.MyGraph";
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		StringBuilder sb = new StringBuilder("");
		String cmd = e.getActionCommand().toString();
		if(cmd.equals("CreateMap")){
			CreateGraph(cmd);
		}
		else if(cmd.equals("showDirectedGraph")){
			ShowGraph(cmd);
		}
		else if(cmd.equals("queryBridgeWords")){
			QueryBridge(cmd);
		}
		else if(cmd.equals("generateNewText")){
			GenerateText(cmd);
		}
		else if(cmd.equals("calShortestPath")){
			CalMinPath(cmd);
		}
		else if(cmd.equals("randomWalk")){
			String s = RandomWalk(cmd);
			JOptionPane.showMessageDialog(MyGraphUI.this.jf, "游走结果如下: " + s, "随机游走", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			JOptionPane.showMessageDialog(MyGraphUI.this.jf, "未知命令，解析失败", "Failed", JOptionPane.PLAIN_MESSAGE);
		}
		
		
	}
	
	void CreateGraph(String cmd){
		
		JDialog jd = new JDialog(MyGraphUI.this.jf);
		jd.setBounds(600,200, 600, 250);
		jd.setLayout(new GridLayout(4,1));
		
		
		
		JLabel infoLabel = new JLabel("请选择或输入文件路径:");
		JPanel pane1 = new JPanel();
		JTextField jtf = new JTextField();
		JButton chooseBtn = new JButton("选择");
		
		pane1.setLayout(new GridLayout(1,2));
		
		chooseBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				FileDialog fd = new FileDialog(jd);
				fd.setMode(FileDialog.LOAD);
				
				fd.setVisible(true);
				if(fd.getDirectory() != null && fd.getFile() != null)
					jtf.setText(fd.getDirectory() + fd.getFile());
				
			}
		});
		pane1.add(jtf);
		pane1.add(chooseBtn);
		
		
		
		JButton submitBtn = new JButton("生成");
		
		
		submitBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				String filePath = jtf.getText();
				
				if(File2Graph(cmd,filePath)){
					JOptionPane.showMessageDialog(MyGraphUI.this.jf, "已生成图", "成功通知", JOptionPane.PLAIN_MESSAGE);
				}
				else{
					JOptionPane.showMessageDialog(MyGraphUI.this.jf, "未生成图，请检查路径是否正确 或文件为空文件", "失败通知", JOptionPane.PLAIN_MESSAGE);
				}
				}
			
		});
		
		jd.add(infoLabel);
		jd.add(pane1);
		jd.add(submitBtn);
		
		jd.setVisible(true);
	}
	
	void ShowGraph(String cmd){
		JOptionPane.showMessageDialog(MyGraphUI.this.jf, "请等待, 生成中......", "生成有向图图片", JOptionPane.INFORMATION_MESSAGE);
		Boolean showOk = graph.showDirectedGraph("Result");
		if(showOk){
			JOptionPane.showMessageDialog(MyGraphUI.this.jf, "已生成，请在当前文件夹查看 ", "生成有向图图片", JOptionPane.INFORMATION_MESSAGE);
		}
		else{
			JOptionPane.showMessageDialog(MyGraphUI.this.jf, "生成失败，请检查是否文件过大 ", "生成有向图图片", JOptionPane.INFORMATION_MESSAGE);

		}
	}
	
	void QueryBridge(String cmd){
		String word1 = null;
		String word2 = null;
		word1 = JOptionPane.showInputDialog(MyGraphUI.this.jf, "请输入word1： ", "查询两词的桥接词", JOptionPane.INFORMATION_MESSAGE);
		word2 = JOptionPane.showInputDialog(MyGraphUI.this.jf, "请输入word2： ", "查询两词的桥接词", JOptionPane.INFORMATION_MESSAGE);

		
		System.out.println(word1 +" " + word2 );
		Bridge_Status tempAns = GetBridgeWord(cmd, word1, word2);
		if(tempAns.equals(Bridge_Status.ERRORONE)){
			JOptionPane.showMessageDialog(MyGraphUI.this.jf, "No \"" + word1 +"\" in the graph!", "Error", JOptionPane.PLAIN_MESSAGE);
		}
		else if(tempAns.equals(Bridge_Status.ERRORTWO)){
			JOptionPane.showMessageDialog(MyGraphUI.this.jf, "No \"" + word2 +"\" in the graph!", "Error", JOptionPane.PLAIN_MESSAGE);

		}
		else if(tempAns.equals(Bridge_Status.ERRORONEANDTWO)){
			JOptionPane.showMessageDialog(MyGraphUI.this.jf, "No \"" + word1 +"\" and \"" + word2 
					+ "\" in the graph!", "Error", JOptionPane.PLAIN_MESSAGE);

		}
		else if(tempAns.equals(Bridge_Status.NONE_BRIDGE)){
			JOptionPane.showMessageDialog(MyGraphUI.this.jf, "No bridge words from \"" + word1 + "\" to \"" + word2 + "\"!", "Fail", JOptionPane.PLAIN_MESSAGE);
		}
		else if(tempAns.equals(Bridge_Status.ONE_BRIDGE)){
			JOptionPane.showMessageDialog(MyGraphUI.this.jf, "The bridge words from \"" + word1 + "\" to \"" + word2 +"\"are: " + MyGraphUI.this.graph.Get_Bridge(), "Success", JOptionPane.PLAIN_MESSAGE);
		}
		else if(tempAns.equals(Bridge_Status.MULTI_BRIDGE)){
			String tempArr[] = MyGraphUI.this.graph.Get_Bridge().split(" ");
			String temp = "";
			for(int cnt = 0; cnt < tempArr.length -1; cnt++){
				temp += tempArr[cnt];
				temp += ", ";
			}
			temp += "and ";
			temp += tempArr[tempArr.length -1];
			temp += ".";
			JOptionPane.showMessageDialog(MyGraphUI.this.jf, "The bridge words from \"" + word1 + "\" to \"" + word2 +"\"are: " + temp, "Success", JOptionPane.PLAIN_MESSAGE);

		}
	}
	
	void GenerateText(String cmd){
		JDialog jd = new JDialog(MyGraphUI.this.jf);
		jd.setBounds(600,200, 500, 500);
		jd.setLayout(new GridLayout(3,1));
		
		JLabel infoLabel = new JLabel("请输入文本:");
		
		JTextArea jta = new JTextArea();
		
		// 自动换行，及断行不断字（若该词超出该行，则从上一个空白处移到新的一行）
		jta.setLineWrap(true);
		jta.setWrapStyleWord(true); 
		
		JScrollPane jsp = new JScrollPane(jta);
		JButton submitBtn = new JButton("提交");
		
		
		submitBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				String temp = null;
				temp = GenerateText("generateNewText",jta.getText());
				jta.setText(temp);
				JOptionPane.showMessageDialog(MyGraphUI.this.jf, "新文本已生成： 见文本区", "生成新文本", JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
		
		jd.add(infoLabel);
		jd.add(jsp);
		jd.add(submitBtn);
		
		jd.setVisible(true);
		
	}
	
	void CalMinPath(String cmd){
		JDialog jd = new JDialog(MyGraphUI.this.jf);
		jd.setTitle("计算两词的最短路径");
		jd.setBounds(600,200, 500, 500);
		jd.setLayout(new GridLayout(5,1));
		
		JLabel infoLabel = new JLabel("请输入word1、2:");
		JTextField jtf1 = new JTextField();
		JTextField jtf2 = new JTextField();
		JTextField jtf3 = new JTextField();
		JButton submitBtn = new JButton("提交");
		
		
		submitBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				String temp = null;
				
				temp = GetMinPath("calShortestPath",jtf1.getText(), jtf2.getText());
				
				if(temp == ""){
					jtf3.setText("两词之间不可达" );
				}
				else{
					jtf3.setText("生成最短路径如下: " + temp);
				}
				
				
				
			}
		});
		
		jd.add(infoLabel);
		jd.add(jtf1);
		jd.add(jtf2);
		jd.add(jtf3);
		jd.add(submitBtn);
		
		jd.setVisible(true);
	}
	
	
	//C:/Users/Mr.Wang/Desktop/test.txt  // test path string
	public Boolean File2Graph(String methodName, String path){
		
		Method createMethod = null;
		try{
			Class<?> model = Class.forName(CLASSNAME);
			createMethod = model.getDeclaredMethod(methodName, String.class);
		}
		catch(Exception exc){
		}
		
		File file = new File(path);
		
		
		StringBuilder sb = new StringBuilder();
		try{
			String temp = "";
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while((temp = reader.readLine()) != null)
				sb.append(temp);
			
			reader.close();
			
			if(sb.length() == 0){
				System.out.println(sb.length());
				return false;
			}
			
		}
		catch(Exception ecp){
			System.out.println(sb.length());
			return false;
		}
		try{
			createMethod.invoke(MyGraphUI.this.graph ,sb.toString());
		}
		catch(Exception e){
			
			
		}
		
		System.out.println("Create Graph is ok");
		
		return true;
		
		
	}
	
	
	public Bridge_Status GetBridgeWord(String methodName, String word1, String word2){
		Bridge_Status bs = null;
		Method searchBridgeMethod = null;
		try{
			Class<?> model = Class.forName(CLASSNAME);
			searchBridgeMethod = model.getDeclaredMethod(methodName, String.class, String.class);
		}
		catch(Exception exc){
			System.out.println("not find method");
		}
		
		try{
			
			bs = (Bridge_Status)searchBridgeMethod.invoke(MyGraphUI.this.graph ,word1, word2);
			if(bs != null){
				System.out.println(bs);
			}
		}
		catch(Exception e){
			System.out.println("method cant exe " );
		}
		
		return bs;
	}
	
	
	
	public String GenerateText(String methodName, String oldText){
		
		String ans = "";
		
		Method GenerateTextMethod = null;
		try{
			Class<?> model = Class.forName(CLASSNAME);
			GenerateTextMethod = model.getDeclaredMethod(methodName, String.class);
		}
		catch(Exception exc){
			System.out.println("not find method");
		}
	
		
		try{
			
			ans = (String)GenerateTextMethod.invoke(MyGraphUI.this.graph ,oldText);
			System.out.println(ans);
			
			if(ans == null){
				
			}
			else{
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("method cant exe " );
		}
		return ans;
	}
	
	
	public String GetMinPath(String methodName, String word1, String word2){
		String minPathStr = "";
		Method MinPathMethod = null;
		try{
			Class<?> model = Class.forName(CLASSNAME);
			MinPathMethod = model.getDeclaredMethod(methodName, String.class, String.class);
		}
		catch(Exception exc){
			System.out.println("not find method");
		}
	
		
		try{
			minPathStr = (String)MinPathMethod.invoke(MyGraphUI.this.graph ,word1, word2);
			if(minPathStr == null){
			}
			else{
				System.out.println(minPathStr);
			}
		}
		catch(Exception e){
			System.out.println("method cant exe " );
		}
		
		return minPathStr;
		
	}
	
	public String RandomWalk(String methodName){
		String ans = "";
		
		Method RandomWalkMethod = null;	
		try{
			Class<?> model = Class.forName(CLASSNAME);
			RandomWalkMethod = model.getDeclaredMethod(methodName);
		}
		catch(Exception exc){
			System.out.println("not find method");
		}
		try{
			//String s = (String)searchBridgeMethod.invoke(MyGraphUI.this.graph ,word1, word2);
			ans = (String)RandomWalkMethod.invoke(MyGraphUI.this.graph );
			if(ans == null){
				
			}
			else{
				System.out.println(ans);
			}
		}
		catch(Exception e){
			System.out.println("method cant exe " );
		}
		
		
		return ans;
		
	}
	
	// buttonActionListener class end here
	
}

	
	// the MyGraphUI class end here
}
	

>>>>>>> company_branch
