/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot;

import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author tuomo
 */
public class TheBotTest {

    private String map;
    private TheBot bot;

    public TheBotTest() {
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
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of readBoardIntoArray method, of class TheBot.
     */
    @Test
    public void testReadBoardIntoArray() {
        double size = Math.sqrt(map.length() / 2.0);
        String[][] testBoard = new String[(int)size][(int)size];
        testBoard = bot.readBoardIntoArray(map, (int)size, testBoard);
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
}
