import java.awt.*;
import java.util.Random;
import java.util.Stack;

public class DrawMaze extends MyFrame {

    int startX =10;
    int startY =40;
    int mazeSize = 31;
    int wallSize = 15;
    int[][] Maze = new int[mazeSize][mazeSize];

    public void run() {
        initialize();
        Draw();
    }

    private void Draw() {
        int drawXpos = startX;
        int drawYpos = startY;

        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                if (Maze[i][j] == 0) {
                    setColor(drawYpos,0,drawXpos);
                    fillRect(drawXpos, drawYpos,wallSize ,wallSize );
                    //drawString(String.valueOf(drawXpos)+String.valueOf(drawYpos),drawXpos,drawYpos,20);
                }
                drawYpos += wallSize;
            }
            drawXpos += wallSize;
            drawYpos = startY;
        }
    }

    public void initialize(){
        for(int x = 0; x < mazeSize; x++){
            for(int y = 0; y < mazeSize; y++){
                Random rand = new Random();
                int num = rand.nextInt(10) + 100;
                if (num % 2 == 0) {
                    Maze[x][y] = 1;
                }else {
                    Maze[x][y] = 0;
                }
            }
        }
    }




}
