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

import com.watchrabbit.commons.sleep.Sleep;
import com.watchrabbit.executor.exception.CircuitOpenException;
import com.watchrabbit.executor.spring.config.AnnotatedService;
import com.watchrabbit.executor.spring.config.ContextTestBase;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Mariusz
 */
public class AnnotationDiscoverTest extends ContextTestBase {

    @Autowired
    AnnotatedService annotatedService;

    @Test(expected = CircuitOpenException.class)
    public void shouldCallOnlyOnce() throws Exception {
        try {
            annotatedService.helloWorld(() -> {
                throw new Exception();
            });
            failBecauseExceptionWasNotThrown(Exception.class);
        } catch (Exception ex) {
        }
        annotatedService.helloWorld(() -> {
            throw new Exception();
        });
        failBecauseExceptionWasNotThrown(Exception.class);
    }

    @Test
    public void shouldCloseCircuit() throws Exception {
        try {
            annotatedService.fastClose(() -> {
                throw new Exception();
            });
            failBecauseExceptionWasNotThrown(Exception.class);
        } catch (Exception ex) {
        }
        Sleep.untilTrue(Boolean.TRUE::booleanValue, 10, TimeUnit.MILLISECONDS);

        CountDownLatch latch = new CountDownLatch(1);
        annotatedService.fastClose(() -> {
            latch.countDown();
            return "ok";
        });
        assertThat(latch.getCount()).isEqualTo(0);
    }
}
