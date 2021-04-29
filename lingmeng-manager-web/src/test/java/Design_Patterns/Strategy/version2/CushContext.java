package Design_Patterns.Strategy.version2;

import Design_Patterns.Strategy.version1.CushNormal;
import Design_Patterns.Strategy.version1.CushRebate;
import Design_Patterns.Strategy.version1.CushReturn;
import Design_Patterns.Strategy.version1.CushSuper;

/**
 * @author skin
 * @createTime 2021年03月21日
 * @Description  上下文 (维护一个对Strategy对象的引用)
 */
public class CushContext  {

    CushSuper cushSuper=null;
    
    public CushContext(String type) {
        switch (type) {
            case "正常收费":
                cushSuper=new CushNormal();
                break;
            case "打8折":
                cushSuper=new CushRebate("0.8");
                break;
            case "打5折":
                cushSuper=new CushRebate("0.5");
                break;
            case "满300减100":
                cushSuper=new CushReturn("300","100");
                break;
        }
    }
    public double getResult(double money){
        return cushSuper.attachMoney(money);
    }
}
