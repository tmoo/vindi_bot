package bot;

import dto.GameState;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The bot I will be working on. At the moment it chooses an action randomly.
 *
 * @author tuomo
 */
public class TheBot implements Bot {

    private static final Logger logger = LogManager.getLogger(SimpleBotRunner.class);

    /**
     * 
     * @param gameState
     * @return The next move of the bot.
     */
    @Override
    public BotMove move(GameState gameState) {

        String[][] board = readBoardIntoArray(gameState);

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
     * @param gameState
     * @return The board in array for easy manipulation.
     */
    private String[][] readBoardIntoArray(GameState gameState) {
        int sizeOfBoard = gameState.getGame().getBoard().getSize();
        String boardString = gameState.getGame().getBoard().getTiles();
        String[][] board = new String[sizeOfBoard][sizeOfBoard];
        int charsInBoard = sizeOfBoard * sizeOfBoard * 2;

        logger.info(sizeOfBoard);

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
