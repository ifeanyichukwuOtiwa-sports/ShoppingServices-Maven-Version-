package io.codewithwinnie.shopping.client;



import java.util.List;
import java.util.logging.Logger;

import io.codewithwinnie.shopping.stubs.order.Order;
import io.codewithwinnie.shopping.stubs.order.OrderRequest;
import io.codewithwinnie.shopping.stubs.order.OrderResponse;
import io.codewithwinnie.shopping.stubs.order.OrderServiceGrpc;
import io.grpc.Channel;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 17/08/2022
 */

public class OrderClient {
    private static final Logger LOG = Logger.getLogger(OrderClient.class.getName());
    // get a stub object to make a blocking or asynchronous call to server
    // call the service method
    // before we get a stub object, we need to get HTTP/2 connection to the server through a channel

    private OrderServiceGrpc.OrderServiceBlockingStub orderServiceClientStub;

    public OrderClient(Channel channel) {
        this.orderServiceClientStub = OrderServiceGrpc.newBlockingStub(channel);
    }


    public List<Order> getOrders(final int userId) {
        // first build request object
        OrderRequest orderRequest = OrderRequest.newBuilder()
                .setUserId(userId)
                .build();
        LOG.info("Getting orders for user, Order client is calling OrderServer");

        final OrderResponse orderResponse = orderServiceClientStub.getOrderForUser(orderRequest);

        return orderResponse.getOrderList();
    }
}
