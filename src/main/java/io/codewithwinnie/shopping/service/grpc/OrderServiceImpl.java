package io.codewithwinnie.shopping.service.grpc;

import java.util.List;
import java.util.logging.Logger;

import com.google.protobuf.util.Timestamps;

import io.codewithwinnie.shopping.dao.OrderDao;
import io.codewithwinnie.shopping.domain.Order;
import io.codewithwinnie.shopping.stubs.order.OrderRequest;
import io.codewithwinnie.shopping.stubs.order.OrderResponse;
import io.codewithwinnie.shopping.stubs.order.OrderServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 17/08/2022
 */

public class OrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {
    private static final Logger LOG = Logger.getLogger(OrderServiceImpl.class.getName());
    private OrderDao orderDao = new OrderDao();
    @Override
    public void getOrderForUser(final OrderRequest request, final StreamObserver<OrderResponse> responseObserver) {
        List<Order> orders = orderDao.getOrders(request.getUserId());
        LOG.info("Got " + orders.size() + " orders for user " + request.getUserId() + "and converting to Order Proto");
        List<io.codewithwinnie.shopping.stubs.order.Order> ordersForUser =
                orders.stream().map(order -> io.codewithwinnie.shopping.stubs.order.Order.newBuilder()
                .setUserId(order.getUserId())
                .setId(order.getId())
                .setNumberOfItems(order.getNoOfItems())
                .setTotalAmount(order.getTotalAmount())
                .setOrderDate(Timestamps.fromMillis(order.getDate().getTime()))
                .build()
        ).toList();

        OrderResponse response = OrderResponse.newBuilder()
                .addAllOrder(ordersForUser)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
