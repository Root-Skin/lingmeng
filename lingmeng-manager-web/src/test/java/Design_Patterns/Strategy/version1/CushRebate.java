package Design_Patterns.Strategy.version1;

/**
 * @author skin
 * @createTime 2021年03月21日
 * @Description 打折收费
 */
public class CushRebate extends CushSuper {

    private double cushRebate=1d;

    public CushRebate(String moneyRebate) {
        this.cushRebate=Double.parseDouble(moneyRebate);
    }


    @Override
    public double attachMoney(double money) {
        double result=money*cushRebate;
        return result;
    }
}
