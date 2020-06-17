package com.ibm.garage.cpat.cp4i.MockProducer;

import javax.enterprise.context.ApplicationScoped;

import com.ibm.garage.cpat.cp4i.FinancialMessage.FinancialMessage;

import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;
import java.util.Random;

@ApplicationScoped
public class MockProducer {

    private Random random = new Random();

    private FinancialMessage mock = new FinancialMessage(random.nextInt(100), "MET", "SWISS", "bonds", "10/20/2019",
                                                         "10/21/2019", 12, 1822.38, 21868.55, 94, 7,
                                                         true, false, false, false, false);

    @Outgoing("compliance-mock-message")
    public Flowable<FinancialMessage> produceMock() {
        return Flowable.interval(10, TimeUnit.SECONDS)
                       .map(tick -> {
                            return setRandomUserId(mock);
                        });
    }

    public FinancialMessage setRandomUserId(FinancialMessage mock) {
        mock.user_id = random.nextInt(100);
        return mock;
    }

    // @Outgoing("compliance-mock-message")
    // public Flowable<FinancialMessage> produceMock() {
    //     return Flowable.just(mock);
    // }
    
}