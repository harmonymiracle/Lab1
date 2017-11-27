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
                if(!graphMap.containsKey(header))                         //����ǵ�һ���ʣ��򲻽�����ع�ͼ����
                {
                	graphMap.put(header, new MyValue(tail, 1));           // ����ôʵ�һ�γ��֣��������������
                }
                else
                {
                	graphMap.get(header).AddEdge(tail);       // ����ô��ѳ��֣����мӱߡ���Ȩ
                }
                header = tail;                  //Ϊ��һ������ǰ��ֵ ��Ϊ�´� ��ǰֵ
            }
        }
        
        if(!graphMap.containsKey(header))
        	graphMap.put(header, new MyValue("", 0));
        
        Graph.SetGraphMap(graphMap);
        HashMap<String, MyValue> signMap = new HashMap<String, MyValue>();
        Graph.SetSignMap(signMap);
    }
}
