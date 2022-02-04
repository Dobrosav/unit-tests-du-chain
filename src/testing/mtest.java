package testing;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import simulator.DUChain;

public class mtest {
		  DU d;
		  @Before
		  public void setup() {
			  DUChainInterFace di=mock(DUChainInterFace.class);
			  when(di.getRowNoUse()).thenReturn(3);
			  d=new DU("s",3,3);
			  d.setD(di);
		  }
		
		@Test
		  public void testGet() {
			  //d.setD(service);
			 // when(d.getRowNoUse()).thenReturn(3);
			  assertEquals(3,d.getRowNoUse());
		  }
}