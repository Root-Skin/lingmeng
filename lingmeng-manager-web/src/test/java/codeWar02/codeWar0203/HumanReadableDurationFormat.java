package codeWar02.codeWar0203;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author skin
 * @createTime 2021年02月03日
 * @Description Your task in order to complete this Kata is to write a function which formats a duration,
 * given as a number of seconds, in a human-friendly way.
 * <p>
 * The function must accept a non-negative integer. If it is zero,
 * it just returns "now". Otherwise, the duration is expressed as
 * a combination of years, days, hours, minutes and seconds.
 */
public class HumanReadableDurationFormat {
    public static void main(String[] args) {

        int seconds = 446456;
        System.out.println(formatDuration(seconds));
    }

    public static String formatDuration(int seconds) {
        // your code goes here

        if(seconds==0){
            return "now";
        }
        StringBuilder result = new StringBuilder();
        int year = (seconds / (3600 * 24 * 365));
        int day = (seconds / (3600 * 24)) % 365;
        int hour = (seconds / 3600) % 24;
        int min = (seconds / 60) % 60;
        int sec = seconds % 60;

        if (year != 0 && year == 1) {
            result.append(year + " " + "year,");
        } else if (year != 0 && year > 1) {
            result.append(year + " " + "years,");
        }
        if (day != 0 && day == 1) {
            result.append(day + " " + "day,");
        } else if (day != 0 && day > 1) {
            result.append(day + " " + "days,");
        }
        if (hour != 0 && hour == 1) {
            result.append(hour + " " + "hour,");
        } else if (hour != 0 && hour > 1) {
            result.append(hour + " " + "hours,");
        }
        if (min != 0 && min == 1) {
            result.append(min + " " + "minute,");
        } else if (min != 0 && min > 1) {
            result.append(min + " " + "minutes,");
        }
        if (sec != 0 && sec == 1) {
            result.append(sec + " " + "second,");
        } else if (sec != 0 && sec > 1) {
            result.append(sec + " " + "seconds,");
        }
        String temp = result.toString();
        int arrayLength = result.toString().split(",").length;
        String result2 = "";
        if (arrayLength == 1) {
            result2 = temp.replaceAll(",", "");
        } else if (arrayLength == 2) {
            String temp2 = temp.replaceFirst(",", " and ");
            result2 = temp2.replaceAll(",", "");
        } else if (arrayLength >= 3) {

            //最多切割三次
            for (int i=0;i<=arrayLength-3;i++) {
                String substring = temp.substring(0, temp.indexOf(",") + 1);
                String remain = temp.substring(temp.indexOf(",") + 1, temp.length());
                String temp1 = substring.replaceFirst(",", ", ");
                result2 += temp1;
                temp = remain;
            }

            String substring1 = temp.substring(0, temp.indexOf(","));
            String substring2 = temp.substring(temp.indexOf(","), temp.length());
            String temp2 = substring2.replaceFirst(",", " and ");
            result2 = result2 + substring1 + temp2;
            result2 = result2.substring(0, result2.length() - 1);
        }
        return result2;
    }


    public static String study(int seconds) {
        return seconds == 0 ? "now" :
                Arrays.stream(
                        new String[]{
                                formatTime("year",  (seconds / 31536000)),
                                formatTime("day",   (seconds / 86400)%365),
                                formatTime("hour",  (seconds / 3600)%24),
                                formatTime("minute",(seconds / 60)%60),
                                formatTime("second",(seconds%3600)%60)})
                        .filter(e->e!="")
                        .collect(Collectors.joining(", "))
                        .replaceAll(", (?!.+,)", " and ");  //负向预查(后面不存在,的)
    }
    public static String formatTime(String s, int time){
        return time==0 ? "" : Integer.toString(time)+ " " + s + (time==1?"" : "s");
    }
}
