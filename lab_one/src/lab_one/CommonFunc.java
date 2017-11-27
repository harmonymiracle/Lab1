package lab_one;

public class CommonFunc {
    
    
    // make the text formal
    public static String TreatFile(String text)
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

    private static boolean CheckValidity(char c){
        return (c >= 'a' && c <= 'z');
    }

}
