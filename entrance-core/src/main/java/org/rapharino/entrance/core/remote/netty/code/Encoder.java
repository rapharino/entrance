
package org.rapharino.entrance.core.remote.netty.code;

import org.rapharino.entrance.core.serialize.Protostuff;
import org.rapharino.entrance.core.serialize.Serialization;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class Encoder extends MessageToByteEncoder<Object> {

    private Class<?> genericClass;

    private Serialization serialization;

    public Encoder(Class<?> genericClass) {
        this.genericClass = genericClass;
        this.serialization = new Protostuff();
    }

    public Encoder(Class<?> genericClass, Serialization serialization) {
        this.genericClass = genericClass;
        this.serialization = serialization;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if (genericClass.isInstance(in)) {

            byte[] data = serialization.serialize(in);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}
