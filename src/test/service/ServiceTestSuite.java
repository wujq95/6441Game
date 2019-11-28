package service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AttackServiceTest.class, service.CardServiceTest.class, FortifyServiceTest.class, GamePlayerServiceTest.class,
        MapEditorServiceTest.class, ReinforceServiceTest.class})
public class ServiceTestSuite {
}
