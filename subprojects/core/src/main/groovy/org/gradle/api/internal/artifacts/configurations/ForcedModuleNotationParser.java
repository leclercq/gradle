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

package org.gradle.api.internal.artifacts.configurations;

import org.gradle.api.IllegalDependencyNotation;
import org.gradle.api.artifacts.ModuleVersionSelector;
import org.gradle.api.internal.artifacts.DefaultModuleVersionSelector;
import org.gradle.api.internal.artifacts.dsl.dependencies.ParsedModuleStringNotation;
import org.gradle.api.internal.notations.NotationParserBuilder;
import org.gradle.api.internal.notations.api.InvalidNotationFormat;
import org.gradle.api.internal.notations.api.NotationParser;
import org.gradle.api.internal.notations.api.TopLevelNotationParser;
import org.gradle.api.internal.notations.parsers.TypedNotationParser;
import org.gradle.util.ConfigureUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 * by Szczepan Faber, created at: 10/11/11
 */
public class ForcedModuleNotationParser implements TopLevelNotationParser, NotationParser<Set<ModuleVersionSelector>> {

    private NotationParser<Set<ModuleVersionSelector>> delegate = new NotationParserBuilder()
            .resultingType(ModuleVersionSelector.class)
            .parser(new ForcedModuleStringParser())
            .parser(new ForcedModuleMapParser())
            .invalidNotationMessage(
                            "The forced module notation cannot be used to form the forced module.\n"
                            + "Forced module notation only supports following types/formats:\n"
                            + "  1. instances of ModuleIdentifier\n"
                            + "  2. Strings (actually CharSequences), e.g. 'org.gradle:gradle-core:1.0'\n"
                            + "  3. Maps, e.g. [group: 'org.gradle', name:'gradle-core', version: '1.0']\n"
                            + "  4. A Collection or array of above (nested collections/arrays will be flattened)\n"
            )
            .toFlatteningComposite();

    public Set<ModuleVersionSelector> parseNotation(Object notation) {
        assert notation != null : "notation cannot be null";
        return delegate.parseNotation(notation);
    }

    public boolean canParse(Object notation) {
        return delegate.canParse(notation);
    }

    static class ForcedModuleMapParser extends TypedNotationParser<Map, ModuleVersionSelector> {

        public ForcedModuleMapParser() {
            super(Map.class);
        }

        public ModuleVersionSelector parseType(Map notation) {
            ModuleVersionSelector out = selector(null, null, null);
            List<String> mandatoryKeys = asList("group", "name", "version");
            try {
                ConfigureUtil.configureByMap(notation, out, mandatoryKeys);
            } catch (ConfigureUtil.IncompleteInputException e) {
                throw new InvalidNotationFormat(
                          "Invalid format: " + notation + ". Missing mandatory key(s): " + e.getMissingKeys() + "\n"
                        + "The correct notation is a map with keys: " + mandatoryKeys + ", for example: [group: 'org.gradle', name:'gradle-core', version: '1.0']", e);
            }
            return out;
        }
    }

    static class ForcedModuleStringParser extends TypedNotationParser<CharSequence, ModuleVersionSelector> {

        public ForcedModuleStringParser() {
            super(CharSequence.class);
        }

        public ModuleVersionSelector parseType(CharSequence notation) {
            ParsedModuleStringNotation parsed;
            try {
                parsed = new ParsedModuleStringNotation(notation.toString(), null);
            } catch (IllegalDependencyNotation e) {
                throw new InvalidNotationFormat(
                    "Invalid format: '" + notation + "'. The Correct notation is a 3-part group:name:version notation,"
                    + "e.g: 'org.gradle:gradle-core:1.0'");
            }

            if (parsed.getGroup() == null || parsed.getName() == null || parsed.getVersion() == null) {
                throw new InvalidNotationFormat(
                    "Invalid format: '" + notation + "'. Group, name and version cannot be empty. Correct example: "
                    + "'org.gradle:gradle-core:1.0'");
            }
            return selector(parsed.getGroup(), parsed.getName(), parsed.getVersion());
        }
    }

    static ModuleVersionSelector selector(final String group, final String name, final String version) {
        return new DefaultModuleVersionSelector(group, name, version);
    }
}
