import java.awt.*;
import java.util.Random;
import java.util.Stack;

public class DrawMaze extends MyFrame {

    int startX =10;
    int startY =40;
    int mazeSize = 30;
    int wallSize = 30;
    int[][] Maze = new int[mazeSize][mazeSize];

    public void run() {
        while(true) {
            initialize();
            Draw();
            CreateMaze();
            Draw();
            sleep(0.5);
            clear();
        }
    }

    private void Draw() {
        int drawXpos = startX;
        int drawYpos = startY;

        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                Random rand = new Random();
                int red = rand.nextInt(255) + 100;
                int ble = rand.nextInt(255) + 100;
                int gre = rand.nextInt(255) + 100;

                switch (Maze[i][j]) {
                    case 0://null
                        setColor(255,255,255);
                        fillRect(drawXpos, drawYpos,wallSize ,wallSize );
                        setColor(0,0,0);
                        drawString(String.valueOf(i)+","+String.valueOf(j),drawXpos+5,drawYpos+10,wallSize/4);
                        break;
                    case 1://InsideWall
                        setColor(red,gre,ble);
                        fillRect(drawXpos, drawYpos,wallSize ,wallSize );
                        setColor(0,0,0);
                        drawString(String.valueOf(i)+","+String.valueOf(j),drawXpos+5,drawYpos+10,wallSize/4);
                        break;
                    case 2://OutsideWall
                        setColor(0,drawXpos-100,drawYpos-100);
                        fillRect(drawXpos, drawYpos,wallSize ,wallSize );
                        setColor(0,0,0);
                        drawString(String.valueOf(i)+","+String.valueOf(j),drawXpos+5,drawYpos+10,wallSize/4);
                        break;
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
                //壁
                if (x==0 || x==mazeSize-1 || y==0 || y==mazeSize-1) {
                    Maze[x][y] = 2;
                }else {
                    Maze[x][y] = 0;
                }
            }
        }
    }

    public void CreateMaze(){
        for(int x = 0; x < mazeSize; x++){
            for(int y = 0; y < mazeSize; y++){
                Random rand = new Random();
                int num = rand.nextInt(10) + 100;
                if (x==0 || x==mazeSize-1 || y==0 || y==mazeSize-1) {
                }else{
                    if (num % 2 == 0) {
                        Maze[x][y] = 1;
                    }else {
                        Maze[x][y] = 0;
                    }
                }
            }
        }
    }
}
