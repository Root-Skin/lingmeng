package Design_Patterns.Strategy.version1;

/**
 * @author skin
 * @createTime 2021年03月21日
 * @Description 满减收费
 */
public class CushReturn extends CushSuper {

    private double cushContidion=0d;
    private double cushReturn=0;

    public CushReturn(String cushContidion,String cushReturn) {
        this.cushContidion=Double.parseDouble(cushContidion);
        this.cushReturn=Double.parseDouble(cushReturn);
    }

    @Override
    public double attachMoney(double money) {
        double result=money-Math.floor(money/cushContidion)*cushReturn;
        return result;
    }
}
