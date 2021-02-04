package codeWar02.codeWar0202;

/**
 * @author skin
 * @createTime 2021年02月02日
 * @Description Write a function,
 * which takes a non-negative integer (seconds) as input and returns the time in a human-readable format (HH:MM:SS)
 * <p>
 * HH = hours, padded to 2 digits, range: 00 - 99
 * MM = minutes, padded to 2 digits, range: 00 - 59
 * SS = seconds, padded to 2 digits, range: 00 - 59
 * The maximum time never exceeds 359999 (99:59:59)
 * <p>
 * You can find some examples in the test fixtures.
 */
public class HumanReadableTime {
    public static void main(String[] args) {
        System.out.println(makeReadable(359999));

    }

    public static String makeReadable(int seconds) {
        // Do something

        String hourString = "";
        String minString = "";
        String secString = "";

        //获取小时
        int hour = seconds / 3600;
        //分钟
        int min = (seconds - (hour * 3600)) / 60;
        //获取秒
        int sec = seconds - (hour * 3600) - (min * 60);

        if (hour == 0) {
            hourString = "00";
        } else if (hour > 0 && hour < 10) {
            hourString = "0" + hour;
        } else if (hour > 10) {
            hourString = "" + hour;
        }

        if (min == 0) {
            minString = "00";
        } else if (min > 0 && min < 10) {
            minString = "0" + min;
        } else if (min > 10) {
            minString = "" + min;
        }
        if (sec == 0) {
            secString = "00";
        } else if (sec > 0 && sec < 10) {
            secString = "0" + sec;
        } else if (sec > 10) {
            secString = "" + sec;
        }
        String result = String.format("%s:%s:%s", hourString, minString, secString);
        return result;
    }

    public static String study(int seconds) {
        //格式化为至少两位
        return String.format("%02d:%02d:%02d", seconds / 3600, (seconds / 60) % 60, seconds % 60);
    }
}