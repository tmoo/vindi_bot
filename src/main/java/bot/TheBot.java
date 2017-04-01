package bot;

import dto.GameState;
import dto.GameState.Hero;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The bot I will be working on. At the moment it chooses an action randomly.
 *
 * @author tuomo
 */
public class TheBot implements Bot {

    private String[][] board;
    private List<int[]> taverns;
    private List<int[]> mines;
    private boolean[][] visited;
    private int[][][] parent;
    private int[][] route;

    Hero hero;
    int heroId;
    int hero_i;
    int hero_j;
    int heroLife;
    int heroGold;
    List<Hero> ownHeroes = new ArrayList<>();

    private static final Logger logger = LogManager.getLogger(SimpleBotRunner.class);

    /**
     *
     * @param gameState
     * @return The next move of the bot.
     */
    @Override
    public BotMove move(GameState gameState) {

        board = readBoardIntoArray(gameState);
        mines = new ArrayList<>();
        taverns = new ArrayList<>();
        visited = new boolean[board.length][board[0].length];
        parent = new int[board.length][board[0].length][2];
        route = new int[board.length][board[0].length];

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

        return navigate();
    }

    /**
     * A method for finding the shortest way to different important parts of the
     * map. At the moment uses BFS, but A* may be better. For now it works well.
     *
     * @param board
     * @param gameState
     * @return
     */
    private BotMove navigate() {
        findHeroMinesAndTaverns();
        BFS();

        int[] closestTavern = findClosestMineOrTavern(taverns);
        int[] closest = findClosestMineOrTavern(mines);

        // if needed/reasonable, go to tavern instead of mine.
        if ((route[closestTavern[0]][closestTavern[1]] == 1 && heroLife < 95)
                || heroLife < 30 || mines.isEmpty() && heroGold >= 2) {
            closest = closestTavern;
        }

        int l = closest[0];
        int k = closest[1];
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

    private int[] findClosestMineOrTavern(List<int[]> locations) {
        if (locations.isEmpty()) {
            return new int[0];
        }
        int[] closest = {locations.get(0)[0], locations.get(0)[1]};
        for (int[] coord : locations) {
            int i = coord[0];
            int j = coord[1];
            if (route[i][j] < route[closest[0]][closest[1]]) {
                closest = coord;
            }
        }
        return closest;
    }

    private void findHeroMinesAndTaverns() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].equals("@" + heroId)) {
                    hero_i = i;
                    hero_j = j;
                } else if (board[i][j].charAt(0) == '$') {
                    boolean isOwnMine = false;
                    for (Hero h : ownHeroes) {
                        if (board[i][j].equals("$" + h.getId())) {
                            isOwnMine = true;
                        }
                    }
                    if (isOwnMine) {
                        continue;
                    }
                    int[] mineCoords = {i, j};
                    mines.add(mineCoords);
                } else if (board[i][j].equals("[]")) {
                    int[] tavernCoords = {i, j};
                    taverns.add(tavernCoords);
                }
            }
        }
    }

    /**
     * Basic BFS for finding distance to every position in the map.
     *
     * @param visited
     * @param hero_i
     * @param hero_j
     * @param board
     * @param route
     */
    private void BFS() {
        visited[hero_i][hero_j] = true;
        int[] lel = {hero_i, hero_j};

        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.add(lel);
        while (!queue.isEmpty()) {
            int[] coords = queue.poll();
            int i = coords[0];
            int j = coords[1];
            if (board[i][j].equals("[]") || board[i][j].equals("##")
                    || board[i][j].charAt(0) == '$') {
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

    private void processNeighbours(int i, int j, int new_i, int new_j, ArrayDeque<int[]> queue) {
        int[] parentCoords = {i, j};
        parent[new_i][new_j] = parentCoords;
        route[new_i][new_j] = route[i][j] + 1;
        visited[new_i][new_j] = true;
        int[] newCoords = {new_i, new_j};
        queue.add(newCoords);
    }

    /**
     *
     * @param gameState
     * @return The board in array for easy manipulation.
     */
    private String[][] readBoardIntoArray(GameState gameState) {
        int sizeOfBoard = gameState.getGame().getBoard().getSize();
        String boardString = gameState.getGame().getBoard().getTiles();
        String[][] board = new String[sizeOfBoard][sizeOfBoard];
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
