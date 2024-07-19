package org.example;

import java.util.*;

public class ErrorCorrectionCode {
    /**
     * Generated polynomials
     */
    Vector<Vector<Integer>>generatedPolynomials = new Vector<>();


    /**
     * Data Codewords for each block
     */
    private final Vector<Vector<Integer>>dataCodewords = new Vector<>();
    
    
    /**
     * Error Correction Codewords for each block
     */
    private final Vector<Vector<Integer>>errorCorrectionCodewords = new Vector<>();

    /**
     * Entire final message in binary
     */
    private String finalMessage = "";

    /**
     * Log table
     */
    private static final Map<Integer, Integer> logTable = Map.<Integer, Integer>ofEntries(new AbstractMap.SimpleEntry<>(0, 1), new AbstractMap.SimpleEntry<>(1, 2), new AbstractMap.SimpleEntry<>(2, 4), new AbstractMap.SimpleEntry<>(3, 8), new AbstractMap.SimpleEntry<>(4, 16), new AbstractMap.SimpleEntry<>(5, 32), new AbstractMap.SimpleEntry<>(6, 64), new AbstractMap.SimpleEntry<>(7, 128), new AbstractMap.SimpleEntry<>(8, 29), new AbstractMap.SimpleEntry<>(9, 58), new AbstractMap.SimpleEntry<>(10, 116), new AbstractMap.SimpleEntry<>(11, 232), new AbstractMap.SimpleEntry<>(12, 205), new AbstractMap.SimpleEntry<>(13, 135), new AbstractMap.SimpleEntry<>(14, 19), new AbstractMap.SimpleEntry<>(15, 38), new AbstractMap.SimpleEntry<>(16, 76), new AbstractMap.SimpleEntry<>(17, 152), new AbstractMap.SimpleEntry<>(18, 45), new AbstractMap.SimpleEntry<>(19, 90), new AbstractMap.SimpleEntry<>(20, 180), new AbstractMap.SimpleEntry<>(21, 117), new AbstractMap.SimpleEntry<>(22, 234), new AbstractMap.SimpleEntry<>(23, 201), new AbstractMap.SimpleEntry<>(24, 143), new AbstractMap.SimpleEntry<>(25, 3), new AbstractMap.SimpleEntry<>(26, 6), new AbstractMap.SimpleEntry<>(27, 12), new AbstractMap.SimpleEntry<>(28, 24), new AbstractMap.SimpleEntry<>(29, 48), new AbstractMap.SimpleEntry<>(30, 96), new AbstractMap.SimpleEntry<>(31, 192), new AbstractMap.SimpleEntry<>(32, 157), new AbstractMap.SimpleEntry<>(33, 39), new AbstractMap.SimpleEntry<>(34, 78), new AbstractMap.SimpleEntry<>(35, 156), new AbstractMap.SimpleEntry<>(36, 37), new AbstractMap.SimpleEntry<>(37, 74), new AbstractMap.SimpleEntry<>(38, 148), new AbstractMap.SimpleEntry<>(39, 53), new AbstractMap.SimpleEntry<>(40, 106), new AbstractMap.SimpleEntry<>(41, 212), new AbstractMap.SimpleEntry<>(42, 181), new AbstractMap.SimpleEntry<>(43, 119), new AbstractMap.SimpleEntry<>(44, 238), new AbstractMap.SimpleEntry<>(45, 193), new AbstractMap.SimpleEntry<>(46, 159), new AbstractMap.SimpleEntry<>(47, 35), new AbstractMap.SimpleEntry<>(48, 70), new AbstractMap.SimpleEntry<>(49, 140), new AbstractMap.SimpleEntry<>(50, 5), new AbstractMap.SimpleEntry<>(51, 10), new AbstractMap.SimpleEntry<>(52, 20), new AbstractMap.SimpleEntry<>(53, 40), new AbstractMap.SimpleEntry<>(54, 80), new AbstractMap.SimpleEntry<>(55, 160), new AbstractMap.SimpleEntry<>(56, 93), new AbstractMap.SimpleEntry<>(57, 186), new AbstractMap.SimpleEntry<>(58, 105), new AbstractMap.SimpleEntry<>(59, 210), new AbstractMap.SimpleEntry<>(60, 185), new AbstractMap.SimpleEntry<>(61, 111), new AbstractMap.SimpleEntry<>(62, 222), new AbstractMap.SimpleEntry<>(63, 161), new AbstractMap.SimpleEntry<>(64, 95), new AbstractMap.SimpleEntry<>(65, 190), new AbstractMap.SimpleEntry<>(66, 97), new AbstractMap.SimpleEntry<>(67, 194), new AbstractMap.SimpleEntry<>(68, 153), new AbstractMap.SimpleEntry<>(69, 47), new AbstractMap.SimpleEntry<>(70, 94), new AbstractMap.SimpleEntry<>(71, 188), new AbstractMap.SimpleEntry<>(72, 101), new AbstractMap.SimpleEntry<>(73, 202), new AbstractMap.SimpleEntry<>(74, 137), new AbstractMap.SimpleEntry<>(75, 15), new AbstractMap.SimpleEntry<>(76, 30), new AbstractMap.SimpleEntry<>(77, 60), new AbstractMap.SimpleEntry<>(78, 120), new AbstractMap.SimpleEntry<>(79, 240), new AbstractMap.SimpleEntry<>(80, 253), new AbstractMap.SimpleEntry<>(81, 231), new AbstractMap.SimpleEntry<>(82, 211), new AbstractMap.SimpleEntry<>(83, 187), new AbstractMap.SimpleEntry<>(84, 107), new AbstractMap.SimpleEntry<>(85, 214), new AbstractMap.SimpleEntry<>(86, 177), new AbstractMap.SimpleEntry<>(87, 127), new AbstractMap.SimpleEntry<>(88, 254), new AbstractMap.SimpleEntry<>(89, 225), new AbstractMap.SimpleEntry<>(90, 223), new AbstractMap.SimpleEntry<>(91, 163), new AbstractMap.SimpleEntry<>(92, 91), new AbstractMap.SimpleEntry<>(93, 182), new AbstractMap.SimpleEntry<>(94, 113), new AbstractMap.SimpleEntry<>(95, 226), new AbstractMap.SimpleEntry<>(96, 217), new AbstractMap.SimpleEntry<>(97, 175), new AbstractMap.SimpleEntry<>(98, 67), new AbstractMap.SimpleEntry<>(99, 134), new AbstractMap.SimpleEntry<>(100, 17), new AbstractMap.SimpleEntry<>(101, 34), new AbstractMap.SimpleEntry<>(102, 68), new AbstractMap.SimpleEntry<>(103, 136), new AbstractMap.SimpleEntry<>(104, 13), new AbstractMap.SimpleEntry<>(105, 26), new AbstractMap.SimpleEntry<>(106, 52), new AbstractMap.SimpleEntry<>(107, 104), new AbstractMap.SimpleEntry<>(108, 208), new AbstractMap.SimpleEntry<>(109, 189), new AbstractMap.SimpleEntry<>(110, 103), new AbstractMap.SimpleEntry<>(111, 206), new AbstractMap.SimpleEntry<>(112, 129), new AbstractMap.SimpleEntry<>(113, 31), new AbstractMap.SimpleEntry<>(114, 62), new AbstractMap.SimpleEntry<>(115, 124), new AbstractMap.SimpleEntry<>(116, 248), new AbstractMap.SimpleEntry<>(117, 237), new AbstractMap.SimpleEntry<>(118, 199), new AbstractMap.SimpleEntry<>(119, 147), new AbstractMap.SimpleEntry<>(120, 59), new AbstractMap.SimpleEntry<>(121, 118), new AbstractMap.SimpleEntry<>(122, 236), new AbstractMap.SimpleEntry<>(123, 197), new AbstractMap.SimpleEntry<>(124, 151), new AbstractMap.SimpleEntry<>(125, 51), new AbstractMap.SimpleEntry<>(126, 102), new AbstractMap.SimpleEntry<>(127, 204), new AbstractMap.SimpleEntry<>(128, 133), new AbstractMap.SimpleEntry<>(129, 23), new AbstractMap.SimpleEntry<>(130, 46), new AbstractMap.SimpleEntry<>(131, 92), new AbstractMap.SimpleEntry<>(132, 184), new AbstractMap.SimpleEntry<>(133, 109), new AbstractMap.SimpleEntry<>(134, 218), new AbstractMap.SimpleEntry<>(135, 169), new AbstractMap.SimpleEntry<>(136, 79), new AbstractMap.SimpleEntry<>(137, 158), new AbstractMap.SimpleEntry<>(138, 33), new AbstractMap.SimpleEntry<>(139, 66), new AbstractMap.SimpleEntry<>(140, 132), new AbstractMap.SimpleEntry<>(141, 21), new AbstractMap.SimpleEntry<>(142, 42), new AbstractMap.SimpleEntry<>(143, 84), new AbstractMap.SimpleEntry<>(144, 168), new AbstractMap.SimpleEntry<>(145, 77), new AbstractMap.SimpleEntry<>(146, 154), new AbstractMap.SimpleEntry<>(147, 41), new AbstractMap.SimpleEntry<>(148, 82), new AbstractMap.SimpleEntry<>(149, 164), new AbstractMap.SimpleEntry<>(150, 85), new AbstractMap.SimpleEntry<>(151, 170), new AbstractMap.SimpleEntry<>(152, 73), new AbstractMap.SimpleEntry<>(153, 146), new AbstractMap.SimpleEntry<>(154, 57), new AbstractMap.SimpleEntry<>(155, 114), new AbstractMap.SimpleEntry<>(156, 228), new AbstractMap.SimpleEntry<>(157, 213), new AbstractMap.SimpleEntry<>(158, 183), new AbstractMap.SimpleEntry<>(159, 115), new AbstractMap.SimpleEntry<>(160, 230), new AbstractMap.SimpleEntry<>(161, 209), new AbstractMap.SimpleEntry<>(162, 191), new AbstractMap.SimpleEntry<>(163, 99), new AbstractMap.SimpleEntry<>(164, 198), new AbstractMap.SimpleEntry<>(165, 145), new AbstractMap.SimpleEntry<>(166, 63), new AbstractMap.SimpleEntry<>(167, 126), new AbstractMap.SimpleEntry<>(168, 252), new AbstractMap.SimpleEntry<>(169, 229), new AbstractMap.SimpleEntry<>(170, 215), new AbstractMap.SimpleEntry<>(171, 179), new AbstractMap.SimpleEntry<>(172, 123), new AbstractMap.SimpleEntry<>(173, 246), new AbstractMap.SimpleEntry<>(174, 241), new AbstractMap.SimpleEntry<>(175, 255), new AbstractMap.SimpleEntry<>(176, 227), new AbstractMap.SimpleEntry<>(177, 219), new AbstractMap.SimpleEntry<>(178, 171), new AbstractMap.SimpleEntry<>(179, 75), new AbstractMap.SimpleEntry<>(180, 150), new AbstractMap.SimpleEntry<>(181, 49), new AbstractMap.SimpleEntry<>(182, 98), new AbstractMap.SimpleEntry<>(183, 196), new AbstractMap.SimpleEntry<>(184, 149), new AbstractMap.SimpleEntry<>(185, 55), new AbstractMap.SimpleEntry<>(186, 110), new AbstractMap.SimpleEntry<>(187, 220), new AbstractMap.SimpleEntry<>(188, 165), new AbstractMap.SimpleEntry<>(189, 87), new AbstractMap.SimpleEntry<>(190, 174), new AbstractMap.SimpleEntry<>(191, 65), new AbstractMap.SimpleEntry<>(192, 130), new AbstractMap.SimpleEntry<>(193, 25), new AbstractMap.SimpleEntry<>(194, 50), new AbstractMap.SimpleEntry<>(195, 100), new AbstractMap.SimpleEntry<>(196, 200), new AbstractMap.SimpleEntry<>(197, 141), new AbstractMap.SimpleEntry<>(198, 7), new AbstractMap.SimpleEntry<>(199, 14), new AbstractMap.SimpleEntry<>(200, 28), new AbstractMap.SimpleEntry<>(201, 56), new AbstractMap.SimpleEntry<>(202, 112), new AbstractMap.SimpleEntry<>(203, 224), new AbstractMap.SimpleEntry<>(204, 221), new AbstractMap.SimpleEntry<>(205, 167), new AbstractMap.SimpleEntry<>(206, 83), new AbstractMap.SimpleEntry<>(207, 166), new AbstractMap.SimpleEntry<>(208, 81), new AbstractMap.SimpleEntry<>(209, 162), new AbstractMap.SimpleEntry<>(210, 89), new AbstractMap.SimpleEntry<>(211, 178), new AbstractMap.SimpleEntry<>(212, 121), new AbstractMap.SimpleEntry<>(213, 242), new AbstractMap.SimpleEntry<>(214, 249), new AbstractMap.SimpleEntry<>(215, 239), new AbstractMap.SimpleEntry<>(216, 195), new AbstractMap.SimpleEntry<>(217, 155), new AbstractMap.SimpleEntry<>(218, 43), new AbstractMap.SimpleEntry<>(219, 86), new AbstractMap.SimpleEntry<>(220, 172), new AbstractMap.SimpleEntry<>(221, 69), new AbstractMap.SimpleEntry<>(222, 138), new AbstractMap.SimpleEntry<>(223, 9), new AbstractMap.SimpleEntry<>(224, 18), new AbstractMap.SimpleEntry<>(225, 36), new AbstractMap.SimpleEntry<>(226, 72), new AbstractMap.SimpleEntry<>(227, 144), new AbstractMap.SimpleEntry<>(228, 61), new AbstractMap.SimpleEntry<>(229, 122), new AbstractMap.SimpleEntry<>(230, 244), new AbstractMap.SimpleEntry<>(231, 245), new AbstractMap.SimpleEntry<>(232, 247), new AbstractMap.SimpleEntry<>(233, 243), new AbstractMap.SimpleEntry<>(234, 251), new AbstractMap.SimpleEntry<>(235, 235), new AbstractMap.SimpleEntry<>(236, 203), new AbstractMap.SimpleEntry<>(237, 139), new AbstractMap.SimpleEntry<>(238, 11), new AbstractMap.SimpleEntry<>(239, 22), new AbstractMap.SimpleEntry<>(240, 44), new AbstractMap.SimpleEntry<>(241, 88), new AbstractMap.SimpleEntry<>(242, 176), new AbstractMap.SimpleEntry<>(243, 125), new AbstractMap.SimpleEntry<>(244, 250), new AbstractMap.SimpleEntry<>(245, 233), new AbstractMap.SimpleEntry<>(246, 207), new AbstractMap.SimpleEntry<>(247, 131), new AbstractMap.SimpleEntry<>(248, 27), new AbstractMap.SimpleEntry<>(249, 54), new AbstractMap.SimpleEntry<>(250, 108), new AbstractMap.SimpleEntry<>(251, 216), new AbstractMap.SimpleEntry<>(252, 173), new AbstractMap.SimpleEntry<>(253, 71), new AbstractMap.SimpleEntry<>(254, 142), new AbstractMap.SimpleEntry<>(255, 1));
    
    
    /**
     * Antilog table
     */
    private static final Map<Integer, Integer> antilogTable = Map.<Integer, Integer>ofEntries(new AbstractMap.SimpleEntry<>(1, 0), new AbstractMap.SimpleEntry<>(2, 1), new AbstractMap.SimpleEntry<>(3, 25), new AbstractMap.SimpleEntry<>(4, 2), new AbstractMap.SimpleEntry<>(5, 50), new AbstractMap.SimpleEntry<>(6, 26), new AbstractMap.SimpleEntry<>(7, 198), new AbstractMap.SimpleEntry<>(8, 3), new AbstractMap.SimpleEntry<>(9, 223), new AbstractMap.SimpleEntry<>(10, 51), new AbstractMap.SimpleEntry<>(11, 238), new AbstractMap.SimpleEntry<>(12, 27), new AbstractMap.SimpleEntry<>(13, 104), new AbstractMap.SimpleEntry<>(14, 199), new AbstractMap.SimpleEntry<>(15, 75), new AbstractMap.SimpleEntry<>(16, 4), new AbstractMap.SimpleEntry<>(17, 100), new AbstractMap.SimpleEntry<>(18, 224), new AbstractMap.SimpleEntry<>(19, 14), new AbstractMap.SimpleEntry<>(20, 52), new AbstractMap.SimpleEntry<>(21, 141), new AbstractMap.SimpleEntry<>(22, 239), new AbstractMap.SimpleEntry<>(23, 129), new AbstractMap.SimpleEntry<>(24, 28), new AbstractMap.SimpleEntry<>(25, 193), new AbstractMap.SimpleEntry<>(26, 105), new AbstractMap.SimpleEntry<>(27, 248), new AbstractMap.SimpleEntry<>(28, 200), new AbstractMap.SimpleEntry<>(29, 8), new AbstractMap.SimpleEntry<>(30, 76), new AbstractMap.SimpleEntry<>(31, 113), new AbstractMap.SimpleEntry<>(32, 5), new AbstractMap.SimpleEntry<>(33, 138), new AbstractMap.SimpleEntry<>(34, 101), new AbstractMap.SimpleEntry<>(35, 47), new AbstractMap.SimpleEntry<>(36, 225), new AbstractMap.SimpleEntry<>(37, 36), new AbstractMap.SimpleEntry<>(38, 15), new AbstractMap.SimpleEntry<>(39, 33), new AbstractMap.SimpleEntry<>(40, 53), new AbstractMap.SimpleEntry<>(41, 147), new AbstractMap.SimpleEntry<>(42, 142), new AbstractMap.SimpleEntry<>(43, 218), new AbstractMap.SimpleEntry<>(44, 240), new AbstractMap.SimpleEntry<>(45, 18), new AbstractMap.SimpleEntry<>(46, 130), new AbstractMap.SimpleEntry<>(47, 69), new AbstractMap.SimpleEntry<>(48, 29), new AbstractMap.SimpleEntry<>(49, 181), new AbstractMap.SimpleEntry<>(50, 194), new AbstractMap.SimpleEntry<>(51, 125), new AbstractMap.SimpleEntry<>(52, 106), new AbstractMap.SimpleEntry<>(53, 39), new AbstractMap.SimpleEntry<>(54, 249), new AbstractMap.SimpleEntry<>(55, 185), new AbstractMap.SimpleEntry<>(56, 201), new AbstractMap.SimpleEntry<>(57, 154), new AbstractMap.SimpleEntry<>(58, 9), new AbstractMap.SimpleEntry<>(59, 120), new AbstractMap.SimpleEntry<>(60, 77), new AbstractMap.SimpleEntry<>(61, 228), new AbstractMap.SimpleEntry<>(62, 114), new AbstractMap.SimpleEntry<>(63, 166), new AbstractMap.SimpleEntry<>(64, 6), new AbstractMap.SimpleEntry<>(65, 191), new AbstractMap.SimpleEntry<>(66, 139), new AbstractMap.SimpleEntry<>(67, 98), new AbstractMap.SimpleEntry<>(68, 102), new AbstractMap.SimpleEntry<>(69, 221), new AbstractMap.SimpleEntry<>(70, 48), new AbstractMap.SimpleEntry<>(71, 253), new AbstractMap.SimpleEntry<>(72, 226), new AbstractMap.SimpleEntry<>(73, 152), new AbstractMap.SimpleEntry<>(74, 37), new AbstractMap.SimpleEntry<>(75, 179), new AbstractMap.SimpleEntry<>(76, 16), new AbstractMap.SimpleEntry<>(77, 145), new AbstractMap.SimpleEntry<>(78, 34), new AbstractMap.SimpleEntry<>(79, 136), new AbstractMap.SimpleEntry<>(80, 54), new AbstractMap.SimpleEntry<>(81, 208), new AbstractMap.SimpleEntry<>(82, 148), new AbstractMap.SimpleEntry<>(83, 206), new AbstractMap.SimpleEntry<>(84, 143), new AbstractMap.SimpleEntry<>(85, 150), new AbstractMap.SimpleEntry<>(86, 219), new AbstractMap.SimpleEntry<>(87, 189), new AbstractMap.SimpleEntry<>(88, 241), new AbstractMap.SimpleEntry<>(89, 210), new AbstractMap.SimpleEntry<>(90, 19), new AbstractMap.SimpleEntry<>(91, 92), new AbstractMap.SimpleEntry<>(92, 131), new AbstractMap.SimpleEntry<>(93, 56), new AbstractMap.SimpleEntry<>(94, 70), new AbstractMap.SimpleEntry<>(95, 64), new AbstractMap.SimpleEntry<>(96, 30), new AbstractMap.SimpleEntry<>(97, 66), new AbstractMap.SimpleEntry<>(98, 182), new AbstractMap.SimpleEntry<>(99, 163), new AbstractMap.SimpleEntry<>(100, 195), new AbstractMap.SimpleEntry<>(101, 72), new AbstractMap.SimpleEntry<>(102, 126), new AbstractMap.SimpleEntry<>(103, 110), new AbstractMap.SimpleEntry<>(104, 107), new AbstractMap.SimpleEntry<>(105, 58), new AbstractMap.SimpleEntry<>(106, 40), new AbstractMap.SimpleEntry<>(107, 84), new AbstractMap.SimpleEntry<>(108, 250), new AbstractMap.SimpleEntry<>(109, 133), new AbstractMap.SimpleEntry<>(110, 186), new AbstractMap.SimpleEntry<>(111, 61), new AbstractMap.SimpleEntry<>(112, 202), new AbstractMap.SimpleEntry<>(113, 94), new AbstractMap.SimpleEntry<>(114, 155), new AbstractMap.SimpleEntry<>(115, 159), new AbstractMap.SimpleEntry<>(116, 10), new AbstractMap.SimpleEntry<>(117, 21), new AbstractMap.SimpleEntry<>(118, 121), new AbstractMap.SimpleEntry<>(119, 43), new AbstractMap.SimpleEntry<>(120, 78), new AbstractMap.SimpleEntry<>(121, 212), new AbstractMap.SimpleEntry<>(122, 229), new AbstractMap.SimpleEntry<>(123, 172), new AbstractMap.SimpleEntry<>(124, 115), new AbstractMap.SimpleEntry<>(125, 243), new AbstractMap.SimpleEntry<>(126, 167), new AbstractMap.SimpleEntry<>(127, 87), new AbstractMap.SimpleEntry<>(128, 7), new AbstractMap.SimpleEntry<>(129, 112), new AbstractMap.SimpleEntry<>(130, 192), new AbstractMap.SimpleEntry<>(131, 247), new AbstractMap.SimpleEntry<>(132, 140), new AbstractMap.SimpleEntry<>(133, 128), new AbstractMap.SimpleEntry<>(134, 99), new AbstractMap.SimpleEntry<>(135, 13), new AbstractMap.SimpleEntry<>(136, 103), new AbstractMap.SimpleEntry<>(137, 74), new AbstractMap.SimpleEntry<>(138, 222), new AbstractMap.SimpleEntry<>(139, 237), new AbstractMap.SimpleEntry<>(140, 49), new AbstractMap.SimpleEntry<>(141, 197), new AbstractMap.SimpleEntry<>(142, 254), new AbstractMap.SimpleEntry<>(143, 24), new AbstractMap.SimpleEntry<>(144, 227), new AbstractMap.SimpleEntry<>(145, 165), new AbstractMap.SimpleEntry<>(146, 153), new AbstractMap.SimpleEntry<>(147, 119), new AbstractMap.SimpleEntry<>(148, 38), new AbstractMap.SimpleEntry<>(149, 184), new AbstractMap.SimpleEntry<>(150, 180), new AbstractMap.SimpleEntry<>(151, 124), new AbstractMap.SimpleEntry<>(152, 17), new AbstractMap.SimpleEntry<>(153, 68), new AbstractMap.SimpleEntry<>(154, 146), new AbstractMap.SimpleEntry<>(155, 217), new AbstractMap.SimpleEntry<>(156, 35), new AbstractMap.SimpleEntry<>(157, 32), new AbstractMap.SimpleEntry<>(158, 137), new AbstractMap.SimpleEntry<>(159, 46), new AbstractMap.SimpleEntry<>(160, 55), new AbstractMap.SimpleEntry<>(161, 63), new AbstractMap.SimpleEntry<>(162, 209), new AbstractMap.SimpleEntry<>(163, 91), new AbstractMap.SimpleEntry<>(164, 149), new AbstractMap.SimpleEntry<>(165, 188), new AbstractMap.SimpleEntry<>(166, 207), new AbstractMap.SimpleEntry<>(167, 205), new AbstractMap.SimpleEntry<>(168, 144), new AbstractMap.SimpleEntry<>(169, 135), new AbstractMap.SimpleEntry<>(170, 151), new AbstractMap.SimpleEntry<>(171, 178), new AbstractMap.SimpleEntry<>(172, 220), new AbstractMap.SimpleEntry<>(173, 252), new AbstractMap.SimpleEntry<>(174, 190), new AbstractMap.SimpleEntry<>(175, 97), new AbstractMap.SimpleEntry<>(176, 242), new AbstractMap.SimpleEntry<>(177, 86), new AbstractMap.SimpleEntry<>(178, 211), new AbstractMap.SimpleEntry<>(179, 171), new AbstractMap.SimpleEntry<>(180, 20), new AbstractMap.SimpleEntry<>(181, 42), new AbstractMap.SimpleEntry<>(182, 93), new AbstractMap.SimpleEntry<>(183, 158), new AbstractMap.SimpleEntry<>(184, 132), new AbstractMap.SimpleEntry<>(185, 60), new AbstractMap.SimpleEntry<>(186, 57), new AbstractMap.SimpleEntry<>(187, 83), new AbstractMap.SimpleEntry<>(188, 71), new AbstractMap.SimpleEntry<>(189, 109), new AbstractMap.SimpleEntry<>(190, 65), new AbstractMap.SimpleEntry<>(191, 162), new AbstractMap.SimpleEntry<>(192, 31), new AbstractMap.SimpleEntry<>(193, 45), new AbstractMap.SimpleEntry<>(194, 67), new AbstractMap.SimpleEntry<>(195, 216), new AbstractMap.SimpleEntry<>(196, 183), new AbstractMap.SimpleEntry<>(197, 123), new AbstractMap.SimpleEntry<>(198, 164), new AbstractMap.SimpleEntry<>(199, 118), new AbstractMap.SimpleEntry<>(200, 196), new AbstractMap.SimpleEntry<>(201, 23), new AbstractMap.SimpleEntry<>(202, 73), new AbstractMap.SimpleEntry<>(203, 236), new AbstractMap.SimpleEntry<>(204, 127), new AbstractMap.SimpleEntry<>(205, 12), new AbstractMap.SimpleEntry<>(206, 111), new AbstractMap.SimpleEntry<>(207, 246), new AbstractMap.SimpleEntry<>(208, 108), new AbstractMap.SimpleEntry<>(209, 161), new AbstractMap.SimpleEntry<>(210, 59), new AbstractMap.SimpleEntry<>(211, 82), new AbstractMap.SimpleEntry<>(212, 41), new AbstractMap.SimpleEntry<>(213, 157), new AbstractMap.SimpleEntry<>(214, 85), new AbstractMap.SimpleEntry<>(215, 170), new AbstractMap.SimpleEntry<>(216, 251), new AbstractMap.SimpleEntry<>(217, 96), new AbstractMap.SimpleEntry<>(218, 134), new AbstractMap.SimpleEntry<>(219, 177), new AbstractMap.SimpleEntry<>(220, 187), new AbstractMap.SimpleEntry<>(221, 204), new AbstractMap.SimpleEntry<>(222, 62), new AbstractMap.SimpleEntry<>(223, 90), new AbstractMap.SimpleEntry<>(224, 203), new AbstractMap.SimpleEntry<>(225, 89), new AbstractMap.SimpleEntry<>(226, 95), new AbstractMap.SimpleEntry<>(227, 176), new AbstractMap.SimpleEntry<>(228, 156), new AbstractMap.SimpleEntry<>(229, 169), new AbstractMap.SimpleEntry<>(230, 160), new AbstractMap.SimpleEntry<>(231, 81), new AbstractMap.SimpleEntry<>(232, 11), new AbstractMap.SimpleEntry<>(233, 245), new AbstractMap.SimpleEntry<>(234, 22), new AbstractMap.SimpleEntry<>(235, 235), new AbstractMap.SimpleEntry<>(236, 122), new AbstractMap.SimpleEntry<>(237, 117), new AbstractMap.SimpleEntry<>(238, 44), new AbstractMap.SimpleEntry<>(239, 215), new AbstractMap.SimpleEntry<>(240, 79), new AbstractMap.SimpleEntry<>(241, 174), new AbstractMap.SimpleEntry<>(242, 213), new AbstractMap.SimpleEntry<>(243, 233), new AbstractMap.SimpleEntry<>(244, 230), new AbstractMap.SimpleEntry<>(245, 231), new AbstractMap.SimpleEntry<>(246, 173), new AbstractMap.SimpleEntry<>(247, 232), new AbstractMap.SimpleEntry<>(248, 116), new AbstractMap.SimpleEntry<>(249, 214), new AbstractMap.SimpleEntry<>(250, 244), new AbstractMap.SimpleEntry<>(251, 234), new AbstractMap.SimpleEntry<>(252, 168), new AbstractMap.SimpleEntry<>(253, 80), new AbstractMap.SimpleEntry<>(254, 88), new AbstractMap.SimpleEntry<>(255, 175));

    
    /**
     * Error Correction Codewords tabke
     */
    private static final int [][] eccTable = {{7, 1, 19, 0, 0}, {10, 1, 16, 0, 0}, {13, 1, 13, 0, 0}, {17, 1, 9, 0, 0}, {10, 1, 34, 0, 0}, {16, 1, 28, 0, 0}, {22, 1, 22, 0, 0}, {28, 1, 16, 0, 0}, {15, 1, 55, 0, 0}, {26, 1, 44, 0, 0}, {18, 2, 17, 0, 0}, {22, 2, 13, 0, 0}, {20, 1, 80, 0, 0}, {18, 2, 32, 0, 0}, {26, 2, 24, 0, 0}, {16, 4, 9, 0, 0}, {26, 1, 108, 0, 0}, {24, 2, 43, 0, 0}, {18, 2, 15, 2, 16}, {22, 2, 11, 2, 12}, {18, 2, 68, 0, 0}, {16, 4, 27, 0, 0}, {24, 4, 19, 0, 0}, {28, 4, 15, 0, 0}, {20, 2, 78, 0, 0}, {18, 4, 31, 0, 0}, {18, 2, 14, 4, 15}, {26, 4, 13, 1, 14}, {24, 2, 97, 0, 0}, {22, 2, 38, 2, 39}, {22, 4, 18, 2, 19}, {26, 4, 14, 2, 15}, {30, 2, 116, 0, 0}, {22, 3, 36, 2, 37}, {20, 4, 16, 4, 17}, {24, 4, 12, 4, 13}, {18, 2, 68, 2, 69}, {26, 4, 43, 1, 44}, {24, 6, 19, 2, 20}, {28, 6, 15, 2, 16}, {20, 4, 81, 0, 0}, {30, 1, 50, 4, 51}, {28, 4, 22, 4, 23}, {24, 3, 12, 8, 13}, {24, 2, 92, 2, 93}, {22, 6, 36, 2, 37}, {26, 4, 20, 6, 21}, {28, 7, 14, 4, 15}, {26, 4, 107, 0, 0}, {22, 8, 37, 1, 38}, {24, 8, 20, 4, 21}, {22, 12, 11, 4, 12}, {30, 3, 115, 1, 116}, {24, 4, 40, 5, 41}, {20, 11, 16, 5, 17}, {24, 11, 12, 5, 13}, {22, 5, 87, 1, 88}, {24, 5, 41, 5, 42}, {30, 5, 24, 7, 25}, {24, 11, 12, 7, 13}, {24, 5, 98, 1, 99}, {28, 7, 45, 3, 46}, {24, 15, 19, 2, 20}, {30, 3, 15, 13, 16}, {28, 1, 107, 5, 108}, {28, 10, 46, 1, 47}, {28, 1, 22, 15, 23}, {28, 2, 14, 17, 15}, {30, 5, 120, 1, 121}, {26, 9, 43, 4, 44}, {28, 17, 22, 1, 23}, {28, 2, 14, 19, 15}, {28, 3, 113, 4, 114}, {26, 3, 44, 11, 45}, {26, 17, 21, 4, 22}, {26, 9, 13, 16, 14}, {28, 3, 107, 5, 108}, {26, 3, 41, 13, 42}, {30, 15, 24, 5, 25}, {28, 15, 15, 10, 16}, {28, 4, 116, 4, 117}, {26, 17, 42, 0, 0}, {28, 17, 22, 6, 23}, {30, 19, 16, 6, 17}, {28, 2, 111, 7, 112}, {28, 17, 46, 0, 0}, {30, 7, 24, 16, 25}, {24, 34, 13, 0, 0}, {30, 4, 121, 5, 122}, {28, 4, 47, 14, 48}, {30, 11, 24, 14, 25}, {30, 16, 15, 14, 16}, {30, 6, 117, 4, 118}, {28, 6, 45, 14, 46}, {30, 11, 24, 16, 25}, {30, 30, 16, 2, 17}, {26, 8, 106, 4, 107}, {28, 8, 47, 13, 48}, {30, 7, 24, 22, 25}, {30, 22, 15, 13, 16}, {28, 10, 114, 2, 115}, {28, 19, 46, 4, 47}, {28, 28, 22, 6, 23}, {30, 33, 16, 4, 17}, {30, 8, 122, 4, 123}, {28, 22, 45, 3, 46}, {30, 8, 23, 26, 24}, {30, 12, 15, 28, 16}, {30, 3, 117, 10, 118}, {28, 3, 45, 23, 46}, {30, 4, 24, 31, 25}, {30, 11, 15, 31, 16}, {30, 7, 116, 7, 117}, {28, 21, 45, 7, 46}, {30, 1, 23, 37, 24}, {30, 19, 15, 26, 16}, {30, 5, 115, 10, 116}, {28, 19, 47, 10, 48}, {30, 15, 24, 25, 25}, {30, 23, 15, 25, 16}, {30, 13, 115, 3, 116}, {28, 2, 46, 29, 47}, {30, 42, 24, 1, 25}, {30, 23, 15, 28, 16}, {30, 17, 115, 0, 0}, {28, 10, 46, 23, 47}, {30, 10, 24, 35, 25}, {30, 19, 15, 35, 16}, {30, 17, 115, 1, 116}, {28, 14, 46, 21, 47}, {30, 29, 24, 19, 25}, {30, 11, 15, 46, 16}, {30, 13, 115, 6, 116}, {28, 14, 46, 23, 47}, {30, 44, 24, 7, 25}, {30, 59, 16, 1, 17}, {30, 12, 121, 7, 122}, {28, 12, 47, 26, 48}, {30, 39, 24, 14, 25}, {30, 22, 15, 41, 16}, {30, 6, 121, 14, 122}, {28, 6, 47, 34, 48}, {30, 46, 24, 10, 25}, {30, 2, 15, 64, 16}, {30, 17, 122, 4, 123}, {28, 29, 46, 14, 47}, {30, 49, 24, 10, 25}, {30, 24, 15, 46, 16}, {30, 4, 122, 18, 123}, {28, 13, 46, 32, 47}, {30, 48, 24, 14, 25}, {30, 42, 15, 32, 16}, {30, 20, 117, 4, 118}, {28, 40, 47, 7, 48}, {30, 43, 24, 22, 25}, {30, 10, 15, 67, 16}, {30, 19, 118, 6, 119}, {28, 18, 47, 31, 48}, {30, 34, 24, 34, 25}, {30, 20, 15, 61, 16}};

    
    /**
     * Required reminder bits table
     */
    private static final int [] requiredReminderBitsTable = {0, 7, 7, 7, 7, 7, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0};


    public ErrorCorrectionCode(int versionNumber, int eccLevel, String input)
    {
        generatePolynomials();
        getPolynomial(versionNumber, eccLevel, input);
    }


    /**
     * Generates polynomials
     */
    public void generatePolynomials()
    {
        Vector<Integer>t = new Vector<>();
        t.add(0);
        t.add(25);
        t.add(1);
        generatedPolynomials.add(t);
        for(int i = 3; i <= 255; i++)
        {
            t = new Vector<>();
            int lastA = i - 1;
            t.add(0);
            for(int j = 1; j < generatedPolynomials.get(i - 3).size(); j++)
            {
                Integer p1 = antilogTable.get(logTable.get(generatedPolynomials.get(i - 3).get(j) % 255) ^ logTable.get(lastA % 255));
                t.add(p1);
                lastA = generatedPolynomials.get(i - 3).get(j) + i-1;
            }
            t.add(lastA);
            generatedPolynomials.add(t);
        }
    }


    /**
     * Generates ECC for each block
     * @param input single block of input
     * @param numberOfCodewords number of Error Correction Codewords for each block
     * @return generates ECC
     */
    public Vector<Integer> messagePolynomial(String input, int numberOfCodewords)
    {
        Vector<Integer>message = new Vector<>();
        Vector<Integer>g = generatedPolynomials.get(numberOfCodewords - 2);
        for(int i = 0; i < input.length(); i += 8)
        {
            int x = 0;
            for(int j = 0; j < 8; j++)
                x += Math.pow(2, 7 - j) * (input.charAt(j + i) - (int)'0');
            message.add(x);
        }
        System.out.println();
        dataCodewords.add((Vector<Integer>)message.clone());

        for(int i = 0; i < input.length() / 8; i++)
        {
            int a = antilogTable.get(message.get(0));
            while(g.size() > message.size())
                message.add(0);

            for(int j = 0; j < g.size(); j++)
                message.set(j, logTable.get((g.get(j) + a) % 255) ^ message.get(j));

            message.remove(0);
        }
        return message;
    }


    /**
     * Generates final encoding
     * @param versionNumber Smallest version for the input data
     * @param ecLevel Error Correction Level
     * @param input encoded input
     */
    public void getPolynomial(int versionNumber, int ecLevel, String input)
    {
        int numberOfCodewords = eccTable[versionNumber * 4 + ecLevel][0];
        int gr1 = eccTable[versionNumber * 4 + ecLevel][1], gr1_num = eccTable[versionNumber * 4 + ecLevel][2];
        int gr2_num = eccTable[versionNumber * 4 + ecLevel][4];

        int j = 0;
        for(; j < gr1 * gr1_num * 8; j += gr1_num * 8)
            errorCorrectionCodewords.add(messagePolynomial(input.substring(j, j + gr1_num * 8), numberOfCodewords));

        for(; j < input.length(); j += gr2_num * 8)
            errorCorrectionCodewords.add(messagePolynomial(input.substring(j, j + gr2_num * 8), numberOfCodewords));

        for(int i = 0; i < Math.max(gr1_num, gr2_num); i++)
            for(j = 0; j < dataCodewords.size(); j++)
                if(dataCodewords.get(j).size() > i)
                    finalMessage = finalMessage.concat(QrCodeGenerator.binary(dataCodewords.get(j).get(i), 8));

        for(int i = 0; i < numberOfCodewords; i++)
            for(j = 0; j < errorCorrectionCodewords.size(); j++)
                finalMessage = finalMessage.concat(QrCodeGenerator.binary(errorCorrectionCodewords.get(j).get(i), 8));

        j = 0;
        while(j < requiredReminderBitsTable[versionNumber]) {
            finalMessage = finalMessage.concat("0");
            j++;
        }
    }

    public String getFinalMessage()
    {
        return finalMessage;
    }
}
