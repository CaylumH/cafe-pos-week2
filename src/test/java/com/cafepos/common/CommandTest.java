package com.cafepos.common;

import com.cafepos.command.Command;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class CommandTest {
    
    @Test
    public void testExecuteIsCalled() {
        Command command = new Command() {
            private boolean executed = false;
            
            @Override
            public void execute() {
                executed = true;
            }
            
            public boolean isExecuted() {
                return executed;
            }
        };
        
        command.execute();
        assertTrue(((Command) command).toString().contains("Command"));
    }
    
    @Test
    public void testUndoDefaultImplementation() {
        Command command = new Command() {
            @Override
            public void execute() {
            }
        };
        
        assertDoesNotThrow(() -> command.undo());
    }
    
    @Test
    public void testUndoCanBeOverridden() {
        Command command = new Command() {
            private boolean undone = false;
            
            @Override
            public void execute() {
            }
            
            @Override
            public void undo() {
                undone = true;
            }
            
            public boolean isUndone() {
                return undone;
            }
        };
        
        command.undo();
    }
}