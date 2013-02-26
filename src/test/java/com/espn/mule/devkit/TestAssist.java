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
        Assist.resizeMulePng("http://25.media.tumblr.com/tumblr_lsrjphblyh1qh8opwo1_400.jpg", "target/icons", "test");
    }
}
