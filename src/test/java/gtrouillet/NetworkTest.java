package gtrouillet;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class NetworkTest {

    private Network network;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        network = new Network(8);
        network.connect(1, 2);
        network.connect(1, 6);
        network.connect(2, 4);
        network.connect(2, 6);
        network.connect(5, 8);
    }

    @Test
    public void shouldReturnAffirmativeForDirectlyConnectedNodes() {
        assertThat(network.query(1, 6), is(true));
    }

    @Test
    public void shouldReturnAffirmativeForIndirectlyConnectedNodes() {
        assertThat(network.query(1, 4), is(true));
    }

    @Test
    public void shouldReturnNegativeForUnconnectedNodes() {
        assertThat(network.query(1, 8), is(false));
    }

    @Test
    public void shouldReturnAffirmativeForSameNode() {
        assertThat(network.query(1, 1), is(true));
    }

    @Test
    public void shouldReturnNegativeForUnconnectedNode() {
        final int node = 3;
        final Integer[] others = new Integer[]{1, 2, 4, 5, 6, 7, 8};
        Arrays.stream(others).forEach(n -> assertThat(network.query(node, n), is(false)));
    }

    @Test
    public void shouldReturnAffirmativeOnlyForNeighborhood() {
        final int node = 1;
        final Integer[] connected = new Integer[]{1, 2, 4, 6};
        final Integer[] unconnected = new Integer[]{3, 5, 7, 8};
        Arrays.stream(connected).forEach(n -> assertThat(network.query(node, n), is(true)));
        Arrays.stream(unconnected).forEach(n -> assertThat(network.query(node, n), is(false)));
    }

    @Test
    public void shouldThrowExceptionForInvalidSizeParameter() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid size parameter, only a positive integer is allowed");

        new Network(-2);
    }

    @Test
    public void shouldThrowExceptionForInvalidNodeAParameter() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("NodeA must be between 1 and Network size 4");

        final Network n = new Network(4);
        n.connect(0, 2);
    }

    @Test
    public void shouldThrowExceptionForInvalidNodeBParameter() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("NodeB must be between 1 and Network size 4");

        final Network n = new Network(4);
        n.connect(2, 5);
    }
}
