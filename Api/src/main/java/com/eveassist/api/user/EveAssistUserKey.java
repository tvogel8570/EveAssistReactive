/*
 * Copyright 2021 EveAssist Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.eveassist.api.user;

public interface EveAssistUserKey {
    String getUserUnique();

    static int compareTo(EveAssistUserKey o1, EveAssistUserKey o2) {
        return o1.getUserUnique().compareTo(o2.getUserUnique());
    }

    static int compareTo(EveAssistUserKey o1, String uniqueString) {
        return o1.getUserUnique().compareTo(uniqueString);
    }
}
