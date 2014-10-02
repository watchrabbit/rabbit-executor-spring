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
package com.watchrabbit.executor.spring.config;

import com.watchrabbit.executor.spring.annotaion.Executor;
import java.util.concurrent.Callable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mariusz
 */
@Service
public class AnnotatedService {

    @Executor(circuitName = "helloWorld")
    public String helloWorld(Callable<String> callable) throws Exception {
        return callable.call();
    }

    @Executor(circuitName = "fastClose", breakerRetryTimeout = 1)
    public String fastClose(Callable<String> callable) throws Exception {
        return callable.call();
    }
}
