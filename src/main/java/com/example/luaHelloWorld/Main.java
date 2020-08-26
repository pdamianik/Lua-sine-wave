package com.example.luaHelloWorld;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.io.IOUtils;

public class Main
{
    public static void main(String[] args) throws ScriptException, InterruptedException, IOException
    {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream("run.lua");
        assert is != null;
        String script = IOUtils.toString(is, String.valueOf(StandardCharsets.UTF_8));
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine e = mgr.getEngineByExtension(".lua");
        int width = 40;
        double stepSize = 0.005;
        e.put("width", width);
        e.put("stepSize", stepSize);
        String[] render = new String[(int) (2*Math.PI/stepSize)];

        for (int i = 0;i<render.length;i+=1)
        {
            e.put("counter", i);
            render[i] = (String) e.eval(script);
        }

        for (int i = 0; i < render.length || (i = 0) == 0; i++) {
            System.out.print("\n"+render[i]);
            TimeUnit.MILLISECONDS.sleep(5);
        }
    }
}
