package ru.vsu.cs.course2;

import ru.vsu.cs.course2.converter.Line;
import ru.vsu.cs.course2.converter.RealPoint;
import ru.vsu.cs.course2.converter.ScreenConverter;
import ru.vsu.cs.course2.converter.ScreenPoint;
import ru.vsu.cs.course2.drawers.line.*;
import ru.vsu.cs.course2.drawers.pixel.GraphicsPixelDrawer;
import ru.vsu.cs.course2.drawers.pixel.PixelDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener {
    private ScreenConverter sc = new ScreenConverter(
            -10, 190, 200, 200, 800, 600);

    private Line xAxis = new Line(0, 0, 200, 0);
    private Line yAxis = new Line(0, 0, 0, 200);
    private ScreenPoint prevPoint = null;
    private int x = 0, y = 0;


    public DrawPanel() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage bf = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics gr = bf.createGraphics();
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, getWidth(), getHeight());
        PixelDrawer pd = new GraphicsPixelDrawer(gr);
        LineDrawer ld = new GraphicsLineDrawer(gr);

        drawAll(ld,gr);
        g.drawImage(bf, 0, 0, null);
        gr.dispose();
    }

    public void drawAll(LineDrawer ld,Graphics g) {
        CoordNet cn = new CoordNet();
        ScreenPoint sp = new ScreenPoint(this.getWidth(),this.getHeight());
        drawLine(ld, xAxis);
        drawLine(ld, yAxis);
        mouseLocation(g);
        DrawCoordNet.draw(cn,ld,sc,sp,g);
    }

    private void drawLine(LineDrawer ld, Line l) {
        ScreenPoint p1 = sc.r2s(l.getP1());
        ScreenPoint p2 = sc.r2s(l.getP2());
        ld.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    private void mouseLocation(Graphics g) {
        RealPoint r1 = sc.s2r(new ScreenPoint(x, y));
        DecimalFormat df = new DecimalFormat("###.###");
        g.drawString(df.format(r1.getX()) + " " + df.format(r1.getY()), x, y);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        prevPoint = new ScreenPoint(e.getX(), e.getY());
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        ScreenPoint currentPoint = new ScreenPoint(e.getX(), e.getY());
        if (prevPoint != null) {
            ScreenPoint deltaScreen = new ScreenPoint(
                    currentPoint.getX() - prevPoint.getX(),
                    currentPoint.getY() - prevPoint.getY()
            );
            RealPoint deltaReal = sc.s2r(deltaScreen);
            double vectorX = deltaReal.getX() - sc.getCornerX();
            double vectorY = deltaReal.getY() - sc.getCornerY();

            sc.setCornerX(sc.getCornerX() - vectorX);
            sc.setCornerY(sc.getCornerY() - vectorY);
            prevPoint = currentPoint;
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        double scale = 1;
        double step = (clicks < 0) ? 0.9 : 1.1;
        for (int i = Math.abs(clicks); i > 0; i--) {
            scale *= step;
        }
        sc.setRealW(scale * sc.getRealW());
        sc.setRealH(scale * sc.getRealH());
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {


    }
}
