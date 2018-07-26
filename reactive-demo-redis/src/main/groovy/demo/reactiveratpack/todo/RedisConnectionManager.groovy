package demo.reactiveratpack.todo

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import groovy.transform.CompileStatic
import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection

@CompileStatic
class RedisConnectionManager {

    private final RedisClient redisClient
    private final StatefulRedisConnection<String, String> connection
    private final ObjectMapper mapper

    RedisConnectionManager() {
        redisClient= RedisClient.create("redis://localhost");
        connection = redisClient.connect();
        mapper = new ObjectMapper()
        mapper.registerModule(new JavaTimeModule())
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true)
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        mapper
    }

    RedisClient getRedisClient() {
        return redisClient
    }

    StatefulRedisConnection<String, String> getConnection() {
        return connection
    }


}
