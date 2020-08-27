package com.example.luaHelloWorld;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;

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

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new Window());
        frame.pack();
        frame.setVisible(true);

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

    public static class Window extends JPanel {
        private int i = 0;

        public Window()
        {
            setPreferredSize(new Dimension(1000, 1000));
            Timer timer = new Timer(100, e -> this.repaint());
            timer.start();
        }

        private static void drawPixel(Graphics g, int x, int y) {
            g.drawRect(x, y, 1, 1);
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            for (int x = 0; x < getWidth(); x++) {
                for (int y = 0; y < getHeight(); y++) {
                    int red = (x*x+y*y+i*20)%256;
                    int green = (x+y+i*10)%256;
                    int blue = (x*y+i*10)%256;
                    g.setColor((new Color(red, green, blue)));
                    drawPixel(g, x, y);
                }
            }
            i++;
        }
    }
}
