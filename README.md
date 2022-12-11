# native-demo2

Small demo project to try out how to best use Native Images with Spring Boot 3.

## About

This project demonstrates:

* **Kotlin 1.7:** JetBrain's Java Successor :^)
* **Project Reactor:** Reactive Java
* **H2 Database:** In-Memory & File-based RDBMS
* **Spring WebFlux:** Reactive Web Support
* **Spring Data JPA:** Hibernate 6 & Jakarta JPA
* **Spring Validation:** jakarta.validation Provider
* **Spring Batch:** Batch Processing
* **SpringDoc:** OpenAPI & Swagger UI Integration

Please also have a look at `resources/application.properties` for detailed configuration.

## Getting started

This project has been configured to let you generate either a lightweight container or a native executable.
It is also possible to run your tests in a native image.

### Installing GraalVM on macOS

After downloading [GraalVM](https://www.graalvm.org/22.0/docs/getting-started/macos/) and unzipping it to 
`/Library/Java/JavaVirtualMachines/`, it quickly becomes a hassle to manage either running OpenJDK or GraalVM.

If you don't want to replace OpenJDK just yet, here's a small aid to (un)configure it as needed:

```shell
function graal {
    local GRAAL_PATH="$(/usr/libexec/java_home -V 2>&1 | grep -i graalvm | head -n1 | sed 's,[^/]*,,')"
    export JAVA_HOME="$GRAAL_PATH"
    if ! command -v gu >/dev/null; then
        export PATH="${GRAAL_PATH}/bin:$PATH"
    fi
    echo "Graal away :^)"
}

function nograal {
    local GRAAL_PATH="$(/usr/libexec/java_home -V 2>&1 | grep -i graalvm | head -n1 | sed 's,[^/]*,,')"
    export JAVA_HOME="$(/usr/libexec/java_home -v 17)"
    export PATH="$(echo -n $PATH | sed s,${GRAAL_PATH}/bin:,,)"
    echo "Back to OpenJDK :^("
}
```

Once this is in your `.zshrc` (or similar), you can just run `graal` before any of the native-image stuff.

This process can easily be adopted on Linux, probably less so on Windows. Sorry!

#### Lightweight Container with Cloud Native Buildpacks

If you're already familiar with Spring Boot container images support, this is the easiest way to get started.
Docker should be installed and configured on your machine prior to creating the image.

To create the image, run the following goal:

```
$ ./mvnw spring-boot:build-image -Pnative
```

Then, you can run the app like any other container:

```
$ docker run --rm native-demo2:0.0.1-SNAPSHOT
```

#### Executable with Native Build Tools

Use this option if you want to explore more options such as running your tests in a native image.
The GraalVM `native-image` compiler should be installed and configured on your machine.

NOTE: GraalVM 22.3+ is required.

To create the executable, run the following goal:

```
$ ./mvnw native:compile -Pnative
```

Then, you can run the app as follows:

```
$ target/native-demo2
```

You can also run your existing tests suite in a native image.
This is an efficient way to validate the compatibility of your application.

To run your existing tests in a native image, run the following goal:

```
$ ./mvnw test -PnativeTest
```

## Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.0/maven-plugin/reference/html/#build-image)
* [GraalVM Native Image Support](https://docs.spring.io/spring-boot/docs/3.0.0/reference/html/native-image.html#native-image)

### Additional Links

These additional references should also help you:

* [Configure AOT settings in Build Plugin](https://docs.spring.io/spring-boot/docs/3.0.0/maven-plugin/reference/htmlsingle/#aot)

