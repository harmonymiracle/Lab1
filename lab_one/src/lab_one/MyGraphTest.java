package lab_one;

import static org.junit.Assert.*;

import org.junit.Test;

public class MyGraphTest {

    @Test
    public void testGenerateNewText() {
        MyGraph graph = new MyGraph ();
        graph.testMain();
        String inputText = ""; // input is ""
        String newText = graph.generateNewText(inputText);
        System.out.println(newText);
        assertEquals (newText, "");
        
        inputText = "study"; // input is ""
        newText = graph.generateNewText(inputText);
        System.out.println(newText);
        assertEquals (newText, "study");
        
        inputText = "study when"; // input is ""
        newText = graph.generateNewText(inputText);
        System.out.println(newText);
        assertEquals (newText, "study when");
        
        inputText = "study holiday"; // input is ""
        newText = graph.generateNewText(inputText);
        System.out.println(newText);
        assertEquals (newText, "study when holiday");
        
        inputText = "and study"; // input is ""
        newText = graph.generateNewText(inputText);
        System.out.println(newText);
        assertEquals (newText, "and well study");
        // if not right, just use this statement:
        // assertEquals (newText, "and to study");
        
    }

}
