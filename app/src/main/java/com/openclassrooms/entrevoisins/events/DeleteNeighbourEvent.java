package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

/**
 * Event fired when a user deletes a Neighbour
 */
public class DeleteNeighbourEvent {

    /**
     * Neighbour to delete
     */
    public final Neighbour neighbour;

    /**
     * Constructor.
     * @param neighbour a neighbour from the list
     */
    public DeleteNeighbourEvent(Neighbour neighbour) {
        this.neighbour = neighbour;
    }
}