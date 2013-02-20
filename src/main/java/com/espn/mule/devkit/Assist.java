package com.espn.mule.devkit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author arlethp1
 */
public class Assist {

    public static String methodToMule(String methodName) {
        String result = methodName.replaceAll("(\\p{javaUpperCase}+)", "-$1");
        result = result.replaceAll("(\\p{javaUpperCase}{2,}+)", "$1-");
        if (result.startsWith("-")) {
            result = result.substring(1, result.length());
        }
        result = result.replaceAll("--", "-").toLowerCase();        
        return result;
    }

    public static abstract class Handler {

        HashMap<String, StringBuilder> resultMap = new HashMap<String, StringBuilder>();
        ArrayList<String> names = new ArrayList<String>();

        public synchronized StringBuilder getStringBuilder(Method method) {
            String name = method.getName();
            StringBuilder sb = resultMap.get(name);
            if (sb == null) {
                names.add(name);
                sb = new StringBuilder();
                this.resultMap.put(name, sb);
            }
            return sb;
        }

        public abstract void handle(Method m);

        @Override
        public String toString() {
            StringBuilder resultSb = new StringBuilder();
            for (String name : names) {
                StringBuilder sb = resultMap.get(name);
                resultSb.append(sb).append("\n");
            }
            return resultSb.toString();
        }
    }
    private static final HashSet<String> bogusMethodNameSet = new HashSet<String>();

    static {
        for (Method m : java.lang.Object.class.getMethods()) {
            bogusMethodNameSet.add(m.getName());
        }
        for (Method m : java.lang.Class.class.getMethods()) {
            bogusMethodNameSet.add(m.getName());
        }
    }

    public static void handlePublicMethods(Class c, List<Handler> handlers) {
        Method[] methods = c.getMethods();
        HashSet<String> nameset = new HashSet<String>(bogusMethodNameSet);
        for (Method m : methods) {
            if (nameset.add(m.getName())) {
                for (Handler h : handlers) {
                    h.handle(m);
                }
            }
        }
    }

    public static class JavadocHandler extends Handler {

        String namespace;
        String sampleXmlFileName;

        public JavadocHandler(String namespace, String sampleXmlFileName) {
            this.namespace = namespace;
            this.sampleXmlFileName = sampleXmlFileName;
        }

        @Override
        public void handle(Method m) {
            StringBuilder sb = getStringBuilder(m);
            sb.append("    /**\n");
            sb.append("     * ").append(m.getName()).append("\n");
            sb.append("     *\n");
            sb.append("     * {@sample.xml ../../../doc/").
                    append(this.sampleXmlFileName).append(" ").
                    append(this.namespace).append(":").append(methodToMule(m.getName())).append("}\n");
            Class[] paramTypes = m.getParameterTypes();
            if (paramTypes != null) {
                for (int i = 0; i < paramTypes.length; i++) {
                    Class c = paramTypes[i];
                    sb.append("     * @param ");
                    sb.append("arg").append(i).append(" ");
                    sb.append(c.getName()).append("\n");
                }
            }

            Class r = m.getReturnType();
            if (r.equals(Void.TYPE)) {
                //print nothing
            } else {
                sb.append("     * @return \n");
            }

            Class[] exceptions = m.getExceptionTypes();
            if (exceptions.length > 0) {
                sb.append("     * @throws");
                for (Class c : exceptions) {
                    sb.append(" ").append(c.getName());
                }
            }
            sb.append("     */");
        }
    }

    public static class ConnectorMethodHandler extends Handler {

        String variableName;

        public ConnectorMethodHandler(String variableName) {
            this.variableName = variableName;
        }

        @Override
        public void handle(Method m) {
            StringBuilder sb = getStringBuilder(m);
            sb.append("    @Processor\n");
            sb.append("    public ");
            Class r = m.getReturnType();
            if (r.equals(Void.TYPE)) {
                sb.append("void");
            } else if (r.isArray()) {
                sb.append(r.getComponentType().getName()).append("[]");
            } else {
                sb.append(r.getName());
            }
            sb.append(" ").append(m.getName()).append("(");

            Class[] paramTypes = m.getParameterTypes();
            for (int i = 0; i < paramTypes.length; i++) {
                Class c = paramTypes[i];

                if (i > 0) {
                    sb.append(",");
                }
                if (c.isArray()) {
                    sb.append(" ").append(c.getComponentType().getName()).append("[]");
                } else {
                    sb.append(" ").append(c.getName());
                }
                sb.append(" arg").append(i);
            }
            if (paramTypes.length > 0) {
                sb.append(" ");
            }

            sb.append(")");

            Class[] exceptions = m.getExceptionTypes();
            if (exceptions.length > 0) {
                sb.append(" throws");
                for (Class c : exceptions) {
                    sb.append(" ").append(c.getName());
                }
            }

            sb.append(" {\n");
            if (r.equals(Void.TYPE)) {
                sb.append("        ").append(variableName).append(m.getName()).append("(");
            } else {
                sb.append("        return ").append(variableName).append(m.getName()).append("(");
            }

            for (int i = 0; i < paramTypes.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append("arg").append(i);
            }
            sb.append(");\n");
            sb.append("    }\n\n");
        }
    }

    public static class TestMuleConfigHandler extends Handler {

        String namespace;

        public TestMuleConfigHandler(String namespace) {
            this.namespace = namespace;
        }

        @Override
        public void handle(Method m) {
            StringBuilder sb = getStringBuilder(m);
            sb.append("    <flow name=\"").append(m.getName()).append("Flow\">\n");

            sb.append("        <").append(namespace).append(":").append(methodToMule(m.getName()));

            Class[] paramTypes = m.getParameterTypes();

            for (int i = 0; i < paramTypes.length; i++) {
                sb.append(" arg").append(i).append("=\"\"");
            }
            sb.append("/>\n").append("    </flow>\n\n");
        }
    }

    public static class ConnectorSampleXmlHandler extends Handler {

        String namespace;

        public ConnectorSampleXmlHandler(String namespace) {
            this.namespace = namespace;
        }

        @Override
        public void handle(Method m) {
            StringBuilder sb = getStringBuilder(m);
            String mname = methodToMule(m.getName());
            sb.append("\n<!-- BEGIN_INCLUDE(").append(namespace).append(":").append(mname).append(") -->\n");
            sb.append("    <").append(namespace).append(":").append(mname);
            Class[] paramTypes = m.getParameterTypes();
            for (int i = 0; i < paramTypes.length; i++) {
                sb.append(" arg").append(i).append("=\"#[map-payload:").append("arg").append(i).append("]\"");
            }
            sb.append(" -->\n");
            sb.append("<!-- END_INCLUDE(").append(namespace).append(":").append(mname).append(") -->");
        }
    }

    public static void run(String classname, String sampleXmlFileName, String namespace, String variableName) throws Exception {

        Class c = Class.forName(classname);

        ConnectorMethodHandler connectorMethodHandler = new ConnectorMethodHandler(variableName);
        ConnectorSampleXmlHandler connectorSampleXmlHandler = new ConnectorSampleXmlHandler(namespace);
        JavadocHandler javadocHandler = new JavadocHandler(namespace, sampleXmlFileName);
        TestMuleConfigHandler testMuleConfigHandler = new TestMuleConfigHandler(namespace);

        ArrayList<Handler> handlers = new ArrayList<Handler>();
        handlers.add(connectorMethodHandler);
        handlers.add(connectorSampleXmlHandler);
        handlers.add(javadocHandler);
        handlers.add(testMuleConfigHandler);

        handlePublicMethods(c, handlers);

        System.out.println(testMuleConfigHandler);

        System.out.println("\n\n\n");
        System.out.println(" ---------------------------------------- ");
        System.out.println("\n\n\n");

        System.out.println(connectorSampleXmlHandler);

        System.out.println("\n\n\n");
        System.out.println(" ---------------------------------------- ");
        System.out.println("\n\n\n");

        for (String name : connectorMethodHandler.names) {
            System.out.println(javadocHandler.resultMap.get(name));
            System.out.println(connectorMethodHandler.resultMap.get(name));
            System.out.println();
        }

        System.out.println("\n\n\n");
        System.out.println(" ---------------------------------------- ");
        System.out.println("\n\n\n");

    }

    public static void main(String[] args) throws Exception {
        String classname = "java.lang.Math";
        String sampleXmlFileName = "Math-connector.xml.sample";
        String namespace = "math";
        String variableName = "math";

        if (args != null && args.length == 4) {
            classname = args[0];
            sampleXmlFileName = args[1];
            namespace = args[2];
            variableName = args[3];
        }

        run(classname, sampleXmlFileName, namespace, variableName);
    }
}