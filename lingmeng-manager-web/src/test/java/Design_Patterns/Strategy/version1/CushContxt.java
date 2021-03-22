package Design_Patterns.Strategy.version1;

/**
 * @author skin
 * @createTime 2021年03月21日
 * @Description  上下文 (维护一个对Strategy对象的引用)
 */
public class CushContxt {

    private CushSuper cushSuper;

    public CushContxt(CushSuper cushSuper) {
        this.cushSuper=cushSuper;
    }
    public double getResult(double money){
        return cushSuper.attachMoney(money);
    }
}
