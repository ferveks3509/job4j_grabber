package ru.job4j;

import org.quartz.SchedulerException;

public interface Grab {
    void init() throws SchedulerException;
}
