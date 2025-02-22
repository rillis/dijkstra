package br.com.rillis.sort;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Dijkstra {

    List<Point> open_list = null;
    List<Point> closed_list = null;
    Map <Point, Integer> values = null;
    Map <Point, Point> path_list = null;
    List<Point> interest = null;

    Node[][] board;
    Point s;
    Point e;


    private Node[] getAdjacent(Point point, boolean diagonal){
        ArrayList<Node> nodes = new ArrayList<>();

        int y = point.y;
        int x = point.x;

        if (y < board.length-1 && !board[y+1][x].isWall()){
            nodes.add(new Node(new Point(x, y+1), 10));
        }
        if (x < board.length-1 && !board[y][x+1].isWall()){
            nodes.add(new Node(new Point(x+1, y), 10));
        }
        if (y > 0 && !board[y-1][x].isWall()){
            nodes.add(new Node(new Point(x, y-1), 10));
        }
        if (x > 0 && !board[y][x-1].isWall()){
            nodes.add(new Node(new Point(x-1, y), 10));
        }

        if(diagonal){
            if (y < board.length-1 && x < board.length-1 && !board[y+1][x+1].isWall()){
                nodes.add(new Node(new Point(x+1, y+1), 14));
            }
            if (y > 0 && x > 0 && !board[y-1][x-1].isWall()){
                nodes.add(new Node(new Point(x-1, y-1), 14));
            }
            if (y > 0 && x < board.length-1 && !board[y-1][x+1].isWall()){
                nodes.add(new Node(new Point(x+1, y-1), 14));
            }
            if (y < board.length-1 && x > 0 && !board[y+1][x-1].isWall()){
                nodes.add(new Node(new Point(x-1, y+1), 14));
            }

        }

        return nodes.toArray(new Node[0]);

    }

    private Node[] getAdjacent(Point point){
        return getAdjacent(point, false);
    }

    private boolean iterate(boolean diagonal){
        for (Point pointInterest : interest){
            open_list.remove(pointInterest);
            closed_list.add(pointInterest);

            Node[] adjacents = getAdjacent(pointInterest, diagonal);
            for(Node adjacent : adjacents){
                if(!open_list.contains(adjacent.getPoint()) && !closed_list.contains(adjacent.getPoint())){
                    open_list.add(adjacent.getPoint());
                }

                if(!adjacent.getPoint().equals(s)){
                    int min_value = Integer.MAX_VALUE;
                    Point min_point = null;

                    for(Node adjacent2 : getAdjacent(adjacent.getPoint(), diagonal)){
                        if(values.containsKey(adjacent2.getPoint())){
                            if ((values.get(adjacent2.getPoint()) + adjacent.getValue()) < min_value){
                                min_value = values.get(adjacent2.getPoint()) + adjacent.getValue();
                                min_point = adjacent2.getPoint();
                            }
                        }
                    }

                    if (min_value != Integer.MAX_VALUE){
                        values.put(adjacent.getPoint(), min_value);
                        path_list.put(adjacent.getPoint(), min_point);
                    }

                }
            }

            if(path_list.containsKey(e)){
                return true;
            }
        }

        return false;
    }

    private void calculateInterest(){
        int min_value = Integer.MAX_VALUE;
        for (Point point : open_list){
            if (values.containsKey(point)){
                if (values.get(point) < min_value){
                    min_value = values.get(point);
                }
            }
        }
        interest.clear();
        for (Point point : open_list){
            if (values.containsKey(point)){
                if (values.get(point) == min_value){
                    interest.add(point);
                }
            }
        }
    }

    public Dijkstra(Node[][] board, Point s, Point e){
        this.board = board;
        this.s = s;
        this.e = e;

        open_list = Collections.synchronizedList(new ArrayList<Point>());
        closed_list = Collections.synchronizedList(new ArrayList<Point>());
        interest = Collections.synchronizedList(new ArrayList<Point>());
        values = new HashMap<Point, Integer>();
        path_list = new HashMap<Point, Point>();

        open_list.add(s);
        values.put(s, 0);
        interest.add(s);
    }

    public Point[] getPath(boolean diagonal){
        for (int i = 0; i < 1500; i++) {
            if (iterate(diagonal)) break;
            else{
                calculateInterest();
            }
        }

        List<Point> path = new ArrayList<>();
        Point point = e;
        System.out.println(e);
        path.add(point);
        while (!point.equals(s)){
            point = path_list.get(point);
            path.add(point);
        }
        return path.toArray(new Point[0]);
    }


}
