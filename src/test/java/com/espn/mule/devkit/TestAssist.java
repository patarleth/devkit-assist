package com.espn.mule.devkit;

import org.junit.Test;

/**
 *
 * @author arlethp1
 */
public class TestAssist {
    
    @Test
    public void testTestBean() throws Exception {
        String str = Assist.run("com.espn.mule.devkit.TestBean", "SampleScope-connector.xml.sample", "samplescope", "testBean");
        System.out.println(str);
    }
    
    @Test
    public void testIconResize() throws Exception {
        Assist.resizeMulePng(TestAssist.class.getResource("/kg.jpg").toString(), "target/icons", "test");
    }
}
