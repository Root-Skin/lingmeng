package codeWar01.codeWar0124;

/**
 * @author skin
 * @createTime 2021年01月24日
 * @Description
 */
public class SumofDigitsDigitalRoot {
    public static void main(String[] args) {

        int n =132189    ;
        System.out.println(digital_root(n));
    }

    public static int digital_root(int n) {
        // ...
        int sum = 0;
        int length = String.valueOf(n).length();
        if(length>1){
             sum = String.valueOf(n).chars().map(i->i-'0').sum();
        }else{
            return n;
        }
        return digital_root(sum);
    }

    public static int study(int n) {
        // ...
//        return (n != 0 && n%9 == 0) ? 9 : n % 9;

        while(n > 9){
            n = n/10 + n % 10;
        }
        return(n);
    }
}
