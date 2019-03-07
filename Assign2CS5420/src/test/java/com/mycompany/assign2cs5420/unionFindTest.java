package com.mycompany.assign2cs5420;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;

public class unionFindTest {
    private unionFind[] ufs;
    public unionFindTest() {
        String[] fileNames = {
            "tinyUG.txt",
            "mediumUG.txt"
        };
        ufs = new unionFind[fileNames.length];
        int iter =0;
        for(String file : fileNames){
            StopWatch timer = new StopWatch();
            ufs[iter++] = new unionFind(file);
            System.out.println(timer.elapsedTime());
        }
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
     * Test of count method, of class unionFind.
     */
    @org.junit.jupiter.api.Test
    public void testCount() {
        assertEquals(ufs[0].count(), 4);
        assertEquals(ufs[1].count(), 3);
    }
}
