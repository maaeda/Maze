import java.awt.*;
import java.util.Random;
import java.util.Stack;

public class DrawMaze extends MyFrame {

    int startX = 10;
    int startY = 40;
    int mazeSize = 31;
    int wallSize = 30;
    int[][] Maze;
    Random rand = new Random();
    int startRow = 1;
    int startCol = 1;
    int goalRow;
    int goalCol;
    Stack<int[]> path;

    public DrawMaze() {
        Maze = new int[mazeSize][mazeSize];
        goalRow = mazeSize - 2;
        goalCol = mazeSize - 2;
    }

    public void run() {
        while (true) {
            initialize();
            CreateMaze();
            setStartAndGoal();
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
                int randRed = rand.nextInt(155) + 100; // 道の色は明るい範囲から選ぶ
                int randGreen = rand.nextInt(155) + 100;
                int randBlue = rand.nextInt(155) + 100;
                int addRed = 255 / mazeSize;
                int addGreen = 255 / mazeSize;
                int addBlue = 255 / mazeSize;

                if (i == startRow && j == startCol) {
                    setColor(0, 255, 0); // スタート位置は緑色
                } else if (i == goalRow && j == goalCol) {
                    setColor(255, 0, 0); // ゴール位置は赤色
                } else if (isInPath(i, j)) {
                    setColor(0, 0, 255); // パスは青色で表示
                } else {
                    switch (Maze[i][j]) {
                        case 0: // 道
                            setColor(255,255,255);
                            break;
                        case 1: // 中の壁
                            setColor(0, addGreen * i, addBlue * j);
                            break;
                        case 2: // 外側の壁
                            setColor(0, addGreen * i, addBlue * j);
                            break;
                    }
                }
                fillRect(drawXpos, drawYpos, wallSize, wallSize);
                setColor(0, 0, 0);
                drawString(String.valueOf(i) + "," + String.valueOf(j), drawXpos + 5, drawYpos + 10, wallSize / 4);
                drawYpos += wallSize;
            }
            drawXpos += wallSize;
            drawYpos = startY;
        }
    }

    public void initialize() {
        for (int x = 0; x < mazeSize; x++) {
            for (int y = 0; y < mazeSize; y++) {
                // 外側の壁
                if (x == 0 || x == mazeSize - 1 || y == 0 || y == mazeSize - 1) {
                    Maze[x][y] = 2;
                } else {
                    Maze[x][y] = 1; // 初期状態は全て中の壁
                }
            }
        }
    }

    public void CreateMaze() {
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{startRow, startCol});
        Maze[startRow][startCol] = 0;

        while (!stack.isEmpty()) {
            int[] current = stack.peek();
            int x = current[0];
            int y = current[1];

            int[][] directions = {
                    {0, 2}, // 下
                    {0, -2}, // 上
                    {2, 0}, // 右
                    {-2, 0} // 左
            };

            shuffleArray(directions); // 方向をランダムにシャッフル

            boolean moved = false;
            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (newX > 0 && newX < mazeSize - 1 && newY > 0 && newY < mazeSize - 1 && Maze[newX][newY] == 1) {
                    Maze[newX][newY] = 0;
                    Maze[x + dir[0] / 2][y + dir[1] / 2] = 0;
                    stack.push(new int[]{newX, newY});
                    moved = true;
                    break;
                }
            }
            if (!moved) {
                stack.pop();
            }
        }

        // 連結成分をチェックしてゴールに到達できるようにする
        if (!isReachable(startRow, startCol, goalRow, goalCol)) {
            connectStartToGoal();
        }

        // スタートからゴールまでのパスを探す
        path = new Stack<>();
        findPath(startRow, startCol, goalRow, goalCol);
    }

    private void setStartAndGoal() {
        Maze[startRow][startCol] = 0; // スタート位置を道に設定
        Maze[goalRow][goalCol] = 0;   // ゴール位置を道に設定
    }

    private void shuffleArray(int[][] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int[] temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    private boolean isReachable(int startX, int startY, int endX, int endY) {
        boolean[][] visited = new boolean[mazeSize][mazeSize];
        return dfs(startX, startY, endX, endY, visited);
    }

    private boolean dfs(int x, int y, int endX, int endY, boolean[][] visited) {
        if (x == endX && y == endY) return true;
        if (x < 0 || x >= mazeSize || y < 0 || y >= mazeSize || visited[x][y] || Maze[x][y] == 1 || Maze[x][y] == 2) return false;

        visited[x][y] = true;

        int[][] directions = {
                {0, 1}, // 右
                {1, 0}, // 下
                {0, -1}, // 左
                {-1, 0} // 上
        };

        for (int[] dir : directions) {
            if (dfs(x + dir[0], y + dir[1], endX, endY, visited)) {
                return true;
            }
        }

        return false;
    }

    private void connectStartToGoal() {
        Stack<int[]> path = new Stack<>();
        path.push(new int[]{startRow, startCol});
        boolean[][] visited = new boolean[mazeSize][mazeSize];
        visited[startRow][startCol] = true;

        while (!path.isEmpty()) {
            int[] current = path.peek();
            int x = current[0];
            int y = current[1];

            if (x == goalRow && y == goalCol) break;

            int[][] directions = {
                    {0, 1}, // 右
                    {1, 0}, // 下
                    {0, -1}, // 左
                    {-1, 0} // 上
            };

            boolean moved = false;
            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (newX > 0 && newX < mazeSize - 1 && newY > 0 && newY < mazeSize - 1 && !visited[newX][newY] && Maze[newX][newY] != 1 && Maze[newX][newY] != 2) {
                    path.push(new int[]{newX, newY});
                    visited[newX][newY] = true;
                    moved = true;
                    break;
                }
            }
            if (!moved) {
                path.pop();
            }
        }

        while (!path.isEmpty()) {
            int[] cell = path.pop();
            Maze[cell[0]][cell[1]] = 0;
        }
    }

    private boolean findPath(int x, int y, int endX, int endY) {
        if (x == endX && y == endY) {
            path.push(new int[]{x, y});
            return true;
        }
        if (x < 0 || x >= mazeSize || y < 0 || y >= mazeSize || Maze[x][y] != 0) return false;

        Maze[x][y] = 3; // 一時的に訪問済みとしてマーク

        int[][] directions = {
                {0, 1}, // 右
                {1, 0}, // 下
                {0, -1}, // 左
                {-1, 0} // 上
        };

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (findPath(newX, newY, endX, endY)) {
                path.push(new int[]{x, y});
                return true;
            }
        }

        Maze[x][y] = 0; // 戻す
        return false;
    }

    private boolean isInPath(int x, int y) {
        for (int[] cell : path) {
            if (cell[0] == x && cell[1] == y) {
                return true;
            }
        }
        return false;
    }
}
