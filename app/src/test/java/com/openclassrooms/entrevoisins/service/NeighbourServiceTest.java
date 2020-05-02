package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.Objects;

import static com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours;
        neighbours = service.getNeighbours();
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(Objects.requireNonNull(DUMMY_NEIGHBOURS.toArray())));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void addNeighbourWithSuccess() {
        int sizeCount = service.getNeighbours().size();
        Neighbour newNeighbour = new Neighbour(22, "Joe", "https://i.pravatar.cc/150?u="+ System.currentTimeMillis(), "10 rue du lac", "060606060606", "c'est moi", false );
        service.createNeighbour(newNeighbour);
        assertEquals(service.getNeighbours().size(), sizeCount +1);
        Neighbour neighbourTest = service.getNeighbours().get(service.getNeighbours().size() - 1);
        assertEquals(neighbourTest.getId(), 22);
        assertEquals(neighbourTest.getName(), "Joe");
        assertEquals(neighbourTest.getAddress(), "10 rue du lac");
        assertEquals(neighbourTest.getPhoneNumber(), "060606060606");
        assertEquals(neighbourTest.getAboutMe(), "c'est moi");
        assertFalse(neighbourTest.isFavorite());
    }

    @Test
    public void addFavoriteWithSuccess() {
        int countFav = service.getFavorites().size();
        service.getNeighbours().get(0).setFavorite(true);
        assertEquals(service.getFavorites().size(), countFav + 1);
    }

    @Test
    public void removeFavoriteWithSuccess() {
        int countFav = service.getFavorites().size();
        Neighbour neighbour = (Neighbour) service.getFavorites().get(0);
        neighbour.setFavorite(false);
        assertEquals(service.getFavorites().size(), countFav - 1);
    }
}
