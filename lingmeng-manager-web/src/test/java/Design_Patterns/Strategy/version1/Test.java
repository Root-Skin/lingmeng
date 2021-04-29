package Design_Patterns.Strategy.version1;

/**
 * @author skin
 * @createTime 2021年03月21日
 * @Description
 * 优点：1、算法可以自由切换。 2、避免使用多重条件判断。 3、扩展性好。
 * 缺点：1、客户端必须知道所有的策略类，并自己决定由那个策略类。   2、策略类会很多。
 */
public class Test {
    public static void main(String[] args) {
        double price = 10d;
        double count = 3d;

        //计算总价
        Double money = price * count;
        //获取输入框中的类型
//		String type="正常收费";
        String type = "打8折";
//		String type="打5折";
//		String type="满300减100";
        CushContxt content = null;
        switch (type) {
            case "正常收费":
                content = new CushContxt(new CushNormal());
                break;
            case "打8折":
                content = new CushContxt(new CushRebate("0.8"));
                break;
            case "打5折":
                content = new CushContxt(new CushRebate("0.5"));
                break;
            case "满300减100":
                content = new CushContxt(new CushReturn("300", "100"));
                break;
        }
        if (content != null) {
            System.out.println(content.getResult(money));
        }
    }
}

