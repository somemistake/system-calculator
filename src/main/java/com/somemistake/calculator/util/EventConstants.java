package com.somemistake.calculator.util;

import com.somemistake.calculator.event.*;

public class EventConstants {
    public static final ContextEvent CONTEXT_STARTED = new ContextStarted();
    public static final ContextEvent CONTEXT_STOPPED = new ContextStopped();
    public static final ContextEvent CONTEXT_REFRESHED = new ContextRefreshed();
    public static final ContextEvent CONTEXT_CLOSED = new ContextClosed();
}
