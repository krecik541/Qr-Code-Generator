package com.example.qrcodegeneratorui.generator;

public class DrawQR {
    /**
     * Size of the qr code
     */
    private final int size;


    /**
     * Generated qr code
     */
    private char [][] qr;


    /**
     * Using when applying masks
     */
    private final boolean [][] qrR;


    /**
     * Positions of the alignment patterns based on qr code version
     */
    private static final int [][] alignmentPatternsTable = {{6, 18, 0, 0, 0, 0, 0},{6, 22, 0, 0, 0, 0, 0},{6, 26, 0, 0, 0, 0, 0},{6, 30, 0, 0, 0, 0, 0},{6, 34, 0, 0, 0, 0, 0},{6, 22, 38, 0, 0, 0, 0},{6, 24, 42, 0, 0, 0, 0},{6, 26, 46, 0, 0, 0, 0},{6, 28, 50, 0, 0, 0, 0},{6, 30, 54, 0, 0, 0, 0},{6, 32, 58, 0, 0, 0, 0},{6, 34, 62, 0, 0, 0, 0},{6, 26, 46, 66, 0, 0, 0},{6, 26, 48, 70, 0, 0, 0},{6, 26, 50, 74, 0, 0, 0},{6, 30, 54, 78, 0, 0, 0},{6, 30, 56, 82, 0, 0, 0},{6, 30, 58, 86, 0, 0, 0},{6, 34, 62, 90, 0, 0, 0},{6, 28, 50, 72, 94, 0, 0},{6, 26, 50, 74, 98, 0, 0},{6, 30, 54, 78, 102, 0, 0},{6, 28, 54, 80, 106, 0, 0},{6, 32, 58, 84, 110, 0, 0},{6, 30, 58, 86, 114, 0, 0},{6, 34, 62, 90, 118, 0, 0},{6, 26, 50, 74, 98, 122, 0},{6, 30, 54, 78, 102, 126, 0},{6, 26, 52, 78, 104, 130, 0},{6, 30, 56, 82, 108, 134, 0},{6, 34, 60, 86, 112, 138, 0},{6, 30, 58, 86, 114, 142, 0},{6, 34, 62, 90, 118, 146, 0},{6, 30, 54, 78, 102, 126, 150},{6, 24, 50, 76, 102, 128, 154},{6, 28, 54, 80, 106, 132, 158},{6, 32, 58, 84, 110, 136, 162},{6, 26, 54, 82, 110, 138, 166},{6, 30, 58, 86, 114, 142, 170}};


    public DrawQR(int versionNumber, String input, int errCorrectionLevel)
    {
        size = versionNumber * 4 + 21;
        qr = new char[size][size];
        qrR = new boolean[size][size];
        addFinderPatternsAndSeparators();
        if(versionNumber != 0)
            addAlignmentPatterns(versionNumber);
        addTimingPatterns();
        qr[4 * (versionNumber + 1) + 9][8] = 'b';
        reserveFormatInformationArea(versionNumber);
        bitsToMasking();
        drawDataPlaceData(input);
        masking(errCorrectionLevel, versionNumber);
        draw();
    }


    /**
     * Adding finder patterns and separators to qr code
     */
    public void addFinderPatternsAndSeparators()
    {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                qr[i][j] = 'w';
                qr[size - i - 1][j] = 'w';
                qr[i][size - j - 1] = 'w';
            }
        }

        for(int i = 0; i < 7; i++) {
            qr[0][i] = 'b';
            qr[6][i] = 'b';
            qr[0][size - 1 - i] = 'b';
            qr[6][size - 1 - i] = 'b';
            qr[size-1][i] = 'b';
            qr[size-7][i] = 'b';
        }

        for(int i = 0; i < 7; i++)
        {
            qr[i][0] = 'b';
            qr[i][6] = 'b';
            qr[size - 1 - i][0] = 'b';
            qr[size - 1 - i][6] = 'b';
            qr[i][size-1] = 'b';
            qr[i][size-7] = 'b';
        }

        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++) {
                qr[i + 2][j + 2] = 'b';
                qr[i + 2][size - 3 - j] = 'b';
                qr[size - 3 - i][j + 2] = 'b';
            }
    }


    /**
     * Adding alignment patterns
     * @param versionNumber version number of qr code, needed for getting alignment patterns in proper places
     */
    public void addAlignmentPatterns(int versionNumber)
    {
        for(int i = 0; i < 7; i++)
        {
            for(int k = 0; k < 7; k++) {
                int x = alignmentPatternsTable[versionNumber - 1][i];
                int y = alignmentPatternsTable[versionNumber - 1][k];
                if (x == 0 || y == 0)
                    continue;
                if (!(qr[x - 2][y - 2] == 'b') && !(qr[x + 2][y - 2] == 'b') && !(qr[x - 2][y + 2] == 'b')) {
                    for(int j = 0; j < 3; j++)
                        for(int m = 0; m < 3; m++)
                            qr[x - 1 + j][y - 1 + m] = 'w';
                    for (int j = 0; j < 5; j++) {
                        qr[x - 2][y - 2 + j] = 'b';
                        qr[x + 2][y - 2 + j] = 'b';
                    }
                    for (int j = 0; j < 5; j++) {
                        qr[x - 2 + j][y - 2] = 'b';
                        qr[x - 2 + j][y + 2] = 'b';
                    }
                    qr[x][y] = 'b';
                }
            }
        }
    }


    /**
     * Adding timing patterns
     */
    public void addTimingPatterns()
    {
        for(int i = 8; i < size - 8; i+=2) {
            qr[6][i] = 'b';
            qr[6][i + 1] = 'w';
        }
        for(int i = 8; i < size - 8; i+=2) {
            qr[i][6] = 'b';
            qr[i + 1][6] = 'w';
        }
    }


    /**
     * Reserves place for format and, if needed, for version information
     * @param versionNumber if q code version is 7 and larger qr code contains areas where version information are coded
     */
    public void reserveFormatInformationArea(int versionNumber)
    {
        for(int i = 0; i < 6; i++)
            qr[i][8] = 'r';
        for(int i = 0; i < 6; i++)
            qr[8][i] = 'r';
        qr[7][8] = 'r';
        qr[8][8] = 'r';
        qr[8][7] = 'r';
        for(int i = 0; i < 7; i++)
            qr[size - i - 1][8] = 'r';
        for(int i = 0; i < 8; i++)
            qr[8][size - i - 1] = 'r';

        if(versionNumber >= 6)
        {
            for(int i = 0; i < 3; i++)
                for(int j = 0; j < 6; j++)
                    qr[j][size - i - 9] = 'r';

            for(int i = 0; i < 3; i++)
                for(int j = 0; j < 6; j++)
                    qr[size - i - 9][j] = 'r';
        }
    }


    /**
     * Places encoded input onto the prepared qr code
     * @param input encoded data
     */
    public void drawDataPlaceData(String input)
    {
        int k = 0;
        boolean up = true;
        for(int j = size - 1; j > 0; j-=2)
        {
            if(j == 6)
                j--;
            if(up)
                for(int i = size - 1; i >= 0; i--)
                {
                    if(qr[i][j] != 'b' && qr[i][j] != 'w' && qr[i][j] != 'r') {
                        qr[i][j] = input.charAt(k) == '1' ? 'b' : 'w';
                        k++;
                    }
                    if(qr[i][j - 1] != 'b' && qr[i][j - 1] != 'w' && qr[i][j - 1] != 'r') {
                        qr[i][j - 1] = input.charAt(k) == '1' ? 'b' : 'w';
                        k++;
                    }
                }
            else
                for(int i = 0; i < size; i++)
                {
                    if(qr[i][j] != 'b' && qr[i][j] != 'w' && qr[i][j] != 'r') {
                        qr[i][j] = input.charAt(k) == '1' ? 'b' : 'w';
                        k++;
                    }
                    if(qr[i][j - 1] != 'b' && qr[i][j - 1] != 'w' && qr[i][j - 1] != 'r') {
                        qr[i][j - 1] = input.charAt(k) == '1' ? 'b' : 'w';
                        k++;
                    }
                }
            up = !up;
        }
    }


    /**
     * Bits that remain true may be masked
     */
    public void bitsToMasking()
    {
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                if(qr[i][j] == 'w' || qr[i][j] == 'b' || qr[i][j] == 'r')
                    qrR[i][j] = true;
    }


    /**
     * Finding the best mask
     * @param errCorrLevel Error Correction Level
     * @param versionNumber version number of qr code
     */
    public void masking(int errCorrLevel, int versionNumber)
    {
        char [][] FINALqr = new char[size][];
        int minScore = Integer.MAX_VALUE;
        for(int i = 0; i < 8; i++)
        {
            char[][]copyQR = generateFormatString(errCorrLevel, versionNumber, i);
            for(int j = 0; j < size; j++)
                for (int k = 0; k < size; k++)
                    if (!qrR[j][k])
                        copyQR[j][k] = switch (i) {
                            case 0 -> (j + k) % 2;
                            case 1 -> j % 2;
                            case 2 -> k % 3;
                            case 3 -> (j + k) % 3;
                            case 4 -> (Math.floor(j / 2.0) + Math.floor(k / 3.0)) % 2;
                            case 5 -> (j * k) % 2 + (j * k) % 3;
                            case 6 -> ((j * k) % 2 + (j * k) % 3) % 2;
                            case 7 -> ((j + k) % 2 + (j * k) % 3) % 2;
                            default -> throw new RuntimeException("Error");
                        } == 0 ? (copyQR[j][k] == 'b' ? 'w' : 'b') : copyQR[j][k];

            int x = penalty(copyQR);
            if(x < minScore)
            {
                minScore = x;
                FINALqr = copy(copyQR);
            }
        }
        qr = FINALqr;
    }


    /**
     * Creates a copy of qr
     * @param qr array to be copied
     * @return copy of input param qr
     */
    public char[][]copy(char[][]qr)
    {
        char[][]copyQR = new char[size][];
        for(int i = 0; i < size; i++)
            copyQR[i] = qr[i].clone();
        return copyQR;
    }


    /**
     * Fills the reserved areas with format string and information about error correction level, version of qr code as well as number of mask
     * @param errCorrLevel error correction level of the qr code
     * @param versionNumber version of the qr code
     * @param masking number of mask
     * @return qr code wih filled reserved area
     */
    public char[][] generateFormatString(int errCorrLevel, int versionNumber, int masking)
    {

        char [][] qr = copy(this.qr);
        String formatString = switch (errCorrLevel)
        {
            case 0 -> "01";
            case 1 -> "00";
            case 2 -> "11";
            case 3 -> "10";
            default -> throw new RuntimeException("Error");
        };
        formatString = formatString.concat(QrCodeGenerator.binary(masking, 3)).concat("0000000000");
        String generatorPolynomial, k = formatString.substring(0, 5);

        while(!formatString.isEmpty() && formatString.charAt(0) == '0')
            formatString = formatString.substring(1);

        while (formatString.length() >= 11)
        {
            generatorPolynomial = "10100110111";
            while(generatorPolynomial.length() < formatString.length())
                generatorPolynomial = generatorPolynomial.concat("0");

            String s = "";
            for(int i = 0; i < formatString.length(); i++)
            {
                s = s.concat(String.valueOf(formatString.charAt(i) ^ generatorPolynomial.charAt(i)));
            }
            formatString = s;

            while(formatString.charAt(0) == '0')
                formatString = formatString.substring(1);
        }

        while (formatString.length() < 10)
            formatString = "0".concat(formatString);

        formatString = k.concat(formatString);
        k = "";
        for(int i = 0; i < formatString.length(); i++)
        {
            k = k.concat(String.valueOf(formatString.charAt(i) ^ "101010000010010".charAt(i)));
        }

        formatString = k;

        qr[8][size - 1] = formatString.charAt(14) == '1' ? 'b' : 'w';
        qr[0][8] = formatString.charAt(14) == '1' ? 'b' : 'w';

        qr[8][size - 2] = formatString.charAt(13) == '1' ? 'b' : 'w';
        qr[1][8] = formatString.charAt(13) == '1' ? 'b' : 'w';

        qr[8][size - 3] = formatString.charAt(12) == '1' ? 'b' : 'w';
        qr[2][8] = formatString.charAt(12) == '1' ? 'b' : 'w';

        qr[8][size - 4] = formatString.charAt(11) == '1' ? 'b' : 'w';
        qr[3][8] = formatString.charAt(11) == '1' ? 'b' : 'w';

        qr[8][size - 5] = formatString.charAt(10) == '1' ? 'b' : 'w';
        qr[4][8] = formatString.charAt(10) == '1' ? 'b' : 'w';

        qr[8][size - 6] = formatString.charAt(9) == '1' ? 'b' : 'w';
        qr[5][8] = formatString.charAt(9) == '1' ? 'b' : 'w';

        qr[8][size - 7] = formatString.charAt(8) == '1' ? 'b' : 'w';
        qr[7][8] = formatString.charAt(8) == '1' ? 'b' : 'w';

        qr[8][size - 8] = formatString.charAt(7) == '1' ? 'b' : 'w';
        qr[8][8] = formatString.charAt(7) == '1' ? 'b' : 'w';

        qr[size - 7][8] = formatString.charAt(6) == '1' ? 'b' : 'w';
        qr[8][7] = formatString.charAt(6) == '1' ? 'b' : 'w';

        qr[size - 6][8] = formatString.charAt(5) == '1' ? 'b' : 'w';
        qr[8][5] = formatString.charAt(5) == '1' ? 'b' : 'w';

        qr[size - 5][8] = formatString.charAt(4) == '1' ? 'b' : 'w';
        qr[8][4] = formatString.charAt(4) == '1' ? 'b' : 'w';

        qr[size - 4][8] = formatString.charAt(3) == '1' ? 'b' : 'w';
        qr[8][3] = formatString.charAt(3) == '1' ? 'b' : 'w';

        qr[size - 3][8] = formatString.charAt(2) == '1' ? 'b' : 'w';
        qr[8][2] = formatString.charAt(2) == '1' ? 'b' : 'w';

        qr[size - 2][8] = formatString.charAt(1) == '1' ? 'b' : 'w';
        qr[8][1] = formatString.charAt(1) == '1' ? 'b' : 'w';

        qr[size - 1][8] = formatString.charAt(0) == '1' ? 'b' : 'w';
        qr[8][0] = formatString.charAt(0) == '1' ? 'b' : 'w';

        if(versionNumber < 6)
            return qr;
        formatString = QrCodeGenerator.binary(versionNumber + 1, 6).concat("000000000000");
        k = formatString.substring(0, 6);

        while(formatString.charAt(0) == '0')
            formatString = formatString.substring(1);

        while (formatString.length() >= 13)
        {
            generatorPolynomial = "1111100100101";
            while(generatorPolynomial.length() < formatString.length())
                generatorPolynomial = generatorPolynomial.concat("0");

            String s = "";
            for(int i = 0; i < formatString.length(); i++)
            {
                s = s.concat(String.valueOf(formatString.charAt(i) ^ generatorPolynomial.charAt(i)));
            }
            formatString = s;

            while(formatString.charAt(0) == '0')
                formatString = formatString.substring(1);
        }

        while(formatString.length() < 12)
            formatString = "0".concat(formatString);

        formatString = k.concat(formatString);

        System.out.println();

        for(int i = 0; i < 6; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                qr[i][size - 11 + j] = formatString.charAt(formatString.length() - (i * 3 + j) - 1) == '1' ? 'b' : 'w';
                qr[size - 11 + j][i] = formatString.charAt(formatString.length() - (i * 3 + j) - 1) == '1' ? 'b' : 'w';
            }
        }
        return qr;
    }


    /**
     * Counts penalty score for specific qr
     * @param qr masked copy of earlier generated qr
     * @return penalty score
     */
    public int penalty(char [][] qr)
    {
        // #1
        int score = 0;
        for(int i = 0; i < size; i++)
        {
            int counter = 1;
            for(int j = 1; j < size; j++)
            {
                if(qr[i][j] == qr[i][j - 1])
                    counter++;
                else if(counter >= 5)
                {
                    score += 3 + (counter - 5);
                    counter = 1;
                }
                else
                    counter = 1;
            }
            if(counter >= 5)
                score += 3 + (counter - 5);
        }

        for(int i = 0; i < size; i++)
        {
            int counter = 1;
            for(int j = 1; j < size; j++)
            {
                if(qr[j][i] == qr[j - 1][i])
                    counter++;
                else if(counter >= 5)
                {
                    score += 3 + (counter - 5);
                    counter = 1;
                }
                else
                    counter = 1;
            }
            if(counter >= 5)
                score += 3 + (counter - 5);
        }

        // #2
        for(int i = 1; i < size; i++)
        {
            for(int j = 1; j < size; j++)
                if(qr[i][j] == qr[i - 1][j] && qr[i][j] == qr[i][j - 1] && qr[i][j] == qr[i - 1][j - 1])
                    score += 3;
        }

        // #3
        for(int i = 0; i < size; i++)
        {
            for(int j = 10; j < size; j++)
                if((qr[i][j] == 'b' && qr[i][j - 1] == 'w' && qr[i][j - 2] == 'b' && qr[i][j - 3] == 'b' &&
                        qr[i][j - 4] == 'b' && qr[i][j - 5] == 'w' && qr[i][j - 6] == 'b' && qr[i][j - 7] == 'w' &&
                        qr[i][j - 8] == 'w' && qr[i][j - 9] == 'w' && qr[i][j - 10] == 'w')
                        || (qr[i][j] == 'w' && qr[i][j - 1] == 'w' && qr[i][j - 2] == 'w' && qr[i][j - 3] == 'w' &&
                        qr[i][j - 4] == 'b' && qr[i][j - 5] == 'w' && qr[i][j - 6] == 'b' && qr[i][j - 7] == 'b' &&
                        qr[i][j - 8] == 'b' && qr[i][j - 9] == 'w' && qr[i][j - 10] == 'b'))
                    score += 40;
        }

        for(int i = 10; i < size; i++)
        {
            for(int j = 0; j < size; j++)
                if((qr[j][i] == 'b' && qr[j][i - 1] == 'w' && qr[j][i - 2] == 'b' && qr[j][i - 3] == 'b' &&
                        qr[j][i - 4] == 'b' && qr[j][i - 5] == 'w' && qr[j][i - 6] == 'b' && qr[j][i - 7] == 'w' &&
                        qr[j][i - 8] == 'w' && qr[j][i - 9] == 'w' && qr[j][i - 10] == 'w')
                        || (qr[j][i] == 'w' && qr[j][i - 1] == 'w' && qr[j][i - 2] == 'w' && qr[j][i - 3] == 'w'
                        && qr[j][i - 4] == 'b' && qr[j][i - 5] == 'w' && qr[j][i - 6] == 'b' && qr[j][i - 7] == 'b' &&
                        qr[j][i - 8] == 'b' && qr[j][i - 9] == 'w' && qr[j][i - 10] == 'b'))
                    score += 40;
        }

        // #4
        int darCounter = 0;
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                if(qr[i][j] == 'b')
                    darCounter++;

        darCounter = (int)(((double) darCounter / (size * size)) * 100);

        for(int i = 0; i < 20; i++)
        {
            if(darCounter >= i * 5 && darCounter <= (i + 1) * 5)
            {
                score += 10 * Math.min(Math.abs(i * 5 - 50)/5, Math.abs((i + 1) * 5 - 50)/5);
                break;
            }
        }

        return score;
    }


    public void draw()
    {
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                System.out.print(qr[i][j]);
            System.out.println();
        }
    }

    public char[][] getQr() {
        return qr;
    }
}
