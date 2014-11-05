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
package com.watchrabbit.executor.spring.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Annotation indicating that a method (or all the methods on a class) can be
 * wrapped by Rabbit Executor.
 *
 * @author Mariusz
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Executor {

    /**
     * Name of the circuit.
     * <p>
     * Used to determine remote system name.
     */
    String circuitName();

    /**
     * Breaker retry timeout.
     * <p>
     * After breakerRetryTimeout elapses circuit breaker will automatically
     * close connection.
     */
    long breakerRetryTimeout() default 100;

    /**
     * Exceptions that will not open circuit when thrown.
     *
     * @return
     */
    Class<? extends Exception>[] excludedExceptions() default {};

    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
