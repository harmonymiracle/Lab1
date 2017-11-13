package lab_one;

import static org.junit.Assert.*;

import org.junit.Test;

public class MyGraphTest {
	//black box test
	private static MyGraph graph = new MyGraph();
	@Before
	public void setUp() throws Exception {
		graph.LoadTestText();
	}

	@Test
	public void testQueryBridgeWords() {
		graph.queryBridgeWords("my", "target");
		assertEquals("main", graph.Get_Bridge());
		
		graph.queryBridgeWords("me", "empty");
		assertEquals("feel", graph.Get_Bridge());
		
		graph.queryBridgeWords("have", "nothing");
		assertEquals("done", graph.Get_Bridge());
		
		graph.queryBridgeWords("then", "make");
		assertEquals("", graph.Get_Bridge());
		
		graph.queryBridgeWords("I", "a");
		assertEquals("set", graph.Get_Bridge());

		graph.queryBridgeWords("is", "for");
		assertEquals("needed time", graph.Get_Bridge());
		
		graph.queryBridgeWords("needed", "me");
		assertEquals("for", graph.Get_Bridge());
		
		graph.queryBridgeWords("all", "the");
		assertEquals("", graph.Get_Bridge());
		
		graph.queryBridgeWords("the", "makes");
		assertEquals("time", graph.Get_Bridge());
		
		graph.queryBridgeWords("make", "happy");
		assertEquals("", graph.Get_Bridge());	
		//fail("Not yet implemented");
	}
	
	// white box test
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
