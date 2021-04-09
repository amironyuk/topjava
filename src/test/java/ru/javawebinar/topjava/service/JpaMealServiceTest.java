package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles(resolver = Profiles.JpaResolver.class)
public class JpaMealServiceTest extends MealServiceTest {
}
