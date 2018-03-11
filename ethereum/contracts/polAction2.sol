pragma solidity ^0.4.17; 

contract polAction2 {
    // Parameters of the event. Times are either
    // absolute unix timestamps (seconds since 1970-01-01)
    // or time periods in seconds.
    address private attendee;
    //msg.sender = organization
    uint public eventEndTime;
    uint public eventValue;

    // Set to true at the end, disallows any change
    bool ended;

    // Modifiers can be used to easily change the behaviour of functions. 
    // For example, they can automatically check a condition prior to executing the function.
    // It is good to put conditions that are reused into modifiers so you don't have to repeat code 
    modifier eventOpen(){
        // Revert the call if the bidding
        // period is over.
        require(now <= eventEndTime);
        _;
    }

    // The following is a so-called natspec comment,
    // recognizable by the three slashes.
    // It will be shown when the user is asked to
    // confirm a transaction.

    /// Create a simple event with `_eventTime`
    /// seconds event time on behalf of the
    /// attendee address `_attendee`.
    function polAction2(uint _eventTime, address _attendee, uint _eventType) public {
        attendee = _attendee;
        eventEndTime = now + _eventTime;
        eventValue = _eventType*2;
    }

    function endEvent() public {
        // It is a good guideline to structure functions that interact
        // with other contracts (i.e. they call functions or send Ether)
        // into three phases:
        // 1. checking conditions
        // 2. performing actions (potentially changing conditions)
        // 3. interacting with other contracts
        // If these phases are mixed up, the other contract could call
        // back into the current contract and modify the state or cause
        // effects (ether payout) to be performed multiple times.
        // If functions called internally include interaction with external
        // contracts, they also have to be considered interaction with
        // external contracts.

        // 1. Conditions
        require(now >= eventEndTime); // auction did not yet end
        require(!ended); // this function has already been called

        // 2. Effects
        ended = true;

        // 3. Interaction
        attendee.transfer(eventValue);
    }
}
