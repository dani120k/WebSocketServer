package io.netty.websocket;

import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * (c) Antonio Rodriges, rodriges@wikience.org
 */
public class MessageHandler {
    // testing purposes
    private static int TIMOUT_VERSION = 2000;
    private static int CORRUPT_BSON_VERSION = 3000;
    private static Logger LOGGER = LoggerFactory.getLogger(MessageHandler.class);


    private byte[] rawMsg;
    private String message;
    private ChannelHandlerContext ctx;

    private byte[] response = getErrorBlob();

    public MessageHandler(byte[] rawMsg, ChannelHandlerContext ctx) {
        this.rawMsg = rawMsg;


        String str = "";
        for(int i = 0; i < rawMsg.length; i++)
            str+= (char)rawMsg[i];

        LOGGER.info("info" + str);
        this.ctx = ctx;
    }

    public void handle() {
        serveRequest();

        sendMessage(response);

    }

    public void serveRequest(){
        System.out.println(rawMsg);
    }

    public byte[] getErrorBlob() {
        return "$ERROR".getBytes();
    }

    public byte[] getSuccessBlob() {
        return "$SUCCESS".getBytes();
    }

    public void sendMessage(byte[] msg) {
        ctx.channel().writeAndFlush(
                new BinaryWebSocketFrame(Unpooled.wrappedBuffer(msg)));
    }
}

