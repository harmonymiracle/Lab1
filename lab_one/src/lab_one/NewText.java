package lab_one;

import java.util.Random;

public class NewText {
    
    public  String generateNewText(String inputText){
        StringBuffer ans = new StringBuffer();
        String split_text[] = CommonFunc.TreatFile(inputText).split(" ");
        String header = split_text[0];
        String tail = "";
        Random rand = new Random();
	    for(Integer i = 1; i < split_text.length; ++i){
	        tail = split_text[i];
	        ans.append(header);
	        ans.append(" ");    

	        BridgeWord bridgeWord = BridgeWord.queryBridgeWords(header, tail);
	        
	        if (bridgeWord.isOneBridge() || bridgeWord.isMultiBridge()) {
	            String bridges[] = bridgeWord.getBridge().split(" ");
	            ans.append(bridges[Math.abs(rand.nextInt()) % bridges.length]);
	            ans.append(" ");
	        }
	        
	        header = tail;
	    }
        ans.append(header);
        return CommonFunc.TreatFile(ans.toString());
    }
    
}
