package Design_Patterns.Strategy.version3;

import Design_Patterns.Strategy.version1.CushSuper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author skin
 * @createTime 2021年03月21日
 * @Description  上下文 (维护一个对Strategy对象的引用)
 */
public class CushContext {

    private static final String[] CUSH_CLASS_NAME = {
            "Design_Patterns.Strategy.version3.CushNormal",
            "Design_Patterns.Strategy.version3.CushRebate",
            "Design_Patterns.Strategy.version3.CushReturn"
    };

    CushSuper cushSuper=null;

    public CushContext(String type,Class[] paramsType, Object[] parmas) {
        int type2=Integer.parseInt(type);
        try {
            //使用相对路径获取类对象
            Class<?> clazz=Class.forName(CUSH_CLASS_NAME[type2]);
            //获取构造函数
            Constructor<?> constructor=clazz.getConstructor(paramsType);
            //向上转型-->得到具体实例
            this.cushSuper=(CushSuper)constructor.newInstance(parmas);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public double getResult(double money){
        return cushSuper.attachMoney(money);
    }

}
