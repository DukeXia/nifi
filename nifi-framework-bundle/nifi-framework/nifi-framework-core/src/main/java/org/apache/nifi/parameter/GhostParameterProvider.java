/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.nifi.parameter;

import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.components.ValidationContext;
import org.apache.nifi.components.ValidationResult;
import org.apache.nifi.controller.ConfigurationContext;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.reporting.InitializationException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GhostParameterProvider implements ParameterProvider {

    private String id;
    private String canonicalClassName;

    public void setIdentifier(final String id) {
        this.id = id;
    }

    public void setCanonicalClassName(final String canonicalClassName) {
        this.canonicalClassName = canonicalClassName;
    }

    @Override
    public Collection<ValidationResult> validate(final ValidationContext context) {
        return Collections.singleton(new ValidationResult.Builder()
            .input("Any Property")
            .subject("Missing Parameter Provider")
            .valid(false)
            .explanation("Parameter Provider is of type " + canonicalClassName + ", but this is not a valid Parameter Provider type")
            .build());
    }

    @Override
    public PropertyDescriptor getPropertyDescriptor(final String name) {
        return buildDescriptor(name);
    }

    private PropertyDescriptor buildDescriptor(final String propertyName) {
        return new PropertyDescriptor.Builder()
            .name(propertyName)
            .description(propertyName)
            .required(true)
            .sensitive(true)
            .build();
    }

    @Override
    public void onPropertyModified(final PropertyDescriptor descriptor, String oldValue, String newValue) {
    }

    @Override
    public List<PropertyDescriptor> getPropertyDescriptors() {
        return Collections.emptyList();
    }

    @Override
    public String getIdentifier() {
        return id;
    }

    @Override
    public String toString() {
        return "GhostParameterProvider[id=" + id + "]";
    }

    @Override
    public void initialize(final ParameterProviderInitializationContext config) throws InitializationException {

    }

    @Override
    public List<ParameterGroup> fetchParameters(final ConfigurationContext context) {
        throw new ProcessException("Unable to instantiate ParameterProvider class");
    }

}
