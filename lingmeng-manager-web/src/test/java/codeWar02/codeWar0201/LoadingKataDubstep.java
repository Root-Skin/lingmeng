package codeWar02.codeWar0201;

/**
 * @author skin
 * @createTime 2021年02月01日
 * @Description
 */
public class LoadingKataDubstep {
    public static void main(String[] args) {

        String song ="RWUBWUBWUBLWUB";
        System.out.println(SongDecoder(song));
    }
    public static String SongDecoder (String song)
    {
        // Your code is here...
        String result = song.replace("WUB", " ");
        String trim = result.trim();
        String s = trim.replaceAll("\\s+", " ");
        return s;
    }

    public static String study (String song)
    {
        return song.replaceAll("(WUB)+", " ").trim();
    }
}
