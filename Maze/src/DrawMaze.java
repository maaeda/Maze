import java.awt.*;
import java.util.Random;
import java.util.Stack;

public class DrawMaze extends MyFrame {

    int startX =10;
    int startY =40;
    int mazeSize =33;
    int wallSize = 25;
    int[][] Maze = new int[mazeSize][mazeSize];

    public void run() {
        if (mazeSize % 2 == 0) {
            mazeSize = mazeSize - 1;
        }
        while(true) {
            initialize();
            Draw();
            CreateMaze();
            Draw();
            sleep(1);
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
                int addRed = 255 / mazeSize;
                int addGreen = 255 / mazeSize;
                int addBlue = 255 / mazeSize;

                switch (Maze[i][j]) {
                    case 0://道
                        setColor(255,255,255);
                        break;
                    case 1://中の壁
                        setColor(0,addGreen*i,addBlue*j);
                        break;
                    case 2://外の壁
                        setColor(0,addGreen*i,addBlue*j);
                        break;
                    case 3://スタート
                        setColor(255,0,0);
                        break;
                    case 4://ゴール
                        setColor(0,255,0);
                        break;
                }
                fillRect(drawXpos, drawYpos,wallSize ,wallSize );
                setColor(0,0,0);
                drawString(String.valueOf(i)+","+String.valueOf(j),drawXpos+5,drawYpos+10,wallSize/4);
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
                } else {
                    Maze[x][y] = 1;
                }
            }
        }
        Maze[1][1] = 3;
        Maze[mazeSize-2][mazeSize-2] = 4;
    }

    public void CreateMaze(){
        Stack<Point> stack = new Stack<>();
        Random rand = new Random();
        stack.push(new Point(1, 1));

        while (!stack.isEmpty()) {
            Point current = stack.peek();
            int x = current.x;
            int y = current.y;

            Maze[x][y] = 0;

            // 進行方向のリストをランダムにシャッフル
            Point[] directions = {
                    new Point(x, y - 2),
                    new Point(x, y + 2),
                    new Point(x - 2, y),
                    new Point(x + 2, y)
            };
            for (int i = 0; i < directions.length; i++) {
                int j = rand.nextInt(directions.length);
                Point temp = directions[i];
                directions[i] = directions[j];
                directions[j] = temp;
            }

            boolean moved = false;
            for (Point direction : directions) {
                int nx = direction.x;
                int ny = direction.y;

                if (nx > 0 && nx < mazeSize - 1 && ny > 0 && ny < mazeSize - 1 && Maze[nx][ny] == 1) {
                    //if (nx == mazeSize - 2 && ny == mazeSize - 2) continue;  // ゴール地点は避ける

                    Maze[nx][ny] = 0;
                    Maze[(x + nx) / 2][(y + ny) / 2] = 0;
                    stack.push(new Point(nx, ny));
                    moved = true;
                    break;
                }
            }

            if (!moved) {
                stack.pop();
            }
            Draw();
            //sleep(0.000001);
        }
        Maze[mazeSize-3][mazeSize-2] = 0;
        Maze[mazeSize-2][mazeSize-3] = 0;
        Maze[mazeSize-3][mazeSize-3] = 0;
        Maze[1][1] = 3;
        Maze[mazeSize-2][mazeSize-2] = 4;
    }
}
