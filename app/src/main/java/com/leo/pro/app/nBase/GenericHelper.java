/*
 * Copyright 2016 XuJiaji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.leo.pro.app.nBase;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Created by qibin on 2015/11/15.
 */
public class GenericHelper {

    public static <T> Class<T> getGenericClass(Class<?> klass, Class<?> filterClass) {
        Type type = klass.getGenericSuperclass();
        if(type == null || !(type instanceof ParameterizedType)) return null;
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] types = parameterizedType.getActualTypeArguments();
        for (Type t : types) {
            if (isPresenter(t, filterClass)) {
                return (Class<T>) t;
            }
        }

        return null;
//        if(types == icon_0 || types.length == 0) return icon_0;
//        return (Class<T>) types[0];
    }

    private static boolean isPresenter(Type t, Class<?> filterClass) {
        Class<?> aClass = (Class<?>) t;
        Class<?>[] classes = aClass.getInterfaces();
        for (Class<?> c : classes) {
            return c == filterClass || isPresenter(c, filterClass);
        }
        return false;
    }

    public static  <T> T newPresenter(Object obj) {
        if (!NContract.View.class.isInstance(obj)) {
            throw new RuntimeException("no implement NContract.View");
        }
        try {
            Class<?> currentClass = obj.getClass();
//            Class<?> viewClass = getViewInterface(currentClass);[
            Class<?> presenterClass = getGenericClass(currentClass, NContract.Presenter.class);
            Class<?> modelClass = getGenericClass(presenterClass, NContract.Model.class);
//            Constructor construct = presenterClass.getConstructor(viewClass, modelClass);
            NPresenter<?,?>  nPresenter= (NPresenter<?, ?>) presenterClass.newInstance();
            nPresenter.init(obj, modelClass.newInstance());
            return (T) nPresenter;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("instance of mPresenter fail\n" +
                " Remind mPresenter need to extends XBasePresenter");
    }


//    public static Class<?> getViewInterface(Class currentClass) {
//        Class<?>[] classes = currentClass.getInterfaces();
//        for (Class<?> c : classes) {
//            if (c != XContract.View.class) {
//                if (getViewInterface(c) == XContract.View.class) {
//                    return c;
//                }
//            }
//            return c;
//        }
//        throw new RuntimeException("no implement XContract.BaseView");
//    }
}