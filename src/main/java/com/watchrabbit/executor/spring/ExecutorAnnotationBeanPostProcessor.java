/*
 * Copyright 2014 Mariusz.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.watchrabbit.executor.spring;

import static com.watchrabbit.executor.command.ExecutorCommand.executor;
import com.watchrabbit.executor.spring.annotaion.Executor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.core.Ordered;
import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

/**
 *
 * @author Mariusz
 */
public class ExecutorAnnotationBeanPostProcessor implements BeanPostProcessor, Ordered {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, String beanName) {
        Class<?> targetClass = AopUtils.getTargetClass(bean);

        Executor classAnnotation = findClassAnnotation(targetClass);

        Map<Method, Executor> annotatedMethods = findAnnotatedMethods(targetClass);

        if (!annotatedMethods.isEmpty()) {
            return createProxy(targetClass, classAnnotation, annotatedMethods, bean).create();
        }
        return bean;
    }

    private Enhancer createProxy(Class<?> targetClass, Executor classAnnotation, Map<Method, Executor> annotatedMethods, final Object bean) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback((InvocationHandler) (Object proxy, Method method, Object[] args) -> {
            if (annotatedMethods.containsKey(method) || classAnnotation != null) {
                Executor annotation = annotatedMethods.get(method);
                if (annotation != null) {
                    return generateExecutor(annotation, method, bean, args);
                } else {
                    return generateExecutor(classAnnotation, method, bean, args);
                }
            } else {
                return method.invoke(bean, args);
            }
        });
        return enhancer;
    }

    private Object generateExecutor(Executor annotation, Method method, final Object bean, Object[] args) throws Exception {
        return executor(annotation.circuitName())
                .withBreakerRetryTimeout(annotation.breakerRetryTimeout(), annotation.timeUnit())
                .invoke(() -> method.invoke(bean, args));
    }

    private Executor findClassAnnotation(Class<?> targetClass) throws IllegalArgumentException {
        return AnnotationUtils.getAnnotation(targetClass, Executor.class);
    }

    private Map<Method, Executor> findAnnotatedMethods(Class<?> targetClass) throws IllegalArgumentException {
        Map<Method, Executor> annotatedMethods = new HashMap<>();
        ReflectionUtils.doWithMethods(targetClass, (Method method) -> {
            Executor annotation = AnnotationUtils.getAnnotation(method, Executor.class);
            if (annotation != null) {
                annotatedMethods.put(method, annotation);
            }
        });
        return annotatedMethods;
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

}
