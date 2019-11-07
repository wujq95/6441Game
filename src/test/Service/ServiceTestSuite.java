package Service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AttackServiceTest.class, CardServiceTest.class, FortifyServiceTest.class, GamePlayerServiceTest.class,
        MapEditorServiceTest.class, ReinforceServiceTest.class})
public class ServiceTestSuite {
}


