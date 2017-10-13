
package org.rapharino.entrance.core.serialize;


/**
 * 序列化器
 */
public interface Serialization {
    // 序列化
    <T> byte[] serialize(T obj);
    // 反序列化
    <T> T deserialize(byte[] data, Class<T> cls);
}
