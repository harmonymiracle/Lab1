package lab_one;

import java.util.HashMap;
import java.util.Iterator;

public class BridgeWord {

    public enum Bridge_Status {
        ONE_BRIDGE,
        MULTI_BRIDGE,
        NONE_BRIDGE,
        ERRORONE,
        ERRORTWO,
        ERRORONEANDTWO
    }

    private String bridge;
    
    public String getBridge() {
        return bridge;
    }

    private boolean isOneBridge = false;
    private boolean isMultiBridge = false;
    private boolean isNoneBridge = false;
    private boolean isErrorOne = false;
    private boolean isErrorTwo = false;
    private boolean isErrorOneAndTwo = false;
    
    public boolean isOneBridge() {
        return isOneBridge;
    }

    public boolean isMultiBridge() {
        return isMultiBridge;
    }

    public boolean isNoneBridge() {
        return isNoneBridge;
    }

    public boolean isErrorOne() {
        return isErrorOne;
    }

    public boolean isErrorTwo() {
        return isErrorTwo;
    }

    public boolean isErrorOneAndTwo() {
        return isErrorOneAndTwo;
    }
    
   
    public static BridgeWord queryBridgeWords(String word1, String word2)
    {
    	HashMap<String, MyValue> graphMap = Graph.GetGraph();
        String header = word1.toLowerCase();
        String tail = word2.toLowerCase();
        
        StringBuffer bufferTemp = new StringBuffer();
        BridgeWord bridgeWord = new BridgeWord();
        bridgeWord.bridge = "";
        if(!graphMap.containsKey(header) || !graphMap.containsKey(tail)){
            
            if(!graphMap.containsKey(header) && !graphMap.containsKey(tail)) {
            	bridgeWord.isErrorOneAndTwo = true;
                return bridgeWord;
            } else if(!graphMap.containsKey(header)) {
            	bridgeWord.isErrorOne = true;
                return bridgeWord;
            } else if(!graphMap.containsKey(tail)) {
            	bridgeWord.isErrorTwo = true;
                return bridgeWord;
            } else {
                System.out.println("unknown bridge error");
            }
            
        }
        
        HashMap<String, Integer> edgeInfo = graphMap.get(header).GetEdgeInfo();    //获得word1的  边-权重 Map
        
        int bridge_cnt = 0;
        for(Iterator it = edgeInfo.keySet().iterator(); it.hasNext(); ){            //查询所属Map中所有条目，检测是否构成桥接
            String mightBridge = (String)it.next();
            
            // avoid NullPointerException, because the word dont have out edge will cause this case
            if(graphMap.get(mightBridge) != null){
                if(graphMap.get(mightBridge).GetEdgeInfo().containsKey(tail)){
                    bufferTemp.append(mightBridge);
                    bufferTemp.append(' ');
                    bridge_cnt++;
                }
            }
        }
        
        bridgeWord.bridge = CommonFunc.TreatFile(bufferTemp.toString());
        
        
        if(bridgeWord.bridge.equals(null)){
            System.out.println("m_bridge null");
        }
        if(bridge_cnt == 0) {
        	bridgeWord.isNoneBridge = true;
        } else if (bridge_cnt == 1){
        	bridgeWord.isOneBridge = true;
        } else {
        	bridgeWord.isMultiBridge = true;
        }
        return bridgeWord;
    }
    
    
    
    
    
}
