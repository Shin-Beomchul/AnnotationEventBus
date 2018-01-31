package magnet.house.godbeom.com.annotationbus.bus.impl;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import magnet.house.godbeom.com.annotationbus.bus.annotations.BrodCastEvent;
import magnet.house.godbeom.com.annotationbus.bus.annotations.TypeMagnetEvent;


/**
 * Created by BeomChul
 */
public class MagnetEvent {
    private static final Map<Object,  Method[]> TypeEventMethodCache = new ConcurrentHashMap<>();
    private static final Map<Object,  Method[]> brodCastMethodCache = new ConcurrentHashMap<>();
    private static final Map<Method, List<Type>> geneticTypeCache = new ConcurrentHashMap<>();
    private static final Map<Method, List<Class<?>>> parameterTypeCache = new ConcurrentHashMap<>();

    private ArrayList<Object> targetClassz = new ArrayList<>();

    private static MagnetEvent instance = null;
    private MagnetEvent() {};
    public static MagnetEvent getInstance() {
        if(instance == null) {
            synchronized (MagnetEvent.class) {
                if(instance == null) {
                    instance = new MagnetEvent();
                }
            }
        }
        return instance;
    }



    public synchronized void register(Object target) {
        targetClassz.add(target);
    }
    public synchronized void unregister(Object target) {
        removeCaches(target);
        targetClassz.remove(target);
    }


    /**
     *  <b>TypeEventListener<T>를 구현한 인터페이스에 @TypeEvent가 붙은 Method들에게 타입에 맞게 이벤트를 발송 합니다.</b><br>
     *  Methods that have @TypeEvent attached to the interface that implements TypeEventListener <T> are issued according to the type.<br><br>
     *
     *   <b>설명)</b><br>
     *   1).A 클래스에서 .postTypeChannel(MyEventDto)를 발행(이벤트 전송).<br>
     *    2).B 클래스에서 TypeEventListener<MyEventDto>인터페이스를 구현.(구독 대기)<br>
     *    3).C 클래스에서 TypeEventListener<OtherDto>인터페이스를 구현(구독 대기)<br>
     *    result). A클래스에게만 이벤트가 발송됩니다.<br><br>
     *
     *
     *    <b> Explanation</b><br>
     *    1).A class publishes .postTypeChannel (MyEventDto).<br>
     *    2).B class implementing TypeEventListener <MyEventDto> interface (wait for subscription)<br>
     *    3).Implement ClassEventListener <OtherDto> interface in class (wait for subscription)<br>
     *    result). Events will only be sent to class A.<br>
     *
     * */
    public synchronized void postTypeChannel(Object object) {
        for(Object RootClass : targetClassz) {
            Method[] methods = getTypeEventAnnotationMethod(RootClass);
            if(methods!=null) {
                for (Method method : methods) {
                    Type[] paramTypes = getGenericTypeParams(method);
                    for (Type paramT : paramTypes) {
                        if (object.getClass().equals(paramT)) {
                            try {
                                method.invoke(RootClass, object);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }


    /* *
     *   @BrodCastEvent Annotation이 붙은 모든 BrodCastListener 구현체에게 이벤트를 전송합니다.<br>
     *    !! 인터페이스를 구현하지 않아도  @BrodCastEvent만 붙어있으면 수신 할 수 있습니다.
     *      - 조건 : 단일 파라미터 이어야 하며,타입은 PacketBrodCast 이어야 합니다.
     *  Broadcast PacketBrodCast to all Methods with @ BrodCastEvent Annotation.
     *
     * */
    public synchronized void postBrodCast(BrodCastPacket brodCastPacket) {
        brodCastPacket.setSender(traceSender());
        for (Object RootClass : targetClassz) {
            Method[] methods = getBrodCastAnnotationMethod(RootClass);
            if(methods!=null) {
                for (Method method : methods) {
                    try {
                        method.invoke(RootClass, brodCastPacket);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }




    /**   해당 클래스에  @BrodCastEvent Annotation이 붙은 메소드를 캐싱하고 반환합니다.<br>
     *  <b>If the class has @BrodCastEvent Annotation, caching & return Methods </b><br><br>
     *
     *      존재이유 : 매 이벤트 전송마다 Reflection FullScanParse는 너무 비쌈.-> 캐싱<br>
     *     <b> Reason for existence :  It is too expensive to do Reflection FullScanParse every time we send an event.</b><br><br>
     *
     * <b> RootClass : activity , Fragment , POJO.. Every thing Object</b><br>
     *  <b>return    : RootClass in Methods with @BrodCastEvent</b>
     * */
    private  Method[] getBrodCastAnnotationMethod(Object RootClass){
        if(brodCastMethodCache.containsKey(RootClass)){
            return brodCastMethodCache.get(RootClass);
        }

        ArrayList<Method> targetMethods= new ArrayList<>();
        for( Method method : RootClass.getClass().getDeclaredMethods()){
            if(method.getAnnotation(BrodCastEvent.class)!=null){
                method.setAccessible(true);
                targetMethods.add(method);
            }
        }
        if(!targetMethods.isEmpty()){
            Method[] methods= targetMethods.toArray(new Method[targetMethods.size()]);
            brodCastMethodCache.put(RootClass,methods);
            return methods;

        }else{
            return null;
        }
    }

    /**
    *
    * @See getBrodCastAnnotationMethod()
    * */
    private  Method[] getTypeEventAnnotationMethod(Object RootClass){
        if(TypeEventMethodCache.containsKey(RootClass)){
            return TypeEventMethodCache.get(RootClass);
        }

        ArrayList<Method> targetMethods= new ArrayList<>();
        for( Method method : RootClass.getClass().getDeclaredMethods()){
            if(method.getAnnotation(TypeMagnetEvent.class)!=null){
                method.setAccessible(true);
                targetMethods.add(method);
            }
        }
        if(!targetMethods.isEmpty()){
            Method[] methods= targetMethods.toArray(new Method[targetMethods.size()]);
            TypeEventMethodCache.put(RootClass,methods);
            return methods;

        }else{
            return null;
        }
    }

      /*
       *  Method genericParams  caching & return Type
       *
       * */

    private Type[] getGenericTypeParams(Method method) {

        if (geneticTypeCache.containsKey(method)) {
            List<Type> genTypes = geneticTypeCache.get(method);
            return genTypes.toArray(new Type[genTypes.size()]);
        }
        List<Type> types = new ArrayList<>();
        Collections.addAll(types, method.getParameterTypes());
        types = Collections.unmodifiableList(types);
        geneticTypeCache.put(method, types);
        return types.toArray(new Type[types.size()]);
    }

    /*
       *  Method ordnaly Params  caching & return Type
       *
       * */

    static List<Class<?>> getParameterTypes(Method method) {
        if (parameterTypeCache.containsKey(method)) {
            return parameterTypeCache.get(method);
        }
        List<Class<?>> types = new ArrayList<>();
        Collections.addAll(types, method.getParameterTypes());
        types = Collections.unmodifiableList(types);
        parameterTypeCache.put(method, types);
        return types;
    }

    private String traceSender(){
        int BackTrackingDeeps = 2;
        Throwable t = new Throwable();
        StackTraceElement[] elements = t.getStackTrace();
      return elements[BackTrackingDeeps].getClassName();

    }

    private void removeCaches(Object target){


        if(TypeEventMethodCache.containsKey(target)){
            TypeEventMethodCache.remove(target);
        }
        if(brodCastMethodCache.containsKey(target)){
            brodCastMethodCache.remove(target);
        }
        if(geneticTypeCache.containsKey(target)){
            geneticTypeCache.remove(target);
        }
        if(parameterTypeCache.containsKey(target)){
            parameterTypeCache.remove(target);
        }



    }

    public void scan() {
        for (Object Root : targetClassz) {

            Log.e("Reflection", "[ " + Root.getClass().getSimpleName() + " ]");

            Method[] methods = Root.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.getAnnotation(TypeMagnetEvent.class) != null) {
                    method.setAccessible(true);
                    Log.d("reflectin", "-----ㄴ@Catch Method " + method.getName());


                    List<Class<?>> params = getParameterTypes(method);
                    for (Class<?> paramType : params) {
                        Log.d("Reflection", "-----------ㄴparamType :: " + paramType.getSimpleName());
                    }

                    Type[] gemericParams = method.getGenericParameterTypes();
                    for (Type genParamType : gemericParams) {
                        if (genParamType.equals(BrodCastEvent.class)) ;
                        Log.d("Reflection", "-----------ㄴGemParamCatch :: " + genParamType);
                    }
                }
            }


        }
    }
}

