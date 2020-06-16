package com.ibm.garage.cpat.cp4i.MockProducer;

import javax.enterprise.context.ApplicationScoped;

import com.ibm.garage.cpat.cp4i.FinancialMessage.FinancialMessage;

import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class MockProducer {

    private FinancialMessage mock = new FinancialMessage(8, "MET", "SWISS", "bonds", "10/20/2019",
                                                         "10/21/2019", 12, 1822.38, 21868.55, 94, 7,
                                                         true, false, false, false, false);

    @Outgoing("compliance-mock-message")
    public Flowable<FinancialMessage> produceMock() {
        return Flowable.interval(5, TimeUnit.SECONDS)
                       .map(tick -> {
                            return mock;
                        });
    }
    
}