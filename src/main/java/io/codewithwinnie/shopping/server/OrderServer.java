package io.codewithwinnie.shopping.server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.codewithwinnie.shopping.service.grpc.OrderServiceImpl;
import io.codewithwinnie.shopping.service.grpc.UserServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 17/08/2022
 */

public class OrderServer {
    private static final Logger LOG = Logger.getLogger(OrderServer.class.getName());

    private Server server;

    public void startServer() {
        LOG.info("Starting OrderServer");
        int port = 50052;
        try {
            server = ServerBuilder.forPort(port)
                    .addService(new OrderServiceImpl())
                    .build()
                    .start();
            LOG.info("OrderServer started on port 50052");
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                LOG.info("Shutting down UserServer");
                try {
                    OrderServer.this.stopServer();
                } catch (InterruptedException e) {
                    LOG.log(Level.SEVERE, "Server Shutdown interrupted", e);
                }
                LOG.info("OrderServer shut down");
            }));
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Could not start OrderServer", e);
        }
    }

    public void stopServer() throws InterruptedException {
        LOG.info("Stopping OrderServer");
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        OrderServer server = new OrderServer();
        server.startServer();
        server.blockUntilShutdown();
    }

}
