package bot;

/**
 * This is the output of a SimpleBot.
 * <p/>
 * Because the SimpleBot does not have enough information to create a full Move, it will return a BotMove instead,
 * allowing the framework to generate a Move response with it.
 * 
 * From https://github.com/bstempi/vindinium-client
 */
public enum BotMove {

    STAY("Stay"), WEST("West"), EAST("East"), NORTH("North"), SOUTH("South");

    private final String direction;

    BotMove(String moveName) {
        this.direction = moveName;
    }

    @Override
    public String toString() {
        return direction;
    }
}
