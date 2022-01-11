package com.test.web.core;

import io.muserver.*;

public class Application {

    public static void main(String[] args) {
        MuServer server = MuServerBuilder.httpServer().withHttpPort(8080)
                .addHandler(Method.GET, "/", (request, response, pathParams) -> {
                    System.out.println("Request from : " + request.remoteAddress());
                    response.write("Hello, world");
                })
                .start();
        System.out.println("Started server at " + server.uri());
    }
}
