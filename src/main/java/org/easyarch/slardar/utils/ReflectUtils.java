package org.easyarch.slardar.utils;

import org.objectweb.asm.*;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.*;
import java.lang.reflect.Type;

/**
 * Description :
 * Created by code4j on 16-11-3
 * 上午12:36
 */

public class ReflectUtils {

    public static Object newInstance(String classname) {
        try {
            Class clazz = Class.forName(classname);
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Object newInstance(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object newInstance(Class<?> clazz, Class[] parameterTypes, Object[] initargs) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor(parameterTypes);
            return constructor.newInstance(initargs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object newInstanceViolently(Class<?> clazz) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object newInstanceViolently(Class<?> clazz, Class[] parameterTypes, Object[] initargs) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor(parameterTypes);
            constructor.setAccessible(true);
            return constructor.newInstance(initargs);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    public static Object getFieldValue(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    public static void setFieldValue(Object obj, String propertyName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    public static Object getter(Object object, String propertyName) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(propertyName, object.getClass());
            Method method = pd.getReadMethod();
            if (method == null) {
                throw new IllegalArgumentException("method may not exists");
            }
            return method.invoke(object);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    public static void setter(Object object, String propertyName, Object value) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(propertyName, object.getClass());
            Method method = pd.getWriteMethod();
            if (method == null) {
                throw new IllegalArgumentException("method may not exists");
            }
            method.invoke(object, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置字段值
     *
     * @param className      类的全路径名称
     * @param methodName     调用方法名
     * @param parameterTypes 参数类型
     * @param values         参数值
     * @param object         实例对象
     * @return
     */
    public static Object methodInvoke(String className, String methodName, Class[] parameterTypes, Object[] values, Object object) {
        try {
            Method method = Class.forName(className).getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return method.invoke(object, values);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    public static PropertyDescriptor[] propertyDescriptors(Class<?> c) {
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(c);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }

        return beanInfo.getPropertyDescriptors();
    }

    public static<T> Class<T> getGenricType(T bean){
        return (Class<T>)getSuperClassGenricType(bean.getClass(), 0);
    }

    public static Class<Object> getSuperClassGenricType(final Class clazz, final int index) {
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        //返回表示此类型实际类型参数的 Type 对象的数组。
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class) params[index];
    }

    public static String[] getMethodParameter(final Method m) {
        final String[] paramNames = new String[m.getParameterTypes().length];
        final String n = m.getDeclaringClass().getName();
        final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassReader cr = null;
        try {
            cr = new ClassReader(n);
        } catch (IOException e) {
            e.printStackTrace();
        }
        cr.accept(new ClassVisitor(Opcodes.ASM4, cw) {
            @Override
            public MethodVisitor visitMethod(final int access,
                                             final String name, final String desc,
                                             final String signature, final String[] exceptions) {
                final org.objectweb.asm.Type[] args = org.objectweb.asm.Type.getArgumentTypes(desc);
                // 方法名相同并且参数个数相同
                if (!name.equals(m.getName())
                        || !sameType(args, m.getParameterTypes())) {
                    return super.visitMethod(access, name, desc, signature,
                            exceptions);
                }
                MethodVisitor v = cv.visitMethod(access, name, desc, signature,
                        exceptions);
                return new MethodVisitor(Opcodes.ASM4, v) {
                    @Override
                    public void visitLocalVariable(String name, String desc,
                                                   String signature, Label start, Label end, int index) {
                        int i = index - 1;
                        // 如果是静态方法，则第一就是参数
                        // 如果不是静态方法，则第一个是"this"，然后才是方法的参数
                        if(Modifier.isStatic(m.getModifiers())) {
                            i = index;
                        }
                        if (i >= 0 && i < paramNames.length) {
                            paramNames[i] = name;
                            System.out.println("paramNames:"+name);
                        }
                        super.visitLocalVariable(name, desc, signature, start,
                                end, index);
                    }

                };
            }
        }, 0);
        return paramNames;
    }

    private static boolean sameType(org.objectweb.asm.Type[] types, Class<?>[] clazzes) {
        // 个数不同
        if (types.length != clazzes.length) {
            return false;
        }

        for (int i = 0; i < types.length; i++) {
            if(!org.objectweb.asm.Type.getType(clazzes[i]).equals(types[i])) {
                return false;
            }
        }
        return true;
    }

    public static Class getReturnType(Method method){
        Type returnType = method.getGenericReturnType();// 返回类型
        return method.getReturnType();
    }
    public static Class getGenericReturnType(Method method){
        Type returnType = method.getGenericReturnType();// 返回类型
        if (returnType instanceof ParameterizedType)/**//* 如果是泛型类型 */{
            Type[] types = ((ParameterizedType) returnType)
                    .getActualTypeArguments();// 泛型类型列表
            for (Type type : types) {
                return (Class) type;
            }
        }
        return method.getReturnType();
    }

    public static boolean isBaseType(Class clz) {
        try {
            return (clz.isPrimitive())||((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 常用类型包括基本类型，包装类型和字符串类型
     * @param clz
     * @return
     */
    public static boolean isFrequentlyUseType(Class clz){
        return isBaseType(clz)||clz.equals(String.class);
    }

}
