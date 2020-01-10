package ua.enjoy.graduation.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ua.enjoy.graduation.TimingExtension;

@ExtendWith({SpringExtension.class, TimingExtension.class})
@SpringBootTest
@Transactional
public abstract class AbstractServiceTest {
}
