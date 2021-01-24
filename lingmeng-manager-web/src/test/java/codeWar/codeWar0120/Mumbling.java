package codeWar.codeWar0120;

/**
 * @author skin
 * @createTime 2021年01月20日
 * @Description
 */
public class Mumbling {
    public static void main(String[] args) {
        // your code
        String s = "ZpglnRxqenU";
        String s1 = mySolution(s);
        String s2 = study(s);
        System.out.println(s2);

    }
    public static String mySolution(String s){
        StringBuilder sb = new StringBuilder();
        String middleLetter;
        int middleNum;
        for (int i = 1; i < s.length()+1; i++) {
            if (i == 1) {
                middleLetter = s.substring(0, i);
            } else {
                middleNum = i;
                middleLetter = s.substring(--middleNum, i);
            }
            for (int j = 0; j < i; j++) {
                if (j == 0) {
                    sb.append(middleLetter.toUpperCase());
                } else {
                    sb.append(middleLetter.toLowerCase());
                }
            }
            sb.append("-");

        }
        String result = sb.substring(0, sb.length() - 1);
        return result;
    }
    public static String study(String s){
        StringBuilder bldr = new StringBuilder();
        int i = 0;
        for(char c : s.toCharArray()) {
            if(i > 0) bldr.append('-');
            bldr.append(Character.toUpperCase(c));
            for(int j = 0; j < i; j++){
                bldr.append(Character.toLowerCase(c));
            }
            i++;
        }
        return bldr.toString();
    }
}
