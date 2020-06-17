package com.ibm.garage.cpat.cp4i.ComplianceService;

import com.ibm.garage.cpat.cp4i.FinancialMessage.FinancialMessage;

import io.reactivex.Flowable;
import io.smallrye.reactive.messaging.annotations.Broadcast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.reactive.messaging.Incoming;



@ApplicationScoped
public class ComplianceService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ComplianceService.class);

    // @Incoming annotation denotes the incoming channel that we'll be reading from.
    // The @Outgoing denotes the outgoing channel that we'll be sending to.
    @Incoming("pre-compliance-check")
    @Outgoing("post-compliance-check")
    @Broadcast
    public Flowable<FinancialMessage> processCompliance(FinancialMessage financialMessage) {

        FinancialMessage receivedMessage = financialMessage;

        LOGGER.info("Message received from topic = {}", receivedMessage);

        if (receivedMessage.compliance_services && !receivedMessage.technical_validation) {
            /*
            Check whether compliance_services is true and technical_validation is false. If so
            we flip the boolean values to indicate that the next microservice (technical_validation)
            in this case is ready to process the message. Since for these service boolean values, having
            a value of true means that it's ready to be processed.
            */
            receivedMessage.compliance_services = false;
            receivedMessage.technical_validation = true;
        
            return Flowable.just(receivedMessage);
        }

        else {
            return Flowable.empty();
        }

        // return (receivedMessage.compliance_services) ? Flowable.just(complianceCheckComplete(receivedMessage)) : Flowable.empty();
    }

    // public FinancialMessage complianceCheckComplete (FinancialMessage complianceMessage) {
    //     FinancialMessage checkedMessage = complianceMessage;
    //     checkedMessage.compliance_services = false;
    //     return checkedMessage;
    // }
}