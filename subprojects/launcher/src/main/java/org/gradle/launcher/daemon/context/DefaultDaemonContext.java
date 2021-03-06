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

import java.io.File;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Keep in mind that this is a serialised value object.
 *
 * @see DaemonContextBuilder
 */
public class DefaultDaemonContext implements DaemonContext {

    private final File javaHome;
    private final File userHomeDir;
    private final Long pid;
    private final Integer idleTimeout;

    public DefaultDaemonContext(File javaHome, File userHomeDir, Long pid, Integer idleTimeout) {
        this.javaHome = javaHome;
        this.userHomeDir = userHomeDir;
        this.pid = pid;
        this.idleTimeout = idleTimeout;
    }

    public static DaemonContext parseFrom(String source) {
        Pattern pattern = Pattern.compile("^.*DefaultDaemonContext\\[javaHome=([^\\n]+),userHomeDir=([^\\n]+),pid=([^\\n]+),idleTimeout=(.+?)].*", Pattern.MULTILINE + Pattern.DOTALL);
        Matcher matcher = pattern.matcher(source);

        if (matcher.matches()) {
            String javaHome = matcher.group(1);
            String userHomeDir = matcher.group(2);
            Long pid = Long.parseLong(matcher.group(3));
            Integer idleTimeout = Integer.decode(matcher.group(4));
            return new DefaultDaemonContext(new File(javaHome), new File(userHomeDir), pid, idleTimeout);
        } else {
            throw new IllegalStateException("unable to parse DefaultDaemonContext from source: " + source);
        }
    }

    public String toString() {
        return String.format("DefaultDaemonContext[javaHome=%s,userHomeDir=%s,pid=%s,idleTimeout=%s]", javaHome, userHomeDir, pid, idleTimeout);
    }

    public File getJavaHome() {
        return javaHome;
    }

    public File getUserHomeDir() {
        return userHomeDir;
    }

    public Long getPid() {
        return pid;
    }

    public Integer getIdleTimeout() {
        return idleTimeout;
    }
}