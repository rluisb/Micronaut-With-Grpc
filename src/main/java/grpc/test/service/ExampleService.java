package grpc.test.service;

import grpc.test.ExampleReply;
import grpc.test.ExampleRequest;
import grpc.test.ExampleServiceGrpc;
import io.grpc.stub.StreamObserver;
import io.micronaut.grpc.annotation.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;


@Singleton
public class ExampleService {

    public String sayHelloTo(String name) {
        return "Hello ".concat(name);
    }
}