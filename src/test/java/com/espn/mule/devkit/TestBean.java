package com.espn.mule.devkit;

import com.sun.org.apache.bcel.internal.classfile.InnerClass;
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

    public TheInnerClass[] getTheInnerClassArray(int i) {
        return new TheInnerClass[0];
    }

    public OuterClass getOuterClass(Long l) {
        return new OuterClass();
    }

    public OuterClass[] getOuterClassArray(Long l) {
        return new OuterClass[0];
    }

    public void setOuterClassArray(OuterClass[] outterClassArray) {
    }

    public void setTheInnerClassArray(TheInnerClass[] innerClassArray) {
    }

    public Random getRandom() {
        return new Random();
    }

    public void setRandom(Random random) {
    }
}
