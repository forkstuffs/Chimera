/*
 * The MIT License
 *
 * Copyright 2019 Karus Labs.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.karuslabs.commons.command.tree.nodes;

import com.karuslabs.commons.command.DispatcherCommand;

import com.mojang.brigadier.CommandDispatcher;

import org.bukkit.command.*;
import org.bukkit.plugin.Plugin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RootTest {
    
    Plugin plugin = when(mock(Plugin.class).getName()).thenReturn("test").getMock();
    CommandMap map = mock(CommandMap.class);
    Root root = new Root(plugin, map);
    
    
    @Test
    void addChild() {
        var literal = Literal.of("a").build();
        doReturn(true).when(map).register(any(String.class), any(Command.class));
        
        root.addChild(literal);
        
        verify(map).register(eq("test"), any(DispatcherCommand.class));
        assertNotNull(root.getChild("a"));
        assertNotNull(root.getChild("test:a"));
    }
    
    
    @Test
    void dispatcher() {
        var dispatcher = mock(CommandDispatcher.class);
        
        root.dispatcher(dispatcher);
        
        assertSame(dispatcher, root.dispatcher);
    }
    
    
    @Test
    void dispatcher_throws_exception() {
        var dispatcher = mock(CommandDispatcher.class);
        
        assertEquals(
            "CommandDispatcher is already initialized", 
            assertThrows(IllegalStateException.class, () -> { root.dispatcher(dispatcher); root.dispatcher(dispatcher); }).getMessage()
        );
    }

} 
