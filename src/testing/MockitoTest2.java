package testing;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import simulator.DUChain;

public class MockitoTest2 {
	DU d;
	  @Before
	  public void setup() {
		  DUChainInterFace di=mock(DUChainInterFace.class);
		  when(di.getVariable()).thenReturn("s");
		  d=new DU("s",3,3);
		  d.setD(di);
	  }
	
	@Test
	  public void testGet() {
		  //d.setD(service);
		 // when(d.getRowNoUse()).thenReturn(3);
		  assertEquals("s",d.getVariable());
	  }
}
