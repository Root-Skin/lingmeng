package codeWar01.codeWar0122.key5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author skin
 * @createTime 2021年01月22日
 * @Description
 * Once upon a time, on a way through the old wild mountainous west,…
 * … a man was given directions to go from one point to another. The directions were "NORTH", "SOUTH", "WEST", "EAST". Clearly "NORTH" and "SOUTH" are opposite, "WEST" and "EAST" too.
 *
 * Going to one direction and coming back the opposite direction right away is a needless effort. Since this is the wild west, with dreadfull weather and not much water, it's important to save yourself some energy, otherwise you might die of thirst!
 *
 * How I crossed a mountainous desert the smart way.
 * The directions given to the man are, for example, the following (depending on the language):
 */
public class DirectionsReduction {
    public static void main(String[] args) {

        String[] arr = new String[]{"EAST", "EAST", "WEST", "NORTH", "WEST", "EAST", "EAST", "SOUTH", "NORTH", "WEST"};

        System.out.println(Arrays.asList(dirReduc(arr)));

    }

    public static String[] dirReduc(String[] arr) {
        // Your code here.

        ArrayList<String> mylist = new ArrayList<String>();

        long count;
        Map<String, Long> collect = Arrays.stream(arr)
                .collect(Collectors.groupingBy(k -> k, Collectors.counting()));

        if(collect.get("NORTH")!=0 && collect.get("SOUTH") !=0 && collect.get("NORTH")>collect.get("SOUTH")){
            count= collect.get("NORTH")-collect.get("SOUTH");
            for(int i=0;i<count;i++){
                mylist.add("NORTH");
            }
        }else if(collect.get("NORTH")!=0 && collect.get("SOUTH") !=0 && collect.get("NORTH")<collect.get("SOUTH")){
            count = collect.get("SOUTH")-collect.get("NORTH");
            for(int i=0;i<count;i++){
                mylist.add("SOUTH");
            }
        }
        if(collect.get("EAST")!=0 && collect.get("WEST") !=0 && collect.get("EAST")>collect.get("WEST")){
            count= collect.get("EAST")-collect.get("WEST");
            for(int i=0;i<count;i++){
                mylist.add("EAST");
            }
        }else if(collect.get("EAST")!=0 && collect.get("WEST") !=0 &&  collect.get("EAST")<collect.get("WEST")){
            count = collect.get("WEST")-collect.get("EAST");
            for(int i=0;i<count;i++){
                mylist.add("WEST");
            }
        }

        System.out.println(mylist.size());
        if(mylist.size()>0){
            return new String[] { String.join(",",mylist)};
        }else{
            return new String[]{};
        }
    }
}
