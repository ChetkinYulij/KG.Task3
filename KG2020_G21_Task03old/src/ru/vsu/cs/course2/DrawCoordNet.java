package ru.vsu.cs.course2;

import ru.vsu.cs.course2.converter.RealPoint;
import ru.vsu.cs.course2.converter.ScreenConverter;
import ru.vsu.cs.course2.converter.ScreenPoint;
import ru.vsu.cs.course2.drawers.line.LineDrawer;

import java.awt.*;
import java.text.DecimalFormat;

public class DrawCoordNet {

    public static void draw(CoordNet cn, LineDrawer ld, ScreenConverter sc, ScreenPoint windowSize, Graphics g) {
        int step = 20;
        int stepX = step, stepY = step;
        int stepControl = 40;
        cn.setsX(windowSize.getX() / step);
        if (cn.getsX() < stepControl) {
            stepX = windowSize.getX() / stepControl;
            cn.setsX(windowSize.getX() / stepX);
        }
        cn.setsY(windowSize.getY() / step);
        if (cn.getsY() < stepControl) {
            stepY = windowSize.getY() / stepControl;
            cn.setsY(windowSize.getY() / stepY);
        }
        RealPoint rp1 = sc.s2r(new ScreenPoint(cn.getsX(), cn.getsY()));
        RealPoint rp2 = sc.s2r(new ScreenPoint(0, 0));
        cn.setrX(rp1.getX() - rp2.getX());
        cn.setrY(rp1.getY() - rp2.getY());

        RealPoint p1;
        if (rp2.getX() < 0) {
            p1 = new RealPoint(rp2.getX() - rp2.getX() % cn.getrX() - cn.getrX(), 0);
        } else {
            p1 = new RealPoint(rp2.getX() - rp2.getX() % cn.getrX(), 0);
        }
        DecimalFormat df = new DecimalFormat("###.##");
        for (int i = 0; i <= stepX; i++) {
            ScreenPoint sp = sc.r2s(p1);
            ld.drawLine(sp.getX(), 0, sp.getX(), windowSize.getY());
            g.drawString(df.format(p1.getX()) + "", sp.getX() + 5, windowSize.getY() - 5);
            p1 = new RealPoint(p1.getX() + cn.getrX(), 0);
        }
        if (rp2.getY() < 0) {
            p1 = new RealPoint(0, rp2.getY() - rp2.getY() % cn.getrY() - cn.getrY());
        } else {
            p1 = new RealPoint(0, rp2.getY() - rp2.getY() % cn.getrY());
        }
        for (int i = 0; i <= stepY ; i++) {
            ScreenPoint sp = sc.r2s(p1);
            ld.drawLine(0, sp.getY(), windowSize.getX(), sp.getY());
            g.drawString(df.format(p1.getY()) + "", 5, sp.getY() - 5);
            p1 = new RealPoint(0, p1.getY() + cn.getrY());
        }
    }
}
