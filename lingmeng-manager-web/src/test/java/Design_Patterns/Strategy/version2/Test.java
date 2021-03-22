package Design_Patterns.Strategy.version2;

/**
 * @author skin
 * @createTime 2021年03月21日
 * @Description
 * 优点：解决了需要在客户端判断使用那个实现的缺点；
 *
 * 缺点：在cashContext中用到了swith,也就是说，
 * 如果我们需要增加一种算法，比如"满200减50"，
 * 就必须更改cashContext中的switch代码。
 */
public class Test {
    public static void main(String[] args) {
        double price=10d;
        double count=3d;

        //计算总价
        Double money=price*count;
        //获取输入框中的类型
        String type1="正常收费";
        String type2="打8折";
        String type3="打5折";
        String type4="满300减100";
        CushContext content=new CushContext(type1);
        //正常收费
        System.out.println(content.getResult(money));
        //打8折
        CushContext content2=new CushContext(type2);
        System.out.println(content2.getResult(money));
        //打5折
        CushContext content3=new CushContext(type3);
        System.out.println(content3.getResult(money));
        //满300减100
        CushContext content4=new CushContext(type4);
        System.out.println(content4.getResult(money));

    }
}

