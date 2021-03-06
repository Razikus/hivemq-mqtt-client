/*
 * Copyright 2018 dc-square and the HiveMQ MQTT Client Project
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
 *
 */

package com.hivemq.client.mqtt;

import com.hivemq.client.annotations.CheckReturnValue;
import com.hivemq.client.annotations.DoNotImplement;
import org.jetbrains.annotations.NotNull;

/**
 * Builder for a {@link MqttProxyConfig}.
 *
 * @author Silvio Giebl
 * @since 1.2
 */
@DoNotImplement
public interface MqttProxyConfigBuilder extends MqttProxyConfigBuilderBase<MqttProxyConfigBuilder> {

    /**
     * Builds the {@link MqttProxyConfig}.
     *
     * @return the built {@link MqttProxyConfig}.
     */
    @CheckReturnValue
    @NotNull MqttProxyConfig build();

    /**
     * Builder for a {@link MqttProxyConfig} that is applied to a parent.
     *
     * @param <P> the type of the result when the built {@link MqttProxyConfig} is applied to the parent.
     */
    @DoNotImplement
    interface Nested<P> extends MqttProxyConfigBuilderBase<Nested<P>> {

        /**
         * Builds the {@link MqttProxyConfig} and applies it to the parent.
         *
         * @return the result when the built {@link MqttProxyConfig} is applied to the parent.
         */
        @NotNull P applyProxyConfig();
    }
}
