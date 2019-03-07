/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ccp
 */
public class RegExMatcherTest {
    
    public RegExMatcherTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of recognizes method, of class RegExMatcher.
     */
    @Test
    public void testRecognizes() {
        System.out.println("recognizes");
        String[] patterns = {"(a(b|c+|.?a)*)", "(a|a|b)", "(a|ab|cb?)", "((ab)*)", "((a.)*)"};
        String[] correct = {"abcbaca", "b", "c", "abab", "aaabab"};
        String[] wrong = {"ba", "aa", "bb", "aba", "aaabba"};
        for(int i=0; i<patterns.length;++i){
            boolean result = RegExMatcher.recognizes(patterns[i], correct[i]);
            //System.out.println(RegExMatcher.graph.toString());
            assertEquals(true, result);
            result = RegExMatcher.recognizes(patterns[i], wrong[i]);
            assertEquals(false, result);
        }
    }
    
}
