package Design_Patterns.Strategy.version3;



/**
 * @author skin
 * @createTime 2021年03月21日
 * @Description Design_Patterns.Strategy.version3.CushNormal
 */
public class Test {
    public static void main(String[] args) {
        double price=100d;
        double count=3d;
        //计算总价
        Double money=price*count;

        //正常收费
        Class[] paramTypes = {};
        Object[] params = {};
        CushContext context=new CushContext("0",paramTypes,params);
        System.out.println(context.getResult(money));
        //打8折
        Class[] paramTypes2 = {String.class};
        Object[] params2 = {"0.8"};
        CushContext context2=new CushContext("1",paramTypes2,params2);
        System.out.println(context2.getResult(money));
        //打5折
        Class[] paramTypes3 = {String.class};
        Object[] params3 = {"0.5"};
        CushContext context3=new CushContext("1",paramTypes3,params3);
        System.out.println(context3.getResult(money));
        //满300减100
        Class[] paramTypes4 = {String.class,String.class};
        Object[] params4 = {"300","100"};
        CushContext context4=new CushContext("2",paramTypes4,params4);
        System.out.println(context4.getResult(money));
    }
}

