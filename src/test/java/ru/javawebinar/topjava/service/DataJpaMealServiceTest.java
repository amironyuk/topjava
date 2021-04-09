package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles(resolver = Profiles.DataJpaResolver.class)
public class DataJpaMealServiceTest extends MealServiceTest {
}
