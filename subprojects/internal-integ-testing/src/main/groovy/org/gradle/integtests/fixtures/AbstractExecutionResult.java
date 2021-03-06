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

package org.gradle.integtests.fixtures;

import static org.gradle.util.Matchers.containsLine;
import static org.gradle.util.Matchers.matchesRegexp;

public abstract class AbstractExecutionResult implements ExecutionResult {
    public void assertOutputHasNoStackTraces() {
        assertNoStackTraces(getOutput(), "Standard output");
    }

    public void assertErrorHasNoStackTraces() {
        assertNoStackTraces(getError(), "Standard error");
    }

    public void assertOutputHasNoDeprecationWarnings() {
        assertNoDeprecationWarnings(getOutput(), "Standard Output");
    }

    private void assertNoStackTraces(String output, String displayName) {
        if (containsLine(matchesRegexp("\\s+at [\\w.$_]+\\([\\w._]+:\\d+\\)")).matches(output)) {
            throw new RuntimeException(String.format("%s contains an unexpected stack trace:%n=====%n%s%n=====%n", displayName, output));
        }
    }

    private void assertNoDeprecationWarnings(String output, String displayName) {
        boolean javacWarning = containsLine(matchesRegexp(".*use(s)? or override(s)? a deprecated API\\.")).matches(output);
        boolean deprecationWarning = containsLine(matchesRegexp(".*deprecated.*")).matches(output);
        if (deprecationWarning && !javacWarning) {
            throw new RuntimeException(String.format("%s contains a deprecation warning:%n=====%n%s%n=====%n", displayName, output));
        }
    }
}
