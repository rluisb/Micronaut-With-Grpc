package grpc.test.grpc.api;

import grpc.test.ExampleReply;
import grpc.test.ExampleRequest;
import grpc.test.ExampleServiceGrpc;
import grpc.test.service.ExampleService;
import io.grpc.stub.StreamObserver;

import javax.inject.Singleton;

@Singleton
public class ExampleEndpoint extends ExampleServiceGrpc.ExampleServiceImplBase {

    private ExampleService exampleService;

    public ExampleEndpoint(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @Override
    public void send(ExampleRequest exampleRequest, StreamObserver<ExampleReply> exampleReplyStreamObserver) {
        var message = exampleService.sayHelloTo(exampleRequest.getName());
        var reply = ExampleReply.newBuilder()
                .setMessage("Hello ".concat(message))
                .build();
        exampleReplyStreamObserver.onNext(reply);
        exampleReplyStreamObserver.onCompleted();
    }

    @Override
    public StreamObserver<ExampleRequest> sendMany(StreamObserver<ExampleReply> exampleReplyStreamObserver) {

        StreamObserver<ExampleRequest> requestStreamObserver = new StreamObserver<ExampleRequest>() {

            String result = "";

            @Override
            public void onNext(ExampleRequest exampleRequest) {
                result += exampleService.sayHelloTo(exampleRequest.getName()).concat("\n");
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(String.format("Erro: %s", t.getMessage()));
            }

            @Override
            public void onCompleted() {
                exampleReplyStreamObserver.onNext(
                        ExampleReply.newBuilder()
                                .setMessage(result)
                                .build()
                );

                exampleReplyStreamObserver.onCompleted();
            }
        };

        return requestStreamObserver;
    }
}
