package bot;

import auxiliary.GameState;
import auxiliary.GameState.Hero;
import datastructures.MyList;
import datastructures.MyQueue;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
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

    // Coordinates for various artefacts on the map
    List<int[]> taverns;
    List<int[]> mines;
    List<int[]> opponents;

    // BFS-related
    boolean[][] visited;
    int[][][] parent;
    int[][] distances;
    final int UNREACHABLE_DISTANCE = 666;

    // All heroes
    List<Hero> heroes;

    // Some properties of this particular instance of the bot
    Hero hero;
    int heroId;
    int hero_i;
    int hero_j;
    int heroLife;
    int heroGold;

    // List of the heroes with the same name as this instance
    List<Hero> ownHeroes;

    private static final Logger logger = LogManager.getLogger(BotRunner.class);

    public TheBot() {
        this.ownHeroes = new MyList<>();
    }

    /**
     * Sets up all fields and calls the method navigate, which processes the
     * board and finds the best move.
     *
     * @param gameState Current gamestate
     * @return The next move of the bot.
     */
    @Override
    public BotMove move(GameState gameState) {
        initialize(gameState);
        printInfo(gameState);

        findHeroesMinesAndTaverns(board, heroId);
        BFS();

        int[] closestTavern = findClosestMineOrTavern(taverns);
        int[] closestMine = findClosestMineOrTavern(mines);

        return decide(closestTavern, closestMine);
    }

    /**
     * Initialize all necessary fields for BFS and decision-making
     *
     * @param gameState Current gamestate
     */
    private void initialize(GameState gameState) {
        int sizeOfBoard = gameState.getGame().getBoard().getSize();
        String boardString = gameState.getGame().getBoard().getTiles();
        String[][] localBoard = new String[sizeOfBoard][sizeOfBoard];
        board = readBoardIntoArray(boardString, sizeOfBoard, localBoard);

        heroes = gameState.getGame().getHeroes();
        visited = new boolean[board.length][board[0].length];
        parent = new int[board.length][board[0].length][2];
        // Makes debugging easier
        for (int[][] row : parent) {
            for (int[] col : row) {
                col[0] = -1;
                col[1] = -1;
            }
        }

        distances = new int[board.length][board[0].length];
        // Fill distances-matrix to avoid errors with unreachable tiles 
        // (mainly mines that are blocked by an opponent)
        for (int[] row : distances) {
            Arrays.fill(row, UNREACHABLE_DISTANCE);
        }

        hero = gameState.getHero();
        heroId = hero.getId();
        heroLife = hero.getLife();
        heroGold = hero.getGold();

        ownHeroes = new MyList<>();
        for (Hero h : gameState.getGame().getHeroes()) {
            if (h.getName().equals(hero.getName())) {
                ownHeroes.add(h);
            }
        }
    }

    /**
     * Info to be printed on console
     *
     * @param state Current gamestate
     */
    private void printInfo(GameState state) {
        for (String[] strings : board) {
            logger.info(Arrays.toString(strings));
        }
//        logger.info(state.getGame().getBoard().getSize());
    }

    /**
     *
     * @param closestTavern Coordinates of closest tavern
     * @param closestMine Coordinates of closest mine
     * @return Either a move in the direction of the closest chosen target
     * (tavern or mine) or if there is no way, choose randomly
     */
    private BotMove decide(int[] closestTavern, int[] closestMine) {
        int[] target = closestMine;

        // if needed/reasonable, go to tavern instead of mine.
        if ((distances[closestTavern[0]][closestTavern[1]] == 1 && heroLife < 95)
                || heroLife < 30 || mines.isEmpty()
                || distances[closestMine[0]][closestMine[1]] >= UNREACHABLE_DISTANCE) {
            target = closestTavern;
        }

        int[] stepTowardsTarget = Arrays.copyOf(target, target.length);

        if (distances[stepTowardsTarget[0]][stepTowardsTarget[1]] >= UNREACHABLE_DISTANCE) {
            return randomMove();
        }

        stepTowardsTarget = findNextStep(stepTowardsTarget);

        if (stepTowardsTarget[0] < hero_i) {
            return BotMove.NORTH;
        } else if (stepTowardsTarget[0] > hero_i) {
            return BotMove.SOUTH;
        } else if (stepTowardsTarget[1] < hero_j) {
            return BotMove.WEST;
        } else {
            return BotMove.EAST;
        }
    }

    /**
     * Backtrack from target to the tile right next to our hero in order to take
     * the next step.
     *
     * @param stepTowardsTarget The coordinates of the target
     * @return The coordinates of the first step towards the target from the
     * hero.
     */
    private int[] findNextStep(int[] stepTowardsTarget) {
        while (true) {
            int parent_i = parent[stepTowardsTarget[0]][stepTowardsTarget[1]][0];
            int parent_j = parent[stepTowardsTarget[0]][stepTowardsTarget[1]][1];

            if (parent_i == hero_i && parent_j == hero_j) {
                break;
            }

            stepTowardsTarget[0] = parent_i;
            stepTowardsTarget[1] = parent_j;
        }
        return stepTowardsTarget;
    }

    /**
     * Given a list of mines or taverns, pick the one that is closest to
     * own hero.
     * @param locations A list of either mines or taverns
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
        mines = new MyList<>();
        taverns = new MyList<>();
        opponents = new MyList<>();
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
     * Add the coordinates of the mine in position (i,j) to list of mines unless
     * the mine is owned by own hero.
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
     * If the hero in position (i,j) is own hero, update it's coordinates. If the hero is opponent,
     * add list of opponents with its coordinates.
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
     * BFS for finding distance to every position in the map. Fills the fields
     * distance and parent (and auxiliary field visited). Doesn't go through
     * paths that are blocked by ## or an opponent with more health than own
     * hero.
     */
    private void BFS() {
        visited[hero_i][hero_j] = true;
        distances[hero_i][hero_j] = 0;
        int[] start = {hero_i, hero_j};

        Queue<int[]> queue = new MyQueue<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            int[] coords = queue.remove();
            int i = coords[0];
            int j = coords[1];

            // Can't go through tavern, mine, blocked terrain, or enemy with more 
            // health than own hero.
            if (board[i][j].equals("[]")
                    || board[i][j].equals("##")
                    || board[i][j].charAt(0) == '$'
                    || dangerZone(i, j)) {
                continue;
            }

            // recursion
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
     * Auxiliary method for BFS.
     *
     * @param i Current i-index
     * @param j Current j-index
     * @return True if there is another hero with more health in the given
     * position.
     */
    private boolean dangerZone(int i, int j) {
        for (Hero h : heroes) {
            if (h.getPos().getX() == i
                    && h.getPos().getY() == j
                    && hero.getId() != h.getId()
                    && h.getLife() >= hero.getLife()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Auxiliary method for BFS. Adds each legal neighbor of current position to
     * the queu and updates the data structures.
     *
     * @param i The current i-position (vertical) of the search
     * @param j The current j-position (horizontal) of the search
     * @param new_i The prospective i-position
     * @param new_j The prospective j-position
     * @param queue The queue used in BFS
     */
    private void processNeighbours(int i, int j, int new_i, int new_j, Queue<int[]> queue) {
        int[] parentCoords = {i, j};
        parent[new_i][new_j] = parentCoords;
        distances[new_i][new_j] = distances[i][j] + 1;
        visited[new_i][new_j] = true;
        int[] newCoords = {new_i, new_j};
        queue.add(newCoords);
    }

    /**
     * Creates a 2d-array of the board from the raw input. In the raw input 
     * each tile is represented as two characters.
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

    /**
     *
     * @return A move in a random direction
     */
    private BotMove randomMove() {
        int randomNumber = (int) (Math.random() * 4);

        switch (randomNumber) {
            case 0:
                return BotMove.NORTH;
            case 1:
                return BotMove.SOUTH;
            case 2:
                return BotMove.EAST;
            case 3:
                return BotMove.WEST;
            default:
                return BotMove.STAY;
        }
    }
}
