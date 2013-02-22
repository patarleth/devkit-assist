package com.espn.mule.devkit;

import java.util.Random;

/**
 *
 * @author arlethp1
 */
public class TestBean {

    public static class TheInnerClass {

        public String yupGoGitIt() {
            return "it";
        }
    }

    public TestBean(String str) {
    }

    public String getStringValue() {
        return "string value";
    }

    public void setStringValue(String theStringValue) {
        //do nothing
    }

    public static long getTime() {
        return System.currentTimeMillis();
    }

    public static long getRandomLongNumber(long seed) {
        Random r = new Random(seed);
        return r.nextLong();
    }
    
    public TheInnerClass getTheInnerClass(int i) {
        return new TheInnerClass();
    }
    
    public OutterCass getOutterClass(Long l) {
        return new OutterCass();
    }
}
