[![Maven central][maven img]][maven]
[![][travis img]][travis]
[![][coverage img]][coverage]


Watchrabbit - Executor - Spring
===============================

Executor - Spring is convinient wrapper of [Executor](https://github.com/watchrabbit/rabbit-executor) library that follows the Spring convention. Easy to configure and use offers basic features  of [Executor](https://github.com/watchrabbit/rabbit-executor) in annotations based style.

Configuration
-------------


Usage
-----
To enable executor around some function in spring bean use `@Executor` annotation.

```java
@Service
public class BarService {

    @Executor(circuitName = "foo-system")
    public void bar() {
        // do something in foo-system
        ...
    }
}
```

If circuit opens executor woudl throw `CircuitOpenException` instead of invoking function.

[coverage]:https://coveralls.io/r/watchrabbit/rabbit-executor-spring
[coverage img]:https://img.shields.io/coveralls/watchrabbit/rabbit-executor-spring.png
[travis]:https://travis-ci.org/watchrabbit/rabbit-executor-spring
[travis img]:https://travis-ci.org/watchrabbit/rabbit-executor-spring.svg?branch=master
[maven]:https://maven-badges.herokuapp.com/maven-central/com.watchrabbit/rabbit-executor-spring
[maven img]:https://maven-badges.herokuapp.com/maven-central/com.watchrabbit/rabbit-executor-spring/badge.svg
