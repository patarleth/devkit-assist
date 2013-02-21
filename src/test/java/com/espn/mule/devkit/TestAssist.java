package com.espn.mule.devkit;

import org.junit.Test;

/**
 *
 * @author arlethp1
 */
public class TestAssist {
    @Test
    public void testTestBean() throws Exception {
        String str = Assist.run("com.espn.mule.devkit.TestBean", "TestBean-connector.xml.sample", "test", "testBean");
        System.out.println(str);
    }
}
