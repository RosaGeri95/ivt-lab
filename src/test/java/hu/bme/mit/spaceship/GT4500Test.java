package hu.bme.mit.spaceship;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockPrimary;
  private TorpedoStore mockSecondary;

  @Before
  public void init(){

    mockPrimary = mock(TorpedoStore.class);
    mockSecondary = mock(TorpedoStore.class);

    this.ship = new GT4500(mockPrimary, mockSecondary);

  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockPrimary.isEmpty()).thenReturn(false);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrimary, times(1)).fire(1);
    verify(mockPrimary, times(1)).isEmpty();
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockPrimary.getTorpedoCount()).thenReturn(3);
    when(mockPrimary.fire(3)).thenReturn(true);
    when(mockPrimary.isEmpty()).thenReturn(false);
    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimary, times(1)).isEmpty();
    verify(mockPrimary, times(1)).fire(3);
  }

  @Test
  public void fire_All_Torpedo_When_Primary_Is_Empty(){
    when(mockPrimary.isEmpty()).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(false);
    when(mockSecondary.getTorpedoCount()).thenReturn(15);
    when(mockSecondary.fire(15)).thenReturn(true);

    ship.fireTorpedo(FiringMode.ALL);

    verify(mockPrimary, times(1)).isEmpty();
    verify(mockSecondary, times(1)).isEmpty();
    verify(mockSecondary, times(1)).fire(15);
  }

  @Test
  public void fire_all_when_no_ammo(){
    when(mockPrimary.isEmpty()).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(true);

    ship.fireTorpedo(FiringMode.ALL);

    verify(mockPrimary, times(1)).isEmpty();
    verify(mockSecondary, times(1)).isEmpty();
  }


  @Test
    public void fireTorpedo_Secondary_After_Primary_Success(){
    // Arrange
    when(mockSecondary.fire(1)).thenReturn(true);
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(false);
    when(mockPrimary.isEmpty()).thenReturn(false);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockSecondary, times(1)).fire(1);
    verify(mockSecondary, times(1)).isEmpty();

    verify(mockPrimary, times(1)).fire(1);
    verify(mockPrimary, times(1)).isEmpty();
  }


  @Test
  public void fireTorpedo_Repeated_Secondary_Success(){
    // Arrange
    when(mockSecondary.fire(1)).thenReturn(true);
    when(mockPrimary.isEmpty()).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(false);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockSecondary, times(1)).fire(1);
    verify(mockPrimary, times(1)).isEmpty();
    verify(mockSecondary, times(1)).isEmpty();
  }

  @Test
  public void fireTorpedo_Primary_After_Secondary_After_Primary_Success(){
    // Arrange
    when(mockSecondary.fire(1)).thenReturn(true);
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(false);
    when(mockPrimary.isEmpty()).thenReturn(false);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockSecondary, times(1)).fire(1);
    verify(mockSecondary, times(1)).isEmpty();

    verify(mockPrimary, times(2)).fire(1);
    verify(mockPrimary, times(2)).isEmpty();
  }

}
