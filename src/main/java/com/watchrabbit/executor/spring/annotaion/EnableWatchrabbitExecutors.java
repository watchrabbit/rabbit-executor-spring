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

import com.watchrabbit.executor.spring.WatchrabbitExecutorConfiguration;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * Enables support for handling components marked with Executor's
 * {@code @Executor} annotation. To be used on @{@code Configuration} classes as
 * follows:
 *
 * <pre class="code">
 * &#064;Configuration &#064;EnableWatchrabbitExecutors public class AppConfig {
 * }</pre>
 *
 * @author Mariusz
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(WatchrabbitExecutorConfiguration.class)
@Documented
public @interface EnableWatchrabbitExecutors {

}
