package com.keep.sso.ticket.policy;

import java.io.Serializable;

public interface ExpirationPolicy extends Serializable {


    boolean isExpired();





    // final ZonedDateTime currentSystemTime = ZonedDateTime.now(ZoneOffset.UTC);
    //        final ZonedDateTime creationTime = ticketState.getCreationTime();
    //        final ZonedDateTime lastTimeUsed = ticketState.getLastTimeUsed();
    //
    //        // Ticket has been used, check maxTimeToLive (hard window)
    //        ZonedDateTime expirationTime;
    //        if(maxTimeToLiveInSeconds > 0) {
    //            expirationTime = creationTime.plus(this.maxTimeToLiveInSeconds, ChronoUnit.SECONDS);
    //            if (currentSystemTime.isAfter(expirationTime)) {
    //                LOGGER.debug(
    //                        "Ticket is expired because the time since creation is greater than maxTimeToLiveInSeconds");
    //                return true;
    //            }
    //        }
    //
    //        expirationTime = lastTimeUsed.plus(this.timeToKillInSeconds, ChronoUnit.SECONDS);
    //        if (currentSystemTime.isAfter(expirationTime)) {
    //            LOGGER.debug("Ticket is expired because the time since last use is greater than timeToKillInSeconds");
    //            return true;
    //        }
}