/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot;

import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author tuomo
 */
public class TheBotTest {
    
    private String map;
    private TheBot bot;
    private double size;
    private String[][] testBoard;

    @Before
    public void setUp() {
        map = "##############        ############################        "
                + "##############################    ###########################"
                + "###$4    $4############################  @4    ##############"
                + "##########  @1##    ##    ####################  []        [] "
                + " ##################        ####        ####################  "
                + "$4####$4  ########################  $4####$4  ###############"
                + "#####        ####        ##################  []        []  ##"
                + "##################  @2##    ##@3  ########################   "
                + "     ############################$-    $-####################"
                + "##########    ##############################        #########"
                + "###################        ##############";

        bot = new TheBot();
        size = Math.sqrt(map.length() / 2.0);
        testBoard = new String[(int) size][(int) size];
        testBoard = bot.readBoardIntoArray(map, (int) size, testBoard);
    }
    
    /**
     * Test of readBoardIntoArray method, of class TheBot.
     */
    @Test
    public void testReadBoardIntoArray() {
        testBoard = bot.readBoardIntoArray(map, (int) size, testBoard);
        StringBuilder sb = new StringBuilder();
        for (String[] s : testBoard) {
            sb.append(Arrays.toString(s)).append("\n");
        }
        String correctOutput = "[##, ##, ##, ##, ##, ##, ##,   ,   ,   ,   , ##, ##, ##, ##, ##, ##, ##]\n"
                + "[##, ##, ##, ##, ##, ##, ##,   ,   ,   ,   , ##, ##, ##, ##, ##, ##, ##]\n"
                + "[##, ##, ##, ##, ##, ##, ##, ##,   ,   , ##, ##, ##, ##, ##, ##, ##, ##]\n"
                + "[##, ##, ##, ##, ##, ##, ##, $4,   ,   , $4, ##, ##, ##, ##, ##, ##, ##]\n"
                + "[##, ##, ##, ##, ##, ##, ##,   , @4,   ,   , ##, ##, ##, ##, ##, ##, ##]\n"
                + "[##, ##, ##, ##, ##,   , @1, ##,   ,   , ##,   ,   , ##, ##, ##, ##, ##]\n"
                + "[##, ##, ##, ##, ##,   , [],   ,   ,   ,   , [],   , ##, ##, ##, ##, ##]\n"
                + "[##, ##, ##, ##,   ,   ,   ,   , ##, ##,   ,   ,   ,   , ##, ##, ##, ##]\n"
                + "[##, ##, ##, ##, ##, ##,   , $4, ##, ##, $4,   , ##, ##, ##, ##, ##, ##]\n"
                + "[##, ##, ##, ##, ##, ##,   , $4, ##, ##, $4,   , ##, ##, ##, ##, ##, ##]\n"
                + "[##, ##, ##, ##,   ,   ,   ,   , ##, ##,   ,   ,   ,   , ##, ##, ##, ##]\n"
                + "[##, ##, ##, ##, ##,   , [],   ,   ,   ,   , [],   , ##, ##, ##, ##, ##]\n"
                + "[##, ##, ##, ##, ##,   , @2, ##,   ,   , ##, @3,   , ##, ##, ##, ##, ##]\n"
                + "[##, ##, ##, ##, ##, ##, ##,   ,   ,   ,   , ##, ##, ##, ##, ##, ##, ##]\n"
                + "[##, ##, ##, ##, ##, ##, ##, $-,   ,   , $-, ##, ##, ##, ##, ##, ##, ##]\n"
                + "[##, ##, ##, ##, ##, ##, ##, ##,   ,   , ##, ##, ##, ##, ##, ##, ##, ##]\n"
                + "[##, ##, ##, ##, ##, ##, ##,   ,   ,   ,   , ##, ##, ##, ##, ##, ##, ##]\n"
                + "[##, ##, ##, ##, ##, ##, ##,   ,   ,   ,   , ##, ##, ##, ##, ##, ##, ##]\n";
        assertEquals(sb.toString(), correctOutput);
    }

    /**
     * Test of method findHeroMinesAndTaverns, testing for opponents.
     */
    @Test
    public void findHeroesMinesAndTavernsFindsOpponents() {
        bot.findHeroesMinesAndTaverns(testBoard, 1);
        int[] o1 = {4, 8};
        int[] o2 = {12, 6};
        int[] o3 = {12, 11};
        boolean opponentsContained = true;
        for (int[] opponent : bot.opponents) {
            if (!(Arrays.equals(opponent, o1) || Arrays.equals(opponent, o2)
                    || Arrays.equals(opponent, o3))) {
                opponentsContained = false;
            }
        }
        assertTrue(opponentsContained);
        assertTrue(bot.opponents.size() == 3);

    }

    /**
     * Test of method findHeroMinesAndTaverns, testing for mines.
     */
    @Test
    public void findHeroesMinesAndTavernsFindsTaverns() {
        bot.findHeroesMinesAndTaverns(testBoard, 1);
        int[] t1 = {6, 6};
        int[] t2 = {6, 11};
        int[] t3 = {11, 6};
        int[] t4 = {11, 11};
        boolean tavernsContained = true;
        for (int[] tavern : bot.taverns) {
            if (!(Arrays.equals(tavern, t1) || Arrays.equals(tavern, t2)
                    || Arrays.equals(tavern, t3) || Arrays.equals(tavern, t4))) {
                tavernsContained = false;
            }
        }
        assertTrue(tavernsContained);
        assertTrue(bot.taverns.size() == 4);
    }

    /**
     * Test of method findHeroMinesAndTaverns, testing for mines.
     */
    @Test
    public void findHeroesMinesAndTavernsFindsMines() {
        bot.findHeroesMinesAndTaverns(testBoard, 1);
        int[] m1 = {3, 10};
        int[] m2 = {3, 7};
        int[] m3 = {8, 10};
        int[] m4 = {8, 7};
        int[] m5 = {9, 7};
        int[] m6 = {9, 10};
        int[] m7 = {14, 7};
        int[] m8 = {14, 10};
        boolean minesContained = true;
        for (int[] mine : bot.mines) {
            if (!(Arrays.equals(mine, m1) || Arrays.equals(mine, m2)
                    || Arrays.equals(mine, m3) || Arrays.equals(mine, m4)
                    || Arrays.equals(mine, m5) || Arrays.equals(mine, m6)
                    || Arrays.equals(mine, m7) || Arrays.equals(mine, m8))) {
                System.out.println(Arrays.toString(mine));
                minesContained = false;
            }
        }
        assertTrue(minesContained);
        assertTrue(bot.mines.size() == 8);
    }

    /**
     * Test of method findHeroMinesAndTaverns, testing for hero.
     */
    @Test
    public void findHeroesMinesAndTavernsFindsHero() {
        bot.findHeroesMinesAndTaverns(testBoard, 1);
        assertEquals(5, bot.hero_i);
        assertEquals(6, bot.hero_j);
    }
}
