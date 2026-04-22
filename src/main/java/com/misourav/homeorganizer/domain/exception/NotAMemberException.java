package com.misourav.homeorganizer.domain.exception;

public class NotAMemberException extends DomainException {
    public NotAMemberException(String userRef, String householdRef) {
        super("User " + userRef + " is not a member of household " + householdRef);
    }
}