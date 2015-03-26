package studio.idle.mathgame.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Udit on 24/03/15.
 */
public class CommonConstants {


    public static final String PREF_FILE_NAME = "settings";

    public static List<String> symbolListEasy = Arrays.asList("+", "+", "-");
    public static List<String> symbolListMedium = Arrays.asList("+", "+", "-", "-", "*", "/");
    public static List<String> symbolListDifficult = Arrays.asList("+", "-", "*", "/");

    public static int minEasyValue = 0;
    public static int minMediumValue = 0;
    public static int minHardValue = 0;

    public static int maxEasyValue = 50;
    public static int maxMediumValue = 500;
    public static int maxHardValue = 5000;

    public enum difficultyLevels {
        easy,
        medium,
        hard;

        public static difficultyLevels get(int code) {

            CommonConstants.difficultyLevels level = easy;
            switch(code) {
                case  0: level = easy;
                    break;
                case  1: level = medium;
                    break;
                case  2: level = hard;
            }

            return level;
        }
    }

}
