package xyz.ms.social_spring;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class ModularityTests {
    @Test
    void verifyModulithStructure() {
        ApplicationModules.of(SocialSpringApplication.class).verify();
    }
}
