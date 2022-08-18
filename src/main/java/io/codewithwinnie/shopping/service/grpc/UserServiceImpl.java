package io.codewithwinnie.shopping.service.grpc;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import io.codewithwinnie.shopping.client.OrderClient;
import io.codewithwinnie.shopping.dao.UserDao;
import io.codewithwinnie.shopping.domain.User;
import io.codewithwinnie.shopping.stubs.order.Order;
import io.codewithwinnie.shopping.stubs.user.Gender;
import io.codewithwinnie.shopping.stubs.user.UserRequest;
import io.codewithwinnie.shopping.stubs.user.UserResponse;
import io.codewithwinnie.shopping.stubs.user.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 17/08/2022
 */

public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase  {
    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class.getName());
    private static final UserDao userDao = new UserDao();

    //invoking order clients
    @Override
    public void getUserDetails(final UserRequest request, final StreamObserver<UserResponse> responseObserver) {
         User user = userDao.getUserDetails(request.getUsername());

         UserResponse.Builder userResponseBuilder =
                 UserResponse.newBuilder()
                         .setId(user.getId())
                         .setUsername(user.getUsername())
                         .setName(user.getName())
                         .setAge(user.getAge())
                         .setGender(Gender.valueOf(user.getGender()));


        final List<Order> orderList = getOrders(userResponseBuilder);


        userResponseBuilder.setNoOfOrders(orderList.size());
        userResponseBuilder.addAllOrders(orderList);



        UserResponse response = userResponseBuilder.build();


         try {
             responseObserver.onNext(response);
         } catch (RuntimeException e) {
             responseObserver.onError(e);
         } finally {

             responseObserver.onCompleted();
         }
    }

    private static List<Order> getOrders(final UserResponse.Builder userResponseBuilder) {
        LOG.info("Creating channel and calling the order server");
        final ManagedChannel orderChannel =
                ManagedChannelBuilder.forAddress("localhost", 50052)
                        .usePlaintext()
                        .build();


        OrderClient orderClient = new OrderClient(orderChannel);


        final List<Order> orderList = orderClient.getOrders(userResponseBuilder.getId());

        try {
            orderChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOG.severe("Interrupted while shutting down channel");
        }
        return orderList;
    }
}
