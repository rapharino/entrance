
package org.rapharino.entrance.core.remote.netty.code;

import java.util.List;

import org.rapharino.entrance.core.serialize.Protostuff;
import org.rapharino.entrance.core.serialize.Serialization;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class Decoder extends ByteToMessageDecoder {

    private Class<?> genericClass;

    private Serialization serialization;

    public Decoder(Class<?> genericClass) {
        this(genericClass, new Protostuff());
    }

    public Decoder(Class<?> genericClass, Serialization serialization) {
        this.genericClass = genericClass;
        this.serialization = serialization;
    }

    @Override
    public final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (dataLength < 0) {
            ctx.close();
        }
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        Object obj = serialization.deserialize(data, genericClass);
        out.add(obj);
    }
}
