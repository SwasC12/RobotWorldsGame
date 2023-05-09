package za.co.wethinkcode.Server.World;

public enum UpdateResponse {
        Done, //position was updated successfully
        FAILED_OUTSIDE_WORLD, //robot will go outside world limits if allowed, so it failed to update the position
        Obstructed, //robot obstructed by at least one obstacle, thus cannot proceed.

}
