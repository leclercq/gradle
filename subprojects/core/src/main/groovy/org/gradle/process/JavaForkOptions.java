/*
 * Copyright 2010 the original author or authors.
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

package org.gradle.process;

import org.gradle.api.file.FileCollection;

import java.util.List;
import java.util.Map;

/**
 * <p>Specifies the options to use to fork a Java process.</p>
 */
public interface JavaForkOptions extends ProcessForkOptions {
    /**
     * Returns the system properties which will be used for the process.
     *
     * @return The system properties. Returns an empty map when there are no system properties.
     */
    Map<String, Object> getSystemProperties();

    /**
     * Sets the system properties to use for the process.
     *
     * @param properties The system properties. Must not be null.
     */
    void setSystemProperties(Map<String, ?> properties);

    /**
     * Adds some system properties to use for the process.
     *
     * @param properties The system properties. Must not be null.
     * @return this
     */
    JavaForkOptions systemProperties(Map<String, ?> properties);

    /**
     * Adds a system property to use for the process.
     *
     * @param name The name of the property
     * @param value The value for the property. May be null.
     * @return this
     */
    JavaForkOptions systemProperty(String name, Object value);

    /**
     * Returns the maximum heap size for the process, if any.
     *
     * @return The maximum heap size. Returns null if the default maximum heap size should be used.
     */
    String getMaxHeapSize();

    /**
     * Sets the maximum heap size for the process.
     *
     * @param heapSize The heap size. Use null for the default maximum heap size.
     */
    void setMaxHeapSize(String heapSize);

    /**
     * Returns the extra arguments to use to launch the JVM for the process. Does not include system properties and the
     * maximum heap size.
     *
     * @return The arguments. Returns an empty list if there are no arguments.
     */
    List<String> getJvmArgs();

    /**
     * Sets the extra arguments to use to launch the JVM for the process. System properties and maximum heap size are
     * updated.
     *
     * @param arguments The arguments. Must not be null.
     */
    void setJvmArgs(Iterable<?> arguments);

    /**
     * Adds some arguments to use to launch the JVM for the process.
     *
     * @param arguments The arguments. Must not be null.
     * @return this
     */
    JavaForkOptions jvmArgs(Iterable<?> arguments);

    /**
     * Adds some arguments to use to launch the JVM for the process.
     *
     * @param arguments The arguments.
     * @return this
     */
    JavaForkOptions jvmArgs(Object... arguments);

    /**
     * Returns the bootstrap classpath to use for the process. The default bootstrap classpath for the JVM is used when
     * this classpath is empty.
     *
     * @return The bootstrap classpath. Never returns null.
     */
    FileCollection getBootstrapClasspath();

    /**
     * Sets the bootstrap classpath to use for the process. Set to an empty classpath to use the default bootstrap
     * classpath for the specified JVM.
     *
     * @param classpath The classpath. Must not be null. Can be empty.
     */
    void setBootstrapClasspath(FileCollection classpath);

    /**
     * Adds the given values to the end of the bootstrap classpath for the process.
     *
     * @param classpath The classpath.
     * @return this
     */
    JavaForkOptions bootstrapClasspath(Object... classpath);

    /**
     * Returns true if assertions are enabled for the process.
     *
     * @return true if assertions are enabled, false if disabled
     */
    boolean getEnableAssertions();

    /**
     * Enable or disable assertions for the process.
     *
     * @param enabled true to enable assertions, false to disable.
     */
    void setEnableAssertions(boolean enabled);

    /**
     * Returns true if debugging is enabled for the process. When enabled, the process is started suspended and
     * listening on port 5005.
     *
     * @return true when debugging is enabled, false to disable.
     */
    boolean getDebug();

    /**
     * Enable or disable debugging for the process. When enabled, the process is started suspended and listening on port
     * 5005.
     *
     * @param enabled true to enable debugging, false to disable.
     */
    void setDebug(boolean enabled);

    /**
     * Returns the full set of arguments to use to launch the JVM for the process. This includes arguments to define
     * system properties, the maximum heap size, and the bootstrap classpath.
     *
     * @return The arguments. Returns an empty list if there are no arguments.
     */
    List<String> getAllJvmArgs();

    /**
     * Sets the full set of arguments to use to launch the JVM for the process. Overwrites any previously set system
     * properties, maximum heap size, assertions, and bootstrap classpath.
     *
     * @param arguments The arguments. Must not be null.
     */
    void setAllJvmArgs(Iterable<?> arguments);

    /**
     * Copies these options to the given options.
     *
     * @param options The target options.
     * @return this
     */
    JavaForkOptions copyTo(JavaForkOptions options);
}
