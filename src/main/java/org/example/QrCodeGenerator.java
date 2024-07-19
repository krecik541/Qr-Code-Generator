package org.example;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.ISO_8859_1;


public class QrCodeGenerator {
    /**
     * String that will be transformed into QR code
     */
    private String input;


    /**
     * Error Correction Level 0 = L (low), 1 = M (medium), 2 = Q (quartile), 3 = H (high)
     */
    private int errCorrLevel;


    /**
     * Smallest version for the input data
     */
    private int versionNumber;


    /**
     * Encoding mode
     * 0001 - numeric, 0010 - alphanumeric, 0100 - byte, 1000 - kanji
     */
    private String modeIndicator = "";


    /**
     * Character count indicator
     */
    private String charCountIndicator = "";


    /**
     * Input after encoding
     */
    private String encodedInput = "";


    /**
     *  Terimnator
     */
    private String terminator = "";


    /**
     * Maps alphanumeric characters to their code
     */
    private static final Map<Character, Integer> alphaNumeric = new HashMap<>(45);

    /**
     * Character capacity table; [0] = nm (numeric mode), [1] = am (alphanumeric mode), [2] = bm (byte mode), [3] = km (kanji mode);
     * [][(version - 1) * 4 + Error Correction Level], ie. 3Q nm (version 3, error correcion M) = [0][2 * 4 + 1]
     */
    private static final int [][] characterCapacityTable= {{41, 34, 27, 17, 77, 63, 48, 34, 127, 101, 77, 58, 187, 149, 111, 82, 255, 202, 144, 106, 322, 255, 178, 139, 370, 293, 207, 154, 461, 365, 259, 202, 552, 432, 312, 235, 652, 513, 364, 288, 772, 604, 427, 331, 883, 691, 489, 374, 1022, 796, 580, 427, 1101, 871, 621, 468, 1250, 991, 703, 530, 1408, 1082, 775, 602, 1548, 1212, 876, 674, 1725, 1346, 948, 746, 1903, 1500, 1063, 813, 2061, 1600, 1159, 919, 2232, 1708, 1224, 969, 2409, 1872, 1358, 1056, 2620, 2059, 1468, 1108, 2812, 2188, 1588, 1228, 3057, 2395, 1718, 1286, 3283, 2544, 1804, 1425, 3517, 2701, 1933, 1501, 3669, 2857, 2085, 1581, 3909, 3035, 2181, 1677, 4158, 3289, 2358, 1782, 4417, 3486, 2473, 1897, 4686, 3693, 2670, 2022, 4965, 3909, 2805, 2157, 5253, 4134, 2949, 2301, 5529, 4343, 3081, 2361, 5836, 4588, 3244, 2524, 6153, 4775, 3417, 2625, 6479, 5039, 3599, 2735, 6743, 5313, 3791, 2927, 7089, 5596, 3993, 3057},
            {25, 20, 16, 10, 47, 38, 29, 20, 77, 61, 47, 35, 114, 90, 67, 50, 154, 122, 87, 64, 195, 154, 108, 84, 224, 178, 125, 93, 279, 221, 157, 122, 335, 262, 189, 143, 395, 311, 221, 174, 468, 366, 259, 200, 535, 419, 296, 227, 619, 483, 352, 259, 667, 528, 376, 283, 758, 600, 426, 321, 854, 656, 470, 365, 938, 734, 531, 408, 1046, 816, 574, 452, 1153, 909, 644, 493, 1249, 970, 702, 557, 1352, 1035, 742, 587, 1460, 1134, 823, 640, 1588, 1248, 890, 672, 1704, 1326, 963, 744, 1853, 1451, 1041, 779, 1990, 1542, 1094, 864, 2132, 1637, 1172, 910, 2223, 1732, 1263, 958, 2369, 1839, 1322, 1016, 2520, 1994, 1429, 1080, 2677, 2113, 1499, 1150, 2840, 2238, 1618, 1226, 3009, 2369, 1700, 1307, 3183, 2506, 1787, 1394, 3351, 2632, 1867, 1431, 3537, 2780, 1966, 1530, 3729, 2894, 2071, 1591, 3927, 3054, 2181, 1658, 4087, 3220, 2298, 1774, 4296, 3391, 2420, 1852},
            {17, 14, 11, 7, 32, 26, 20, 14, 53, 42, 32, 24, 78, 62, 46, 34, 106, 84, 60, 44, 134, 106, 74, 58, 154, 122, 86, 64, 192, 152, 108, 84, 230, 180, 130, 98, 271, 213, 151, 119, 321, 251, 177, 137, 367, 287, 203, 155, 425, 331, 241, 177, 458, 362, 258, 194, 520, 412, 292, 220, 586, 450, 322, 250, 644, 504, 364, 280, 718, 560, 394, 310, 792, 624, 442, 338, 858, 666, 482, 382, 929, 711, 509, 403, 1003, 779, 565, 439, 1091, 857, 611, 461, 1171, 911, 661, 511, 1273, 997, 715, 535, 1367, 1059, 751, 593, 1465, 1125, 805, 625, 1528, 1190, 868, 658, 1628, 1264, 908, 698, 1732, 1370, 982, 742, 1840, 1452, 1030, 790, 1952, 1538, 1112, 842, 2068, 1628, 1168, 898, 2188, 1722, 1228, 958, 2303, 1809, 1283, 983, 2431, 1911, 1351, 1051, 2563, 1989, 1423, 1093, 2699, 2099, 1499, 1139, 2809, 2213, 1579, 1219, 2953, 2331, 1663, 1273},
            {10, 8, 7, 4, 20, 16, 12, 8, 32, 26, 20, 15, 48, 38, 28, 21, 65, 52, 37, 27, 82, 65, 45, 36, 95, 75, 53, 39, 118, 93, 66, 52, 141, 111, 80, 60, 167, 131, 93, 74, 198, 155, 109, 85, 226, 177, 125, 96, 262, 204, 149, 109, 282, 223, 159, 120, 320, 254, 180, 136, 361, 277, 198, 154, 397, 310, 224, 173, 442, 345, 243, 191, 488, 384, 272, 208, 528, 410, 297, 235, 572, 438, 314, 248, 618, 480, 348, 270, 672, 528, 376, 284, 721, 561, 407, 315, 784, 614, 440, 330, 842, 652, 462, 365, 902, 692, 496, 385, 940, 732, 534, 405, 1002, 778, 559, 430, 1066, 843, 604, 457, 1132, 894, 634, 486, 1201, 947, 684, 518, 1273, 1002, 719, 553, 1347, 1060, 756, 590, 1417, 1113, 790, 605, 1496, 1176, 832, 647, 1577, 1224, 876, 673, 1661, 1292, 923, 701, 1729, 1362, 972, 750, 1817, 1435, 1024, 784}};


    /**
     * Error Correction Code table [0] = L (low), [1] = M (medium), [2] = Q (quartile), [3] = H (high)
     */
    private static final int [][] errCorrCodeTable = {{19, 34, 55, 80, 108, 136, 156, 194, 232, 274, 324, 370, 428, 461, 523, 589, 647, 721, 795, 861, 932, 1006, 1094, 1174, 1276, 1370, 1468, 1531, 1631, 1735, 1843, 1955, 2071, 2191, 2306, 2434, 2566, 2702, 2812, 2956},
            {16, 28, 44, 64, 86, 108, 124, 154, 182, 216, 254, 290, 334, 365, 415, 453, 507, 563, 627, 669, 714, 782, 860, 914, 1000, 1062, 1128, 1193, 1267, 1373, 1455, 1541, 1631, 1725, 1812, 1914, 1992, 2102, 2216, 2334},
            {13, 22, 34, 48, 62, 76, 88, 110, 132, 154, 180, 206, 244, 261, 295, 325, 367, 397, 445, 485, 512, 568, 614, 664, 718, 754, 808, 871, 911, 985, 1033, 1115, 1171, 1231, 1286, 1354, 1426, 1502, 1582, 1666},
            {9, 16, 26, 36, 46, 60, 66, 86, 100, 122, 140, 158, 180, 197, 223, 253, 283, 313, 341, 385, 406, 442, 464, 514, 538, 596, 628, 661, 701, 745, 793, 845, 901, 961, 986, 1054, 1096, 1142, 1222, 1276}};


    /**
     * Character count indicator table [0] = nm (numeric mode), [1] = am (alphanumeric mode), [2] = bm (byte mode), [3] = km (kanji mode)
     */
    private static final int [][] charCountIndTable = {{10, 12, 14},
                                                        {9, 11, 13},
                                                        {8, 16, 16},
                                                        {8, 10, 12}};


    public QrCodeGenerator()
    {
        alphaNumeric.put('0', 0);
        alphaNumeric.put('1', 1);
        alphaNumeric.put('2', 2);
        alphaNumeric.put('3', 3);
        alphaNumeric.put('4', 4);
        alphaNumeric.put('5', 5);
        alphaNumeric.put('6', 6);
        alphaNumeric.put('7', 7);
        alphaNumeric.put('8', 8);
        alphaNumeric.put('9', 9);
        alphaNumeric.put('A', 10);
        alphaNumeric.put('B', 11);
        alphaNumeric.put('C', 12);
        alphaNumeric.put('D', 13);
        alphaNumeric.put('E', 14);
        alphaNumeric.put('F', 15);
        alphaNumeric.put('G', 16);
        alphaNumeric.put('H', 17);
        alphaNumeric.put('I', 18);
        alphaNumeric.put('J', 19);
        alphaNumeric.put('K', 20);
        alphaNumeric.put('L', 21);
        alphaNumeric.put('M', 22);
        alphaNumeric.put('N', 23);
        alphaNumeric.put('O', 24);
        alphaNumeric.put('P', 25);
        alphaNumeric.put('Q', 26);
        alphaNumeric.put('R', 27);
        alphaNumeric.put('S', 28);
        alphaNumeric.put('T', 29);
        alphaNumeric.put('U', 30);
        alphaNumeric.put('V', 31);
        alphaNumeric.put('W', 32);
        alphaNumeric.put('X', 33);
        alphaNumeric.put('Y', 34);
        alphaNumeric.put('Z', 35);
        alphaNumeric.put(' ', 36);
        alphaNumeric.put('$', 37);
        alphaNumeric.put('%', 38);
        alphaNumeric.put('*', 39);
        alphaNumeric.put('+', 40);
        alphaNumeric.put('-', 41);
        alphaNumeric.put('.', 42);
        alphaNumeric.put('/', 43);
        alphaNumeric.put(':', 44);
    }


    /**
     * Choose segment mode to encode all characters
     * TODO fill the rest characters of Kanji mode
     */
    public void analyzeCharacters()
    {
        // nm - numeric, am - alphanumeric, bm - byte mode, km - Kanji
        boolean nm = true, am = true, bm = true, km = true;
        for(int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            if(!(c >= '0' && c <= '9'))
                nm = false;
            if(!((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || c == ' ' || c == '$' || c == '%' || c == '*' || c == '+' || c == '-' || c == '.' || c == '/' || c == ':'))
                am = false;
            if(!((c >= 0x8140 && c <= 0x9FFC) || (c >= 0xE040 && c <= 0xEBBF)))
                km = false;
            if(!((int)c <= 255))
                bm = false;
        }
        if(nm) {
            modeIndicator = "0001";
        }
        else if(am) {
            modeIndicator = "0010";
        }
        else if(bm) {
            modeIndicator = "0100";
        }
        else if(km) {
            modeIndicator = "1000";
        }
        else
            throw new RuntimeException("Error while trying to choose segment mode to encode all characters");
    }


    /**
     * Encoding the input data based on encoding mode
     * TODO mixed data encoding
     */
    public void createDataSegment()
    {
        switch (modeIndicator) {
            case "0001" -> {
                String s = "";
                for (int i = 0; i < input.length(); i += 3) {
                    if (i + 3 <= input.length())
                        s = input.substring(i, i + 3);
                    else if (i + 2 <= input.length())
                        s = input.substring(i, i + 2);
                    else
                        s = input.substring(i, i + 1);

                    encodedInput = encodedInput.concat(binary(Integer.parseInt(s), s.length() == 3 ? 10 : s.length() == 2 ? 7 : 4));
                }
            }
            case "0010" -> {
                for (int i = 0; i < input.length(); i += 2) {
                    if (i + 2 <= input.length())
                        encodedInput = encodedInput.concat(binary(alphaNumeric.get(input.charAt(i)) * 45 + alphaNumeric.get(input.charAt(i + 1)), 11));
                    else
                        encodedInput = encodedInput.concat(binary(alphaNumeric.get(input.charAt(i)), 6));
                }
            }
            case "0100" -> {
                byte[] bytes = input.getBytes(ISO_8859_1);
                for (int n : bytes) {
                    encodedInput = encodedInput.concat(binary(n, 8));
                }
            }
            case "1000" -> {
                for (int i = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if (c >= 0x8140 && c <= 0x9FFC)
                        c -= 0x8140;
                    else if (c >= 0xE040 && c <= 0xEBBF)
                        c -= 0xC140;
                    else
                        throw new RuntimeException("Error while creating data segments: Kanji");

                    int n = ((c >> 8) * 0xC0) + (c & 0x00FF);
                    encodedInput = encodedInput.concat(binary(n, 13));
                }
            }
            default -> throw new RuntimeException("Error while creating data segments");
        }
    }


    /**
     * Encodes (translates to binary code) part of the input
     * @param n part of the input that will be encoded
     * @param length if encoded part of the input is shorter than required length, the difference between actual
     *               and required length is filled with 0
     * @return encoded (translated to binary code) part of the input
     */
    public static String binary(int n, int length) {
        String s = "";
        while(n != 0)
        {
            s = ((n % 2) == 0 ? "0" : "1") + s;
            n /= 2;
        }

        while(s.length() != length)
            s = "0" + s;

        return s;
    }


    /**
     * Finds smallest version number based on encoded input data length and Error Correction Level
     */
    public void fitToVersionNumber()
    {
        int k = switch (modeIndicator) {
            case "0001" -> 0;
            case "0010" -> 1;
            case "0100" -> 2;
            case "1000" -> 3;
            default -> throw new RuntimeException("Unexpected value of modeIndicator: " + modeIndicator);
        };

        for(int j = 0; j < 40; j++)
            if(characterCapacityTable[k][j * 4 + errCorrLevel] <= input.length())
                continue;
            else {
                versionNumber = j;
                int length;
                if(j + 1 <= 9)
                    length = charCountIndTable[k][0];
                else if(j + 1 <= 26)
                    length = charCountIndTable[k][1];
                else
                    length = charCountIndTable[k][2];
                charCountIndicator = binary(input.length(), length);
                 return;
            }
        throw new RuntimeException("Error while finding the smallest version for input data: input too large");
    }

    /**
     * Merges mode indicator, character count indicator and encoded input, adds 0s and/or pad bytes if its necessary
     */
    public void addPaddingBytes()
    {
        int length = errCorrCodeTable[errCorrLevel][versionNumber] * 8;
        terminator = switch (length - (modeIndicator.length() + charCountIndicator.length() + encodedInput.length())) {
            case 0 -> "";
            case 1 -> "0";
            case 2 -> "00";
            case 3 -> "000";
            default -> "0000";
        };

        encodedInput = modeIndicator + charCountIndicator + encodedInput + terminator;

        while(encodedInput.length() % 8 != 0)
            encodedInput = encodedInput.concat("0");

        while(encodedInput.length() != length)
        {
            encodedInput = encodedInput.concat("11101100");
            if(encodedInput.length() == length)
                break;
            encodedInput = encodedInput.concat("00010001");
        }
    }

    public static void main(String[] args) {
        QrCodeGenerator qrCodeGenerator = new QrCodeGenerator();
        Reader reader = new InputStreamReader(System.in);

        qrCodeGenerator.input = "https://www.twitch.tv/izakooo";
        qrCodeGenerator.errCorrLevel = 2;

        qrCodeGenerator.analyzeCharacters();
        qrCodeGenerator.createDataSegment();
        qrCodeGenerator.fitToVersionNumber();
        qrCodeGenerator.addPaddingBytes();
        ErrorCorrectionCode ecc = new ErrorCorrectionCode(qrCodeGenerator.versionNumber, qrCodeGenerator.errCorrLevel, qrCodeGenerator.encodedInput);
        // DrawQR draw = new DrawQR(qrCodeGenerator.versionNumber);
        DrawQR draw = new DrawQR(qrCodeGenerator.versionNumber, ecc.getFinalMessage(), qrCodeGenerator.errCorrLevel);
    }
}