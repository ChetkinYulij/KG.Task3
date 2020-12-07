package ru.vsu.cs.course2;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow() throws HeadlessException{
        this.add(new DrawPanel());
    }
}
