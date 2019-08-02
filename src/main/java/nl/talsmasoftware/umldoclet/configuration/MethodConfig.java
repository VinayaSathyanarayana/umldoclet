/*
 * Copyright 2016-2019 Talsma ICT
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.talsmasoftware.umldoclet.configuration;

/**
 * Influences how Methods are rendered in the UML.
 *
 * @author Sjoerd Talsma
 */
public interface MethodConfig {
    enum ParamNames {
        NONE, BEFORE_TYPE, AFTER_TYPE
    }

    ParamNames paramNames();

    TypeDisplay paramTypes();

    TypeDisplay returnType();

    boolean include(Visibility methodVisibility);

    /**
     * @return Whether JavaBean properties ({@code getXyz(), isXyz(), setXyz(Xyz xyz)}) methods
     * should be rendered as Fields in UML
     */
    boolean javaBeanPropertiesAsFields();
}