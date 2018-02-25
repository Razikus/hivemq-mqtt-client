package org.mqttbee.mqtt.codec.decoder.mqtt5;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mqttbee.api.mqtt.mqtt5.message.disconnect.Mqtt5Disconnect;
import org.mqttbee.api.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mqttbee.api.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode.MALFORMED_PACKET;
import static org.mqttbee.api.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode.PROTOCOL_ERROR;

class Mqtt5MessageDecoderUtilTest extends AbstractMqtt5DecoderTest {

    private ByteBuf in;

    Mqtt5MessageDecoderUtilTest() {
        super((code) -> null);
    }

    @BeforeEach
    protected void setUp() {
        super.setUp();
        in = channel.alloc().buffer();
    }

    @AfterEach
    protected void tearDown() {
        in.release();
        super.tearDown();
    }

    @Test
    void checkByteOnlyOnce() {
        in.writeByte(1);
        assertTrue(Mqtt5MessageDecoderUtil.checkByteOnlyOnce(false, "name", channel, in));
    }

    @Test
    void checkByteOnlyOnce_inputLengthTooShort() {
        assertFalse(Mqtt5MessageDecoderUtil.checkByteOnlyOnce(false, "name", channel, in));
        checkClosed(MALFORMED_PACKET);
    }

    @Test
    void checkShortOnlyOnce() {
        in.writeByte(0);
        in.writeByte(1);
        assertTrue(Mqtt5MessageDecoderUtil.checkShortOnlyOnce(false, "name", channel, in));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void checkShortOnlyOnce_inputLengthTooShort(final int length) {
        pushBytes(in, length);
        assertFalse(Mqtt5MessageDecoderUtil.checkShortOnlyOnce(false, "name", channel, in));
        checkClosed(MALFORMED_PACKET);
    }

    @Test
    void checkIntOnlyOnce() {
        in.writeByte(0);
        in.writeByte(0);
        in.writeByte(0);
        in.writeByte(1);
        assertTrue(Mqtt5MessageDecoderUtil.checkIntOnlyOnce(false, "name", channel, in));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    void checkIntOnlyOnce_inputLengthTooShort(final int length) {
        pushBytes(in, length);
        assertFalse(Mqtt5MessageDecoderUtil.checkIntOnlyOnce(false, "name", channel, in));
        checkClosed(MALFORMED_PACKET);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void checkBoolean(final int booleanAsInt) {
        assertTrue(Mqtt5MessageDecoderUtil.checkBoolean((byte) booleanAsInt, "name", channel));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 3, 1000})
    void checkBooleanIncorrect(final int booleanAsInt) {
        assertFalse(Mqtt5MessageDecoderUtil.checkBoolean((byte) booleanAsInt, "name", channel));
        checkClosed(PROTOCOL_ERROR);
    }

    private void pushBytes(final ByteBuf in, final int length) {
        IntStream.range(0, length).forEach(in::writeByte);
        assertEquals(length, in.readableBytes());
    }

    private void checkClosed(final Mqtt5DisconnectReasonCode reasonCode) {
        final Mqtt5Disconnect disconnect = channel.readOutbound();
        assertNotNull(disconnect);
        assertEquals(reasonCode, disconnect.getReasonCode());
    }

}