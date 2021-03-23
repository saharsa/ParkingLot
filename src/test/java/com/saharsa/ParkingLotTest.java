package com.saharsa;

import com.saharsa.model.ParkingLot;
import com.saharsa.model.ParkingSpot;
import com.saharsa.model.Vehicle;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ParkingLotTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private ParkingLot parkingLot;

    @Mock
    private Vehicle vehicle;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        parkingLot = new ParkingLot();
    }


    @Test
    public void parkVehicleExceptionParkingLotNotCreated() throws Exception {
        exceptionRule.expect(Exception.class);
        exceptionRule.expectMessage("Parking lot not created yet");
        parkingLot.park(vehicle);
    }

    @Test
    public void parkVehicleSuccess() throws Exception {

        parkingLot.createParkingLot(6);

        assertTrue(parkingLot.getOccupiedSpots().isEmpty());
        assertFalse(parkingLot.getEmptySpots().isEmpty());
        assertEquals(parkingLot.getOccupiedSpots().size(), 0);
        assertEquals(parkingLot.getEmptySpots().size(), 6);

        Integer spotId = parkingLot.getNextAvailableSpot();
        ParkingSpot parkingSpot = parkingLot.park(vehicle);

        assertFalse(parkingLot.getOccupiedSpots().isEmpty());
        assertEquals(parkingLot.getOccupiedSpots().size(), 1);
        assertEquals(parkingLot.getEmptySpots().size(), 5);
        assertFalse(parkingSpot.isFree());
        assertEquals(parkingSpot.getSpotId(), spotId);
    }

    @Test
    public void leaveVehicleExceptionParkingLotNotCreated() throws Exception {
        exceptionRule.expect(Exception.class);
        exceptionRule.expectMessage("Parking lot not created yet");
        parkingLot.leave(1);
    }

    @Test
    public void leaveVehicleExceptionInvalidSpotId() throws Exception {
        exceptionRule.expect(Exception.class);
        exceptionRule.expectMessage("Such a parking spot does not exist");
        parkingLot.createParkingLot(6);
        parkingLot.leave(7);
    }

    @Test
    public void leaveVehicleExceptionSpotUnoccupied() throws Exception {
        exceptionRule.expect(Exception.class);
        exceptionRule.expectMessage("Parking spot unoccupied");
        parkingLot.createParkingLot(6);
        parkingLot.leave(5);
    }

    @Test
    public void leaveVehicleSuccess() throws Exception {

        parkingLot.createParkingLot(6);
        ParkingSpot assigned = parkingLot.park(vehicle);

        assertFalse(parkingLot.getOccupiedSpots().isEmpty());
        assertEquals(parkingLot.getOccupiedSpots().size(), 1);
        assertEquals(parkingLot.getEmptySpots().size(), 5);
        assertFalse(assigned.isFree());

        ParkingSpot vacated = parkingLot.leave(1);

        assertEquals(parkingLot.getOccupiedSpots().size(), 0);
        assertEquals(parkingLot.getEmptySpots().size(), 6);
        assertTrue(vacated.isFree());
    }


}
