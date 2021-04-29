package codeWar01.codeWar0121;

/**
 * @author skin
 * @createTime 2021年01月21日
 * @Description You like building blocks.
 * You especially like building blocks that are squares.
 * And what you even like more, is to arrange them into a square of square building blocks!
 * <p>
 * However, sometimes, you can't arrange them into a square.
 * Instead, you end up with an ordinary rectangle!
 * Those blasted things! If you just had a way to know,
 * whether you're currently working in vain… Wait!
 * That's it! You just have to check if your number of building blocks is a perfect square.
 */
public class YoureAsquare {
    public static void main(String[] args) {

        int n = 4;
        System.out.println(isSquare(n));

    }

    public static boolean isSquare(int n) {

        Double result = Math.sqrt(n);
        int b = result.intValue();
        if (b - result == 0) {
            return true;
        }
        return false;
    }

    public static boolean study(int n) {

        return Math.sqrt(n) % 1 == 0;
    }
}
