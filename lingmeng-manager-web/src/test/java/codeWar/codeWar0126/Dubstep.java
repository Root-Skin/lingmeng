package codeWar.codeWar0126;

/**
 * @author skin
 * @createTime 2021年01月27日
 * @Description
 */
public class Dubstep {
    public static void main(String[] args) {

        String song = "RWUBWUBWUBLWUB";
        System.out.println(SongDecoder(song));
    }
    public static String SongDecoder (String song)
    {
        // Your code is here...


            if(song.startsWith("RWU")){
                song = song.replaceFirst("RWU","");
            }else if(!song.startsWith("RWU")){
                song = song.replaceFirst("RWU"," ");
            }

        return SongDecoder(song);
    }
}
