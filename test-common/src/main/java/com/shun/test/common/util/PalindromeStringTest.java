package com.shun.test.common.util;

import java.io.FileNotFoundException;

public class PalindromeStringTest {

    public static void main(String[] args) throws FileNotFoundException {

        String strdemo = "afdfdafabcabcbafdfdafxxxxxxxxyyyyxyyxxyyx";

        PalindromeStringTest PalindromeStringTest = new PalindromeStringTest();
        String longest = PalindromeStringTest.longestPalindrome(strdemo);
        String shortest = PalindromeStringTest.shortestPalindrome(strdemo);

        System.out.println("longest: " + longest + ",shortest: " +shortest);
    }

    public String longestPalindrome(String str) {
        // TODO Auto-generated method stub
        int max=0;
        int x=0;
        int y=0;


        int[] p=new int[str.length()];

        char[] charArray=new char[str.length()];

        for (int i = 0,m = 0; i < str.length(); i++) {
            if (Character.isLetter(str.charAt(i))) {
                p[m] = i;
                charArray[m++] = Character.toLowerCase(str.charAt(i));
            }
        }

        String strTemp = new String(charArray).trim();
        for (int i = 0; i < strTemp.length(); i++) {
            for (int j = i+1; j < strTemp.length(); j++) {
                StringBuilder builder = new StringBuilder(strTemp.substring(i, j));
                if (builder.toString().equals(builder.reverse().toString())) {
                    if (j-i > max) {
                        max = j - i;
                        x = i;
                        y = j;
                    }
                }
            }
        }
        return new StringBuilder(str.substring(p[x],p[y])).toString();
    }

    public String shortestPalindrome(String s) {
        String r = new StringBuilder(s).reverse().toString();
        String t = s + "#" + r;
        int[] next = new int[t.length()];
        for (int i = 1; i < t.length(); ++i) {
            int j = next[i - 1];
            while (j > 0 && t.charAt(i) != t.charAt(j)) j = next[j - 1];
            j += (t.charAt(i) == t.charAt(j)) ? 1 : 0;
            next[i] = j;
        }
        return r.substring(0, s.length() - next[t.length() - 1]) + s;
    }

}


