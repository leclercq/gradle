/*
 * Copyright 2011 the original author or authors.
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
package org.gradle.launcher.daemon.context;

import java.io.Serializable;
import java.io.File;

/**
 * A value object that describes a daemons environment/context.
 * <p>
 * This is used by clients to determine whether or not a daemon meets its requirements
 * such as JDK version, special system properties etc.
 * <p>
 * Instances must be serialisable because they are shared via the DaemonRegistry, which is permitted
 * to use serialisation to communicate across VM boundaries. Implementations are not required to be,
 * but should also be immutable.
 *
 * @see DaemonContextBuilder
 * @see DaemonCompatibilitySpecFactory
 */
public interface DaemonContext extends Serializable {

    /**
     * The JAVA_HOME in use, as the canonical file.
     */
    File getJavaHome();

    /**
     * The user home dir that Gradle should use.
     */
    File getUserHomeDir();

    /**
     * The process id of the daemon.
     */
    Long getPid();

    /**
     * The daemon's idle timeout in milliseconds.
     */
    Integer getIdleTimeout();
}