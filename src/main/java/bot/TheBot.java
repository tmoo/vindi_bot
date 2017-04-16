package bot;

import dto.GameState;
import dto.GameState.Hero;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The bot I am working on. At the moment it uses BFS to find the closest mine
 * and taverns, and deciding pretty simply which one to go for.
 *
 * @author tuomo
 */
public class TheBot implements Bot {

    String[][] board;
    List<int[]> taverns;
    List<int[]> mines;
    List<int[]> opponents;
    boolean[][] visited;
    int[][][] parent;
    int[][] distances;

    List<Hero> heroes;

    Hero hero;
    int heroId;
    int hero_i;
    int hero_j;
    int heroLife;
    int heroGold;
    List<Hero> ownHeroes = new ArrayList<>();

    private static final Logger logger = LogManager.getLogger(BotRunner.class);

    /**
     *
     * @param gameState Current gamestate
     * @return The next move of the bot.
     */
    @Override
    public BotMove move(GameState gameState) {
        int sizeOfBoard = gameState.getGame().getBoard().getSize();
        String boardString = gameState.getGame().getBoard().getTiles();
        String[][] localBoard = new String[sizeOfBoard][sizeOfBoard];
        board = readBoardIntoArray(boardString, sizeOfBoard, localBoard);

        for (String[] strings : board) {
            logger.info(Arrays.toString(strings));
        }

        heroes = gameState.getGame().getHeroes();

        visited = new boolean[board.length][board[0].length];

        parent = new int[board.length][board[0].length][2];
        // Makes debugging easier
        for (int[][] row : parent) {
            for (int[] tile : row) {
                tile[0] = -1;
                tile[1] = -1;
            }
        }
        distances = new int[board.length][board[0].length];
        // Fill distances-matrix to avoid errors with unreachable tiles 
        // (mainly mines that are blocked by an opponent)
        for (int[] row : distances) {
            Arrays.fill(row, 666);
        }

        hero = gameState.getHero();
        heroId = hero.getId();
        heroLife = hero.getLife();
        heroGold = hero.getGold();
        ownHeroes = new ArrayList<>();

        for (Hero h : gameState.getGame().getHeroes()) {
            if (h.getName().equals(hero.getName())) {
                ownHeroes.add(h);
            }
        }

        logger.info(sizeOfBoard);
        return navigate();
    }

    /**
     * A method for finding the shortest way to different important parts of the
     * map. At the moment uses BFS, which works well for now.
     *
     * @param board The board in a 2d-array
     * @param gameState Current gamestate
     * @return
     */
    private BotMove navigate() {
        findHeroesMinesAndTaverns(board, heroId);
        BFS();

        int[] closestTavern = findClosestMineOrTavern(taverns);
        int[] closestMine = findClosestMineOrTavern(mines);
        
        logger.info(Arrays.toString(closestMine));
        
        return decide(closestTavern, closestMine);
    }

    /**
     *
     * @param closestMine Coordinates of the target
     * @return Either a move in the direction of the closest chosen target
     * (tavern or mine) or if there is no way, choose randomly
     */
    private BotMove decide(int[] closestTavern, int[] closestMine) {
        int[] closest = closestMine;
        
        // if needed/reasonable, go to tavern instead of mine.
        if ((distances[closestTavern[0]][closestTavern[1]] == 1 && heroLife < 95)
                || heroLife < 30 || mines.isEmpty() 
                || distances[closestMine[0]][closestMine[1]] >= 666) {
            closest = closestTavern;
        }

        int l = closest[0];
        int k = closest[1];

        if (distances[l][k] >= 666) {
            return randomMove();
        }

        while (true) {
            int parent_i = parent[l][k][0];
            int parent_j = parent[l][k][1];

            if (parent_i == hero_i && parent_j == hero_j) {
                break;
            }

            l = parent_i;
            k = parent_j;
        }

        if (l < hero_i) {
            return BotMove.NORTH;
        } else if (l > hero_i) {
            return BotMove.SOUTH;
        } else if (k < hero_j) {
            return BotMove.WEST;
        } else {
            return BotMove.EAST;
        }
    }

    /**
     *
     * @return A move in a random direction
     */
    private BotMove randomMove() {
        int randomNumber = (int) (Math.random() * 4);
        
        switch (randomNumber) {
            case 1:
                return BotMove.NORTH;
            case 2:
                return BotMove.SOUTH;
            case 3:
                return BotMove.EAST;
            case 4:
                return BotMove.WEST;
            default:
                return BotMove.STAY;
        }
    }

    /**
     *
     * @param locations Either mine or tavern
     * @return The mine/tavern that is closest to the hero
     */
    private int[] findClosestMineOrTavern(List<int[]> locations) {
        if (locations.isEmpty()) {
            return new int[0];
        }
        int[] closest = {locations.get(0)[0], locations.get(0)[1]};
        for (int[] coord : locations) {
            int i = coord[0];
            int j = coord[1];
            if (distances[i][j] < distances[closest[0]][closest[1]]) {
                closest = coord;
            }
        }
        return closest;
    }

    /**
     * Go over the board and note where the hero is and where are the taverns
     * and mines that are not owned by any instance of the same bot.
     *
     * @param board Game board
     * @param heroId Id of own hero
     */
    public void findHeroesMinesAndTaverns(String[][] board, int heroId) {
        mines = new ArrayList<>();
        taverns = new ArrayList<>();
        opponents = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].charAt(0) == '@') {
                    processHeroes(board, i, j, heroId);
                } else if (board[i][j].charAt(0) == '$') {
                    processMines(board, i, j);
                } else if (board[i][j].equals("[]")) {
                    int[] tavernCoords = {i, j};
                    taverns.add(tavernCoords);
                }
            }
        }
    }

    /**
     *
     * @param board Game board
     * @param i Current i-index
     * @param j Current j-index
     */
    private void processMines(String[][] board, int i, int j) {
        boolean isOwnMine = false;
        for (Hero h : ownHeroes) {
            if (board[i][j].equals("$" + h.getId())) {
                isOwnMine = true;
            }
        }
        if (!isOwnMine) {
            int[] mineCoords = {i, j};
            mines.add(mineCoords);
        }
    }

    /**
     *
     * @param board Game board
     * @param i Current i-index
     * @param j Current j-index
     * @param heroId Id of own hero
     */
    private void processHeroes(String[][] board, int i, int j, int heroId) {
        if (Character.getNumericValue(board[i][j].charAt(1)) == heroId) {
            hero_i = i;
            hero_j = j;
        } else {
            int[] oppCoords = {i, j};
            opponents.add(oppCoords);
        }
    }

    /**
     * Basic BFS for finding distance to every position in the map.
     */
    private void BFS() {
        visited[hero_i][hero_j] = true;
        distances[hero_i][hero_j] = 0;
        int[] start = {hero_i, hero_j};

        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            int[] coords = queue.poll();
            int i = coords[0];
            int j = coords[1];
            if (board[i][j].equals("[]") || board[i][j].equals("##")
                    || board[i][j].charAt(0) == '$') {
                continue;
            }
            if (dangerZone(i, j)) {
                continue;
            }

            if (j < board[i].length - 1 && !visited[i][j + 1]) {
                processNeighbours(i, j, i, j + 1, queue);
            }
            if (i < board.length - 1 && !visited[i + 1][j]) {
                processNeighbours(i, j, i + 1, j, queue);
            }
            if (j > 0 && !visited[i][j - 1]) {
                processNeighbours(i, j, i, j - 1, queue);
            }
            if (i > 0 && !visited[i - 1][j]) {
                processNeighbours(i, j, i - 1, j, queue);
            }
        }
    }

    /**
     *
     * @param i Current i-index
     * @param j Current j-index
     * @return True if there is another hero with more health in the position
     */
    private boolean dangerZone(int i, int j) {
        boolean danger = false;
        for (Hero h : heroes) {
            if (h.getPos().getX() == i && h.getPos().getY() == j) {
                if (hero.getId() != h.getId() && h.getLife() >= hero.getLife()) {
                    danger = true;
                }
            }
        }
        return danger;
    }

    /**
     * Auxiliary method for BFS. Adds each legal neighbor of current position to
     * the queu and updates the data structures.
     *
     * @param i The current i-position (vertical) of the search
     * @param j The current j-position (horizontal) of the search
     * @param new_i The prospective i-position
     * @param new_j The prospective j-position
     * @param queue The queu used in BFS
     */
    private void processNeighbours(int i, int j, int new_i, int new_j, ArrayDeque<int[]> queue) {
        int[] parentCoords = {i, j};
        parent[new_i][new_j] = parentCoords;
        distances[new_i][new_j] = distances[i][j] + 1;
        visited[new_i][new_j] = true;
        int[] newCoords = {new_i, new_j};
        queue.add(newCoords);
    }

    /**
     * Creates a 2d-array of the board from the raw input.
     *
     * @param boardString The board as a string
     * @param sizeOfBoard Number of characters in the board string
     * @param board The array we want to build
     * @return The finished board in an array
     */
    public String[][] readBoardIntoArray(String boardString, int sizeOfBoard, String[][] board) {
        int charsInBoard = sizeOfBoard * sizeOfBoard * 2;

        int j = 0;
        for (int i = 0; i < charsInBoard - 1; i += 2) {
            if (i != 0 && i % (2 * sizeOfBoard) == 0) {
                j++;
            }
            String tile = Character.toString(boardString.charAt(i))
                    + Character.toString(boardString.charAt(i + 1));
            board[j][(i / 2) % sizeOfBoard] = tile;
        }
        return board;
    }

    @Override
    public void setup() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void shutdown() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
