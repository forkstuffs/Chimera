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
package com.karuslabs.commons.command.synchronization;

import com.karuslabs.commons.command.tree.nodes.Argument;

import net.minecraft.server.v1_15_R1.*;

import org.junit.jupiter.api.Test;

import static com.mojang.brigadier.arguments.StringArgumentType.word;
import static org.junit.jupiter.api.Assertions.*;


class SynchronizationMapperTest {
    
    SynchronizationMapper mapper = SynchronizationMapper.MAPPER;
        
    
    @Test
    void suggestions_b() {
        assertSame(CompletionProviders.a, mapper.suggestions(Argument.<CommandListenerWrapper, String>builder("", word()).suggests((a, b) -> null).build()));
    }
    
    
    @Test
    void suggestions_null() {
        assertNull(mapper.suggestions(Argument.<CommandListenerWrapper, String>builder("", word()).build()));
    }
    
} 
