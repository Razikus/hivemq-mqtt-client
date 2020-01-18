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

package com.hivemq.client.mqtt.mqtt5.message.subscribe;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author David Katz
 * @author Silvio Giebl
 */
class Mqtt5RetainHandlingTest {

    @Test
    void getCode_send() {
        assertEquals(0x00, Mqtt5RetainHandling.SEND.getCode());
    }

    @Test
    void getCode_sendIfSubscriptionDoesNotExist() {
        assertEquals(0x01, Mqtt5RetainHandling.SEND_IF_SUBSCRIPTION_DOES_NOT_EXIST.getCode());
    }

    @Test
    void getCode_doNotSend() {
        assertEquals(0x02, Mqtt5RetainHandling.DO_NOT_SEND.getCode());
    }

    @ParameterizedTest
    @EnumSource(Mqtt5RetainHandling.class)
    void fromCode(final @NotNull Mqtt5RetainHandling retainHandling) {
        assertEquals(retainHandling, Mqtt5RetainHandling.fromCode(retainHandling.getCode()));
    }

    @Test
    void fromCode_invalidCodes() {
        assertNull(Mqtt5RetainHandling.fromCode(0x03));
        assertNull(Mqtt5RetainHandling.fromCode(0xFF));
        assertNull(Mqtt5RetainHandling.fromCode(-1));
    }
}