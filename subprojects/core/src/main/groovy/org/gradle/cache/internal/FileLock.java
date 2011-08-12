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
package org.gradle.cache.internal;

import java.io.File;

public interface FileLock {
    /**
     * Returns true if the most recent {@link #writeToFile(Runnable)} succeeded (ie a process did not crash while updating the target file)
     */
    boolean getUnlockedCleanly();

    /**
     * Returns true if the given file is used by this lock.
     */
    boolean isLockFile(File file);

    /**
     * Escalates this lock to an exclusive lock and runs the given action. If the given action fails, the lock is marked as uncleanly unlocked.
     */
    void writeToFile(Runnable action) throws LockTimeoutException;

    /**
     * Releases this lock.
     */
    void unlock();
}