
package lab_one;

import java.lang.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Graph {
    
    public static HashMap<String, MyValue> graphMap_;
    public static HashMap<String, MyValue> signMap_;

    public static HashMap<String, MyValue> GetGraph(){
        return graphMap_;
    }
    public static HashMap<String, MyValue> GetSign(){
        return signMap_;
    }

    public static void SetGraphMap(HashMap<String, MyValue> graphMap){
    	graphMap_ = graphMap;
    	System.out.println("in SetGraphMap");
    }
    public static void SetSignMap(HashMap<String, MyValue> signMap){
    	signMap_ = signMap;
    }

}






