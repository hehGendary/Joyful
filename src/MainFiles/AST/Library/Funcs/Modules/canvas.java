package MainFiles.AST.Library.Funcs.Modules;

import MainFiles.AST.JFExpection;
import MainFiles.AST.Library.Funcs.Function;
import MainFiles.AST.Library.Funcs.Functions;
import MainFiles.AST.Library.Variables.Variables;
import MainFiles.AST.Values.ArrayValue;
import MainFiles.AST.Values.NumberValue;
import MainFiles.AST.Values.StringValue;
import MainFiles.AST.Values.Value;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

public final class canvas implements Module {

    private static final NumberValue MINUS_ONE = new NumberValue(-1);

    private static JFrame frame;
    private static CanvasPanel panel;
    private static Graphics2D graphics;
    private static BufferedImage img;

    private static NumberValue lastKey;
    private static ArrayValue mouseHover;

    public static void initConstants() {

        Variables.define("UP", new NumberValue(KeyEvent.VK_UP));
        Variables.define("DOWN", new NumberValue(KeyEvent.VK_DOWN));
        Variables.define("LEFT", new NumberValue(KeyEvent.VK_LEFT));
        Variables.define("RIGHT", new NumberValue(KeyEvent.VK_RIGHT));
        Variables.define("FIRE", new NumberValue(KeyEvent.VK_ENTER));
        Variables.define("ESCAPE", new NumberValue(KeyEvent.VK_ESCAPE));
    }
    public static void init() {
        Functions.define("window", new CreateWindow());
        Functions.define("prompt", new Prompt());
        Functions.define("keypressed", new KeyPressed());
        Functions.define("mousehover", new MouseHover());
        Functions.define("line", intConsumer4Convert(canvas::line));
        Functions.define("oval", intConsumer4Convert(canvas::oval));
        Functions.define("foval", intConsumer4Convert(canvas::foval));
        Functions.define("rect", intConsumer4Convert(canvas::rect));
        Functions.define("frect", intConsumer4Convert(canvas::frect));
        Functions.define("clip", intConsumer4Convert(canvas::clip));
        Functions.define("drawstring", new DrawString());
        Functions.define("color", new SetColor());
        Functions.define("repaint", new Repaint());

        initConstants();

        lastKey = MINUS_ONE;
        mouseHover = new ArrayValue(new Value[] { new NumberValue(0), new NumberValue(0)});
    }

    @FunctionalInterface
    private interface IntConsumer4 {
        void accept(int i1, int i2, int i3, int i4);
    }

    private static void line(int x1, int y1, int x2, int y2) {
        graphics.drawLine(x1, y1, x2, y2);
    }

    private static void oval(int x, int y, int w, int h) {
        graphics.drawOval(x, y, w, h);
    }

    private static void foval(int x, int y, int w, int h) {
        graphics.fillOval(x, y, w, h);
    }

    private static void rect(int x, int y, int w, int h) {
        graphics.drawRect(x, y, w, h);
    }

    private static void frect(int x, int y, int w, int h) {
        graphics.fillRect(x, y, w, h);
    }

    private static void clip(int x, int y, int w, int h) {
        graphics.setClip(x, y, w, h);
    }

    private static Function intConsumer4Convert(IntConsumer4 consumer) {
        return args -> {
            if (args.length != 4) throw new RuntimeException("Four args expected");
            int x = (int) args[0].asNum();
            int y = (int) args[1].asNum();
            int w = (int) args[2].asNum();
            int h = (int) args[3].asNum();
            consumer.accept(x, y, w, h);
            return new NumberValue(0);
        };
    }

    private static class CanvasPanel extends JPanel {

        public CanvasPanel(int width, int height) {
            setPreferredSize(new Dimension(width, height));
            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            graphics = img.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            setFocusable(true);
            requestFocus();
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    lastKey = new NumberValue(e.getKeyCode());
                }
                @Override
                public void keyReleased(KeyEvent e) {
                    lastKey = MINUS_ONE;
                }
            });
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    mouseHover.set(0, new NumberValue(e.getX()));
                    mouseHover.set(1, new NumberValue(e.getY()));
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, 0, 0, null);
        }
    }

    private static class CreateWindow implements Function {

        @Override
        public Value execute(Value... args) {
            String title = "";
            int width = 640;
            int height = 480;
            switch (args.length) {
                case 1:
                    title = args[0].asStr();
                    break;
                case 2:
                    width = (int) args[0].asNum();
                    height = (int) args[1].asNum();
                    break;
                case 3:
                    title = args[0].asStr();
                    width = (int) args[1].asNum();
                    height = (int) args[2].asNum();
                    break;
            }
            panel = new CanvasPanel(width, height);

            frame = new JFrame(title);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel);
            frame.pack();
            frame.setVisible(true);
            return new NumberValue(0);
        }
    }

    private static class KeyPressed implements Function {

        @Override
        public Value execute(Value... args) {
            return lastKey;
        }
    }

    private static class MouseHover implements Function {

        @Override
        public Value execute(Value... args) {
            return mouseHover;
        }
    }

    private static class DrawString implements Function {

        @Override
        public Value execute(Value... args) {
            if (args.length != 3) throw new JFExpection("", "Three args expected");
            int x = (int) args[1].asNum();
            int y = (int) args[2].asNum();
            graphics.drawString(args[0].asStr(), x, y);
            return new NumberValue(0);
        }
    }

    private static class Prompt implements Function {

        @Override
        public Value execute(Value... args) {
            final String v = JOptionPane.showInputDialog(args[0].asStr());
            return new StringValue(v == null ? "0" : v);
        }
    }

    private static class Repaint implements Function {

        @Override
        public Value execute(Value... args) {
            panel.invalidate();
            panel.repaint();
            return new NumberValue(0);
        }
    }

    private static class SetColor implements Function {

        @Override
        public Value execute(Value... args) {
            if (args.length == 1) {
                graphics.setColor(new Color((int) args[0].asNum()));
                return new NumberValue(0);
            }
            int r = (int) args[0].asNum();
            int g = (int) args[1].asNum();
            int b = (int) args[2].asNum();
            graphics.setColor(new Color(r, g, b));
            return new NumberValue(0);
        }

    }
}