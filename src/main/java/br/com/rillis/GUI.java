package br.com.rillis;

import br.com.rillis.sort.Dijkstra;
import br.com.rillis.sort.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class GUI extends JFrame {

    Node[][] board;

    Map<Point, JPanel> panels = new HashMap<>();

    Point s;

    public void init(int w, int h, int window_width, int window_height) {
        s = new Point(-1, -1);


        board = new Node[h][w];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                board[y][x] = new Node(new Point(x, y));
            }
        }

        setTitle("PathFinder");
        setSize(window_width, window_height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setLayout(null);


        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setSize(window_width, window_height);
        contentPane.setBackground(new Color(149, 149, 149));
        setContentPane(contentPane);


        window_width -= 1;
        window_height -= 38;

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                JPanel panel = new JPanel();
                panel.setSize(window_width / w, window_height / h);
                panel.setLocation(x * (window_width / w), y * (window_height / h));
                panel.setBackground(new Color(149, 149, 149));
                panel.setBorder(BorderFactory.createLineBorder(new Color(124, 124, 124)));
                contentPane.add(panel);

                final int finalX = x;
                final int finalY = y;

                panels.put(new Point(x, y), panel);

                //mouse release
                panel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseReleased(java.awt.event.MouseEvent evt) {
                        if(evt.getButton() == MouseEvent.BUTTON3){
                            if(board[finalY][finalX].isWall()){
                                board[finalY][finalX].setWall(false);
                                panel.setBackground(new Color(149, 149, 149));
                            }else{
                                board[finalY][finalX].setWall(true);
                                panel.setBackground(new Color(230, 94, 94));
                            }
                        }else if(evt.getButton() == MouseEvent.BUTTON1){
                            if(s.equals(new Point(finalX, finalY))){
                                s = new Point(-1, -1);
                                panel.setBackground(new Color(149, 149, 149));
                            }else if(s.equals(new Point(-1, -1))){
                                s = new Point(finalX, finalY);
                                panel.setBackground(new Color(94, 137, 230));
                            }
                        }
                }
                });

                //mouse enter
                panel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {

                        //paint all if not wall or s
                        for (int y = 0; y < h; y++) {
                            for (int x = 0; x < w; x++) {
                                if(!new Point(x, y).equals(s) && !board[y][x].isWall()){
                                    panels.get(new Point(x, y)).setBackground(new Color(149, 149, 149));
                                }
                            }
                        }


                        if(!s.equals(new Point(-1, -1)) && !s.equals(new Point(finalX, finalY)) && !board[finalY][finalX].isWall()){
                            panel.setBackground(new Color(152, 66, 245));

                            Dijkstra dijkstra = new Dijkstra(board, s, new Point(finalX, finalY));
                            Point[] path = dijkstra.getPath(true);
                            for (Point p : path) {
                                if (p != null && !p.equals(s) && !p.equals(new Point(finalX, finalY)) && !board[p.y][p.x].isWall()) {
                                    panels.get(p).setBackground(new Color(94, 230, 94));
                                }
                            }
                        }



                    }
                });
            }
        }

        contentPane.revalidate();
        contentPane.repaint();

        setVisible(true);
    }


}
