package Design_Patterns.Strategy.version1;

/**
 * @author skin
 * @createTime 2021年03月21日
 * @Description 正常收费
 */
public class CushNormal extends CushSuper {

    @Override
    public double attachMoney(double money) {
        return money;
    }
}
