package utils;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Абстрактный класс для тестов DAO
 * Created by Баянов on 07.02.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/jpa-test-config.xml" , "/test-config-for-dao.xml"})
@ActiveProfiles("test")
public abstract class AbstractDaoTest {
}
