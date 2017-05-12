package bot;

import auxiliary.GameState;

/**
 * Most basic interface for a bot
 * <p/>
 * The Bot gets a GameState and is expected to return a BotMove.  The response to the server is a Move,
 * but since a Bot does not know its API key, it returns a BotMove to indicate the direction and allows the framework
 * to take care of building a Move response.
 * <p/>
 * The bot must handle its own map parsing, threading, timing, etc.
 * 
 * From https://github.com/bstempi/vindinium-client with some modifications
 */
public interface Bot {

    /**
     * Method that plays each move
     *
     * @param gameState the current game state
     * @return the decided move
     */
    public BotMove move(GameState gameState);
}
