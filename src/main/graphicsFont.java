package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class graphicsFont {
  static Color c = Color.WHITE;
  
  static byte[] letterA = new byte[] { 1, 2, 3, 7, 8, 9, 10, 14 };
  
  static byte[] letterB = new byte[] { 1, 2, 3, 6, 8, 10, 13, 15, 16 };
  
  static byte[] letterC = new byte[] { 1, 2, 3, 10, 15, 16 };
  
  static byte[] letterD = new byte[] { 1, 2, 5, 7, 12, 14, 15, 16 };
  
  static byte[] letterE = new byte[] { 1, 2, 3, 8, 10, 15, 16 };
  
  static byte[] letterF = new byte[] { 1, 2, 3, 8, 10 };
  
  static byte[] letterG = new byte[] { 1, 2, 3, 9, 10, 14, 15, 16 };
  
  static byte[] letterH = new byte[] { 3, 7, 8, 9, 10, 14 };
  
  static byte[] letterI = new byte[] { 1, 2, 5, 12, 15, 16 };
  
  static byte[] letterJ = new byte[] { 2, 7, 10, 14, 15, 16 };
  
  static byte[] letterK = new byte[] { 3, 6, 8, 10, 13 };
  
  static byte[] letterL = new byte[] { 3, 10, 15, 16 };
  
  static byte[] letterM = new byte[] { 3, 4, 6, 7, 10, 14 };
  
  static byte[] letterN = new byte[] { 3, 4, 7, 10, 13, 14 };
  
  static byte[] letterO = new byte[] { 1, 2, 3, 7, 10, 14, 15, 16 };
  
  static byte[] letterP = new byte[] { 1, 2, 3, 7, 8, 9, 10 };
  
  static byte[] letterQ = new byte[] { 1, 2, 3, 7, 10, 13, 14, 15, 16 };
  
  static byte[] letterR = new byte[] { 1, 2, 3, 7, 8, 9, 10, 13 };
  
  static byte[] letterS = new byte[] { 1, 2, 3, 8, 9, 14, 15, 16 };
  
  static byte[] letterT = new byte[] { 1, 2, 5, 12 };
  
  static byte[] letterU = new byte[] { 3, 7, 10, 14, 15, 16 };
  
  static byte[] letterV = new byte[] { 3, 6, 10, 11 };
  
  static byte[] letterW = new byte[] { 3, 7, 10, 11, 13, 14 };
  
  static byte[] letterX = new byte[] { 4, 6, 11, 13 };
  
  static byte[] letterY = new byte[] { 4, 6, 12 };
  
  static byte[] letterZ = new byte[] { 1, 2, 6, 11, 15, 16 };
  
  static byte[] numeral0 = new byte[] { 1, 2, 3, 6, 7, 10, 11, 14, 15, 16 };
  
  static byte[] numeral1 = new byte[] { 6, 7, 14 };
  
  static byte[] numeral2 = new byte[] { 1, 2, 7, 8, 9, 10, 15, 16 };
  
  static byte[] numeral3 = new byte[] { 1, 2, 7, 9, 14, 15, 16 };
  
  static byte[] numeral4 = new byte[] { 3, 7, 8, 9, 14 };
  
  static byte[] numeral5 = new byte[] { 1, 2, 3, 8, 13, 15, 16 };
  
  static byte[] numeral6 = new byte[] { 1, 2, 3, 8, 9, 10, 14, 15, 16 };
  
  static byte[] numeral7 = new byte[] { 1, 2, 6, 8, 9, 12 };
  
  static byte[] numeral8 = new byte[] { 1, 2, 3, 7, 8, 9, 10, 14, 15, 16 };
  
  static byte[] numeral9 = new byte[] { 1, 2, 3, 7, 8, 9, 14 };
  
  static byte[] space = new byte[0];
  
  public static void setColor(Color z) {
    c = z;
  }
  
  public static void drawString(Graphics g, String s, int x, int y, int size, int th) {
    char[] tempC = s.toCharArray();
    byte[] tmpL = null;
    g.setColor(c);
    for (int i = 0; i < tempC.length; i++) {
      switch (tempC[i]) {
        case 'a':
          tmpL = letterA;
          break;
        case 'b':
          tmpL = letterB;
          break;
        case 'c':
          tmpL = letterC;
          break;
        case 'd':
          tmpL = letterD;
          break;
        case 'e':
          tmpL = letterE;
          break;
        case 'f':
          tmpL = letterF;
          break;
        case 'g':
          tmpL = letterG;
          break;
        case 'h':
          tmpL = letterH;
          break;
        case 'i':
          tmpL = letterI;
          break;
        case 'j':
          tmpL = letterJ;
          break;
        case 'k':
          tmpL = letterK;
          break;
        case 'l':
          tmpL = letterL;
          break;
        case 'm':
          tmpL = letterM;
          break;
        case 'n':
          tmpL = letterN;
          break;
        case 'o':
          tmpL = letterO;
          break;
        case 'p':
          tmpL = letterP;
          break;
        case 'q':
          tmpL = letterQ;
          break;
        case 'r':
          tmpL = letterR;
          break;
        case 's':
          tmpL = letterS;
          break;
        case 't':
          tmpL = letterT;
          break;
        case 'u':
          tmpL = letterU;
          break;
        case 'v':
          tmpL = letterV;
          break;
        case 'w':
          tmpL = letterW;
          break;
        case 'x':
          tmpL = letterX;
          break;
        case 'y':
          tmpL = letterY;
          break;
        case 'z':
          tmpL = letterZ;
          break;
        case '0':
          tmpL = numeral0;
          break;
        case '1':
          tmpL = numeral1;
          break;
        case '2':
          tmpL = numeral2;
          break;
        case '3':
          tmpL = numeral3;
          break;
        case '4':
          tmpL = numeral4;
          break;
        case '5':
          tmpL = numeral5;
          break;
        case '6':
          tmpL = numeral6;
          break;
        case '7':
          tmpL = numeral7;
          break;
        case '8':
          tmpL = numeral8;
          break;
        case '9':
          tmpL = numeral9;
          break;
        case ' ':
          tmpL = space;
          break;
      } 
      drawLetter(g, tmpL, x + size * i * 3, y, size, th);
    } 
  }
  
  public static void drawLetter(Graphics g2, byte[] tmp, int x, int y, int size, int th) {
    Graphics2D g = (Graphics2D)g2;
    g.setStroke(new BasicStroke(th, 1, 1));
    byte b;
    int i;
    byte[] arrayOfByte;
    for (i = (arrayOfByte = tmp).length, b = 0; b < i; ) {
      byte a = arrayOfByte[b];
      switch (a) {
        case 1:
          g.drawLine(x, y, x + 1 * size, y);
          break;
        case 2:
          g.drawLine(x + 1 * size, y, x + 2 * size, y);
          break;
        case 3:
          g.drawLine(x, y, x, y + 2 * size);
          break;
        case 4:
          g.drawLine(x, y, x + 1 * size, y + 2 * size);
          break;
        case 5:
          g.drawLine(x + 1 * size, y, x + 1 * size, y + 2 * size);
          break;
        case 6:
          g.drawLine(x + 2 * size, y, x + 1 * size, y + 2 * size);
          break;
        case 7:
          g.drawLine(x + 2 * size, y, x + 2 * size, y + 2 * size);
          break;
        case 8:
          g.drawLine(x, y + 2 * size, x + 1 * size, y + 2 * size);
          break;
        case 9:
          g.drawLine(x + 1 * size, y + 2 * size, x + 2 * size, y + 2 * size);
          break;
        case 10:
          g.drawLine(x, y + 2 * size, x, y + 4 * size);
          break;
        case 11:
          g.drawLine(x + 1 * size, y + 2 * size, x, y + 4 * size);
          break;
        case 12:
          g.drawLine(x + 1 * size, y + 2 * size, x + 1 * size, y + 4 * size);
          break;
        case 13:
          g.drawLine(x + 1 * size, y + 2 * size, x + 2 * size, y + 4 * size);
          break;
        case 14:
          g.drawLine(x + 2 * size, y + 2 * size, x + 2 * size, y + 4 * size);
          break;
        case 15:
          g.drawLine(x, y + 4 * size, x + 1 * size, y + 4 * size);
          break;
        case 16:
          g.drawLine(x + 1 * size, y + 4 * size, x + 2 * size, y + 4 * size);
          break;
      } 
      b++;
    } 
  }
}
