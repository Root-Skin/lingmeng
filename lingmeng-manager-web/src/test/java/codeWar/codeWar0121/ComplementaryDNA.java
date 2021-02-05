package codeWar01.codeWar0121;

import java.util.HashMap;
import java.util.Map;

/**
 * @author skin
 * @createTime 2021年01月21日
 * @Description Deoxyribonucleic acid (DNA) is a chemical found in the nucleus of cells and carries the "instructions"
 * for the development and functioning of living organisms.
 * In DNA strings, symbols "A" and "T" are complements of each other, as "C" and "G".
 * You have function with one side of the DNA (string, except for Haskell);
 * you need to get the other complementary side.
 * DNA strand is never empty or there is no DNA at all (again, except for Haskell).
 */
public class ComplementaryDNA {
    public static void main(String[] args) {

        String dna = "ATTGC";
        System.out.println(makeComplement(dna));

    }

    public static String makeComplement(String dna) {
        StringBuilder result = new StringBuilder();
        Map match = new HashMap();
        match.put("A", "T");
        match.put("T", "A");
        match.put("C", "G");
        match.put("G", "C");
        //Your code
        dna.chars().forEach(i -> {
            String replace = String.valueOf((char) i).replace(String.valueOf((char) i), String.valueOf(match.get(String.valueOf((char) i))));
            result.append(replace);
        });
        return result.toString();
    }

    public static String study(String dna) {

//        char[] chars = dna.toCharArray();
//        for (int i = 0; i < chars.length; i++) {
//            chars[i] = getComplement(chars[i]);
//        }
//
//        return new String(chars);
//         }
//
//    private static char getComplement(char c) {
//        switch (c) {
//            case 'A':
//                return 'T';
//            case 'T':
//                return 'A';
//            case 'C':
//                return 'G';
//            case 'G':
//                return 'C';
//        }
//        return c;
        dna = dna.replaceAll("A","Z");
        dna = dna.replaceAll("T","A");
        dna = dna.replaceAll("Z","T");
        dna = dna.replaceAll("C","Z");
        dna = dna.replaceAll("G","C");
        dna = dna.replaceAll("Z","G");
        return dna;
    }


}

